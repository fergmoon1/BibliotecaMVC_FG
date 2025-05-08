package com.biblioteca.dao;

import com.biblioteca.model.Libro;

import java.util.List;

public interface LibroDAO extends ElementoBibliotecaDAO {
    boolean crear(Libro libro);
    Libro leer(int id);
    boolean actualizar(Libro libro);
    boolean eliminar(int id);
    List<Libro> obtenerTodos();
}