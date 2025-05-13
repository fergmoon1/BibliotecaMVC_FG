package com.biblioteca.model;

public class Revista {
    private int id;
    private String titulo;
    private String autor;
    private int anio;
    private int numero;
    private String categoria;

    // Constructores
    public Revista() {}

    public Revista(String titulo, String autor, int anio, int numero, String categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.numero = numero;
        this.categoria = categoria;
    }

    public Revista(int id, String titulo, String autor, int anio, int numero, String categoria) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.numero = numero;
        this.categoria = categoria;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}