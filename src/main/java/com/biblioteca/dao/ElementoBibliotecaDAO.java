package com.biblioteca.dao;

import com.biblioteca.model.ElementoBiblioteca;
import java.util.List;

/**
 * Interfaz DAO para gestionar los elementos de la com.biblioteca.
 * Define las operaciones CRUD básicas para ElementoBiblioteca y sus subclases.
 */
public interface ElementoBibliotecaDAO {

    /**
     * Crea un nuevo elemento en la com.biblioteca.
     *
     * @param elemento Elemento a crear (Libro, Revista o DVD)
     * @return true si la creación fue exitosa, false en caso contrario
     */
    boolean crear(ElementoBiblioteca elemento);

    /**
     * Busca un elemento por su ID.
     *
     * @param id Identificador del elemento
     * @return ElementoBiblioteca encontrado, o null si no existe
     */
    ElementoBiblioteca buscarPorId(int id);

    /**
     * Obtiene todos los elementos de la com.biblioteca.
     *
     * @return Lista de todos los elementos
     */
    List<ElementoBiblioteca> obtenerTodos();

    /**
     * Actualiza un elemento existente en la com.biblioteca.
     *
     * @param elemento Elemento con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    boolean actualizar(ElementoBiblioteca elemento);

    /**
     * Elimina un elemento de la com.biblioteca.
     *
     * @param id Identificador del elemento a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    boolean eliminar(int id);
}