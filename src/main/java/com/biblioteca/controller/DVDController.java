package com.biblioteca.controller;

import com.biblioteca.dao.DVDDAO;
import com.biblioteca.model.DVD;
import com.biblioteca.model.ElementoBiblioteca;
import com.biblioteca.view.PanelDVDs;
import com.biblioteca.view.DialogoAgregarElemento;
import com.biblioteca.view.DialogoDetallesElemento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Controlador para gestionar las operaciones relacionadas con los DVDs.
 * Conecta la vista PanelDVDs con el DVDDAO y maneja las operaciones CRUD.
 */
public class DVDController {

    private PanelDVDs panelDVDs;
    private DVDDAO dvdDAO;
    private DefaultTableModel modeloTabla;

    /**
     * Constructor del controlador de DVDs.
     *
     * @param panelDVDs Vista asociada para gestionar DVDs
     * @param dvdDAO DAO para acceder a los datos de DVDs
     */
    public DVDController(PanelDVDs panelDVDs, DVDDAO dvdDAO) {
        this.panelDVDs = panelDVDs;
        this.dvdDAO = dvdDAO;
        this.modeloTabla = panelDVDs.getModeloTabla();

        // Cargar los DVDs al iniciar
        cargarDVDs();

        // Agregar acciones a los botones
        agregarAcciones();
    }

    /**
     * Carga todos los DVDs desde el DAO y los muestra en la tabla.
     */
    private void cargarDVDs() {
        try {
            modeloTabla.setRowCount(0); // Limpiar la tabla
            List<ElementoBiblioteca> elementos = dvdDAO.obtenerTodos();
            for (ElementoBiblioteca elemento : elementos) {
                if (elemento instanceof DVD) {
                    DVD dvd = (DVD) elemento;
                    modeloTabla.addRow(new Object[]{
                            dvd.getId(),
                            dvd.getTitulo(),
                            dvd.getAutor(),
                            dvd.getAnoPublicacion(),
                            dvd.getDirector(),
                            dvd.getDuracionMinutos(),
                            dvd.getFormato()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panelDVDs, "Error al cargar los DVDs: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Agrega acciones a los botones y la tabla de la vista.
     */
    private void agregarAcciones() {
        // Acción para el botón Agregar
        panelDVDs.getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoAgregar();
            }
        });

        // Acción para el botón Editar
        panelDVDs.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = panelDVDs.getTablaDVDs().getSelectedRow();
                if (filaSeleccionada != -1) {
                    JOptionPane.showMessageDialog(panelDVDs, "Funcionalidad de edición aún no implementada.",
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(panelDVDs, "Seleccione un DVD para editar.",
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Acción para el botón Eliminar
        panelDVDs.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = panelDVDs.getTablaDVDs().getSelectedRow();
                if (filaSeleccionada != -1) {
                    int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                    int confirm = JOptionPane.showConfirmDialog(panelDVDs,
                            "¿Está seguro de que desea eliminar este DVD?",
                            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            if (dvdDAO.eliminar(id)) {
                                JOptionPane.showMessageDialog(panelDVDs, "DVD eliminado exitosamente.");
                                cargarDVDs();
                            } else {
                                JOptionPane.showMessageDialog(panelDVDs, "Error al eliminar el DVD.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(panelDVDs, "Error al eliminar el DVD: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(panelDVDs, "Seleccione un DVD para eliminar.",
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Acción para mostrar detalles al hacer doble clic en la tabla
        panelDVDs.getTablaDVDs().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int filaSeleccionada = panelDVDs.getTablaDVDs().getSelectedRow();
                    if (filaSeleccionada != -1) {
                        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                        try {
                            ElementoBiblioteca elemento = dvdDAO.buscarPorId(id);
                            if (elemento instanceof DVD) {
                                DVD dvd = (DVD) elemento;
                                new DialogoDetallesElemento((JFrame) SwingUtilities.getWindowAncestor(panelDVDs), dvd).setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(panelDVDs, "El elemento seleccionado no es un DVD.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(panelDVDs, "Error al buscar el DVD: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }

    /**
     * Muestra el diálogo para agregar un nuevo DVD y maneja la acción de agregar.
     */
    private void mostrarDialogoAgregar() {
        DialogoAgregarElemento dialogo = new DialogoAgregarElemento(
                (JFrame) SwingUtilities.getWindowAncestor(panelDVDs), "DVD");

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
                    String director = dialogo.getTxtDatoEspecifico1().getText();
                    int duracionMinutos = Integer.parseInt(dialogo.getTxtDatoEspecifico2().getText());
                    String formato = ""; // No se captura en el diálogo, asignar valor por defecto

                    if (titulo.isEmpty() || autor.isEmpty() || director.isEmpty()) {
                        JOptionPane.showMessageDialog(dialogo, "Por favor, complete todos los campos requeridos.",
                                "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    DVD dvd = new DVD(id, titulo, autor, anoPublicacion, director, duracionMinutos, formato);
                    if (dvdDAO.crear(dvd)) {
                        JOptionPane.showMessageDialog(dialogo, "DVD agregado exitosamente.");
                        cargarDVDs();
                        dialogo.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialogo, "Error al agregar el DVD.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialogo, "ID, Año de Publicación y Duración deben ser números.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialogo, "Error al agregar el DVD: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialogo.setVisible(true);
    }
}