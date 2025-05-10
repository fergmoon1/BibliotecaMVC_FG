package com.biblioteca.model;

public class Revista extends ElementoBiblioteca {

    private int numero;

    public Revista() {
        super();
    }

    public Revista(int id, String titulo, String autor, int anoPublicacion, String tipo, int numero) {
        super(id, titulo, autor, anoPublicacion, tipo);
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
