package com.biblioteca.view;

import com.biblioteca.controller.LibroController;
import com.biblioteca.model.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LibroPanel extends JPanel {

    private final LibroController controller = new LibroController();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public LibroPanel() {
        setLayout(new BorderLayout());

        // Tabla
        String[] columnNames = {"ID", "Título", "Autor", "Año", "ISBN", "Páginas", "Género", "Editorial"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnActualizar);
        add(buttonPanel, BorderLayout.SOUTH);

        // Eventos
        btnActualizar.addActionListener(e -> cargarLibros());

        btnAgregar.addActionListener(e -> {
            LibroForm form = new LibroForm(null);
            if (form.mostrarDialogo()) {
                controller.agregarLibro(form.getLibro());
                cargarLibros();
            }
        });

        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Libro libro = getLibroFromRow(row);
                LibroForm form = new LibroForm(libro);
                if (form.mostrarDialogo()) {
                    controller.actualizarLibro(form.getLibro());
                    cargarLibros();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un libro para editar.");
            }
        });

        btnEliminar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) tableModel.getValueAt(row, 0);
                controller.eliminarLibro(id);
                cargarLibros();
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un libro para eliminar.");
            }
        });

        // Cargar libros al iniciar
        cargarLibros();
    }

    private void cargarLibros() {
        tableModel.setRowCount(0);
        List<Libro> libros = controller.obtenerTodosLosLibros();
        for (Libro libro : libros) {
            tableModel.addRow(new Object[]{
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getAnioPublicacion(),
                    libro.getIsbn(),
                    libro.getNumeroPaginas(),
                    libro.getGenero(),
                    libro.getEditorial()
            });
        }
    }

    private Libro getLibroFromRow(int row) {
        return new Libro(
                (int) tableModel.getValueAt(row, 0),
                (String) tableModel.getValueAt(row, 1),
                (String) tableModel.getValueAt(row, 2),
                (int) tableModel.getValueAt(row, 3),
                (String) tableModel.getValueAt(row, 4),
                (int) tableModel.getValueAt(row, 5),
                (String) tableModel.getValueAt(row, 6),
                (String) tableModel.getValueAt(row, 7)
        );
    }
}
