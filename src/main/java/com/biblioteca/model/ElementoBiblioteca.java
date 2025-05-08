package com.biblioteca.model;

public class ElementoBiblioteca {
    private int id;
    private String titulo;
    private String autor;
    private int anoPublicacion;
    private String tipo;

    // Constructor por defecto
    public ElementoBiblioteca() {
    }

    // Constructor con parámetros
    public ElementoBiblioteca(int id, String titulo, String autor, int anoPublicacion, String tipo) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacion = anoPublicacion;
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

    public int getAnoPublicacion() {
        return anoPublicacion;
    }

    public void setAnoPublicacion(int anoPublicacion) {
        this.anoPublicacion = anoPublicacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "ElementoBiblioteca{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", anoPublicacion=" + anoPublicacion +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
