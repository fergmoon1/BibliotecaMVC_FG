package com.biblioteca.view;

import com.biblioteca.model.Revista;
import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class RevistaForm extends JDialog {
    private final JTextField txtId = new JTextField(5);
    private final JTextField txtTitulo = new JTextField(20);
    private final JTextField txtAutor = new JTextField(20);
    private final JTextField txtAnio = new JTextField(5);
    private final JTextField txtEdicion = new JTextField(5);
    private final JTextField txtCategoria = new JTextField(15);
    private boolean confirmado = false;

    public RevistaForm(Revista revista) {
        setTitle(revista == null ? "Nueva Revista" : "Editar Revista");
        setModal(true);
        setLayout(new GridLayout(7, 2, 10, 10));
        setSize(400, 300);
        setLocationRelativeTo(null);

        addField("ID:", txtId);
        addField("Título:", txtTitulo);
        addField("Autor:", txtAutor);
        addField("Año:", txtAnio);
        addField("N° Edición:", txtEdicion);
        addField("Categoría:", txtCategoria);

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

        if (revista != null) {
            txtId.setText(String.valueOf(revista.getId()));
            txtId.setEnabled(false);
            txtTitulo.setText(revista.getTitulo());
            txtAutor.setText(revista.getAutor());
            txtAnio.setText(String.valueOf(revista.getAnioPublicacion()));
            txtEdicion.setText(String.valueOf(revista.getNumeroEdicion()));
            txtCategoria.setText(revista.getCategoria());
        }
    }

    private void addField(String label, JTextField field) {
        add(new JLabel(label));
        add(field);
    }

    public Optional<Revista> mostrarDialogo() {
        setVisible(true);
        if (confirmado) {
            try {
                return Optional.of(new Revista(
                        txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText()),
                        txtTitulo.getText(),
                        txtAutor.getText(),
                        Integer.parseInt(txtAnio.getText()),
                        Integer.parseInt(txtEdicion.getText()),
                        txtCategoria.getText()
                ));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Datos numéricos inválidos", "Error", JOptionPane.ERROR_MESSAGE);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}