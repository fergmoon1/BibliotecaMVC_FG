package com.biblioteca.view;

import com.biblioteca.model.Revista;
import javax.swing.*;
import java.awt.*;

public class RevistaForm extends JDialog {
    private JTextField txtTitulo;
    private JTextField txtEditor;
    private JTextField txtAnio;
    private JTextField txtEdicion;
    private JTextField txtCategoria;

    public RevistaForm(Revista revista) {
        setTitle("Formulario de Revista");
        setModal(true);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents(revista);
        layoutComponents();
    }

    private void initComponents(Revista revista) {
        txtTitulo = new JTextField(20);
        txtEditor = new JTextField(20);
        txtAnio = new JTextField(20);
        txtEdicion = new JTextField(20);
        txtCategoria = new JTextField(20);

        if (revista != null) {
            txtTitulo.setText(revista.getTitulo());
            txtEditor.setText(revista.getAutor());
            txtAnio.setText(String.valueOf(revista.getAnioPublicacion()));
            txtEdicion.setText(String.valueOf(revista.getNumeroEdicion()));
            txtCategoria.setText(revista.getCategoria());
        }
    }

    private void layoutComponents() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Título:"));
        panel.add(txtTitulo);
        panel.add(new JLabel("Editor:"));
        panel.add(txtEditor);
        panel.add(new JLabel("Año:"));
        panel.add(txtAnio);
        panel.add(new JLabel("Número de Edición:"));
        panel.add(txtEdicion);
        panel.add(new JLabel("Categoría:"));
        panel.add(txtCategoria);

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnOk = new JButton("Aceptar");
        JButton btnCancel = new JButton("Cancelar");

        btnOk.addActionListener(e -> {
            if (validarCampos()) {
                dispose();
            }
        });

        btnCancel.addActionListener(e -> dispose());

        buttonPanel.add(btnOk);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public boolean mostrarDialogo() {
        setVisible(true);
        return validarCampos();
    }

    private boolean validarCampos() {
        try {
            if (txtTitulo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El título es requerido", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            Integer.parseInt(txtAnio.getText().trim());
            Integer.parseInt(txtEdicion.getText().trim());

            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Año y Número de Edición deben ser valores numéricos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public Revista getRevista() throws NumberFormatException {
        return new Revista(
                0, // ID se genera al guardar
                txtTitulo.getText().trim(),
                txtEditor.getText().trim(),
                Integer.parseInt(txtAnio.getText().trim()),
                Integer.parseInt(txtEdicion.getText().trim()),
                txtCategoria.getText().trim()
        );
    }
}