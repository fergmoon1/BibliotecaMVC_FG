package com.biblioteca.view;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Sistema de Biblioteca - MVC");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("📚 Libros", new LibroPanel());
        tabbedPane.addTab("📰 Revistas", new RevistaPanel());
        tabbedPane.addTab("🎬 DVDs", new DVDPanel());

        add(tabbedPane);

        // Menú superior
        JMenuBar menuBar = new JMenuBar();
        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem salirItem = new JMenuItem("Salir");
        salirItem.addActionListener(e -> System.exit(0));
        archivoMenu.add(salirItem);
        menuBar.add(archivoMenu);
        setJMenuBar(menuBar);
    }
}