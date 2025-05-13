package com.biblioteca.model;

public class Libro extends ElementoBiblioteca {
    private int id;
    private String isbn;
    private int numeroPaginas;
    private String genero;
    private String editorial;

    public Libro(int id, String titulo, String autor, int anioPublicacion,
                 String isbn, int numeroPaginas, String genero, String editorial) {
        super(id, titulo, autor, anioPublicacion, "Libro");
        this.id = id;
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.genero = genero;
        this.editorial = editorial;
    }

    // Getters y setters...
    public int getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", ISBN: " + isbn +
                ", Páginas: " + numeroPaginas +
                ", Género: " + genero +
                ", Editorial: " + editorial;
    }
}