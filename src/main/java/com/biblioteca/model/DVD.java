package com.biblioteca.model;

public class DVD extends ElementoBiblioteca {
    private int duracion;
    private String genero;

    public DVD() {
        setTipo("DVD");
    }

    public DVD(int id, String titulo, String autor, int anioPublicacion, int duracion, String genero) {
        super(id, titulo, autor, anioPublicacion, "DVD");
        this.duracion = duracion;
        this.genero = genero;
    }

    // Getters y Setters
    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return super.toString() + ", Duración: " + duracion + " mins, Género: " + genero;
    }
}