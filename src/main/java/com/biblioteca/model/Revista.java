package com.biblioteca.model;

public class Revista extends ElementoBiblioteca {
    private int numeroEdicion;
    private String categoria;

    public Revista() {
        super();
        setTipo("Revista");
    }

    public Revista(int id, String titulo, String autor, int anioPublicacion,
                   int numeroEdicion, String categoria) {
        super(id, titulo, autor, anioPublicacion, "Revista");
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

    @Override
    public String toString() {
        return "Revista{" +
                "id=" + getId() +
                ", titulo='" + getTitulo() + '\'' +
                ", autor='" + getAutor() + '\'' +
                ", anioPublicacion=" + getAnioPublicacion() +
                ", numeroEdicion=" + numeroEdicion +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
