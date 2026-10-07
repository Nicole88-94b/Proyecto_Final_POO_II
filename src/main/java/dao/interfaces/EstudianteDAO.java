package dao.interfaces;

import modelo.Estudiante;

import java.sql.SQLException;
import java.util.List;

public interface EstudianteDAO {
    boolean insertarEstudiante(Estudiante estudiante) throws SQLException;
    boolean actualizarEstudiante(Estudiante estudiante) throws SQLException;
    boolean eliminarEstudiante(int idEstudiante) throws SQLException;
    Estudiante buscarEstudiantePorId(int idEstudiante) throws SQLException;
    List<Estudiante> listarEstudiantes() throws SQLException;
}
