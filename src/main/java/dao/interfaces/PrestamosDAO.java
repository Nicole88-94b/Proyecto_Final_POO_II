package dao.interfaces;

import modelo.Prestamo;

import java.sql.SQLException;
import java.util.List;

/**
 * Define las operaciones de persistencia para préstamos y devoluciones.
 */
public interface PrestamosDAO {
    /**
     * Registra un préstamo y recupera su ID.
     *
     * @param prestamo préstamo que se desea guardar
     * @return {@code true} si fue registrado
     * @throws SQLException si ocurre un problema de persistencia
     */
    boolean insertarPrestamo(Prestamo prestamo) throws SQLException;

    /**
     * Actualiza los datos y el estado de devolución de un préstamo.
     *
     * @param prestamo préstamo con los datos actualizados
     * @return {@code true} si fue actualizado
     * @throws SQLException si ocurre un problema de persistencia
     */
    boolean actualizarPrestamo(Prestamo prestamo) throws SQLException;

    /**
     * Elimina un préstamo por su identificador.
     *
     * @param idPrestamo identificador del préstamo
     * @return {@code true} si fue eliminado
     * @throws SQLException si ocurre un problema de persistencia
     */
    boolean eliminarPrestamo(int idPrestamo) throws SQLException;

    /**
     * Busca un préstamo por su identificador.
     *
     * @param idPrestamo identificador del préstamo
     * @return préstamo encontrado o {@code null}
     * @throws SQLException si la consulta no puede ejecutarse
     */
    Prestamo buscarPrestamoPorId(int idPrestamo) throws SQLException;

    /**
     * Recupera el historial completo de préstamos.
     *
     * @return lista de préstamos
     * @throws SQLException si la consulta no puede ejecutarse
     */
    List<Prestamo> listarPrestamos() throws SQLException;
}
