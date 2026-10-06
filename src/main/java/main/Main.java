package main;

import dao.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection db = DatabaseConnection.getInstance().getConnection()) {
            System.out.println("Conectado");
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar");
        }
    }
}
