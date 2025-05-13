package com.biblioteca.model;

public abstract class ElementoBiblioteca {
    private int id;
    private String titulo;
    private String autor;
    private int anoPublicacion;

    public ElementoBiblioteca(int id, String titulo, String autor, int anoPublicacion) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacion = anoPublicacion;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public int getAnoPublicacion() { return anoPublicacion; }
    public void setAnoPublicacion(int anoPublicacion) { this.anoPublicacion = anoPublicacion; }

    public abstract String getInfoEspecifica();
}