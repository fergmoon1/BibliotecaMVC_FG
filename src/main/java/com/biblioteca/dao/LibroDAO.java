package com.biblioteca.dao;

import com.biblioteca.model.Libro;
import java.sql.SQLException;
import java.util.List;

public interface LibroDAO {
    List<Libro> obtenerTodos() throws SQLException;
    List<Libro> buscarPorGenero(String genero) throws SQLException;
    boolean agregarLibro(Libro libro) throws SQLException;
    boolean actualizarLibro(Libro libro) throws SQLException;
    boolean eliminarLibro(int id) throws SQLException;
}