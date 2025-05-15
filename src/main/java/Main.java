package com.biblioteca;

import com.biblioteca.util.DatabaseConnection;
import com.biblioteca.view.BibliotecaView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                BibliotecaView view = new BibliotecaView();
                view.setVisible(true);
            } catch (Exception e) {
                System.err.println("Error al iniciar la aplicación: " + e.getMessage());
                e.printStackTrace();
            }
        });

        // Registrar un hook para cerrar la conexión a la base de datos al cerrar la aplicación
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DatabaseConnection.closeConnection();
        }));
    }
}