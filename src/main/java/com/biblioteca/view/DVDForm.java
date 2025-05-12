package com.biblioteca.view;

import com.biblioteca.model.DVD;
import javax.swing.*;
import java.awt.*;

public class DVDForm extends JPanel {
    private JTextField txtTitulo;
    private JTextField txtDirector;
    private JTextField txtAnio;
    private JTextField txtDuracion;
    private JTextField txtGenero;

    public DVDForm(DVD dvd) {
        setLayout(new GridLayout(5, 2, 10, 10));

        // Componentes del formulario
        add(new JLabel("Título:"));
        txtTitulo = new JTextField(20);
        if (dvd != null) txtTitulo.setText(dvd.getTitulo());
        add(txtTitulo);

        add(new JLabel("Director:"));
        txtDirector = new JTextField(20);
        if (dvd != null) txtDirector.setText(dvd.getAutor());
        add(txtDirector);

        add(new JLabel("Año:"));
        txtAnio = new JTextField(20);
        if (dvd != null) txtAnio.setText(String.valueOf(dvd.getAnioPublicacion()));
        add(txtAnio);

        add(new JLabel("Duración (min):"));
        txtDuracion = new JTextField(20);
        if (dvd != null) txtDuracion.setText(String.valueOf(dvd.getDuracion()));
        add(txtDuracion);

        add(new JLabel("Género:"));
        txtGenero = new JTextField(20);
        if (dvd != null) txtGenero.setText(dvd.getGenero());
        add(txtGenero);
    }

    public boolean mostrarDialogo(Component parent) {
        int result = JOptionPane.showConfirmDialog(
                parent,
                this,
                "Formulario de DVD",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        return result == JOptionPane.OK_OPTION;
    }

    public DVD getDVD() {
        return new DVD(
                0, // ID se genera al guardar
                txtTitulo.getText(),
                txtDirector.getText(),
                Integer.parseInt(txtAnio.getText()),
                Integer.parseInt(txtDuracion.getText()),
                txtGenero.getText()
        );
    }
}