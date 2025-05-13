package com.biblioteca.dao;

import com.biblioteca.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class BibliotecaDAO {
    private static DataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/biblioteca");
        config.setUsername("tu_usuario");
        config.setPassword("tu_contraseña");
        config.setMaximumPoolSize(10);
        dataSource = new HikariDataSource(config);
    }

    public List<ElementoBiblioteca> cargarTodosElementos() throws SQLException {
        List<ElementoBiblioteca> elementos = new ArrayList<>();
        elementos.addAll(cargarDVDs());
        elementos.addAll(cargarLibros());
        elementos.addAll(cargarRevistas());
        return elementos;
    }

    public void guardarElemento(ElementoBiblioteca elemento) throws SQLException {
        if (elemento instanceof DVD) {
            guardarDVD((DVD) elemento);
        } else if (elemento instanceof Libro) {
            guardarLibro((Libro) elemento);
        } else if (elemento instanceof Revista) {
            guardarRevista((Revista) elemento);
        }
    }

    // Métodos específicos para DVD
    private void guardarDVD(DVD dvd) throws SQLException {
        String sql = "INSERT INTO dvds (id, titulo, autor, ano_publicacion, duracion, genero) VALUES (?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE titulo=?, autor=?, ano_publicacion=?, duracion=?, genero=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dvd.getId());
            stmt.setString(2, dvd.getTitulo());
            stmt.setString(3, dvd.getAutor());
            stmt.setInt(4, dvd.getAnoPublicacion());
            stmt.setInt(5, dvd.getDuracion());
            stmt.setString(6, dvd.getGenero());
            stmt.setString(7, dvd.getTitulo());
            stmt.setString(8, dvd.getAutor());
            stmt.setInt(9, dvd.getAnoPublicacion());
            stmt.setInt(10, dvd.getDuracion());
            stmt.setString(11, dvd.getGenero());

            stmt.executeUpdate();
        }
    }

    public List<DVD> cargarDVDs() throws SQLException {
        List<DVD> dvds = new ArrayList<>();
        String sql = "SELECT * FROM dvds";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DVD dvd = new DVD(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("ano_publicacion"),
                        rs.getInt("duracion"),
                        rs.getString("genero")
                );
                dvds.add(dvd);
            }
        }
        return dvds;
    }

    // Métodos específicos para Libro
    private void guardarLibro(Libro libro) throws SQLException {
        String sql = "INSERT INTO libros (id, titulo, autor, ano_publicacion, isbn, paginas, genero, editorial) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "
                + "titulo=?, autor=?, ano_publicacion=?, isbn=?, paginas=?, genero=?, editorial=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Parámetros para INSERT
            stmt.setInt(1, libro.getId());
            stmt.setString(2, libro.getTitulo());
            stmt.setString(3, libro.getAutor());
            stmt.setInt(4, libro.getAnoPublicacion());
            stmt.setString(5, libro.getIsbn());
            stmt.setInt(6, libro.getNumeroPaginas());
            stmt.setString(7, libro.getGenero());
            stmt.setString(8, libro.getEditorial());

            // Parámetros para UPDATE
            stmt.setString(9, libro.getTitulo());
            stmt.setString(10, libro.getAutor());
            stmt.setInt(11, libro.getAnoPublicacion());
            stmt.setString(12, libro.getIsbn());
            stmt.setInt(13, libro.getNumeroPaginas());
            stmt.setString(14, libro.getGenero());
            stmt.setString(15, libro.getEditorial());

            stmt.executeUpdate();
        }
    }

    public List<Libro> cargarLibros() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("ano_publicacion"),
                        rs.getString("isbn"),
                        rs.getInt("paginas"),
                        rs.getString("genero"),
                        rs.getString("editorial")
                );
                libros.add(libro);
            }
        }
        return libros;
    }

    // Métodos específicos para Revista
    private void guardarRevista(Revista revista) throws SQLException {
        String sql = "INSERT INTO revistas (id, titulo, ano_publicacion, numero_edicion, categoria) "
                + "VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "
                + "titulo=?, ano_publicacion=?, numero_edicion=?, categoria=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, revista.getId());
            stmt.setString(2, revista.getTitulo());
            stmt.setInt(3, revista.getAnoPublicacion());
            stmt.setInt(4, revista.getNumeroEdicion());
            stmt.setString(5, revista.getCategoria());
            stmt.setString(6, revista.getTitulo());
            stmt.setInt(7, revista.getAnoPublicacion());
            stmt.setInt(8, revista.getNumeroEdicion());
            stmt.setString(9, revista.getCategoria());

            stmt.executeUpdate();
        }
    }

    public List<Revista> cargarRevistas() throws SQLException {
        List<Revista> revistas = new ArrayList<>();
        String sql = "SELECT * FROM revistas";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Revista revista = new Revista(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getInt("ano_publicacion"),
                        rs.getInt("numero_edicion"),
                        rs.getString("categoria")
                );
                revistas.add(revista);
            }
        }
        return revistas;
    }

    // Métodos para eliminar elementos
    public void eliminarElemento(int id, String tipo) throws SQLException {
        String tabla = "";
        switch (tipo.toLowerCase()) {
            case "dvd": tabla = "dvds"; break;
            case "libro": tabla = "libros"; break;
            case "revista": tabla = "revistas"; break;
            default: throw new IllegalArgumentException("Tipo no válido");
        }

        String sql = "DELETE FROM " + tabla + " WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}