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
        btnAgregar.addActionListener(e -> {
            RevistaForm form = new RevistaForm(null);
            if (form.mostrarDialogo(this)) {
                controller.agregarRevista(form.getRevista());
                cargarRevistas();
            }
        });

        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Revista revista = getRevistaFromRow(row);
                RevistaForm form = new RevistaForm(revista);
                if (form.mostrarDialogo(this)) {
                    controller.actualizarRevista(form.getRevista());
                    cargarRevistas();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una revista para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Eliminar esta revista?", "Confirmar",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.eliminarRevista((int) tableModel.getValueAt(row, 0));
                    cargarRevistas();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una revista para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> cargarRevistas());

        cargarRevistas();
    }

    private void cargarRevistas() {
        tableModel.setRowCount(0);
        List<Revista> revistas = controller.obtenerTodasLasRevistas();
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