package com.biblioteca.model;

/**
 * Clase que representa un DVD en la com.biblioteca.
 * Extiende de ElementoBiblioteca e incluye atributos específicos de DVDs.
 */
public class DVD extends ElementoBiblioteca {

    // Atributos específicos de DVD
    private String director;
    private int duracionMinutos;
    private String formato;

    /**
     * Constructor por defecto
     */
    public DVD() {
        super();
        // Establecemos el tipo como "DVD" automáticamente
        setTipo("DVD");
    }

    /**
     * Constructor con parámetros
     *
     * @param id Identificador único del DVD
     * @param titulo Título del DVD
     * @param autor Director del DVD
     * @param anoPublicacion Año de publicación o lanzamiento
     * @param director Nombre del director
     * @param duracionMinutos Duración en minutos
     * @param formato Formato del DVD (ej. DVD-R, Blu-ray)
     */
    public DVD(int id, String titulo, String autor, int anoPublicacion,
               String director, int duracionMinutos, String formato) {
        // Llamamos al constructor de la clase padre
        super(id, titulo, autor, anoPublicacion, "DVD");
        this.director = director;
        this.duracionMinutos = duracionMinutos;
        this.formato = formato;
    }

    // Getters y Setters específicos

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    /**
     * Sobrescritura del método toString para incluir los atributos específicos de DVD
     *
     * @return String con toda la información del DVD
     */
    @Override
    public String toString() {
        return "DVD{" +
                "id=" + getId() +
                ", titulo='" + getTitulo() + '\'' +
                ", autor='" + getAutor() + '\'' +
                ", anoPublicacion=" + getAnoPublicacion() +
                ", director='" + director + '\'' +
                ", duracionMinutos=" + duracionMinutos +
                ", formato='" + formato + '\'' +
                '}';
    }

    /**
     * Método para obtener una representación resumida del DVD
     *
     * @return String con información resumida del DVD
     */
    public String getResumen() {
        return getTitulo() + " - Dir: " + director + " (" + getAnoPublicacion() + ")";
    }
}