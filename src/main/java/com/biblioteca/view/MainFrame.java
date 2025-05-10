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

        // Agregar listeners para los botones CRUD
        btnBuscarLibros.addActionListener(e -> buscarLibrosPorGenero());
        btnAgregarLibros.addActionListener(e -> agregarLibro());
        btnEditarLibros.addActionListener(e -> editarLibro());
        btnEliminarLibros.addActionListener(e -> eliminarLibro());
        btnActualizarLibros.addActionListener(e -> cargarDatosLibros());

        btnBuscarRevistas.addActionListener(e -> buscarRevistasPorCategoria());
        btnAgregarRevistas.addActionListener(e -> agregarRevista());
        btnEditarRevistas.addActionListener(e -> editarRevista());
        btnEliminarRevistas.addActionListener(e -> eliminarRevista());
        btnActualizarRevistas.addActionListener(e -> cargarDatosRevistas());

        btnBuscarDVDs.addActionListener(e -> buscarDVDsPorGenero());
        btnAgregarDVDs.addActionListener(e -> agregarDVD());
        btnEditarDVDs.addActionListener(e -> editarDVD());
        btnEliminarDVDs.addActionListener(e -> eliminarDVD());
        btnActualizarDVDs.addActionListener(e -> cargarDatosDVDs());
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
                    JOptionPane.showMessageDialog(this, "Libro agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatosLibros();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar el libro.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese valores válidos para Año y Páginas.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarLibro() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un libro para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tablaLibros.getValueAt(filaSeleccionada, 0);
        Libro libro = libroDAO.leer(id);
        if (libro == null) {
            JOptionPane.showMessageDialog(this, "Libro no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField txtId = new JTextField(String.valueOf(libro.getId()), 5);
        JTextField txtTitulo = new JTextField(libro.getTitulo(), 20);
        JTextField txtAutor = new JTextField(libro.getAutor(), 20);
        JTextField txtAno = new JTextField(String.valueOf(libro.getAnoPublicacion()), 5);
        JTextField txtIsbn = new JTextField(libro.getIsbn(), 15);
        JTextField txtPaginas = new JTextField(String.valueOf(libro.getNumeroPaginas()), 5);
        JTextField txtGenero = new JTextField(libro.getGenero(), 15);
        JTextField txtEditorial = new JTextField(libro.getEditorial(), 20);

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("ID (solo lectura):"));
        panel.add(txtId);
        txtId.setEditable(false);
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

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Libro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
        if (result == JOptionPane.OK_OPTION) {
            try {
                libro.setTitulo(txtTitulo.getText());
                libro.setAutor(txtAutor.getText());
                libro.setAnoPublicacion(Integer.parseInt(txtAno.getText()));
                libro.setIsbn(txtIsbn.getText());
                libro.setNumeroPaginas(Integer.parseInt(txtPaginas.getText()));
                libro.setGenero(txtGenero.getText());
                libro.setEditorial(txtEditorial.getText());

                if (libroDAO.actualizar(libro)) {
                    JOptionPane.showMessageDialog(this, "Libro actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatosLibros();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el libro.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese valores válidos para Año y Páginas.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarLibro() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un libro para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tablaLibros.getValueAt(filaSeleccionada, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este libro?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            if (libroDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Libro eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosLibros();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el libro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Métodos para Revistas
    private void cargarDatosRevistas() {
        DefaultTableModel modelo = (DefaultTableModel) tablaRevistas.getModel();
        modelo.setRowCount(0);
        List<Revista> revistas = revistaDAO.obtenerTodasLasRevistas();
        for (Revista revista : revistas) {
            modelo.addRow(new Object[]{
                    revista.getId(),
                    revista.getTitulo(),
                    revista.getAutor(),
                    revista.getAnoPublicacion(),
                    revista.getNombreEdicion(),
                    revista.getCategoria()
            });
        }
    }

    private void buscarRevistasPorCategoria() {
        String categoria = txtBuscarRevistas.getText().trim();
        DefaultTableModel modelo = (DefaultTableModel) tablaRevistas.getModel();
        modelo.setRowCount(0);
        List<Revista> revistas = revistaDAO.buscarPorCategoria(categoria);
        for (Revista revista : revistas) {
            modelo.addRow(new Object[]{
                    revista.getId(),
                    revista.getTitulo(),
                    revista.getAutor(),
                    revista.getAnoPublicacion(),
                    revista.getNombreEdicion(),
                    revista.getCategoria()
            });
        }
    }

    private void agregarRevista() {
        JTextField txtId = new JTextField(5);
        JTextField txtTitulo = new JTextField(20);
        JTextField txtEditor = new JTextField(20);
        JTextField txtAno = new JTextField(5);
        JTextField txtEdicion = new JTextField(15);
        JTextField txtCategoria = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Título:"));
        panel.add(txtTitulo);
        panel.add(new JLabel("Editor:"));
        panel.add(txtEditor);
        panel.add(new JLabel("Año:"));
        panel.add(txtAno);
        panel.add(new JLabel("Edición:"));
        panel.add(txtEdicion);
        panel.add(new JLabel("Categoría:"));
        panel.add(txtCategoria);

        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Revista", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Revista revista = new Revista();
                revista.setTitulo(txtTitulo.getText());
                revista.setAutor(txtEditor.getText());
                revista.setAnoPublicacion(Integer.parseInt(txtAno.getText()));
                revista.setNombreEdicion(txtEdicion.getText());
                revista.setCategoria(txtCategoria.getText());

                revistaDAO.agregarRevista(revista);
                JOptionPane.showMessageDialog(this, "Revista agregada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosRevistas();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un valor válido para Año.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarRevista() {
        int filaSeleccionada = tablaRevistas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una revista para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tablaRevistas.getValueAt(filaSeleccionada, 0);
        Revista revista = new Revista();
        revista.setId(id);
        revista.setTitulo((String) tablaRevistas.getValueAt(filaSeleccionada, 1));
        revista.setAutor((String) tablaRevistas.getValueAt(filaSeleccionada, 2));
        revista.setAnoPublicacion((int) tablaRevistas.getValueAt(filaSeleccionada, 3));
        revista.setNombreEdicion((String) tablaRevistas.getValueAt(filaSeleccionada, 4));
        revista.setCategoria((String) tablaRevistas.getValueAt(filaSeleccionada, 5));

        JTextField txtId = new JTextField(String.valueOf(revista.getId()), 5);
        JTextField txtTitulo = new JTextField(revista.getTitulo(), 20);
        JTextField txtEditor = new JTextField(revista.getAutor(), 20);
        JTextField txtAno = new JTextField(String.valueOf(revista.getAnoPublicacion()), 5);
        JTextField txtEdicion = new JTextField(revista.getNombreEdicion(), 15);
        JTextField txtCategoria = new JTextField(revista.getCategoria(), 15);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("ID (solo lectura):"));
        panel.add(txtId);
        txtId.setEditable(false);
        panel.add(new JLabel("Título:"));
        panel.add(txtTitulo);
        panel.add(new JLabel("Editor:"));
        panel.add(txtEditor);
        panel.add(new JLabel("Año:"));
        panel.add(txtAno);
        panel.add(new JLabel("Edición:"));
        panel.add(txtEdicion);
        panel.add(new JLabel("Categoría:"));
        panel.add(txtCategoria);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Revista", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
        if (result == JOptionPane.OK_OPTION) {
            try {
                revista.setTitulo(txtTitulo.getText());
                revista.setAutor(txtEditor.getText());
                revista.setAnoPublicacion(Integer.parseInt(txtAno.getText()));
                revista.setNombreEdicion(txtEdicion.getText());
                revista.setCategoria(txtCategoria.getText());

                revistaDAO.actualizarRevista(revista);
                JOptionPane.showMessageDialog(this, "Revista actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosRevistas();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un valor válido para Año.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarRevista() {
        int filaSeleccionada = tablaRevistas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una revista para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tablaRevistas.getValueAt(filaSeleccionada, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar esta revista?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            revistaDAO.eliminarRevista(id);
            JOptionPane.showMessageDialog(this, "Revista eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatosRevistas();
        }
    }

    // Métodos para DVDs
    private void cargarDatosDVDs() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDVDs.getModel();
        modelo.setRowCount(0);
        List<DVD> dvds = dvdDAO.obtenerTodosLosDVDs();
        for (DVD dvd : dvds) {
            modelo.addRow(new Object[]{
                    dvd.getId(),
                    dvd.getTitulo(),
                    dvd.getAutor(),
                    dvd.getAnoPublicacion(),
                    dvd.getDuracion(),
                    dvd.getGenero()
            });
        }
    }

    private void buscarDVDsPorGenero() {
        String genero = txtBuscarDVDs.getText().trim();
        DefaultTableModel modelo = (DefaultTableModel) tablaDVDs.getModel();
        modelo.setRowCount(0);
        List<DVD> dvds = dvdDAO.buscarPorGenero(genero);
        for (DVD dvd : dvds) {
            modelo.addRow(new Object[]{
                    dvd.getId(),
                    dvd.getTitulo(),
                    dvd.getAutor(),
                    dvd.getAnoPublicacion(),
                    dvd.getDuracion(),
                    dvd.getGenero()
            });
        }
    }

    private void agregarDVD() {
        JTextField txtId = new JTextField(5);
        JTextField txtTitulo = new JTextField(20);
        JTextField txtAutor = new JTextField(20);
        JTextField txtAno = new JTextField(5);
        JTextField txtDuracion = new JTextField(5);
        JTextField txtGenero = new JTextField(15);
        JTextField txtDirector = new JTextField(20);
        JTextField txtFormato = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("Título:"));
        panel.add(txtTitulo);
        panel.add(new JLabel("Autor:"));
        panel.add(txtAutor);
        panel.add(new JLabel("Año:"));
        panel.add(txtAno);
        panel.add(new JLabel("Duración (min):"));
        panel.add(txtDuracion);
        panel.add(new JLabel("Género:"));
        panel.add(txtGenero);
        panel.add(new JLabel("Director:"));
        panel.add(txtDirector);
        panel.add(new JLabel("Formato:"));
        panel.add(txtFormato);

        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar DVD", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
        if (result == JOptionPane.OK_OPTION) {
            try {
                DVD dvd = new DVD();
                dvd.setTitulo(txtTitulo.getText());
                dvd.setAutor(txtAutor.getText());
                dvd.setAnoPublicacion(Integer.parseInt(txtAno.getText()));
                dvd.setDuracion(Integer.parseInt(txtDuracion.getText()));
                dvd.setGenero(txtGenero.getText());
                dvd.setDirector(txtDirector.getText());
                dvd.setFormato(txtFormato.getText());

                dvdDAO.agregarDVD(dvd);
                JOptionPane.showMessageDialog(this, "DVD agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosDVDs();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese valores válidos para Año y Duración.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarDVD() {
        int filaSeleccionada = tablaDVDs.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un DVD para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tablaDVDs.getValueAt(filaSeleccionada, 0);
        DVD dvd = new DVD();
        dvd.setId(id);
        dvd.setTitulo((String) tablaDVDs.getValueAt(filaSeleccionada, 1));
        dvd.setAutor((String) tablaDVDs.getValueAt(filaSeleccionada, 2));
        dvd.setAnoPublicacion((int) tablaDVDs.getValueAt(filaSeleccionada, 3));
        dvd.setDuracion((int) tablaDVDs.getValueAt(filaSeleccionada, 4));
        dvd.setGenero((String) tablaDVDs.getValueAt(filaSeleccionada, 5));
        // Nota: No tenemos director ni formato en la tabla, así que los inicializamos como vacíos
        dvd.setDirector("");
        dvd.setFormato("");

        JTextField txtId = new JTextField(String.valueOf(dvd.getId()), 5);
        JTextField txtTitulo = new JTextField(dvd.getTitulo(), 20);
        JTextField txtAutor = new JTextField(dvd.getAutor(), 20);
        JTextField txtAno = new JTextField(String.valueOf(dvd.getAnoPublicacion()), 5);
        JTextField txtDuracion = new JTextField(String.valueOf(dvd.getDuracion()), 5);
        JTextField txtGenero = new JTextField(dvd.getGenero(), 15);
        JTextField txtDirector = new JTextField(dvd.getDirector(), 20);
        JTextField txtFormato = new JTextField(dvd.getFormato(), 10);

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("ID (solo lectura):"));
        panel.add(txtId);
        txtId.setEditable(false);
        panel.add(new JLabel("Título:"));
        panel.add(txtTitulo);
        panel.add(new JLabel("Autor:"));
        panel.add(txtAutor);
        panel.add(new JLabel("Año:"));
        panel.add(txtAno);
        panel.add(new JLabel("Duración (min):"));
        panel.add(txtDuracion);
        panel.add(new JLabel("Género:"));
        panel.add(txtGenero);
        panel.add(new JLabel("Director:"));
        panel.add(txtDirector);
        panel.add(new JLabel("Formato:"));
        panel.add(txtFormato);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar DVD", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
        if (result == JOptionPane.OK_OPTION) {
            try {
                dvd.setTitulo(txtTitulo.getText());
                dvd.setAutor(txtAutor.getText());
                dvd.setAnoPublicacion(Integer.parseInt(txtAno.getText()));
                dvd.setDuracion(Integer.parseInt(txtDuracion.getText()));
                dvd.setGenero(txtGenero.getText());
                dvd.setDirector(txtDirector.getText());
                dvd.setFormato(txtFormato.getText());

                dvdDAO.actualizarDVD(dvd);
                JOptionPane.showMessageDialog(this, "DVD actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosDVDs();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese valores válidos para Año y Duración.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarDVD() {
        int filaSeleccionada = tablaDVDs.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un DVD para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tablaDVDs.getValueAt(filaSeleccionada, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este DVD?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            dvdDAO.eliminarDVD(id);
            JOptionPane.showMessageDialog(this, "DVD eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatosDVDs();
        }
    }

    // Getters para los componentes
    public JTable getTablaLibros() { return tablaLibros; }
    public JTable getTablaRevistas() { return tablaRevistas; }
    public JTable getTablaDVDs() { return tablaDVDs; }
    public JTextField getTxtBuscarLibros() { return txtBuscarLibros; }
    public JTextField getTxtBuscarRevistas() { return txtBuscarRevistas; }
    public JTextField getTxtBuscarDVDs() { return txtBuscarDVDs; }
    public JButton getBtnAgregarLibros() { return btnAgregarLibros; }
    public JButton getBtnEditarLibros() { return btnEditarLibros; }
    public JButton getBtnEliminarLibros() { return btnEliminarLibros; }
    public JButton getBtnActualizarLibros() { return btnActualizarLibros; }
    public JButton getBtnBuscarLibros() { return btnBuscarLibros; }
    public JButton getBtnLimpiarLibros() { return btnLimpiarLibros; }
    public JButton getBtnAgregarRevistas() { return btnAgregarRevistas; }
    public JButton getBtnEditarRevistas() { return btnEditarRevistas; }
    public JButton getBtnEliminarRevistas() { return btnEliminarRevistas; }
    public JButton getBtnActualizarRevistas() { return btnActualizarRevistas; }
    public JButton getBtnBuscarRevistas() { return btnBuscarRevistas; }
    public JButton getBtnLimpiarRevistas() { return btnLimpiarRevistas; }
    public JButton getBtnAgregarDVDs() { return btnAgregarDVDs; }
    public JButton getBtnEditarDVDs() { return btnEditarDVDs; }
    public JButton getBtnEliminarDVDs() { return btnEliminarDVDs; }
    public JButton getBtnActualizarDVDs() { return btnActualizarDVDs; }
    public JButton getBtnBuscarDVDs() { return btnBuscarDVDs; }
    public JButton getBtnLimpiarDVDs() { return btnLimpiarDVDs; }
}