package com.biblioteca.controller;

import com.biblioteca.dao.RevistaDAO;
import com.biblioteca.dao.RevistaDAOImpl;
import com.biblioteca.model.Revista;
import java.util.List;

public class RevistaController {
    private RevistaDAO revistaDAO;

    public RevistaController() {
        this.revistaDAO = new RevistaDAOImpl();
    }

    // Métodos CRUD
    public void agregarRevista(Revista revista) {
        revistaDAO.agregarRevista(revista);
    }

    public void actualizarRevista(Revista revista) {
        revistaDAO.actualizarRevista(revista);
    }

    public void eliminarRevista(int id) {
        revistaDAO.eliminarRevista(id);
    }

    public Revista obtenerRevistaPorId(int id) {
        return revistaDAO.obtenerRevistaPorId(id);
    }

    public List<Revista> obtenerTodasLasRevistas() {
        return revistaDAO.obtenerTodasLasRevistas();
    }

    // Método adicional para búsqueda por categoría (como en tu DER)
    public List<Revista> buscarRevistasPorCategoria(String categoria) {
        return revistaDAO.findByCategoria(categoria);
    }
}