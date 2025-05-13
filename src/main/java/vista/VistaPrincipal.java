package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VistaPrincipal extends JFrame {
    private JMenuItem itemCatalogo, itemAyuda;

    public VistaPrincipal() {
        configurarVentana();
        initMenu();
    }

    private void configurarVentana() {
        setTitle("Sistema de Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initMenu() {
        // Barra de menú
        JMenuBar menuBar = new JMenuBar();

        // Menú "Archivo"
        JMenu menuArchivo = new JMenu("Archivo");
        itemCatalogo = new JMenuItem("Catálogo");
        itemAyuda = new JMenuItem("Ayuda");

        // Añadir items al menú
        menuArchivo.add(itemCatalogo);
        menuArchivo.addSeparator();
        menuArchivo.add(itemAyuda);

        // Añadir menú a la barra
        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);
    }

    // Getters para los items del menú (usados por el controlador)
    public JMenuItem getItemCatalogo() {
        return itemCatalogo;
    }

    public JMenuItem getItemAyuda() {
        return itemAyuda;
    }
}