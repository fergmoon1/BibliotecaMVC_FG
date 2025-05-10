package com.biblioteca.model;

public class DVD extends ElementoBiblioteca {
    private int duracion;
    private String genero;

    public DVD() {
        super();
    }

    public DVD(int id, String titulo, String autor, int anioPublicacion, String tipo, int duracion, String genero) {
        super(id, titulo, autor, anioPublicacion, tipo);
        this.duracion = duracion;
        this.genero = genero;
    }

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
}
