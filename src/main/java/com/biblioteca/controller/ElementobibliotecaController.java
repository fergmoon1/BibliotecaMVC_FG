package com.biblioteca.controller;

import com.biblioteca.dao.ElementoBibliotecaDAO;
import com.biblioteca.dao.ElementoBibliotecaDAOImpl;
import com.biblioteca.model.ElementoBiblioteca;

import java.util.List;

public class ElementoBibliotecaController {

    private final ElementoBibliotecaDAO elementoDAO;

    public ElementoBibliotecaController() {
        this.elementoDAO = new ElementoBibliotecaDAOImpl();
    }

    public void agregarElemento(ElementoBiblioteca elemento) {
        elementoDAO.agregarElemento(elemento);
    }

    public void actualizarElemento(ElementoBiblioteca elemento) {
        elementoDAO.actualizarElemento(elemento);
    }

    public void eliminarElemento(int id) {
        elementoDAO.eliminarElemento(id);
    }

    public ElementoBiblioteca obtenerElementoPorId(int id) {
        return elementoDAO.obtenerElementoPorId(id);
    }

    public List<ElementoBiblioteca> obtenerTodosLosElementos() {
        return elementoDAO.obtenerTodosLosElementos();
    }

    public List<ElementoBiblioteca> buscarPorGenero(String genero) {
        return elementoDAO.buscarPorGenero(genero);
    }
}

