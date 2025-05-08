package com.biblioteca.dao;

import com.biblioteca.model.Revista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RevistaDAOImpl implements RevistaDAO {

    @Override
    public void agregarRevista(Revista revista) {
        String sqlElemento = "INSERT INTO ElementoBiblioteca (titulo, autor, ano_publicacion, tipo) VALUES (?, ?, ?, ?)";
        String sqlRevista = "INSERT INTO Revista (id_revista, nombre_edicion, categoria) VALUES (?, ?, ?)";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Insertar en ElementoBiblioteca
            try (PreparedStatement psElemento = conn.prepareStatement(sqlElemento, PreparedStatement.RETURN_GENERATED_KEYS)) {
                psElemento.setString(1, revista.getTitulo());
                psElemento.setString(2, revista.getAutor());
                psElemento.setInt(3, revista.getAnoPublicacion());
                psElemento.setString(4, "Revista");
                psElemento.executeUpdate();

                // Obtener el ID generado
                ResultSet rs = psElemento.getGeneratedKeys();
                if (rs.next()) {
                    revista.setId(rs.getInt(1));
                } else {
                    throw new SQLException("No se pudo obtener el ID generado para la revista.");
                }
            }

            // Insertar en Revista
            try (PreparedStatement psRevista = conn.prepareStatement(sqlRevista)) {
                psRevista.setInt(1, revista.getId());
                psRevista.setString(2, revista.getNombreEdicion());
                psRevista.setString(3, revista.getCategoria());
                psRevista.executeUpdate();
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
    public void actualizarRevista(Revista revista) {
        String sqlElemento = "UPDATE ElementoBiblioteca SET titulo = ?, autor = ?, ano_publicacion = ? WHERE id = ? AND tipo = 'Revista'";
        String sqlRevista = "UPDATE Revista SET nombre_edicion = ?, categoria = ? WHERE id_revista = ?";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement psElemento = conn.prepareStatement(sqlElemento)) {
                psElemento.setString(1, revista.getTitulo());
                psElemento.setString(2, revista.getAutor());
                psElemento.setInt(3, revista.getAnoPublicacion());
                psElemento.setInt(4, revista.getId());
                psElemento.executeUpdate();
            }

            try (PreparedStatement psRevista = conn.prepareStatement(sqlRevista)) {
                psRevista.setString(1, revista.getNombreEdicion());
                psRevista.setString(2, revista.getCategoria());
                psRevista.setInt(3, revista.getId());
                psRevista.executeUpdate();
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
    public void eliminarRevista(int id) {
        String sqlRevista = "DELETE FROM Revista WHERE id_revista = ?";
        String sqlElemento = "DELETE FROM ElementoBiblioteca WHERE id = ? AND tipo = 'Revista'";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement psRevista = conn.prepareStatement(sqlRevista)) {
                psRevista.setInt(1, id);
                psRevista.executeUpdate();
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
    public List<Revista> obtenerTodasLasRevistas() {
        List<Revista> lista = new ArrayList<>();
        String sql = "SELECT e.id, e.titulo, e.autor, e.ano_publicacion, r.nombre_edicion, r.categoria " +
                "FROM ElementoBiblioteca e JOIN Revista r ON e.id = r.id_revista WHERE e.tipo = 'Revista'";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Revista revista = new Revista();
                    revista.setId(rs.getInt("id"));
                    revista.setTitulo(rs.getString("titulo"));
                    revista.setAutor(rs.getString("autor"));
                    revista.setAnoPublicacion(rs.getInt("ano_publicacion"));
                    revista.setNombreEdicion(rs.getString("nombre_edicion"));
                    revista.setCategoria(rs.getString("categoria"));
                    lista.add(revista);
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
    public List<Revista> buscarPorCategoria(String categoria) {
        List<Revista> lista = new ArrayList<>();
        String sql = "SELECT e.id, e.titulo, e.autor, e.ano_publicacion, r.nombre_edicion, r.categoria " +
                "FROM ElementoBiblioteca e JOIN Revista r ON e.id = r.id_revista WHERE e.tipo = 'Revista' AND r.categoria LIKE ?";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "%" + categoria + "%");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Revista revista = new Revista();
                    revista.setId(rs.getInt("id"));
                    revista.setTitulo(rs.getString("titulo"));
                    revista.setAutor(rs.getString("autor"));
                    revista.setAnoPublicacion(rs.getInt("ano_publicacion"));
                    revista.setNombreEdicion(rs.getString("nombre_edicion"));
                    revista.setCategoria(rs.getString("categoria"));
                    lista.add(revista);
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