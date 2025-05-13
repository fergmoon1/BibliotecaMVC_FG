package modelo;

public abstract class ElementoBiblioteca {
    protected int id;
    protected String titulo;
    protected String autor;
    protected int anoPublicacion;
    protected String tipo; // "Libro", "Revista" o "DVD"

    // Constructor
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

    // Método abstracto para mostrar información específica
    public abstract String obtenerInformacionEspecifica();
}