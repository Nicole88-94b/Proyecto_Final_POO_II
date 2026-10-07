package dao.interfaces;

import modelo.Libro;

import java.sql.SQLException;
import java.util.List;

public interface LibroDAO {
    boolean insertarLibro(Libro libro) throws SQLException;
    boolean actualizarLibro(Libro libro) throws SQLException;
    boolean eliminarLibro(int idLibro) throws SQLException;
    Libro buscarLibroPorId(int idLibro) throws SQLException;
    List<Libro> listarLibros() throws SQLException;
}
