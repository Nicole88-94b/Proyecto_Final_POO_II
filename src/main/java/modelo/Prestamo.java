package modelo;

import java.time.LocalDate;

/**
 * Registra la relación entre un estudiante y un libro prestado.
 * Conserva las fechas del préstamo y el estado de devolución.
 */
public class Prestamo {
    private int idPrestamo;
    private Estudiante estudiante;
    private Libro libro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private boolean devuelto;

    public Prestamo(int idPrestamo, Estudiante estudiante, Libro libro, LocalDate fechaPrestamo, LocalDate fechaDevolucion, boolean devuelto) {
        this.idPrestamo = idPrestamo;
        setEstudiante(estudiante);
        setLibro(libro);
        setFechaPrestamo(fechaPrestamo);
        setFechaDevolucion(fechaDevolucion);
        this.devuelto = devuelto;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            throw new IllegalArgumentException("Todo préstamo debe tener un estudiante asociado.");
        }
        this.estudiante = estudiante;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("Un préstamo debe tener un libro asociado.");
        }
        this.libro = libro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        if (fechaPrestamo == null) {
            throw new IllegalArgumentException("Debe ingresar una fecha");
        }
        if (this.fechaDevolucion != null && fechaPrestamo.isAfter(this.fechaDevolucion)){
            throw new IllegalArgumentException("La fecha del préstamo no puede ser posterior a la fecha de devolución");
        }
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        if (fechaDevolucion == null) {
            throw new IllegalArgumentException("Debe ingresar una fecha");
        }
        if (this.fechaPrestamo != null && fechaDevolucion.isBefore(this.fechaPrestamo)){
            throw new IllegalArgumentException("La fecha límite de devolución no puede ser anterior a la fecha de préstamo");
        }
        this.fechaDevolucion = fechaDevolucion;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }
}
