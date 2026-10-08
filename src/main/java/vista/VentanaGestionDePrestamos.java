package vista;

import controlador.ControladorDeEstudiantes;
import controlador.ControladorDeLibros;
import controlador.ControladorDePrestamos;

import modelo.Estudiante;
import modelo.Prestamo;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class VentanaGestionDePrestamos extends JFrame {
    private Usuario usuarioActual;
    private ControladorDePrestamos controladorDePrestamos;
    private ControladorDeEstudiantes controladorDeEstudiantes;
    private ControladorDeLibros controladorDeLibros;
    private JTextField campoRutEstudiante;
    private JTextField campoIsbnLibro;
    private JTable tablaPrestamos;
    private DefaultTableModel modeloTablaPrestamos;
    private JButton btnRegistrarPrestamo;
    private JButton btnDevolverPrestamo;
    private JButton btnMostrarTodos;
    private JButton btnMostrarActivos;
    private JButton btnVerHistorial;


    public VentanaGestionDePrestamos(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        controladorDeEstudiantes = new ControladorDeEstudiantes();
        controladorDePrestamos = new ControladorDePrestamos();
        controladorDeLibros = new ControladorDeLibros();
        estructuraBase();
        panelVentana();
        configurarUsuario();
        setLocationRelativeTo(null);
    }

    private void estructuraBase() {
        setTitle("Gestión de Préstamos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocation(500, 300);
        getContentPane().setBackground(new Color(241, 245, 244));
    }

    private void panelVentana() {
        setLayout(new BorderLayout(0, 10));
        add(campos(), BorderLayout.NORTH);
        listadoDePrestamos();
        add(panelBotones(), BorderLayout.SOUTH);
    }

    private JPanel campos() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        campoRutEstudiante = new JTextField(10);
        campoIsbnLibro = new JTextField(10);
        distribucionCamposTextos(panel);
        return panel;
    }

    private void distribucionCamposTextos(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(new JLabel("Registre los datos del préstamo"), c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("RUT Estudiante:"), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        panel.add(campoRutEstudiante, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("ISBN libro:"), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        panel.add(campoIsbnLibro, c);
    }

    public JPanel panelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRegistrarPrestamo = new JButton("Registrar");
        btnDevolverPrestamo = new JButton("Devolver");
        btnMostrarTodos = new JButton("Mostrar Todos");
        btnMostrarActivos = new JButton("Mostrar Préstamos Activos");
        btnVerHistorial = new JButton("Ver Historial");

        btnRegistrarPrestamo.addActionListener(e -> registrarPrestamo());
        btnDevolverPrestamo.addActionListener(e -> devolverPrestamo());
        btnMostrarTodos.addActionListener(e -> cargarPrestamos());
        btnMostrarActivos.addActionListener(e -> cargarPrestamosActivos());
        btnVerHistorial.addActionListener(e -> cargarHistorial());

        panelBotones.add(btnRegistrarPrestamo);
        panelBotones.add(btnDevolverPrestamo);
        panelBotones.add(btnMostrarActivos);
        panelBotones.add(btnMostrarTodos);
        panelBotones.add(btnVerHistorial);
        return panelBotones;
    }

    private void cargarHistorial() {
        String rut = campoRutEstudiante.getText().trim();
        if (rut.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el RUT del estudiante.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Estudiante estudianteEncontrado = controladorDeEstudiantes.buscarEstudiantePorRut(rut);
            if (estudianteEncontrado == null) {
                JOptionPane.showMessageDialog(this, "No existe un estudiante con ese RUT",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            List <Prestamo> historial = controladorDePrestamos.listarHistorialPorEstudiante(estudianteEncontrado.getIdEstudiante());
            modeloTablaPrestamos.setRowCount(0);
            for (Prestamo prestamo : historial) {
                Object[] fila = {prestamo.getIdPrestamo(),
                        prestamo.getEstudiante().getNombre(),
                        prestamo.getEstudiante().getRut(),
                        prestamo.getLibro().getTitulo(),
                        prestamo.getFechaPrestamo(),
                        prestamo.getFechaDevolucion(),
                        controladorDePrestamos.obtenerEstado(prestamo)
                };
                modeloTablaPrestamos.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No fue posible cargar el historial",
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void cargarPrestamosActivos() {
        modeloTablaPrestamos.setRowCount(0);
        try {
            for (Prestamo prestamo : obtenerPrestamosVisibles()) {
                if (!prestamo.isDevuelto()) {
                    Object[] fila = {prestamo.getIdPrestamo(),
                            prestamo.getEstudiante().getNombre(),
                            prestamo.getEstudiante().getRut(),
                            prestamo.getLibro().getTitulo(),
                            prestamo.getFechaPrestamo(),
                            prestamo.getFechaDevolucion(),
                            controladorDePrestamos.obtenerEstado(prestamo)
                };
                    modeloTablaPrestamos.addRow(fila);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No fue posible cargar los préstamos: " + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
        catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Cuenta sin estudiante", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void devolverPrestamo() {
        int fila = tablaPrestamos.getSelectedRow();

        if (fila <0) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un préstamo para devolver.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idPrestamo = (int) modeloTablaPrestamos.getValueAt(fila, 0);
        String estado = modeloTablaPrestamos.getValueAt(fila, 6).toString();

        if ("DEVUELTO".equals(estado)) {
            JOptionPane.showMessageDialog(this, "El préstamo seleccionado ya fue devuelto.",
                    "Devolución no permitida", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea registrar la devolución seleccionada?",
                "Confirmar devolución", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            boolean devuelto = controladorDePrestamos.devolverPrestamo(idPrestamo);
            if (devuelto) {
                JOptionPane.showMessageDialog(this, "Devolución registrada correctamente.",
                        "Devolución exitosa", JOptionPane.INFORMATION_MESSAGE);
            }
            cargarPrestamos();
        } catch (IllegalArgumentException | IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Devolución rechazada", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No fue posible registrar la devolución.",
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarPrestamo() {
        String rutEstudiante = campoRutEstudiante.getText().trim();
        String isbnLibro = campoIsbnLibro.getText().trim();
        if (rutEstudiante.isEmpty() || isbnLibro.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Prestamo prestamo = controladorDePrestamos.registrarPrestamo(rutEstudiante, isbnLibro);
            if (prestamo == null) {
                JOptionPane.showMessageDialog(this, "No fue posible registrar el préstamo.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Préstamo registrado correctamente."
                            + "\nLibro: " + prestamo.getLibro().getTitulo()
                            + "\nFecha límite: " + prestamo.getFechaDevolucion(),
                    "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            campoIsbnLibro.setText("");
            if ("bibliotecario".equals(usuarioActual.getRol())) {
                campoRutEstudiante.setText("");
            }
            cargarPrestamos();
        } catch (IllegalArgumentException | IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Préstamo rechazado", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {JOptionPane.showMessageDialog(this, "No fue posible guardar el préstamo en la base de datos.",
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listadoDePrestamos() {
        String[] columnas = {"ID", "Estudiante", "RUT", "Libro",
                "Fecha préstamo", "Fecha límite", "Estado"};
        modeloTablaPrestamos = new DefaultTableModel(columnas, 0);
        tablaPrestamos = new JTable(modeloTablaPrestamos);
        tablaPrestamos.setDefaultEditor(Object.class, null);

        add(new JScrollPane(tablaPrestamos), BorderLayout.CENTER);
        tablaPrestamos.getColumnModel().getColumn(0).setMinWidth(0);
        tablaPrestamos.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaPrestamos.getColumnModel().getColumn(0).setPreferredWidth(0);
        cargarPrestamos();
    }

    private void cargarPrestamos() {
        modeloTablaPrestamos.setRowCount(0);
        try {
            for (Prestamo prestamos : obtenerPrestamosVisibles()) {
                Object[] fila = { prestamos.getIdPrestamo(),
                        prestamos.getEstudiante().getNombre(),
                        prestamos.getEstudiante().getRut(),
                        prestamos.getLibro().getTitulo(),
                        prestamos.getFechaPrestamo(),
                        prestamos.getFechaDevolucion(),
                        controladorDePrestamos.obtenerEstado(prestamos)
                };
                modeloTablaPrestamos.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No fue posible cargar los préstamos: " + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
        catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Cuenta sin estudiante", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void configurarUsuario() {
            boolean esBibliotecario = "bibliotecario".equals(usuarioActual.getRol());
            btnMostrarTodos.setVisible(esBibliotecario);

            if (!esBibliotecario) {
                campoRutEstudiante.setText(usuarioActual.getRut());
                campoRutEstudiante.setEditable(false);
            }
    }

    private List<Prestamo> obtenerPrestamosVisibles() throws SQLException {
        campoIsbnLibro.setText("");
        if ("bibliotecario".equals(usuarioActual.getRol())) {
            return controladorDePrestamos.listarPrestamos();
        }
        Estudiante estudiante = controladorDeEstudiantes.buscarEstudiantePorRut(usuarioActual.getRut());
        if (estudiante == null) {
            throw new IllegalStateException("La cuenta no tiene un estudiante asociado.");
        }
        return controladorDePrestamos.listarHistorialPorEstudiante(estudiante.getIdEstudiante());
    }

}


