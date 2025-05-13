package com.biblioteca.view;

import com.biblioteca.model.DVD;
import javax.swing.*;
import java.awt.*;

public class DVDForm {
    private JDialog dialog;
    private JTextField txtTitulo, txtDirector, txtAnio, txtDuracion, txtGenero;
    private DVD dvd; // Puede ser null para agregar, o un DVD existente para editar

    public DVDForm(Frame parent, String title, DVD dvd) {
        this.dvd = dvd;
        initComponents(parent, title);
    }

    private void initComponents(Frame parent, String title) {
        dialog = new JDialog(parent, title, true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parent);

        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtTitulo = new JTextField(dvd != null ? dvd.getTitulo() : "");
        txtDirector = new JTextField(dvd != null ? dvd.getDirector() : "");
        txtAnio = new JTextField(dvd != null ? String.valueOf(dvd.getAnio()) : "");
        txtDuracion = new JTextField(dvd != null ? String.valueOf(dvd.getDuracion()) : "");
        txtGenero = new JTextField(dvd != null ? dvd.getGenero() : "");

        panelForm.add(new JLabel("Título:"));
        panelForm.add(txtTitulo);
        panelForm.add(new JLabel("Director:"));
        panelForm.add(txtDirector);
        panelForm.add(new JLabel("Año:"));
        panelForm.add(txtAnio);
        panelForm.add(new JLabel("Duración (min):"));
        panelForm.add(txtDuracion);
        panelForm.add(new JLabel("Género:"));
        panelForm.add(txtGenero);

        dialog.add(panelForm, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                // Validar campos obligatorios
                if (txtTitulo.getText().trim().isEmpty() ||
                        txtDirector.getText().trim().isEmpty() ||
                        txtAnio.getText().trim().isEmpty() ||
                        txtDuracion.getText().trim().isEmpty() ||
                        txtGenero.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Todos los campos son obligatorios",
                            "Error de validación",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar que año y duración sean números
                int anio = Integer.parseInt(txtAnio.getText().trim());
                int duracion = Integer.parseInt(txtDuracion.getText().trim());

                // Crear o actualizar el DVD
                if (dvd == null) {
                    dvd = new DVD(
                            txtTitulo.getText().trim(),
                            txtDirector.getText().trim(),
                            anio,
                            duracion,
                            txtGenero.getText().trim()
                    );
                } else {
                    dvd.setTitulo(txtTitulo.getText().trim());
                    dvd.setDirector(txtDirector.getText().trim());
                    dvd.setAnio(anio);
                    dvd.setDuracion(duracion);
                    dvd.setGenero(txtGenero.getText().trim());
                }

                if (dvd.guardar()) {
                    dialog.dispose();
                    JOptionPane.showMessageDialog(dialog,
                            "DVD " + (dvd.getId() == 0 ? "agregado" : "actualizado") + " correctamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog,
                            "Error al guardar el DVD",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "El año y la duración deben ser números",
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

    public DVD getDVD() {
        return dvd;
    }
}