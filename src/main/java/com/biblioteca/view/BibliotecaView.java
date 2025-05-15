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
import java.util.List;

public class BibliotecaView extends JFrame {
    private BibliotecaController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton btnBuscar, btnAgregar, btnEditar, btnEliminar, btnActualizar;

    public BibliotecaView() {
        controller = new BibliotecaController();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setTitle("Sistema de Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        UIConfig.configureFrame(this);

        // Panel superior para búsqueda
        JPanel searchPanel = new JPanel();
        UIConfig.configurePanel(searchPanel);
        JLabel lblBuscar = new JLabel("Buscar por género:");
        UIConfig.configureLabel(lblBuscar);
        searchField = new JTextField(20);
        UIConfig.configureTextField(searchField);
        btnBuscar = new JButton("Buscar");
        UIConfig.configureButton(btnBuscar);
        searchPanel.add(lblBuscar);
        searchPanel.add(searchField);
        searchPanel.add(btnBuscar);
        add(searchPanel, BorderLayout.NORTH);

        // Tabla
        String[] columnNames = {"ID", "Título", "Autor", "Año", "Duración/Páginas", "Género/Categoría", "Editorial/Edición"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        UIConfig.configureTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel inferior para botones
        JPanel buttonPanel = new JPanel();
        UIConfig.configurePanel(buttonPanel);
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");
        UIConfig.configureButton(btnAgregar);
        UIConfig.configureButton(btnEditar);
        UIConfig.configureButton(btnEliminar);
        UIConfig.configureButton(btnActualizar);
        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnActualizar);
        add(buttonPanel, BorderLayout.SOUTH);

        // Acción de botones
        btnBuscar.addActionListener(e -> buscarPorGenero());
        btnAgregar.addActionListener(e -> agregarElemento());
        btnEditar.addActionListener(e -> editarElemento());
        btnEliminar.addActionListener(e -> eliminarElemento());
        btnActualizar.addActionListener(e -> loadData());

        setSize(800, 400);
        setLocationRelativeTo(null);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<ElementoBiblioteca> elementos = controller.obtenerTodos();
            if (elementos == null) {
                Logger.logError("Error al cargar los datos en la vista", new Exception("Datos nulos"));
                JOptionPane.showMessageDialog(this, "Error al cargar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (ElementoBiblioteca elemento : elementos) {
                Object[] row = getRowData(elemento);
                tableModel.addRow(row);
            }
            Logger.logInfo("Datos cargados en la vista. Total de elementos: " + elementos.size());
        } catch (BibliotecaException e) {
            Logger.logError("Error al cargar los datos en la vista", e);
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Object[] getRowData(ElementoBiblioteca elemento) {
        if (elemento instanceof Libro) {
            Libro libro = (Libro) elemento;
            return new Object[]{
                    libro.getId(), libro.getTitulo(), libro.getAutor(), libro.getAnoPublicacion(),
                    libro.getNumeroPaginas(), libro.getGenero(), libro.getEditorial()
            };
        } else if (elemento instanceof Revista) {
            Revista revista = (Revista) elemento;
            return new Object[]{
                    revista.getId(), revista.getTitulo(), revista.getAutor(), revista.getAnoPublicacion(),
                    "-", revista.getCategoria(), revista.getNumeroEdicion()
            };
        } else if (elemento instanceof DVD) {
            DVD dvd = (DVD) elemento;
            return new Object[]{
                    dvd.getId(), dvd.getTitulo(), dvd.getAutor(), dvd.getAnoPublicacion(),
                    dvd.getDuracion(), dvd.getGenero(), "-"
            };
        }
        return new Object[]{};
    }

    private void buscarPorGenero() {
        String genero = searchField.getText();
        tableModel.setRowCount(0);
        try {
            List<ElementoBiblioteca> elementos = controller.obtenerTodos();
            if (elementos == null) {
                Logger.logError("Error al cargar los datos para la búsqueda por género", new Exception("Datos nulos"));
                JOptionPane.showMessageDialog(this, "Error al cargar los datos para la búsqueda.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int count = 0;
            for (ElementoBiblioteca elemento : elementos) {
                if (elemento instanceof Libro && ((Libro) elemento).getGenero().equalsIgnoreCase(genero)) {
                    tableModel.addRow(getRowData(elemento));
                    count++;
                } else if (elemento instanceof DVD && ((DVD) elemento).getGenero().equalsIgnoreCase(genero)) {
                    tableModel.addRow(getRowData(elemento));
                    count++;
                }
            }
            Logger.logInfo("Búsqueda por género '" + genero + "' realizada. Elementos encontrados: " + count);
        } catch (BibliotecaException e) {
            Logger.logError("Error al buscar por género: " + genero, e);
            JOptionPane.showMessageDialog(this, "Error al buscar por género: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarElemento() {
        InputForm form = new InputForm(this, "Agregar Elemento", null);
        form.setVisible(true);
        if (form.isConfirmed()) {
            try {
                String tipo = form.getTipo();
                String titulo = form.getTitulo();
                String autor = form.getAutor();
                int ano = form.getAnoPublicacion();

                if ("Libro".equalsIgnoreCase(tipo)) {
                    String isbn = form.getIsbn();
                    int paginas = form.getNumeroPaginas();
                    String genero = form.getGeneroLibro();
                    String editorial = form.getEditorial();
                    Libro libro = controller.crearLibro(titulo, autor, ano, tipo, isbn, paginas, genero, editorial);
                    controller.agregarElemento(libro);
                } else if ("Revista".equalsIgnoreCase(tipo)) {
                    int edicion = form.getNumeroEdicion();
                    String categoria = form.getCategoria();
                    Revista revista = controller.crearRevista(titulo, autor, ano, tipo, edicion, categoria);
                    controller.agregarElemento(revista);
                } else if ("DVD".equalsIgnoreCase(tipo)) {
                    int duracion = form.getDuracion();
                    String genero = form.getGeneroDVD();
                    DVD dvd = controller.crearDVD(titulo, autor, ano, tipo, duracion, genero);
                    controller.agregarElemento(dvd);
                }
                loadData();
                Logger.logInfo("Elemento agregado desde la vista.");
            } catch (NumberFormatException e) {
                Logger.logError("Error al agregar elemento: valor numérico inválido", e);
                JOptionPane.showMessageDialog(this, "Error: Ingresa un valor numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (BibliotecaException e) {
                Logger.logError("Error al agregar elemento desde la vista", e);
                JOptionPane.showMessageDialog(this, "Error al agregar elemento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarElemento() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                ElementoBiblioteca elemento = controller.buscarElemento(id);
                if (elemento != null) {
                    InputForm form = new InputForm(this, "Editar Elemento", elemento);
                    form.setVisible(true);
                    if (form.isConfirmed()) {
                        elemento.setTitulo(form.getTitulo());
                        elemento.setAutor(form.getAutor());
                        elemento.setAnoPublicacion(form.getAnoPublicacion());

                        if (elemento instanceof Libro) {
                            Libro libro = (Libro) elemento;
                            libro.setIsbn(form.getIsbn());
                            libro.setNumeroPaginas(form.getNumeroPaginas());
                            libro.setGenero(form.getGeneroLibro());
                            libro.setEditorial(form.getEditorial());
                        } else if (elemento instanceof Revista) {
                            Revista revista = (Revista) elemento;
                            revista.setNumeroEdicion(form.getNumeroEdicion());
                            revista.setCategoria(form.getCategoria());
                        } else if (elemento instanceof DVD) {
                            DVD dvd = (DVD) elemento;
                            dvd.setDuracion(form.getDuracion());
                            dvd.setGenero(form.getGeneroDVD());
                        }
                        controller.actualizarElemento(elemento);
                        loadData();
                        Logger.logInfo("Elemento editado desde la vista. ID: " + id);
                    }
                } else {
                    Logger.logError("Error al encontrar el elemento con ID: " + id, new Exception("Elemento no encontrado"));
                    JOptionPane.showMessageDialog(this, "Error al encontrar el elemento.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                Logger.logError("Error al editar elemento con ID: " + id + ": valor numérico inválido", e);
                JOptionPane.showMessageDialog(this, "Error: Ingresa un valor numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (BibliotecaException e) {
                Logger.logError("Error al editar elemento con ID: " + id, e);
                JOptionPane.showMessageDialog(this, "Error al editar elemento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            Logger.logInfo("Intento de edición fallido: no se seleccionó ningún elemento");
            JOptionPane.showMessageDialog(this, "Seleccione un elemento para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarElemento() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este elemento?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    controller.eliminarElemento(id);
                    loadData();
                    Logger.logInfo("Elemento eliminado desde la vista. ID: " + id);
                } catch (BibliotecaException e) {
                    Logger.logError("Error al eliminar elemento con ID: " + id, e);
                    JOptionPane.showMessageDialog(this, "Error al eliminar elemento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            Logger.logInfo("Intento de eliminación fallido: no se seleccionó ningún elemento");
            JOptionPane.showMessageDialog(this, "Seleccione un elemento para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}