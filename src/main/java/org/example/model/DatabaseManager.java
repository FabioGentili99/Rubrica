package org.example.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private static final Properties properties = new Properties();

    static {
        String resourceName = "credenziali_database.properties";

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            if (resourceStream == null) {
                System.err.println("Resource not found: " + resourceName);
                throw new RuntimeException("Database properties file not found: " + resourceName);
            }
            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database properties", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        // Retrieve the connection details from the properties object
        return DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password")
        );
    }
}