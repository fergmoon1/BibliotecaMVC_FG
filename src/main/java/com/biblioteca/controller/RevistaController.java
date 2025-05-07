package com.biblioteca.controller;

import com.biblioteca.dao.RevistaDAO;
import com.biblioteca.model.ElementoBiblioteca;
import com.biblioteca.model.Revista;
import com.biblioteca.view.DialogoAgregarElemento;
import com.biblioteca.view.DialogoDetallesElemento;
import com.biblioteca.view.PanelRevistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Controlador para gestionar las operaciones relacionadas con las revistas.
 * Conecta la vista PanelRevistas con el RevistaDAO y maneja las operaciones CRUD.
 */
public class RevistaController {

    private PanelRevistas panelRevistas;
    private RevistaDAO revistaDAO;
    private DefaultTableModel modeloTabla;

    /**
     * Constructor del controlador de revistas.
     *
     * @param panelRevistas Vista asociada para gestionar revistas
     * @param revistaDAO DAO para acceder a los datos de revistas
     */
    public RevistaController(PanelRevistas panelRevistas, RevistaDAO revistaDAO) {
        this.panelRevistas = panelRevistas;
        this.revistaDAO = revistaDAO;
        this.modeloTabla = panelRevistas.getModeloTabla();

        // Cargar las revistas al iniciar
        cargarRevistas();

        // Agregar acciones a los botones
        agregarAcciones();
    }

    /**
     * Carga todas las revistas desde el DAO y las muestra en la tabla.
     */
    private void cargarRevistas() {
        try {
            modeloTabla.setRowCount(0); // Limpiar la tabla
            List<ElementoBiblioteca> elementos = revistaDAO.obtenerTodos();
            for (ElementoBiblioteca elemento : elementos) {
                if (elemento instanceof Revista) {
                    Revista revista = (Revista) elemento;
                    modeloTabla.addRow(new Object[]{
                            revista.getId(),
                            revista.getTitulo(),
                            revista.getAutor(),
                            revista.getAnoPublicacion(),
                            revista.getNombreEdicion(),
                            revista.getCategoria()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panelRevistas, "Error al cargar las revistas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Agrega acciones a los botones y la tabla de la vista.
     */
    private void agregarAcciones() {
        // Acción para el botón Agregar
        panelRevistas.getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoAgregar();
            }
        });

        // Acción para el botón Editar
        panelRevistas.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = panelRevistas.getTablaRevistas().getSelectedRow();
                if (filaSeleccionada != -1) {
                    JOptionPane.showMessageDialog(panelRevistas, "Funcionalidad de edición aún no implementada.",
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(panelRevistas, "Seleccione una revista para editar.",
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Acción para el botón Eliminar
        panelRevistas.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = panelRevistas.getTablaRevistas().getSelectedRow();
                if (filaSeleccionada != -1) {
                    int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                    int confirm = JOptionPane.showConfirmDialog(panelRevistas,
                            "¿Está seguro de que desea eliminar esta revista?",
                            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            if (revistaDAO.eliminar(id)) {
                                JOptionPane.showMessageDialog(panelRevistas, "Revista eliminada exitosamente.");
                                cargarRevistas();
                            } else {
                                JOptionPane.showMessageDialog(panelRevistas, "Error al eliminar la revista.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(panelRevistas, "Error al eliminar la revista: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(panelRevistas, "Seleccione una revista para eliminar.",
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Acción para mostrar detalles al hacer doble clic en la tabla
        panelRevistas.getTablaRevistas().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int filaSeleccionada = panelRevistas.getTablaRevistas().getSelectedRow();
                    if (filaSeleccionada != -1) {
                        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                        try {
                            ElementoBiblioteca elemento = revistaDAO.buscarPorId(id);
                            if (elemento instanceof Revista) {
                                Revista revista = (Revista) elemento;
                                new DialogoDetallesElemento((JFrame) SwingUtilities.getWindowAncestor(panelRevistas), revista).setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(panelRevistas, "El elemento seleccionado no es una revista.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(panelRevistas, "Error al buscar la revista: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }

    /**
     * Muestra el diálogo para agregar una nueva revista y maneja la acción de agregar.
     */
    private void mostrarDialogoAgregar() {
        DialogoAgregarElemento dialogo = new DialogoAgregarElemento(
                (JFrame) SwingUtilities.getWindowAncestor(panelRevistas), "Revista");

        // Reemplazar la acción del botón Aceptar
        dialogo.getBtnAceptar().removeActionListener(dialogo.getBtnAceptar().getActionListeners()[0]);
        dialogo.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(dialogo.getTxtId().getText());
                    String titulo = dialogo.getTxtTitulo().getText();
                    String autor = dialogo.getTxtAutor().getText();
                    int anoPublicacion = Integer.parseInt(dialogo.getTxtAnoPublicacion().getText());
                    String nombreEdicion = dialogo.getTxtDatoEspecifico1().getText();
                    String categoria = dialogo.getTxtDatoEspecifico2().getText();

                    if (titulo.isEmpty() || autor.isEmpty() || nombreEdicion.isEmpty() || categoria.isEmpty()) {
                        JOptionPane.showMessageDialog(dialogo, "Por favor, complete todos los campos requeridos.",
                                "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    Revista revista = new Revista(id, titulo, autor, anoPublicacion, nombreEdicion, categoria);
                    if (revistaDAO.crear(revista)) {
                        JOptionPane.showMessageDialog(dialogo, "Revista agregada exitosamente.");
                        cargarRevistas();
                        dialogo.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialogo, "Error al agregar la revista.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialogo, "ID y Año de Publicación deben ser números.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialogo, "Error al agregar la revista: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialogo.setVisible(true);
    }
}