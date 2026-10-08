package dao.interfaces;

import modelo.Categoria;

import java.sql.SQLException;
import java.util.List;

/**
 * Define las operaciones de persistencia para las categorías del catálogo.
 */
public interface CategoriaDAO {
    /**
     * Registra una categoría y recupera su ID.
     *
     * @param categoria categoría que se desea guardar
     * @return {@code true} si fue registrada
     * @throws SQLException si ocurre un problema de persistencia
     */
    boolean insertarCategoria(Categoria categoria) throws SQLException;

    /**
     * Recupera todas las categorías disponibles.
     *
     * @return lista de categorías
     * @throws SQLException si la consulta no puede ejecutarse
     */
    List<Categoria> listarCategorias() throws SQLException;

    /**
     * Busca una categoría por su identificador.
     *
     * @param idCategoria identificador de la categoría
     * @return categoría encontrada o {@code null}
     * @throws SQLException si la consulta no puede ejecutarse
     */
    Categoria buscarCategoriaPorId(int idCategoria) throws SQLException;

    /**
     * Actualiza el nombre de una categoría.
     *
     * @param categoria categoría con los datos modificados
     * @return {@code true} si fue actualizada
     * @throws SQLException si ocurre un problema de persistencia
     */
    boolean actualizarCategoria(Categoria categoria) throws SQLException;

    /**
     * Elimina una categoría que no esté asociada a libros.
     *
     * @param idCategoria identificador de la categoría
     * @return {@code true} si fue eliminada
     * @throws SQLException si mantiene relaciones en la base de datos
     */
    boolean eliminarCategoria(int idCategoria) throws SQLException;
}
