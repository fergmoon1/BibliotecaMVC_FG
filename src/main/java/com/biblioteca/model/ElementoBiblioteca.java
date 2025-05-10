package com.biblioteca.model;

public abstract class ElementoBiblioteca {
    private int id;
    private String titulo;
    private String autor;
    private int anoPublicacion;
    private String genero;

    // Constructor sin argumentos
    public ElementoBiblioteca() {
    }

    // Constructor con parámetros (sin id, ya que se genera en la BD)
    public ElementoBiblioteca(String titulo, String autor, int anoPublicacion, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacion = anoPublicacion;
        this.genero = genero;
    }

    // Constructor con id (para cargar desde la BD)
    public ElementoBiblioteca(int id, String titulo, String autor, int anoPublicacion, String genero) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacion = anoPublicacion;
        this.genero = genero;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}