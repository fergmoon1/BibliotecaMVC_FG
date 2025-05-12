package com.biblioteca.view;

import com.biblioteca.view.*;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    public MainFrame() {
        setTitle("Sistema de Biblioteca");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. Panel de pestañas principal
        tabbedPane = new JTabbedPane();

        // 2. Agregar pestañas
        tabbedPane.addTab("Libros", new LibroPanel());
        tabbedPane.addTab("Revistas", new RevistaPanel());
        tabbedPane.addTab("DVDs", new DVDPanel());
        tabbedPane.addTab("Catálogo", new CatalogoPanel()); // Nueva pestaña

        // 3. Configurar íconos
        tabbedPane.setIconAt(0, new ImageIcon("src/main/resources/book.png"));
        tabbedPane.setIconAt(1, new ImageIcon("src/main/resources/magazine.png"));
        tabbedPane.setIconAt(2, new ImageIcon("src/main/resources/dvd.png"));
        tabbedPane.setIconAt(3, new ImageIcon("src/main/resources/catalog.png"));

        add(tabbedPane, BorderLayout.CENTER);

        // 4. Menú superior con atajos
        JMenuBar menuBar = new JMenuBar();

        // Menú de navegación
        JMenu navegacionMenu = new JMenu("Navegación");
        navegacionMenu.add(crearMenuItem("Libros", 0));
        navegacionMenu.add(crearMenuItem("Revistas", 1));
        navegacionMenu.add(crearMenuItem("DVDs", 2));
        navegacionMenu.add(crearMenuItem("Catálogo", 3));

        // Menú Archivo
        JMenu archivoMenu = new JMenu("Archivo");
        archivoMenu.add(new JMenuItem("Salir")).addActionListener(e -> System.exit(0));

        menuBar.add(navegacionMenu);
        menuBar.add(archivoMenu);
        setJMenuBar(menuBar);
    }

    private JMenuItem crearMenuItem(String texto, int tabIndex) {
        JMenuItem item = new JMenuItem(texto);
        item.addActionListener(e -> tabbedPane.setSelectedIndex(tabIndex));
        return item;
    }
}