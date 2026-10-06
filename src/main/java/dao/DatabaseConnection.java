package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    public static final String DB_URL ="jdbc:mysql://localhost:3306/biblioteca";
    public static final String USER = "root";
    public static final String PASS = System.getenv("BIBLIOTECA_DB_PASSWORD");
    private Connection connection;

    private DatabaseConnection() {

    }
    public static DatabaseConnection getInstance() {
        return INSTANCE;
    }
    public synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        return connection;
    }

}