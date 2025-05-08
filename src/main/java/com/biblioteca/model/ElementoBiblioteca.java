package com.biblioteca.model;

/**
 * Clase base que representa un elemento genérico de la com.biblioteca.
 * Esta clase funciona como superclase para los diferentes tipos de elementos:
 * Libros, Revistas y DVDs.
 */
public class ElementoBiblioteca {

    // Atributos
    private int id;
    private String titulo;
    private String autor;
    private int anoPublicacion;
    private String tipo;

    /**
     * Constructor por defecto
     */
    public ElementoBiblioteca() {
    }

    /**
     * Constructor con parámetros
     *
     * @param id Identificador único del elemento
     * @param titulo Título del elemento
     * @param autor Autor del elemento
     * @param anoPublicacion Año de publicación
     * @param tipo Tipo de elemento (Libro, Revista, DVD)
     */
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

    /**
     * Sobrescritura del método toString para mostrar información del elemento
     *
     * @return String con la información básica del elemento
     */
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

    /**
     * Sobrescritura del método equals para comparar elementos
     *
     * @param o Objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElementoBiblioteca that = (ElementoBiblioteca) o;

        return id == that.id;
    }

    /**
     * Sobrescritura del método hashCode
     *
     * @return código hash basado en el ID
     */
    @Override
    public int hashCode() {
        return id;
    }
}