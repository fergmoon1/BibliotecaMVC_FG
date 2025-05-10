package com.biblioteca.dao;

import com.biblioteca.model.Libro;
import com.biblioteca.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    @Override
    public List<Libro> obtenerTodos() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setAnoPublicacion(rs.getInt("ano_publicacion"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setNumeroPaginas(rs.getInt("numero_paginas"));
                libro.setGenero(rs.getString("genero"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setTipo("Libro"); // Valor por defecto para tipo
                libros.add(libro);
            }
        }
        return libros;
    }

    @Override
    public List<Libro> buscarPorGenero(String genero) throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE genero = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, genero);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Libro libro = new Libro();
                    libro.setId(rs.getInt("id"));
                    libro.setTitulo(rs.getString("titulo"));
                    libro.setAutor(rs.getString("autor"));
                    libro.setAnoPublicacion(rs.getInt("ano_publicacion"));
                    libro.setIsbn(rs.getString("isbn"));
                    libro.setNumeroPaginas(rs.getInt("numero_paginas"));
                    libro.setGenero(rs.getString("genero"));
                    libro.setEditorial(rs.getString("editorial"));
                    libro.setTipo("Libro"); // Valor por defecto para tipo
                    libros.add(libro);
                }
            }
        }
        return libros;
    }

    @Override
    public boolean agregarLibro(Libro libro) throws SQLException {
        String sql = "INSERT INTO libros (titulo, autor, ano_publicacion, isbn, numero_paginas, genero, editorial) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setInt(3, libro.getAnoPublicacion());
            stmt.setString(4, libro.getIsbn());
            stmt.setInt(5, libro.getNumeroPaginas());
            stmt.setString(6, libro.getGenero());
            stmt.setString(7, libro.getEditorial());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public boolean actualizarLibro(Libro libro) throws SQLException {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, ano_publicacion = ?, isbn = ?, numero_paginas = ?, genero = ?, editorial = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setInt(3, libro.getAnoPublicacion());
            stmt.setString(4, libro.getIsbn());
            stmt.setInt(5, libro.getNumeroPaginas());
            stmt.setString(6, libro.getGenero());
            stmt.setString(7, libro.getEditorial());
            stmt.setInt(8, libro.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public boolean eliminarLibro(int id) throws SQLException {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}