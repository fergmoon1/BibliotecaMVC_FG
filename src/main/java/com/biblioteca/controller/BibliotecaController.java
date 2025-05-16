package com.biblioteca.controller;

import com.biblioteca.dao.ElementoBibliotecaDAO;
import com.biblioteca.dao.ElementoBibliotecaDAOImpl;
import com.biblioteca.model.ElementoBiblioteca;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Revista;
import com.biblioteca.model.DVD;
import com.biblioteca.util.BibliotecaException;
import com.biblioteca.util.Logger;
import java.util.List;
import java.util.ArrayList;

public class BibliotecaController {
    private final ElementoBibliotecaDAO elementoDAO;

    public BibliotecaController() {
        this.elementoDAO = new ElementoBibliotecaDAOImpl();
    }

    // Métodos CRUD
    public void agregarElemento(ElementoBiblioteca elemento) throws BibliotecaException {
        validarElemento(elemento);
        try {
            elementoDAO.agregar(elemento);
            Logger.logInfo("Elemento agregado: " + elemento.getTitulo());
        } catch (BibliotecaException e) {
            Logger.logError("Error al agregar elemento", e);
            throw new BibliotecaException("Error al agregar elemento: " + e.getMessage());
        }
    }

    public void actualizarElemento(ElementoBiblioteca elemento) throws BibliotecaException {
        validarElemento(elemento);
        if (elemento.getId() <= 0) {
            throw new BibliotecaException("ID inválido para actualización");
        }
        try {
            elementoDAO.actualizar(elemento);
            Logger.logInfo("Elemento actualizado ID: " + elemento.getId());
        } catch (BibliotecaException e) {
            Logger.logError("Error al actualizar elemento ID: " + elemento.getId(), e);
            throw new BibliotecaException("Error al actualizar elemento: " + e.getMessage());
        }
    }

    public void eliminarPorTitulo(String titulo) throws BibliotecaException {
        validarCadenaNoVacia(titulo, "título");
        ElementoBiblioteca elemento = buscarPorTitulo(titulo);
        elementoDAO.eliminar(elemento.getId());
        Logger.logInfo("Elemento eliminado: " + titulo);
    }

    // Métodos de búsqueda
    public List<ElementoBiblioteca> obtenerTodos() throws BibliotecaException {
        try {
            List<ElementoBiblioteca> elementos = elementoDAO.obtenerTodos();
            Logger.logInfo("Obtenidos " + elementos.size() + " elementos");
            return elementos;
        } catch (BibliotecaException e) {
            Logger.logError("Error al obtener elementos", e);
            throw new BibliotecaException("Error al obtener elementos: " + e.getMessage());
        }
    }

    public List<ElementoBiblioteca> buscarPorGenero(String genero) throws BibliotecaException {
        validarCadenaNoVacia(genero, "género");
        List<ElementoBiblioteca> resultados = new ArrayList<>();

        for (ElementoBiblioteca elemento : elementoDAO.obtenerTodos()) {
            if ((elemento instanceof Libro && ((Libro)elemento).getGenero().equalsIgnoreCase(genero)) ||
                    (elemento instanceof DVD && ((DVD)elemento).getGenero().equalsIgnoreCase(genero)) ||
                    (elemento instanceof Revista && ((Revista)elemento).getCategoria().equalsIgnoreCase(genero))) {
                resultados.add(elemento);
            }
        }

        if (resultados.isEmpty()) {
            throw new BibliotecaException("No se encontraron elementos del género: " + genero);
        }
        return resultados;
    }

    // Métodos de creación (ajustados a constructores reales)
    public Libro crearLibro(String titulo, String autor, int anoPublicacion,
                            String isbn, int paginas, String genero, String editorial)
            throws BibliotecaException {
        validarDatosComunes(titulo, autor, anoPublicacion);
        if (paginas <= 0) throw new BibliotecaException("Número de páginas inválido");
        return new Libro(titulo, autor, anoPublicacion, isbn, paginas, genero, editorial);
    }

    public Revista crearRevista(String titulo, String autor, int anoPublicacion,
                                int edicion, String categoria) throws BibliotecaException {
        validarDatosComunes(titulo, autor, anoPublicacion);
        if (edicion <= 0) throw new BibliotecaException("Número de edición inválido");
        return new Revista(titulo, autor, anoPublicacion, edicion, categoria);
    }

    public DVD crearDVD(String titulo, String autor, int anoPublicacion,
                        int duracion, String genero) throws BibliotecaException {
        validarDatosComunes(titulo, autor, anoPublicacion);
        if (duracion <= 0) throw new BibliotecaException("Duración inválida");
        return new DVD(titulo, autor, anoPublicacion, duracion, genero);
    }

    // Métodos auxiliares
    private ElementoBiblioteca buscarPorTitulo(String titulo) throws BibliotecaException {
        for (ElementoBiblioteca elemento : elementoDAO.obtenerTodos()) {
            if (elemento.getTitulo().equalsIgnoreCase(titulo)) {
                return elemento;
            }
        }
        throw new BibliotecaException("Elemento no encontrado: " + titulo);
    }

    private void validarElemento(ElementoBiblioteca elemento) throws BibliotecaException {
        if (elemento == null) {
            throw new BibliotecaException("Elemento no puede ser nulo");
        }
        validarDatosComunes(elemento.getTitulo(), elemento.getAutor(), elemento.getAnoPublicacion());

        if (elemento instanceof Libro) {
            Libro libro = (Libro) elemento;
            if (libro.getNumeroPaginas() <= 0) {
                throw new BibliotecaException("Número de páginas inválido");
            }
        } else if (elemento instanceof DVD) {
            DVD dvd = (DVD) elemento;
            if (dvd.getDuracion() <= 0) {
                throw new BibliotecaException("Duración inválida");
            }
        } else if (elemento instanceof Revista) {
            Revista revista = (Revista) elemento;
            if (revista.getNumeroEdicion() <= 0) {
                throw new BibliotecaException("Número de edición inválido");
            }
        }
    }

    private void validarDatosComunes(String titulo, String autor, int ano) throws BibliotecaException {
        validarCadenaNoVacia(titulo, "título");
        validarCadenaNoVacia(autor, "autor");
        if (ano <= 0) throw new BibliotecaException("Año de publicación inválido");
    }

    private void validarCadenaNoVacia(String valor, String campo) throws BibliotecaException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new BibliotecaException("El " + campo + " no puede estar vacío");
        }
    }
}