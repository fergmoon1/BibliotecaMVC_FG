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

public class BibliotecaController {
    private ElementoBibliotecaDAO elementoDAO;

    public BibliotecaController() {
        this.elementoDAO = new ElementoBibliotecaDAOImpl();
    }

    public void agregarElemento(ElementoBiblioteca elemento) throws BibliotecaException {
        validarElemento(elemento);
        try {
            elementoDAO.create(elemento);
            Logger.logInfo("Elemento agregado con ID: " + elemento.getId() + ", Tipo: " + elemento.getTipo());
        } catch (BibliotecaException e) {
            Logger.logError("Error al agregar elemento con ID: " + elemento.getId(), e);
            throw new BibliotecaException("Error al agregar elemento: " + e.getMessage(), e);
        }
    }

    public ElementoBiblioteca buscarElemento(int id) throws BibliotecaException {
        if (id <= 0) {
            throw new BibliotecaException("El ID debe ser un número positivo.");
        }
        try {
            ElementoBiblioteca elemento = elementoDAO.read(id);
            if (elemento == null) {
                throw new BibliotecaException("No se encontró un elemento con ID: " + id);
            }
            Logger.logInfo("Elemento encontrado con ID: " + id);
            return elemento;
        } catch (BibliotecaException e) {
            Logger.logError("Error al buscar elemento con ID: " + id, e);
            throw new BibliotecaException("Error al buscar elemento: " + e.getMessage(), e);
        }
    }

    public void actualizarElemento(ElementoBiblioteca elemento) throws BibliotecaException {
        validarElemento(elemento);
        try {
            elementoDAO.update(elemento);
            Logger.logInfo("Elemento actualizado con ID: " + elemento.getId() + ", Tipo: " + elemento.getTipo());
        } catch (BibliotecaException e) {
            Logger.logError("Error al actualizar elemento con ID: " + elemento.getId(), e);
            throw new BibliotecaException("Error al actualizar elemento: " + e.getMessage(), e);
        }
    }

    public void eliminarElemento(int id) throws BibliotecaException {
        if (id <= 0) {
            throw new BibliotecaException("El ID debe ser un número positivo.");
        }
        try {
            elementoDAO.delete(id);
            Logger.logInfo("Elemento eliminado con ID: " + id);
        } catch (BibliotecaException e) {
            Logger.logError("Error al eliminar elemento con ID: " + id, e);
            throw new BibliotecaException("Error al eliminar elemento: " + e.getMessage(), e);
        }
    }

    public List<ElementoBiblioteca> obtenerTodos() throws BibliotecaException {
        try {
            List<ElementoBiblioteca> elementos = elementoDAO.getAll();
            if (elementos == null) {
                throw new BibliotecaException("Error al obtener la lista de elementos.");
            }
            Logger.logInfo("Lista de elementos obtenida. Total: " + elementos.size());
            return elementos;
        } catch (BibliotecaException e) {
            Logger.logError("Error al obtener todos los elementos", e);
            throw new BibliotecaException("Error al obtener todos los elementos: " + e.getMessage(), e);
        }
    }

    public Libro crearLibro(int id, String titulo, String autor, int anoPublicacion, String tipo,
                            String isbn, int numeroPaginas, String genero, String editorial) throws BibliotecaException {
        if (id <= 0) throw new BibliotecaException("El ID debe ser un número positivo.");
        if (numeroPaginas <= 0) throw new BibliotecaException("El número de páginas debe ser un número positivo.");
        return new Libro(id, titulo, autor, anoPublicacion, tipo, isbn, numeroPaginas, genero, editorial);
    }

    public Revista crearRevista(int id, String titulo, String autor, int anoPublicacion, String tipo,
                                int numeroEdicion, String categoria) throws BibliotecaException {
        if (id <= 0) throw new BibliotecaException("El ID debe ser un número positivo.");
        if (numeroEdicion <= 0) throw new BibliotecaException("El número de edición debe ser un número positivo.");
        return new Revista(id, titulo, autor, anoPublicacion, tipo, numeroEdicion, categoria);
    }

    public DVD crearDVD(int id, String titulo, String autor, int anoPublicacion, String tipo,
                        int duracion, String genero) throws BibliotecaException {
        if (id <= 0) throw new BibliotecaException("El ID debe ser un número positivo.");
        if (duracion <= 0) throw new BibliotecaException("La duración debe ser un número positivo.");
        return new DVD(id, titulo, autor, anoPublicacion, tipo, duracion, genero);
    }

    private void validarElemento(ElementoBiblioteca elemento) throws BibliotecaException {
        if (elemento == null) {
            throw new BibliotecaException("El elemento no puede ser nulo.");
        }
        if (elemento.getId() <= 0) {
            throw new BibliotecaException("El ID debe ser un número positivo.");
        }
        if (elemento.getTitulo() == null || elemento.getTitulo().trim().isEmpty()) {
            throw new BibliotecaException("El título no puede estar vacío.");
        }
        if (elemento.getTipo() == null || elemento.getTipo().trim().isEmpty()) {
            throw new BibliotecaException("El tipo no puede estar vacío.");
        }
        if (elemento.getAnoPublicacion() < 0) {
            throw new BibliotecaException("El año de publicación no puede ser negativo.");
        }
        if (elemento instanceof Libro) {
            Libro libro = (Libro) elemento;
            if (libro.getIsbn() != null && libro.getIsbn().trim().isEmpty()) {
                throw new BibliotecaException("El ISBN no puede estar vacío si se proporciona.");
            }
            if (libro.getNumeroPaginas() <= 0) {
                throw new BibliotecaException("El número de páginas debe ser un número positivo.");
            }
        } else if (elemento instanceof Revista) {
            Revista revista = (Revista) elemento;
            if (revista.getNumeroEdicion() <= 0) {
                throw new BibliotecaException("El número de edición debe ser un número positivo.");
            }
        } else if (elemento instanceof DVD) {
            DVD dvd = (DVD) elemento;
            if (dvd.getDuracion() <= 0) {
                throw new BibliotecaException("La duración debe ser un número positivo.");
            }
        }
    }
}