package com.biblioteca.dao;

import com.biblioteca.model.ElementoBiblioteca;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Revista;
import com.biblioteca.model.DVD;
import com.biblioteca.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ElementoBibliotecaDAOImpl implements ElementoBibliotecaDAO {

    @Override
    public List<ElementoBiblioteca> obtenerTodos() throws SQLException {
        List<ElementoBiblioteca> elementos = new ArrayList<>();

        // Obtener Libros
        String sqlLibros = "SELECT * FROM libros";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlLibros);
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
                elementos.add(libro);
            }
        }

        // Obtener Revistas
        String sqlRevistas = "SELECT * FROM revistas";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlRevistas);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Revista revista = new Revista();
                revista.setId(rs.getInt("id"));
                revista.setTitulo(rs.getString("titulo"));
                revista.setAutor(rs.getString("autor"));
                revista.setAnoPublicacion(rs.getInt("ano_publicacion"));
                revista.setNombreEdicion(rs.getString("nombre_edicion"));
                revista.setCategoria(rs.getString("categoria"));
                elementos.add(revista);
            }
        }

        // Obtener DVDs
        String sqlDVDs = "SELECT * FROM dvds";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlDVDs);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DVD dvd = new DVD();
                dvd.setId(rs.getInt("id"));
                dvd.setTitulo(rs.getString("titulo"));
                dvd.setAutor(rs.getString("autor"));
                dvd.setAnoPublicacion(rs.getInt("ano_publicacion"));
                dvd.setDuracion(rs.getInt("duracion"));
                dvd.setGenero(rs.getString("genero"));
                dvd.setDirector(rs.getString("director"));
                dvd.setFormato(rs.getString("formato"));
                elementos.add(dvd);
            }
        }

        return elementos;
    }

    @Override
    public List<ElementoBiblioteca> buscarPorGenero(String genero) throws SQLException {
        List<ElementoBiblioteca> elementos = new ArrayList<>();

        // Buscar Libros
        String sqlLibros = "SELECT * FROM libros WHERE genero = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlLibros)) {
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
                    elementos.add(libro);
                }
            }
        }

        // Buscar Revistas (usando categoria en lugar de genero)
        String sqlRevistas = "SELECT * FROM revistas WHERE categoria = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlRevistas)) {
            stmt.setString(1, genero);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Revista revista = new Revista();
                    revista.setId(rs.getInt("id"));
                    revista.setTitulo(rs.getString("titulo"));
                    revista.setAutor(rs.getString("autor"));
                    revista.setAnoPublicacion(rs.getInt("ano_publicacion"));
                    revista.setNombreEdicion(rs.getString("nombre_edicion"));
                    revista.setCategoria(rs.getString("categoria"));
                    elementos.add(revista);
                }
            }
        }

        // Buscar DVDs
        String sqlDVDs = "SELECT * FROM dvds WHERE genero = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlDVDs)) {
            stmt.setString(1, genero);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DVD dvd = new DVD();
                    dvd.setId(rs.getInt("id"));
                    dvd.setTitulo(rs.getString("titulo"));
                    dvd.setAutor(rs.getString("autor"));
                    dvd.setAnoPublicacion(rs.getInt("ano_publicacion"));
                    dvd.setDuracion(rs.getInt("duracion"));
                    dvd.setGenero(rs.getString("genero"));
                    dvd.setDirector(rs.getString("director"));
                    dvd.setFormato(rs.getString("formato"));
                    elementos.add(dvd);
                }
            }
        }

        return elementos;
    }

    @Override
    public boolean agregarElemento(ElementoBiblioteca elemento) throws SQLException {
        if (elemento instanceof Libro) {
            Libro libro = (Libro) elemento;
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
        } else if (elemento instanceof Revista) {
            Revista revista = (Revista) elemento;
            String sql = "INSERT INTO revistas (titulo, autor, ano_publicacion, nombre_edicion, categoria) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, revista.getTitulo());
                stmt.setString(2, revista.getAutor());
                stmt.setInt(3, revista.getAnoPublicacion());
                stmt.setString(4, revista.getNombreEdicion());
                stmt.setString(5, revista.getCategoria());
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } else if (elemento instanceof DVD) {
            DVD dvd = (DVD) elemento;
            String sql = "INSERT INTO dvds (titulo, autor, ano_publicacion, duracion, genero, director, formato) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, dvd.getTitulo());
                stmt.setString(2, dvd.getAutor());
                stmt.setInt(3, dvd.getAnoPublicacion());
                stmt.setInt(4, dvd.getDuracion());
                stmt.setString(5, dvd.getGenero());
                stmt.setString(6, dvd.getDirector());
                stmt.setString(7, dvd.getFormato());
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        }
        return false;
    }

    @Override
    public boolean actualizarElemento(ElementoBiblioteca elemento) throws SQLException {
        if (elemento instanceof Libro) {
            Libro libro = (Libro) elemento;
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
        } else if (elemento instanceof Revista) {
            Revista revista = (Revista) elemento;
            String sql = "UPDATE revistas SET titulo = ?, autor = ?, ano_publicacion = ?, nombre_edicion = ?, categoria = ? WHERE id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, revista.getTitulo());
                stmt.setString(2, revista.getAutor());
                stmt.setInt(3, revista.getAnoPublicacion());
                stmt.setString(4, revista.getNombreEdicion());
                stmt.setString(5, revista.getCategoria());
                stmt.setInt(6, revista.getId());
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } else if (elemento instanceof DVD) {
            DVD dvd = (DVD) elemento;
            String sql = "UPDATE dvds SET titulo = ?, autor = ?, ano_publicacion = ?, duracion = ?, genero = ?, director = ?, formato = ? WHERE id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, dvd.getTitulo());
                stmt.setString(2, dvd.getAutor());
                stmt.setInt(3, dvd.getAnoPublicacion());
                stmt.setInt(4, dvd.getDuracion());
                stmt.setString(5, dvd.getGenero());
                stmt.setString(6, dvd.getDirector());
                stmt.setString(7, dvd.getFormato());
                stmt.setInt(8, dvd.getId());
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        }
        return false;
    }

    @Override
    public boolean eliminarElemento(int id) throws SQLException {
        // Intentar eliminar de cada tabla hasta que se encuentre el elemento
        String sqlLibro = "DELETE FROM libros WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlLibro)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        }

        String sqlRevista = "DELETE FROM revistas WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlRevista)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        }

        String sqlDVD = "DELETE FROM dvds WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlDVD)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}