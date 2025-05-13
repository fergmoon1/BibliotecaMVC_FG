package com.biblioteca.model;

public abstract class ElementoBiblioteca {
    protected int id;
    protected String titulo;
    protected String autor;
    protected int anioPublicacion;
    protected String tipo;  // "Libro", "Revista" o "DVD"

    // Constructor
    public ElementoBiblioteca(int id, String titulo, String autor, int anioPublicacion, String tipo) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.tipo = tipo;
    }

    // Getters y Setters
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Método toString() base
    @Override
    public String toString() {
        return "ID: " + id +
                ", Título: " + titulo +
                ", Autor: " + autor +
                ", Año: " + anioPublicacion +
                ", Tipo: " + tipo;
    }
}