package com.biblioteca.dao;

import com.biblioteca.model.ElementoBiblioteca;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Revista;
import com.biblioteca.model.DVD;
import java.util.List;

public interface BibliotecaDAO {
    // Operaciones CRUD básicas
    void agregarElemento(ElementoBiblioteca elemento);
    void actualizarElemento(ElementoBiblioteca elemento);
    void eliminarElemento(int id);
    ElementoBiblioteca buscarPorId(int id);

    // Búsquedas específicas
    List<ElementoBiblioteca> buscarPorTitulo(String titulo);
    List<ElementoBiblioteca> buscarPorAutor(String autor);
    List<ElementoBiblioteca> buscarPorAnioPublicacion(int anio);

    // Búsquedas por tipo
    List<Libro> buscarTodosLibros();
    List<Revista> buscarTodasRevistas();
    List<DVD> buscarTodosDVDs();

    // Búsquedas por características específicas (ej: género)
    List<Libro> buscarLibrosPorGenero(String genero);
    List<DVD> buscarDVDsPorGenero(String genero);
    List<Revista> buscarRevistasPorCategoria(String categoria);
}