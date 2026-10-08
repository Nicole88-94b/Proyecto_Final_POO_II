package main;

import controlador.ControladorDePrestamos;
import dao.*;
import dao.interfaces.*;
import modelo.*;
import vista.VentanaGestionLibros;

import javax.swing.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        //PRUEBAS

        Categoria categoria = new Categoria(0, "Ciencia Ficcion");
        Estudiante estudiante = new Estudiante("pepito", "18870358-5", "pruebita@prueba", 0, "POO");
        Libro libro1 = new Libro(0, "ana torrojas", "ana", "ISDHJSH", "santillana", 1,
                categoria);
        LocalDate fechaPrestamo = LocalDate.of(2026, 10, 5);
        LocalDate fechaDevolucion = LocalDate.of(2026, 10, 6);

        Prestamo prestamo = new Prestamo(0, estudiante, libro1,
                fechaPrestamo, fechaDevolucion, false);
        CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
        Categoria categoria2 = new Categoria(0, "Horror");
        LibroDAO libroDAO = new LibroDAOImpl();
        EstudianteDAO estudianteDAO = new EstudianteDAOImpl();
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        Usuario usuario1 = new Usuario("Nicole", "18870358-5", "ni.ortegar@duoc.cl",
                0, "abcd123", "bibliotecario");
        PrestamosDAO prestamosDAO = new PrestamosDAOImpl();
        ControladorDePrestamos controladorDePrestamos = new ControladorDePrestamos();

        SwingUtilities.invokeLater(() -> {
            VentanaGestionLibros ventana = new VentanaGestionLibros();
            ventana.setVisible(true);
        });

    }
}
