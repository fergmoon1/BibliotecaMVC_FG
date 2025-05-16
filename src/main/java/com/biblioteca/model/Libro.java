package com.biblioteca.model;

public class Libro extends ElementoBiblioteca {
    private String isbn;
    private int numeroPaginas;
    private String genero;
    private String editorial;

    // Constructor para creación
    public Libro(String titulo, String autor, int anoPublicacion,
                 String isbn, int numeroPaginas, String genero, String editorial) {
        super(titulo, autor, anoPublicacion, "Libro");
        this.isbn = (isbn != null) ? isbn : ""; // Evitar null
        this.numeroPaginas = numeroPaginas;
        this.genero = (genero != null) ? genero : ""; // Evitar null
        this.editorial = (editorial != null) ? editorial : ""; // Evitar null
    }

    // Constructor para BD
    public Libro(int id, String titulo, String autor, int anoPublicacion,
                 String isbn, int numeroPaginas, String genero, String editorial) {
        super(id, titulo, autor, anoPublicacion, "Libro");
        this.isbn = (isbn != null) ? isbn : ""; // Evitar null
        this.numeroPaginas = numeroPaginas;
        this.genero = (genero != null) ? genero : ""; // Evitar null
        this.editorial = (editorial != null) ? editorial : ""; // Evitar null
    }

    // Getters y Setters
    public String getIsbn() { return isbn != null ? isbn : ""; }
    public void setIsbn(String isbn) { this.isbn = (isbn != null) ? isbn : ""; }
    public int getNumeroPaginas() { return numeroPaginas; }
    public void setNumeroPaginas(int numeroPaginas) { this.numeroPaginas = numeroPaginas; }
    public String getGenero() { return genero != null ? genero : ""; } // Evitar devolver null
    public void setGenero(String genero) { this.genero = (genero != null) ? genero : ""; }
    public String getEditorial() { return editorial != null ? editorial : ""; }
    public void setEditorial(String editorial) { this.editorial = (editorial != null) ? editorial : ""; }
}