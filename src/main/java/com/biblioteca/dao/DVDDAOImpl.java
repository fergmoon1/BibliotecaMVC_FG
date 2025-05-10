package com.biblioteca.dao;

import com.biblioteca.model.DVD;
import com.biblioteca.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DVDDAOImpl implements DVDDAO {

    @Override
    public void agregarDVD(DVD dvd) {
        String sql1 = "INSERT INTO ElementoBiblioteca (id, titulo, autor, ano_publicacion, tipo) VALUES (?, ?, ?, ?, ?)";
        String sql2 = "INSERT INTO DVD (id, duracion, genero) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt1 = conn.prepareStatement(sql1);
             PreparedStatement stmt2 = conn.prepareStatement(sql2)) {

            stmt1.setInt(1, dvd.getId());
            stmt1.setString(2, dvd.getTitulo());
            stmt1.setString(3, dvd.getAutor());
            stmt1.setInt(4, dvd.getAnioPublicacion());
            stmt1.setString(5, dvd.getTipo());
            stmt1.executeUpdate();

            stmt2.setInt(1, dvd.getId());
            stmt2.setInt(2, dvd.getDuracion());
            stmt2.setString(3, dvd.getGenero());
            stmt2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DVD> obtenerTodosDVDs() {
        List<DVD> dvds = new ArrayList<>();
        String sql = """
            SELECT eb.id, eb.titulo, eb.autor, eb.ano_publicacion, eb.tipo, d.duracion, d.genero
            FROM ElementoBiblioteca eb
            JOIN DVD d ON eb.id = d.id
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DVD dvd = new DVD(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("ano_publicacion"),
                        rs.getString("tipo"),
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
}
