package com.biblioteca.model;

/**
 * Clase que representa una revista en la com.biblioteca.
 * Extiende de ElementoBiblioteca e incluye atributos específicos de revistas.
 */
public class Revista extends ElementoBiblioteca {

    // Atributos específicos de Revista
    private String nombreEdicion;
    private String categoria;

    /**
     * Constructor por defecto
     */
    public Revista() {
        super();
        // Establecemos el tipo como "Revista" automáticamente
        setTipo("Revista");
    }

    /**
     * Constructor con parámetros
     *
     * @param id Identificador único de la revista
     * @param titulo Título de la revista
     * @param autor Editor o autor principal de la revista
     * @param anoPublicacion Año de publicación
     * @param nombreEdicion Nombre de la edición o número de la revista
     * @param categoria Categoría o temática de la revista
     */
    public Revista(int id, String titulo, String autor, int anoPublicacion,
                   String nombreEdicion, String categoria) {
        // Llamamos al constructor de la clase padre
        super(id, titulo, autor, anoPublicacion, "Revista");
        this.nombreEdicion = nombreEdicion;
        this.categoria = categoria;
    }

    // Getters y Setters específicos

    public String getNombreEdicion() {
        return nombreEdicion;
    }

    public void setNombreEdicion(String nombreEdicion) {
        this.nombreEdicion = nombreEdicion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Sobrescritura del método toString para incluir los atributos específicos de Revista
     *
     * @return String con toda la información de la revista
     */
    @Override
    public String toString() {
        return "Revista{" +
                "id=" + getId() +
                ", titulo='" + getTitulo() + '\'' +
                ", autor='" + getAutor() + '\'' +
                ", anoPublicacion=" + getAnoPublicacion() +
                ", nombreEdicion='" + nombreEdicion + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }

    /**
     * Método para obtener una representación resumida de la revista
     *
     * @return String con información resumida de la revista
     */
    public String getResumen() {
        return getTitulo() + " - Edición: " + nombreEdicion + " (" + getAnoPublicacion() + ")";
    }
}