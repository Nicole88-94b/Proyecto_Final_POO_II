package controlador;

import dao.UsuarioDAOImpl;
import dao.interfaces.UsuarioDAO;
import modelo.Usuario;

import java.sql.SQLException;

/**
 * Coordina la autenticación y las operaciones de cuentas de usuario.
 */
public class ControladorDeUsuarios {
    private UsuarioDAO usuarioDAO;

    public ControladorDeUsuarios() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    /**
     * Valida los datos de acceso y consulta las credenciales registradas.
     *
     * @param rut RUT ingresado por la persona
     * @param contrasena contraseña ingresada
     * @return usuario autenticado o {@code null} si no existe coincidencia
     * @throws SQLException si la consulta no puede ejecutarse
     */
    public Usuario autenticarUsuario(String rut, String contrasena) throws SQLException {
        if (rut == null || rut.trim().isEmpty() || contrasena == null || contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar el RUT y la contraseña.");
        }
        return this.usuarioDAO.autenticarUsuario(rut, contrasena);
    }
    public boolean registrarUsuario(Usuario usuario) throws SQLException {
        return this.usuarioDAO.insertarUsuario(usuario);
    }
    public boolean actualizarUsuario(Usuario usuario) throws SQLException {
        return this.usuarioDAO.actualizarUsuario(usuario);
    }
    public boolean eliminarUsuario(int idUsuario) throws SQLException {
        return this.usuarioDAO.eliminarUsuario(idUsuario);
    }
    public Usuario buscarUsuarioPorId(int idUsuario) throws SQLException {
        return this.usuarioDAO.buscarUsuarioPorId(idUsuario);
    }
}
