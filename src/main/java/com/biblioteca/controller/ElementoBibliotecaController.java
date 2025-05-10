package com.biblioteca.controller;

import com.biblioteca.dao.ElementoBibliotecaDAO;
import com.biblioteca.dao.ElementoBibliotecaDAOImpl;
import com.biblioteca.model.ElementoBiblioteca;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

public class ElementoBibliotecaController {
    private ElementoBibliotecaDAO elementoDAO;

    public ElementoBibliotecaController() {
        this.elementoDAO = new ElementoBibliotecaDAOImpl();
    }

    public void agregarElemento(ElementoBiblioteca elemento) {
        try {
            if (elementoDAO.agregarElemento(elemento)) {
                JOptionPane.showMessageDialog(null, "Elemento agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar el elemento.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editarElemento(ElementoBiblioteca elemento) {
        try {
            if (elementoDAO.actualizarElemento(elemento)) {
                JOptionPane.showMessageDialog(null, "Elemento actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el elemento.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarElemento(int id) {
        try {
            if (elementoDAO.eliminarElemento(id)) {
                JOptionPane.showMessageDialog(null, "Elemento eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el elemento.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<ElementoBiblioteca> buscarElementosPorGenero(String genero) {
        try {
            return elementoDAO.buscarPorGenero(genero);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public List<ElementoBiblioteca> obtenerTodosLosElementos() {
        try {
            return elementoDAO.obtenerTodos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}