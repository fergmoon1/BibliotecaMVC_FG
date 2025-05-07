package com.biblioteca.view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

/**
 * Clase que representa el panel para gestionar revistas en la com.biblioteca.
 * Muestra una tabla con las revistas y botones para operaciones CRUD.
 */
public class PanelRevistas extends JPanel {

    private JTable tablaRevistas;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;

    /**
     * Constructor del panel de gestión de revistas.
     * Inicializa los componentes gráficos y configura el layout.
     */
    public PanelRevistas() {
        // Configurar el layout principal
        setLayout(new BorderLayout());

        // Panel superior con título
        JPanel panelTitulo = new JPanel(new FlowLayout());
        JLabel lblTitulo = new JLabel("Gestión de Revistas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel central con la tabla
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Título", "Autor", "Año", "Nombre Edición", "Categoría"},
                0
        );
        tablaRevistas = new JTable(modeloTabla);
        tablaRevistas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaRevistas);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAgregar = new JButton("Agregar Revista");
        btnEditar = new JButton("Editar Revista");
        btnEliminar = new JButton("Eliminar Revista");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    // Getters para los componentes (para uso del controlador)

    /**
     * Obtiene la tabla de revistas.
     *
     * @return JTable que muestra las revistas
     */
    public JTable getTablaRevistas() {
        return tablaRevistas;
    }

    /**
     * Obtiene el modelo de la tabla de revistas.
     *
     * @return DefaultTableModel de la tabla
     */
    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    /**
     * Obtiene el botón de agregar revista.
     *
     * @return JButton para agregar revistas
     */
    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    /**
     * Obtiene el botón de editar revista.
     *
     * @return JButton para editar revistas
     */
    public JButton getBtnEditar() {
        return btnEditar;
    }

    /**
     * Obtiene el botón de eliminar revista.
     *
     * @return JButton para eliminar revistas
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }
}