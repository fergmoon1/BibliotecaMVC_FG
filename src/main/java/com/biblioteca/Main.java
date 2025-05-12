package com.biblioteca;

import com.biblioteca.view.LibroPanel; // Importa el panel principal que sí existe
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Configuración básica de Swing
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sistema de Biblioteca");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Usa LibroPanel como contenido principal (o el panel que prefieras)
            frame.add(new LibroPanel());

            frame.setLocationRelativeTo(null); // Centra la ventana
            frame.setVisible(true);
        });
    }
}