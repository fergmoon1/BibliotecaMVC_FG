package com.biblioteca.view;

import com.biblioteca.model.Revista;
import javax.swing.*;
import java.awt.*;

public class RevistaForm extends JPanel {
    private JTextField txtTitulo;
    private JTextField txtEditor;
    private JTextField txtAnio;
    private JTextField txtEdicion;
    private JTextField txtCategoria;

    public RevistaForm(Revista revista) {
        setLayout(new GridLayout(5, 2, 10, 10));

        // Componentes del formulario
        add(new JLabel("Título:"));
        txtTitulo = new JTextField(20);
        if (revista != null) txtTitulo.setText(revista.getTitulo());
        add(txtTitulo);

        add(new JLabel("Editor:"));
        txtEditor = new JTextField(20);
        if (revista != null) txtEditor.setText(revista.getAutor());
        add(txtEditor);

        add(new JLabel("Año:"));
        txtAnio = new JTextField(20);
        if (revista != null) txtAnio.setText(String.valueOf(revista.getAnioPublicacion()));
        add(txtAnio);

        add(new JLabel("Número de Edición:"));
        txtEdicion = new JTextField(20);
        if (revista != null) txtEdicion.setText(String.valueOf(revista.getNumeroEdicion()));
        add(txtEdicion);

        add(new JLabel("Categoría:"));
        txtCategoria = new JTextField(20);
        if (revista != null) txtCategoria.setText(revista.getCategoria());
        add(txtCategoria);
    }

    public boolean mostrarDialogo(Component parent) {
        int result = JOptionPane.showConfirmDialog(
                parent,
                this,
                "Formulario de Revista",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        return result == JOptionPane.OK_OPTION;
    }

    public Revista getRevista() {
        return new Revista(
                0, // ID se genera al guardar
                txtTitulo.getText(),
                txtEditor.getText(),
                Integer.parseInt(txtAnio.getText()),
                Integer.parseInt(txtEdicion.getText()),
                txtCategoria.getText()
        );
    }
}