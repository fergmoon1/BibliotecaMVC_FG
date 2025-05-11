package com.biblioteca.dao;

import com.biblioteca.model.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    private final Connection connection;

    public LibroDAOImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public void agregarLibro(Libro libro) {
        String sql1 = "INSERT INTO ElementoBiblioteca (id, titulo, autor, anio_publicacion, tipo) VALUES (?, ?, ?, ?, ?)";
        String sql2 = "INSERT INTO Libro (id, isbn, numero_paginas, genero, editorial) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt1 = connection.prepareStatement(sql1);
             PreparedStatement stmt2 = connection.prepareStatement(sql2)) {

            stmt1.setInt(1, libro.getId());
            stmt1.setString(2, libro.getTitulo());
            stmt1.setString(3, libro.getAutor());
            stmt1.setInt(4, libro.getAnioPublicacion());
            stmt1.setString(5, libro.getTipo());
            stmt1.executeUpdate();

            stmt2.setInt(1, libro.getId());
            stmt2.setString(2, libro.getIsbn());
            stmt2.setInt(3, libro.getNumeroPaginas());
            stmt2.setString(4, libro.getGenero());
            stmt2.setString(5, libro.getEditorial());
            stmt2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarLibro(Libro libro) {
        String sql1 = "UPDATE ElementoBiblioteca SET titulo=?, autor=?, anio_publicacion=? WHERE id=?";
        String sql2 = "UPDATE Libro SET isbn=?, numero_paginas=?, genero=?, editorial=? WHERE id=?";

        try (PreparedStatement stmt1 = connection.prepareStatement(sql1);
             PreparedStatement stmt2 = connection.prepareStatement(sql2)) {

            stmt1.setString(1, libro.getTitulo());
            stmt1.setString(2, libro.getAutor());
            stmt1.setInt(3, libro.getAnioPublicacion());
            stmt1.setInt(4, libro.getId());
            stmt1.executeUpdate();

            stmt2.setString(1, libro.getIsbn());
            stmt2.setInt(2, libro.getNumeroPaginas());
            stmt2.setString(3, libro.getGenero());
            stmt2.setString(4, libro.getEditorial());
            stmt2.setInt(5, libro.getId());
            stmt2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarLibro(int id) {
        String sql2 = "DELETE FROM Libro WHERE id=?";
        String sql1 = "DELETE FROM ElementoBiblioteca WHERE id=?";

        try (PreparedStatement stmt2 = connection.prepareStatement(sql2);
             PreparedStatement stmt1 = connection.prepareStatement(sql1)) {

            stmt2.setInt(1, id);
            stmt2.executeUpdate();

            stmt1.setInt(1, id);
            stmt1.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Libro obtenerLibroPorId(int id) {
        String sql = "SELECT eb.id, eb.titulo, eb.autor, eb.anio_publicacion, " +
                "l.isbn, l.numero_paginas, l.genero, l.editorial " +
                "FROM ElementoBiblioteca eb " +
                "JOIN Libro l ON eb.id = l.id " +
                "WHERE eb.id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("anio_publicacion"),
                        rs.getString("isbn"),
                        rs.getInt("numero_paginas"),
                        rs.getString("genero"),
                        rs.getString("editorial")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Libro> obtenerTodosLosLibros() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT eb.id, eb.titulo, eb.autor, eb.anio_publicacion, " +
                "l.isbn, l.numero_paginas, l.genero, l.editorial " +
                "FROM ElementoBiblioteca eb " +
                "JOIN Libro l ON eb.id = l.id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("anio_publicacion"),
                        rs.getString("isbn"),
                        rs.getInt("numero_paginas"),
                        rs.getString("genero"),
                        rs.getString("editorial")
                );
                lista.add(libro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
