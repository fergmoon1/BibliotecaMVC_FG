package com.biblioteca.dao;

import com.biblioteca.model.DVD;
import com.biblioteca.model.ElementoBiblioteca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta de DVDDAO para interactuar con la tabla DVDs.
 */
public class DVDDAOImpl implements DVDDAO {

    private Connection connection;

    public DVDDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean crear(ElementoBiblioteca elemento) {
        if (!(elemento instanceof DVD)) return false;
        DVD dvd = (DVD) elemento;
        String sql = "INSERT INTO DVDs (titulo, autor, ano_publicacion, director, duracion_minutos, formato) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, dvd.getTitulo());
            stmt.setString(2, dvd.getAutor());
            stmt.setInt(3, dvd.getAnoPublicacion());
            stmt.setString(4, dvd.getDirector());
            stmt.setInt(5, dvd.getDuracionMinutos());
            stmt.setString(6, dvd.getFormato());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    dvd.setId(generatedKeys.getInt(1));
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
        String sql = "SELECT * FROM DVDs WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                DVD dvd = new DVD();
                dvd.setId(rs.getInt("id"));
                dvd.setTitulo(rs.getString("titulo"));
                dvd.setAutor(rs.getString("autor"));
                dvd.setAnoPublicacion(rs.getInt("ano_publicacion"));
                dvd.setDirector(rs.getString("director"));
                dvd.setDuracionMinutos(rs.getInt("duracion_minutos"));
                dvd.setFormato(rs.getString("formato"));
                return dvd;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ElementoBiblioteca> obtenerTodos() {
        List<ElementoBiblioteca> elementos = new ArrayList<>();
        String sql = "SELECT * FROM DVDs";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DVD dvd = new DVD();
                dvd.setId(rs.getInt("id"));
                dvd.setTitulo(rs.getString("titulo"));
                dvd.setAutor(rs.getString("autor"));
                dvd.setAnoPublicacion(rs.getInt("ano_publicacion"));
                dvd.setDirector(rs.getString("director"));
                dvd.setDuracionMinutos(rs.getInt("duracion_minutos"));
                dvd.setFormato(rs.getString("formato"));
                elementos.add(dvd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elementos;
    }

    @Override
    public boolean actualizar(ElementoBiblioteca elemento) {
        if (!(elemento instanceof DVD)) return false;
        DVD dvd = (DVD) elemento;
        String sql = "UPDATE DVDs SET titulo = ?, autor = ?, ano_publicacion = ?, director = ?, duracion_minutos = ?, formato = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dvd.getTitulo());
            stmt.setString(2, dvd.getAutor());
            stmt.setInt(3, dvd.getAnoPublicacion());
            stmt.setString(4, dvd.getDirector());
            stmt.setInt(5, dvd.getDuracionMinutos());
            stmt.setString(6, dvd.getFormato());
            stmt.setInt(7, dvd.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM DVDs WHERE id = ?";
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
    public DVD buscarPorDirector(String director) {
        String sql = "SELECT * FROM DVDs WHERE director = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, director);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                DVD dvd = new DVD();
                dvd.setId(rs.getInt("id"));
                dvd.setTitulo(rs.getString("titulo"));
                dvd.setAutor(rs.getString("autor"));
                dvd.setAnoPublicacion(rs.getInt("ano_publicacion"));
                dvd.setDirector(rs.getString("director"));
                dvd.setDuracionMinutos(rs.getInt("duracion_minutos"));
                dvd.setFormato(rs.getString("formato"));
                return dvd;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DVD> obtenerPorDuracionMaxima(int duracionMaxima) {
        List<DVD> dvds = new ArrayList<>();
        String sql = "SELECT * FROM DVDs WHERE duracion_minutos <= ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, duracionMaxima);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DVD dvd = new DVD();
                dvd.setId(rs.getInt("id"));
                dvd.setTitulo(rs.getString("titulo"));
                dvd.setAutor(rs.getString("autor"));
                dvd.setAnoPublicacion(rs.getInt("ano_publicacion"));
                dvd.setDirector(rs.getString("director"));
                dvd.setDuracionMinutos(rs.getInt("duracion_minutos"));
                dvd.setFormato(rs.getString("formato"));
                dvds.add(dvd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dvds;
    }

    @Override
    public List<DVD> obtenerPorFormato(String formato) {
        List<DVD> dvds = new ArrayList<>();
        String sql = "SELECT * FROM DVDs WHERE formato = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, formato);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DVD dvd = new DVD();
                dvd.setId(rs.getInt("id"));
                dvd.setTitulo(rs.getString("titulo"));
                dvd.setAutor(rs.getString("autor"));
                dvd.setAnoPublicacion(rs.getInt("ano_publicacion"));
                dvd.setDirector(rs.getString("director"));
                dvd.setDuracionMinutos(rs.getInt("duracion_minutos"));
                dvd.setFormato(rs.getString("formato"));
                dvds.add(dvd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dvds;
    }
}