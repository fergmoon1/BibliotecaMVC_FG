package com.biblioteca.model;

public class Libro extends ElementoBiblioteca {

    private String genero;

    public Libro() {
        super();
    }

    public Libro(int id, String titulo, String autor, int anoPublicacion, String tipo, String genero) {
        super(id, titulo, autor, anoPublicacion, tipo);
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
