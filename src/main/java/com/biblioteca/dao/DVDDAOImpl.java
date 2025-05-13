package com.biblioteca.dao;

import com.biblioteca.model.DVD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DVDDAOImpl implements DVDDAO {
    private final Connection connection;

    public DVDDAOImpl() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión a la BD", e);
        }
    }

    @Override
    public void agregarDVD(DVD dvd) {
        String sqlElemento = "INSERT INTO elemento_biblioteca(titulo, autor, anio_publicacion, tipo) VALUES (?, ?, ?, ?)";
        String sqlDVD = "INSERT INTO dvd(id, duracion, genero) VALUES (?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmtElemento = connection.prepareStatement(sqlElemento, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement stmtDVD = connection.prepareStatement(sqlDVD)) {

                // Insertar en elemento_biblioteca
                stmtElemento.setString(1, dvd.getTitulo());
                stmtElemento.setString(2, dvd.getAutor()); // Usar getAutor()
                stmtElemento.setInt(3, dvd.getAnio()); // Corregido a getAnio()
                stmtElemento.setString(4, "DVD");
                stmtElemento.executeUpdate();

                // Obtener ID generado
                try (ResultSet generatedKeys = stmtElemento.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        // Insertar en tabla DVD
                        stmtDVD.setInt(1, id);
                        stmtDVD.setInt(2, dvd.getDuracion());
                        stmtDVD.setString(3, dvd.getGenero());
                        stmtDVD.executeUpdate();
                    }
                }
                connection.commit();
            }
        } catch (SQLException e) {
            rollback(connection);
            throw new RuntimeException("Error al agregar DVD", e);
        } finally {
            resetAutoCommit(connection);
        }
    }

    @Override
    public void actualizarDVD(DVD dvd) {
        String sqlElemento = "UPDATE elemento_biblioteca SET titulo=?, autor=?, anio_publicacion=? WHERE id=?";
        String sqlDVD = "UPDATE dvd SET duracion=?, genero=? WHERE id=?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmtElemento = connection.prepareStatement(sqlElemento);
                 PreparedStatement stmtDVD = connection.prepareStatement(sqlDVD)) {

                // Actualizar elemento_biblioteca
                stmtElemento.setString(1, dvd.getTitulo());
                stmtElemento.setString(2, dvd.getAutor()); // Usar getAutor()
                stmtElemento.setInt(3, dvd.getAnio()); // Corregido a getAnio()
                stmtElemento.setInt(4, dvd.getId());
                stmtElemento.executeUpdate();

                // Actualizar DVD
                stmtDVD.setInt(1, dvd.getDuracion());
                stmtDVD.setString(2, dvd.getGenero());
                stmtDVD.setInt(3, dvd.getId());
                stmtDVD.executeUpdate();

                connection.commit();
            }
        } catch (SQLException e) {
            rollback(connection);
            throw new RuntimeException("Error al actualizar DVD", e);
        } finally {
            resetAutoCommit(connection);
        }
    }

    @Override
    public void eliminarDVD(int id) {
        String sqlDVD = "DELETE FROM dvd WHERE id=?";
        String sqlElemento = "DELETE FROM elemento_biblioteca WHERE id=?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmtDVD = connection.prepareStatement(sqlDVD);
                 PreparedStatement stmtElemento = connection.prepareStatement(sqlElemento)) {

                // Eliminar primero de DVD (por la FK)
                stmtDVD.setInt(1, id);
                stmtDVD.executeUpdate();

                // Luego eliminar de elemento_biblioteca
                stmtElemento.setInt(1, id);
                stmtElemento.executeUpdate();

                connection.commit();
            }
        } catch (SQLException e) {
            rollback(connection);
            throw new RuntimeException("Error al eliminar DVD", e);
        } finally {
            resetAutoCommit(connection);
        }
    }

    @Override
    public DVD obtenerDVDPorId(int id) {
        String sql = "SELECT e.*, d.duracion, d.genero FROM elemento_biblioteca e JOIN dvd d ON e.id = d.id WHERE e.id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapDVD(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener DVD por ID", e);
        }
        return null;
    }

    @Override
    public List<DVD> obtenerTodosLosDVDs() {
        List<DVD> dvds = new ArrayList<>();
        String sql = "SELECT e.*, d.duracion, d.genero FROM elemento_biblioteca e JOIN dvd d ON e.id = d.id";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                dvds.add(mapDVD(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todos los DVDs", e);
        }
        return dvds;
    }

    @Override
    public List<DVD> buscarPorGenero(String genero) {
        List<DVD> dvds = new ArrayList<>();
        String sql = "SELECT e.*, d.duracion, d.genero FROM elemento_biblioteca e JOIN dvd d ON e.id = d.id WHERE d.genero LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + genero + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dvds.add(mapDVD(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar DVDs por género", e);
        }
        return dvds;
    }

    private DVD mapDVD(ResultSet rs) throws SQLException {
        DVD dvd = new DVD();
        dvd.setId(rs.getInt("id"));
        dvd.setTitulo(rs.getString("titulo"));
        dvd.setAutor(rs.getString("autor")); // Mapear 'autor' desde la base de datos
        dvd.setAnio(rs.getInt("anio_publicacion"));
        dvd.setDuracion(rs.getInt("duracion"));
        dvd.setGenero(rs.getString("genero"));
        return dvd;
    }

    private void rollback(Connection conn) {
        try {
            if (conn != null) conn.rollback();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al hacer rollback", ex);
        }
    }

    private void resetAutoCommit(Connection conn) {
        try {
            if (conn != null) conn.setAutoCommit(true);
        } catch (SQLException ex) {
            throw new RuntimeException("Error al resetear autocommit", ex);
        }
    }
}