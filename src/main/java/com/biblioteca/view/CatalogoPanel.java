package com.biblioteca.view;

import com.biblioteca.controller.*;
import com.biblioteca.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CatalogoPanel extends JPanel {
    private final LibroController libroController = new LibroController();
    private final RevistaController revistaController = new RevistaController();
    private final DVDController dvdController = new DVDController();

    private final JTable table;
    private final DefaultTableModel tableModel;

    public CatalogoPanel() {
        setLayout(new BorderLayout());

        // Configurar tabla
        String[] columnNames = {"Tipo", "ID", "Título", "Autor/Director", "Año", "Detalle 1", "Detalle 2"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de filtros
        JPanel filterPanel = new JPanel();
        JComboBox<String> tipoFilter = new JComboBox<>(new String[]{"Todos", "Libros", "Revistas", "DVDs"});
        JButton btnFiltrar = new JButton("Filtrar");

        filterPanel.add(new JLabel("Tipo:"));
        filterPanel.add(tipoFilter);
        filterPanel.add(btnFiltrar);
        add(filterPanel, BorderLayout.NORTH);

        // Eventos
        btnFiltrar.addActionListener(e -> cargarCatalogo((String) tipoFilter.getSelectedItem()));

        // Cargar todos los items inicialmente
        cargarCatalogo("Todos");
    }

    private void cargarCatalogo(String filtro) {
        tableModel.setRowCount(0);

        if (filtro.equals("Todos") || filtro.equals("Libros")) {
            cargarLibros();
        }
        if (filtro.equals("Todos") || filtro.equals("Revistas")) {
            cargarRevistas();
        }
        if (filtro.equals("Todos") || filtro.equals("DVDs")) {
            cargarDVDs();
        }
    }

    private void cargarLibros() {
        List<Libro> libros = libroController.obtenerTodosLosLibros();
        for (Libro libro : libros) {
            tableModel.addRow(new Object[]{
                    "Libro",
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getAnioPublicacion(),
                    "ISBN: " + libro.getIsbn(),
                    "Págs: " + libro.getNumeroPaginas()
            });
        }
    }

    private void cargarRevistas() {
        List<Revista> revistas = revistaController.obtenerTodasLasRevistas();
        for (Revista revista : revistas) {
            tableModel.addRow(new Object[]{
                    "Revista",
                    revista.getId(),
                    revista.getTitulo(),
                    revista.getAutor(),
                    revista.getAnioPublicacion(),
                    "Edición: " + revista.getNumeroEdicion(),
                    "Categoría: " + revista.getCategoria()
            });
        }
    }

    private void cargarDVDs() {
        List<DVD> dvds = dvdController.obtenerTodosLosDVDs();
        for (DVD dvd : dvds) {
            tableModel.addRow(new Object[]{
                    "DVD",
                    dvd.getId(),
                    dvd.getTitulo(),
                    dvd.getAutor(),
                    dvd.getAnioPublicacion(),
                    "Duración: " + dvd.getDuracion() + " min",
                    "Género: " + dvd.getGenero()
            });
        }
    }
}
