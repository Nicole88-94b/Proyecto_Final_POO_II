package main;

import controlador.ControladorDePrestamos;
import dao.*;
import dao.interfaces.*;
import modelo.*;

import java.sql.SQLException;
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


        // Usa el ID de un préstamo creado con el controlador; no se crea otro aquí.
        int idPrestamoPrueba = 12;

        try {
            Prestamo prestamoPrueba = prestamosDAO.buscarPrestamoPorId(idPrestamoPrueba);
            if (prestamoPrueba == null) {
                throw new IllegalArgumentException("No existe el préstamo de prueba.");
            }

            // El ID del libro se obtiene del préstamo, sin sumarle el stock.
            int idLibroPrueba = prestamoPrueba.getLibro().getIdLibro();
            Libro libroAntes = libroDAO.buscarLibroPorId(idLibroPrueba);
            int stockAntes = libroAntes.getStock();
            boolean yaEstabaDevuelto = prestamoPrueba.isDevuelto();
            System.out.println("Préstamo: " + idPrestamoPrueba);
            System.out.println("Libro: " + libroAntes.getTitulo());
            System.out.println("Stock antes: " + stockAntes);

            // Sólo el controlador guarda la devolución y modifica el stock.
            try {
                boolean devuelto = controladorDePrestamos.devolverPrestamo(idPrestamoPrueba);
                System.out.println("Resultado de la devolución: " + devuelto);
            } catch (IllegalStateException e) {
                System.out.println("Devolución rechazada: " + e.getMessage());
            }

            // Consultamos nuevamente MySQL; el objeto anterior no se actualiza solo.
            Libro libroDespues = libroDAO.buscarLibroPorId(idLibroPrueba);
            int stockDespues = libroDespues.getStock();
            Prestamo prestamoDespues = prestamosDAO.buscarPrestamoPorId(idPrestamoPrueba);
            System.out.println("Stock después: " + stockDespues);
            System.out.println("Estado devuelto en MySQL: " + prestamoDespues.isDevuelto());
            if (yaEstabaDevuelto) {
                System.out.println("Stock sin cambios: " + (stockDespues == stockAntes));
            } else {
                System.out.println("Stock aumentó en uno: " + (stockDespues == stockAntes + 1));
            }

            // Un segundo intento debe rechazarse sin aumentar nuevamente el stock.
            try {
                controladorDePrestamos.devolverPrestamo(idPrestamoPrueba);
                System.out.println("ERROR: el sistema permitió una segunda devolución.");
            } catch (IllegalStateException e) {
                System.out.println("Segundo intento rechazado: " + e.getMessage());
            }
            Libro libroFinal = libroDAO.buscarLibroPorId(idLibroPrueba);
            System.out.println("Stock final: " + libroFinal.getStock());
            System.out.println("Stock igual tras el segundo intento: "
                    + (libroFinal.getStock() == stockDespues));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al consultar o guardar en MySQL: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Prueba rechazada: " + e.getMessage());
        }
    }
}
