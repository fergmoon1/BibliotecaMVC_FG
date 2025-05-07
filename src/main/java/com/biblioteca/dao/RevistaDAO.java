package com.biblioteca.dao;

import com.biblioteca.model.Revista;
import java.util.List;

/**
 * Interfaz DAO para gestionar las revistas en la com.biblioteca.
 * Extiende ElementoBibliotecaDAO para heredar operaciones genéricas y añade métodos específicos para revistas.
 */
public interface RevistaDAO extends ElementoBibliotecaDAO {

    /**
     * Busca una revista por su nombre de edición.
     *
     * @param nombreEdicion Nombre de la edición de la revista
     * @return Revista encontrada, o null si no existe
     */
    Revista buscarPorNombreEdicion(String nombreEdicion);

    /**
     * Obtiene todas las revistas filtradas por categoría.
     *
     * @param categoria Categoría para filtrar
     * @return Lista de revistas con la categoría especificada
     */
    List<Revista> obtenerPorCategoria(String categoria);
}