package com.biblioteca.dao;

import com.biblioteca.model.DVD;
import java.util.List;

/**
 * Interfaz DAO para gestionar los DVDs en la com.biblioteca.
 * Extiende ElementoBibliotecaDAO para heredar operaciones genéricas y añade métodos específicos para DVDs.
 */
public interface DVDDAO extends ElementoBibliotecaDAO {

    /**
     * Busca un DVD por su director.
     *
     * @param director Nombre del director del DVD
     * @return DVD encontrado, o null si no existe
     */
    DVD buscarPorDirector(String director);

    /**
     * Obtiene todos los DVDs filtrados por duración máxima.
     *
     * @param duracionMaxima Duración máxima en minutos para filtrar
     * @return Lista de DVDs con duración menor o igual a la especificada
     */
    List<DVD> obtenerPorDuracionMaxima(int duracionMaxima);

    /**
     * Obtiene todos los DVDs filtrados por formato.
     *
     * @param formato Formato del DVD (ej. DVD-R, Blu-ray)
     * @return Lista de DVDs con el formato especificado
     */
    List<DVD> obtenerPorFormato(String formato);
}
