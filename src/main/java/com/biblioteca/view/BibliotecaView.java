package com.biblioteca.view;

import com.biblioteca.controller.BibliotecaController;
import com.biblioteca.model.ElementoBiblioteca;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Revista;
import com.biblioteca.model.DVD;
import com.biblioteca.util.BibliotecaException;
import com.biblioteca.util.Logger;
import com.biblioteca.util.UIConfig;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class BibliotecaView extends JFrame {
    private BibliotecaController controller;
    private JTabbedPane tabbedPane;
    private DefaultTableModel librosModel, revistasModel, dvdsModel;
    private JTextField searchField;
    private JTable librosTable, revistasTable, dvdsTable;

    public BibliotecaView() {
        controller = new BibliotecaController();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setTitle("Sistema de Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel superior con botones
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnCatalogo = new JButton("Catálogo");
        JButton btnAyuda = new JButton("Ayuda");

        UIConfig.configureButton(btnCatalogo);
        UIConfig.configureButton(btnAyuda);

        topButtonPanel.add(btnCatalogo);
        topButtonPanel.add(btnAyuda);
        mainPanel.add(topButtonPanel, BorderLayout.NORTH);

        // Configuración de pestañas
        tabbedPane = new JTabbedPane();
        String[] columnNames = {"Título", "Autor", "Año", "Duración", "Género"};

        librosModel = new DefaultTableModel(columnNames, 0);
        revistasModel = new DefaultTableModel(columnNames, 0);
        dvdsModel = new DefaultTableModel(columnNames, 0);

        librosTable = new JTable(librosModel);
        revistasTable = new JTable(revistasModel);
        dvdsTable = new JTable(dvdsModel);

        tabbedPane.addTab("Libros", createTabPanel(librosTable));
        tabbedPane.addTab("Revistas", createTabPanel(revistasTable));
        tabbedPane.addTab("DVDs", createTabPanel(dvdsTable));

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JPanel createTabPanel(JTable table) {
        JPanel panel = new JPanel(new BorderLayout());
        UIConfig.configureTable(table);

        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        UIConfig.configureTextField(searchField);
        UIConfig.configureButton(btnBuscar);
        UIConfig.configureButton(btnAgregar);
        UIConfig.configureButton(btnEditar);
        UIConfig.configureButton(btnEliminar);
        UIConfig.configureButton(btnActualizar);

        searchPanel.add(new JLabel("Buscar por género:"));
        searchPanel.add(searchField);
        searchPanel.add(btnBuscar);
        searchPanel.add(btnAgregar);
        searchPanel.add(btnEditar);
        searchPanel.add(btnEliminar);
        searchPanel.add(btnActualizar);

        // Configurar acciones
        btnBuscar.addActionListener(this::buscarPorGenero);
        btnAgregar.addActionListener(this::agregarElemento);
        btnEditar.addActionListener(this::editarElemento);
        btnEliminar.addActionListener(this::eliminarElemento);
        btnActualizar.addActionListener(e -> loadData());

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    private void loadData() {
        clearAllTables();
        try {
            List<ElementoBiblioteca> elementos = controller.obtenerTodos();
            if (elementos != null) {
                for (ElementoBiblioteca elemento : elementos) {
                    addToTable(elemento);
                }
            }
        } catch (BibliotecaException e) {
            handleError("Error al cargar datos", e);
        }
    }

    private void clearAllTables() {
        librosModel.setRowCount(0);
        revistasModel.setRowCount(0);
        dvdsModel.setRowCount(0);
    }

    private void addToTable(ElementoBiblioteca elemento) {
        Object[] rowData = getRowData(elemento);
        if (elemento instanceof Libro) {
            librosModel.addRow(rowData);
        } else if (elemento instanceof Revista) {
            revistasModel.addRow(rowData);
        } else if (elemento instanceof DVD) {
            dvdsModel.addRow(rowData);
        }
    }

    private Object[] getRowData(ElementoBiblioteca elemento) {
        if (elemento instanceof Libro) {
            Libro libro = (Libro) elemento;
            return new Object[]{
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getAnoPublicacion(),
                    libro.getNumeroPaginas() + " págs",
                    libro.getGenero()
            };
        } else if (elemento instanceof Revista) {
            Revista revista = (Revista) elemento;
            return new Object[]{
                    revista.getTitulo(),
                    revista.getAutor(),
                    revista.getAnoPublicacion(),
                    "-",
                    revista.getCategoria()
            };
        } else if (elemento instanceof DVD) {
            DVD dvd = (DVD) elemento;
            return new Object[]{
                    dvd.getTitulo(),
                    dvd.getAutor(),
                    dvd.getAnoPublicacion(),
                    dvd.getDuracion() + " mins",
                    dvd.getGenero()
            };
        }
        return new Object[]{};
    }

    private void buscarPorGenero(ActionEvent e) {
        String genero = searchField.getText().trim();
        if (genero.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un género para buscar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        clearAllTables();
        try {
            List<ElementoBiblioteca> elementos = controller.obtenerTodos();
            if (elementos != null) {
                for (ElementoBiblioteca elemento : elementos) {
                    if (matchesGenre(elemento, genero)) {
                        addToTable(elemento);
                    }
                }
            }
        } catch (BibliotecaException ex) {
            handleError("Error al buscar elementos", ex);
        }
    }

    private boolean matchesGenre(ElementoBiblioteca elemento, String genero) {
        if (elemento instanceof Libro) {
            return ((Libro) elemento).getGenero().equalsIgnoreCase(genero);
        } else if (elemento instanceof Revista) {
            return ((Revista) elemento).getCategoria().equalsIgnoreCase(genero);
        } else if (elemento instanceof DVD) {
            return ((DVD) elemento).getGenero().equalsIgnoreCase(genero);
        }
        return false;
    }
    private void agregarElemento(ActionEvent e) {
        // Implementar lógica de agregar
        JOptionPane.showMessageDialog(this, "Funcionalidad de agregar no implementada aún");
    }

    private void editarElemento(ActionEvent e) {
        JTable currentTable = getCurrentTable();
        if (currentTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un elemento", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String titulo = (String) currentTable.getValueAt(currentTable.getSelectedRow(), 0);
        JOptionPane.showMessageDialog(this, "Editando: " + titulo);
    }

    private void eliminarElemento(ActionEvent e) {
        JTable currentTable = getCurrentTable();
        if (currentTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un elemento", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String titulo = (String) currentTable.getValueAt(currentTable.getSelectedRow(), 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar '" + titulo + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controller.eliminarPorTitulo(titulo);
                loadData();
            } catch (BibliotecaException ex) {
                handleError("Error al eliminar", ex);
            }
        }
    }

    private JTable getCurrentTable() {
        int index = tabbedPane.getSelectedIndex();
        switch (index) {
            case 0:
                return librosTable;
            case 1:
                return revistasTable;
            case 2:
                return dvdsTable;
            default:
                return null;
        }
    }

    private void handleError(String message, Exception e) {
        Logger.logError(message, e);
        JOptionPane.showMessageDialog(this,
                message + ": " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrar() {
        EventQueue.invokeLater(() -> setVisible(true));
    }
}