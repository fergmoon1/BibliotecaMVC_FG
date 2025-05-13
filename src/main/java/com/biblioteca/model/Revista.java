package com.biblioteca.model;

public class Revista extends ElementoBiblioteca {
    private int numeroEdicion;
    private String categoria;

    // Constructor - Nota: No incluye autor como parámetro
    public Revista(int id, String titulo, int anoPublicacion,
                   int numeroEdicion, String categoria) {
        super(id, titulo, null, anoPublicacion); // Autor se pasa como null
        this.numeroEdicion = numeroEdicion;
        this.categoria = categoria;
    }

    // Getters y Setters
    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    public void setNumeroEdicion(int numeroEdicion) {
        this.numeroEdicion = numeroEdicion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // Implementación del método abstracto
    @Override
    public String getInfoEspecifica() {
        return "Número de edición: " + numeroEdicion +
                "\nCategoría: " + categoria;
    }

    // Método para mostrar información en formato de tabla
    public Object[] toTableRow() {
        return new Object[]{
                getId(),
                getTitulo(),
                "-", // Autor no aplica para revistas
                getAnoPublicacion(),
                "-", // Duración no aplica
                getCategoria() // Usamos categoría como género en la tabla
        };
    }
}