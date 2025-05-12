package com.biblioteca.dao;

import com.biblioteca.model.Revista;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RevistaDAOImpl implements RevistaDAO {
    private final Connection connection;

    public RevistaDAOImpl() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }

    @Override
    public void agregarRevista(Revista revista) {
        String sqlElemento = "INSERT INTO elemento_biblioteca(titulo, autor, anio_publicacion, tipo) VALUES (?, ?, ?, ?)";
        String sqlRevista = "INSERT INTO revista(id, numero_edicion, categoria) VALUES (?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmtElemento = connection.prepareStatement(sqlElemento, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement stmtRevista = connection.prepareStatement(sqlRevista)) {

                // Insertar en elemento_biblioteca
                stmtElemento.setString(1, revista.getTitulo());
                stmtElemento.setString(2, revista.getAutor());
                stmtElemento.setInt(3, revista.getAnioPublicacion());
                stmtElemento.setString(4, "REVISTA");
                stmtElemento.executeUpdate();

                // Obtener ID generado
                try (ResultSet generatedKeys = stmtElemento.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        // Insertar en revista
                        stmtRevista.setInt(1, id);
                        stmtRevista.setInt(2, revista.getNumeroEdicion());
                        stmtRevista.setString(3, revista.getCategoria());
                        stmtRevista.executeUpdate();
                    }
                }
                connection.commit();
            }
        } catch (SQLException e) {
            rollback(connection);
            throw new RuntimeException("Error al agregar revista", e);
        } finally {
            resetAutoCommit(connection);
        }
    }

    @Override
    public Revista obtenerRevistaPorId(int id) {
        String sql = "SELECT e.*, r.numero_edicion, r.categoria FROM elemento_biblioteca e JOIN revista r ON e.id = r.id WHERE e.id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRevista(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener revista por ID", e);
        }
        return null;
    }

    @Override
    public List<Revista> obtenerTodasLasRevistas() {
        List<Revista> revistas = new ArrayList<>();
        String sql = "SELECT e.*, r.numero_edicion, r.categoria FROM elemento_biblioteca e JOIN revista r ON e.id = r.id";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                revistas.add(mapRevista(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener revistas", e);
        }
        return revistas;
    }

    @Override
    public List<Revista> findByCategoria(String categoria) {
        List<Revista> revistas = new ArrayList<>();
        String sql = "SELECT e.*, r.numero_edicion, r.categoria FROM elemento_biblioteca e JOIN revista r ON e.id = r.id WHERE r.categoria LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + categoria + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    revistas.add(mapRevista(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por categoría", e);
        }
        return revistas;
    }

    private Revista mapRevista(ResultSet rs) throws SQLException {
        Revista revista = new Revista();
        revista.setId(rs.getInt("id"));
        revista.setTitulo(rs.getString("titulo"));
        revista.setAutor(rs.getString("autor"));
        revista.setAnioPublicacion(rs.getInt("anio_publicacion"));
        revista.setNumeroEdicion(rs.getInt("numero_edicion"));
        revista.setCategoria(rs.getString("categoria"));
        return revista;
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

    @Override
    public void actualizarRevista(Revista revista) {
        String sqlElemento = "UPDATE elemento_biblioteca SET titulo=?, autor=?, anio_publicacion=? WHERE id=?";
        String sqlRevista = "UPDATE revista SET numero_edicion=?, categoria=? WHERE id=?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmtElemento = connection.prepareStatement(sqlElemento);
                 PreparedStatement stmtRevista = connection.prepareStatement(sqlRevista)) {

                // Actualizar elemento_biblioteca
                stmtElemento.setString(1, revista.getTitulo());
                stmtElemento.setString(2, revista.getAutor());
                stmtElemento.setInt(3, revista.getAnioPublicacion());
                stmtElemento.setInt(4, revista.getId());
                stmtElemento.executeUpdate();

                // Actualizar revista
                stmtRevista.setInt(1, revista.getNumeroEdicion());
                stmtRevista.setString(2, revista.getCategoria());
                stmtRevista.setInt(3, revista.getId());
                stmtRevista.executeUpdate();

                connection.commit();
            }
        } catch (SQLException e) {
            rollback(connection);
            throw new RuntimeException("Error al actualizar revista", e);
        } finally {
            resetAutoCommit(connection);
        }
    }

    @Override
    public void eliminarRevista(int id) {
        String sqlRevista = "DELETE FROM revista WHERE id=?";
        String sqlElemento = "DELETE FROM elemento_biblioteca WHERE id=?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmtRevista = connection.prepareStatement(sqlRevista);
                 PreparedStatement stmtElemento = connection.prepareStatement(sqlElemento)) {

                // Eliminar primero de revista (por la FK)
                stmtRevista.setInt(1, id);
                stmtRevista.executeUpdate();

                // Luego eliminar de elemento_biblioteca
                stmtElemento.setInt(1, id);
                stmtElemento.executeUpdate();

                connection.commit();
            }
        } catch (SQLException e) {
            rollback(connection);
            throw new RuntimeException("Error al eliminar revista", e);
        } finally {
            resetAutoCommit(connection);
        }
    }
}