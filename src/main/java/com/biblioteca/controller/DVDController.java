package com.biblioteca.controller;

import com.biblioteca.dao.DVDDAO;
import com.biblioteca.dao.DVDDAOImpl;
import com.biblioteca.model.DVD;

import java.util.List;

public class DVDController {

    private final DVDDAO dvdDAO;

    public DVDController() {
        this.dvdDAO = new DVDDAOImpl();
    }

    public void agregarDVD(DVD dvd) {
        dvdDAO.agregarDVD(dvd);
    }

    public void actualizarDVD(DVD dvd) {
        dvdDAO.actualizarDVD(dvd);
    }

    public void eliminarDVD(int id) {
        dvdDAO.eliminarDVD(id);
    }

    public List<DVD> obtenerTodosLosDVDs() {
        return dvdDAO.obtenerTodosLosDVDs();
    }

    public List<DVD> buscarPorGenero(String genero) {
        return dvdDAO.buscarPorGenero(genero);
    }
}
