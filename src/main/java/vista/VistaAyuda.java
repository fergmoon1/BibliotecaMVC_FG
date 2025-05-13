package vista;

import javax.swing.*;

public class VistaAyuda extends JDialog {
    public VistaAyuda() {
        setTitle("Ayuda - Sistema de Biblioteca");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JTextArea textoAyuda = new JTextArea(
                "SISTEMA DE BIBLIOTECA v1.0\n\n" +
                        "Funciones:\n" +
                        "1. Catálogo: Agregar/editar/eliminar libros, revistas o DVDs.\n" +
                        "2. Búsqueda: Filtrar por género o título.\n\n" +
                        "Desarrollado por [Tu Nombre]"
        );
        textoAyuda.setEditable(false);
        add(new JScrollPane(textoAyuda));
    }
}