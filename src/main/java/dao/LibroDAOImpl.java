package dao;

import dao.interfaces.CategoriaDAO;
import dao.interfaces.LibroDAO;
import modelo.Categoria;
import modelo.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    @Override
    public boolean insertarLibro(Libro libro) throws SQLException {
        if (libro == null || libro.getCategoria().getIdCategoria() <= 0) {
            throw new IllegalArgumentException("El libro es nulo o su categoría no tiene un ID válido.");
        }
        String sql = "INSERT INTO libros (titulo, autor, isbn, editorial, stock, id_categoria) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = DatabaseConnection.getInstance().getConnection().
                prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getStock());
            ps.setInt(6, libro.getCategoria().getIdCategoria());
            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas == 0) {
                return false;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    libro.setIdLibro(rs.getInt(1));
                    return true;
                }
            }
            throw new SQLException("El libro se insertó, pero no se pudo recuperar su ID.");
        }

    }

    @Override
    public boolean actualizarLibro(Libro libro) throws SQLException {
        if (libro == null || libro.getIdLibro() <= 0 || libro.getCategoria().getIdCategoria() <= 0) {
            throw new IllegalArgumentException("El libro o su categoría no tienen un ID válido.");
        }
        String sql = "UPDATE libros SET titulo = ?, autor = ?, isbn = ?, editorial = ?, stock = ?, " +
                "id_categoria = ? WHERE id = ?";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getStock());
            ps.setInt(6, libro.getCategoria().getIdCategoria());
            ps.setInt(7, libro.getIdLibro());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminarLibro(int idLibro) throws SQLException {
        if (idLibro <= 0) {
            throw new IllegalArgumentException("El libro debe tener registrado un ID");
        }
        String sql = "DELETE FROM libros WHERE id = ?";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setInt(1, idLibro);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Libro buscarLibroPorId(int idLibro) throws SQLException {
        String sql = "SELECT id, titulo, autor, isbn, editorial, stock, id_categoria FROM libros WHERE id = ?";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setInt(1, idLibro);
            CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Categoria categoriaLibro = categoriaDAO.buscarCategoriaPorId(rs.getInt("id_categoria"));
                    if (categoriaLibro == null) {
                        throw new SQLException("No se encontró la categoría del libro con ID " + rs.getInt("id"));
                    }
                    return new Libro(rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getString("isbn"),
                            rs.getString("editorial"),
                            rs.getInt("stock"), categoriaLibro);
                }
            }
        }
        return null;
    }

    @Override
    public List<Libro> listarLibros() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, isbn, editorial, stock, id_categoria FROM libros ORDER BY id";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
            while (rs.next()) {
                Categoria categoriaLibro = categoriaDAO.buscarCategoriaPorId(rs.getInt("id_categoria"));
                if (categoriaLibro == null) {
                    throw new SQLException("No se encontró la categoría del libro con ID " + rs.getInt("id"));
                }
                Libro libro = new Libro(rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("isbn"),
                        rs.getString("editorial"),
                        rs.getInt("stock"), categoriaLibro);
                libros.add(libro);
            }
        }
        return libros;
    }

    @Override
    public Libro buscarLibroPorISBN(String isbn) throws SQLException {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("Se debe ingresar un ISBN válido");

        }
        String sql = "SELECT id, titulo, autor, isbn, editorial, stock, id_categoria FROM libros WHERE isbn = ?";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setString(1, isbn.trim());
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
                    Categoria categoriaLibro = categoriaDAO.buscarCategoriaPorId(rs.getInt("id_categoria"));
                    if (categoriaLibro == null) {
                        throw new SQLException("No se encontró la categoría del libro");
                    }
                    return new Libro(rs.getInt("id"), rs.getString("titulo"),
                            rs.getString("autor"), rs.getString("isbn"),
                            rs.getString("editorial"), rs.getInt("stock"), categoriaLibro);
                }
            }
        }
        return null;
    }

}


