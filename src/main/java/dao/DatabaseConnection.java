package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Administra la conexión compartida con MySQL mediante el patrón Singleton.
 * La contraseña se obtiene desde la variable de entorno
 * {@code BIBLIOTECA_DB_PASSWORD} para no guardarla en el código fuente.
 */
public class DatabaseConnection {
    private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    public static final String DB_URL ="jdbc:mysql://localhost:3306/biblioteca";
    public static final String USER = "root";
    public static final String PASS = System.getenv("BIBLIOTECA_DB_PASSWORD");
    private Connection connection;

    private DatabaseConnection() {
    }

    /**
     * Devuelve la única instancia disponible del administrador de conexión.
     *
     * @return instancia Singleton de la clase
     */
    public static DatabaseConnection getInstance() {
        return INSTANCE;
    }

    /**
     * Entrega una conexión activa y crea una nueva si la anterior fue cerrada.
     *
     * @return conexión activa con la base de datos biblioteca
     * @throws SQLException si MySQL no está disponible o las credenciales son incorrectas
     */
    public synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        return connection;
    }

}
