package com.biblioteca.controller;

import com.biblioteca.dao.LibroDAO;
import com.biblioteca.dao.LibroDAOImpl;
import com.biblioteca.model.Libro;

import java.util.List;

public class LibroController {

    private final LibroDAO libroDAO;

    public LibroController() {
        this.libroDAO = new LibroDAOImpl();
    }

    public void agregarLibro(Libro libro) {
        libroDAO.agregarLibro(libro);
    }

    public void actualizarLibro(Libro libro) {
        libroDAO.actualizarLibro(libro);
    }

    public void eliminarLibro(int id) {
        libroDAO.eliminarLibro(id);
    }

    public List<Libro> obtenerTodosLosLibros() {
        return libroDAO.obtenerTodosLosLibros();
    }

    public List<Libro> buscarPorGenero(String genero) {
        return libroDAO.buscarPorGenero(genero);
    }
}
