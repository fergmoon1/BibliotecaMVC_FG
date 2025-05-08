package com.biblioteca.dao;

import com.biblioteca.model.DVD;

import java.util.List;

public interface DVDDAO extends ElementoBibliotecaDAO {
    boolean crear(DVD dvd);
    DVD leer(int id);
    boolean actualizar(DVD dvd);
    boolean eliminar(int id);
    List<DVD> obtenerTodos();
}
