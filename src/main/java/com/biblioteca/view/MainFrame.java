package com.biblioteca.view;

import com.biblioteca.dao.LibroDAO;
import com.biblioteca.dao.LibroDAOImpl;
import com.biblioteca.dao.RevistaDAO;
import com.biblioteca.dao.RevistaDAOImpl;
import com.biblioteca.dao.DVDDAO;
import com.biblioteca.dao.DVDDAOImpl;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Revista;
import com.biblioteca.model.DVD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private JTable tablaLibros;
    private JTable tablaRevistas;
    private JTable tablaDVDs;
    private JTextField txtBuscarLibros;
    private JTextField txtBuscarRevistas;
    private JTextField txtBuscarDVDs;
    private JButton btnAgregarLibros;
    private JButton btnEditarLibros;
    private JButton btnEliminarLibros;
    private JButton btnActualizarLibros;
    private JButton btnBuscarLibros;
    private JButton btnLimpiarLibros;
    private JButton btnAgregarRevistas;
    private JButton btnEditarRevistas;
    private JButton btnEliminarRevistas;
    private JButton btnActualizarRevistas;
    private JButton btnBuscarRevistas;
    private JButton btnLimpiarRevistas;
    private JButton btnAgregarDVDs;
    private JButton btnEditarDVDs;
    private JButton btnEliminarDVDs;
    private JButton btnActualizarDVDs;
    private JButton btnBuscarDVDs;
    private JButton btnLimpiarDVDs;
    private LibroDAO libroDAO;
    private RevistaDAO revistaDAO;
    private DVDDAO dvdDAO;

    public MainFrame() {
        setTitle("Sistema de Gestión de Biblioteca");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600)); // Tamaño mínimo para responsividad

        // Inicializar DAOs
        libroDAO = new LibroDAOImpl();
        revistaDAO = new RevistaDAOImpl();
        dvdDAO = new DVDDAOImpl();

        // Crear el menú
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(240, 240, 240));
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem menuItemSalir = new JMenuItem("Salir");
        menuItemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(menuItemSalir);

        JMenu menuCatalogo = new JMenu("Catálogo");
        JMenuItem menuItemRefrescar = new JMenuItem("Refrescar Todo");
        menuItemRefrescar.addActionListener(e -> {
            cargarDatosLibros();
            cargarDatosRevistas();
            cargarDatosDVDs();
        });
        menuCatalogo.add(menuItemRefrescar);

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem menuItemAcercaDe = new JMenuItem("Acerca de");
        menuItemAcercaDe.addActionListener(e -> JOptionPane.showMessageDialog(this, "Sistema de Gestión de Biblioteca\nVersión 1.0\nDesarrollado por xAI", "Acerca de", JOptionPane.INFORMATION_MESSAGE));
        menuAyuda.add(menuItemAcercaDe);

        menuBar.add(menuArchivo);
        menuBar.add(menuCatalogo);
        menuBar.add(menuAyuda);
        setJMenuBar(menuBar);

        // Crear el JTabbedPane
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(230, 230, 230));
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // Pestaña de Libros
        JPanel panelLibros = new JPanel(new BorderLayout(10, 10));
        panelLibros.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelLibros.setBackground(new Color(245, 245, 245));

        String[] columnasLibros = {"ID", "Título", "Autor", "Año", "ISBN", "Páginas", "Género", "Editorial"};
        DefaultTableModel modeloLibros = new DefaultTableModel(columnasLibros, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        tablaLibros = new JTable(modeloLibros);
        tablaLibros.setFillsViewportHeight(true);
        tablaLibros.setRowHeight(25);
        tablaLibros.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaLibros.getTableHeader().setBackground(new Color(200, 220, 240));
        JScrollPane scrollLibros = new JScrollPane(tablaLibros);
        scrollLibros.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        panelLibros.add(scrollLibros, BorderLayout.CENTER);

        JPanel panelBotoneraLibros = new JPanel(new GridBagLayout());
        panelBotoneraLibros.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtBuscarLibros = new JTextField(20);
        btnBuscarLibros = new JButton("Buscar por Género");
        btnLimpiarLibros = new JButton("Limpiar");
        btnAgregarLibros = new JButton("Agregar");
        btnEditarLibros = new JButton("Editar");
        btnEliminarLibros = new JButton("Eliminar");
        btnActualizarLibros = new JButton("Actualizar");

        // Estilo de botones
        JButton[] botonesLibros = {btnBuscarLibros, btnLimpiarLibros, btnAgregarLibros, btnEditarLibros, btnEliminarLibros, btnActualizarLibros};
        for (JButton btn : botonesLibros) {
            btn.setBackground(new Color(70, 130, 180));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 12));
        }

        gbc.gridx = 0; gbc.gridy = 0;
        panelBotoneraLibros.add(new JLabel("Género:"), gbc);
        gbc.gridx = 1;
        panelBotoneraLibros.add(txtBuscarLibros, gbc);
        gbc.gridx = 2;
        panelBotoneraLibros.add(btnBuscarLibros, gbc);
        gbc.gridx = 3;
        panelBotoneraLibros.add(btnLimpiarLibros, gbc);
        gbc.gridx = 4; gbc.gridwidth = 1;
        panelBotoneraLibros.add(btnAgregarLibros, gbc);
        gbc.gridx = 5;
        panelBotoneraLibros.add(btnEditarLibros, gbc);
        gbc.gridx = 6;
        panelBotoneraLibros.add(btnEliminarLibros, gbc);
        gbc.gridx = 7;
        panelBotoneraLibros.add(btnActualizarLibros, gbc);

        panelLibros.add(panelBotoneraLibros, BorderLayout.SOUTH);
        tabbedPane.addTab("Libros", panelLibros);

        // Pestaña de Revistas
        JPanel panelRevistas = new JPanel(new BorderLayout(10, 10));
        panelRevistas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelRevistas.setBackground(new Color(245, 245, 245));

        String[] columnasRevistas = {"ID", "Título", "Editor", "Año", "Edición", "Categoría"};
        DefaultTableModel modeloRevistas = new DefaultTableModel(columnasRevistas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaRevistas = new JTable(modeloRevistas);
        tablaRevistas.setFillsViewportHeight(true);
        tablaRevistas.setRowHeight(25);
        tablaRevistas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaRevistas.getTableHeader().setBackground(new Color(200, 220, 240));
        JScrollPane scrollRevistas = new JScrollPane(tablaRevistas);
        scrollRevistas.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        panelRevistas.add(scrollRevistas, BorderLayout.CENTER);

        JPanel panelBotoneraRevistas = new JPanel(new GridBagLayout());
        panelBotoneraRevistas.setBackground(new Color(245, 245, 245));
        txtBuscarRevistas = new JTextField(20);
        btnBuscarRevistas = new JButton("Buscar por Categoría");
        btnLimpiarRevistas = new JButton("Limpiar");
        btnAgregarRevistas = new JButton("Agregar");
        btnEditarRevistas = new JButton("Editar");
        btnEliminarRevistas = new JButton("Eliminar");
        btnActualizarRevistas = new JButton("Actualizar");

        JButton[] botonesRevistas = {btnBuscarRevistas, btnLimpiarRevistas, btnAgregarRevistas, btnEditarRevistas, btnEliminarRevistas, btnActualizarRevistas};
        for (JButton btn : botonesRevistas) {
            btn.setBackground(new Color(70, 130, 180));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 12));
        }

        gbc.gridx = 0; gbc.gridy = 0;
        panelBotoneraRevistas.add(new JLabel("Categoría:"), gbc);
        gbc.gridx = 1;
        panelBotoneraRevistas.add(txtBuscarRevistas, gbc);
        gbc.gridx = 2;
        panelBotoneraRevistas.add(btnBuscarRevistas, gbc);
        gbc.gridx = 3;
        panelBotoneraRevistas.add(btnLimpiarRevistas, gbc);
        gbc.gridx = 4;
        panelBotoneraRevistas.add(btnAgregarRevistas, gbc);
        gbc.gridx = 5;
        panelBotoneraRevistas.add(btnEditarRevistas, gbc);
        gbc.gridx = 6;
        panelBotoneraRevistas.add(btnEliminarRevistas, gbc);
        gbc.gridx = 7;
        panelBotoneraRevistas.add(btnActualizarRevistas, gbc);

        panelRevistas.add(panelBotoneraRevistas, BorderLayout.SOUTH);
        tabbedPane.addTab("Revistas", panelRevistas);

        // Pestaña de DVDs
        JPanel panelDVDs = new JPanel(new BorderLayout(10, 10));
        panelDVDs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelDVDs.setBackground(new Color(245, 245, 245));

        String[] columnasDVDs = {"ID", "Título", "Autor", "Año", "Duración", "Género"};
        DefaultTableModel modeloDVDs = new DefaultTableModel(columnasDVDs, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaDVDs = new JTable(modeloDVDs);
        tablaDVDs.setFillsViewportHeight(true);
        tablaDVDs.setRowHeight(25);
        tablaDVDs.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaDVDs.getTableHeader().setBackground(new Color(200, 220, 240));
        JScrollPane scrollDVDs = new JScrollPane(tablaDVDs);
        scrollDVDs.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        panelDVDs.add(scrollDVDs, BorderLayout.CENTER);

        JPanel panelBotoneraDVDs = new JPanel(new GridBagLayout());
        panelBotoneraDVDs.setBackground(new Color(245, 245, 245));
        txtBuscarDVDs = new JTextField(20);
        btnBuscarDVDs = new JButton("Buscar por Género");
        btnLimpiarDVDs = new JButton("Limpiar");
        btnAgregarDVDs = new JButton("Agregar");
        btnEditarDVDs = new JButton("Editar");
        btnEliminarDVDs = new JButton("Eliminar");
        btnActualizarDVDs = new JButton("Actualizar");

        JButton[] botonesDVDs = {btnBuscarDVDs, btnLimpiarDVDs, btnAgregarDVDs, btnEditarDVDs, btnEliminarDVDs, btnActualizarDVDs};
        for (JButton btn : botonesDVDs) {
            btn.setBackground(new Color(70, 130, 180));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 12));
        }

        gbc.gridx = 0; gbc.gridy = 0;
        panelBotoneraDVDs.add(new JLabel("Género:"), gbc);
        gbc.gridx = 1;
        panelBotoneraDVDs.add(txtBuscarDVDs, gbc);
        gbc.gridx = 2;
        panelBotoneraDVDs.add(btnBuscarDVDs, gbc);
        gbc.gridx = 3;
        panelBotoneraDVDs.add(btnLimpiarDVDs, gbc);
        gbc.gridx = 4;
        panelBotoneraDVDs.add(btnAgregarDVDs, gbc);
        gbc.gridx = 5;
        panelBotoneraDVDs.add(btnEditarDVDs, gbc);
        gbc.gridx = 6;
        panelBotoneraDVDs.add(btnEliminarDVDs, gbc);
        gbc.gridx = 7;
        panelBotoneraDVDs.add(btnActualizarDVDs, gbc);

        panelDVDs.add(panelBotoneraDVDs, BorderLayout.SOUTH);
        tabbedPane.addTab("DVDs", panelDVDs);

        // Agregar el JTabbedPane al frame
        add(tabbedPane);

        // Cargar datos iniciales
        cargarDatosLibros();
        cargarDatosRevistas();
        cargarDatosDVDs();

        // Agregar listeners
        btnLimpiarLibros.addActionListener(e -> {
            txtBuscarLibros.setText("");
            cargarDatosLibros();
        });
        btnLimpiarRevistas.addActionListener(e -> {
            txtBuscarRevistas.setText("");
            cargarDatosRevistas();
        });
        btnLimpiarDVDs.addActionListener(e -> {
            txtBuscarDVDs.setText("");
            cargarDatosDVDs();
        });
    }

    // Métodos para Libros
    private void cargarDatosLibros() {
        DefaultTableModel modelo = (DefaultTableModel) tablaLibros.getModel();
        modelo.setRowCount(0);
        List<Libro> libros = libroDAO.obtenerTodos();
        for (Libro libro : libros) {
            modelo.addRow(new Object[]{
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getAnoPublicacion(),
                    libro.getIsbn(),
                    libro.getNumeroPaginas(),
                    libro.getGenero(),
                    libro.getEditorial()
            });
        }
    }

    private void buscarLibrosPorGenero() {
        String genero = txtBuscarLibros.getText().trim();
        DefaultTableModel modelo = (DefaultTableModel) tablaLibros.getModel();
        modelo.setRowCount(0);
        List<Libro> libros = libroDAO.buscarPorGenero(genero);
        for (Libro libro : libros) {
            modelo.addRow(new Object[]{
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getAnoPublicacion(),
                    libro.getIsbn(),
                    libro.getNumeroPaginas(),
                    libro.getGenero(),
                    libro.getEditorial()
            });
        }
    }

    private void agregarLibro() {
        JTextField txtId = new JTextField(5);
        JTextField txtTitulo = new JTextField(20);
        JTextField txtAutor = new JTextField(20);
        JTextField txtAno = new JTextField(5);
        JTextField txtIsbn = new JTextField(15);
        JTextField txtPaginas = new JTextField(5);
        JTextField txtGenero = new JTextField(15);
        JTextField txtEditorial = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("Título:"));
        panel.add(txtTitulo);
        panel.add(new JLabel("Autor:"));
        panel.add(txtAutor);
        panel.add(new JLabel("Año:"));
        panel.add(txtAno);
        panel.add(new JLabel("ISBN:"));
        panel.add(txtIsbn);
        panel.add(new JLabel("Páginas:"));
        panel.add(txtPaginas);
        panel.add(new JLabel("Género:"));
        panel.add(txtGenero);
        panel.add(new JLabel("Editorial:"));
        panel.add(txtEditorial);

        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Libro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Libro libro = new Libro();
                libro.setTitulo(txtTitulo.getText());
                libro.setAutor(txtAutor.getText());
                libro.setAnoPublicacion(Integer.parseInt(txtAno.getText()));
                libro.setIsbn(txtIsbn.getText());
                libro.setNumeroPaginas(Integer.parseInt(txtPaginas.getText()));
                libro.setGenero(txtGenero.getText());
                libro.setEditorial(txtEditorial.getText());

                if (libroDAO.crear(libro)) {
                    JOptionPane.showMessageDialog(this, "Libro agregado exitosamente.", "Éxito",