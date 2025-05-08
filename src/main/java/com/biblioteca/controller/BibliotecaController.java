package com.biblioteca.controller;

import com.biblioteca.dao.ElementoBibliotecaDAO;
import com.biblioteca.dao.LibroDAO;
import com.biblioteca.dao.RevistaDAO;
import com.biblioteca.dao.DVDDAO;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Revista;
import com.biblioteca.model.DVD;
import com.biblioteca.view.MainFrame;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BibliotecaController {
    private MainFrame mainFrame;
    private ElementoBibliotecaDAO elementoDAO;
    private LibroDAO libroDAO;
    private RevistaDAO revistaDAO;
    private DVDDAO dvdDAO;

    public BibliotecaController(MainFrame mainFrame, ElementoBibliotecaDAO elementoDAO, LibroDAO libroDAO, RevistaDAO revistaDAO, DVDDAO dvdDAO) {
        this.mainFrame = mainFrame;
        this.elementoDAO = elementoDAO;
        this.libroDAO = libroDAO;
        this.revistaDAO = revistaDAO;
        this.dvdDAO = dvdDAO;

        // Cargar datos iniciales en las tablas
        cargarDatosLibros();
        cargarDatosRevistas();
        cargarDatosDVDs();

        // Configurar listeners para los botones de cada pestaña
        // Libros
        mainFrame.getBtnAgregarLibros().addActionListener(e -> agregarLibro());
        mainFrame.getBtnEditarLibros().addActionListener(e -> editarLibro());
        mainFrame.getBtnEliminarLibros().addActionListener(e -> eliminarLibro());
        mainFrame.getBtnBuscarLibros().addActionListener(e -> buscarLibros());

        // Revistas
        mainFrame.getBtnAgregarRevistas().addActionListener(e -> agregarRevista());
        mainFrame.getBtnEditarRevistas().addActionListener(e -> editarRevista());
        mainFrame.getBtnEliminarRevistas().addActionListener(e -> eliminarRevista());
        mainFrame.getBtnBuscarRevistas().addActionListener(e -> buscarRevistas());

        // DVDs
        mainFrame.getBtnAgregarDVDs().addActionListener(e -> agregarDVD());
        mainFrame.getBtnEditarDVDs().addActionListener(e -> editarDVD());
        mainFrame.getBtnEliminarDVDs().addActionListener(e -> eliminarDVD());
        mainFrame.getBtnBuscarDVDs().addActionListener(e -> buscarDVDs());
    }

    // Métodos para cargar datos en las tablas
    private void cargarDatosLibros() {
        DefaultTableModel model = (DefaultTableModel) mainFrame.getTablaLibros().getModel();
        model.setRowCount(0); // Limpiar la tabla
        List<?> libros = libroDAO.obtenerTodos();
        for (Object obj : libros) {
            if (obj instanceof Libro) {
                Libro libro = (Libro) obj;
                model.addRow(new Object[]{libro.getId(), libro.getTitulo(), libro.getAutor(), libro.getAnioPublicacion()});
            }
        }
    }

    private void cargarDatosRevistas() {
        DefaultTableModel model = (DefaultTableModel) mainFrame.getTablaRevistas().getModel();
        model.setRowCount(0); // Limpiar la tabla
        List<?> revistas = revistaDAO.obtenerTodos();
        for (Object obj : revistas) {
            if (obj instanceof Revista) {
                Revista revista = (Revista) obj;
                model.addRow(new Object[]{revista.getId(), revista.getTitulo(), revista.getEditor(), revista.getNumeroEdicion()});
            }
        }
    }

    private void cargarDatosDVDs() {
        DefaultTableModel model = (DefaultTableModel) mainFrame.getTablaDVDs().getModel();
        model.setRowCount(0); // Limpiar la tabla
        List<?> dvds = dvdDAO.obtenerTodos();
        for (Object obj : dvds) {
            if (obj instanceof DVD) {
                DVD dvd = (DVD) obj;
                model.addRow(new Object[]{dvd.getId(), dvd.getTitulo(), dvd.getDirector(), dvd.getAnio(), dvd.getDuracion(), dvd.getGenero()});
            }
        }
    }

    // Métodos para manejar acciones (implementaciones básicas por ahora)
    private void agregarLibro() {
        // Mostrar un diálogo para ingresar los datos del libro
        String id = JOptionPane.showInputDialog(mainFrame, "Ingrese el ID del libro:");
        String titulo = JOptionPane.showInputDialog(mainFrame, "Ingrese el título del libro:");
        String autor = JOptionPane.showInputDialog(mainFrame, "Ingrese el autor del libro:");
        String anio = JOptionPane.showInputDialog(mainFrame, "Ingrese el año de publicación del libro:");

        try {
            Libro libro = new Libro(Integer.parseInt(id), titulo, autor, Integer.parseInt(anio));
            if (libroDAO.crear(libro)) {
                JOptionPane.showMessageDialog(mainFrame, "Libro agregado correctamente.");
                cargarDatosLibros(); // Actualizar la tabla
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Error al agregar el libro.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame, "Por favor, ingrese valores válidos.");
        }
    }

    private void editarLibro() {
        JOptionPane.showMessageDialog(mainFrame, "Funcionalidad de edición de libros aún no implementada.");
    }

    private void eliminarLibro() {
        int selectedRow = mainFrame.getTablaLibros().getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) mainFrame.getTablaLibros().getValueAt(selectedRow, 0);
            if (libroDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(mainFrame, "Libro eliminado correctamente.");
                cargarDatosLibros(); // Actualizar la tabla
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Error al eliminar el libro.");
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Por favor, seleccione un libro para eliminar.");
        }
    }

    private void buscarLibros() {
        String autor = mainFrame.getTxtBuscarLibros().getText();
        DefaultTableModel model = (DefaultTableModel) mainFrame.getTablaLibros().getModel();
        model.setRowCount(0); // Limpiar la tabla
        List<?> libros = libroDAO.obtenerTodos();
        for (Object obj : libros) {
            if (obj instanceof Libro) {
                Libro libro = (Libro) obj;
                if (libro.getAutor().toLowerCase().contains(autor.toLowerCase())) {
                    model.addRow(new Object[]{libro.getId(), libro.getTitulo(), libro.getAutor(), libro.getAnioPublicacion()});
                }
            }
        }
    }

    private void agregarRevista() {
        String id = JOptionPane.showInputDialog(mainFrame, "Ingrese el ID de la revista:");
        String titulo = JOptionPane.showInputDialog(mainFrame, "Ingrese el título de la revista:");
        String editor = JOptionPane.showInputDialog(mainFrame, "Ingrese el editor de la revista:");
        String numeroEdicion = JOptionPane.showInputDialog(mainFrame, "Ingrese el número de edición de la revista:");

        try {
            Revista revista = new Revista(Integer.parseInt(id), titulo, editor, Integer.parseInt(numeroEdicion));
            if (revistaDAO.crear(revista)) {
                JOptionPane.showMessageDialog(mainFrame, "Revista agregada correctamente.");
                cargarDatosRevistas(); // Actualizar la tabla
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Error al agregar la revista.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame, "Por favor, ingrese valores válidos.");
        }
    }

    private void editarRevista() {
        JOptionPane.showMessageDialog(mainFrame, "Funcionalidad de edición de revistas aún no implementada.");
    }

    private void eliminarRevista() {
        int selectedRow = mainFrame.getTablaRevistas().getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) mainFrame.getTablaRevistas().getValueAt(selectedRow, 0);
            if (revistaDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(mainFrame, "Revista eliminada correctamente.");
                cargarDatosRevistas(); // Actualizar la tabla
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Error al eliminar la revista.");
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Por favor, seleccione una revista para eliminar.");
        }
    }

    private void buscarRevistas() {
        String editor = mainFrame.getTxtBuscarRevistas().getText();
        DefaultTableModel model = (DefaultTableModel) mainFrame.getTablaRevistas().getModel();
        model.setRowCount(0); // Limpiar la tabla
        List<?> revistas = revistaDAO.obtenerTodos();
        for (Object obj : revistas) {
            if (obj instanceof Revista) {
                Revista revista = (Revista) obj;
                if (revista.getEditor().toLowerCase().contains(editor.toLowerCase())) {
                    model.addRow(new Object[]{revista.getId(), revista.getTitulo(), revista.getEditor(), revista.getNumeroEdicion()});
                }
            }
        }
    }

    private void agregarDVD() {
        String id = JOptionPane.showInputDialog(mainFrame, "Ingrese el ID del DVD:");
        String titulo = JOptionPane.showInputDialog(mainFrame, "Ingrese el título del DVD:");
        String director = JOptionPane.showInputDialog(mainFrame, "Ingrese el director del DVD:");
        String anio = JOptionPane.showInputDialog(mainFrame, "Ingrese el año del DVD:");
        String duracion = JOptionPane.showInputDialog(mainFrame, "Ingrese la duración del DVD (minutos):");
        String genero = JOptionPane.showInputDialog(mainFrame, "Ingrese el género del DVD:");

        try {
            DVD dvd = new DVD(Integer.parseInt(id), titulo, director, Integer.parseInt(anio), Integer.parseInt(duracion), genero);
            if (dvdDAO.crear(dvd)) {
                JOptionPane.showMessageDialog(mainFrame, "DVD agregado correctamente.");
                cargarDatosDVDs(); // Actualizar la tabla
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Error al agregar el DVD.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame, "Por favor, ingrese valores válidos.");
        }
    }

    private void editarDVD() {
        JOptionPane.showMessageDialog(mainFrame, "Funcionalidad de edición de DVDs aún no implementada.");
    }

    private void eliminarDVD() {
        int selectedRow = mainFrame.getTablaDVDs().getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) mainFrame.getTablaDVDs().getValueAt(selectedRow, 0);
            if (dvdDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(mainFrame, "DVD eliminado correctamente.");
                cargarDatosDVDs(); // Actualizar la tabla
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Error al eliminar el DVD.");
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Por favor, seleccione un DVD para eliminar.");
        }
    }

    private void buscarDVDs() {
        String genero = mainFrame.getTxtBuscarDVDs().getText();
        DefaultTableModel model = (DefaultTableModel) mainFrame.getTablaDVDs().getModel();
        model.setRowCount(0); // Limpiar la tabla
        List<?> dvds = dvdDAO.obtenerTodos();
        for (Object obj : dvds) {
            if (obj instanceof DVD) {
                DVD dvd = (DVD) obj;
                if (dvd.getGenero().toLowerCase().contains(genero.toLowerCase())) {
                    model.addRow(new Object[]{dvd.getId(), dvd.getTitulo(), dvd.getDirector(), dvd.getAnio(), dvd.getDuracion(), dvd.getGenero()});
                }
            }
        }
    }
}