package com.biblioteca.controller;

import com.biblioteca.dao.LibroDAO;
import com.biblioteca.dao.LibroDAOImpl;
import com.biblioteca.model.Libro;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

public class LibroController {
    private LibroDAO libroDAO;

    public LibroController() {
        this.libroDAO = new LibroDAOImpl();
    }

    public void agregarLibro(Libro libro) {
        try {
            if (libroDAO.agregarLibro(libro)) {
                JOptionPane.showMessageDialog(null, "Libro agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar el libro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editarLibro(Libro libro) {
        try {
            if (libroDAO.actualizarLibro(libro)) {
                JOptionPane.showMessageDialog(null, "Libro actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el libro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarLibro(int id) {
        try {
            if (libroDAO.eliminarLibro(id)) {
                JOptionPane.showMessageDialog(null, "Libro eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el libro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Libro> buscarLibrosPorGenero(String genero) {
        try {
            return libroDAO.buscarPorGenero(genero);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public List<Libro> obtenerTodosLosLibros() {
        try {
            return libroDAO.obtenerTodos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}