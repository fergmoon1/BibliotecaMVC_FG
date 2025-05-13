package com.biblioteca;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Configuración básica de Swing
        try {
            // Establecer el look and feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}