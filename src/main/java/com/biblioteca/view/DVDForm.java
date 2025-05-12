package com.biblioteca.view;

import com.biblioteca.model.DVD;
import javax.swing.*;
import java.awt.*;

public class DVDForm extends JDialog {
    private boolean confirmado = false;
    private final JTextField txtTitulo;
    private final JTextField txtDirector;
    private final JTextField txtAnio;
    private final JTextField txtDuracion;
    private final JTextField txtGenero;

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
        add(panel, BorderLayout.CENTER);
        return panel;
    }

    private void crearComponentes(JPanel panel, DVD dvd) {
        txtTitulo = crearCampo(panel, "Título:", dvd != null ? dvd.getTitulo() : "");
        txtDirector = crearCampo(panel, "Director:", dvd != null ? dvd.getAutor() : "");
        txtAnio = crearCampo(panel, "Año:", dvd != null ? String.valueOf(dvd.getAnioPublicacion()) : "");
        txtDuracion = crearCampo(panel, "Duración (min):", dvd != null ? String.valueOf(dvd.getDuracion()) : "");
        txtGenero = crearCampo(panel, "Género:", dvd != null ? dvd.getGenero() : "");
    }

    private JTextField crearCampo(JPanel panel, String etiqueta, String valor) {
        panel.add(new JLabel(etiqueta));
        JTextField campo = new JTextField(20);
        campo.setText(valor);
        panel.add(campo);
        return campo;
    }

    private void configurarBotones(JPanel panelPrincipal) {
        JButton btnOk = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnOk.addActionListener(e -> manejarGuardar());
        btnCancelar.addActionListener(e -> manejarCancelar());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnOk);
        buttonPanel.add(btnCancelar);
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