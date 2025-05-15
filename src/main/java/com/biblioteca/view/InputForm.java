package com.biblioteca.view;

import com.biblioteca.model.ElementoBiblioteca;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Revista;
import com.biblioteca.model.DVD;
import com.biblioteca.util.UIConfig;

import javax.swing.*;
import java.awt.*;

public class InputForm extends JDialog {
    private JTextField txtId, txtTitulo, txtAutor, txtAno;
    private JTextField txtIsbn, txtPaginas, txtGeneroLibro, txtEditorial;
    private JTextField txtEdicion, txtCategoria;
    private JTextField txtDuracion, txtGeneroDVD;
    private JComboBox<String> comboTipo;
    private JButton btnAceptar, btnCancelar;
    private ElementoBiblioteca elemento;
    private boolean isConfirmed;

    public InputForm(JFrame parent, String title, ElementoBiblioteca elemento) {
        super(parent, title, true);
        this.elemento = elemento;
        this.isConfirmed = false;
        initializeUI();
        if (elemento != null) {
            cargarDatos();
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        UIConfig.configureDialog(this); // Cambiado de configureFrame a configureDialog

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        UIConfig.configurePanel(formPanel);

        // Campos comunes
        JLabel lblId = new JLabel("ID:");
        UIConfig.configureLabel(lblId);
        txtId = new JTextField();
        UIConfig.configureTextField(txtId);
        formPanel.add(lblId);
        formPanel.add(txtId);

        JLabel lblTipo = new JLabel("Tipo:");
        UIConfig.configureLabel(lblTipo);
        comboTipo = new JComboBox<>(new String[]{"Libro", "Revista", "DVD"});
        UIConfig.configureComboBox(comboTipo);
        formPanel.add(lblTipo);
        formPanel.add(comboTipo);

        JLabel lblTitulo = new JLabel("Título:");
        UIConfig.configureLabel(lblTitulo);
        txtTitulo = new JTextField();
        UIConfig.configureTextField(txtTitulo);
        formPanel.add(lblTitulo);
        formPanel.add(txtTitulo);

        JLabel lblAutor = new JLabel("Autor:");
        UIConfig.configureLabel(lblAutor);
        txtAutor = new JTextField();
        UIConfig.configureTextField(txtAutor);
        formPanel.add(lblAutor);
        formPanel.add(txtAutor);

        JLabel lblAno = new JLabel("Año de Publicación:");
        UIConfig.configureLabel(lblAno);
        txtAno = new JTextField();
        UIConfig.configureTextField(txtAno);
        formPanel.add(lblAno);
        formPanel.add(txtAno);

        // Campos para Libro
        JLabel lblIsbn = new JLabel("ISBN (Libro):");
        UIConfig.configureLabel(lblIsbn);
        txtIsbn = new JTextField();
        UIConfig.configureTextField(txtIsbn);
        formPanel.add(lblIsbn);
        formPanel.add(txtIsbn);

        JLabel lblPaginas = new JLabel("Número de Páginas (Libro):");
        UIConfig.configureLabel(lblPaginas);
        txtPaginas = new JTextField();
        UIConfig.configureTextField(txtPaginas);
        formPanel.add(lblPaginas);
        formPanel.add(txtPaginas);

        JLabel lblGeneroLibro = new JLabel("Género (Libro):");
        UIConfig.configureLabel(lblGeneroLibro);
        txtGeneroLibro = new JTextField();
        UIConfig.configureTextField(txtGeneroLibro);
        formPanel.add(lblGeneroLibro);
        formPanel.add(txtGeneroLibro);

        JLabel lblEditorial = new JLabel("Editorial (Libro):");
        UIConfig.configureLabel(lblEditorial);
        txtEditorial = new JTextField();
        UIConfig.configureTextField(txtEditorial);
        formPanel.add(lblEditorial);
        formPanel.add(txtEditorial);

        // Campos para Revista
        JLabel lblEdicion = new JLabel("Número de Edición (Revista):");
        UIConfig.configureLabel(lblEdicion);
        txtEdicion = new JTextField();
        UIConfig.configureTextField(txtEdicion);
        formPanel.add(lblEdicion);
        formPanel.add(txtEdicion);

        JLabel lblCategoria = new JLabel("Categoría (Revista):");
        UIConfig.configureLabel(lblCategoria);
        txtCategoria = new JTextField();
        UIConfig.configureTextField(txtCategoria);
        formPanel.add(lblCategoria);
        formPanel.add(txtCategoria);

        // Campos para DVD
        JLabel lblDuracion = new JLabel("Duración (DVD, min):");
        UIConfig.configureLabel(lblDuracion);
        txtDuracion = new JTextField();
        UIConfig.configureTextField(txtDuracion);
        formPanel.add(lblDuracion);
        formPanel.add(txtDuracion);

        JLabel lblGeneroDVD = new JLabel("Género (DVD):");
        UIConfig.configureLabel(lblGeneroDVD);
        txtGeneroDVD = new JTextField();
        UIConfig.configureTextField(txtGeneroDVD);
        formPanel.add(lblGeneroDVD);
        formPanel.add(txtGeneroDVD);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        UIConfig.configurePanel(buttonPanel);
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");
        UIConfig.configureButton(btnAceptar);
        UIConfig.configureButton(btnCancelar);
        buttonPanel.add(btnAceptar);
        buttonPanel.add(btnCancelar);

        // Acciones de los botones
        btnAceptar.addActionListener(e -> {
            isConfirmed = true;
            setVisible(false);
        });
        btnCancelar.addActionListener(e -> {
            isConfirmed = false;
            setVisible(false);
        });

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(400, 500);
        setLocationRelativeTo(getParent());
    }

    private void cargarDatos() {
        if (elemento != null) {
            txtId.setText(String.valueOf(elemento.getId()));
            txtId.setEditable(false); // No permitir editar el ID al editar
            comboTipo.setSelectedItem(elemento.getTipo());
            comboTipo.setEnabled(false); // No permitir cambiar el tipo al editar
            txtTitulo.setText(elemento.getTitulo());
            txtAutor.setText(elemento.getAutor());
            txtAno.setText(String.valueOf(elemento.getAnoPublicacion()));

            if (elemento instanceof Libro) {
                Libro libro = (Libro) elemento;
                txtIsbn.setText(libro.getIsbn());
                txtPaginas.setText(String.valueOf(libro.getNumeroPaginas()));
                txtGeneroLibro.setText(libro.getGenero());
                txtEditorial.setText(libro.getEditorial());
            } else if (elemento instanceof Revista) {
                Revista revista = (Revista) elemento;
                txtEdicion.setText(String.valueOf(revista.getNumeroEdicion()));
                txtCategoria.setText(revista.getCategoria());
            } else if (elemento instanceof DVD) {
                DVD dvd = (DVD) elemento;
                txtDuracion.setText(String.valueOf(dvd.getDuracion()));
                txtGeneroDVD.setText(dvd.getGenero());
            }
        }
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public int getId() {
        return Integer.parseInt(txtId.getText());
    }

    public String getTipo() {
        return (String) comboTipo.getSelectedItem();
    }

    public String getTitulo() {
        return txtTitulo.getText();
    }

    public String getAutor() {
        return txtAutor.getText();
    }

    public int getAnoPublicacion() {
        return Integer.parseInt(txtAno.getText());
    }

    public String getIsbn() {
        return txtIsbn.getText();
    }

    public int getNumeroPaginas() {
        return Integer.parseInt(txtPaginas.getText());
    }

    public String getGeneroLibro() {
        return txtGeneroLibro.getText();
    }

    public String getEditorial() {
        return txtEditorial.getText();
    }

    public int getNumeroEdicion() {
        return Integer.parseInt(txtEdicion.getText());
    }

    public String getCategoria() {
        return txtCategoria.getText();
    }

    public int getDuracion() {
        return Integer.parseInt(txtDuracion.getText());
    }

    public String getGeneroDVD() {
        return txtGeneroDVD.getText();
    }
}