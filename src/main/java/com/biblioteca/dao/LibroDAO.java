package com.biblioteca.dao;

import com.biblioteca.model.Libro;
import java.util.List;

public interface LibroDAO {
    void agregarLibro(Libro libro);
    void actualizarLibro(Libro libro);
    void eliminarLibro(int id);
    Libro obtenerLibroPorId(int id);
    List<Libro> obtenerTodosLosLibros();
    List<Libro> buscarPorGenero(String genero); // ¡Nuevo método!
}