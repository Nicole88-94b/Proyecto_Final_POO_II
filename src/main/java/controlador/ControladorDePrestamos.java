package controlador;

import dao.EstudianteDAOImpl;
import dao.LibroDAOImpl;
import dao.PrestamosDAOImpl;
import dao.interfaces.EstudianteDAO;
import dao.interfaces.LibroDAO;
import dao.interfaces.PrestamosDAO;
import modelo.Estudiante;
import modelo.Libro;
import modelo.Prestamo;

import java.sql.SQLException;
import java.time.LocalDate;

public class ControladorDePrestamos {
    private final EstudianteDAO estudianteDAO;
    private final PrestamosDAO prestamosDAO;
    private final LibroDAO libroDAO;

    public ControladorDePrestamos() {
        estudianteDAO = new EstudianteDAOImpl();
        prestamosDAO = new PrestamosDAOImpl();
        libroDAO = new LibroDAOImpl();
    }

    public synchronized Prestamo registrarPrestamo(int idEstudiante, int idLibro)
            throws SQLException {
        if (idEstudiante <= 0 || idLibro <= 0) {
            throw new IllegalArgumentException("El estudiante y el libro deben tener asignados ID");
        }
        Estudiante estudianteEncontrado = estudianteDAO.buscarEstudiantePorId(idEstudiante);
        if (estudianteEncontrado == null) {
            throw new IllegalArgumentException("El estudiante no existe");
        }
        Libro libroBuscado = libroDAO.buscarLibroPorId(idLibro);
        if (libroBuscado == null) {
            throw new IllegalArgumentException("El libro no existe");
        }
        if (libroBuscado.getStock() <= 0) {
            throw new IllegalStateException("El libro no tiene ejemplares disponible");
        }
        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaDevolucion = fechaPrestamo.plusDays(7);
        Prestamo prestamo = new Prestamo(0, estudianteEncontrado, libroBuscado, fechaPrestamo,
                fechaDevolucion, false);

        boolean insercionExitosa = prestamosDAO.insertarPrestamo(prestamo);
        if (insercionExitosa) {
            libroBuscado.setStock(libroBuscado.getStock() - 1);
            boolean actualizacionExitosa = libroDAO.actualizarLibro(libroBuscado);
            if (!actualizacionExitosa) {
                throw new SQLException("El préstamo se registró, pero no se pudo actualizar el stock");
            }
            return prestamo;
        } else {
            return null;
        }


    }

    public synchronized boolean devolverPrestamo(int idPrestamo)
            throws SQLException {
        if (idPrestamo <= 0) {
            throw new IllegalArgumentException("El ID del préstamo debe ser mayor que cero.");
        }
        Prestamo prestamo = prestamosDAO.buscarPrestamoPorId(idPrestamo);
        if (prestamo == null) {
            throw new IllegalArgumentException("El préstamo no existe");
        }
        if (prestamo.isDevuelto()) {
            throw new IllegalStateException("El préstamo ya fue devuelto");
        }
        prestamo.setDevuelto(true);
        boolean prestamoDevuelto = prestamosDAO.actualizarPrestamo(prestamo);
        if (!prestamoDevuelto) {
            throw new SQLException("Los sentimos, no fue posible devolver el préstamo");
        }
        Libro libro = prestamo.getLibro();
        libro.setStock(libro.getStock() + 1);
        boolean restitucionStock = libroDAO.actualizarLibro(prestamo.getLibro());
        if (!restitucionStock) {
            throw new SQLException("Los sentimos, se registró la devolución pero no fue posible restituir el stock del libro");
        }
        return true;
    }

}
