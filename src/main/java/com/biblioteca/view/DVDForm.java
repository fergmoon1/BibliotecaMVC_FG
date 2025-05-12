package com.biblioteca.view;

import com.biblioteca.model.DVD;
import javax.swing.*;
import java.awt.*;

public class DVDForm extends JDialog {
    private boolean confirmado = false;
    private JTextField txtTitulo;
    private JTextField txtDirector;
    private JTextField txtAnio;
    private JTextField txtDuracion;
    private JTextField txtGenero;

    public DVDForm(JFrame parent, DVD dvd) {
        super(parent, dvd == null ? "Nuevo DVD" : "Editar DVD", true);
        configurarVentana(parent);
        JPanel panelPrincipal = crearPanelPrincipal();
        crearComponentes(panelPrincipal, dvd);
        configurarBotones(panelPrincipal);
    }

    private void configurarVentana(JFrame parent) {
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    private void crearComponentes(JPanel panel, DVD dvd) {
        // Título
        panel.add(new JLabel("Título:"));
        txtTitulo = new JTextField(20);
        if (dvd != null) txtTitulo.setText(dvd.getTitulo());
        panel.add(txtTitulo);

        // Director
        panel.add(new JLabel("Director:"));
        txtDirector = new JTextField(20);
        if (dvd != null) txtDirector.setText(dvd.getAutor());
        panel.add(txtDirector);

        // Año
        panel.add(new JLabel("Año:"));
        txtAnio = new JTextField(20);
        if (dvd != null) txtAnio.setText(String.valueOf(dvd.getAnioPublicacion()));
        panel.add(txtAnio);

        // Duración
        panel.add(new JLabel("Duración (min):"));
        txtDuracion = new JTextField(20);
        if (dvd != null) txtDuracion.setText(String.valueOf(dvd.getDuracion()));
        panel.add(txtDuracion);

        // Género
        panel.add(new JLabel("Género:"));
        txtGenero = new JTextField(20);
        if (dvd != null) txtGenero.setText(dvd.getGenero());
        panel.add(txtGenero);
    }

    private void configurarBotones(JPanel panelPrincipal) {
        JButton btnOk = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnOk.addActionListener(e -> manejarGuardar());
        btnCancelar.addActionListener(e -> manejarCancelar());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnOk);
        buttonPanel.add(btnCancelar);

        add(panelPrincipal, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void manejarGuardar() {
        if (validarCampos()) {
            confirmado = true;
            dispose();
        }
    }

    private void manejarCancelar() {
        confirmado = false;
        dispose();
    }

    public boolean mostrarDialogo() {
        setVisible(true);
        return confirmado;
    }

    public DVD getDVD() {
        try {
            return new DVD(
                    0, // ID temporal
                    txtTitulo.getText().trim(),
                    txtDirector.getText().trim(),
                    Integer.parseInt(txtAnio.getText().trim()),
                    Integer.parseInt(txtDuracion.getText().trim()),
                    txtGenero.getText().trim()
            );
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean validarCampos() {
        if (camposVacios()) {
            mostrarError("Todos los campos son obligatorios", "Error de validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!camposNumericosValidos()) {
            mostrarError("Año y Duración deben ser números válidos", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean camposVacios() {
        return txtTitulo.getText().trim().isEmpty() ||
                txtDirector.getText().trim().isEmpty() ||
                txtAnio.getText().trim().isEmpty() ||
                txtDuracion.getText().trim().isEmpty() ||
                txtGenero.getText().trim().isEmpty();
    }

    private boolean camposNumericosValidos() {
        try {
            Integer.parseInt(txtAnio.getText().trim());
            Integer.parseInt(txtDuracion.getText().trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void mostrarError(String mensaje, String titulo, int tipoMensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipoMensaje);
    }
}