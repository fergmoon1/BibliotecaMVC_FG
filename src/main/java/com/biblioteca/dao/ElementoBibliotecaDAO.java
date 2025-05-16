package com.biblioteca.dao;

import com.biblioteca.model.ElementoBiblioteca;
import com.biblioteca.util.BibliotecaException;
import java.util.List;

public interface ElementoBibliotecaDAO {
    List<ElementoBiblioteca> obtenerTodos() throws BibliotecaException;
    void eliminar(int id) throws BibliotecaException;
    void agregar(ElementoBiblioteca elemento) throws BibliotecaException;
    void actualizar(ElementoBiblioteca elemento) throws BibliotecaException;
    ElementoBiblioteca buscarPorId(int id) throws BibliotecaException;
}