package com.biblioteca.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        // Configuración básica de la ventana
        setTitle("Sistema de Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();

        // Menú "Archivo"
        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem salirItem = new JMenuItem("Salir");
        salirItem.addActionListener(e -> System.exit(0));
        archivoMenu.add(salirItem);
        menuBar.add(archivoMenu);

        // Menú "Ayuda"
        JMenu ayudaMenu = new JMenu("Ayuda");
        JMenuItem acercaDeItem = new JMenuItem("Acerca de...");
        acercaDeItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Sistema de Biblioteca\nVersión 1.0\nDesarrollado por xAI",
                    "Acerca de",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        ayudaMenu.add(acercaDeItem);
        menuBar.add(ayudaMenu);

        // Establecer la barra de menú
        setJMenuBar(menuBar);

        // Crear el panel con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Agregar los paneles de Libros, Revistas y DVDs
        tabbedPane.addTab("Libros", new LibroPanel());
        tabbedPane.addTab("Revistas", new RevistaPanel());
        tabbedPane.addTab("DVDs", new DVDPanel());

        // Agregar el panel de pestañas a la ventana
        add(tabbedPane, BorderLayout.CENTER);

        // Hacer visible la ventana
        setVisible(true);
    }

    public static void main(String[] args) {
        // Ejecutar la aplicación en el hilo de despacho de eventos
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}