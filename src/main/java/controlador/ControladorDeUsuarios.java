package controlador;

import dao.UsuarioDAOImpl;
import dao.interfaces.UsuarioDAO;
import modelo.Usuario;

import java.sql.SQLException;

public class ControladorDeUsuarios {
    private UsuarioDAO usuarioDAO;

    public ControladorDeUsuarios() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

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
