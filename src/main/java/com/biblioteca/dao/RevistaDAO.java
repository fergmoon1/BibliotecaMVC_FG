package com.biblioteca.dao;

import com.biblioteca.model.Revista;

import java.util.List;

public interface RevistaDAO extends ElementoBibliotecaDAO {
    boolean crear(Revista revista);
    Revista leer(int id);
    boolean actualizar(Revista revista);
    boolean eliminar(int id);
    List<Revista> obtenerTodos();
}