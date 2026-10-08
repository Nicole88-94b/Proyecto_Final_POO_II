package vista;

import controlador.ControladorDeEstudiantes;
import controlador.ControladorDeLibros;
import controlador.ControladorDePrestamos;

import modelo.Prestamo;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

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


        panelBotones.add(btnRegistrarPrestamo);
        panelBotones.add(btnDevolverPrestamo);
        panelBotones.add(btnMostrarActivos);
        panelBotones.add(btnMostrarTodos);
        panelBotones.add(btnVerHistorial);
        return panelBotones;
    }

    private void listadoDePrestamos() {
        String[] columnas = {"ID", "Estudiante", "Libro", "Fecha préstamo", "Fecha límite", "Estado"};
        modeloTablaPrestamos = new DefaultTableModel(columnas, 0);
        tablaPrestamos = new JTable(modeloTablaPrestamos);
        tablaPrestamos.setDefaultEditor(Object.class, null);

        tablaPrestamos.getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting()) {
               // cargarPrestamoSeleccionado();
            }
        });
        add(new JScrollPane(tablaPrestamos), BorderLayout.CENTER);
        tablaPrestamos.getColumnModel().getColumn(0).setMinWidth(0);
        tablaPrestamos.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaPrestamos.getColumnModel().getColumn(0).setPreferredWidth(0);
        cargarPrestamos();
    }

    private void cargarPrestamos() {
        modeloTablaPrestamos.setRowCount(0);
        try {
            for (Prestamo prestamos : controladorDePrestamos.listarPrestamos()) {
                Object[] fila = { prestamos.getIdPrestamo(),
                        prestamos.getEstudiante().getNombre(),
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

    }


}


