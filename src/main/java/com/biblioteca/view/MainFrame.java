package com.biblioteca.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private JTable tablaLibros, tablaRevistas, tablaDVDs;
    private JTextField txtBuscarLibros, txtBuscarRevistas, txtBuscarDVDs;
    private JButton btnBuscarLibros, btnAgregarLibros, btnEditarLibros, btnEliminarLibros;
    private JButton btnBuscarRevistas, btnAgregarRevistas, btnEditarRevistas, btnEliminarRevistas;
    private JButton btnBuscarDVDs, btnAgregarDVDs, btnEditarDVDs, btnEliminarDVDs;

    public MainFrame() {
        setTitle("Sistema de Gestión de Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el JTabbedPane
        tabbedPane = new JTabbedPane();

        // Pestaña de Libros
        JPanel panelLibros = crearPanel("Libros");
        tablaLibros = (JTable) panelLibros.getClientProperty("tabla");
        txtBuscarLibros = (JTextField) panelLibros.getClientProperty("txtBuscar");
        btnBuscarLibros = (JButton) panelLibros.getClientProperty("btnBuscar");
        btnAgregarLibros = (JButton) panelLibros.getClientProperty("btnAgregar");
        btnEditarLibros = (JButton) panelLibros.getClientProperty("btnEditar");
        btnEliminarLibros = (JButton) panelLibros.getClientProperty("btnEliminar");
        tabbedPane.addTab("Libros", panelLibros);

        // Pestaña de Revistas
        JPanel panelRevistas = crearPanel("Revistas");
        tablaRevistas = (JTable) panelRevistas.getClientProperty("tabla");
        txtBuscarRevistas = (JTextField) panelRevistas.getClientProperty("txtBuscar");
        btnBuscarRevistas = (JButton) panelRevistas.getClientProperty("btnBuscar");
        btnAgregarRevistas = (JButton) panelRevistas.getClientProperty("btnAgregar");
        btnEditarRevistas = (JButton) panelRevistas.getClientProperty("btnEditar");
        btnEliminarRevistas = (JButton) panelRevistas.getClientProperty("btnEliminar");
        tabbedPane.addTab("Revistas", panelRevistas);

        // Pestaña de DVDs
        JPanel panelDVDs = crearPanel("DVDs");
        tablaDVDs = (JTable) panelDVDs.getClientProperty("tabla");
        txtBuscarDVDs = (JTextField) panelDVDs.getClientProperty("txtBuscar");
        btnBuscarDVDs = (JButton) panelDVDs.getClientProperty("btnBuscar");
        btnAgregarDVDs = (JButton) panelDVDs.getClientProperty("btnAgregar");
        btnEditarDVDs = (JButton) panelDVDs.getClientProperty("btnEditar");
        btnEliminarDVDs = (JButton) panelDVDs.getClientProperty("btnEliminar");
        tabbedPane.addTab("DVDs", panelDVDs);

        // Agregar el JTabbedPane al frame
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel crearPanel(String tipo) {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout());
        panelBusqueda.add(new JLabel("Buscar por " + (tipo.equals("DVDs") ? "género" : "autor") + ":"));
        JTextField txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);

        // Tabla
        JTable tabla;
        if (tipo.equals("Libros")) {
            tabla = new JTable(new DefaultTableModel(
                    new Object[][]{},
                    new String[]{"ID", "Título", "Autor", "Año Publicación"}
            ));
        } else if (tipo.equals("Revistas")) {
            tabla = new JTable(new DefaultTableModel(
                    new Object[][]{},
                    new String[]{"ID", "Título", "Editor", "Número Edición"}
            ));
        } else {
            tabla = new JTable(new DefaultTableModel(
                    new Object[][]{},
                    new String[]{"ID", "Título", "Autor", "Año", "Duración", "Género"}
            ));
        }
        JScrollPane scrollPane = new JScrollPane(tabla);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        // Agregar componentes al panel
        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        // Guardar referencias en el panel
        panel.putClientProperty("tabla", tabla);
        panel.putClientProperty("txtBuscar", txtBuscar);
        panel.putClientProperty("btnBuscar", btnBuscar);
        panel.putClientProperty("btnAgregar", btnAgregar);
        panel.putClientProperty("btnEditar", btnEditar);
        panel.putClientProperty("btnEliminar", btnEliminar);

        return panel;
    }

    // Getters para los componentes
    public JTabbedPane getTabbedPane() { return tabbedPane; }

    // Libros
    public JTable getTablaLibros() { return tablaLibros; }
    public JTextField getTxtBuscarLibros() { return txtBuscarLibros; }
    public JButton getBtnBuscarLibros() { return btnBuscarLibros; }
    public JButton getBtnAgregarLibros() { return btnAgregarLibros; }
    public JButton getBtnEditarLibros() { return btnEditarLibros; }
    public JButton getBtnEliminarLibros() { return btnEliminarLibros; }

    // Revistas
    public JTable getTablaRevistas() { return tablaRevistas; }
    public JTextField getTxtBuscarRevistas() { return txtBuscarRevistas; }
    public JButton getBtnBuscarRevistas() { return btnBuscarRevistas; }
    public JButton getBtnAgregarRevistas() { return btnAgregarRevistas; }
    public JButton getBtnEditarRevistas() { return btnEditarRevistas; }
    public JButton getBtnEliminarRevistas() { return btnEliminarRevistas; }

    // DVDs
    public JTable getTablaDVDs() { return tablaDVDs; }
    public JTextField getTxtBuscarDVDs() { return txtBuscarDVDs; }
    public JButton getBtnBuscarDVDs() { return btnBuscarDVDs; }
    public JButton getBtnAgregarDVDs() { return btnAgregarDVDs; }
    public JButton getBtnEditarDVDs() { return btnEditarDVDs; }
    public JButton getBtnEliminarDVDs() { return btnEliminarDVDs; }
}