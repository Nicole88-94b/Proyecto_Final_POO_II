package dao.interfaces;

import modelo.Prestamo;

import java.sql.SQLException;
import java.util.List;

public interface PrestamosDAO {
    boolean insertarPrestamo(Prestamo prestamo) throws SQLException;
    boolean actualizarPrestamo(Prestamo prestamo) throws SQLException;
    boolean eliminarPrestamo(int idPrestamo) throws SQLException;
    Prestamo buscarPrestamoPorId(int idPrestamo) throws SQLException;
    List<Prestamo> listarPrestamos() throws SQLException;
}
