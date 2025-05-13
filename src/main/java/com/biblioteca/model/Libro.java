package com.biblioteca.model;

import com.biblioteca.dao.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private int anio;
    private String isbn;
    private int numeroPaginas;
    private String genero;
    private String editorial;

    public Libro() {
    }

    public Libro(String titulo, String autor, int anio, int numeroPaginas, String isbn, String genero, String editorial) {
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.numeroPaginas = numeroPaginas;
        this.isbn = isbn;
        this.genero = genero;
        this.editorial = editorial;
    }

    public Libro(int id, String titulo, String autor, int anio, String isbn, int numeroPaginas, String genero, String editorial) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.genero = genero;
        this.editorial = editorial;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    // Métodos de base de datos
    public static List<Libro> obtenerTodos() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                libros.add(new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("anio"),
                        rs.getString("isbn"),
                        rs.getInt("numero_paginas"),
                        rs.getString("genero"),
                        rs.getString("editorial")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    public static Libro obtener(int id) {
        String sql = "SELECT * FROM libros WHERE id = ?";
        Libro libro = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    libro = new Libro(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("anio"),
                            rs.getString("isbn"),
                            rs.getInt("numero_paginas"),
                            rs.getString("genero"),
                            rs.getString("editorial")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libro;
    }

    public static List<Libro> buscarPorGenero(String genero) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE genero LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + genero + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    libros.add(new Libro(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("anio"),
                            rs.getString("isbn"),
                            rs.getInt("numero_paginas"),
                            rs.getString("genero"),
                            rs.getString("editorial")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    public boolean guardar() {
        String sql;
        if (this.id == 0) {
            sql = "INSERT INTO libros (titulo, autor, anio, isbn, numero_paginas, genero, editorial) VALUES (?, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE libros SET titulo = ?, autor = ?, anio = ?, isbn = ?, numero_paginas = ?, genero = ?, editorial = ? WHERE id = ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, this.titulo);
            stmt.setString(2, this.autor);
            stmt.setInt(3, this.anio);
            stmt.setString(4, this.isbn);
            stmt.setInt(5, this.numeroPaginas);
            stmt.setString(6, this.genero);
            stmt.setString(7, this.editorial);

            if (this.id != 0) {
                stmt.setInt(8, this.id);
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            if (this.id == 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.id = generatedKeys.getInt(1);
                    }
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean eliminar(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void cargarDatosEjemplo() {
        // Implementación para cargar datos de ejemplo
        // Puedes personalizar esto según tus necesidades
        List<Libro> librosEjemplo = List.of(
                new Libro("Cien años de soledad", "Gabriel García Márquez", 1967, 432, "978-0307474728", "Novela", "Editorial Sudamericana"),
                new Libro("1984", "George Orwell", 1949, 328, "978-0451524935", "Ciencia ficción", "Secker & Warburg"),
                new Libro("El Principito", "Antoine de Saint-Exupéry", 1943, 96, "978-0156012195", "Fábula", "Reynal & Hitchcock")
        );

        for (Libro libro : librosEjemplo) {
            libro.guardar();
        }
    }
}