package com.biblioteca.model;

public class Revista extends ElementoBiblioteca {
    private int numeroEdicion;
    private String categoria;

    // Constructor para creación
    public Revista(String titulo, String autor, int anoPublicacion,
                   int numeroEdicion, String categoria) {
        super(titulo, autor, anoPublicacion, "Revista");
        this.numeroEdicion = numeroEdicion;
        this.categoria = (categoria != null) ? categoria : ""; // Evitar null
    }

    // Constructor para BD
    public Revista(int id, String titulo, String autor, int anoPublicacion,
                   int numeroEdicion, String categoria) {
        super(id, titulo, autor, anoPublicacion, "Revista");
        this.numeroEdicion = numeroEdicion;
        this.categoria = (categoria != null) ? categoria : ""; // Evitar null
    }

    // Getters y Setters
    public int getNumeroEdicion() { return numeroEdicion; }
    public void setNumeroEdicion(int numeroEdicion) { this.numeroEdicion = numeroEdicion; }
    public String getCategoria() { return categoria != null ? categoria : ""; } // Evitar devolver null
    public void setCategoria(String categoria) { this.categoria = (categoria != null) ? categoria : ""; }
}