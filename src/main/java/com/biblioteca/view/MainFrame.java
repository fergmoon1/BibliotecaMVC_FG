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

// Menú de navegación como tabs (no visible en la captura pero necesario)
        JPanel tabPanel = new JPanel();
        tabPanel.add(new JButton("Libros"));
        tabPanel.add(new JButton("Revistas"));
        tabPanel.add(new JButton("DVDs"));
        add(tabPanel, BorderLayout.NORTH);

        // Menú Archivo
        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem catalogoItem = new JMenuItem("Catálogo");
        JMenuItem ayudaItem = new JMenuItem("Ayuda");

        archivoMenu.add(catalogoItem);
        archivoMenu.add(ayudaItem);
        menuBar.add(archivoMenu);

        // Eventos para los ítems del menú
        catalogoItem.addActionListener(e -> tabbedPane.setSelectedIndex(3));
        ayudaItem.addActionListener(e -> mostrarAyuda());
    }

    private JMenuItem crearMenuItem(String texto, int tabIndex) {
        JMenuItem item = new JMenuItem(texto);
        item.addActionListener(e -> tabbedPane.setSelectedIndex(tabIndex));
        return item;
    }

    private void mostrarAyuda() {
        JOptionPane.showMessageDialog(this,
                "<html><h2>Sistema de Biblioteca</h2>"
                        + "<p>Versión 1.0<br>© 2025</p>",
                "Ayuda",
                JOptionPane.INFORMATION_MESSAGE);
    }
}