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
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class BibliotecaView extends JFrame {
    private BibliotecaController controller;
    private JTabbedPane tabbedPane;
    private DefaultTableModel librosModel, revistasModel, dvdsModel;
    private Map<Integer, Map<String, JTextField>> searchFields; // Mapa para almacenar campos de búsqueda por pestaña
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
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel superior con botones
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnCatalogo = new JButton("Catálogo");
        JButton btnAyuda = new JButton("Ayuda");

        UIConfig.configureButton(btnCatalogo);
        UIConfig.configureButton(btnAyuda);

        // Acción para el botón Catálogo
        btnCatalogo.addActionListener(e -> mostrarCatalogo());

        // Acción para el botón Ayuda
        btnAyuda.addActionListener(e -> mostrarAyuda());

        topButtonPanel.add(btnCatalogo);
        topButtonPanel.add(btnAyuda);
        mainPanel.add(topButtonPanel, BorderLayout.NORTH);

        // Configuración de pestañas
        tabbedPane = new JTabbedPane();

        // Columnas específicas por pestaña
        String[] librosColumns = {"Título", "Autor", "Año", "Páginas", "Género", "Editorial", "ISBN"};
        String[] revistasColumns = {"Título", "Autor", "Año", "Edición", "Categoría"};
        String[] dvdsColumns = {"Título", "Autor", "Año", "Duración"};

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

        // Actualizar datos al cambiar de pestaña y limpiar searchFields
        tabbedPane.addChangeListener(e -> {
            int selectedTab = tabbedPane.getSelectedIndex();
            Map<String, JTextField> currentFields = getSearchFieldsForTab(selectedTab);
            if (currentFields != null) {
                currentFields.get("title").setText("");
                currentFields.get("author").setText("");
                currentFields.get("genre").setText("");
            }
            loadData(); // Recargar datos al cambiar de pestaña
        });

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Panel inferior con botón Salir destacado
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSalir = new JButton("Salir");

        // Configurar estilo destacado para el botón Salir
        UIConfig.configureButton(btnSalir);
        btnSalir.setPreferredSize(new Dimension(150, 40)); // Tamaño fijo
        btnSalir.setBackground(new Color(255, 69, 58)); // Fondo rojo
        btnSalir.setForeground(Color.WHITE); // Texto blanco
        btnSalir.setFont(new Font("Arial", Font.BOLD, 16)); // Fuente más grande y en negrita

        // Acción para el botón Salir
        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // Cierra la aplicación de forma segura
            }
        });

        bottomButtonPanel.add(btnSalir);
        mainPanel.add(bottomButtonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void mostrarCatalogo() {
        // Crear una ventana modal para el catálogo
        JDialog catalogoDialog = new JDialog(this, "Catálogo Completo", true);
        catalogoDialog.setSize(800, 500);
        catalogoDialog.setLocationRelativeTo(this);
        catalogoDialog.setLayout(new BorderLayout());

        // Definir columnas para la tabla del catálogo
        String[] catalogoColumns = {"Tipo", "Título", "Autor", "Año"};
        DefaultTableModel catalogoModel = new DefaultTableModel(catalogoColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable catalogoTable = new JTable(catalogoModel);
        UIConfig.configureTable(catalogoTable);

        // Cargar todos los elementos en la tabla
        try {
            List<ElementoBiblioteca> elementos = controller.obtenerTodos();
            if (elementos != null) {
                for (ElementoBiblioteca elemento : elementos) {
                    String tipo = elemento instanceof Libro ? "Libro" :
                            elemento instanceof Revista ? "Revista" : "DVD";
                    Object[] rowData = new Object[]{
                            tipo,
                            elemento.getTitulo(),
                            elemento.getAutor(),
                            elemento.getAnoPublicacion()
                    };
                    catalogoModel.addRow(rowData);
                }
            }
        } catch (BibliotecaException e) {
            handleError("Error al cargar el catálogo", e);
        }

        // Añadir la tabla al diálogo
        catalogoDialog.add(new JScrollPane(catalogoTable), BorderLayout.CENTER);

        // Botón para cerrar el diálogo
        JButton btnCerrar = new JButton("Cerrar");
        UIConfig.configureButton(btnCerrar);
        btnCerrar.addActionListener(e -> catalogoDialog.dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnCerrar);
        catalogoDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Mostrar el diálogo
        catalogoDialog.setVisible(true);
    }

    private void mostrarAyuda() {
        // Mostrar mensaje inicial con instrucciones básicas
        JOptionPane.showMessageDialog(this,
                "Bienvenido al Sistema de Biblioteca.\n\n" +
                        "- Use las pestañas (Libros, Revistas, DVDs) para gestionar elementos.\n" +
                        "- Agregue, edite o elimine elementos con los botones correspondientes.\n" +
                        "- Busque por título, autor o género usando los campos de búsqueda.\n" +
                        "- Vea el catálogo completo con el botón 'Catálogo'.\n" +
                        "- Haga clic en 'Más detalles' para ver instrucciones adicionales.",
                "Ayuda",
                JOptionPane.INFORMATION_MESSAGE,
                null);

        // Opción para ver más detalles
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Desea ver más detalles sobre el uso de la aplicación?",
                "Más detalles",
                JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            // Crear ventana de ayuda detallada
            JDialog ayudaDialog = new JDialog(this, "Ayuda Detallada", true);
            ayudaDialog.setSize(600, 400);
            ayudaDialog.setLocationRelativeTo(this);
            ayudaDialog.setLayout(new BorderLayout());

            JTextArea textArea = new JTextArea(
                    "=== Guía de Uso del Sistema de Biblioteca ===\n\n" +
                            "1. **Gestión de Elementos**\n" +
                            "   - Agregar: Complete el formulario que aparece al presionar 'Agregar'.\n" +
                            "   - Editar: Seleccione un elemento y presione 'Editar' para modificar sus datos.\n" +
                            "   - Eliminar: Seleccione un elemento y confirme la eliminación con 'Eliminar'.\n" +
                            "   - Actualizar: Recarga los datos de la tabla actual con 'Actualizar'.\n" +
                            "2. **Búsqueda**\n" +
                            "   - Use los campos 'Buscar por título', 'Buscar por autor' y 'Buscar por género' y presione 'Buscar' para filtrar elementos.\n" +
                            "3. **Catálogo**\n" +
                            "   - Presione 'Catálogo' para ver todos los elementos en una lista consolidada.\n" +
                            "4. **Salir**\n" +
                            "   - Use el botón 'Salir' para cerrar la aplicación de forma segura.\n\n" +
                            "Nota: Cualquier error será registrado en el archivo de log."
            );
            textArea.setEditable(false);
            textArea.setFont(new Font("Arial", Font.PLAIN, 12));
            ayudaDialog.add(new JScrollPane(textArea), BorderLayout.CENTER);

            JButton btnCerrarAyuda = new JButton("Cerrar");
            UIConfig.configureButton(btnCerrarAyuda);
            btnCerrarAyuda.addActionListener(e -> ayudaDialog.dispose());
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(btnCerrarAyuda);
            ayudaDialog.add(buttonPanel, BorderLayout.SOUTH);

            ayudaDialog.setVisible(true);
        }
    }

    private JPanel createTabPanel(JTable table, int tabIndex) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes

        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new GridBagLayout());
        Map<String, JTextField> searchFieldsMap = new HashMap<>();

        // Campo de búsqueda por título
        JLabel titleLabel = new JLabel("Buscar por título:");
        JTextField titleSearchField = new JTextField(20);
        titleSearchField.setEditable(true);
        UIConfig.configureTextField(titleSearchField);
        titleSearchField.setPreferredSize(new Dimension(200, 25)); // Ampliado a 200 píxeles de ancho
        searchFieldsMap.put("title", titleSearchField);

        JButton btnBuscarTitle = new JButton("Buscar");
        UIConfig.configureButton(btnBuscarTitle);
        btnBuscarTitle.setPreferredSize(new Dimension(80, 25)); // Tamaño más pequeño
        btnBuscarTitle.setMaximumSize(btnBuscarTitle.getPreferredSize()); // Evitar que crezca

        // Campo de búsqueda por autor
        JLabel authorLabel = new JLabel("Buscar por autor:");
        JTextField authorSearchField = new JTextField(20);
        authorSearchField.setEditable(true);
        UIConfig.configureTextField(authorSearchField);
        authorSearchField.setPreferredSize(new Dimension(200, 25)); // Ampliado a 200 píxeles de ancho
        searchFieldsMap.put("author", authorSearchField);

        JButton btnBuscarAuthor = new JButton("Buscar");
        UIConfig.configureButton(btnBuscarAuthor);
        btnBuscarAuthor.setPreferredSize(new Dimension(80, 25)); // Tamaño más pequeño
        btnBuscarAuthor.setMaximumSize(btnBuscarAuthor.getPreferredSize()); // Evitar que crezca

        // Campo de búsqueda por género
        JLabel genreLabel = new JLabel("Buscar por género:");
        JTextField genreSearchField = new JTextField(20);
        genreSearchField.setEditable(true);
        genreSearchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                System.out.println("Texto actual en genreSearchField (pestaña " + tabIndex + "): '" + genreSearchField.getText() + "'");
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                System.out.println("Texto actual en genreSearchField (pestaña " + tabIndex + "): '" + genreSearchField.getText() + "'");
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                System.out.println("Texto actual en genreSearchField (pestaña " + tabIndex + "): '" + genreSearchField.getText() + "'");
            }
        });
        genreSearchField.setPreferredSize(new Dimension(200, 25)); // Ampliado a 200 píxeles de ancho
        searchFieldsMap.put("genre", genreSearchField);

        JButton btnBuscarGenre = new JButton("Buscar");
        UIConfig.configureButton(btnBuscarGenre);
        btnBuscarGenre.setPreferredSize(new Dimension(80, 25)); // Tamaño más pequeño
        btnBuscarGenre.setMaximumSize(btnBuscarGenre.getPreferredSize()); // Evitar que crezca

        // Disposición del searchPanel con GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        searchPanel.add(titleLabel, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Permitir que el campo se expanda horizontalmente
        searchPanel.add(titleSearchField, gbc);
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0; // Resetear weightx para el botón
        searchPanel.add(btnBuscarTitle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(authorLabel, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        searchPanel.add(authorSearchField, gbc);
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        searchPanel.add(btnBuscarAuthor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        searchPanel.add(genreLabel, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        searchPanel.add(genreSearchField, gbc);
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        searchPanel.add(btnBuscarGenre, gbc);

        // Panel de botones de acción
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        UIConfig.configureButton(btnAgregar);
        UIConfig.configureButton(btnEditar);
        UIConfig.configureButton(btnEliminar);
        UIConfig.configureButton(btnActualizar);

        // Fijar tamaño de los botones de acción
        Dimension buttonSize = new Dimension(100, 30);
        btnAgregar.setPreferredSize(buttonSize);
        btnEditar.setPreferredSize(buttonSize);
        btnEliminar.setPreferredSize(buttonSize);
        btnActualizar.setPreferredSize(buttonSize);
        btnAgregar.setMaximumSize(buttonSize);
        btnEditar.setMaximumSize(buttonSize);
        btnEliminar.setMaximumSize(buttonSize);
        btnActualizar.setMaximumSize(buttonSize);

        actionPanel.add(btnAgregar);
        actionPanel.add(btnEditar);
        actionPanel.add(btnEliminar);
        actionPanel.add(btnActualizar);

        // Panel combinado de búsqueda y acciones
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(actionPanel, BorderLayout.EAST);
        topPanel.setPreferredSize(new Dimension(topPanel.getPreferredSize().width, 120)); // Altura fija

        // Almacenar los campos de búsqueda en el mapa global
        searchFields.put(tabIndex, searchFieldsMap);

        // Disposición del panel principal con GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        panel.add(topPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(new JScrollPane(table), gbc);

        // Configurar acciones para los botones de búsqueda
        btnBuscarTitle.addActionListener(e -> {
            System.out.println("Botón Buscar por título clicado en pestaña " + tabIndex);
            buscarEnTodosLosCampos(tabIndex);
            titleSearchField.setText("");
            authorSearchField.setText("");
            genreSearchField.setText("");
        });

        btnBuscarAuthor.addActionListener(e -> {
            System.out.println("Botón Buscar por autor clicado en pestaña " + tabIndex);
            buscarEnTodosLosCampos(tabIndex);
            titleSearchField.setText("");
            authorSearchField.setText("");
            genreSearchField.setText("");
        });

        btnBuscarGenre.addActionListener(e -> {
            System.out.println("Botón Buscar por género clicado en pestaña " + tabIndex);
            buscarEnTodosLosCampos(tabIndex);
            titleSearchField.setText("");
            authorSearchField.setText("");
            genreSearchField.setText("");
        });

        btnAgregar.addActionListener(this::agregarElemento);
        btnEditar.addActionListener(e -> editarElemento());
        btnEliminar.addActionListener(this::eliminarElemento);
        btnActualizar.addActionListener(e -> {
            loadData();
            // Limpiar los campos de búsqueda al actualizar
            titleSearchField.setText("");
            authorSearchField.setText("");
            genreSearchField.setText("");
            // Mostrar mensaje de confirmación
            JOptionPane.showMessageDialog(this, "Datos actualizados con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });

        return panel;
    }

    private Map<String, JTextField> getSearchFieldsForTab(int tabIndex) {
        return searchFields.get(tabIndex);
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

    private void buscarEnTodosLosCampos(int tabIndex) {
        JTable currentTable = tables.get(tabIndex);
        Map<String, JTextField> currentFields = getSearchFieldsForTab(tabIndex);
        if (currentTable == null || currentFields == null) {
            JOptionPane.showMessageDialog(this, "Error: No se encontró la tabla o los campos de búsqueda", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField titleField = currentFields.get("title");
        JTextField authorField = currentFields.get("author");
        JTextField genreField = currentFields.get("genre");

        String searchTitle = titleField.getText() != null ? titleField.getText().trim() : "";
        String searchAuthor = authorField.getText() != null ? authorField.getText().trim() : "";
        String searchGenre = genreField.getText() != null ? genreField.getText().trim() : "";

        // Normalizar texto para eliminar acentos y convertir a minúsculas
        searchTitle = normalizeText(searchTitle);
        searchAuthor = normalizeText(searchAuthor);
        searchGenre = normalizeText(searchGenre);

        // Validar que al menos un campo tenga datos
        if (searchTitle.isEmpty() && searchAuthor.isEmpty() && searchGenre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese al menos un criterio de búsqueda", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        clearTableModel(currentTable);

        try {
            List<ElementoBiblioteca> resultados = new ArrayList<>();
            List<ElementoBiblioteca> todos = controller.obtenerTodos();

            if (todos != null) {
                for (ElementoBiblioteca elemento : todos) {
                    String normalizedTitle = normalizeText(elemento.getTitulo());
                    String normalizedAuthor = normalizeText(elemento.getAutor());

                    boolean coincide = true;

                    if (!searchTitle.isEmpty() && !normalizedTitle.contains(searchTitle)) {
                        coincide = false;
                    }
                    if (!searchAuthor.isEmpty() && !normalizedAuthor.contains(searchAuthor)) {
                        coincide = false;
                    }
                    if (!searchGenre.isEmpty()) {
                        if (elemento instanceof Libro && !normalizeText(((Libro) elemento).getGenero()).contains(searchGenre)) {
                            coincide = false;
                        } else if (elemento instanceof Revista && !normalizeText(((Revista) elemento).getCategoria()).contains(searchGenre)) {
                            coincide = false;
                        } else if (elemento instanceof DVD) {
                            // DVD no tiene género en el modelo actual, se ignora el filtro de género para DVDs
                        }
                    }

                    if (coincide) {
                        resultados.add(elemento);
                    }
                }

                if (!resultados.isEmpty()) {
                    for (ElementoBiblioteca elemento : resultados) {
                        addToTable(elemento);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron elementos con los criterios especificados", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (BibliotecaException ex) {
            handleError("Error al buscar elementos", ex);
        }
    }

    // Método para normalizar texto (eliminar acentos y convertir a minúsculas)
    private String normalizeText(String text) {
        if (text == null) return "";
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").toLowerCase();
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
                    // Nota: generoDVD podría no estar en el modelo, ajustar según necesidad
                    // dvd.setGenero(form.getGeneroDVD());
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