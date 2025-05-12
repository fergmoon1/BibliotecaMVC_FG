package com.biblioteca.view;

import com.biblioteca.view.LibroPanel;
import com.biblioteca.view.RevistaPanel;
import com.biblioteca.view.DVDPanel;
import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Sistema de Biblioteca");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. Crear el panel de pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // 2. Agregar todas las pestañas
        tabbedPane.addTab("Libros", new LibroPanel());
        tabbedPane.addTab("Revistas", new RevistaPanel());
        tabbedPane.addTab("DVDs", new DVDPanel());

        // 3. Configurar íconos (opcional)
        tabbedPane.setIconAt(0, new ImageIcon("src/main/resources/book.png"));
        tabbedPane.setIconAt(1, new ImageIcon("src/main/resources/magazine.png"));
        tabbedPane.setIconAt(2, new ImageIcon("src/main/resources/dvd.png"));

        add(tabbedPane);

        // Menú superior (opcional)
        JMenuBar menuBar = new JMenuBar();
        JMenu archivoMenu = new JMenu("Archivo");
        archivoMenu.add(new JMenuItem("Salir")).addActionListener(e -> System.exit(0));
        menuBar.add(archivoMenu);
        setJMenuBar(menuBar);
    }
}