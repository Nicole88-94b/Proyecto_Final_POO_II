package main;

import dao.CategoriaDAOImpl;
import dao.EstudianteDAOImpl;
import dao.LibroDAOImpl;
import dao.UsuarioDAOImpl;
import dao.interfaces.CategoriaDAO;
import dao.interfaces.EstudianteDAO;
import dao.interfaces.LibroDAO;
import dao.interfaces.UsuarioDAO;
import modelo.*;

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
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        Usuario usuario1 = new Usuario("Nicole", "18870358-5", "ni.ortegar@duoc.cl",
                0, "abcd123", "bibliotecario");



        try {
            List<Usuario> usuarios = usuarioDAO.listarUsuarios();
            for (Usuario usuario : usuarios) {
                System.out.println(usuario.getRut() + " " + usuario.getContrasena());
            }
           Usuario autenticado = usuarioDAO.autenticarUsuario("12345678-9", "clave123");
            if (autenticado != null) {
                System.out.println("Usuario autenticado");
            } else {
                System.out.println("Usuario no autenticado");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar");
        }
    }
}
