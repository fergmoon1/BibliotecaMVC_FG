package com.biblioteca.view;

import com.biblioteca.controller.BibliotecaController;
import com.biblioteca.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class BibliotecaView extends JFrame {
    private BibliotecaController controller;
    private JTable tablaElementos;
    private JTextField txtBuscarGenero;
    private JButton btnBuscar, btnAgregar, btnEditar, btnEliminar, btnActualizar;
    private JComboBox<String> comboFiltro;
    private DefaultTableModel tableModel;

    public BibliotecaView(BibliotecaController controller) {
        this.controller = controller;
        configurarVentana();
        initComponents();
        cargarDatosIniciales();
    }

    private void configurarVentana() {
        setTitle("Sistema de Biblioteca");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Barra de menú
        JMenuBar menuBar = crearMenuBar();
        setJMenuBar(menuBar);

        // Panel de búsqueda
        JPanel panelBusqueda = crearPanelBusqueda();
        panelPrincipal.add(panelBusqueda, BorderLayout.NORTH);

        // Tabla de elementos
        tablaElementos = new JTable();
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Título", "Autor", "Año", "Duración", "Género"}, 0);
        tablaElementos.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(tablaElementos);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones CRUD
        JPanel panelBotones = crearPanelBotones();
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JMenuBar crearMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menú Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemSalir);

        // Menú Catálogo
        JMenu menuCatalogo = new JMenu("Catálogo");
        JMenuItem itemVerDVDs = new JMenuItem("Ver DVDs");
        itemVerDVDs.addActionListener(e -> controller.mostrarSoloDVDs());
        menuCatalogo.add(itemVerDVDs);

        JMenuItem itemVerLibros = new JMenuItem("Ver Libros");
        itemVerLibros.addActionListener(e -> controller.mostrarSoloLibros());
        menuCatalogo.add(itemVerLibros);

        JMenuItem itemVerRevistas = new JMenuItem("Ver Revistas");
        itemVerRevistas.addActionListener(e -> controller.mostrarSoloRevistas());
        menuCatalogo.add(itemVerRevistas);

        JMenuItem itemVerTodos = new JMenuItem("Ver Todos");
        itemVerTodos.addActionListener(e -> controller.mostrarTodos());
        menuCatalogo.add(itemVerTodos);

        // Menú Ayuda
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de");
        itemAcercaDe.addActionListener(e -> mostrarAcercaDe());
        menuAyuda.add(itemAcercaDe);

        menuBar.add(menuArchivo);
        menuBar.add(menuCatalogo);
        menuBar.add(menuAyuda);

        return menuBar;
    }

    private JPanel crearPanelBusqueda() {
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelBusqueda.add(new JLabel("Buscar por género:"));
        txtBuscarGenero = new JTextField(20);
        panelBusqueda.add(txtBuscarGenero);

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarPorGenero());
        panelBusqueda.add(btnBuscar);

        // Filtro por tipo
        comboFiltro = new JComboBox<>(new String[]{"Todos", "DVDs", "Libros", "Revistas"});
        comboFiltro.addActionListener(e -> filtrarPorTipo());
        panelBusqueda.add(new JLabel("Filtrar:"));
        panelBusqueda.add(comboFiltro);

        return panelBusqueda;
    }

    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel();

        btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> mostrarDialogoAgregar());
        panelBotones.add(btnAgregar);

        btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> mostrarDialogoEditar());
        panelBotones.add(btnEditar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarElemento());
        panelBotones.add(btnEliminar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> actualizarTabla());
        panelBotones.add(btnActualizar);

        return panelBotones;
    }

    private void cargarDatosIniciales() {
        actualizarTabla();
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);
        List<ElementoBiblioteca> elementos = controller.getTodosElementos();
        for (ElementoBiblioteca elemento : elementos) {
            if (elemento instanceof DVD) {
                tableModel.addRow(((DVD) elemento).toTableRow());
            } else if (elemento instanceof Libro) {
                tableModel.addRow(((Libro) elemento).toTableRow());
            } else if (elemento instanceof Revista) {
                tableModel.addRow(((Revista) elemento).toTableRow());
            }
        }
    }

    private void buscarPorGenero() {
        String genero = txtBuscarGenero.getText().trim();
        if (!genero.isEmpty()) {
            tableModel.setRowCount(0);
            List<ElementoBiblioteca> resultados = controller.buscarPorGenero(genero);
            for (ElementoBiblioteca elemento : resultados) {
                if (elemento instanceof DVD) {
                    tableModel.addRow(((DVD) elemento).toTableRow());
                } else if (elemento instanceof Libro) {
                    tableModel.addRow(((Libro) elemento).toTableRow());
                } else if (elemento instanceof Revista) {
                    tableModel.addRow(((Revista) elemento).toTableRow());
                }
            }
        } else {
            actualizarTabla();
        }
    }

    private void filtrarPorTipo() {
        String tipo = (String) comboFiltro.getSelectedItem();
        switch (tipo) {
            case "DVDs":
                controller.mostrarSoloDVDs();
                break;
            case "Libros":
                controller.mostrarSoloLibros();
                break;
            case "Revistas":
                controller.mostrarSoloRevistas();
                break;
            default:
                controller.mostrarTodos();
        }
        actualizarTabla();
    }

    private void mostrarDialogoAgregar() {
        DialogoElemento dialogo = new DialogoElemento(this, controller, false, null);
        dialogo.setVisible(true);
        actualizarTabla();
    }

    private void mostrarDialogoEditar() {
        int filaSeleccionada = tablaElementos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int id = (int) tablaElementos.getValueAt(filaSeleccionada, 0);
            ElementoBiblioteca elemento = controller.buscarPorId(id);
            if (elemento != null) {
                DialogoElemento dialogo = new DialogoElemento(this, controller, true, elemento);
                dialogo.setVisible(true);
                actualizarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un elemento para editar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarElemento() {
        int filaSeleccionada = tablaElementos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de eliminar este elemento?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                int id = (int) tablaElementos.getValueAt(filaSeleccionada, 0);
                controller.eliminarElemento(id);
                actualizarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un elemento para eliminar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void mostrarAcercaDe() {
        JOptionPane.showMessageDialog(this,
                "Sistema de Biblioteca\nVersión 1.0\n© 2025",
                "Acerca de",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            BibliotecaController controller = new BibliotecaController();
            BibliotecaView view = new BibliotecaView(controller);
            view.setVisible(true);
        });
    }
}