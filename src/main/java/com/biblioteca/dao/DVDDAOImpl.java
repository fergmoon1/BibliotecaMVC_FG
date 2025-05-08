package com.biblioteca.dao;

import com.biblioteca.model.DVD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DVDDAOImpl implements DVDDAO {

    private final Connection connection;

    public DVDDAOImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public void agregarDVD(DVD dvd) {
        String sqlElemento = "INSERT INTO elemento_biblioteca (id, titulo, autor, ano_publicacion, tipo) VALUES (?, ?, ?, ?, ?)";
        String sqlDVD = "INSERT INTO dvd (id, duracion, genero) VALUES (?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            // Insertar en elemento_biblioteca
            try (PreparedStatement psElemento = connection.prepareStatement(sqlElemento)) {
                psElemento.setInt(1, dvd.getId());
                psElemento.setString(2, dvd.getTitulo());
                psElemento.setString(3, dvd.getAutor());
                psElemento.setInt(4, dvd.getAnoPublicacion());
                psElemento.setString(5, "DVD");
                psElemento.executeUpdate();
            }

            // Insertar en dvd
            try (PreparedStatement psDVD = connection.prepareStatement(sqlDVD)) {
                psDVD.setInt(1, dvd.getId());
                psDVD.setInt(2, dvd.getDuracion());
                psDVD.setString(3, dvd.getGenero());
                psDVD.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarDVD(DVD dvd) {
        String sqlElemento = "UPDATE elemento_biblioteca SET titulo = ?, autor = ?, ano_publicacion = ? WHERE id = ?";
        String sqlDVD = "UPDATE dvd SET duracion = ?, genero = ? WHERE id = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement psElemento = connection.prepareStatement(sqlElemento)) {
                psElemento.setString(1, dvd.getTitulo());
                psElemento.setString(2, dvd.getAutor());
                psElemento.setInt(3, dvd.getAnoPublicacion());
                psElemento.setInt(4, dvd.getId());
                psElemento.executeUpdate();
            }

            try (PreparedStatement psDVD = connection.prepareStatement(sqlDVD)) {
                psDVD.setInt(1, dvd.getDuracion());
                psDVD.setString(2, dvd.getGenero());
                psDVD.setInt(3, dvd.getId());
                psDVD.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarDVD(int id) {
        String sqlDVD = "DELETE FROM dvd WHERE id = ?";
        String sqlElemento = "DELETE FROM elemento_biblioteca WHERE id = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement psDVD = connection.prepareStatement(sqlDVD)) {
                psDVD.setInt(1, id);
                psDVD.executeUpdate();
            }

            try (PreparedStatement psElemento = connection.prepareStatement(sqlElemento)) {
                psElemento.setInt(1, id);
                psElemento.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<DVD> obtenerTodosLosDVDs() {
        List<DVD> lista = new ArrayList<>();
        String sql = "SELECT e.id, e.titulo, e.autor, e.ano_publicacion, d.duracion, d.genero " +
                "FROM elemento_biblioteca e JOIN dvd d ON e.id = d.id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DVD dvd = new DVD();
                dvd.setId(rs.getInt("id"));
                dvd.setTitulo(rs.getString("titulo"));
                dvd.setAutor(rs.getString("autor"));
                dvd.setAnoPublicacion(rs.getInt("ano_publicacion"));
                dvd.setDuracion(rs.getInt("duracion"));
                dvd.setGenero(rs.getString("genero"));
                lista.add(dvd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public List<DVD> buscarPorGenero(String genero) {
        List<DVD> lista = new ArrayList<>();
        String sql = "SELECT e.id, e.titulo, e.autor, e.ano_publicacion, d.duracion, d.genero " +
                "FROM elemento_biblioteca e JOIN dvd d ON e.id = d.id WHERE d.genero LIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + genero + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DVD dvd = new DVD();
                dvd.setId(rs.getInt("id"));
                dvd.setTitulo(rs.getString("titulo"));
                dvd.setAutor(rs.getString("autor"));
                dvd.setAnoPublicacion(rs.getInt("ano_publicacion"));
                dvd.setDuracion(rs.getInt("duracion"));
                dvd.setGenero(rs.getString("genero"));
                lista.add(dvd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}

