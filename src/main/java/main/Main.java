package main;

import dao.DatabaseConnection;
import modelo.Categoria;
import modelo.Estudiante;
import modelo.Libro;
import modelo.Prestamo;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        //PRUEBAS
        Categoria categoria = new Categoria(0, "Ciencia Ficcion");
        Estudiante estudiante = new Estudiante("pepito", "18870358-5", "pruebita@prueba", 0, "POO");
        Libro libro1 = new Libro(0, "ana torrojas", "ana", "ISDHDJSH", "santillana", 1,
                categoria);
        LocalDate fechaPrestamo = LocalDate.of(2026, 10, 5);
        LocalDate fechaDevolucion = LocalDate.of(2026, 10, 6);

        Prestamo prestamo = new Prestamo(0, estudiante, libro1,
                fechaPrestamo, fechaDevolucion, false);


        try (Connection db = DatabaseConnection.getInstance().getConnection()) {
            System.out.println("Conectado");
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar");
        }
    }
}
