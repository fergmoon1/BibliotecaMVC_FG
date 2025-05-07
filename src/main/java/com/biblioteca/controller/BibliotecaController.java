package com.biblioteca.controller;

import com.biblioteca.view.MainFrame;
import com.biblioteca.view.PanelPrincipal;
import com.biblioteca.view.PanelLibros;
import com.biblioteca.view.PanelRevistas;
import com.biblioteca.view.PanelDVDs;
import com.biblioteca.dao.ElementoBibliotecaDAO;
import com.biblioteca.dao.DVDDAO;
import com.biblioteca.dao.LibroDAO;
import com.biblioteca.dao.RevistaDAO;

import javax.swing.JPanel;
import javax.swing.JButton;

/**
 * Controlador principal de la aplicación de la biblioteca.
 * Coordina las interacciones entre las vistas y los DAOs, e inicializa los controladores específicos.
 */
public class BibliotecaController {

    private MainFrame mainFrame;
    private PanelPrincipal panelPrincipal;
    private PanelLibros panelLibros;
    private PanelRevistas panelRevistas;
    private PanelDVDs panelDVDs;
    private ElementoBibliotecaDAO elementoDAO;
    private LibroDAO libroDAO;
    private RevistaDAO revistaDAO;
    private DVDDAO dvdDAO;
    private LibroController libroController;
    private RevistaController revistaController;
    private DVDController dvdController;
    private JPanel contentPanel; // Campo de instancia

    /**
     * Constructor del controlador principal.
     *
     * @param mainFrame Ventana principal de la aplicación
     * @param elementoDAO DAO para elementos genéricos
     * @param libroDAO DAO para libros
     * @param revistaDAO DAO para revistas
     * @param dvdDAO DAO para DVDs
     */
    public BibliotecaController(MainFrame mainFrame, ElementoBibliotecaDAO elementoDAO,
                                LibroDAO libroDAO, RevistaDAO revistaDAO, DVDDAO dvdDAO) {
        this.mainFrame = mainFrame;
        this.elementoDAO = elementoDAO;
        this.libroDAO = libroDAO;
        this.revistaDAO = revistaDAO;
        this.dvdDAO = dvdDAO;

        // Inicializar los paneles
        panelPrincipal = new PanelPrincipal();
        panelLibros = new PanelLibros();
        panelRevistas = new PanelRevistas();
        panelDVDs = new PanelDVDs();

        // Configurar el CardLayout en MainFrame
        mainFrame.getContentPane().removeAll();
        contentPanel = new JPanel(new java.awt.CardLayout());
        contentPanel.add(panelPrincipal, "Principal");
        contentPanel.add(panelLibros, "Libros");
        contentPanel.add(panelRevistas, "Revistas");
        contentPanel.add(panelDVDs, "DVDs");
        mainFrame.add(contentPanel, java.awt.BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();

        // Inicializar los controladores específicos
        libroController = new LibroController(panelLibros, libroDAO);
        revistaController = new RevistaController(panelRevistas, revistaDAO);
        dvdController = new DVDController(panelDVDs, dvdDAO);

        // Agregar acciones a los botones del panel principal
        agregarAccionesPanelPrincipal();
    }

    /**
     * Agrega acciones a los botones del panel principal para navegar entre secciones.
     */
    private void agregarAccionesPanelPrincipal() {
        for (java.awt.Component comp : panelPrincipal.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panelCentral = (JPanel) comp;
                for (java.awt.Component innerComp : panelCentral.getComponents()) {
                    if (innerComp instanceof JPanel) {
                        JPanel panelBotones = (JPanel) innerComp;
                        for (java.awt.Component button : panelBotones.getComponents()) {
                            if (button instanceof JButton) {
                                JButton btn = (JButton) button;
                                if (btn.getText().equals("Gestionar Libros")) {
                                    btn.addActionListener(e -> {
                                        java.awt.CardLayout cl = (java.awt.CardLayout) contentPanel.getLayout();
                                        cl.show(contentPanel, "Libros");
                                    });
                                } else if (btn.getText().equals("Gestionar Revistas")) {
                                    btn.addActionListener(e -> {
                                        java.awt.CardLayout cl = (java.awt.CardLayout) contentPanel.getLayout();
                                        cl.show(contentPanel, "Revistas");
                                    });
                                } else if (btn.getText().equals("Gestionar DVDs")) {
                                    btn.addActionListener(e -> {
                                        java.awt.CardLayout cl = (java.awt.CardLayout) contentPanel.getLayout();
                                        cl.show(contentPanel, "DVDs");
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}