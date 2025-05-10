package com.biblioteca.dao;

import com.biblioteca.model.DVD;
import com.biblioteca.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DVDDAOImpl implements DVDDAO {
    @Override
    public List<DVD> obtenerTodosLosDVDs() throws SQLException {
        List<DVD> dvds = new ArrayList<>();
        String sql = "SELECT * FROM dvds";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
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
                dvds.add(dvd);
            }
        }
        return dvds;
    }

    @Override
    public List<DVD> buscarPorGenero(String genero) throws SQLException {
        List<DVD> dvds = new ArrayList<>();
        String sql = "SELECT * FROM dvds WHERE genero = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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
                    dvds.add(dvd);
                }
            }
        }
        return dvds;
    }

    @Override
    public boolean agregarDVD(DVD dvd) throws SQLException {
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

    @Override
    public boolean actualizarDVD(DVD dvd) throws SQLException {
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

    @Override
    public boolean eliminarDVD(int id) throws SQLException {
        String sql = "DELETE FROM dvds WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}