package com.biblioteca.dao;

import com.biblioteca.model.ElementoBiblioteca;
import java.sql.SQLException;
import java.util.List;

public interface ElementoBibliotecaDAO {
    List<ElementoBiblioteca> obtenerTodos() throws SQLException;
    List<ElementoBiblioteca> buscarPorGenero(String genero) throws SQLException;
    boolean agregarElemento(ElementoBiblioteca elemento) throws SQLException;
    boolean actualizarElemento(ElementoBiblioteca elemento) throws SQLException;
    boolean eliminarElemento(int id) throws SQLException;
}
