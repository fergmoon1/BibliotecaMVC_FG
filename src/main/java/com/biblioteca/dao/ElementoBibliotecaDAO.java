package com.biblioteca.dao;

import com.biblioteca.model.ElementoBiblioteca;

import java.util.List;

public interface ElementoBibliotecaDAO {
    boolean crear(ElementoBiblioteca elemento);
    ElementoBiblioteca leer(int id);
    boolean actualizar(ElementoBiblioteca elemento);
    boolean eliminar(int id);
    List<ElementoBiblioteca> obtenerTodos();
}
