package com.biblioteca.model;

public class DVD extends ElementoBiblioteca {
    private int duracion;
    private String director;
    private String formato;

    // Constructor vacío
    public DVD() {
    }

    // Constructor con parámetros
    public DVD(String titulo, String autor, int anoPublicacion, String genero, int duracion, String director, String formato) {
        super(titulo, autor, anoPublicacion, genero);
        this.duracion = duracion;
        this.director = director;
        this.formato = formato;
    }

    // Constructor con id (para cargar desde la BD)
    public DVD(int id, String titulo, String autor, int anoPublicacion, String genero, int duracion, String director, String formato) {
        super(id, titulo, autor, anoPublicacion, genero);
        this.duracion = duracion;
        this.director = director;
        this.formato = formato;
    }

    // Getters y Setters
    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }
}