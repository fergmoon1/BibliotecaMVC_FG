package com.biblioteca.view;

import com.biblioteca.model.Libro;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class LibroPanel extends JPanel {
    private JTextField txtBuscar;
    private JButton btnBuscar, btnAgregar, btnEditar, btnEliminar, btnActualizar;
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    public LibroPanel() {
        // Cargar datos de ejemplo si no hay datos
        Libro.cargarDatosEjemplo();

        // Inicializar componentes
        initComponents();

        // Cargar datos
        cargarLibros();
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
        btnBuscar.addActionListener(e -> buscarLibros());
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
        modeloTabla.addColumn("Autor");
        modeloTabla.addColumn("Año");
        modeloTabla.addColumn("Páginas");
        modeloTabla.addColumn("Género");
        modeloTabla.addColumn("Editorial");
        modeloTabla.addColumn("ISBN");

        tablaLibros = new JTable(modeloTabla);
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaLibros.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tablaLibros);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarLibro());
        panelInferior.add(btnAgregar);

        btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> editarLibro());
        panelInferior.add(btnEditar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarLibro());
        panelInferior.add(btnEliminar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarLibros());
        panelInferior.add(btnActualizar);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarLibros() {
        // Limpiar la tabla
        modeloTabla.setRowCount(0);

        // Obtener todos los libros
        List<Libro> listaLibros = Libro.obtenerTodos();

        // Agregar cada libro a la tabla
        for (Libro libro : listaLibros) {
            modeloTabla.addRow(new Object[]{
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getAnio(),
                    libro.getNumeroPaginas(),
                    libro.getGenero(),
                    libro.getEditorial(),
                    libro.getIsbn()
            });
        }
    }

    private void buscarLibros() {
        String termino = txtBuscar.getText().trim();

        // Si el término está vacío, mostrar todos
        if (termino.isEmpty()) {
            cargarLibros();
            return;
        }

        // Limpiar la tabla
        modeloTabla.setRowCount(0);

        // Buscar libros por género
        List<Libro> resultados = Libro.buscarPorGenero(termino);

        // Agregar resultados a la tabla
        for (Libro libro : resultados) {
            modeloTabla.addRow(new Object[]{
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getAnio(),
                    libro.getNumeroPaginas(),
                    libro.getGenero(),
                    libro.getEditorial(),
                    libro.getIsbn()
            });
        }
    }

    private void agregarLibro() {
        // Crear diálogo para agregar libro
        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Agregar Libro", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);

        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(8, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtTitulo = new JTextField();
        JTextField txtAutor = new JTextField();
        JTextField txtAnio = new JTextField();
        JTextField txtPaginas = new JTextField();
        JTextField txtGenero = new JTextField();
        JTextField txtEditorial = new JTextField();
        JTextField txtIsbn = new JTextField();

        panelForm.add(new JLabel("Título:"));
        panelForm.add(txtTitulo);
        panelForm.add(new JLabel("Autor:"));
        panelForm.add(txtAutor);
        panelForm.add(new JLabel("Año:"));
        panelForm.add(txtAnio);
        panelForm.add(new JLabel("Número de páginas:"));
        panelForm.add(txtPaginas);
        panelForm.add(new JLabel("Género:"));
        panelForm.add(txtGenero);
        panelForm.add(new JLabel("Editorial:"));
        panelForm.add(txtEditorial);
        panelForm.add(new JLabel("ISBN:"));
        panelForm.add(txtIsbn);

        dialog.add(panelForm, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                // Validar campos obligatorios
                if (txtTitulo.getText().trim().isEmpty() ||
                        txtAutor.getText().trim().isEmpty() ||
                        txtAnio.getText().trim().isEmpty() ||
                        txtPaginas.getText().trim().isEmpty() ||
                        txtGenero.getText().trim().isEmpty() ||
                        txtEditorial.getText().trim().isEmpty() ||
                        txtIsbn.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Todos los campos son obligatorios",
                            "Error de validación",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar que año y páginas sean números
                int anio = Integer.parseInt(txtAnio.getText().trim());
                int paginas = Integer.parseInt(txtPaginas.getText().trim());

                // Crear y guardar el nuevo libro
                Libro nuevoLibro = new Libro(
                        txtTitulo.getText().trim(),
                        txtAutor.getText().trim(),
                        anio,
                        paginas,
                        txtGenero.getText().trim(),
                        txtEditorial.getText().trim(),
                        txtIsbn.getText().trim()
                );

                if (nuevoLibro.guardar()) {
                    dialog.dispose();
                    cargarLibros();
                    JOptionPane.showMessageDialog(this,
                            "Libro agregado correctamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog,
                            "Error al guardar el libro",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "El año y el número de páginas deben ser números",
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

    private void editarLibro() {
        // Verificar si hay una fila seleccionada
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un libro para editar",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID del libro seleccionado
        int id = (int) tablaLibros.getValueAt(filaSeleccionada, 0);
        Libro libroSeleccionado = Libro.obtener(id);

        if (libroSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo encontrar el libro seleccionado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear diálogo para editar libro
        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Editar Libro", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);

        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(8, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtTitulo = new JTextField(libroSeleccionado.getTitulo());
        JTextField txtAutor = new JTextField(libroSeleccionado.getAutor());
        JTextField txtAnio = new JTextField(String.valueOf(libroSeleccionado.getAnio()));
        JTextField txtPaginas = new JTextField(String.valueOf(libroSeleccionado.getNumeroPaginas()));
        JTextField txtGenero = new JTextField(libroSeleccionado.getGenero());
        JTextField txtEditorial = new JTextField(libroSeleccionado.getEditorial());
        JTextField txtIsbn = new JTextField(libroSeleccionado.getIsbn());

        panelForm.add(new JLabel("Título:"));
        panelForm.add(txtTitulo);
        panelForm.add(new JLabel("Autor:"));
        panelForm.add(txtAutor);
        panelForm.add(new JLabel("Año:"));
        panelForm.add(txtAnio);
        panelForm.add(new JLabel("Número de páginas:"));
        panelForm.add(txtPaginas);
        panelForm.add(new JLabel("Género:"));
        panelForm.add(txtGenero);
        panelForm.add(new JLabel("Editorial:"));
        panelForm.add(txtEditorial);
        panelForm.add(new JLabel("ISBN:"));
        panelForm.add(txtIsbn);

        dialog.add(panelForm, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                // Validar campos obligatorios
                if (txtTitulo.getText().trim().isEmpty() ||
                        txtAutor.getText().trim().isEmpty() ||
                        txtAnio.getText().trim().isEmpty() ||
                        txtPaginas.getText().trim().isEmpty() ||
                        txtGenero.getText().trim().isEmpty() ||
                        txtEditorial.getText().trim().isEmpty() ||
                        txtIsbn.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Todos los campos son obligatorios",
                            "Error de validación",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar que año y páginas sean números
                int anio = Integer.parseInt(txtAnio.getText().trim());
                int paginas = Integer.parseInt(txtPaginas.getText().trim());

                // Actualizar el libro
                libroSeleccionado.setTitulo(txtTitulo.getText().trim());
                libroSeleccionado.setAutor(txtAutor.getText().trim());
                libroSeleccionado.setAnio(anio);
                libroSeleccionado.setNumeroPaginas(paginas);
                libroSeleccionado.setGenero(txtGenero.getText().trim());
                libroSeleccionado.setEditorial(txtEditorial.getText().trim());
                libroSeleccionado.setIsbn(txtIsbn.getText().trim());

                if (libroSeleccionado.guardar()) {
                    dialog.dispose();
                    cargarLibros();
                    JOptionPane.showMessageDialog(this,
                            "Libro actualizado correctamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog,
                            "Error al actualizar el libro",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "El año y el número de páginas deben ser números",
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

    private void eliminarLibro() {
        // Verificar si hay una fila seleccionada
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un libro para eliminar",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID del libro seleccionado
        int id = (int) tablaLibros.getValueAt(filaSeleccionada, 0);

        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar este libro?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (Libro.eliminar(id)) {
                cargarLibros();
                JOptionPane.showMessageDialog(this,
                        "Libro eliminado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al eliminar el libro",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}