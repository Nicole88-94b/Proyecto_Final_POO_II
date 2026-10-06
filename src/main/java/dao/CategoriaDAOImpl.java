package dao;

import dao.interfaces.CategoriaDAO;
import modelo.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {

    @Override
    public boolean insertarCategoria(Categoria categoria) throws SQLException {
        if (categoria == null) {
            throw new IllegalArgumentException("Debe ingresar una categoría válida");
        }
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, categoria.getNombre());
            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas == 0) {
                return false;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    categoria.setIdCategoria(rs.getInt(1));
                    return true;
                }
            }
            throw new SQLException("La categoría se insertó, pero no se pudo recuperar su ID.");
        }
    }

    @Override
    public List<Categoria> listarCategorias() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id, nombre FROM categorias ORDER BY id";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
                 while (rs.next()) {
                     Categoria categoria = new Categoria(rs.getInt("id"), rs.getString("nombre"));
                     categorias.add(categoria);
                 }
        }

        return categorias;
    }

    @Override
    public Categoria buscarCategoriaPorId(int idCategoria) throws SQLException {
        String sql = "SELECT id, nombre FROM categorias WHERE id = ?";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Categoria(rs.getInt("id"), rs.getString("nombre"));
                }
                return null;
            }
        }
    }

    @Override
    public boolean actualizarCategoria(Categoria categoria) throws SQLException {
        if (categoria == null || categoria.getIdCategoria() <= 0) {
            throw new IllegalArgumentException("Categoría inválida.");
        }
        String sql = "UPDATE categorias SET nombre = ? WHERE id = ?";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            ps.setInt(2, categoria.getIdCategoria());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminarCategoria(int idCategoria) throws SQLException {
        if (idCategoria <= 0) {
            throw new IllegalArgumentException("La categoría no es válida.");
        }
        String sql = "DELETE FROM categorias WHERE id = ?";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            return ps.executeUpdate() > 0;
        }
    }
}
