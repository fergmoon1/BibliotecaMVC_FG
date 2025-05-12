package com.biblioteca.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://localhost:3306/biblioteca");
            config.setUsername("root"); // Cambia por tu usuario
            config.setPassword("");     // Cambia por tu contraseña
            config.setMaximumPoolSize(10);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");

            dataSource = new HikariDataSource(config);
            System.out.println("✅ Pool de conexiones inicializado");

            // Test de conexión
            try (Connection conn = dataSource.getConnection()) {
                System.out.println("Conexión exitosa a: " + conn.getMetaData().getDatabaseProductName());
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError("❌ Error al configurar pool: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}