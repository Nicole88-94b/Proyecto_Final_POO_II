package dao.interfaces;

import modelo.Libro;

import java.sql.SQLException;
import java.util.List;

/**
 * Define las operaciones CRUD y búsqueda disponibles para libros.
 */
public interface LibroDAO {
    /**
     * Registra un libro y recupera su ID.
     *
     * @param libro libro que se desea guardar
     * @return {@code true} si fue registrado
     * @throws SQLException si ocurre un problema de persistencia
     */
    boolean insertarLibro(Libro libro) throws SQLException;

    /**
     * Actualiza los datos y el stock de un libro.
     *
     * @param libro libro con los datos modificados
     * @return {@code true} si fue actualizado
     * @throws SQLException si ocurre un problema de persistencia
     */
    boolean actualizarLibro(Libro libro) throws SQLException;

    /**
     * Elimina un libro que no tenga préstamos asociados.
     *
     * @param idLibro identificador del libro
     * @return {@code true} si fue eliminado
     * @throws SQLException si el libro mantiene relaciones en la base de datos
     */
    boolean eliminarLibro(int idLibro) throws SQLException;

    /**
     * Busca un libro por su identificador.
     *
     * @param idLibro identificador del libro
     * @return libro encontrado o {@code null}
     * @throws SQLException si la consulta no puede ejecutarse
     */
    Libro buscarLibroPorId(int idLibro) throws SQLException;

    /**
     * Recupera el catálogo completo de libros.
     *
     * @return lista de libros
     * @throws SQLException si la consulta no puede ejecutarse
     */
    List<Libro> listarLibros() throws SQLException;

    /**
     * Busca un libro mediante su ISBN.
     *
     * @param isbn ISBN que se desea consultar
     * @return libro encontrado o {@code null}
     * @throws SQLException si la consulta no puede ejecutarse
     */
    Libro buscarLibroPorISBN(String isbn) throws SQLException;
}
