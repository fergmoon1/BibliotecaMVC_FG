package com.biblioteca.dao;

import com.biblioteca.model.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {
    private final Connection connection;

    public LibroDAOImpl() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión a la BD", e);
        }
    }

    @Override
    public void agregarLibro(Libro libro) {
        String sqlElemento = "INSERT INTO elemento_biblioteca(titulo, autor, anio_publicacion, tipo) VALUES (?, ?, ?, ?)";
        String sqlLibro = "INSERT INTO libro(id, isbn, numero_paginas, genero, editorial) VALUES (?, ?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmtElemento = connection.prepareStatement(sqlElemento, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement stmtLibro = connection.prepareStatement(sqlLibro)) {

                // Insertar en elemento_biblioteca
                stmtElemento.setString(1, libro.getTitulo());
                stmtElemento.setString(2, libro.getAutor());
                stmtElemento.setInt(3, libro.getAnio()); // Cambiado de getAnioPublicacion() a getAnio()
                stmtElemento.setString(4, "LIBRO");
                stmtElemento.executeUpdate();

                // Obtener ID generado
                try (ResultSet generatedKeys = stmtElemento.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        // Insertar en tabla libro
                        stmtLibro.setInt(1, id);
                        stmtLibro.setString(2, libro.getIsbn());
                        stmtLibro.setInt(3, libro.getNumeroPaginas());
                        stmtLibro.setString(4, libro.getGenero());
                        stmtLibro.setString(5, libro.getEditorial());
                        stmtLibro.executeUpdate();
                    }
                }
                connection.commit();
            }
        } catch (SQLException e) {
            rollback(connection);
            throw new RuntimeException("Error al agregar libro", e);
        } finally {
            resetAutoCommit(connection);
        }
    }

    @Override
    public Libro obtenerLibroPorId(int id) {
        String sql = "SELECT e.*, l.isbn, l.numero_paginas, l.genero, l.editorial " +
                "FROM elemento_biblioteca e JOIN libro l ON e.id = l.id WHERE e.id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapLibro(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener libro por ID", e);
        }
        return null;
    }

    @Override
    public List<Libro> obtenerTodosLosLibros() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT e.*, l.isbn, l.numero_paginas, l.genero, l.editorial " +
                "FROM elemento_biblioteca e JOIN libro l ON e.id = l.id";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                libros.add(mapLibro(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todos los libros", e);
        }
        return libros;
    }

    @Override
    public List<Libro> buscarPorGenero(String genero) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT e.*, l.isbn, l.numero_paginas, l.genero, l.editorial " +
                "FROM elemento_biblioteca e JOIN libro l ON e.id = l.id WHERE l.genero LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + genero + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    libros.add(mapLibro(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar libros por género", e);
        }
        return libros;
    }

    private Libro mapLibro(ResultSet rs) throws SQLException {
        Libro libro = new Libro();
        libro.setId(rs.getInt("id"));
        libro.setTitulo(rs.getString("titulo"));
        libro.setAutor(rs.getString("autor"));
        libro.setAnio(rs.getInt("anio_publicacion")); // Cambiado de setAnioPublicacion() a setAnio()
        libro.setIsbn(rs.getString("isbn"));
        libro.setNumeroPaginas(rs.getInt("numero_paginas"));
        libro.setGenero(rs.getString("genero"));
        libro.setEditorial(rs.getString("editorial"));
        return libro;
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
    public void actualizarLibro(Libro libro) {
        String sqlElemento = "UPDATE elemento_biblioteca SET titulo=?, autor=?, anio_publicacion=? WHERE id=?";
        String sqlLibro = "UPDATE libro SET isbn=?, numero_paginas=?, genero=?, editorial=? WHERE id=?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmtElemento = connection.prepareStatement(sqlElemento);
                 PreparedStatement stmtLibro = connection.prepareStatement(sqlLibro)) {

                // Actualizar elemento_biblioteca
                stmtElemento.setString(1, libro.getTitulo());
                stmtElemento.setString(2, libro.getAutor());
                stmtElemento.setInt(3, libro.getAnio()); // Cambiado de getAnioPublicacion() a getAnio()
                stmtElemento.setInt(4, libro.getId());
                stmtElemento.executeUpdate();

                // Actualizar libro
                stmtLibro.setString(1, libro.getIsbn());
                stmtLibro.setInt(2, libro.getNumeroPaginas());
                stmtLibro.setString(3, libro.getGenero());
                stmtLibro.setString(4, libro.getEditorial());
                stmtLibro.setInt(5, libro.getId());
                stmtLibro.executeUpdate();

                connection.commit();
            }
        } catch (SQLException e) {
            rollback(connection);
            throw new RuntimeException("Error al actualizar libro", e);
        } finally {
            resetAutoCommit(connection);
        }
    }

    @Override
    public void eliminarLibro(int id) {
        String sqlLibro = "DELETE FROM libro WHERE id=?";
        String sqlElemento = "DELETE FROM elemento_biblioteca WHERE id=?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmtLibro = connection.prepareStatement(sqlLibro);
                 PreparedStatement stmtElemento = connection.prepareStatement(sqlElemento)) {

                // Eliminar primero de libro (por la FK)
                stmtLibro.setInt(1, id);
                stmtLibro.executeUpdate();

                // Luego eliminar de elemento_biblioteca
                stmtElemento.setInt(1, id);
                stmtElemento.executeUpdate();

                connection.commit();
            }
        } catch (SQLException e) {
            rollback(connection);
            throw new RuntimeException("Error al eliminar libro", e);
        } finally {
            resetAutoCommit(connection);
        }
    }
}