package com.biblioteca.controller;

import com.biblioteca.dao.DVDDAO;
import com.biblioteca.dao.DVDDAOImpl;
import com.biblioteca.model.DVD;
import java.util.List;

public class DVDController {
    private DVDDAO dvdDAO;

    public DVDController() {
        this.dvdDAO = new DVDDAOImpl();
    }

    // Métodos CRUD
    public void agregarDVD(DVD dvd) {
        dvdDAO.agregarDVD(dvd);
    }

    public void actualizarDVD(DVD dvd) {
        dvdDAO.actualizarDVD(dvd);
    }

    public void eliminarDVD(int id) {
        dvdDAO.eliminarDVD(id);
    }

    public DVD obtenerDVDPorId(int id) {
        return dvdDAO.obtenerDVDPorId(id);
    }

    public List<DVD> obtenerTodosLosDVDs() {
        return dvdDAO.obtenerTodosLosDVDs();
    }

    // Método adicional para búsqueda por género (como en tu interfaz gráfica)
    public List<DVD> buscarDVDsPorGenero(String genero) {
        return dvdDAO.buscarPorGenero(genero);
    }
}