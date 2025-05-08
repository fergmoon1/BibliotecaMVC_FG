package com.biblioteca.dao;

import com.biblioteca.model.DVD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DVDDAOImpl implements DVDDAO {

    @Override
    public void agregarDVD(DVD dvd) {
        String sqlElemento = "INSERT INTO ElementoBiblioteca (titulo, autor, ano_publicacion, tipo) VALUES (?, ?, ?, ?)";
        String sqlDVD = "INSERT INTO DVD (id_dvd, director, duracion_minutos, genero, formato) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Insertar en ElementoBiblioteca
            try (PreparedStatement psElemento = conn.prepareStatement(sqlElemento, PreparedStatement.RETURN_GENERATED_KEYS)) {
                psElemento.setString(1, dvd.getTitulo());
                psElemento.setString(2, dvd.getAutor());
                psElemento.setInt(3, dvd.getAnoPublicacion());
                psElemento.setString(4, "DVD");
                psElemento.executeUpdate();

                // Obtener el ID generado
                ResultSet rs = psElemento.getGeneratedKeys();
                if (rs.next()) {
                    dvd.setId(rs.getInt(1));
                } else {
                    throw new SQLException("No se pudo obtener el ID generado para el DVD.");
                }
            }

            // Insertar en DVD
            try (PreparedStatement psDVD = conn.prepareStatement(sqlDVD)) {
                psDVD.setInt(1, dvd.getId());
                psDVD.setString(2, dvd.getDirector());
                psDVD.setInt(3, dvd.getDuracion());
                psDVD.setString(4, dvd.getGenero());
                psDVD.setString(5, dvd.getFormato());
                psDVD.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    @Override
    public void actualizarDVD(DVD dvd) {
        String sqlElemento = "UPDATE ElementoBiblioteca SET titulo = ?, autor = ?, ano_publicacion = ? WHERE id = ? AND tipo = 'DVD'";
        String sqlDVD = "UPDATE DVD SET director = ?, duracion_minutos = ?, genero = ?, formato = ? WHERE id_dvd = ?";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement psElemento = conn.prepareStatement(sqlElemento)) {
                psElemento.setString(1, dvd.getTitulo());
                psElemento.setString(2, dvd.getAutor());
                psElemento.setInt(3, dvd.getAnoPublicacion());
                psElemento.setInt(4, dvd.getId());
                psElemento.executeUpdate();
            }

            try (PreparedStatement psDVD = conn.prepareStatement(sqlDVD)) {
                psDVD.setString(1, dvd.getDirector());
                psDVD.setInt(2, dvd.getDuracion());
                psDVD.setString(3, dvd.getGenero());
                psDVD.setString(4, dvd.getFormato());
                psDVD.setInt(5, dvd.getId());
                psDVD.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    @Override
    public void eliminarDVD(int id) {
        String sqlDVD = "DELETE FROM DVD WHERE id_dvd = ?";
        String sqlElemento = "DELETE FROM ElementoBiblioteca WHERE id = ? AND tipo = 'DVD'";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement psDVD = conn.prepareStatement(sqlDVD)) {
                psDVD.setInt(1, id);
                psDVD.executeUpdate();
            }

            try (PreparedStatement psElemento = conn.prepareStatement(sqlElemento)) {
                psElemento.setInt(1, id);
                psElemento.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    @Override
    public List<DVD> obtenerTodosLosDVDs() {
        List<DVD> lista = new ArrayList<>();
        String sql = "SELECT e.id, e.titulo, e.autor, e.ano_publicacion, d.director, d.duracion_minutos, d.genero, d.formato " +
                "FROM ElementoBiblioteca e JOIN DVD d ON e.id = d.id_dvd WHERE e.tipo = 'DVD'";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DVD dvd = new DVD();
                    dvd.setId(rs.getInt("id"));
                    dvd.setTitulo(rs.getString("titulo"));
                    dvd.setAutor(rs.getString("autor"));
                    dvd.setAnoPublicacion(rs.getInt("ano_publicacion"));
                    dvd.setDirector(rs.getString("director"));
                    dvd.setDuracion(rs.getInt("duracion_minutos"));
                    dvd.setGenero(rs.getString("genero"));
                    dvd.setFormato(rs.getString("formato"));
                    lista.add(dvd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return lista;
    }

    @Override
    public List<DVD> buscarPorGenero(String genero) {
        List<DVD> lista = new ArrayList<>();
        String sql = "SELECT e.id, e.titulo, e.autor, e.ano_publicacion, d.director, d.duracion_minutos, d.genero, d.formato " +
                "FROM ElementoBiblioteca e JOIN DVD d ON e.id = d.id_dvd WHERE e.tipo = 'DVD' AND d.genero LIKE ?";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "%" + genero + "%");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    DVD dvd = new DVD();
                    dvd.setId(rs.getInt("id"));
                    dvd.setTitulo(rs.getString("titulo"));
                    dvd.setAutor(rs.getString("autor"));
                    dvd.setAnoPublicacion(rs.getInt("ano_publicacion"));
                    dvd.setDirector(rs.getString("director"));
                    dvd.setDuracion(rs.getInt("duracion_minutos"));
                    dvd.setGenero(rs.getString("genero"));
                    dvd.setFormato(rs.getString("formato"));
                    lista.add(dvd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return lista;
    }
}