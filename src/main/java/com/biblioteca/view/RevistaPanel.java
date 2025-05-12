package com.biblioteca.view;

import com.biblioteca.controller.RevistaController;
import com.biblioteca.model.Revista;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RevistaPanel extends JPanel {
    private final RevistaController controller = new RevistaController();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public RevistaPanel() {
        setLayout(new BorderLayout());

        // Tabla
        String[] columnNames = {"ID", "Título", "Autor", "Año", "Edición", "Categoría"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        String[] acciones = {"Agregar", "Editar", "Eliminar", "Actualizar", "Filtrar por Categoría"};
        for (String accion : acciones) {
            JButton btn = new JButton(accion);
            btn.addActionListener(e -> manejarAccion(accion));
            buttonPanel.add(btn);
        }
        add(buttonPanel, BorderLayout.SOUTH);

        cargarDatos();
    }

    private void manejarAccion(String accion) {
        switch (accion) {
            case "Agregar":
                new RevistaForm(null).mostrarDialogo().ifPresent(controller::agregarRevista);
                break;
            case "Editar":
                int row = table.getSelectedRow();
                if (row >= 0) {
                    Revista revista = getRevistaFromRow(row);
                    new RevistaForm(revista).mostrarDialogo().ifPresent(controller::actualizarRevista);
                } else {
                    JOptionPane.showMessageDialog(this, "Seleccione una revista", "Error", JOptionPane.WARNING_MESSAGE);
                }
                break;
            case "Filtrar por Categoría":
                String categoria = JOptionPane.showInputDialog(this, "Ingrese categoría:");
                if (categoria != null && !categoria.isEmpty()) {
                    actualizarTabla(controller.buscarRevistasPorCategoria(categoria));
                }
                break;
            case "Eliminar":
                if (table.getSelectedRow() >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar registro?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        controller.eliminarRevista((int) tableModel.getValueAt(table.getSelectedRow(), 0));
                    }
                }
                break;
        }
        cargarDatos();
    }

    private void cargarDatos() {
        actualizarTabla(controller.obtenerTodasLasRevistas());
    }

    private void actualizarTabla(List<Revista> revistas) {
        tableModel.setRowCount(0);
        revistas.forEach(r -> tableModel.addRow(new Object[]{
                r.getId(),
                r.getTitulo(),
                r.getAutor(),
                r.getAnioPublicacion(),
                r.getNumeroEdicion(),
                r.getCategoria()
        }));
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