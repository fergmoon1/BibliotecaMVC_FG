package com.biblioteca.dao;

import com.biblioteca.model.Libro;
import java.util.List;

/**
 * Interfaz DAO para gestionar los libros en la com.biblioteca.
 * Extiende ElementoBibliotecaDAO para heredar operaciones genéricas y añade métodos específicos para libros.
 */
public interface LibroDAO extends ElementoBibliotecaDAO {

    /**
     * Busca un libro por su ISBN.
     *
     * @param isbn Código ISBN del libro
     * @return Libro encontrado, o null si no existe
     */
    Libro buscarPorISBN(String isbn);

    /**
     * Obtiene todos los libros filtrados por género principal.
     *
     * @param generoPrincipal Género principal para filtrar
     * @return Lista de libros con el género principal especificado
     */
    List<Libro> obtenerPorGeneroPrincipal(String generoPrincipal);

    /**
     * Obtiene todos los libros filtrados por editorial.
     *
     * @param editorial Editorial para filtrar
     * @return Lista de libros de la editorial especificada
     */
    List<Libro> obtenerPorEditorial(String editorial);
}
