package com.biblioteca.model;

public class Libro extends ElementoBiblioteca {
    private String isbn;
    private int numeroPaginas;
    private String editorial;
    private String tipo; // Nuevo atributo para indicar el tipo (ej. "Libro")

    // Constructor vacío
    public Libro() {
    }

    // Constructor con parámetros (sin id, para creación)
    public Libro(String titulo, String autor, int anoPublicacion, String genero, String isbn, int numeroPaginas, String editorial, String tipo) {
        super(titulo, autor, anoPublicacion, genero);
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.editorial = editorial;
        this.tipo = tipo;
    }

    // Constructor con id (para cargar desde la BD)
    public Libro(int id, String titulo, String autor, int anoPublicacion, String genero, String isbn, int numeroPaginas, String editorial, String tipo) {
        super(id, titulo, autor, anoPublicacion, genero);
        setId(id); // Usamos setId en lugar de this.id
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.editorial = editorial;
        this.tipo = tipo;
    }

    // Getters y Setters
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

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}