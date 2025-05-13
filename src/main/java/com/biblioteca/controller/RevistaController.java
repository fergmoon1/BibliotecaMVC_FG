package com.biblioteca.controller;

import com.biblioteca.dao.RevistaDAO;
import com.biblioteca.dao.RevistaDAOImpl;
import com.biblioteca.model.Revista;
import java.util.List;
import java.util.stream.Collectors;

public class RevistaController {
    private final RevistaDAO revistaDAO;

    public RevistaController() {
        this.revistaDAO = new RevistaDAOImpl();
        inicializarDatosDemo();
    }

    private void inicializarDatosDemo() {
        if (revistaDAO.obtenerTodasLasRevistas().isEmpty()) {
            cargarDatosEjemplo();
        }
    }

    private void cargarDatosEjemplo() {
        List<Revista> revistasEjemplo = List.of(
                new Revista("National Geographic", "National Geographic Society", 2023, 5, "Ciencia"),
                new Revista("Time", "Time USA, LLC", 2023, 12, "Actualidad"),
                new Revista("Scientific American", "Springer Nature", 2022, 8, "Ciencia"),
                new Revista("PC Gamer", "Future US", 2023, 3, "Tecnología"),
                new Revista("The Economist", "The Economist Group", 2023, 15, "Economía")
        );

        revistasEjemplo.forEach(revistaDAO::agregarRevista);
    }

    // Métodos CRUD
    public void agregarRevista(Revista revista) {
        revistaDAO.agregarRevista(revista);
    }

    public List<Revista> obtenerTodasLasRevistas() {
        return revistaDAO.obtenerTodasLasRevistas();
    }

    public Revista obtenerRevistaPorId(int id) {
        return revistaDAO.obtenerRevistaPorId(id);
    }

    public void actualizarRevista(Revista revista) {
        revistaDAO.actualizarRevista(revista);
    }

    public void eliminarRevista(int id) {
        revistaDAO.eliminarRevista(id);
    }

    // Métodos de búsqueda
    public List<Revista> buscarPorTitulo(String titulo) {
        return revistaDAO.obtenerTodasLasRevistas().stream()
                .filter(r -> r.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Revista> buscarPorCategoria(String categoria) {
        return revistaDAO.findByCategoria(categoria);
    }

    public List<Revista> buscarPorAnio(int anio) {
        return revistaDAO.obtenerTodasLasRevistas().stream()
                .filter(r -> r.getAnio() == anio)
                .collect(Collectors.toList());
    }
}