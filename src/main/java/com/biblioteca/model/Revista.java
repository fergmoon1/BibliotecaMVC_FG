package com.biblioteca.model;

public class Revista extends ElementoBiblioteca {
    private int id;  // PK y FK según DER
    private int numeroEdicion;
    private String categoria;

    public Revista(int id, String titulo, String autor, int anioPublicacion,
                   int numeroEdicion, String categoria) {
        super(id, titulo, autor, anioPublicacion, "Revista");
        this.id = id;
        this.numeroEdicion = numeroEdicion;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return super.toString() +
                ", Número Edición: " + numeroEdicion +
                ", Categoría: " + categoria;
    }
}