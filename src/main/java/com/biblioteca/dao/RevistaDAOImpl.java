package com.biblioteca.dao;

import com.biblioteca.model.Revista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RevistaDAOImpl implements RevistaDAO {

    private final Connection connection;

    public RevistaDAOImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public void agregarRevista(Revista revista) {
        String sqlElemento = "INSERT INTO elemento_biblioteca (id, titulo, autor, ano_publicacion, tipo) VALUES (?, ?, ?, ?, ?)";
        String sqlRevista = "INSERT INTO revista (id, numero_edicion, categoria) VALUES (?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            // Insertar en elemento_biblioteca
            try (PreparedStatement psElemento = connection.prepareStatement(sqlElemento)) {
                psElemento.setInt(1, revista.getId());
                psElemento.setString(2, revista.getTitulo());
                psElemento.setString(3, revista.getAutor());
                psElemento.setInt(4, revista.getAnoPublicacion());
                psElemento.setString(5, "Revista");
                psElemento.executeUpdate();
            }

            // Insertar en revista
            try (PreparedStatement psRevista = connection.prepareStatement(sqlRevista)) {
                psRevista.setInt(1, revista.getId());
                psRevista.setInt(2, revista.getNumeroEdicion());
                psRevista.setString(3, revista.getCategoria());
                psRevista.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            rollback(e);
        }
    }

    @Override
    public void actualizarRevista(Revista revista) {
        String sqlElemento = "UPDATE elemento_biblioteca SET titulo = ?, autor = ?, ano_publicacion = ? WHERE id = ?";
        String sqlRevista = "UPDATE revista SET numero_edicion = ?, categoria = ? WHERE id = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement psElemento = connection.prepareStatement(sqlElemento)) {
                psElemento.setString(1, revista.getTitulo());
                psElemento.setString(2, revista.getAutor());
                psElemento.setInt(3, revista.getAnoPublicacion());
                psElemento.setInt(4, revista.getId());
                psElemento.executeUpdate();
            }

            try (PreparedStatement psRevista = connection.prepareStatement(sqlRevista)) {
                psRevista.setInt(1, revista.getNumeroEdicion());
                psRevista.setString(2, revista.getCategoria());
                psRevista.setInt(3, revista.getId());
                psRevista.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            rollback(e);
        }
    }

    @Override
    public void eliminarRevista(int id) {
        String sqlRevista = "DELETE FROM revista WHERE id = ?";
        String sqlElemento = "DELETE FROM elemento_biblioteca WHERE id = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement psRevista = connection.prepareStatement(sqlRevista)) {
                psRevista.setInt(1, id);
                psRevista.executeUpdate();
            }

            try (PreparedStatement psElemento = connection.prepareStatement(sqlElemento)) {
                psElemento.setInt(1, id);
                psElemento.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            rollback(e);
        }
    }

    @Override
    public List<Revista> obtenerTodasLasRevistas() {
        List<Revista> lista = new ArrayList<>();
        String sql = "SELECT e.id, e.titulo, e.autor, e.ano_publicacion, r.numero_edicion, r.categoria " +
                "FROM elemento_biblioteca e JOIN revista r ON e.id = r.id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Revista revista = new Revista();
                revista.setId(rs.getInt("id"));
                revista.setTitulo(rs.getString("titulo"));
                revista.setAutor(rs.getString("autor"));
                revista.setAnoPublicacion(rs.getInt("ano_publicacion"));
                revista.setNumeroEdicion(rs.getInt("numero_edicion"));
                revista.setCategoria(rs.getString("categoria"));
                lista.add(revista);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public List<Revista> buscarPorCategoria(String categoria) {
        List<Revista> lista = new ArrayList<>();
        String sql = "SELECT e.id, e.titulo, e.autor, e.ano_publicacion, r.numero_edicion, r.categoria " +
                "FROM elemento_biblioteca e JOIN revista r ON e.id = r.id WHERE r.categoria LIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + categoria + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Revista revista = new Revista();
                revista.setId(rs.getInt("id"));
                revista.setTitulo(rs.getString("titulo"));
                revista.setAutor(rs.getString("autor"));
                revista.setAnoPublicacion(rs.getInt("ano_publicacion"));
                revista.setNumeroEdicion(rs.getInt("numero_edicion"));
                revista.setCategoria(rs.getString("categoria"));
                lista.add(revista);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    private void rollback(SQLException e) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
    }
}

