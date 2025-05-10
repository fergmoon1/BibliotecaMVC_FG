package com.biblioteca.dao;

import com.biblioteca.model.DVD;
import java.util.List;
import java.sql.SQLException;

public interface DVDDAO {
    List<DVD> obtenerTodosLosDVDs() throws SQLException;
    List<DVD> buscarPorGenero(String genero) throws SQLException;
    boolean agregarDVD(DVD dvd) throws SQLException;
    boolean actualizarDVD(DVD dvd) throws SQLException;
    boolean eliminarDVD(int id) throws SQLException;
}