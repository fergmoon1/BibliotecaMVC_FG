package com.biblioteca.view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

/**
 * Clase que representa el panel para gestionar DVDs en la com.biblioteca.
 * Muestra una tabla con los DVDs y botones para operaciones CRUD.
 */
public class PanelDVDs extends JPanel {

    private JTable tablaDVDs;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;

    /**
     * Constructor del panel de gestión de DVDs.
     * Inicializa los componentes gráficos y configura el layout.
     */
    public PanelDVDs() {
        // Configurar el layout principal
        setLayout(new BorderLayout());

        // Panel superior con título
        JPanel panelTitulo = new JPanel(new FlowLayout());
        JLabel lblTitulo = new JLabel("Gestión de DVDs");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel central con la tabla
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Título", "Autor", "Año", "Director", "Duración", "Formato"},
                0
        );
        tablaDVDs = new JTable(modeloTabla);
        tablaDVDs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaDVDs);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAgregar = new JButton("Agregar DVD");
        btnEditar = new JButton("Editar DVD");
        btnEliminar = new JButton("Eliminar DVD");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    // Getters para los componentes (para uso del controlador)

    /**
     * Obtiene la tabla de DVDs.
     *
     * @return JTable que muestra los DVDs
     */
    public JTable getTablaDVDs() {
        return tablaDVDs;
    }

    /**
     * Obtiene el modelo de la tabla de DVDs.
     *
     * @return DefaultTableModel de la tabla
     */
    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    /**
     * Obtiene el botón de agregar DVD.
     *
     * @return JButton para agregar DVDs
     */
    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    /**
     * Obtiene el botón de editar DVD.
     *
     * @return JButton para editar DVDs
     */
    public JButton getBtnEditar() {
        return btnEditar;
    }

    /**
     * Obtiene el botón de eliminar DVD.
     *
     * @return JButton para eliminar DVDs
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }
}