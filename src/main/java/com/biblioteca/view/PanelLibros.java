package com.biblioteca.view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

/**
 * Clase que representa el panel para gestionar libros en la com.biblioteca.
 * Muestra una tabla con los libros y botones para operaciones CRUD.
 */
public class PanelLibros extends JPanel {

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;

    /**
     * Constructor del panel de gestión de libros.
     * Inicializa los componentes gráficos y configura el layout.
     */
    public PanelLibros() {
        // Configurar el layout principal
        setLayout(new BorderLayout());

        // Panel superior con título
        JPanel panelTitulo = new JPanel(new FlowLayout());
        JLabel lblTitulo = new JLabel("Gestión de Libros");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel central con la tabla
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Título", "Autor", "Año", "ISBN", "Género", "Editorial"},
                0
        );
        tablaLibros = new JTable(modeloTabla);
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaLibros);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAgregar = new JButton("Agregar Libro");
        btnEditar = new JButton("Editar Libro");
        btnEliminar = new JButton("Eliminar Libro");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    // Getters para los componentes (para uso del controlador)

    /**
     * Obtiene la tabla de libros.
     *
     * @return JTable que muestra los libros
     */
    public JTable getTablaLibros() {
        return tablaLibros;
    }

    /**
     * Obtiene el modelo de la tabla de libros.
     *
     * @return DefaultTableModel de la tabla
     */
    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    /**
     * Obtiene el botón de agregar libro.
     *
     * @return JButton para agregar libros
     */
    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    /**
     * Obtiene el botón de editar libro.
     *
     * @return JButton para editar libros
     */
    public JButton getBtnEditar() {
        return btnEditar;
    }

    /**
     * Obtiene el botón de eliminar libro.
     *
     * @return JButton para eliminar libros
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }
}