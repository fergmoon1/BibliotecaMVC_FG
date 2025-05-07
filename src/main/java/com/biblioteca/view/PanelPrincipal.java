package com.biblioteca.view;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que representa el panel principal de la interfaz de la com.biblioteca.
 * Sirve como pantalla de bienvenida con opciones para navegar a otras secciones.
 */
public class PanelPrincipal extends JPanel {

    /**
     * Constructor del panel principal.
     * Inicializa los componentes gráficos y configura el layout.
     */
    public PanelPrincipal() {
        // Configurar el layout
        setLayout(new BorderLayout());

        // Panel superior con título
        JPanel panelTitulo = new JPanel(new FlowLayout());
        JLabel lblTitulo = new JLabel("Bienvenido al Sistema de Gestión de Biblioteca");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel central con mensaje e imagen (opcional)
        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblMensaje = new JLabel("<html><center>Gestione libros, revistas y DVDs.<br>Seleccione una opción para comenzar.</center></html>");
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentral.add(lblMensaje, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnLibros = new JButton("Gestionar Libros");
        JButton btnRevistas = new JButton("Gestionar Revistas");
        JButton btnDVDs = new JButton("Gestionar DVDs");

        panelBotones.add(btnLibros);
        panelBotones.add(btnRevistas);
        panelBotones.add(btnDVDs);

        gbc.gridy = 1;
        panelCentral.add(panelBotones, gbc);

        // Agregar panel central al contenedor principal
        add(panelCentral, BorderLayout.CENTER);
    }
}
