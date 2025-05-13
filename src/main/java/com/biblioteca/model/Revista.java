package com.biblioteca.model;

import java.util.ArrayList;
import java.util.List;

public class Revista {
    private int id;
    private String titulo;
    private String editorial;
    private int periodicidad;
    private String fechaPublicacion;
    private static List<Revista> revistas = new ArrayList<>();
    private static int contadorId = 1;

    public Revista() {
    }

    public Revista(String titulo, String editorial, int periodicidad, String fechaPublicacion) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.editorial = editorial;
        this.periodicidad = periodicidad;
        this.fechaPublicacion = fechaPublicacion;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(int periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    // Métodos para gestionar la colección de revistas
    public void guardar() {
        revistas.add(this);
    }

    public static Revista buscarPorId(int id) {
        for (Revista revista : revistas) {
            if (revista.getId() == id) {
                return revista;
            }
        }
        return null;
    }

    public void actualizar() {
        for (int i = 0; i < revistas.size(); i++) {
            if (revistas.get(i).getId() == this.id) {
                revistas.set(i, this);
                break;
            }
        }
    }

    public void eliminar() {
        revistas.removeIf(r -> r.getId() == this.id);
    }

    // Método faltante: obtener todas las revistas
    public static List<Revista> obtenerTodas() {
        return new ArrayList<>(revistas);
    }

    // Método faltante: cargar datos de ejemplo
    public static void cargarDatosEjemplo() {
        if (revistas.isEmpty()) {
            new Revista("National Geographic", "National Geographic Society", 12, "01/01/2023").guardar();
            new Revista("Scientific American", "Springer Nature", 12, "15/02/2023").guardar();
            new Revista("The Economist", "The Economist Group", 52, "05/03/2023").guardar();
            new Revista("Time", "Time USA, LLC", 24, "10/04/2023").guardar();
            new Revista("Forbes", "Forbes Media", 12, "20/05/2023").guardar();
        }
    }

    @Override
    public String toString() {
        return titulo + " - " + editorial + " (" + fechaPublicacion + ")";
    }
}