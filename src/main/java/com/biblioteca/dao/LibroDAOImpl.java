package com.biblioteca.dao;

import com.biblioteca.model.Libro;
import com.biblioteca.model.ElementoBiblioteca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta de LibroDAO para interactuar con la tabla Libros.
 */
public class LibroDAOImpl implements LibroDAO {

    private Connection connection;

    public LibroDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean crear(ElementoBiblioteca elemento) {
        if (!(elemento instanceof Libro)) return false;
        Libro libro = (Libro) elemento;
        String sql = "INSERT INTO Libros (titulo, autor, ano_publicacion, isbn, genero_principal, genero, editorial) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setInt(3, libro.getAnoPublicacion());
            stmt.setString(4, libro.getISBN());
            stmt.setString(5, libro.getGeneroPrincipal());
            stmt.setString(6, libro.getGenero());
            stmt.setString(7, libro.getEditorial());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    libro.setId(generatedKeys.getInt(1));
                }
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ElementoBiblioteca buscarPorId(int id) {
        String sql = "SELECT * FROM Libros WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setAnoPublicacion(rs.getInt("ano_publicacion"));
                libro.setISBN(rs.getString("isbn"));
                libro.setGeneroPrincipal(rs.getString("genero_principal"));
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
    public List<ElementoBiblioteca> obtenerTodos() {
        List<ElementoBiblioteca> elementos = new ArrayList<>();
        String sql = "SELECT * FROM Libros";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setAnoPublicacion(rs.getInt("ano_publicacion"));
                libro.setISBN(rs.getString("isbn"));
                libro.setGeneroPrincipal(rs.getString("genero_principal"));
                libro.setGenero(rs.getString("genero"));
                libro.setEditorial(rs.getString("editorial"));
                elementos.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elementos;
    }

    @Override
    public boolean actualizar(ElementoBiblioteca elemento) {
        if (!(elemento instanceof Libro)) return false;
        Libro libro = (Libro) elemento;
        String sql = "UPDATE Libros SET titulo = ?, autor = ?, ano_publicacion = ?, isbn = ?, genero_principal = ?, genero = ?, editorial = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setInt(3, libro.getAnoPublicacion());
            stmt.setString(4, libro.getISBN());
            stmt.setString(5, libro.getGeneroPrincipal());
            stmt.setString(6, libro.getGenero());
            stmt.setString(7, libro.getEditorial());
            stmt.setInt(8, libro.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Libros WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Libro buscarPorISBN(String isbn) {
        String sql = "SELECT * FROM Libros WHERE isbn = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setAnoPublicacion(rs.getInt("ano_publicacion"));
                libro.setISBN(rs.getString("isbn"));
                libro.setGeneroPrincipal(rs.getString("genero_principal"));
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
    public List<Libro> obtenerPorGeneroPrincipal(String generoPrincipal) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM Libros WHERE genero_principal = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, generoPrincipal);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setAnoPublicacion(rs.getInt("ano_publicacion"));
                libro.setISBN(rs.getString("isbn"));
                libro.setGeneroPrincipal(rs.getString("genero_principal"));
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
    public List<Libro> obtenerPorEditorial(String editorial) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM Libros WHERE editorial = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, editorial);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setAnoPublicacion(rs.getInt("ano_publicacion"));
                libro.setISBN(rs.getString("isbn"));
                libro.setGeneroPrincipal(rs.getString("genero_principal"));
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