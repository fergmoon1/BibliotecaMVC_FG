package com.biblioteca.model;

public class DVD extends ElementoBiblioteca {
    private int duracion; // en minutos
    private String genero;

    // Constructor
    public DVD(int id, String titulo, String autor, int anoPublicacion,
               int duracion, String genero) {
        super(id, titulo, autor, anoPublicacion);
        this.duracion = duracion;
        this.genero = genero;
    }

    // Getters y Setters
    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    // Implementación del método abstracto
    @Override
    public String getInfoEspecifica() {
        return "Duración: " + duracion + " minutos" +
                "\nGénero: " + genero;
    }

    // Método para mostrar información en formato de tabla
    public Object[] toTableRow() {
        return new Object[]{
                getId(),
                getTitulo(),
                getAutor(),
                getAnoPublicacion(),
                getDuracion(),
                getGenero()
        };
    }

    // Método para formatear la duración en horas y minutos
    public String getDuracionFormateada() {
        int horas = duracion / 60;
        int minutos = duracion % 60;
        return String.format("%dh %02dm", horas, minutos);
    }
}