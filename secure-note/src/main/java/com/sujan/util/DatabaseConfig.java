package com.sujan.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {

    // Single instance shared across entire app
    private static HikariDataSource dataSource;

    // Private constructor — nobody can do "new DatabaseConfig()"
    private DatabaseConfig() {}

    // Called ONCE when app starts
    public static void initialize() {
        try {
            // Load db.properties from resources folder
            Properties props = loadProperties();

            HikariConfig config = new HikariConfig();

            // Basic connection settings
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));

            // Pool settings
            config.setMaximumPoolSize(
                    Integer.parseInt(props.getProperty("db.pool.size", "10"))
            );
            config.setMinimumIdle(2);          // Keep min 2 connections ready
            config.setIdleTimeout(30000);       // Remove idle connection after 30s
            config.setConnectionTimeout(20000); // Wait max 20s for a connection
            config.setMaxLifetime(1800000);     // Recycle connections every 30min

            // Pool name — helpful for debugging logs
            config.setPoolName("SecureNotePool");

            // Test query — verifies connection is alive before using it
            config.setConnectionTestQuery("SELECT 1");

            dataSource = new HikariDataSource(config);
            System.out.println("✅ Database connection pool initialized successfully!");

        } catch (Exception e) {
            System.err.println("❌ Failed to initialize database pool: " + e.getMessage());
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    // Called by every repository to get a connection
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("Database not initialized! Call DatabaseConfig.initialize() first.");
        }
        return dataSource.getConnection();
    }

    // Called when app shuts down
    public static void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("✅ Database connection pool shut down.");
        }
    }

    // Reads db.properties file from resources folder
    private static Properties loadProperties() throws IOException {
        Properties props = new Properties();
        try (InputStream input = DatabaseConfig.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IOException("db.properties file not found in resources!");
            }
            props.load(input);
        }
        return props;
    }
}