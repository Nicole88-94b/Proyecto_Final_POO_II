package dao.interfaces;

import modelo.Categoria;

import java.sql.SQLException;
import java.util.List;

public interface CategoriaDAO {
    boolean insertarCategoria(Categoria categoria) throws SQLException;
    List<Categoria> listarCategorias() throws SQLException;
    Categoria buscarCategoriaPorId(int idCategoria) throws SQLException;
    boolean actualizarCategoria(Categoria categoria) throws SQLException;
    boolean eliminarCategoria(int idCategoria) throws SQLException;
}
