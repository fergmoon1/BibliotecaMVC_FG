package com.biblioteca.controller;

import com.biblioteca.dao.RevistaDAO;
import com.biblioteca.dao.RevistaDAOImpl;
import com.biblioteca.model.Revista;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

public class RevistaController {
    private RevistaDAO revistaDAO;

    public RevistaController() {
        this.revistaDAO = new RevistaDAOImpl();
    }

    public void agregarRevista(Revista revista) {
        try {
            if (revistaDAO.agregarRevista(revista)) {
                JOptionPane.showMessageDialog(null, "Revista agregada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar la revista.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editarRevista(Revista revista) {
        try {
            if (revistaDAO.actualizarRevista(revista)) {
                JOptionPane.showMessageDialog(null, "Revista actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar la revista.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarRevista(int id) {
        try {
            if (revistaDAO.eliminarRevista(id)) {
                JOptionPane.showMessageDialog(null, "Revista eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar la revista.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Revista> buscarRevistasPorCategoria(String categoria) {
        try {
            return revistaDAO.buscarPorCategoria(categoria);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public List<Revista> obtenerTodasLasRevistas() {
        try {
            return revistaDAO.obtenerTodas();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}