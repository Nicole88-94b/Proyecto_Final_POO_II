package controlador;

import dao.CategoriaDAOImpl;
import dao.LibroDAOImpl;
import dao.interfaces.CategoriaDAO;
import dao.interfaces.LibroDAO;
import modelo.Categoria;
import modelo.Libro;

import java.sql.SQLException;
import java.util.List;

public class ControladorDeLibros {
    private LibroDAO libroDAO;
    private CategoriaDAO categoriaDAO;

    public ControladorDeLibros() {
        this.libroDAO = new LibroDAOImpl();
        this.categoriaDAO = new CategoriaDAOImpl();
    }
    public List<Libro> listarLibros() throws SQLException {
        return this.libroDAO.listarLibros();
    }

    public List <Categoria> listarCategorias() throws SQLException{
        return this.categoriaDAO.listarCategorias();
    }

    public boolean registrarLibro(Libro libro) throws SQLException {
        return this.libroDAO.insertarLibro(libro);
    }

    public boolean actualizarLibro(Libro libro) throws SQLException {
        return this.libroDAO.actualizarLibro(libro);
    }

    public boolean eliminarLibro(int idLibro) throws SQLException {
        return this.libroDAO.eliminarLibro(idLibro);
    }

    public Libro buscarLibroPorIsbn(String isbn) throws SQLException {
        return this.libroDAO.buscarLibroPorISBN(isbn);
    }

}
