package com.biblioteca.model;

public class DVD extends ElementoBiblioteca {
    private int duracion;
    private String genero;

    // Constructor para creación
    public DVD(String titulo, String autor, int anoPublicacion,
               int duracion, String genero) {
        super(titulo, autor, anoPublicacion, "DVD");
        this.duracion = duracion;
        this.genero = (genero != null) ? genero : ""; // Evitar null
    }

    // Constructor para BD
    public DVD(int id, String titulo, String autor, int anoPublicacion,
               int duracion, String genero) {
        super(id, titulo, autor, anoPublicacion, "DVD");
        this.duracion = duracion;
        this.genero = (genero != null) ? genero : ""; // Evitar null
    }

    // Getters y Setters
    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
    public String getGenero() { return genero != null ? genero : ""; } // Evitar devolver null
    public void setGenero(String genero) { this.genero = (genero != null) ? genero : ""; }
}