package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaCatalogo extends JFrame {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> comboGeneros;
    private JButton btnBuscar, btnAgregar, btnEditar, btnEliminar, btnActualizar;

    public VistaCatalogo() {
        configurarVentana();
        initComponentes();
    }

    private void configurarVentana() {
        setTitle("Catálogo - Sistema de Biblioteca");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Panel superior (filtro por género)
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.add(new JLabel("Buscar por género:"));
        comboGeneros = new JComboBox<>(new String[]{"Todos", "Drama", "Ciencia Ficción", "Fantasía", "Educación"});
        panelFiltro.add(comboGeneros);
        btnBuscar = new JButton("Buscar");
        panelFiltro.add(btnBuscar);

        // Tabla (modelo con columnas como en tu imagen)
        String[] columnas = {"ID", "Título", "Autor", "Año", "Duración", "Género"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable directamente
            }
        };
        tabla = new JTable(modeloTabla);
        cargarDatosEjemplo(); // Datos de prueba (eliminar en producción)

        // Panel inferior (botones CRUD)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        // Ensamblar ventana
        panelPrincipal.add(panelFiltro, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    // Método temporal con datos de ejemplo (simulando BD)
    private void cargarDatosEjemplo() {
        Object[][] datos = {
                {11, "El Padrino", "Francis Ford Coppola", 1972, 175, "Drama"},
                {12, "Matrix", "Wachowski Brothers", 1999, 136, "Ciencia Ficción"},
                {13, "El Señor de los Anillos", "Peter Jackson", 2001, 132, "Fantasía"},
                {14, "Interestelar", "Christopher Nolan", 2014, 169, "Ciencia Ficción"},
                {15, "Parásitos", "Bong Joon-ho", 2019, 132, "Drama"},
                {16, "La tumba de las luciérnagas", "Isao Takahata", 1988, 89, "Anime Drama Histórico"},
                {17, "Sena 1", "Yo", 2025, 90, "Educación"}
        };
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }

    // Getters para botones (usados por el controlador)
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JComboBox<String> getComboGeneros() {
        return comboGeneros;
    }

    public JTable getTabla() {
        return tabla;
    }
    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }
}