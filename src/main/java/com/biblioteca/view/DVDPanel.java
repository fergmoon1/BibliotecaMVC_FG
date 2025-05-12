package com.biblioteca.view;

import com.biblioteca.controller.DVDController;
import com.biblioteca.model.DVD;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DVDPanel extends JPanel {
    private final DVDController controller;
    private final JTable table;
    private final DefaultTableModel tableModel;

    public DVDPanel() {
        this.controller = new DVDController();
        setLayout(new BorderLayout());

        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Buscar por género:"));
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Buscar");
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // Configuración de la tabla
        String[] columnNames = {"ID", "Título", "Director", "Año", "Duración (min)", "Género"};
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

        // Eventos
        searchButton.addActionListener(e -> {
            String genero = searchField.getText();
            if (!genero.isEmpty()) {
                List<DVD> resultados = controller.buscarDVDsPorGenero(genero);
                actualizarTabla(resultados);
            }
        });

        btnAgregar.addActionListener(e -> {
            DVDForm form = new DVDForm(null);
            if (form.mostrarDialogo(this)) {
                controller.agregarDVD(form.getDVD());
                cargarDatos();
            }
        });

        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                DVD dvd = getDVDFromRow(row);
                DVDForm form = new DVDForm(dvd);
                if (form.mostrarDialogo(this)) {
                    controller.actualizarDVD(form.getDVD());
                    cargarDatos();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Seleccione un DVD para editar",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "¿Eliminar este DVD?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    controller.eliminarDVD((int) tableModel.getValueAt(row, 0));
                    cargarDatos();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Seleccione un DVD para eliminar",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> cargarDatos());

        // Cargar datos iniciales
        cargarDatos();
    }

    private void cargarDatos() {
        List<DVD> dvds = controller.obtenerTodosLosDVDs();
        actualizarTabla(dvds);
    }

    private void actualizarTabla(List<DVD> dvds) {
        tableModel.setRowCount(0);
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