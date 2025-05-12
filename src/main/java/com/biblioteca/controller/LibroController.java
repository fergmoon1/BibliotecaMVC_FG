package com.biblioteca.controller;

import com.biblioteca.model.Libro;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibroController {
    private List<Libro> libros;
    private int ultimoId;

    public LibroController() {
        this.libros = new ArrayList<>();
        this.ultimoId = 0;
        // Datos de ejemplo (opcional)
        inicializarDatosDemo();
    }

    private void inicializarDatosDemo() {
        agregarLibro(new Libro(0, "Cien años de soledad", "Gabriel García Márquez", 1967,
                "9780307474728", 417, "Realismo mágico", "Sudamericana"));
        agregarLibro(new Libro(0, "El Principito", "Antoine de Saint-Exupéry", 1943,
                "9780156012195", 96, "Fábula", "Reynal & Hitchcock"));
    }

    // CRUD Operations
    public void agregarLibro(Libro libro) {
        libro.setId(++ultimoId);
        libros.add(libro);
    }

    public List<Libro> obtenerTodosLosLibros() {
        return new ArrayList<>(libros);
    }

    public Libro buscarLibroPorId(int id) {
        return libros.stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void actualizarLibro(Libro libroActualizado) {
        Libro libroExistente = buscarLibroPorId(libroActualizado.getId());
        if (libroExistente != null) {
            libroExistente.setTitulo(libroActualizado.getTitulo());
            libroExistente.setAutor(libroActualizado.getAutor());
            libroExistente.setAnioPublicacion(libroActualizado.getAnioPublicacion());
            libroExistente.setIsbn(libroActualizado.getIsbn());
            libroExistente.setNumeroPaginas(libroActualizado.getNumeroPaginas());
            libroExistente.setGenero(libroActualizado.getGenero());
            libroExistente.setEditorial(libroActualizado.getEditorial());
        }
    }

    public void eliminarLibro(int id) {
        libros.removeIf(l -> l.getId() == id);
    }

    // Métodos adicionales para filtrado (usados por CatalogoPanel)
    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        return libros.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Libro> buscarLibrosPorAutor(String autor) {
        return libros.stream()
                .filter(l -> l.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Libro> buscarLibrosPorGenero(String genero) {
        return libros.stream()
                .filter(l -> l.getGenero().equalsIgnoreCase(genero))
                .collect(Collectors.toList());
    }

    // Método especial para el catálogo
    public List<Libro> buscarLibrosConFiltro(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            return obtenerTodosLosLibros();
        }
        return libros.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(filtro.toLowerCase()) ||
                        l.getAutor().toLowerCase().contains(filtro.toLowerCase()) ||
                        l.getGenero().toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
    }
}