package com.biblioteca.model;

public class Libro extends ElementoBiblioteca {
    private String isbn;
    private int numeroPaginas;
    private String genero;
    private String editorial;

    public Libro() {
        super();
        setTipo("Libro");
    }

    public Libro(int id, String titulo, String autor, int anioPublicacion,
                 String isbn, int numeroPaginas, String genero, String editorial) {
        super(id, titulo, autor, anioPublicacion, "Libro");
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.genero = genero;
        this.editorial = editorial;
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
        return "Libro{" +
                "id=" + getId() +
                ", titulo='" + getTitulo() + '\'' +
                ", autor='" + getAutor() + '\'' +
                ", anioPublicacion=" + getAnioPublicacion() +
                ", isbn='" + isbn + '\'' +
                ", numeroPaginas=" + numeroPaginas +
                ", genero='" + genero + '\'' +
                ", editorial='" + editorial + '\'' +
                '}';
    }
}