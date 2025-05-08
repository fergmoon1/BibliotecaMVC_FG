package com.biblioteca.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa un cuadro de diálogo para agregar un nuevo elemento a la com.biblioteca.
 * Permite ingresar datos genéricos de un elemento (Libro, Revista o DVD).
 */
public class DialogoAgregarElemento extends JDialog {

    private JTextField txtId;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtAnoPublicacion;
    private JTextField txtDatoEspecifico1; // Campo para datos específicos (ISBN, Nombre Edición, Director)
    private JTextField txtDatoEspecifico2; // Campo para datos adicionales (Género, Categoría, Duración, Formato)
    private String tipoElemento; // Tipo del elemento (Libro, Revista, DVD)
    private JButton btnAceptar;
    private JButton btnCancelar;

    /**
     * Constructor del diálogo para agregar un elemento.
     *
     * @param parent Ventana padre (MainFrame u otro contenedor)
     * @param tipoElemento Tipo de elemento a agregar (Libro, Revista, DVD)
     */
    public DialogoAgregarElemento(JFrame parent, String tipoElemento) {
        super(parent, "Agregar " + tipoElemento, true);
        this.tipoElemento = tipoElemento;
        inicializarComponentes();
        configurarLayout();
        agregarAcciones();
        setSize(400, 300);
        setLocationRelativeTo(parent); // Centra el diálogo
    }

    /**
     * Inicializa los componentes del diálogo.
     */
    private void inicializarComponentes() {
        txtId = new JTextField(10);
        txtTitulo = new JTextField(20);
        txtAutor = new JTextField(20);
        txtAnoPublicacion = new JTextField(10);
        txtDatoEspecifico1 = new JTextField(20);
        txtDatoEspecifico2 = new JTextField(20);
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");
    }

    /**
     * Configura el layout del diálogo.
     */
    private void configurarLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiquetas y campos
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        add(txtId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        add(txtTitulo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1;
        add(txtAutor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Año de Publicación:"), gbc);
        gbc.gridx = 1;
        add(txtAnoPublicacion, gbc);

        // Campos específicos según el tipo de elemento
        if ("Libro".equals(tipoElemento)) {
            gbc.gridx = 0;
            gbc.gridy = 4;
            add(new JLabel("ISBN:"), gbc);
            gbc.gridx = 1;
            add(txtDatoEspecifico1, gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            add(new JLabel("Género:"), gbc);
            gbc.gridx = 1;
            add(txtDatoEspecifico2, gbc);
        } else if ("Revista".equals(tipoElemento)) {
            gbc.gridx = 0;
            gbc.gridy = 4;
            add(new JLabel("Nombre Edición:"), gbc);
            gbc.gridx = 1;
            add(txtDatoEspecifico1, gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            add(new JLabel("Categoría:"), gbc);
            gbc.gridx = 1;
            add(txtDatoEspecifico2, gbc);
        } else if ("DVD".equals(tipoElemento)) {
            gbc.gridx = 0;
            gbc.gridy = 4;
            add(new JLabel("Director:"), gbc);
            gbc.gridx = 1;
            add(txtDatoEspecifico1, gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            add(new JLabel("Duración (min):"), gbc);
            gbc.gridx = 1;
            add(txtDatoEspecifico2, gbc);
        }

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(panelBotones, gbc);
    }

    /**
     * Agrega las acciones a los botones.
     */
    private void agregarAcciones() {
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para aceptar (se implementará en el controlador)
                dispose(); // Cierra el diálogo
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra el diálogo sin guardar
            }
        });
    }

    // Getters para los componentes (para uso del controlador)

    /**
     * Obtiene el campo de texto del ID.
     *
     * @return JTextField con el ID
     */
    public JTextField getTxtId() {
        return txtId;
    }

    /**
     * Obtiene el campo de texto del título.
     *
     * @return JTextField con el título
     */
    public JTextField getTxtTitulo() {
        return txtTitulo;
    }

    /**
     * Obtiene el campo de texto del autor.
     *
     * @return JTextField con el autor
     */
    public JTextField getTxtAutor() {
        return txtAutor;
    }

    /**
     * Obtiene el campo de texto del año de publicación.
     *
     * @return JTextField con el año de publicación
     */
    public JTextField getTxtAnoPublicacion() {
        return txtAnoPublicacion;
    }

    /**
     * Obtiene el primer campo de texto específico.
     *
     * @return JTextField con el dato específico 1 (ISBN, Nombre Edición, Director)
     */
    public JTextField getTxtDatoEspecifico1() {
        return txtDatoEspecifico1;
    }

    /**
     * Obtiene el segundo campo de texto específico.
     *
     * @return JTextField con el dato específico 2 (Género, Categoría, Duración)
     */
    public JTextField getTxtDatoEspecifico2() {
        return txtDatoEspecifico2;
    }

    /**
     * Obtiene el botón de aceptar.
     *
     * @return JButton para aceptar la acción
     */
    public JButton getBtnAceptar() {
        return btnAceptar;
    }
}