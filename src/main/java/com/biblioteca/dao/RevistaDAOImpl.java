package com.biblioteca.dao;

import com.biblioteca.model.Revista;
import com.biblioteca.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RevistaDAOImpl implements RevistaDAO {

    @Override
    public List<Revista> obtenerTodas() throws SQLException {
        List<Revista> revistas = new ArrayList<>();
        String sql = "SELECT * FROM revistas";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Revista revista = new Revista();
                revista.setId(rs.getInt("id"));
                revista.setTitulo(rs.getString("titulo"));
                revista.setAutor(rs.getString("autor"));
                revista.setAnoPublicacion(rs.getInt("ano_publicacion"));
                revista.setNombreEdicion(rs.getString("nombre_edicion"));
                revista.setCategoria(rs.getString("categoria"));
                revistas.add(revista);
            }
        }
        return revistas;
    }

    @Override
    public List<Revista> buscarPorCategoria(String categoria) throws SQLException {
        List<Revista> revistas = new ArrayList<>();
        String sql = "SELECT * FROM revistas WHERE categoria = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Revista revista = new Revista();
                    revista.setId(rs.getInt("id"));
                    revista.setTitulo(rs.getString("titulo"));
                    revista.setAutor(rs.getString("autor"));
                    revista.setAnoPublicacion(rs.getInt("ano_publicacion"));
                    revista.setNombreEdicion(rs.getString("nombre_edicion"));
                    revista.setCategoria(rs.getString("categoria"));
                    revistas.add(revista);
                }
            }
        }
        return revistas;
    }

    @Override
    public boolean agregarRevista(Revista revista) throws SQLException {
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
    }

    @Override
    public boolean actualizarRevista(Revista revista) throws SQLException {
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
    }

    @Override
    public boolean eliminarRevista(int id) throws SQLException {
        String sql = "DELETE FROM revistas WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}