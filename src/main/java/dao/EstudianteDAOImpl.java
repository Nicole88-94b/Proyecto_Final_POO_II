package dao;

import dao.interfaces.EstudianteDAO;
import modelo.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAOImpl  implements EstudianteDAO {


    @Override
    public boolean insertarEstudiante(Estudiante estudiante) throws SQLException {
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo.");
        }
        String sql = "INSERT INTO estudiantes (nombre, rut, curso, correo) VALUES (?, ?, ?, ?)";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getRut());
            ps.setString(3, estudiante.getCurso());
            ps.setString(4, estudiante.getCorreo());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                return false;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    estudiante.setIdEstudiante(rs.getInt(1));
                    return true;
                }
            }
            throw new SQLException("No fue posible recuperar el ID del estudiante insertado.");

        }
    }

    @Override
    public boolean actualizarEstudiante(Estudiante estudiante) throws SQLException {
       if (estudiante == null || estudiante.getIdEstudiante() <= 0) {
           throw new IllegalArgumentException("El estudiante no puede ser nulo o no tener ID.");
       }
       String sql = "UPDATE estudiantes SET nombre = ?, rut = ?, curso = ?, correo = ? WHERE id = ?";
       Connection db = DatabaseConnection.getInstance().getConnection();
       try (PreparedStatement ps = db.prepareStatement(sql)) {
           ps.setString(1, estudiante.getNombre());
           ps.setString(2, estudiante.getRut());
           ps.setString(3, estudiante.getCurso());
           ps.setString(4, estudiante.getCorreo());
           ps.setInt(5, estudiante.getIdEstudiante());
           return ps.executeUpdate() > 0;
       }
    }

    @Override
    public boolean eliminarEstudiante(int idEstudiante) throws SQLException {
        if (idEstudiante <= 0) {
            throw new IllegalArgumentException("El estudiante debe tener asignado un ID");
        }
        String sql = "DELETE FROM estudiantes WHERE id = ?";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setInt(1, idEstudiante);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Estudiante buscarEstudiantePorId(int idEstudiante) throws SQLException {
        String sql = "SELECT id, nombre, rut, curso, correo FROM estudiantes WHERE id = ?";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setInt(1, idEstudiante);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Estudiante(rs.getString("nombre"),
                            rs.getString("rut"), rs.getString("correo"),
                            rs.getInt("id"), rs.getString("curso"));
                }
            }
            return null;
        }
    }

    @Override
    public List<Estudiante> listarEstudiantes() throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT id, nombre, rut, curso, correo FROM estudiantes ORDER BY id";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Estudiante estudiante = new Estudiante(rs.getString("nombre"),
                        rs.getString("rut"), rs.getString("correo"),
                        rs.getInt("id"), rs.getString("curso"));
                estudiantes.add(estudiante);
            }
            return estudiantes;
        }

    }
}
