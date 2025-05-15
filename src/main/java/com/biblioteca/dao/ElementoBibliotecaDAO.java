package com.biblioteca.dao;

import com.biblioteca.model.ElementoBiblioteca;
import com.biblioteca.util.BibliotecaException;

import java.util.List;

public interface ElementoBibliotecaDAO {
    void create(ElementoBiblioteca elemento) throws BibliotecaException;
    ElementoBiblioteca read(int id) throws BibliotecaException;
    void update(ElementoBiblioteca elemento) throws BibliotecaException;
    void delete(int id) throws BibliotecaException;
    List<ElementoBiblioteca> getAll() throws BibliotecaException;
}