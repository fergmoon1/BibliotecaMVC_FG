package com.biblioteca.view;

import com.biblioteca.model.ElementoBiblioteca;
import com.biblioteca.model.Revista;
import com.biblioteca.model.Libro;
import com.biblioteca.model.DVD;

import javax.swing.*;
import java.awt.*;

/**
 * Diálogo para mostrar los detalles de un elemento de la biblioteca.
 */
public class DialogoDetallesElemento extends JDialog {

    /**
     * Constructor del diálogo de detalles.
     *
     * @param parent Ventana padre (MainFrame)
     * @param elemento Elemento de la biblioteca cuyos detalles se mostrarán
     */
    public DialogoDetallesElemento(JFrame parent, ElementoBiblioteca elemento) {
        super(parent, "Detalles del Elemento", true); // Modal dialog
        initComponents(elemento);
    }

    private void initComponents(ElementoBiblioteca elemento) {
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(getParent());

        // Panel para mostrar los detalles
        JPanel panelDetalles = new JPanel();
        panelDetalles.setLayout(new GridLayout(0, 2, 10, 10));
        panelDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campos comunes a todos los elementos
        panelDetalles.add(new JLabel("ID:"));
        panelDetalles.add(new JLabel(String.valueOf(elemento.getId())));

        panelDetalles.add(new JLabel("Título:"));
        panelDetalles.add(new JLabel(elemento.getTitulo()));

        panelDetalles.add(new JLabel("Autor:"));
        panelDetalles.add(new JLabel(elemento.getAutor()));

        panelDetalles.add(new JLabel("Año de Publicación:"));
        panelDetalles.add(new JLabel(String.valueOf(elemento.getAnoPublicacion())));

        // Campos específicos según el tipo de elemento
        if (elemento instanceof Revista) {
            Revista revista = (Revista) elemento;
            panelDetalles.add(new JLabel("Nombre de Edición:"));
            panelDetalles.add(new JLabel(revista.getNombreEdicion()));

            panelDetalles.add(new JLabel("Categoría:"));
            panelDetalles.add(new JLabel(revista.getCategoria()));
        } else if (elemento instanceof Libro) {
            Libro libro = (Libro) elemento;
            panelDetalles.add(new JLabel("ISBN:"));
            panelDetalles.add(new JLabel(libro.getISBN()));

            panelDetalles.add(new JLabel("Género Principal:"));
            panelDetalles.add(new JLabel(libro.getGeneroPrincipal()));

            panelDetalles.add(new JLabel("Editorial:"));
            panelDetalles.add(new JLabel(libro.getEditorial()));
        } else if (elemento instanceof DVD) {
            DVD dvd = (DVD) elemento;
            panelDetalles.add(new JLabel("Director:"));
            panelDetalles.add(new JLabel(dvd.getDirector()));

            panelDetalles.add(new JLabel("Duración (minutos):"));
            panelDetalles.add(new JLabel(String.valueOf(dvd.getDuracionMinutos())));

            panelDetalles.add(new JLabel("Formato:"));
            panelDetalles.add(new JLabel(dvd.getFormato()));
        }

        // Botón para cerrar el diálogo
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        // Añadir componentes al diálogo
        add(new JScrollPane(panelDetalles), BorderLayout.CENTER);
        add(btnCerrar, BorderLayout.SOUTH);
    }
}