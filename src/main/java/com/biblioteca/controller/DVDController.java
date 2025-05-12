package com.biblioteca.controller;

import com.biblioteca.model.DVD;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DVDController {
    private List<DVD> dvds;
    private int ultimoId;

    public DVDController() {
        this.dvds = new ArrayList<>();
        this.ultimoId = 0;
        inicializarDatosDemo();
    }

    private void inicializarDatosDemo() {
        agregarDVD(new DVD(0, "El Padrino", "Francis Ford Coppola", 1972, 175, "Drama"));
        agregarDVD(new DVD(0, "Interestelar", "Christopher Nolan", 2014, 169, "Ciencia Ficción"));
        agregarDVD(new DVD(0, "Parásitos", "Bong Joon-ho", 2019, 132, "Suspenso"));
    }

    // Operaciones CRUD básicas
    public void agregarDVD(DVD dvd) {
        dvd.setId(++ultimoId);
        dvds.add(dvd);
    }

    public List<DVD> obtenerTodosLosDVDs() {
        return new ArrayList<>(dvds);
    }

    public DVD buscarDVDPorId(int id) {
        return dvds.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void actualizarDVD(DVD dvdActualizado) {
        DVD dvdExistente = buscarDVDPorId(dvdActualizado.getId());
        if (dvdExistente != null) {
            dvdExistente.setTitulo(dvdActualizado.getTitulo());
            dvdExistente.setAutor(dvdActualizado.getAutor());
            dvdExistente.setAnioPublicacion(dvdActualizado.getAnioPublicacion());
            dvdExistente.setDuracion(dvdActualizado.getDuracion());
            dvdExistente.setGenero(dvdActualizado.getGenero());
        }
    }

    public void eliminarDVD(int id) {
        dvds.removeIf(d -> d.getId() == id);
    }

    // Métodos de búsqueda específicos
    public List<DVD> buscarDVDsPorTitulo(String titulo) {
        return dvds.stream()
                .filter(d -> d.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<DVD> buscarDVDsPorDirector(String director) {
        return dvds.stream()
                .filter(d -> d.getAutor().equalsIgnoreCase(director))
                .collect(Collectors.toList());
    }

    public List<DVD> buscarDVDsPorGenero(String genero) {
        return dvds.stream()
                .filter(d -> d.getGenero().equalsIgnoreCase(genero))
                .collect(Collectors.toList());
    }

    public List<DVD> buscarDVDsPorRangoAnio(int anioInicio, int anioFin) {
        return dvds.stream()
                .filter(d -> d.getAnioPublicacion() >= anioInicio &&
                        d.getAnioPublicacion() <= anioFin)
                .collect(Collectors.toList());
    }

    // Método para integración con CatalogoPanel
    public List<DVD> buscarDVDsConFiltro(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            return obtenerTodosLosDVDs();
        }
        return dvds.stream()
                .filter(d -> d.getTitulo().toLowerCase().contains(filtro.toLowerCase()) ||
                        d.getAutor().toLowerCase().contains(filtro.toLowerCase()) ||
                        d.getGenero().toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
    }
}