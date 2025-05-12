package com.biblioteca.view;

import com.biblioteca.model.DVD;
import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class DVDForm extends JDialog {
    private final JTextField txtId = new JTextField(5);
    private final JTextField txtTitulo = new JTextField(20);
    private final JTextField txtAutor = new JTextField(20);
    private final JTextField txtAnio = new JTextField(5);
    private final JTextField txtDuracion = new JTextField(5);
    private final JTextField txtGenero = new JTextField(15);
    private boolean confirmado = false;

    public DVDForm(DVD dvd) {
        setTitle(dvd == null ? "Nuevo DVD" : "Editar DVD");
        setModal(true);
        setLayout(new GridLayout(7, 2, 10, 10));
        setSize(400, 300);
        setLocationRelativeTo(null);

        addField("ID:", txtId);
        addField("Título:", txtTitulo);
        addField("Autor:", txtAutor);
        addField("Año:", txtAnio);
        addField("Duración (min):", txtDuracion);
        addField("Género:", txtGenero);

        JButton btnOk = new JButton("Guardar");
        JButton btnCancel = new JButton("Cancelar");

        add(btnOk);
        add(btnCancel);

        btnOk.addActionListener(e -> {
            confirmado = true;
            setVisible(false);
        });

        btnCancel.addActionListener(e -> {
            confirmado = false;
            setVisible(false);
        });

        if (dvd != null) {
            txtId.setText(String.valueOf(dvd.getId()));
            txtId.setEnabled(false);
            txtTitulo.setText(dvd.getTitulo());
            txtAutor.setText(dvd.getAutor());
            txtAnio.setText(String.valueOf(dvd.getAnioPublicacion()));
            txtDuracion.setText(String.valueOf(dvd.getDuracion()));
            txtGenero.setText(dvd.getGenero());
        }
    }

    private void addField(String label, JTextField field) {
        add(new JLabel(label));
        add(field);
    }

    public Optional<DVD> mostrarDialogo() {
        setVisible(true);
        if (confirmado) {
            try {
                return Optional.of(new DVD(
                        txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText()),
                        txtTitulo.getText(),
                        txtAutor.getText(),
                        Integer.parseInt(txtAnio.getText()),
                        Integer.parseInt(txtDuracion.getText()),
                        txtGenero.getText()
                ));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Datos numéricos inválidos", "Error", JOptionPane.ERROR_MESSAGE);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}