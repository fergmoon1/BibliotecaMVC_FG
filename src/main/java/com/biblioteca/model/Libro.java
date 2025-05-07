package com.biblioteca.model;

/**
 * Clase que representa un libro en la com.biblioteca.
 * Extiende de ElementoBiblioteca e incluye atributos específicos de libros.
 */
public class Libro extends ElementoBiblioteca {

    // Atributos específicos de Libro
    private String ISBN;
    private String generoPrincipal;
    private String genero;
    private String editorial;

    /**
     * Constructor por defecto
     */
    public Libro() {
        super();
        // Establecemos el tipo como "Libro" automáticamente
        setTipo("Libro");
    }

    /**
     * Constructor con parámetros
     *
     * @param id Identificador único del libro
     * @param titulo Título del libro
     * @param autor Autor del libro
     * @param anoPublicacion Año de publicación
     * @param ISBN Código ISBN del libro
     * @param generoPrincipal Género principal del libro
     * @param genero Género secundario del libro
     * @param editorial Editorial del libro
     */
    public Libro(int id, String titulo, String autor, int anoPublicacion,
                 String ISBN, String generoPrincipal, String genero, String editorial) {
        // Llamamos al constructor de la clase padre
        super(id, titulo, autor, anoPublicacion, "Libro");
        this.ISBN = ISBN;
        this.generoPrincipal = generoPrincipal;
        this.genero = genero;
        this.editorial = editorial;
    }

    // Getters y Setters específicos

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getGeneroPrincipal() {
        return generoPrincipal;
    }

    public void setGeneroPrincipal(String generoPrincipal) {
        this.generoPrincipal = generoPrincipal;
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

    /**
     * Sobrescritura del método toString para incluir los atributos específicos de Libro
     *
     * @return String con toda la información del libro
     */
    @Override
    public String toString() {
        return "Libro{" +
                "id=" + getId() +
                ", titulo='" + getTitulo() + '\'' +
                ", autor='" + getAutor() + '\'' +
                ", anoPublicacion=" + getAnoPublicacion() +
                ", ISBN='" + ISBN + '\'' +
                ", generoPrincipal='" + generoPrincipal + '\'' +
                ", genero='" + genero + '\'' +
                ", editorial='" + editorial + '\'' +
                '}';
    }

    /**
     * Método para obtener una representación resumida del libro
     *
     * @return String con información resumida del libro
     */
    public String getResumen() {
        return getTitulo() + " - " + getAutor() + " (" + getAnoPublicacion() + ")";
    }
}