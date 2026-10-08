package dao;

import dao.interfaces.UsuarioDAO;
import modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa el acceso JDBC a la tabla {@code usuarios}.
 */
public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public boolean insertarUsuario(Usuario usuario) throws SQLException {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        String sql = "INSERT INTO usuarios (nombre, rut, correo, contraseña, rol) VALUES (?, ?, ?, ?, ?)";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getRut());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getContrasena());
            ps.setString(5, usuario.getRol());

            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas == 0) {
                return false;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt(1));
                    return true;
                }
            }
            throw new SQLException("No ha sido posible recuperar el ID del usuario insertado.");
        }
    }

    @Override
    public boolean actualizarUsuario(Usuario usuario) throws SQLException {
        if (usuario == null || usuario.getIdUsuario() <= 0) {
            throw new IllegalArgumentException("El usuario no puede ser nulo o no tener ID.");
        }
        String sql = "UPDATE usuarios SET nombre = ?, rut = ?,correo = ?, contraseña = ?, rol = ? WHERE id = ?";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getRut());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getContrasena());
            ps.setString(5, usuario.getRol());
            ps.setInt(6, usuario.getIdUsuario());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminarUsuario(int idUsuario) throws SQLException {
        if (idUsuario <= 0) {
            throw new IllegalArgumentException("El usuario debe tener asignado un ID");
        }
        String sql = "DELETE FROM usuarios WHERE id = ?";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Usuario buscarUsuarioPorId(int idUsuario) throws SQLException {
        if (idUsuario <= 0) {
            throw new IllegalArgumentException("El usuario debe tener asignado un ID");
        }
        String sql = "SELECT id, nombre, rut, correo, contraseña, rol FROM usuarios WHERE id = ?";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(rs.getString("nombre"),
                            rs.getString("rut"),
                            rs.getString("correo"),
                            rs.getInt("id"),
                            rs.getString("contraseña"),
                            rs.getString("rol"));
                }
            }
            return null;
        }

    }

    @Override
    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, nombre, rut, correo, contraseña, rol FROM usuarios ORDER BY id";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario(rs.getString("nombre"),
                        rs.getString("rut"),
                        rs.getString("correo"),
                        rs.getInt("id"),
                        rs.getString("contraseña"),
                        rs.getString("rol"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    @Override
    public Usuario autenticarUsuario(String rut, String contrasena) throws SQLException {
        if (rut == null || rut.trim().isEmpty() || contrasena == null || contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("El usuario o la contraseña no puede ser nulo o estar vacíos");
        }
        String sql = "SELECT id, nombre, rut, correo, contraseña, rol FROM usuarios WHERE rut = ? AND contraseña =?";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setString(1, rut.trim());
            ps.setString(2, contrasena);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario(rs.getString("nombre"),
                            rs.getString("rut"),
                            rs.getString("correo"),
                            rs.getInt("id"),
                            rs.getString("contraseña"),
                            rs.getString("rol"));
                    return usuario;
                }
            }
            return null;
        }
    }
}
