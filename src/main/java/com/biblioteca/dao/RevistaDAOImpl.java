package com.biblioteca.dao;

import com.biblioteca.model.Revista;
import com.biblioteca.model.ElementoBiblioteca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta de RevistaDAO para interactuar con la tabla Revistas.
 */
public class RevistaDAOImpl implements RevistaDAO {

    private Connection connection;

    public RevistaDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean crear(ElementoBiblioteca elemento) {
        if (!(elemento instanceof Revista)) return false;
        Revista revista = (Revista) elemento;
        String sql = "INSERT INTO Revistas (titulo, autor, ano_publicacion, nombre_edicion, categoria) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, revista.getTitulo());
            stmt.setString(2, revista.getAutor());
            stmt.setInt(3, revista.getAnoPublicacion());
            stmt.setString(4, revista.getNombreEdicion());
            stmt.setString(5, revista.getCategoria());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    revista.setId(generatedKeys.getInt(1));
                }
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ElementoBiblioteca buscarPorId(int id) {
        String sql = "SELECT * FROM Revistas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Revista revista = new Revista();
                revista.setId(rs.getInt("id"));
                revista.setTitulo(rs.getString("titulo"));
                revista.setAutor(rs.getString("autor"));
                revista.setAnoPublicacion(rs.getInt("ano_publicacion"));
                revista.setNombreEdicion(rs.getString("nombre_edicion"));
                revista.setCategoria(rs.getString("categoria"));
                return revista;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ElementoBiblioteca> obtenerTodos() {
        List<ElementoBiblioteca> elementos = new ArrayList<>();
        String sql = "SELECT * FROM Revistas";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Revista revista = new Revista();
                revista.setId(rs.getInt("id"));
                revista.setTitulo(rs.getString("titulo"));
                revista.setAutor(rs.getString("autor"));
                revista.setAnoPublicacion(rs.getInt("ano_publicacion"));
                revista.setNombreEdicion(rs.getString("nombre_edicion"));
                revista.setCategoria(rs.getString("categoria"));
                elementos.add(revista);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elementos;
    }

    @Override
    public boolean actualizar(ElementoBiblioteca elemento) {
        if (!(elemento instanceof Revista)) return false;
        Revista revista = (Revista) elemento;
        String sql = "UPDATE Revistas SET titulo = ?, autor = ?, ano_publicacion = ?, nombre_edicion = ?, categoria = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, revista.getTitulo());
            stmt.setString(2, revista.getAutor());
            stmt.setInt(3, revista.getAnoPublicacion());
            stmt.setString(4, revista.getNombreEdicion());
            stmt.setString(5, revista.getCategoria());
            stmt.setInt(6, revista.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Revistas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Revista buscarPorNombreEdicion(String nombreEdicion) {
        String sql = "SELECT * FROM Revistas WHERE nombre_edicion = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombreEdicion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Revista revista = new Revista();
                revista.setId(rs.getInt("id"));
                revista.setTitulo(rs.getString("titulo"));
                revista.setAutor(rs.getString("autor"));
                revista.setAnoPublicacion(rs.getInt("ano_publicacion"));
                revista.setNombreEdicion(rs.getString("nombre_edicion"));
                revista.setCategoria(rs.getString("categoria"));
                return revista;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Revista> obtenerPorCategoria(String categoria) {
        List<Revista> revistas = new ArrayList<>();
        String sql = "SELECT * FROM Revistas WHERE categoria = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoria);
            ResultSet rs = stmt.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revistas;
    }
}