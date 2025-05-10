package com.biblioteca.controller;

import com.biblioteca.dao.DVDDAO;
import com.biblioteca.dao.DVDDAOImpl;
import com.biblioteca.model.DVD;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

public class DVDController {
    private DVDDAO dvdDAO;

    public DVDController() {
        this.dvdDAO = new DVDDAOImpl();
    }

    public void agregarDVD(DVD dvd) {
        try {
            if (dvdDAO.agregarDVD(dvd)) {
                JOptionPane.showMessageDialog(null, "DVD agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar el DVD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editarDVD(DVD dvd) {
        try {
            if (dvdDAO.actualizarDVD(dvd)) {
                JOptionPane.showMessageDialog(null, "DVD actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el DVD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarDVD(int id) {
        try {
            if (dvdDAO.eliminarDVD(id)) {
                JOptionPane.showMessageDialog(null, "DVD eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el DVD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<DVD> buscarDVDsPorGenero(String genero) {
        try {
            return dvdDAO.buscarPorGenero(genero);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public List<DVD> obtenerTodosLosDVDs() {
        try {
            return dvdDAO.obtenerTodosLosDVDs();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}