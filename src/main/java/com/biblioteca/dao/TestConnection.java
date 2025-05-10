package com.biblioteca.util;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("¡Conexión exitosa a la base de datos!");
            DatabaseConnection.closeConnection();
            System.out.println("Conexión cerrada.");
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
