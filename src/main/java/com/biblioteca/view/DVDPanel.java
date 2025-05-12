package com.biblioteca.view;

import com.biblioteca.controller.DVDController;
import com.biblioteca.model.DVD;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class DVDPanel extends JPanel {
    private final DVDController controller = new DVDController();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public DVDPanel() {
        setLayout(new BorderLayout());

        // Tabla
        String[] columnNames = {"ID", "Título", "Autor", "Año", "Duración", "Género"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        String[] botones = {"Agregar", "Editar", "Eliminar", "Actualizar", "Buscar por Género"};
        for (String texto : botones) {
            JButton btn = new JButton(texto);
            btn.addActionListener(e -> manejarEvento(texto));
            buttonPanel.add(btn);
        }
        add(buttonPanel, BorderLayout.SOUTH);

        cargarDVDs();
    }

    private void manejarEvento(String accion) {
        switch (accion) {
            case "Agregar":
                new DVDForm(null).mostrarDialogo().ifPresent(controller::agregarDVD);
                break;
            case "Editar":
                if (table.getSelectedRow() != -1) {
                    DVD dvd = getDVDFromRow(table.getSelectedRow());
                    new DVDForm(dvd).mostrarDialogo().ifPresent(controller::actualizarDVD);
                }
                break;
            case "Buscar por Género":
                String genero = JOptionPane.showInputDialog(this, "Ingrese género:");
                if (genero != null && !genero.isEmpty()) {
                    actualizarTabla(controller.buscarDVDsPorGenero(genero));
                }
                break;
            case "Eliminar":
                if (table.getSelectedRow() != -1) {
                    if (JOptionPane.showConfirmDialog(this, "¿Eliminar DVD?", "Confirmar",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        controller.eliminarDVD((int) tableModel.getValueAt(table.getSelectedRow(), 0));
                    }
                }
                break;
        }
        cargarDVDs();
    }

    private void cargarDVDs() {
        actualizarTabla(controller.obtenerTodosLosDVDs());
    }

    private void actualizarTabla(List<DVD> dvds) {
        tableModel.setRowCount(0);
        dvds.forEach(d -> tableModel.addRow(new Object[]{
                d.getId(),
                d.getTitulo(),
                d.getAutor(),
                d.getAnioPublicacion(),
                d.getDuracion(),
                d.getGenero()
        }));
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