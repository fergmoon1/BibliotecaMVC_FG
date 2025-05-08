package com.biblioteca.model;

public class DVD extends ElementoBiblioteca {
    private int duracion;
    private String genero;

    public DVD() {
        super();
        setTipo("DVD");
    }

    public DVD(int id, String titulo, String autor, int anoPublicacion,
               int duracion, String genero) {
        super(id, titulo, autor, anoPublicacion, "DVD");
        this.duracion = duracion;
        this.genero = genero;
    }

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

    @Override
    public String toString() {
        return "DVD{" +
                "duracion=" + duracion +
                ", genero='" + genero + '\'' +
                "} " + super.toString();
    }
}