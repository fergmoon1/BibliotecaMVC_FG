package com.biblioteca.controller;

import com.biblioteca.dao.LibroDAO;
import com.biblioteca.model.Libro;
import com.biblioteca.model.ElementoBiblioteca;
import com.biblioteca.view.DialogoAgregarElemento;
import com.biblioteca.view.DialogoDetallesElemento;
import com.biblioteca.view.PanelLibros;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Controlador para gestionar las operaciones relacionadas con los libros.
 * Conecta la vista PanelLibros con el LibroDAO y maneja las operaciones CRUD.
 */
public class LibroController {

    private PanelLibros panelLibros;
    private LibroDAO libroDAO;
    private DefaultTableModel modeloTabla;

    /**
     * Constructor del controlador de libros.
     *
     * @param panelLibros Vista asociada para gestionar libros
     * @param libroDAO DAO para acceder a los datos de libros
     */
    public LibroController(PanelLibros panelLibros, LibroDAO libroDAO) {
        this.panelLibros = panelLibros;
        this.libroDAO = libroDAO;
        this.modeloTabla = panelLibros.getModeloTabla();

        // Cargar los libros al iniciar
        cargarLibros();

        // Agregar acciones a los botones
        agregarAcciones();
    }

    /**
     * Carga todos los libros desde el DAO y los muestra en la tabla.
     */
    private void cargarLibros() {
        try {
            modeloTabla.setRowCount(0); // Limpiar la tabla
            List<ElementoBiblioteca> elementos = libroDAO.obtenerTodos();
            for (ElementoBiblioteca elemento : elementos) {
                if (elemento instanceof Libro) {
                    Libro libro = (Libro) elemento;
                    modeloTabla.addRow(new Object[]{
                            libro.getId(),
                            libro.getTitulo(),
                            libro.getAutor(),
                            libro.getAnoPublicacion(),
                            libro.getISBN(),
                            libro.getGeneroPrincipal(),
                            libro.getEditorial()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panelLibros, "Error al cargar los libros: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Agrega acciones a los botones y la tabla de la vista.
     */
    private void agregarAcciones() {
        // Acción para el botón Agregar
        panelLibros.getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoAgregar();
            }
        });

        // Acción para el botón Editar
        panelLibros.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = panelLibros.getTablaLibros().getSelectedRow();
                if (filaSeleccionada != -1) {
                    JOptionPane.showMessageDialog(panelLibros, "Funcionalidad de edición aún no implementada.",
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(panelLibros, "Seleccione un libro para editar.",
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Acción para el botón Eliminar
        panelLibros.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = panelLibros.getTablaLibros().getSelectedRow();
                if (filaSeleccionada != -1) {
                    int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                    int confirm = JOptionPane.showConfirmDialog(panelLibros,
                            "¿Está seguro de que desea eliminar este libro?",
                            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            if (libroDAO.eliminar(id)) {
                                JOptionPane.showMessageDialog(panelLibros, "Libro eliminado exitosamente.");
                                cargarLibros();
                            } else {
                                JOptionPane.showMessageDialog(panelLibros, "Error al eliminar el libro.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(panelLibros, "Error al eliminar el libro: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(panelLibros, "Seleccione un libro para eliminar.",
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Acción para mostrar detalles al hacer doble clic en la tabla
        panelLibros.getTablaLibros().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int filaSeleccionada = panelLibros.getTablaLibros().getSelectedRow();
                    if (filaSeleccionada != -1) {
                        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                        try {
                            ElementoBiblioteca elemento = libroDAO.buscarPorId(id);
                            if (elemento instanceof Libro) {
                                Libro libro = (Libro) elemento;
                                new DialogoDetallesElemento((JFrame) SwingUtilities.getWindowAncestor(panelLibros), libro).setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(panelLibros, "El elemento seleccionado no es un libro.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(panelLibros, "Error al buscar el libro: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }

    /**
     * Muestra el diálogo para agregar un nuevo libro y maneja la acción de agregar.
     */
    private void mostrarDialogoAgregar() {
        DialogoAgregarElemento dialogo = new DialogoAgregarElemento(
                (JFrame) SwingUtilities.getWindowAncestor(panelLibros), "Libro");

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
                    String isbn = dialogo.getTxtDatoEspecifico1().getText();
                    String generoPrincipal = dialogo.getTxtDatoEspecifico2().getText();
                    String editorial = ""; // No se captura en el diálogo, asignar valor por defecto

                    if (titulo.isEmpty() || autor.isEmpty() || isbn.isEmpty() || generoPrincipal.isEmpty()) {
                        JOptionPane.showMessageDialog(dialogo, "Por favor, complete todos los campos requeridos.",
                                "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    Libro libro = new Libro(id, titulo, autor, anoPublicacion, isbn, generoPrincipal, "", editorial);
                    if (libroDAO.crear(libro)) {
                        JOptionPane.showMessageDialog(dialogo, "Libro agregado exitosamente.");
                        cargarLibros();
                        dialogo.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialogo, "Error al agregar el libro.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialogo, "ID y Año de Publicación deben ser números.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialogo, "Error al agregar el libro: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialogo.setVisible(true);
    }
}
