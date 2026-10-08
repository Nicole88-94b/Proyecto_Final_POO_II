package vista;

import controlador.ControladorDeEstudiantes;
import modelo.Categoria;
import modelo.Estudiante;
import modelo.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

/**
 * Ventana de administración de estudiantes y sus cuentas de acceso.
 * Permite registrar, consultar, modificar y eliminar registros respetando las
 * relaciones existentes en la base de datos.
 */
public class VentanaGestionEstudiantes extends JFrame {
    private ControladorDeEstudiantes controladorDeEstudiantes;
    private JTextField campoNombre;
    private JTextField campoRut;
    private JTextField campoCorreo;
    private JTextField campoCurso;
    private JPasswordField campoContrasena;
    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTablaEstudiantes;
    private JButton btnRegistrar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    /**
     * Construye la ventana y carga los estudiantes registrados.
     */
    public VentanaGestionEstudiantes() {
        controladorDeEstudiantes = new ControladorDeEstudiantes();
        estructuraBase();
        panelVentana();
        setLocationRelativeTo(null);
    }
    private void estructuraBase() {
        setTitle("Gestión de Estudiantes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocation(500, 300);
        getContentPane().setBackground(new Color(241, 245, 244));
    }

    private void panelVentana() {
        setLayout(new BorderLayout(0, 10));
        add(campos(), BorderLayout.NORTH);
        listadoDeEstudiantes();
        add(panelBotones(), BorderLayout.SOUTH);

    }

    private JPanel campos() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        campoNombre = new JTextField(10);
        campoRut = new JTextField(10);
        campoCorreo = new JTextField(10);
        campoCurso = new JTextField(10);
        campoContrasena = new JPasswordField(10);
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
        panel.add(new JLabel("Registre al estudiante"), c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Nombre:"), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        panel.add(campoNombre, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("RUT:"), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        panel.add(campoRut, c);


        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Correo:"), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        panel.add(campoCorreo, c);

        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Curso:"), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        panel.add(campoCurso, c);

        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Contraseña:"), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        panel.add(campoContrasena, c);

    }

    private void listadoDeEstudiantes() {
        String [] columnas = {"ID", "Nombre", "RUT", "Correo", "Curso"};
        modeloTablaEstudiantes = new DefaultTableModel(columnas, 0);
        tablaEstudiantes = new JTable(modeloTablaEstudiantes);
        tablaEstudiantes.setDefaultEditor(Object.class, null);

        tablaEstudiantes.getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting()) {
                cargarEstudianteSeleccionado();
            }
        });
        add(new JScrollPane(tablaEstudiantes), BorderLayout.CENTER);
        tablaEstudiantes.getColumnModel().getColumn(0).setMinWidth(0);
        tablaEstudiantes.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaEstudiantes.getColumnModel().getColumn(0).setPreferredWidth(0);
        cargarEstudiantes();
    }

    public JPanel panelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRegistrar = new JButton("Registrar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        btnRegistrar.addActionListener(e -> registrarEstudiante());
        btnModificar.addActionListener(e -> modificarEstudiante());
        btnEliminar.addActionListener(e -> eliminarEstudiante());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        return panelBotones;
    }

    private void cargarEstudianteSeleccionado() {
        int fila = tablaEstudiantes.getSelectedRow();

        if (fila < 0) {
            return;
        }
        campoNombre.setText(modeloTablaEstudiantes.getValueAt(fila, 1).toString());
        campoRut.setText(modeloTablaEstudiantes.getValueAt(fila, 2).toString());
        campoCorreo.setText(modeloTablaEstudiantes.getValueAt(fila, 3).toString());
        campoCurso.setText(modeloTablaEstudiantes.getValueAt(fila, 4).toString());
        campoContrasena.setText("");
        campoRut.setEditable(false);
    }

    public void cargarEstudiantes()  {
        modeloTablaEstudiantes.setRowCount(0);
        try {
            for (Estudiante estudiante : controladorDeEstudiantes.listarEstudiantes()) {
                Object[] fila = {
                        estudiante.getIdEstudiante(),
                        estudiante.getNombre(),
                        estudiante.getRut(),
                        estudiante.getCorreo(),
                        estudiante.getCurso()
                };
                modeloTablaEstudiantes.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No fue posible cargar los estudiantes: " + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void registrarEstudiante() {
        String nombre = campoNombre.getText().trim();
        String rut = campoRut.getText().trim();
        String correo = campoCorreo.getText().trim();
        String curso = campoCurso.getText().trim();
        String contrasena = new String(campoContrasena.getPassword());


        if (nombre.isEmpty() || rut.isEmpty() || correo.isEmpty() || curso.isEmpty()
        || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idEstudiante = 0;

        try {

            Estudiante estudianteRegistrado = new Estudiante(nombre, rut, correo, idEstudiante, curso);
            boolean registrado = controladorDeEstudiantes.registrarEstudianteConCuenta(estudianteRegistrado, contrasena);
            if(!registrado) {
                JOptionPane.showMessageDialog(this, "Hubo un error al registrar el estudiante.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Registro exitoso",
                        "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                cargarEstudiantes();
            }
            limpiarCampos();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Datos inválidos", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No fue posible guardar al estudiante en la base de datos. "
                    + "Verifique que el RUT no esté registrado.", "Error de base de datos", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void limpiarCampos() {
        campoNombre.setText("");
        campoRut.setText("");
        campoCorreo.setText("");
        campoCurso.setText("");
        campoContrasena.setText("");
        tablaEstudiantes.clearSelection();
        campoRut.setEditable(true);
    }

    private void modificarEstudiante() {
        int fila = tablaEstudiantes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fila para modificar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idEstudiante = (int) modeloTablaEstudiantes.getValueAt(fila, 0);
        String nombre = campoNombre.getText().trim();
        String rut = campoRut.getText().trim();
        String correo = campoCorreo.getText().trim();
        String curso = campoCurso.getText().trim();
        String nuevaContrasena = new String(campoContrasena.getPassword());

        if (nombre.isEmpty() || rut.isEmpty() || correo.isEmpty() || curso.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Estudiante estudianteModificado = new Estudiante(nombre, rut, correo, idEstudiante, curso);
            int opcion = JOptionPane.showConfirmDialog(this, "¿Desea guardar los cambios realizados en este estudiante?", "Confirmar modificación",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opcion != JOptionPane.YES_OPTION) {
                return;
            }
            boolean modificado = controladorDeEstudiantes.actualizarEstudianteConCuenta(estudianteModificado, nuevaContrasena);
            if (modificado) {
                JOptionPane.showMessageDialog(this, "Estudiante modificado exitosamente");
                cargarEstudiantes();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo modificar al estudiante.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            limpiarCampos();
            tablaEstudiantes.clearSelection();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Datos inválidos", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No fue posible guardar al estudiante en la base de datos. "
                    + "Verifique que el RUT no esté registrado.", "Error de base de datos", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void eliminarEstudiante() {
        int fila = tablaEstudiantes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fila para eliminar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idEstudiante = (int) modeloTablaEstudiantes.getValueAt(fila, 0);
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea borrar al estudiante seleccionado?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            if (controladorDeEstudiantes.eliminarEstudianteConCuenta(idEstudiante)) {
                JOptionPane.showMessageDialog(this, "Estudiante borrado exitosamente");
                cargarEstudiantes();
                limpiarCampos();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No fue posible borrar al estudiante. Puede tener préstamos asociados.",
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }

    }
}
