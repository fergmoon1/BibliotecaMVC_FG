package com.biblioteca.controller;

import com.biblioteca.model.DVD;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DVDController {

    public DVDController() {
        inicializarDatosDemo();
    }

    private void inicializarDatosDemo() {
        if (DVD.obtenerTodos().isEmpty()) {
            DVD.cargarDatosEjemplo();
        }
    }

    public void agregarDVD(DVD dvd) {
        dvd.guardar();
    }

    public List<DVD> obtenerTodosLosDVDs() {
        return DVD.obtenerTodos();
    }

    public DVD buscarDVDPorId(int id) {
        return DVD.obtener(id);
    }

    public void actualizarDVD(DVD dvdActualizado) {
        DVD dvdExistente = buscarDVDPorId(dvdActualizado.getId());
        if (dvdExistente != null) {
            dvdExistente.setTitulo(dvdActualizado.getTitulo());
            dvdExistente.setDirector(dvdActualizado.getDirector());
            dvdExistente.setAnio(dvdActualizado.getAnio());
            dvdExistente.setDuracion(dvdActualizado.getDuracion());
            dvdExistente.setGenero(dvdActualizado.getGenero());
            dvdExistente.guardar();
        }
    }

    public void eliminarDVD(int id) {
        DVD.eliminar(id);
    }

    public List<DVD> buscarDVDsPorTitulo(String titulo) {
        return obtenerTodosLosDVDs().stream()
                .filter(d -> d.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<DVD> buscarDVDsPorDirector(String director) {
        return obtenerTodosLosDVDs().stream()
                .filter(d -> d.getDirector().toLowerCase().contains(director.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<DVD> buscarDVDsPorGenero(String genero) {
        return DVD.buscarPorGenero(genero);
    }

    public List<DVD> buscarDVDsPorRangoAnio(int anioInicio, int anioFin) {
        return obtenerTodosLosDVDs().stream()
                .filter(d -> d.getAnio() >= anioInicio && d.getAnio() <= anioFin)
                .collect(Collectors.toList());
    }

    public List<DVD> buscarDVDsConFiltro(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            return obtenerTodosLosDVDs();
        }
        return obtenerTodosLosDVDs().stream()
                .filter(d -> d.getTitulo().toLowerCase().contains(filtro.toLowerCase()) ||
                        d.getDirector().toLowerCase().contains(filtro.toLowerCase()) ||
                        d.getGenero().toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
    }
}