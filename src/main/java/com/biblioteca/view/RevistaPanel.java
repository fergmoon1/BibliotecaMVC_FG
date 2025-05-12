package com.biblioteca.view;

import com.biblioteca.controller.RevistaController;
import com.biblioteca.model.Revista;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RevistaPanel extends JPanel {
    private final RevistaController controller;
    private final JTable table;
    private final DefaultTableModel tableModel;

    public RevistaPanel() {
        this.controller = new RevistaController();
        setLayout(new BorderLayout());

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnActualizar);
        add(buttonPanel, BorderLayout.SOUTH);

        // Configuración de la tabla
        String[] columnNames = {"ID", "Título", "Editor", "Año", "Edición", "Categoría"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Eventos
        btnAgregar.addActionListener(e -> agregarRevista());
        btnEditar.addActionListener(e -> editarRevista());
        btnEliminar.addActionListener(e -> eliminarRevista());
        btnActualizar.addActionListener(e -> cargarDatos());

        cargarDatos();
    }

    private void agregarRevista() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        RevistaForm form = new RevistaForm(null);
        if (form.mostrarDialogo()) { // Cambio aquí: sin parámetro
            Revista nuevaRevista = form.getRevista();
            if (nuevaRevista != null) {
                controller.agregarRevista(nuevaRevista);
                cargarDatos();
            }
        }
    }

    private void editarRevista() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            Revista revistaSeleccionada = getRevistaFromRow(row);
            RevistaForm form = new RevistaForm(revistaSeleccionada);

            if (form.mostrarDialogo()) { // Cambio aquí: sin parámetro
                Revista revistaEditada = form.getRevista();
                if (revistaEditada != null) {
                    controller.actualizarRevista(revistaEditada);
                    cargarDatos();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una revista para editar",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarRevista() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Eliminar esta revista?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                int idRevista = (int) tableModel.getValueAt(row, 0);
                controller.eliminarRevista(idRevista);
                cargarDatos();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una revista para eliminar",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cargarDatos() {
        List<Revista> revistas = controller.obtenerTodasLasRevistas();
        actualizarTabla(revistas);
    }

    private void actualizarTabla(List<Revista> revistas) {
        tableModel.setRowCount(0);
        for (Revista revista : revistas) {
            tableModel.addRow(new Object[]{
                    revista.getId(),
                    revista.getTitulo(),
                    revista.getAutor(),
                    revista.getAnioPublicacion(),
                    revista.getNumeroEdicion(),
                    revista.getCategoria()
            });
        }
    }

    private Revista getRevistaFromRow(int row) {
        return new Revista(
                (int) tableModel.getValueAt(row, 0),
                (String) tableModel.getValueAt(row, 1),
                (String) tableModel.getValueAt(row, 2),
                (int) tableModel.getValueAt(row, 3),
                (int) tableModel.getValueAt(row, 4),
                (String) tableModel.getValueAt(row, 5)
        );
    }
}