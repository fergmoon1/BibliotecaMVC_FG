package com.biblioteca.util;

import javax.swing.*;
import java.awt.*;

public class UIConfig {
    // Colores
    public static final Color PRIMARY_COLOR = new Color(70, 130, 180); // Azul acero
    public static final Color SECONDARY_COLOR = new Color(220, 220, 220); // Gris claro
    public static final Color TEXT_COLOR = Color.BLACK;
    public static final Color ERROR_COLOR = new Color(200, 50, 50); // Rojo suave

    // Fuentes
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 16);
    public static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 14);
    public static final Font TEXT_FONT = new Font("Arial", Font.PLAIN, 12);
    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 12);

    // Configuración de componentes
    public static void configureFrame(JFrame frame) {
        frame.getContentPane().setBackground(SECONDARY_COLOR);
    }

    public static void configureDialog(JDialog dialog) {
        dialog.getContentPane().setBackground(SECONDARY_COLOR);
    }

    public static void configurePanel(JPanel panel) {
        panel.setBackground(SECONDARY_COLOR);
    }

    public static void configureLabel(JLabel label) {
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_COLOR);
    }

    public static void configureTextField(JTextField textField) {
        textField.setFont(TEXT_FONT);
        textField.setForeground(TEXT_COLOR);
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR));
    }

    public static void configureButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setFocusPainted(false);
    }

    public static void configureTable(JTable table) {
        table.setFont(TEXT_FONT);
        table.setRowHeight(25);
        table.setGridColor(PRIMARY_COLOR);
        table.setSelectionBackground(PRIMARY_COLOR);
        table.setSelectionForeground(Color.WHITE);
    }

    public static void configureComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(TEXT_FONT);
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(TEXT_COLOR);
        comboBox.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR));
    }
}