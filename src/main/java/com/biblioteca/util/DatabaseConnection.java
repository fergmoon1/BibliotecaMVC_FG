package com.biblioteca.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection = null;
    private static Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("No se pudo encontrar el archivo config.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            Logger.logError("Error al cargar el archivo de configuración", e);
            throw new RuntimeException("Error al cargar el archivo de configuración: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    return connection;
                }
            } catch (SQLException e) {
                Logger.logError("Error al verificar el estado de la conexión", e);
            }
        }

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        try {
            connection = DriverManager.getConnection(url, user, password);
            Logger.logInfo("Conexión a la base de datos establecida con éxito");
        } catch (SQLException e) {
            Logger.logError("Error al conectar a la base de datos", e);
            throw new RuntimeException("Error al conectar a la base de datos: " + e.getMessage(), e);
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                Logger.logInfo("Conexión a la base de datos cerrada");
            } catch (SQLException e) {
                Logger.logError("Error al cerrar la conexión a la base de datos", e);
            }
        }
    }
}