package com.biblioteca.view;

import com.biblioteca.model.Revista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RevistaPanel extends JPanel {
    private JTextField txtBuscar;
    private JButton btnBuscar, btnAgregar, btnEditar, btnEliminar, btnActualizar;
    private JTable tablaRevistas;
    private DefaultTableModel modeloTabla;

    public RevistaPanel() {
        // Inicializar componentes
        initComponents();

        // Cargar datos
        cargarRevistas();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior con búsqueda
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Buscar por categoría:"));
        txtBuscar = new JTextField(20);
        panelSuperior.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarRevistas());
        panelSuperior.add(btnBuscar);
        add(panelSuperior, BorderLayout.NORTH);

        // Tabla central
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Título");
        modeloTabla.addColumn("Editorial");
        modeloTabla.addColumn("Año");
        modeloTabla.addColumn("Número de Edición");
        modeloTabla.addColumn("Categoría");

        tablaRevistas = new JTable(modeloTabla);
        tablaRevistas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaRevistas.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tablaRevistas);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarRevista());
        panelInferior.add(btnAgregar);

        btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> editarRevista());
        panelInferior.add(btnEditar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarRevista());
        panelInferior.add(btnEliminar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarRevistas());
        panelInferior.add(btnActualizar);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarRevistas() {
        // Limpiar la tabla
        modeloTabla.setRowCount(0);

        // Obtener todas las revistas
        List<Revista> listaRevistas = Revista.obtenerTodas();

        // Agregar cada revista a la tabla
        for (Revista revista : listaRevistas) {
            modeloTabla.addRow(new Object[]{
                    revista.getId(),
                    revista.getTitulo(),
                    revista.getEditorial(),
                    revista.getAnio(),
                    revista.getNumero(),
                    revista.getCategoria()
            });
        }
    }

    private void buscarRevistas() {
        String termino = txtBuscar.getText().trim();

        // Si el término está vacío, mostrar todas
        if (termino.isEmpty()) {
            cargarRevistas();
            return;
        }

        // Limpiar la tabla
        modeloTabla.setRowCount(0);

        // Buscar revistas por categoría
        List<Revista> resultados = Revista.buscarPorCategoria(termino);

        // Agregar resultados a la tabla
        for (Revista revista : resultados) {
            modeloTabla.addRow(new Object[]{
                    revista.getId(),
                    revista.getTitulo(),
                    revista.getEditorial(),
                    revista.getAnio(),
                    revista.getNumero(),
                    revista.getCategoria()
            });
        }
    }

    private void agregarRevista() {
        // Crear diálogo para agregar revista
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Agregar Revista", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtTitulo = new JTextField();
        JTextField txtEditorial = new JTextField();
        JTextField txtAnio = new JTextField();
        JTextField txtNumeroEdicion = new JTextField();
        JTextField txtCategoria = new JTextField();

        panelForm.add(new JLabel("Título:"));
        panelForm.add(txtTitulo);
        panelForm.add(new JLabel("Editorial:"));
        panelForm.add(txtEditorial);
        panelForm.add(new JLabel("Año:"));
        panelForm.add(txtAnio);
        panelForm.add(new JLabel("Número de Edición:"));
        panelForm.add(txtNumeroEdicion);
        panelForm.add(new JLabel("Categoría:"));
        panelForm.add(txtCategoria);

        dialog.add(panelForm, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                // Validar campos obligatorios
                if (txtTitulo.getText().trim().isEmpty() ||
                        txtEditorial.getText().trim().isEmpty() ||
                        txtAnio.getText().trim().isEmpty() ||
                        txtNumeroEdicion.getText().trim().isEmpty() ||
                        txtCategoria.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Todos los campos son obligatorios",
                            "Error de validación",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar que año y número de edición sean números
                int anio = Integer.parseInt(txtAnio.getText().trim());
                int numeroEdicion = Integer.parseInt(txtNumeroEdicion.getText().trim());

                // Crear y guardar la nueva revista
                Revista nuevaRevista = new Revista(
                        txtTitulo.getText().trim(),
                        txtEditorial.getText().trim(),
                        anio,
                        numeroEdicion,
                        txtCategoria.getText().trim()
                );

                if (nuevaRevista.guardar()) {
                    dialog.dispose();
                    cargarRevistas();
                    JOptionPane.showMessageDialog(this,
                            "Revista agregada correctamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog,
                            "Error al guardar la revista",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "El año y el número de edición deben ser números",
                        "Error de formato",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        dialog.add(panelBotones, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void editarRevista() {
        // Verificar si hay una fila seleccionada
        int filaSeleccionada = tablaRevistas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar una revista para editar",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID de la revista seleccionada
        int id = (int) tablaRevistas.getValueAt(filaSeleccionada, 0);
        Revista revistaSeleccionada = Revista.obtener(id);

        if (revistaSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo encontrar la revista seleccionada",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear diálogo para editar revista
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Editar Revista", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtTitulo = new JTextField(revistaSeleccionada.getTitulo());
        JTextField txtEditorial = new JTextField(revistaSeleccionada.getEditorial());
        JTextField txtAnio = new JTextField(String.valueOf(revistaSeleccionada.getAnio()));
        JTextField txtNumeroEdicion = new JTextField(String.valueOf(revistaSeleccionada.getNumeroEdicion()));
        JTextField txtCategoria = new JTextField(revistaSeleccionada.getCategoria());

        panelForm.add(new JLabel("Título:"));
        panelForm.add(txtTitulo);
        panelForm.add(new JLabel("Editorial:"));
        panelForm.add(txtEditorial);
        panelForm.add(new JLabel("Año:"));
        panelForm.add(txtAnio);
        panelForm.add(new JLabel("Número de Edición:"));
        panelForm.add(txtNumeroEdicion);
        panelForm.add(new JLabel("Categoría:"));
        panelForm.add(txtCategoria);

        dialog.add(panelForm, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                // Validar campos obligatorios
                if (txtTitulo.getText().trim().isEmpty() ||
                        txtEditorial.getText().trim().isEmpty() ||
                        txtAnio.getText().trim().isEmpty() ||
                        txtNumeroEdicion.getText().trim().isEmpty() ||
                        txtCategoria.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Todos los campos son obligatorios",
                            "Error de validación",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar que año y número de edición sean números
                int anio = Integer.parseInt(txtAnio.getText().trim());
                int numeroEdicion = Integer.parseInt(txtNumeroEdicion.getText().trim());

                // Actualizar la revista
                revistaSeleccionada.setTitulo(txtTitulo.getText().trim());
                revistaSeleccionada.setEditorial(txtEditorial.getText().trim());
                revistaSeleccionada.setAnio(anio);
                revistaSeleccionada.setNumeroEdicion(numeroEdicion);
                revistaSeleccionada.setCategoria(txtCategoria.getText().trim());

                if (revistaSeleccionada.guardar()) {
                    dialog.dispose();
                    cargarRevistas();
                    JOptionPane.showMessageDialog(this,
                            "Revista actualizada correctamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog,
                            "Error al actualizar la revista",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "El año y el número de edición deben ser números",
                        "Error de formato",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        dialog.add(panelBotones, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void eliminarRevista() {
        // Verificar si hay una fila seleccionada
        int filaSeleccionada = tablaRevistas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar una revista para eliminar",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID de la revista seleccionada
        int id = (int) tablaRevistas.getValueAt(filaSeleccionada, 0);

        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar esta revista?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (Revista.eliminar(id)) {
                cargarRevistas();
                JOptionPane.showMessageDialog(this,
                        "Revista eliminada correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al eliminar la revista",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}