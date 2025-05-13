package com.biblioteca.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DVD {
    private int id;
    private String titulo;
    private String director; // Atributo principal para el director
    private int anio;
    private int duracion;
    private String genero;
    private String autor;    // Atributo para mapear la columna 'autor' de la base de datos

    public DVD() {
    }

    public DVD(String titulo, String director, int anio, int duracion, String genero) {
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
        this.duracion = duracion;
        this.genero = genero;
        this.autor = director; // Por defecto, autor = director
    }

    public DVD(int id, String titulo, String director, int anio, int duracion, String genero) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
        this.duracion = duracion;
        this.genero = genero;
        this.autor = director; // Por defecto, autor = director
    }

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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
        this.autor = director; // Sincronizar autor con director
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
        this.director = autor; // Sincronizar director con autor
    }

    // Métodos estáticos para gestionar la base de datos
    public static List<DVD> obtenerTodos() {
        List<DVD> dvds = new ArrayList<>();
        String sql = "SELECT e.*, d.duracion, d.genero FROM elemento_biblioteca e JOIN dvd d ON e.id = d.id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DVD dvd = new DVD(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"), // Mapear 'autor' desde la base de datos
                        rs.getInt("anio_publicacion"),
                        rs.getInt("duracion"),
                        rs.getString("genero")
                );
                dvds.add(dvd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dvds;
    }

    public static DVD obtener(int id) {
        String sql = "SELECT e.*, d.duracion, d.genero FROM elemento_biblioteca e JOIN dvd d ON e.id = d.id WHERE e.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new DVD(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"), // Mapear 'autor' desde la base de datos
                        rs.getInt("anio_publicacion"),
                        rs.getInt("duracion"),
                        rs.getString("genero")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<DVD> buscarPorGenero(String genero) {
        List<DVD> dvds = new ArrayList<>();
        String sql = "SELECT e.*, d.duracion, d.genero FROM elemento_biblioteca e JOIN dvd d ON e.id = d.id WHERE d.genero LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + genero.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DVD dvd = new DVD(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"), // Mapear 'autor' desde la base de datos
                        rs.getInt("anio_publicacion"),
                        rs.getInt("duracion"),
                        rs.getString("genero")
                );
                dvds.add(dvd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dvds;
    }

    public boolean guardar() {
        String sqlElemento = "INSERT INTO elemento_biblioteca (titulo, autor, anio_publicacion, tipo) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE titulo=VALUES(titulo), autor=VALUES(autor), anio_publicacion=VALUES(anio_publicacion)";
        String sqlDVD = "INSERT INTO dvd (id, duracion, genero) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE duracion=VALUES(duracion), genero=VALUES(genero)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtElemento = conn.prepareStatement(sqlElemento, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement stmtDVD = conn.prepareStatement(sqlDVD)) {

                // Insertar o actualizar en elemento_biblioteca
                stmtElemento.setString(1, titulo);
                stmtElemento.setString(2, autor); // Usar el atributo autor
                stmtElemento.setInt(3, anio);
                stmtElemento.setString(4, "DVD");
                stmtElemento.executeUpdate();

                // Obtener o establecer ID
                if (id == 0) {
                    try (ResultSet generatedKeys = stmtElemento.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            this.id = generatedKeys.getInt(1);
                        }
                    }
                }

                // Insertar o actualizar en dvd
                stmtDVD.setInt(1, id);
                stmtDVD.setInt(2, duracion);
                stmtDVD.setString(3, genero);
                stmtDVD.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean eliminar(int id) {
        String sqlDVD = "DELETE FROM dvd WHERE id = ?";
        String sqlElemento = "DELETE FROM elemento_biblioteca WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtDVD = conn.prepareStatement(sqlDVD);
                 PreparedStatement stmtElemento = conn.prepareStatement(sqlElemento)) {

                stmtDVD.setInt(1, id);
                stmtDVD.executeUpdate();

                stmtElemento.setInt(1, id);
                stmtElemento.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void cargarDatosEjemplo() {
        new DVD("The Godfather", "Francis Ford Coppola", 1972, 175, "Drama").guardar();
        new DVD("Matrix", "Wachowski Brothers", 1999, 136, "Ciencia ficción").guardar();
        new DVD("Inception", "Christopher Nolan", 2010, 148, "Ciencia ficción").guardar();
        new DVD("El Pianista", "Roman Polanski", 2002, 150, "Drama").guardar();
        new DVD("Parásitos", "Bong Joon-ho", 2019, 132, "Drama").guardar();
    }
}