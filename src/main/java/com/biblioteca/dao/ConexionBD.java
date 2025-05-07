package com.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para manejar la conexión a la base de datos.
 */
public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca"; // Ajusta la URL
    private static final String USER = "root"; // Ajusta el usuario
    private static final String PASSWORD = ""; // Ajusta la contraseña
    private Connection connection;

    /**
     * Constructor que establece la conexión a la base de datos.
     */
    public ConexionBD() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la conexión a la base de datos.
     *
     * @return Connection objeto de conexión
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Cierra la conexión a la base de datos.
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
