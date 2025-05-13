package com.biblioteca.dao;

import com.biblioteca.model.ElementoBiblioteca;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Revista;
import com.biblioteca.model.DVD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaDAOImpl implements BibliotecaDAO {
    private Connection conexion;

    public BibliotecaDAOImpl(Connection conexion) {
        this.conexion = conexion;
    }

    // ---- CRUD Completo ----
    @Override
    public void agregarElemento(ElementoBiblioteca elemento) throws SQLException {
        String sqlElemento = "INSERT INTO elemento_biblioteca (titulo, autor, anio_publicacion, tipo) VALUES (?, ?, ?, ?)"; // id es AUTO_INCREMENT
        try (PreparedStatement stmt = conexion.prepareStatement(sqlElemento, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, elemento.getTitulo());
            stmt.setString(2, elemento.getAutor());
            stmt.setInt(3, elemento.getAnioPublicacion());
            stmt.setString(4, elemento.getTipo());
            stmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    elemento.setId(id); // Actualizar el ID en el objeto
                }
            }

            if (elemento instanceof Libro) {
                agregarLibro((Libro) elemento);
            } else if (elemento instanceof Revista) {
                agregarRevista((Revista) elemento);
            } else if (elemento instanceof DVD) {
                agregarDVD((DVD) elemento);
            }
        }
    }

    @Override
    public void actualizarElemento(ElementoBiblioteca elemento) throws SQLException {
        String sqlElemento = "UPDATE elemento_biblioteca SET titulo=?, autor=?, anio_publicacion=? WHERE id=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sqlElemento)) {
            stmt.setString(1, elemento.getTitulo());
            stmt.setString(2, elemento.getAutor());
            stmt.setInt(3, elemento.getAnioPublicacion());
            stmt.setInt(4, elemento.getId());
            stmt.executeUpdate();

            if (elemento instanceof Libro) {
                actualizarLibro((Libro) elemento);
            } else if (elemento instanceof Revista) {
                actualizarRevista((Revista) elemento);
            } else if (elemento instanceof DVD) {
                actualizarDVD((DVD) elemento);
            }
        }
    }

    @Override
    public void eliminarElemento(int id) throws SQLException {
        conexion.setAutoCommit(false);

        try {
            // Determinar el tipo de elemento
            String tipo = obtenerTipoElemento(id);
            if (tipo != null) {
                String sqlEspecifica = "DELETE FROM " + tipo.toLowerCase() + " WHERE id=?";
                try (PreparedStatement stmt = conexion.prepareStatement(sqlEspecifica)) {
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                }
            }

            // Eliminar de la tabla general
            String sqlGeneral = "DELETE FROM elemento_biblioteca WHERE id=?";
            try (PreparedStatement stmt = conexion.prepareStatement(sqlGeneral)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            conexion.commit();
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        } finally {
            conexion.setAutoCommit(true);
        }
    }

    @Override
    public ElementoBiblioteca buscarPorId(int id) throws SQLException {
        String sql = "SELECT e.*, l.isbn, l.numero_paginas, l.genero, l.editorial, " +
                "r.numero_edicion, r.categoria, d.duracion, d.genero AS dvd_genero " +
                "FROM elemento_biblioteca e " +
                "LEFT JOIN libro l ON e.id = l.id " +
                "LEFT JOIN revista r ON e.id = r.id " +
                "LEFT JOIN dvd d ON e.id = d.id " +
                "WHERE e.id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("tipo");
                    switch (tipo.toUpperCase()) {
                        case "LIBRO":
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
                        case "REVISTA":
                            return new Revista(
                                    rs.getInt("id"),
                                    rs.getString("titulo"),
                                    rs.getString("autor"),
                                    rs.getInt("anio_publicacion"),
                                    rs.getInt("numero_edicion"),
                                    rs.getString("categoria")
                            );
                        case "DVD":
                            return new DVD(
                                    rs.getInt("id"),
                                    rs.getString("titulo"),
                                    rs.getString("autor"),
                                    rs.getInt("anio_publicacion"),
                                    rs.getInt("duracion"),
                                    rs.getString("dvd_genero")
                            );
                        default:
                            return null;
                    }
                }
            }
        }
        return null;
    }

    // ---- Búsquedas específicas ----
    @Override
    public List<ElementoBiblioteca> buscarPorTitulo(String titulo) throws SQLException {
        String sql = "SELECT e.*, l.isbn, l.numero_paginas, l.genero, l.editorial, " +
                "r.numero_edicion, r.categoria, d.duracion, d.genero AS dvd_genero " +
                "FROM elemento_biblioteca e " +
                "LEFT JOIN libro l ON e.id = l.id " +
                "LEFT JOIN revista r ON e.id = r.id " +
                "LEFT JOIN dvd d ON e.id = d.id " +
                "WHERE e.titulo LIKE ?";
        List<ElementoBiblioteca> elementos = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + titulo + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    elementos.add(mapElemento(rs));
                }
            }
        }
        return elementos;
    }

    @Override
    public List<ElementoBiblioteca> buscarPorAutor(String autor) throws SQLException {
        String sql = "SELECT e.*, l.isbn, l.numero_paginas, l.genero, l.editorial, " +
                "r.numero_edicion, r.categoria, d.duracion, d.genero AS dvd_genero " +
                "FROM elemento_biblioteca e " +
                "LEFT JOIN libro l ON e.id = l.id " +
                "LEFT JOIN revista r ON e.id = r.id " +
                "LEFT JOIN dvd d ON e.id = d.id " +
                "WHERE e.autor LIKE ?";
        List<ElementoBiblioteca> elementos = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + autor + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    elementos.add(mapElemento(rs));
                }
            }
        }
        return elementos;
    }

    @Override
    public List<ElementoBiblioteca> buscarPorAnioPublicacion(int anio) throws SQLException {
        String sql = "SELECT e.*, l.isbn, l.numero_paginas, l.genero, l.editorial, " +
                "r.numero_edicion, r.categoria, d.duracion, d.genero AS dvd_genero " +
                "FROM elemento_biblioteca e " +
                "LEFT JOIN libro l ON e.id = l.id " +
                "LEFT JOIN revista r ON e.id = r.id " +
                "LEFT JOIN dvd d ON e.id = d.id " +
                "WHERE e.anio_publicacion = ?";
        List<ElementoBiblioteca> elementos = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, anio);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    elementos.add(mapElemento(rs));
                }
            }
        }
        return elementos;
    }

    @Override
    public List<Libro> buscarTodosLibros() throws SQLException {
        String sql = "SELECT e.*, l.isbn, l.numero_paginas, l.genero, l.editorial " +
                "FROM elemento_biblioteca e JOIN libro l ON e.id = l.id";
        List<Libro> libros = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                libros.add(new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("anio_publicacion"),
                        rs.getString("isbn"),
                        rs.getInt("numero_paginas"),
                        rs.getString("genero"),
                        rs.getString("editorial")
                ));
            }
        }
        return libros;
    }

    @Override
    public List<Revista> buscarTodasRevistas() throws SQLException {
        String sql = "SELECT e.*, r.numero_edicion, r.categoria " +
                "FROM elemento_biblioteca e JOIN revista r ON e.id = r.id";
        List<Revista> revistas = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                revistas.add(new Revista(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("anio_publicacion"),
                        rs.getInt("numero_edicion"),
                        rs.getString("categoria")
                ));
            }
        }
        return revistas;
    }

    @Override
    public List<DVD> buscarTodosDVDs() throws SQLException {
        String sql = "SELECT e.*, d.duracion, d.genero " +
                "FROM elemento_biblioteca e JOIN dvd d ON e.id = d.id";
        List<DVD> dvds = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dvds.add(new DVD(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("anio_publicacion"),
                        rs.getInt("duracion"),
                        rs.getString("genero")
                ));
            }
        }
        return dvds;
    }

    @Override
    public List<Libro> buscarLibrosPorGenero(String genero) throws SQLException {
        String sql = "SELECT e.*, l.isbn, l.numero_paginas, l.genero, l.editorial " +
                "FROM elemento_biblioteca e JOIN libro l ON e.id = l.id WHERE l.genero LIKE ?";
        List<Libro> libros = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + genero + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    libros.add(new Libro(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("anio_publicacion"),
                            rs.getString("isbn"),
                            rs.getInt("numero_paginas"),
                            rs.getString("genero"),
                            rs.getString("editorial")
                    ));
                }
            }
        }
        return libros;
    }

    @Override
    public List<DVD> buscarDVDsPorGenero(String genero) throws SQLException {
        String sql = "SELECT e.*, d.duracion, d.genero " +
                "FROM elemento_biblioteca e JOIN dvd d ON e.id = d.id WHERE d.genero LIKE ?";
        List<DVD> dvds = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + genero + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dvds.add(new DVD(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("anio_publicacion"),
                            rs.getInt("duracion"),
                            rs.getString("genero")
                    ));
                }
            }
        }
        return dvds;
    }

    @Override
    public List<Revista> buscarRevistasPorCategoria(String categoria) throws SQLException {
        String sql = "SELECT e.*, r.numero_edicion, r.categoria " +
                "FROM elemento_biblioteca e JOIN revista r ON e.id = r.id WHERE r.categoria LIKE ?";
        List<Revista> revistas = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + categoria + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    revistas.add(new Revista(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("anio_publicacion"),
                            rs.getInt("numero_edicion"),
                            rs.getString("categoria")
                    ));
                }
            }
        }
        return revistas;
    }

    // ---- Métodos auxiliares ----
    private String obtenerTipoElemento(int id) throws SQLException {
        String sql = "SELECT tipo FROM elemento_biblioteca WHERE id=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getString("tipo") : null;
            }
        }
    }

    private void agregarLibro(Libro libro) throws SQLException {
        String sql = "INSERT INTO libro (id, isbn, numero_paginas, genero, editorial) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, libro.getId());
            stmt.setString(2, libro.getIsbn());
            stmt.setInt(3, libro.getNumeroPaginas());
            stmt.setString(4, libro.getGenero());
            stmt.setString(5, libro.getEditorial());
            stmt.executeUpdate();
        }
    }

    private void agregarRevista(Revista revista) throws SQLException {
        String sql = "INSERT INTO revista (id, numero_edicion, categoria) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, revista.getId());
            stmt.setInt(2, revista.getNumeroEdicion());
            stmt.setString(3, revista.getCategoria());
            stmt.executeUpdate();
        }
    }

    private void agregarDVD(DVD dvd) throws SQLException {
        String sql = "INSERT INTO dvd (id, duracion, genero) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, dvd.getId());
            stmt.setInt(2, dvd.getDuracion());
            stmt.setString(3, dvd.getGenero());
            stmt.executeUpdate();
        }
    }

    private void actualizarLibro(Libro libro) throws SQLException {
        String sql = "UPDATE libro SET isbn=?, numero_paginas=?, genero=?, editorial=? WHERE id=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, libro.getIsbn());
            stmt.setInt(2, libro.getNumeroPaginas());
            stmt.setString(3, libro.getGenero());
            stmt.setString(4, libro.getEditorial());
            stmt.setInt(5, libro.getId());
            stmt.executeUpdate();
        }
    }

    private void actualizarRevista(Revista revista) throws SQLException {
        String sql = "UPDATE revista SET numero_edicion=?, categoria=? WHERE id=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, revista.getNumeroEdicion());
            stmt.setString(2, revista.getCategoria());
            stmt.setInt(3, revista.getId());
            stmt.executeUpdate();
        }
    }

    private void actualizarDVD(DVD dvd) throws SQLException {
        String sql = "UPDATE dvd SET duracion=?, genero=? WHERE id=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, dvd.getDuracion());
            stmt.setString(2, dvd.getGenero());
            stmt.setInt(3, dvd.getId());
            stmt.executeUpdate();
        }
    }

    private ElementoBiblioteca mapElemento(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        switch (tipo.toUpperCase()) {
            case "LIBRO":
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
            case "REVISTA":
                return new Revista(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("anio_publicacion"),
                        rs.getInt("numero_edicion"),
                        rs.getString("categoria")
                );
            case "DVD":
                return new DVD(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("anio_publicacion"),
                        rs.getInt("duracion"),
                        rs.getString("dvd_genero")
                );
            default:
                return null;
        }
    }
}