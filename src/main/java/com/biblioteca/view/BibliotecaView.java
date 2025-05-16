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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BibliotecaView extends JFrame {
    private BibliotecaController controller;
    private JTabbedPane tabbedPane;
    private DefaultTableModel librosModel, revistasModel, dvdsModel;
    private Map<Integer, JTextField> searchFields; // Mapa para almacenar un JTextField por pestaña
    private Map<Integer, JTable> tables; // Mapa para almacenar las tablas por pestaña
    private JTable librosTable, revistasTable, dvdsTable;

    public BibliotecaView() {
        controller = new BibliotecaController();
        searchFields = new HashMap<>();
        tables = new HashMap<>();
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

        // Columnas específicas por pestaña
        String[] librosColumns = {"Título", "Autor", "Año", "Páginas", "Género", "Editorial", "ISBN"};
        String[] revistasColumns = {"Título", "Autor", "Año", "Edición", "Categoría"};
        String[] dvdsColumns = {"Título", "Autor", "Año", "Duración"}; // Eliminada la columna "Género"

        librosModel = new DefaultTableModel(librosColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilitar edición directa en la tabla
            }
        };
        revistasModel = new DefaultTableModel(revistasColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilitar edición directa en la tabla
            }
        };
        dvdsModel = new DefaultTableModel(dvdsColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilitar edición directa en la tabla
            }
        };

        librosTable = new JTable(librosModel);
        revistasTable = new JTable(revistasModel);
        dvdsTable = new JTable(dvdsModel);

        // Deshabilitar comportamiento predeterminado de edición con doble clic
        librosTable.setDefaultEditor(Object.class, null);
        revistasTable.setDefaultEditor(Object.class, null);
        dvdsTable.setDefaultEditor(Object.class, null);

        tables.put(0, librosTable);
        tables.put(1, revistasTable);
        tables.put(2, dvdsTable);

        UIConfig.configureTable(librosTable);
        UIConfig.configureTable(revistasTable);
        UIConfig.configureTable(dvdsTable);

        tabbedPane.addTab("Libros", createTabPanel(librosTable, 0));
        tabbedPane.addTab("Revistas", createTabPanel(revistasTable, 1));
        tabbedPane.addTab("DVDs", createTabPanel(dvdsTable, 2));

        // Actualizar datos al cambiar de pestaña y limpiar searchField
        tabbedPane.addChangeListener(e -> {
            int selectedTab = tabbedPane.getSelectedIndex();
            JTextField currentSearchField = searchFields.get(selectedTab);
            if (currentSearchField != null) {
                currentSearchField.setText("");
            }
            loadData(); // Recargar datos al cambiar de pestaña
        });

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JPanel createTabPanel(JTable table, int tabIndex) {
        JPanel panel = new JPanel(new BorderLayout());
        UIConfig.configureTable(table);

        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField tabSearchField = new JTextField(20);
        tabSearchField.setEditable(true);
        tabSearchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                System.out.println("Texto actual en searchField (pestaña " + tabIndex + "): '" + tabSearchField.getText() + "'");
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                System.out.println("Texto actual en searchField (pestaña " + tabIndex + "): '" + tabSearchField.getText() + "'");
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                System.out.println("Texto actual en searchField (pestaña " + tabIndex + "): '" + tabSearchField.getText() + "'");
            }
        });
        searchFields.put(tabIndex, tabSearchField);

        JButton btnBuscar = new JButton("Buscar");
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        UIConfig.configureTextField(tabSearchField);
        UIConfig.configureButton(btnBuscar);
        UIConfig.configureButton(btnAgregar);
        UIConfig.configureButton(btnEditar);
        UIConfig.configureButton(btnEliminar);
        UIConfig.configureButton(btnActualizar);

        searchPanel.add(new JLabel("Buscar por género:"));
        searchPanel.add(tabSearchField);
        searchPanel.add(btnBuscar);
        searchPanel.add(btnAgregar);
        searchPanel.add(btnEditar);
        searchPanel.add(btnEliminar);
        searchPanel.add(btnActualizar);

        // Configurar acciones
        btnBuscar.addActionListener(e -> {
            System.out.println("Botón Buscar clicado en pestaña " + tabIndex);
            buscarPorGenero();
            // Limpiar el campo después de buscar
            tabSearchField.setText("");
        });
        btnAgregar.addActionListener(this::agregarElemento);
        btnEditar.addActionListener(e -> editarElemento());
        btnEliminar.addActionListener(this::eliminarElemento);
        btnActualizar.addActionListener(e -> loadData());

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    private void loadData() {
        int selectedTab = tabbedPane.getSelectedIndex();
        JTable currentTable = tables.get(selectedTab);
        if (currentTable != null) {
            clearTableModel(currentTable);
            Logger.logInfo("Cargando datos para la pestaña: " + selectedTab);
        }
        try {
            List<ElementoBiblioteca> elementos = controller.obtenerTodos();
            Logger.logInfo("Total de elementos cargados: " + (elementos != null ? elementos.size() : 0));
            if (elementos != null) {
                for (ElementoBiblioteca elemento : elementos) {
                    addToTable(elemento);
                }
            }
        } catch (BibliotecaException e) {
            handleError("Error al cargar datos", e);
        }
    }

    private void clearTableModel(JTable table) {
        if (table == librosTable) {
            librosModel.setRowCount(0);
            Logger.logInfo("Tabla de libros limpiada");
        } else if (table == revistasTable) {
            revistasModel.setRowCount(0);
            Logger.logInfo("Tabla de revistas limpiada");
        } else if (table == dvdsTable) {
            dvdsModel.setRowCount(0);
            Logger.logInfo("Tabla de DVDs limpiada");
        }
    }

    private void addToTable(ElementoBiblioteca elemento) {
        int selectedTab = tabbedPane.getSelectedIndex();
        JTable currentTable = tables.get(selectedTab);
        if (currentTable != null) {
            Object[] rowData = getRowData(elemento);
            if (currentTable == librosTable && elemento instanceof Libro) {
                librosModel.addRow(rowData);
                Logger.logInfo("Añadido a tabla de libros: " + elemento.getTitulo());
            } else if (currentTable == revistasTable && elemento instanceof Revista) {
                revistasModel.addRow(rowData);
                Logger.logInfo("Añadido a tabla de revistas: " + elemento.getTitulo());
            } else if (currentTable == dvdsTable && elemento instanceof DVD) {
                dvdsModel.addRow(rowData);
                Logger.logInfo("Añadido a tabla de DVDs: " + elemento.getTitulo());
            }
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
                    libro.getGenero(),
                    libro.getEditorial(),
                    libro.getIsbn()
            };
        } else if (elemento instanceof Revista) {
            Revista revista = (Revista) elemento;
            return new Object[]{
                    revista.getTitulo(),
                    revista.getAutor(),
                    revista.getAnoPublicacion(),
                    revista.getNumeroEdicion(),
                    revista.getCategoria()
            };
        } else if (elemento instanceof DVD) {
            DVD dvd = (DVD) elemento;
            return new Object[]{
                    dvd.getTitulo(),
                    dvd.getAutor(),
                    dvd.getAnoPublicacion(),
                    dvd.getDuracion() + " mins"
            };
        }
        return new Object[]{};
    }

    private void buscarPorGenero() {
        int selectedTab = tabbedPane.getSelectedIndex();
        JTextField currentSearchField = searchFields.get(selectedTab);
        JTable currentTable = tables.get(selectedTab);
        if (currentSearchField == null || currentTable == null) {
            JOptionPane.showMessageDialog(this, "Error: No se encontró el campo o tabla de búsqueda para esta pestaña", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        currentSearchField.requestFocusInWindow();
        String genero = currentSearchField.getText() != null ? currentSearchField.getText().trim() : "";
        System.out.println("Valor capturado en searchField (pestaña " + selectedTab + "): '" + genero + "'");

        if (genero.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un género para buscar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        clearTableModel(currentTable);
        try {
            List<ElementoBiblioteca> elementos = controller.buscarPorGenero(genero);
            if (elementos != null && !elementos.isEmpty()) {
                for (ElementoBiblioteca elemento : elementos) {
                    addToTable(elemento);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron elementos para el género: " + genero, "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (BibliotecaException ex) {
            handleError("Error al buscar elementos", ex);
        }
        // Limpiar el campo después de buscar
        currentSearchField.setText("");
    }

    private void agregarElemento(ActionEvent e) {
        try {
            int selectedTab = tabbedPane.getSelectedIndex();
            String tipo;
            switch (selectedTab) {
                case 0:
                    tipo = "Libro";
                    break;
                case 1:
                    tipo = "Revista";
                    break;
                case 2:
                    tipo = "DVD";
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Seleccione una pestaña válida", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            InputForm form = new InputForm(this, "Agregar " + tipo, null);
            form.setVisible(true);

            if (form.isConfirmed()) {
                ElementoBiblioteca elemento = null;

                String titulo = form.getTitulo();
                String autor = form.getAutor();
                int anoPublicacion = form.getAnoPublicacion();

                switch (tipo) {
                    case "Libro":
                        String isbn = form.getIsbn();
                        int numeroPaginas = form.getNumeroPaginas();
                        String generoLibro = form.getGeneroLibro();
                        String editorial = form.getEditorial();
                        elemento = controller.crearLibro(titulo, autor, anoPublicacion, isbn, numeroPaginas, generoLibro, editorial);
                        break;
                    case "Revista":
                        int numeroEdicion = form.getNumeroEdicion();
                        String categoria = form.getCategoria();
                        elemento = controller.crearRevista(titulo, autor, anoPublicacion, numeroEdicion, categoria);
                        break;
                    case "DVD":
                        int duracion = form.getDuracion();
                        String generoDVD = form.getGeneroDVD();
                        elemento = controller.crearDVD(titulo, autor, anoPublicacion, duracion, generoDVD);
                        break;
                }

                controller.agregarElemento(elemento);
                JOptionPane.showMessageDialog(this, "Elemento agregado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            }
        } catch (BibliotecaException ex) {
            handleError("Error al agregar elemento", ex);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarElemento() {
        int selectedTab = tabbedPane.getSelectedIndex();
        JTable currentTable = tables.get(selectedTab);
        if (currentTable == null || currentTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un elemento para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tipo;
        switch (selectedTab) {
            case 0:
                tipo = "Libro";
                break;
            case 1:
                tipo = "Revista";
                break;
            case 2:
                tipo = "DVD";
                break;
            default:
                JOptionPane.showMessageDialog(this, "Seleccione una pestaña válida", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        }

        try {
            String titulo = (String) currentTable.getValueAt(currentTable.getSelectedRow(), 0);
            ElementoBiblioteca elementoOriginal = controller.buscarPorTitulo(titulo);
            Logger.logInfo("ID del elemento original antes de editar: " + elementoOriginal.getId());

            if (elementoOriginal == null || elementoOriginal.getId() <= 0) {
                JOptionPane.showMessageDialog(this, "Error: No se pudo cargar el elemento para edición", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Abrir el formulario de edición con los datos actuales
            InputForm form = new InputForm(this, "Editar " + tipo, elementoOriginal);
            form.setVisible(true);

            if (form.isConfirmed()) {
                // Preservar el ID original
                int idOriginal = elementoOriginal.getId();
                ElementoBiblioteca elementoActualizado = elementoOriginal; // Usar la misma instancia para mantener el ID

                // Actualizar los datos del elemento según el tipo
                elementoActualizado.setTitulo(form.getTitulo());
                elementoActualizado.setAutor(form.getAutor());
                elementoActualizado.setAnoPublicacion(form.getAnoPublicacion());

                if (elementoActualizado instanceof Libro) {
                    Libro libro = (Libro) elementoActualizado;
                    libro.setIsbn(form.getIsbn());
                    libro.setNumeroPaginas(form.getNumeroPaginas());
                    libro.setGenero(form.getGeneroLibro());
                    libro.setEditorial(form.getEditorial());
                } else if (elementoActualizado instanceof Revista) {
                    Revista revista = (Revista) elementoActualizado;
                    revista.setNumeroEdicion(form.getNumeroEdicion());
                    revista.setCategoria(form.getCategoria());
                } else if (elementoActualizado instanceof DVD) {
                    DVD dvd = (DVD) elementoActualizado;
                    dvd.setDuracion(form.getDuracion());
                    dvd.setGenero(form.getGeneroDVD()); // Nota: generoDVD podría no estar en el modelo, ajustar según necesidad
                }

                // Verificar que el ID no se haya perdido
                elementoActualizado.setId(idOriginal); // Asegurarse de que el ID se mantenga
                Logger.logInfo("ID del elemento actualizado antes de guardar: " + elementoActualizado.getId());

                // Guardar los cambios
                controller.actualizarElemento(elementoActualizado);
                JOptionPane.showMessageDialog(this, "Elemento actualizado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                loadData(); // Refrescar la tabla
            }
        } catch (BibliotecaException ex) {
            handleError("Error al editar elemento", ex);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarElemento(ActionEvent e) {
        int selectedTab = tabbedPane.getSelectedIndex();
        JTable currentTable = tables.get(selectedTab);
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
        return tables.get(index);
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