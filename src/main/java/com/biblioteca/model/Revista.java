package com.biblioteca.model;

public class Revista extends ElementoBiblioteca {
    private int numeroEdicion;
    private String categoria;

    public Revista() {
    }

    public Revista(int id, String titulo, String autor, int anoPublicacion, String tipo, int numeroEdicion, String categoria) {
        super(id, titulo, autor, anoPublicacion, tipo);
        this.numeroEdicion = numeroEdicion;
        this.categoria = categoria;
    }

    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    public void setNumeroEdicion(int numeroEdicion) {
        this.numeroEdicion = numeroEdicion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}