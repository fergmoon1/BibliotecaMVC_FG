package modelo;

public class DVD extends ElementoBiblioteca {
    private int duracion; // en minutos
    private String genero;

    public DVD(int id, String titulo, String autor, int anoPublicacion,
               int duracion, String genero) {
        super(id, titulo, autor, anoPublicacion, "DVD");
        this.duracion = duracion;
        this.genero = genero;
    }

    // Getters y Setters específicos
    public int getDuracion() {
        return duracion;
    }

    public String getGenero() {
        return genero;
    }

    @Override
    public String obtenerInformacionEspecifica() {
        return "Duración: " + duracion + " mins, Género: " + genero;
    }
}