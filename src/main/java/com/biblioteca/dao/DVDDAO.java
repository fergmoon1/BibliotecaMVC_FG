package com.biblioteca.dao;

import com.biblioteca.model.DVD;
import java.util.List;

public interface DVDDAO {
    void agregarDVD(DVD dvd);
    void actualizarDVD(DVD dvd);
    void eliminarDVD(int id);
    DVD obtenerDVDPorId(int id);
    List<DVD> obtenerTodosLosDVDs();
    List<DVD> buscarPorGenero(String genero);
}