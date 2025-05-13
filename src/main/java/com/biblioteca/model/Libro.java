package com.biblioteca.model;

public class Libro extends ElementoBiblioteca {
    private String isbn;
    private int numeroPaginas;
    private String genero;
    private String editorial;

    // Constructor
    public Libro(int id, String titulo, String autor, int anoPublicacion,
                 String isbn, int numeroPaginas, String genero, String editorial) {
        super(id, titulo, autor, anoPublicacion);
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.genero = genero;
        this.editorial = editorial;
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

    // Implementación del método abstracto
    @Override
    public String getInfoEspecifica() {
        return "ISBN: " + isbn +
                "\nNúmero de páginas: " + numeroPaginas +
                "\nGénero: " + genero +
                "\nEditorial: " + editorial;
    }

    // Método para mostrar información en formato de tabla
    public Object[] toTableRow() {
        return new Object[]{
                getId(),
                getTitulo(),
                getAutor(),
                getAnoPublicacion(),
                "-", // Espacio para duración (no aplica a libros)
                getGenero()
        };
    }
}