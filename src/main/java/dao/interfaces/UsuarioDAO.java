package dao.interfaces;

import modelo.Usuario;

import java.sql.SQLException;
import java.util.List;

public interface UsuarioDAO {
    boolean insertarUsuario(Usuario usuario) throws SQLException;
    boolean actualizarUsuario(Usuario usuario) throws SQLException;
    boolean eliminarUsuario(int idUsuario) throws SQLException;
    Usuario buscarUsuarioPorId(int idUsuario) throws SQLException;
    List<Usuario> listarUsuarios() throws SQLException;
    Usuario autenticarUsuario(String rut, String contrasena) throws SQLException;
}
