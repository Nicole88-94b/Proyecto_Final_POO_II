package dao.interfaces;

import modelo.Estudiante;

import java.sql.SQLException;
import java.util.List;

/**
 * Define las operaciones CRUD disponibles para estudiantes.
 */
public interface EstudianteDAO {
    /**
     * Registra un estudiante y recupera su ID.
     *
     * @param estudiante estudiante que se desea registrar
     * @return {@code true} si fue registrado
     * @throws SQLException si ocurre un problema de persistencia
     */
    boolean insertarEstudiante(Estudiante estudiante) throws SQLException;

    /**
     * Actualiza los datos de un estudiante existente.
     *
     * @param estudiante estudiante con los datos modificados
     * @return {@code true} si el registro fue actualizado
     * @throws SQLException si ocurre un problema de persistencia
     */
    boolean actualizarEstudiante(Estudiante estudiante) throws SQLException;

    /**
     * Elimina un estudiante que no tenga préstamos asociados.
     *
     * @param idEstudiante identificador del estudiante
     * @return {@code true} si el registro fue eliminado
     * @throws SQLException si el estudiante mantiene relaciones en la base de datos
     */
    boolean eliminarEstudiante(int idEstudiante) throws SQLException;

    /**
     * Busca un estudiante por su identificador.
     *
     * @param idEstudiante identificador del estudiante
     * @return estudiante encontrado o {@code null}
     * @throws SQLException si la consulta no puede ejecutarse
     */
    Estudiante buscarEstudiantePorId(int idEstudiante) throws SQLException;

    /**
     * Recupera todos los estudiantes registrados.
     *
     * @return lista de estudiantes
     * @throws SQLException si la consulta no puede ejecutarse
     */
    List<Estudiante> listarEstudiantes() throws SQLException;

    /**
     * Busca un estudiante mediante su RUT.
     *
     * @param rut RUT que se desea consultar
     * @return estudiante encontrado o {@code null}
     * @throws SQLException si la consulta no puede ejecutarse
     */
    Estudiante buscarEstudiantePorRut(String rut) throws SQLException;
}
