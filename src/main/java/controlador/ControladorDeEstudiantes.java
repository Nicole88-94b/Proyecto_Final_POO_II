package controlador;

import dao.EstudianteDAOImpl;
import dao.UsuarioDAOImpl;
import dao.interfaces.EstudianteDAO;
import dao.interfaces.UsuarioDAO;
import modelo.Estudiante;
import modelo.Usuario;

import java.sql.SQLException;
import java.util.List;

public class ControladorDeEstudiantes {
    private EstudianteDAO estudianteDAO;
    private UsuarioDAO usuarioDAO;

    public ControladorDeEstudiantes() {
        this.estudianteDAO = new EstudianteDAOImpl();
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    public List<Estudiante> listarEstudiantes() throws SQLException {
        return this.estudianteDAO.listarEstudiantes();
    }


    public boolean registrarEstudianteConCuenta (Estudiante estudiante, String contrasena) throws SQLException {
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo.");
        }
        Usuario usuario = new Usuario(estudiante.getNombre(), estudiante.getRut(), estudiante.getCorreo(), 0, contrasena, "estudiante");

        boolean estudianteRegistrado = estudianteDAO.insertarEstudiante(estudiante);
        if (!estudianteRegistrado) {
            return false;
        }
        return usuarioDAO.insertarUsuario(usuario);
    }

    private Usuario buscarUsuarioPorRut(String rut) throws SQLException {
        for (Usuario usuario : usuarioDAO.listarUsuarios()) {
            if (usuario.getRut().equalsIgnoreCase(rut)) {
                return usuario;
            }
        }
        return null;
    }

    public boolean actualizarEstudianteConCuenta(Estudiante estudiante, String nuevaContrasena) throws SQLException {
        Usuario usuario = buscarUsuarioPorRut(estudiante.getRut());
        if (usuario == null) {
            throw new IllegalArgumentException("No se encontró la cuenta asociada al estudiante.");
        }

        boolean estudianteActualizado = estudianteDAO.actualizarEstudiante(estudiante);
        if (!estudianteActualizado) {
            return false;
        }

        usuario.setNombre(estudiante.getNombre());
        usuario.setCorreo(estudiante.getCorreo());

        if (nuevaContrasena != null && !nuevaContrasena.trim().isEmpty()) {
            usuario.setContrasena(nuevaContrasena);
        }
        return usuarioDAO.actualizarUsuario(usuario);
    }

    public boolean eliminarEstudianteConCuenta(int idEstudiante) throws SQLException {
        Estudiante estudiante = estudianteDAO.buscarEstudiantePorId(idEstudiante);
        if (estudiante == null) {
            throw new IllegalArgumentException("No se encontró el estudiante.");
        }

        Usuario usuario = buscarUsuarioPorRut(estudiante.getRut());
        boolean estudianteEliminado = estudianteDAO.eliminarEstudiante(idEstudiante);

        if (!estudianteEliminado) {
            return false;
        }
        if (usuario == null) {
            return true;
        }
        return usuarioDAO.eliminarUsuario(usuario.getIdUsuario());
    }

}
