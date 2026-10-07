package main;

import dao.CategoriaDAOImpl;
import dao.EstudianteDAOImpl;
import dao.LibroDAOImpl;
import dao.interfaces.CategoriaDAO;
import dao.interfaces.EstudianteDAO;
import dao.interfaces.LibroDAO;
import modelo.Categoria;
import modelo.Estudiante;
import modelo.Libro;
import modelo.Prestamo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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




        try {


            List<Estudiante> estudiantes = estudianteDAO.listarEstudiantes();
            for (Estudiante est : estudiantes) {
                System.out.println(est.getNombre() + " " + est.getCurso() + " " + est.getIdEstudiante());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar");
        }
    }
}
