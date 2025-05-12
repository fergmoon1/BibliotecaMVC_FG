package com.biblioteca.controller;

import com.biblioteca.dao.LibroDAO;
import com.biblioteca.dao.LibroDAOImpl;
import com.biblioteca.model.Libro;
import java.util.List;

public class LibroController {
    private LibroDAO libroDAO;

    public LibroController() {
        this.libroDAO = new LibroDAOImpl(); // Inyección de dependencia básica
    }

    // Métodos CRUD
    public void agregarLibro(Libro libro) {
        libroDAO.agregarLibro(libro);
    }

    public void actualizarLibro(Libro libro) {
        libroDAO.actualizarLibro(libro);
    }

    public void eliminarLibro(int id) {
        libroDAO.eliminarLibro(id);
    }

    public Libro obtenerLibroPorId(int id) {
        return libroDAO.obtenerLibroPorId(id);
    }

    public List<Libro> obtenerTodosLosLibros() {
        return libroDAO.obtenerTodosLosLibros();
    }

    // Método para búsqueda por género (implementado en LibroDAOImpl)
    public List<Libro> buscarLibrosPorGenero(String genero) {
        return libroDAO.buscarPorGenero(genero);
    }
}