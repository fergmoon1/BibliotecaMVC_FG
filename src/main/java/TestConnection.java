package com.biblioteca.util;

import com.biblioteca.dao.DatabaseConnection;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

public class TestConnection {  // Nombre de clase actualizado para coincidir con el archivo

    @Test
    public void testConnectionPool() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            assertTrue(conn.isValid(2), "La conexión debería ser válida");
            System.out.println("✅ Conexión exitosa. Tiempo: " + conn.getNetworkTimeout() + "ms");
            DatabaseConnection.closeConnection(conn);
        } catch (Exception e) {
            fail("❌ Error en la conexión: " + e.getMessage());
        }
    }

    // Método main para pruebas rápidas (sin JUnit)
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("✨ Conexión exitosa a la base de datos");
            conn.close();
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }
}