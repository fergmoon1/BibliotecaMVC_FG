package modelo;

public class Libro extends ElementoBiblioteca {
    private String isbn;
    private int numeroPaginas;
    private String genero;
    private String editorial;

    public Libro(int id, String titulo, String autor, int anoPublicacion,
                 String isbn, int numeroPaginas, String genero, String editorial) {
        super(id, titulo, autor, anoPublicacion, "Libro");
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.genero = genero;
        this.editorial = editorial;
    }

    // Getters y Setters específicos
    public String getIsbn() {
        return isbn;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public String getGenero() {
        return genero;
    }

    public String getEditorial() {
        return editorial;
    }

    @Override
    public String obtenerInformacionEspecifica() {
        return "ISBN: " + isbn + ", Páginas: " + numeroPaginas + ", Editorial: " + editorial;
    }
}