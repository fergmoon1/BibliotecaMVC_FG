package com.biblioteca.dao;

import com.biblioteca.model.ElementoBiblioteca;
import com.biblioteca.model.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    @Override
    public boolean crear(Libro libro) {
        String sqlElemento = "INSERT INTO ElementoBiblioteca (titulo, autor, ano_publicacion, tipo) VALUES (?, ?, ?, ?)";
        String sqlLibro = "INSERT INTO Libro (id_libro, isbn, numero_paginas, genero, editorial) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            // Insertar en ElementoBiblioteca
            try (PreparedStatement stmtElemento = conn.prepareStatement(sqlElemento, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmtElemento.setString(1, libro.getTitulo());
                stmtElemento.setString(2, libro.getAutor());
                stmtElemento.setInt(3, libro.getAnoPublicacion());
                stmtElemento.setString(4, "Libro");
                stmtElemento.executeUpdate();

                // Obtener el ID generado
                ResultSet rs = stmtElemento.getGeneratedKeys();
                if (rs.next()) {
                    libro.setId(rs.getInt(1));
                } else {
                    throw new SQLException("No se pudo obtener el ID generado para el libro.");
                }
            }

            // Insertar en Libro
            try (PreparedStatement stmtLibro = conn.prepareStatement(sqlLibro)) {
                stmtLibro.setInt(1, libro.getId());
                stmtLibro.setString(2, libro.getIsbn());
                stmtLibro.setInt(3, libro.getNumeroPaginas());
                stmtLibro.setString(4, libro.getGenero());
                stmtLibro.setString(5, libro.getEditorial());
                stmtLibro.executeUpdate();
            }

            conn.commit(); // Confirmar transacción
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Revertir transacción en caso de error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    @Override
    public Libro leer(int id) {
        String sql = "SELECT e.*, l.* FROM ElementoBiblioteca e LEFT JOIN Libro l ON e.id = l.id_libro WHERE e.id = ? AND e.tipo = 'Libro'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setAnoPublicacion(rs.getInt("ano_publicacion"));
                libro.setTipo(rs.getString("tipo"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setNumeroPaginas(rs.getInt("numero_paginas"));
                libro.setGenero(rs.getString("genero"));
                libro.setEditorial(rs.getString("editorial"));
                return libro;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean actualizar(Libro libro) {
        String sqlElemento = "UPDATE ElementoBiblioteca SET titulo = ?, autor = ?, ano_publicacion = ? WHERE id = ? AND tipo = 'Libro'";
        String sqlLibro = "UPDATE Libro SET isbn = ?, numero_paginas = ?, genero = ?, editorial = ? WHERE id_libro = ?";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            // Actualizar en ElementoBiblioteca
            try (PreparedStatement stmtElemento = conn.prepareStatement(sqlElemento)) {
                stmtElemento.setString(1, libro.getTitulo());
                stmtElemento.setString(2, libro.getAutor());
                stmtElemento.setInt(3, libro.getAnoPublicacion());
                stmtElemento.setInt(4, libro.getId());
                stmtElemento.executeUpdate();
            }

            // Actualizar en Libro
            try (PreparedStatement stmtLibro = conn.prepareStatement(sqlLibro)) {
                stmtLibro.setString(1, libro.getIsbn());
                stmtLibro.setInt(2, libro.getNumeroPaginas());
                stmtLibro.setString(3, libro.getGenero());
                stmtLibro.setString(4, libro.getEditorial());
                stmtLibro.setInt(5, libro.getId());
                stmtLibro.executeUpdate();
            }

            conn.commit(); // Confirmar transacción
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Revertir transacción en caso de error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sqlLibro = "DELETE FROM Libro WHERE id_libro = ?";
        String sqlElemento = "DELETE FROM ElementoBiblioteca WHERE id = ? AND tipo = 'Libro'";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            // Eliminar de Libro
            try (PreparedStatement stmtLibro = conn.prepareStatement(sqlLibro)) {
                stmtLibro.setInt(1, id);
                stmtLibro.executeUpdate();
            }

            // Eliminar de ElementoBiblioteca
            try (PreparedStatement stmtElemento = conn.prepareStatement(sqlElemento)) {
                stmtElemento.setInt(1, id);
                stmtElemento.executeUpdate();
            }

            conn.commit(); // Confirmar transacción
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Revertir transacción en caso de error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    @Override
    public List<Libro> obtenerTodos() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT e.*, l.* FROM ElementoBiblioteca e LEFT JOIN Libro l ON e.id = l.id_libro WHERE e.tipo = 'Libro'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setAnoPublicacion(rs.getInt("ano_publicacion"));
                libro.setTipo(rs.getString("tipo"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setNumeroPaginas(rs.getInt("numero_paginas"));
                libro.setGenero(rs.getString("genero"));
                libro.setEditorial(rs.getString("editorial"));
                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    @Override
    public List<Libro> buscarPorGenero(String genero) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT e.*, l.* FROM ElementoBiblioteca e JOIN Libro l ON e.id = l.id_libro WHERE e.tipo = 'Libro' AND l.genero LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + genero + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setAnoPublicacion(rs.getInt("ano_publicacion"));
                libro.setTipo(rs.getString("tipo"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setNumeroPaginas(rs.getInt("numero_paginas"));
                libro.setGenero(rs.getString("genero"));
                libro.setEditorial(rs.getString("editorial"));
                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }
}