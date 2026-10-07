package dao;

import dao.interfaces.EstudianteDAO;
import dao.interfaces.LibroDAO;
import dao.interfaces.PrestamosDAO;
import modelo.Estudiante;
import modelo.Libro;
import modelo.Prestamo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamosDAOImpl implements PrestamosDAO {
    @Override
    public boolean insertarPrestamo(Prestamo prestamo) throws SQLException {
        if (prestamo == null || prestamo.getEstudiante().getIdEstudiante() <= 0
                || prestamo.getLibro().getIdLibro() <= 0) {
            throw new IllegalArgumentException("El préstamo no puede ser nulo y debe tener un estudiante y un libro registrados.");
        }
        String sql = "INSERT INTO prestamos (id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, devuelto) " +
                "VALUES (?, ?, ?, ?, ?)";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, prestamo.getEstudiante().getIdEstudiante());
            ps.setInt(2, prestamo.getLibro().getIdLibro());
            ps.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            ps.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
            ps.setBoolean(5, prestamo.isDevuelto());

            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas == 0) {
                return false;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    prestamo.setIdPrestamo(rs.getInt(1));
                    return true;
                }
            }
            throw new SQLException("El préstamo se insertó, pero no se pudo recuperar su ID.");
        }
    }

    @Override
    public boolean actualizarPrestamo(Prestamo prestamo) throws SQLException {
        if (prestamo == null || prestamo.getIdPrestamo() <= 0 || prestamo.getEstudiante() == null
                || prestamo.getLibro() == null || prestamo.getEstudiante().getIdEstudiante() <= 0
                || prestamo.getLibro().getIdLibro() <= 0) {
            throw new IllegalArgumentException("El préstamo o su estudiante o libro no pueden ser nulos o sin ID.");
        }
        String sql = "UPDATE prestamos SET id_estudiante = ?, id_libro = ?, fecha_prestamo = ?, " +
                "fecha_devolucion = ?, devuelto = ? WHERE id = ?";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setInt(1, prestamo.getEstudiante().getIdEstudiante());
            ps.setInt(2, prestamo.getLibro().getIdLibro());
            ps.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            ps.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
            ps.setBoolean(5, prestamo.isDevuelto());
            ps.setInt(6, prestamo.getIdPrestamo());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminarPrestamo(int idPrestamo) throws SQLException {
        if (idPrestamo <= 0) {
            throw new IllegalArgumentException("No es posible eliminar un préstamo sin ID.");
        }
        String sql = "DELETE FROM prestamos WHERE id = ?";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setInt(1, idPrestamo);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Prestamo buscarPrestamoPorId(int idPrestamo) throws SQLException {
        String sql = "SELECT id, id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, devuelto " +
                "FROM prestamos WHERE id = ?";
        Connection db = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setInt(1, idPrestamo);
            EstudianteDAO estudianteDAO = new EstudianteDAOImpl();
            LibroDAO libroDAO = new LibroDAOImpl();
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Estudiante estudiante = estudianteDAO.buscarEstudiantePorId(rs.getInt("id_estudiante"));
                    Libro libro = libroDAO.buscarLibroPorId(rs.getInt("id_libro"));
                    if (estudiante == null || libro == null) {
                        throw new SQLException("No se encontró el estudiante y/o el libro asociado al préstamo con ID " + rs.getInt("id"));
                    }
                    return new Prestamo(rs.getInt("id"), estudiante, libro,
                            rs.getDate("fecha_prestamo").toLocalDate(),
                            rs.getDate("fecha_devolucion").toLocalDate(),
                            rs.getBoolean("devuelto"));
                }
            }
        }
        return null;
    }

    @Override
    public List<Prestamo> listarPrestamos() throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT id, id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, " +
                "devuelto FROM prestamos ORDER BY id";
       Connection db = DatabaseConnection.getInstance().getConnection();
       try (PreparedStatement ps = db.prepareStatement(sql);
       ResultSet rs = ps.executeQuery()) {
           EstudianteDAO estudianteDAO = new EstudianteDAOImpl();
           LibroDAO libroDAO = new LibroDAOImpl();
           while (rs.next()) {
               Estudiante estudiante = estudianteDAO.buscarEstudiantePorId(rs.getInt("id_estudiante"));
               Libro libro = libroDAO.buscarLibroPorId(rs.getInt("id_libro"));
               if (estudiante == null || libro == null) {
                   throw new SQLException("No se encontró el estudiante y/o el libro asociado al préstamo con ID " + rs.getInt("id"));
               }
               Prestamo prestamo = new Prestamo(rs.getInt("id"), estudiante, libro,
                       rs.getDate("fecha_prestamo").toLocalDate(),
                       rs.getDate("fecha_devolucion").toLocalDate(),
                       rs.getBoolean("devuelto"));
               prestamos.add(prestamo);
           }
       }
       return prestamos;
    }
}
