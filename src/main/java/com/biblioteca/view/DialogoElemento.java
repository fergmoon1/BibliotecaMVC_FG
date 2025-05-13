package com.biblioteca.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.biblioteca.model.*;
import com.biblioteca.controller.BibliotecaController;

public class DialogoElemento extends JDialog {
    private BibliotecaController controller;
    private ElementoBiblioteca elemento;
    private boolean esEdicion;

    private JTextField txtId, txtTitulo, txtAutor, txtAno, txtExtra1, txtExtra2;
    private JLabel lblExtra1, lblExtra2;
    private JComboBox<String> comboTipo;

    public DialogoElemento(JFrame parent, BibliotecaController controller, boolean esEdicion, ElementoBiblioteca elemento) {
        super(parent, esEdicion ? "Editar Elemento" : "Agregar Elemento", true);
        this.controller = controller;
        this.esEdicion = esEdicion;
        this.elemento = elemento;

        setSize(400, 350);
        setLocationRelativeTo(parent);
        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new GridLayout(0, 2, 5, 5));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Combo para seleccionar tipo de elemento
        panelPrincipal.add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[]{"DVD", "Libro", "Revista"});
        comboTipo.addActionListener(this::cambiarCamposPorTipo);
        panelPrincipal.add(comboTipo);

        // Campos comunes
        panelPrincipal.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelPrincipal.add(txtId);

        panelPrincipal.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelPrincipal.add(txtTitulo);

        panelPrincipal.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panelPrincipal.add(txtAutor);

        panelPrincipal.add(new JLabel("Año:"));
        txtAno = new JTextField();
        panelPrincipal.add(txtAno);

        // Campos dinámicos (se actualizan según el tipo)
        lblExtra1 = new JLabel();
        panelPrincipal.add(lblExtra1);
        txtExtra1 = new JTextField();
        panelPrincipal.add(txtExtra1);

        lblExtra2 = new JLabel();
        panelPrincipal.add(lblExtra2);
        txtExtra2 = new JTextField();
        panelPrincipal.add(txtExtra2);

        // Botones
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(this::guardarElemento);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnCancelar);
        panelBotones.add(btnAceptar);

        add(panelPrincipal, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Si es edición, cargar datos del elemento
        if (esEdicion && elemento != null) {
            cargarDatosElemento();
        } else {
            comboTipo.setSelectedIndex(0);
        }
    }

    private void cambiarCamposPorTipo(ActionEvent e) {
        String tipo = (String) comboTipo.getSelectedItem();
        switch (tipo) {
            case "DVD":
                lblExtra1.setText("Duración (min):");
                lblExtra2.setText("Género:");
                break;
            case "Libro":
                lblExtra1.setText("ISBN:");
                lblExtra2.setText("Páginas:");
                break;
            case "Revista":
                lblExtra1.setText("Edición:");
                lblExtra2.setText("Categoría:");
                break;
        }
    }

    private void cargarDatosElemento() {
        if (elemento instanceof DVD) {
            comboTipo.setSelectedItem("DVD");
            DVD dvd = (DVD) elemento;
            txtId.setText(String.valueOf(dvd.getId()));
            txtTitulo.setText(dvd.getTitulo());
            txtAutor.setText(dvd.getAutor());
            txtAno.setText(String.valueOf(dvd.getAnoPublicacion()));
            txtExtra1.setText(String.valueOf(dvd.getDuracion()));
            txtExtra2.setText(dvd.getGenero());
        } else if (elemento instanceof Libro) {
            comboTipo.setSelectedItem("Libro");
            Libro libro = (Libro) elemento;
            txtId.setText(String.valueOf(libro.getId()));
            txtTitulo.setText(libro.getTitulo());
            txtAutor.setText(libro.getAutor());
            txtAno.setText(String.valueOf(libro.getAnoPublicacion()));
            txtExtra1.setText(libro.getIsbn());
            txtExtra2.setText(String.valueOf(libro.getNumeroPaginas()));
        } else if (elemento instanceof Revista) {
            comboTipo.setSelectedItem("Revista");
            Revista revista = (Revista) elemento;
            txtId.setText(String.valueOf(revista.getId()));
            txtTitulo.setText(revista.getTitulo());
            txtAutor.setText(""); // Revistas no tienen autor
            txtAno.setText(String.valueOf(revista.getAnoPublicacion()));
            txtExtra1.setText(String.valueOf(revista.getNumeroEdicion()));
            txtExtra2.setText(revista.getCategoria());
        }
    }

    private void guardarElemento(ActionEvent e) {
        try {
            int id = Integer.parseInt(txtId.getText());
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            int ano = Integer.parseInt(txtAno.getText());

            String tipo = (String) comboTipo.getSelectedItem();
            switch (tipo) {
                case "DVD":
                    int duracion = Integer.parseInt(txtExtra1.getText());
                    String genero = txtExtra2.getText();
                    DVD dvd = new DVD(id, titulo, autor, ano, duracion, genero);
                    if (esEdicion) {
                        controller.actualizarElemento(dvd);
                    } else {
                        controller.agregarDVD(dvd);
                    }
                    break;

                case "Libro":
                    String isbn = txtExtra1.getText();
                    int paginas = Integer.parseInt(txtExtra2.getText());
                    Libro libro = new Libro(id, titulo, autor, ano, isbn, paginas, "", "");
                    if (esEdicion) {
                        controller.actualizarElemento(libro);
                    } else {
                        controller.agregarLibro(libro);
                    }
                    break;

                case "Revista":
                    int edicion = Integer.parseInt(txtExtra1.getText());
                    String categoria = txtExtra2.getText();
                    Revista revista = new Revista(id, titulo, ano, edicion, categoria);
                    if (esEdicion) {
                        controller.actualizarElemento(revista);
                    } else {
                        controller.agregarRevista(revista);
                    }
                    break;
            }

            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores válidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}