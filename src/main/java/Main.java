package com.biblioteca;

import com.biblioteca.util.DatabaseConnection;
import com.biblioteca.view.BibliotecaView;
import com.biblioteca.util.Logger;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Configurar el manejo de excepciones no capturadas
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            Logger.logError("Excepción no capturada en hilo " + thread.getName(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error crítico: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        });

        SwingUtilities.invokeLater(() -> {
            try {
                // Inicializar la vista principal
                BibliotecaView view = new BibliotecaView();
                view.mostrar(); // Usamos el método mostrar() de la nueva vista

                Logger.logInfo("Aplicación iniciada correctamente");
            } catch (Exception e) {
                Logger.logError("Error al iniciar la aplicación", e);
                JOptionPane.showMessageDialog(null,
                        "Error al iniciar la aplicación: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });

        // Registrar hook para cerrar recursos al salir
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                DatabaseConnection.closeConnection();
                Logger.logInfo("Conexión a BD cerrada correctamente");
            } catch (Exception e) {
                Logger.logError("Error al cerrar la conexión a BD", e);
            }
        }));
    }
}