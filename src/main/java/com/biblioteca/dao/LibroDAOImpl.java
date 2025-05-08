package com.biblioteca.dao;

import com.biblioteca.model.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {
    private ElementoBibliotecaDAO elementoDAO;

    public LibroDAOImpl() {
        this.elementoDAO = new ElementoBibliotecaDAOImpl();
    }

    @Override
    public boolean crear(Libro libro) {
        // Primero insertar en ElementoBiblioteca
        if (!elementoDAO.crear(libro)) {
            return false;
        }
        // Luego insertar en Libro
        String sql = "INSERT INTO Libro (id_libro, isbn, numero_paginas, genero, editorial) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, libro.getId());
            stmt.setString(2, libro.getIsbn());
            stmt.setInt(3, libro.getNumeroPaginas());
            stmt.setString(4, libro.getGenero());
            stmt.setString(5, libro.getEditorial());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Libro leer(int id) {
        // Primero leer de ElementoBiblioteca
        ElementoBibliotecaDAOImpl elementoDAOImpl = new ElementoBibliotecaDAOImpl();
        ElementoBiblioteca elemento = elementoDAOImpl.leer(id);
        if (elemento == null || !elemento.getTipo().equals("Libro")) {
            return null;
        }
        // Luego leer de Libro
        String sql = "SELECT * FROM Libro WHERE id_libro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Libro libro = new Libro();
                libro.setId(elemento.getId());
                libro.setTitulo(elemento.getTitulo());
                libro.setAutor(elemento.getAutor());
                libro.setAnoPublicacion(elemento.getAnoPublicacion());
                libro.setTipo(elemento.getTipo());
                libro.setIsbn(rs.getString("isbn"));
                libro.setNumeroPaginas(rs.getInt("numero_paginas"));
                libro.setGenero(rs.getString("genero"));
                libro.setEditorial(rs.getString("editorial"));
                rs.close();
                return libro;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean actualizar(Libro libro) {
        // Actualizar en ElementoBiblioteca
        if (!elementoDAO.actualizar(libro)) {
            return false;
        }
        // Actualizar en Libro
        String sql = "UPDATE Libro SET isbn = ?, numero_paginas = ?, genero = ?, editorial = ? WHERE id_libro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, libro.getIsbn());
            stmt.setInt(2, libro.getNumeroPaginas());
            stmt.setString(3, libro.getGenero());
            stmt.setString(4, libro.getEditorial());
            stmt.setInt(5, libro.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        // Eliminar de Libro
        String sqlLibro = "DELETE FROM Libro WHERE id_libro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlLibro)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        // Eliminar de ElementoBiblioteca
        return elementoDAO.eliminar(id);
    }

    @Override
    public List<Libro> obtenerTodos() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT e.*, l.* FROM ElementoBiblioteca e JOIN Libro l ON e.id = l.id_libro WHERE e.tipo = 'Libro'";
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
}