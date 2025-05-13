package modelo;

public class Revista extends ElementoBiblioteca {
    private int numeroEdicion;
    private String categoria;

    public Revista(int id, String titulo, String autor, int anoPublicacion,
                   int numeroEdicion, String categoria) {
        super(id, titulo, autor, anoPublicacion, "Revista");
        this.numeroEdicion = numeroEdicion;
        this.categoria = categoria;
    }

    // Getters y Setters específicos
    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String obtenerInformacionEspecifica() {
        return "Edición: " + numeroEdicion + ", Categoría: " + categoria;
    }
}