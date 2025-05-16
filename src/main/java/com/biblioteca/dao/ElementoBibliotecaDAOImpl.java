package com.biblioteca.dao;

import com.biblioteca.model.*;
import com.biblioteca.util.BibliotecaException;
import com.biblioteca.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ElementoBibliotecaDAOImpl implements ElementoBibliotecaDAO {

    @Override
    public void agregar(ElementoBiblioteca elemento) throws BibliotecaException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            // 1. Insertar en ElementoBiblioteca
            String sqlElemento = "INSERT INTO ElementoBiblioteca (tipo, titulo, autor, ano_publicacion) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmtElemento = conn.prepareStatement(sqlElemento, Statement.RETURN_GENERATED_KEYS);

            pstmtElemento.setString(1, elemento.getTipo());
            pstmtElemento.setString(2, elemento.getTitulo());
            pstmtElemento.setString(3, elemento.getAutor());
            pstmtElemento.setInt(4, elemento.getAnoPublicacion());
            pstmtElemento.executeUpdate();

            // Obtener el ID generado
            int id;
            try (ResultSet generatedKeys = pstmtElemento.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                    elemento.setId(id);
                } else {
                    throw new BibliotecaException("No se pudo obtener el ID generado");
                }
            }

            // 2. Insertar en la tabla específica
            if (elemento instanceof Libro) {
                Libro libro = (Libro) elemento;
                String sqlLibro = "INSERT INTO Libro (id_libro, isbn, numero_paginas, genero, editorial) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmtLibro = conn.prepareStatement(sqlLibro);
                pstmtLibro.setInt(1, id);
                pstmtLibro.setString(2, libro.getIsbn());
                pstmtLibro.setInt(3, libro.getNumeroPaginas());
                pstmtLibro.setString(4, libro.getGenero());
                pstmtLibro.setString(5, libro.getEditorial());
                pstmtLibro.executeUpdate();
            } else if (elemento instanceof Revista) {
                Revista revista = (Revista) elemento;
                String sqlRevista = "INSERT INTO Revista (id_revista, numero_edicion, categoria) VALUES (?, ?, ?)";
                PreparedStatement pstmtRevista = conn.prepareStatement(sqlRevista);
                pstmtRevista.setInt(1, id);
                pstmtRevista.setInt(2, revista.getNumeroEdicion());
                pstmtRevista.setString(3, revista.getCategoria());
                pstmtRevista.executeUpdate();
            } else if (elemento instanceof DVD) {
                DVD dvd = (DVD) elemento;
                String sqlDVD = "INSERT INTO DVD (id_dvd, duracion, genero) VALUES (?, ?, ?)";
                PreparedStatement pstmtDVD = conn.prepareStatement(sqlDVD);
                pstmtDVD.setInt(1, id);
                pstmtDVD.setInt(2, dvd.getDuracion());
                pstmtDVD.setString(3, dvd.getGenero());
                pstmtDVD.executeUpdate();
            }

            conn.commit(); // Confirmar transacción
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Revertir en caso de error
                } catch (SQLException ex) {
                    throw new BibliotecaException("Error al revertir transacción: " + ex.getMessage());
                }
            }
            throw new BibliotecaException("Error al agregar elemento: " + e.getMessage());
        } finally {
            if (conn != null) {

                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    // Logear error pero no lanzar excepción
                }
            }
        }
    }

    @Override
    public ElementoBiblioteca buscarPorId(int id) throws BibliotecaException {
        String sql = "SELECT e.*, l.isbn, l.numero_paginas, l.genero, l.editorial, " +
                "r.numero_edicion, r.categoria, d.duracion, d.genero " +
                "FROM ElementoBiblioteca e " +
                "LEFT JOIN Libro l ON e.id = l.id_libro " +
                "LEFT JOIN Revista r ON e.id = r.id_revista " +
                "LEFT JOIN DVD d ON e.id = d.id_dvd " +
                "WHERE e.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearElemento(rs);
                }
            }
        } catch (SQLException e) {
            throw new BibliotecaException("Error al buscar elemento por ID: " + e.getMessage());
        }
        throw new BibliotecaException("No se encontró elemento con ID: " + id);
    }

    @Override
    public List<ElementoBiblioteca> obtenerTodos() throws BibliotecaException {
        String sql = "SELECT e.*, l.isbn, l.numero_paginas, l.genero, l.editorial, " +
                "r.numero_edicion, r.categoria, d.duracion, d.genero " +
                "FROM ElementoBiblioteca e " +
                "LEFT JOIN Libro l ON e.id = l.id_libro " +
                "LEFT JOIN Revista r ON e.id = r.id_revista " +
                "LEFT JOIN DVD d ON e.id = d.id_dvd";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            List<ElementoBiblioteca> elementos = new ArrayList<>();
            while (rs.next()) {
                elementos.add(mapearElemento(rs));
            }
            return elementos;
        } catch (SQLException e) {
            throw new BibliotecaException("Error al obtener todos los elementos: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(ElementoBiblioteca elemento) throws BibliotecaException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            // 1. Actualizar ElementoBiblioteca
            String sqlElemento = "UPDATE ElementoBiblioteca SET tipo = ?, titulo = ?, autor = ?, ano_publicacion = ? WHERE id = ?";
            PreparedStatement pstmtElemento = conn.prepareStatement(sqlElemento);

            pstmtElemento.setString(1, elemento.getTipo());
            pstmtElemento.setString(2, elemento.getTitulo());
            pstmtElemento.setString(3, elemento.getAutor());
            pstmtElemento.setInt(4, elemento.getAnoPublicacion());
            pstmtElemento.setInt(5, elemento.getId());
            pstmtElemento.executeUpdate();

            // 2. Actualizar la tabla específica
            if (elemento instanceof Libro) {
                Libro libro = (Libro) elemento;
                String sqlLibro = "UPDATE Libro SET isbn = ?, numero_paginas = ?, genero = ?, editorial = ? WHERE id_libro = ?";
                PreparedStatement pstmtLibro = conn.prepareStatement(sqlLibro);
                pstmtLibro.setString(1, libro.getIsbn());
                pstmtLibro.setInt(2, libro.getNumeroPaginas());
                pstmtLibro.setString(3, libro.getGenero());
                pstmtLibro.setString(4, libro.getEditorial());
                pstmtLibro.setInt(5, elemento.getId());
                pstmtLibro.executeUpdate();
            } else if (elemento instanceof Revista) {
                Revista revista = (Revista) elemento;
                String sqlRevista = "UPDATE Revista SET numero_edicion = ?, categoria = ? WHERE id_revista = ?";
                PreparedStatement pstmtRevista = conn.prepareStatement(sqlRevista);
                pstmtRevista.setInt(1, revista.getNumeroEdicion());
                pstmtRevista.setString(2, revista.getCategoria());
                pstmtRevista.setInt(3, elemento.getId());
                pstmtRevista.executeUpdate();
            } else if (elemento instanceof DVD) {
                DVD dvd = (DVD) elemento;
                String sqlDVD = "UPDATE DVD SET duracion = ?, genero = ? WHERE id_dvd = ?";
                PreparedStatement pstmtDVD = conn.prepareStatement(sqlDVD);
                pstmtDVD.setInt(1, dvd.getDuracion());
                pstmtDVD.setString(2, dvd.getGenero());
                pstmtDVD.setInt(3, elemento.getId());
                pstmtDVD.executeUpdate();
            }

            conn.commit(); // Confirmar transacción
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Revertir en caso de error
                } catch (SQLException ex) {
                    throw new BibliotecaException("Error al revertir transacción: " + ex.getMessage());
                }
            }
            throw new BibliotecaException("Error al actualizar elemento: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    // Logear error pero no lanzar excepción
                }
            }
        }
    }

    @Override
    public void eliminar(int id) throws BibliotecaException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            // 1. Primero eliminar de la tabla específica (por las FOREIGN KEY con ON DELETE CASCADE)
            String sqlTipo = "SELECT tipo FROM ElementoBiblioteca WHERE id = ?";
            try (PreparedStatement pstmtTipo = conn.prepareStatement(sqlTipo)) {
                pstmtTipo.setInt(1, id);
                try (ResultSet rs = pstmtTipo.executeQuery()) {
                    if (rs.next()) {
                        String tipo = rs.getString("tipo");
                        String sqlEspecifico = "";
                        switch (tipo) {
                            case "Libro":
                                sqlEspecifico = "DELETE FROM Libro WHERE id_libro = ?";
                                break;
                            case "Revista":
                                sqlEspecifico = "DELETE FROM Revista WHERE id_revista = ?";
                                break;
                            case "DVD":
                                sqlEspecifico = "DELETE FROM DVD WHERE id_dvd = ?";
                                break;
                        }
                        try (PreparedStatement pstmtEspecifico = conn.prepareStatement(sqlEspecifico)) {
                            pstmtEspecifico.setInt(1, id);
                            pstmtEspecifico.executeUpdate();
                        }
                    }
                }
            }

            // 2. Luego eliminar de ElementoBiblioteca
            String sqlElemento = "DELETE FROM ElementoBiblioteca WHERE id = ?";
            try (PreparedStatement pstmtElemento = conn.prepareStatement(sqlElemento)) {
                pstmtElemento.setInt(1, id);
                pstmtElemento.executeUpdate();
            }

            conn.commit(); // Confirmar transacción
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Revertir en caso de error
                } catch (SQLException ex) {
                    throw new BibliotecaException("Error al revertir transacción: " + ex.getMessage());
                }
            }
            throw new BibliotecaException("Error al eliminar elemento: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    // Logear error pero no lanzar excepción
                }
            }
        }
    }

    private ElementoBiblioteca mapearElemento(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        String titulo = rs.getString("titulo");
        String autor = rs.getString("autor");
        int anoPublicacion = rs.getInt("ano_publicacion");

        switch (tipo) {
            case "Libro":
                return new Libro(
                        titulo,
                        autor,
                        anoPublicacion,
                        rs.getString("isbn"),
                        rs.getInt("numero_paginas"),
                        rs.getString("genero"),
                        rs.getString("editorial")
                );
            case "Revista":
                return new Revista(
                        titulo,
                        autor,
                        anoPublicacion,
                        rs.getInt("numero_edicion"),
                        rs.getString("categoria")
                );
            case "DVD":
                return new DVD(
                        titulo,
                        autor,
                        anoPublicacion,
                        rs.getInt("duracion"),
                        rs.getString("genero")
                );
            default:
                throw new SQLException("Tipo de elemento desconocido: " + tipo);
        }
    }
}