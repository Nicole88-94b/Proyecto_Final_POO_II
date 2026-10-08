package dao.interfaces;

import modelo.Usuario;

import java.sql.SQLException;
import java.util.List;

/**
 * Define las operaciones de persistencia y autenticación de usuarios.
 */
public interface UsuarioDAO {
    /**
     * Guarda un usuario y recupera el ID generado por MySQL.
     *
     * @param usuario cuenta que se desea registrar
     * @return {@code true} si el registro fue creado
     * @throws SQLException si ocurre un problema de persistencia
     */
    boolean insertarUsuario(Usuario usuario) throws SQLException;

    /**
     * Actualiza los datos de una cuenta existente.
     *
     * @param usuario cuenta con los nuevos datos
     * @return {@code true} si se actualizó algún registro
     * @throws SQLException si ocurre un problema de persistencia
     */
    boolean actualizarUsuario(Usuario usuario) throws SQLException;

    /**
     * Elimina una cuenta por su identificador.
     *
     * @param idUsuario identificador del usuario
     * @return {@code true} si la cuenta fue eliminada
     * @throws SQLException si ocurre un problema de persistencia
     */
    boolean eliminarUsuario(int idUsuario) throws SQLException;

    /**
     * Busca una cuenta por su identificador.
     *
     * @param idUsuario identificador del usuario
     * @return usuario encontrado o {@code null}
     * @throws SQLException si la consulta no puede ejecutarse
     */
    Usuario buscarUsuarioPorId(int idUsuario) throws SQLException;

    /**
     * Recupera todas las cuentas registradas.
     *
     * @return lista de usuarios
     * @throws SQLException si la consulta no puede ejecutarse
     */
    List<Usuario> listarUsuarios() throws SQLException;

    /**
     * Valida las credenciales de acceso contra la base de datos.
     *
     * @param rut RUT ingresado en el formulario
     * @param contrasena contraseña ingresada
     * @return usuario autenticado o {@code null} si las credenciales no coinciden
     * @throws SQLException si la consulta no puede ejecutarse
     */
    Usuario autenticarUsuario(String rut, String contrasena) throws SQLException;
}
