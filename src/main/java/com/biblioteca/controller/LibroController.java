package com.biblioteca.controller;

import com.biblioteca.model.Libro;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibroController {

    public LibroController() {
        inicializarDatosDemo();
    }

    private void inicializarDatosDemo() {
        // Verificar si la base de datos está vacía antes de cargar datos de ejemplo
        if (Libro.obtenerTodos().isEmpty()) {
            Libro.cargarDatosEjemplo();
        }
    }

    // CRUD Operations
    public void agregarLibro(Libro libro) {
        // El id se asignará automáticamente al guardar en la base de datos
        libro.guardar();
    }

    public List<Libro> obtenerTodosLosLibros() {
        return Libro.obtenerTodos();
    }

    public Libro buscarLibroPorId(int id) {
        return Libro.obtener(id);
    }

    public void actualizarLibro(Libro libroActualizado) {
        Libro libroExistente = buscarLibroPorId(libroActualizado.getId());
        if (libroExistente != null) {
            libroExistente.setTitulo(libroActualizado.getTitulo());
            libroExistente.setAutor(libroActualizado.getAutor());
            libroExistente.setAnio(libroActualizado.getAnio());
            libroExistente.setIsbn(libroActualizado.getIsbn());
            libroExistente.setNumeroPaginas(libroActualizado.getNumeroPaginas());
            libroExistente.setGenero(libroActualizado.getGenero());
            libroExistente.setEditorial(libroActualizado.getEditorial());
            libroExistente.guardar();
        }
    }

    public void eliminarLibro(int id) {
        Libro.eliminar(id);
    }

    // Métodos adicionales para filtrado (usados por CatalogoPanel)
    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        return obtenerTodosLosLibros().stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Libro> buscarLibrosPorAutor(String autor) {
        return obtenerTodosLosLibros().stream()
                .filter(l -> l.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Libro> buscarLibrosPorGenero(String genero) {
        return Libro.buscarPorGenero(genero);
    }

    // Método especial para el catálogo
    public List<Libro> buscarLibrosConFiltro(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            return obtenerTodosLosLibros();
        }
        return obtenerTodosLosLibros().stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(filtro.toLowerCase()) ||
                        l.getAutor().toLowerCase().contains(filtro.toLowerCase()) ||
                        l.getGenero().toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
    }
}