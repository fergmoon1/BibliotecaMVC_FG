package com.biblioteca.controller;

import com.biblioteca.dao.RevistaDAO;
import com.biblioteca.dao.RevistaDAOImpl;
import com.biblioteca.model.Revista;

import java.util.List;

public class RevistaController {

    private final RevistaDAO revistaDAO;

    public RevistaController() {
        this.revistaDAO = new RevistaDAOImpl();
    }

    public void agregarRevista(Revista revista) {
        revistaDAO.agregarRevista(revista);
    }

    public void actualizarRevista(Revista revista) {
        revistaDAO.actualizarRevista(revista);
    }

    public void eliminarRevista(int id) {
        revistaDAO.eliminarRevista(id);
    }

    public List<Revista> obtenerTodasLasRevistas() {
        return revistaDAO.obtenerTodasLasRevistas();
    }

    public List<Revista> buscarPorCategoria(String categoria) {
        return revistaDAO.buscarPorCategoria(categoria);
    }
}

