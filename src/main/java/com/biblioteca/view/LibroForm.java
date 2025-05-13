package com.biblioteca.view;

import com.biblioteca.model.Libro;

import javax.swing.*;
import java.awt.*;

public class LibroForm extends JDialog {

    private final JTextField txtId = new JTextField(5);
    private final JTextField txtTitulo = new JTextField(20);
    private final JTextField txtAutor = new JTextField(20);
    private final JTextField txtAnio = new JTextField(5);
    private final JTextField txtIsbn = new JTextField(15);
    private final JTextField txtPaginas = new JTextField(5);
    private final JTextField txtGenero = new JTextField(15);
    private final JTextField txtEditorial = new JTextField(15);

    private boolean confirmado = false;

    public LibroForm(Libro libro) {
        setTitle(libro == null ? "Agregar Libro" : "Editar Libro");
        setModal(true);
        setLayout(new GridLayout(9, 2, 5, 5));
        setSize(400, 300);
        setLocationRelativeTo(null);

        add(new JLabel("ID:"));
        add(txtId);
        add(new JLabel("Título:"));
        add(txtTitulo);
        add(new JLabel("Autor:"));
        add(txtAutor);
        add(new JLabel("Año de publicación:"));
        add(txtAnio);
        add(new JLabel("ISBN:"));
        add(txtIsbn);
        add(new JLabel("N° páginas:"));
        add(txtPaginas);
        add(new JLabel("Género:"));
        add(txtGenero);
        add(new JLabel("Editorial:"));
        add(txtEditorial);

        JButton btnOk = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnOk);
        add(btnCancelar);

        btnOk.addActionListener(e -> {
            confirmado = true;
            setVisible(false);
        });

        btnCancelar.addActionListener(e -> {
            confirmado = false;
            setVisible(false);
        });

        if (libro != null) {
            txtId.setText(String.valueOf(libro.getId()));
            txtId.setEnabled(false); // ID no editable al editar
            txtTitulo.setText(libro.getTitulo());
            txtAutor.setText(libro.getAutor());
            txtAnio.setText(String.valueOf(libro.getAnio()));
            txtIsbn.setText(libro.getIsbn());
            txtPaginas.setText(String.valueOf(libro.getNumeroPaginas()));
            txtGenero.setText(libro.getGenero());
            txtEditorial.setText(libro.getEditorial());
        }
    }

    public boolean mostrarDialogo() {
        setVisible(true);
        return confirmado;
    }

    public Libro getLibro() {
        return new Libro(
                Integer.parseInt(txtId.getText()),
                txtTitulo.getText(),
                txtAutor.getText(),
                Integer.parseInt(txtAnio.getText()),
                txtIsbn.getText(),
                Integer.parseInt(txtPaginas.getText()),
                txtGenero.getText(),
                txtEditorial.getText()
        );
    }
}

