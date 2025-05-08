package com.biblioteca.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
    private JButton btnAgregarRevistas;
    private JButton btnEditarRevistas;
    private JButton btnEliminarRevistas;
    private JButton btnActualizarRevistas;
    private JButton btnBuscarRevistas;
    private JButton btnAgregarDVDs;
    private JButton btnEditarDVDs;
    private JButton btnEliminarDVDs;
    private JButton btnActualizarDVDs;
    private JButton btnBuscarDVDs;

    public MainFrame() {
        setTitle("Sistema de Gestión de Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem menuItemSalir = new JMenuItem("Salir");
        menuItemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(menuItemSalir);

        JMenu menuCatalogo = new JMenu("Catálogo");
        JMenuItem menuItemRefrescar = new JMenuItem("Refrescar");
        menuCatalogo.add(menuItemRefrescar);

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem menuItemAcercaDe = new JMenuItem("Acerca de");
        menuItemAcercaDe.addActionListener(e -> JOptionPane.showMessageDialog(this, "Sistema de Gestión de Biblioteca\nVersión 1.0"));
        menuAyuda.add(menuItemAcercaDe);

        menuBar.add(menuArchivo);
        menuBar.add(menuCatalogo);
        menuBar.add(menuAyuda);
        setJMenuBar(menuBar);

        // Crear el JTabbedPane
        tabbedPane = new JTabbedPane();

        // Pestaña de Libros
        JPanel panelLibros = new JPanel(new BorderLayout());
        // Tabla de libros
        String[] columnasLibros = {"ID", "Título", "Autor", "Año", "ISBN", "Páginas", "Género", "Editorial"};
        DefaultTableModel modeloLibros = new DefaultTableModel(columnasLibros, 0);
        tablaLibros = new JTable(modeloLibros);
        JScrollPane scrollLibros = new JScrollPane(tablaLibros);
        panelLibros.add(scrollLibros, BorderLayout.CENTER);

        // Panel de búsqueda y botones para libros
        JPanel panelBotoneraLibros = new JPanel();
        txtBuscarLibros = new JTextField(20);
        btnBuscarLibros = new JButton("Buscar por autor");
        btnAgregarLibros = new JButton("Agregar");
        btnEditarLibros = new JButton("Editar");
        btnEliminarLibros = new JButton("Eliminar");
        btnActualizarLibros = new JButton("Actualizar");

        panelBotoneraLibros.add(new JLabel("Autor:"));
        panelBotoneraLibros.add(txtBuscarLibros);
        panelBotoneraLibros.add(btnBuscarLibros);
        panelBotoneraLibros.add(btnAgregarLibros);
        panelBotoneraLibros.add(btnEditarLibros);
        panelBotoneraLibros.add(btnEliminarLibros);
        panelBotoneraLibros.add(btnActualizarLibros);
        panelLibros.add(panelBotoneraLibros, BorderLayout.SOUTH);

        tabbedPane.addTab("Libros", panelLibros);

        // Pestaña de Revistas
        JPanel panelRevistas = new JPanel(new BorderLayout());
        // Tabla de revistas
        String[] columnasRevistas = {"ID", "Título", "Editor", "Año", "Edición", "Categoría"};
        DefaultTableModel modeloRevistas = new DefaultTableModel(columnasRevistas, 0);
        tablaRevistas = new JTable(modeloRevistas);
        JScrollPane scrollRevistas = new JScrollPane(tablaRevistas);
        panelRevistas.add(scrollRevistas, BorderLayout.CENTER);

        // Panel de búsqueda y botones para revistas
        JPanel panelBotoneraRevistas = new JPanel();
        txtBuscarRevistas = new JTextField(20);
        btnBuscarRevistas = new JButton("Buscar por editor");
        btnAgregarRevistas = new JButton("Agregar");
        btnEditarRevistas = new JButton("Editar");
        btnEliminarRevistas = new JButton("Eliminar");
        btnActualizarRevistas = new JButton("Actualizar");

        panelBotoneraRevistas.add(new JLabel("Editor:"));
        panelBotoneraRevistas.add(txtBuscarRevistas);
        panelBotoneraRevistas.add(btnBuscarRevistas);
        panelBotoneraRevistas.add(btnAgregarRevistas);
        panelBotoneraRevistas.add(btnEditarRevistas);
        panelBotoneraRevistas.add(btnEliminarRevistas);
        panelBotoneraRevistas.add(btnActualizarRevistas);
        panelRevistas.add(panelBotoneraRevistas, BorderLayout.SOUTH);

        tabbedPane.addTab("Revistas", panelRevistas);

        // Pestaña de DVDs
        JPanel panelDVDs = new JPanel(new BorderLayout());
        // Tabla de DVDs
        String[] columnasDVDs = {"ID", "Título", "Director", "Año", "Duración", "Género"};
        DefaultTableModel modeloDVDs = new DefaultTableModel(columnasDVDs, 0);
        tablaDVDs = new JTable(modeloDVDs);
        JScrollPane scrollDVDs = new JScrollPane(tablaDVDs);
        panelDVDs.add(scrollDVDs, BorderLayout.CENTER);

        // Panel de búsqueda y botones para DVDs
        JPanel panelBotoneraDVDs = new JPanel();
        txtBuscarDVDs = new JTextField(20);
        btnBuscarDVDs = new JButton("Buscar por género");
        btnAgregarDVDs = new JButton("Agregar");
        btnEditarDVDs = new JButton("Editar");
        btnEliminarDVDs = new JButton("Eliminar");
        btnActualizarDVDs = new JButton("Actualizar");

        panelBotoneraDVDs.add(new JLabel("Género:"));
        panelBotoneraDVDs.add(txtBuscarDVDs);
        panelBotoneraDVDs.add(btnBuscarDVDs);
        panelBotoneraDVDs.add(btnAgregarDVDs);
        panelBotoneraDVDs.add(btnEditarDVDs);
        panelBotoneraDVDs.add(btnEliminarDVDs);
        panelBotoneraDVDs.add(btnActualizarDVDs);
        panelDVDs.add(panelBotoneraDVDs, BorderLayout.SOUTH);

        tabbedPane.addTab("DVDs", panelDVDs);

        // Agregar el JTabbedPane al frame
        add(tabbedPane);
    }

    // Getters para los componentes
    public JTable getTablaLibros() {
        return tablaLibros;
    }

    public JTable getTablaRevistas() {
        return tablaRevistas;
    }

    public JTable getTablaDVDs() {
        return tablaDVDs;
    }

    public JTextField getTxtBuscarLibros() {
        return txtBuscarLibros;
    }

    public JTextField getTxtBuscarRevistas() {
        return txtBuscarRevistas;
    }

    public JTextField getTxtBuscarDVDs() {
        return txtBuscarDVDs;
    }

    public JButton getBtnAgregarLibros() {
        return btnAgregarLibros;
    }

    public JButton getBtnEditarLibros() {
        return btnEditarLibros;
    }

    public JButton getBtnEliminarLibros() {
        return btnEliminarLibros;
    }

    public JButton getBtnActualizarLibros() {
        return btnActualizarLibros;
    }

    public JButton getBtnBuscarLibros() {
        return btnBuscarLibros;
    }

    public JButton getBtnAgregarRevistas() {
        return btnAgregarRevistas;
    }

    public JButton getBtnEditarRevistas() {
        return btnEditarRevistas;
    }

    public JButton getBtnEliminarRevistas() {
        return btnEliminarRevistas;
    }

    public JButton getBtnActualizarRevistas() {
        return btnActualizarRevistas;
    }

    public JButton getBtnBuscarRevistas() {
        return btnBuscarRevistas;
    }

    public JButton getBtnAgregarDVDs() {
        return btnAgregarDVDs;
    }

    public JButton getBtnEditarDVDs() {
        return btnEditarDVDs;
    }

    public JButton getBtnEliminarDVDs() {
        return btnEliminarDVDs;
    }

    public JButton getBtnActualizarDVDs() {
        return btnActualizarDVDs;
    }

    public JButton getBtnBuscarDVDs() {
        return btnBuscarDVDs;
    }
}