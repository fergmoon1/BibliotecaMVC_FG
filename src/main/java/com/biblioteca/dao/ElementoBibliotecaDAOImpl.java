package com.biblioteca.dao;

import com.biblioteca.model.ElementoBiblioteca;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Revista;
import com.biblioteca.model.DVD;
import com.biblioteca.util.DatabaseConnection;
import com.biblioteca.util.BibliotecaException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ElementoBibliotecaDAOImpl implements ElementoBibliotecaDAO {
    @Override
    public void create(ElementoBiblioteca elemento) throws BibliotecaException {
        String sql = "INSERT INTO ElementoBiblioteca (id, titulo, autor, ano_publicacion, tipo) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();

        if (conn == null) {
            throw new BibliotecaException("No se pudo establecer conexión con la base de datos.");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, elemento.getId());
            pstmt.setString(2, elemento.getTitulo());
            pstmt.setString(3, elemento.getAutor());
            pstmt.setInt(4, elemento.getAnoPublicacion());
            pstmt.setString(5, elemento.getTipo());
            pstmt.executeUpdate();

            if (elemento instanceof Libro) {
                Libro libro = (Libro) elemento;
                sql = "INSERT INTO Libro (id_libro, isbn, numero_paginas, genero, editorial) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstmtLibro = conn.prepareStatement(sql)) {
                    pstmtLibro.setInt(1, libro.getId());
                    pstmtLibro.setString(2, libro.getIsbn());
                    pstmtLibro.setInt(3, libro.getNumeroPaginas());
                    pstmtLibro.setString(4, libro.getGenero());
                    pstmtLibro.setString(5, libro.getEditorial());
                    pstmtLibro.executeUpdate();
                }
            } else if (elemento instanceof Revista) {
                Revista revista = (Revista) elemento;
                sql = "INSERT INTO Revista (id_revista, numero_edicion, categoria) VALUES (?, ?, ?)";
                try (PreparedStatement pstmtRevista = conn.prepareStatement(sql)) {
                    pstmtRevista.setInt(1, revista.getId());
                    pstmtRevista.setInt(2, revista.getNumeroEdicion());
                    pstmtRevista.setString(3, revista.getCategoria());
                    pstmtRevista.executeUpdate();
                }
            } else if (elemento instanceof DVD) {
                DVD dvd = (DVD) elemento;
                sql = "INSERT INTO DVD (id_dvd, duracion, genero) VALUES (?, ?, ?)";
                try (PreparedStatement pstmtDVD = conn.prepareStatement(sql)) {
                    pstmtDVD.setInt(1, dvd.getId());
                    pstmtDVD.setInt(2, dvd.getDuracion());
                    pstmtDVD.setString(3, dvd.getGenero());
                    pstmtDVD.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new BibliotecaException("Error al crear elemento: " + e.getMessage(), e);
        }
    }

    @Override
    public ElementoBiblioteca read(int id) throws BibliotecaException {
        String sql = "SELECT * FROM ElementoBiblioteca WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        ElementoBiblioteca elemento = null;

        if (conn == null) {
            throw new BibliotecaException("No se pudo establecer conexión con la base de datos.");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String tipo = rs.getString("tipo");
                if ("Libro".equals(tipo)) {
                    sql = "SELECT * FROM Libro WHERE id_libro = ?";
                    try (PreparedStatement pstmtLibro = conn.prepareStatement(sql)) {
                        pstmtLibro.setInt(1, id);
                        ResultSet rsLibro = pstmtLibro.executeQuery();
                        if (rsLibro.next()) {
                            elemento = new Libro(
                                    rs.getInt("id"),
                                    rs.getString("titulo"),
                                    rs.getString("autor"),
                                    rs.getInt("ano_publicacion"),
                                    tipo,
                                    rsLibro.getString("isbn"),
                                    rsLibro.getInt("numero_paginas"),
                                    rsLibro.getString("genero"),
                                    rsLibro.getString("editorial")
                            );
                        }
                    }
                } else if ("Revista".equals(tipo)) {
                    sql = "SELECT * FROM Revista WHERE id_revista = ?";
                    try (PreparedStatement pstmtRevista = conn.prepareStatement(sql)) {
                        pstmtRevista.setInt(1, id);
                        ResultSet rsRevista = pstmtRevista.executeQuery();
                        if (rsRevista.next()) {
                            elemento = new Revista(
                                    rs.getInt("id"),
                                    rs.getString("titulo"),
                                    rs.getString("autor"),
                                    rs.getInt("ano_publicacion"),
                                    tipo,
                                    rsRevista.getInt("numero_edicion"),
                                    rsRevista.getString("categoria")
                            );
                        }
                    }
                } else if ("DVD".equals(tipo)) {
                    sql = "SELECT * FROM DVD WHERE id_dvd = ?";
                    try (PreparedStatement pstmtDVD = conn.prepareStatement(sql)) {
                        pstmtDVD.setInt(1, id);
                        ResultSet rsDVD = pstmtDVD.executeQuery();
                        if (rsDVD.next()) {
                            elemento = new DVD(
                                    rs.getInt("id"),
                                    rs.getString("titulo"),
                                    rs.getString("autor"),
                                    rs.getInt("ano_publicacion"),
                                    tipo,
                                    rsDVD.getInt("duracion"),
                                    rsDVD.getString("genero")
                            );
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new BibliotecaException("Error al leer elemento: " + e.getMessage(), e);
        }
        return elemento;
    }

    @Override
    public void update(ElementoBiblioteca elemento) throws BibliotecaException {
        String sql = "UPDATE ElementoBiblioteca SET titulo = ?, autor = ?, ano_publicacion = ?, tipo = ? WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();

        if (conn == null) {
            throw new BibliotecaException("No se pudo establecer conexión con la base de datos.");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, elemento.getTitulo());
            pstmt.setString(2, elemento.getAutor());
            pstmt.setInt(3, elemento.getAnoPublicacion());
            pstmt.setString(4, elemento.getTipo());
            pstmt.setInt(5, elemento.getId());
            pstmt.executeUpdate();

            if (elemento instanceof Libro) {
                Libro libro = (Libro) elemento;
                sql = "UPDATE Libro SET isbn = ?, numero_paginas = ?, genero = ?, editorial = ? WHERE id_libro = ?";
                try (PreparedStatement pstmtLibro = conn.prepareStatement(sql)) {
                    pstmtLibro.setString(1, libro.getIsbn());
                    pstmtLibro.setInt(2, libro.getNumeroPaginas());
                    pstmtLibro.setString(3, libro.getGenero());
                    pstmtLibro.setString(4, libro.getEditorial());
                    pstmtLibro.setInt(5, libro.getId());
                    pstmtLibro.executeUpdate();
                }
            } else if (elemento instanceof Revista) {
                Revista revista = (Revista) elemento;
                sql = "UPDATE Revista SET numero_edicion = ?, categoria = ? WHERE id_revista = ?";
                try (PreparedStatement pstmtRevista = conn.prepareStatement(sql)) {
                    pstmtRevista.setInt(1, revista.getNumeroEdicion());
                    pstmtRevista.setString(2, revista.getCategoria());
                    pstmtRevista.setInt(3, revista.getId());
                    pstmtRevista.executeUpdate();
                }
            } else if (elemento instanceof DVD) {
                DVD dvd = (DVD) elemento;
                sql = "UPDATE DVD SET duracion = ?, genero = ? WHERE id_dvd = ?";
                try (PreparedStatement pstmtDVD = conn.prepareStatement(sql)) {
                    pstmtDVD.setInt(1, dvd.getDuracion());
                    pstmtDVD.setString(2, dvd.getGenero());
                    pstmtDVD.setInt(3, dvd.getId());
                    pstmtDVD.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new BibliotecaException("Error al actualizar elemento: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) throws BibliotecaException {
        String sql = "DELETE FROM ElementoBiblioteca WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();

        if (conn == null) {
            throw new BibliotecaException("No se pudo establecer conexión con la base de datos.");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new BibliotecaException("No se encontró un elemento con ID: " + id);
            }
        } catch (SQLException e) {
            throw new BibliotecaException("Error al eliminar elemento: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ElementoBiblioteca> getAll() throws BibliotecaException {
        String sql = "SELECT * FROM ElementoBiblioteca";
        Connection conn = DatabaseConnection.getConnection();
        List<ElementoBiblioteca> elementos = new ArrayList<>();

        if (conn == null) {
            throw new BibliotecaException("No se pudo establecer conexión con la base de datos.");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                ElementoBiblioteca elemento = read(id);
                if (elemento != null) {
                    elementos.add(elemento);
                }
            }
        } catch (SQLException e) {
            throw new BibliotecaException("Error al obtener todos los elementos: " + e.getMessage(), e);
        }
        return elementos;
    }
}