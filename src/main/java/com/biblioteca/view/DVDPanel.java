package com.biblioteca.view;

import com.biblioteca.controller.DVDController;
import com.biblioteca.model.DVD;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DVDPanel extends JPanel {
    private final DVDController controller = new DVDController();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public DVDPanel() {
        setLayout(new BorderLayout());

        // Configuración de la tabla
        String[] columnNames = {"ID", "Título", "Director", "Año", "Duración", "Género"};
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
            DVDForm form = new DVDForm(null);
            if (form.mostrarDialogo(this)) {
                controller.agregarDVD(form.getDVD());
                cargarDVDs();
            }
        });

        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                DVD dvd = getDVDFromRow(row);
                DVDForm form = new DVDForm(dvd);
                if (form.mostrarDialogo(this)) {
                    controller.actualizarDVD(form.getDVD());
                    cargarDVDs();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un DVD para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Eliminar este DVD?", "Confirmar",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.eliminarDVD((int) tableModel.getValueAt(row, 0));
                    cargarDVDs();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un DVD para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> cargarDVDs());

        cargarDVDs();
    }

    private void cargarDVDs() {
        tableModel.setRowCount(0);
        List<DVD> dvds = controller.obtenerTodosLosDVDs();
        for (DVD dvd : dvds) {
            tableModel.addRow(new Object[]{
                    dvd.getId(),
                    dvd.getTitulo(),
                    dvd.getAutor(),
                    dvd.getAnioPublicacion(),
                    dvd.getDuracion(),
                    dvd.getGenero()
            });
        }
    }

    private DVD getDVDFromRow(int row) {
        return new DVD(
                (int) tableModel.getValueAt(row, 0),
                (String) tableModel.getValueAt(row, 1),
                (String) tableModel.getValueAt(row, 2),
                (int) tableModel.getValueAt(row, 3),
                (int) tableModel.getValueAt(row, 4),
                (String) tableModel.getValueAt(row, 5)
        );
    }
}