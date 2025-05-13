package com.biblioteca.view;

import com.biblioteca.model.DVD;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class DVDPanel extends JPanel {
    private JTextField txtBuscar;
    private JButton btnBuscar, btnAgregar, btnEditar, btnEliminar, btnActualizar;
    private JTable tablaDVDs;
    private DefaultTableModel modeloTabla;

    public DVDPanel() {
        // Cargar datos de ejemplo si no hay datos
        DVD.cargarDatosEjemplo();

        // Inicializar componentes
        initComponents();

        // Cargar datos
        cargarDVDs();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior con búsqueda
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Buscar por género:"));
        txtBuscar = new JTextField(20);
        panelSuperior.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarDVDs());
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
        modeloTabla.addColumn("Director");
        modeloTabla.addColumn("Año");
        modeloTabla.addColumn("Duración");
        modeloTabla.addColumn("Género");

        tablaDVDs = new JTable(modeloTabla);
        tablaDVDs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaDVDs.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tablaDVDs);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarDVD());
        panelInferior.add(btnAgregar);

        btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> editarDVD());
        panelInferior.add(btnEditar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarDVD());
        panelInferior.add(btnEliminar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarDVDs());
        panelInferior.add(btnActualizar);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarDVDs() {
        // Limpiar la tabla
        modeloTabla.setRowCount(0);

        // Obtener todos los DVDs
        List<DVD> listaDVDs = DVD.obtenerTodos();

        // Agregar cada DVD a la tabla
        for (DVD dvd : listaDVDs) {
            modeloTabla.addRow(new Object[]{
                    dvd.getId(),
                    dvd.getTitulo(),
                    dvd.getDirector(),
                    dvd.getAnio(),
                    dvd.getDuracion(),
                    dvd.getGenero()
            });
        }
    }

    private void buscarDVDs() {
        String termino = txtBuscar.getText().trim();

        // Si el término está vacío, mostrar todos
        if (termino.isEmpty()) {
            cargarDVDs();
            return;
        }

        // Limpiar la tabla
        modeloTabla.setRowCount(0);

        // Buscar DVDs por género
        List<DVD> resultados = DVD.buscarPorGenero(termino);

        // Agregar resultados a la tabla
        for (DVD dvd : resultados) {
            modeloTabla.addRow(new Object[]{
                    dvd.getId(),
                    dvd.getTitulo(),
                    dvd.getDirector(),
                    dvd.getAnio(),
                    dvd.getDuracion(),
                    dvd.getGenero()
            });
        }
    }

    private void agregarDVD() {
        // Crear diálogo para agregar DVD
        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Agregar DVD", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtTitulo = new JTextField();
        JTextField txtDirector = new JTextField();
        JTextField txtAnio = new JTextField();
        JTextField txtDuracion = new JTextField();
        JTextField txtGenero = new JTextField();

        panelForm.add(new JLabel("Título:"));
        panelForm.add(txtTitulo);
        panelForm.add(new JLabel("Director:"));
        panelForm.add(txtDirector);
        panelForm.add(new JLabel("Año:"));
        panelForm.add(txtAnio);
        panelForm.add(new JLabel("Duración (min):"));
        panelForm.add(txtDuracion);
        panelForm.add(new JLabel("Género:"));
        panelForm.add(txtGenero);

        dialog.add(panelForm, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                // Validar campos obligatorios
                if (txtTitulo.getText().trim().isEmpty() ||
                        txtDirector.getText().trim().isEmpty() ||
                        txtAnio.getText().trim().isEmpty() ||
                        txtDuracion.getText().trim().isEmpty() ||
                        txtGenero.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Todos los campos son obligatorios",
                            "Error de validación",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar que año y duración sean números
                int anio = Integer.parseInt(txtAnio.getText().trim());
                int duracion = Integer.parseInt(txtDuracion.getText().trim());

                // Crear y guardar el nuevo DVD
                DVD nuevoDVD = new DVD(
                        txtTitulo.getText().trim(),
                        txtDirector.getText().trim(),
                        anio,
                        duracion,
                        txtGenero.getText().trim()
                );

                if (nuevoDVD.guardar()) {
                    dialog.dispose();
                    cargarDVDs();
                    JOptionPane.showMessageDialog(this,
                            "DVD agregado correctamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog,
                            "Error al guardar el DVD",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "El año y la duración deben ser números",
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

    private void editarDVD() {
        // Verificar si hay una fila seleccionada
        int filaSeleccionada = tablaDVDs.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un DVD para editar",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID del DVD seleccionado
        int id = (int) tablaDVDs.getValueAt(filaSeleccionada, 0);
        DVD dvdSeleccionado = DVD.obtener(id);

        if (dvdSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo encontrar el DVD seleccionado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear diálogo para editar DVD
        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Editar DVD", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtTitulo = new JTextField(dvdSeleccionado.getTitulo());
        JTextField txtDirector = new JTextField(dvdSeleccionado.getDirector());
        JTextField txtAnio = new JTextField(String.valueOf(dvdSeleccionado.getAnio()));
        JTextField txtDuracion = new JTextField(String.valueOf(dvdSeleccionado.getDuracion()));
        JTextField txtGenero = new JTextField(dvdSeleccionado.getGenero());

        panelForm.add(new JLabel("Título:"));
        panelForm.add(txtTitulo);
        panelForm.add(new JLabel("Director:"));
        panelForm.add(txtDirector);
        panelForm.add(new JLabel("Año:"));
        panelForm.add(txtAnio);
        panelForm.add(new JLabel("Duración (min):"));
        panelForm.add(txtDuracion);
        panelForm.add(new JLabel("Género:"));
        panelForm.add(txtGenero);

        dialog.add(panelForm, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                // Validar campos obligatorios
                if (txtTitulo.getText().trim().isEmpty() ||
                        txtDirector.getText().trim().isEmpty() ||
                        txtAnio.getText().trim().isEmpty() ||
                        txtDuracion.getText().trim().isEmpty() ||
                        txtGenero.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Todos los campos son obligatorios",
                            "Error de validación",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar que año y duración sean números
                int anio = Integer.parseInt(txtAnio.getText().trim());
                int duracion = Integer.parseInt(txtDuracion.getText().trim());

                // Actualizar el DVD
                dvdSeleccionado.setTitulo(txtTitulo.getText().trim());
                dvdSeleccionado.setDirector(txtDirector.getText().trim());
                dvdSeleccionado.setAnio(anio);
                dvdSeleccionado.setDuracion(duracion);
                dvdSeleccionado.setGenero(txtGenero.getText().trim());

                if (dvdSeleccionado.guardar()) {
                    dialog.dispose();
                    cargarDVDs();
                    JOptionPane.showMessageDialog(this,
                            "DVD actualizado correctamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog,
                            "Error al actualizar el DVD",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "El año y la duración deben ser números",
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

    private void eliminarDVD() {
        // Verificar si hay una fila seleccionada
        int filaSeleccionada = tablaDVDs.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un DVD para eliminar",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID del DVD seleccionado
        int id = (int) tablaDVDs.getValueAt(filaSeleccionada, 0);

        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar este DVD?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (DVD.eliminar(id)) {
                cargarDVDs();
                JOptionPane.showMessageDialog(this,
                        "DVD eliminado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al eliminar el DVD",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}