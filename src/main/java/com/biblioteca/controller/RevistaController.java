package com.biblioteca.controller;

import com.biblioteca.model.Revista;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RevistaController {
    private List<Revista> revistas;
    private int ultimoId;

    public RevistaController() {
        this.revistas = new ArrayList<>();
        this.ultimoId = 0;
        inicializarDatosDemo(); // Datos de ejemplo (opcional)
    }

    private void inicializarDatosDemo() {
        agregarRevista(new Revista(0, "National Geographic", "Varios Autores", 2023,
                245, "Ciencia y Naturaleza"));
        agregarRevista(new Revista(0, "Time", "Time USA", 2023,
                32, "Actualidad"));
    }

    // Operaciones CRUD
    public void agregarRevista(Revista revista) {
        revista.setId(++ultimoId);
        revistas.add(revista);
    }

    public List<Revista> obtenerTodasLasRevistas() {
        return new ArrayList<>(revistas);
    }

    public Revista buscarRevistaPorId(int id) {
        return revistas.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void actualizarRevista(Revista revistaActualizada) {
        Revista revistaExistente = buscarRevistaPorId(revistaActualizada.getId());
        if (revistaExistente != null) {
            revistaExistente.setTitulo(revistaActualizada.getTitulo());
            revistaExistente.setAutor(revistaActualizada.getAutor());
            revistaExistente.setAnioPublicacion(revistaActualizada.getAnioPublicacion());
            revistaExistente.setNumeroEdicion(revistaActualizada.getNumeroEdicion());
            revistaExistente.setCategoria(revistaActualizada.getCategoria());
        }
    }

    public void eliminarRevista(int id) {
        revistas.removeIf(r -> r.getId() == id);
    }

    // Métodos de búsqueda
    public List<Revista> buscarRevistasPorTitulo(String titulo) {
        return revistas.stream()
                .filter(r -> r.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Revista> buscarRevistasPorCategoria(String categoria) {
        return revistas.stream()
                .filter(r -> r.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    public List<Revista> buscarRevistasPorAnio(int anio) {
        return revistas.stream()
                .filter(r -> r.getAnioPublicacion() == anio)
                .collect(Collectors.toList());
    }

    // Método para el catálogo unificado
    public List<Revista> buscarRevistasConFiltro(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            return obtenerTodasLasRevistas();
        }
        return revistas.stream()
                .filter(r -> r.getTitulo().toLowerCase().contains(filtro.toLowerCase()) ||
                        r.getAutor().toLowerCase().contains(filtro.toLowerCase()) ||
                        r.getCategoria().toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
    }
}