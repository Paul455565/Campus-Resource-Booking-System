package com.campus.resourcebooking.creational.singleton;

/**
 * Singleton Pattern Implementation
 * Ensures only one database connection instance globally
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

public class DatabaseConnection {
    private static volatile DatabaseConnection instance;
    private static final ReentrantLock lock = new ReentrantLock();
    private Connection connection;

    // Private constructor prevents instantiation from outside
    private DatabaseConnection() {
        try {
            // Simulate database connection initialization
            this.connection = createConnection();
            System.out.println("Database connection established");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create database connection", e);
        }
    }

    /**
     * Thread-safe singleton instance getter using double-checked locking
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * Alternative implementation using synchronized method (simpler but less performant)
     */
    public static synchronized DatabaseConnection getInstanceSynchronized() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private Connection createConnection() throws SQLException {
        // In a real implementation, this would connect to an actual database
        // For demonstration, we'll simulate a connection
        String url = "jdbc:h2:mem:testdb";
        String user = "sa";
        String password = "";

        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void executeQuery(String query) {
        System.out.println("Executing query: " + query);
        // In real implementation, would execute the query using the connection
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    // Prevent cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton cannot be cloned");
    }

    // Prevent deserialization from creating new instances
    private Object readResolve() {
        return getInstance();
    }
}