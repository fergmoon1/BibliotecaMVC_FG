package com.biblioteca.dao;

import com.biblioteca.model.ElementoBiblioteca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ElementoBibliotecaDAOImpl implements ElementoBibliotecaDAO {
    @Override
    public boolean crear(ElementoBiblioteca elemento) {
        String sql = "INSERT INTO ElementoBiblioteca (titulo, autor, ano_publicacion, tipo) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id"})) {
            stmt.setString(1, elemento.getTitulo());
            stmt.setString(2, elemento.getAutor());
            stmt.setInt(3, elemento.getAnoPublicacion());
            stmt.setString(4, elemento.getTipo());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    elemento.setId(rs.getInt(1));
                }
                rs.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ElementoBiblioteca leer(int id) {
        String sql = "SELECT * FROM ElementoBiblioteca WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ElementoBiblioteca elemento = new ElementoBiblioteca();
                elemento.setId(rs.getInt("id"));
                elemento.setTitulo(rs.getString("titulo"));
                elemento.setAutor(rs.getString("autor"));
                elemento.setAnoPublicacion(rs.getInt("ano_publicacion"));
                elemento.setTipo(rs.getString("tipo"));
                rs.close();
                return elemento;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean actualizar(ElementoBiblioteca elemento) {
        String sql = "UPDATE ElementoBiblioteca SET titulo = ?, autor = ?, ano_publicacion = ?, tipo = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, elemento.getTitulo());
            stmt.setString(2, elemento.getAutor());
            stmt.setInt(3, elemento.getAnoPublicacion());
            stmt.setString(4, elemento.getTipo());
            stmt.setInt(5, elemento.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM ElementoBiblioteca WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<ElementoBiblioteca> obtenerTodos() {
        List<ElementoBiblioteca> elementos = new ArrayList<>();
        String sql = "SELECT * FROM ElementoBiblioteca";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ElementoBiblioteca elemento = new ElementoBiblioteca();
                elemento.setId(rs.getInt("id"));
                elemento.setTitulo(rs.getString("titulo"));
                elemento.setAutor(rs.getString("autor"));
                elemento.setAnoPublicacion(rs.getInt("ano_publicacion"));
                elemento.setTipo(rs.getString("tipo"));
                elementos.add(elemento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elementos;
    }
}
