package vista;

import controlador.ControladorDeUsuarios;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * Ventana de acceso al sistema. Solicita las credenciales, autentica al usuario
 * y abre la ventana principal con las opciones correspondientes a su rol.
 */
public class VentanaLogin extends JFrame {
    private ControladorDeUsuarios controladorDeUsuarios;
    private JTextField campoRut;
    private JPasswordField campoContrasena;
    private JButton btnIngresar;

    /**
     * Construye y configura la ventana de inicio de sesión.
     */
    public VentanaLogin() {
        controladorDeUsuarios = new ControladorDeUsuarios();
        estructuraBase();
        panelVentana();
        setLocationRelativeTo(null);
    }

    private void estructuraBase() {
        setTitle("Identificación de Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocation(500, 300);
        getContentPane().setBackground(new Color(241, 245, 244));
    }

    private void panelVentana() {
        setLayout(new BorderLayout(0, 10));
        add(campos(), BorderLayout.CENTER);
        add(panelBotones(), BorderLayout.SOUTH);
    }

    private JPanel panelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnIngresar = new JButton("Ingresar");
        btnIngresar.addActionListener(e -> iniciarSesion());
        panelBotones.add(btnIngresar);
        return panelBotones;
    }

    private JPanel campos() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        campoRut = new JTextField(10);
        campoContrasena = new JPasswordField(10);
        distribucionCampos(panel);
        return panel;
    }

    private void distribucionCampos(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(new JLabel("Bienvenido al Sistema de Gestión de la Biblioteca"), c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Rut Usuario:"), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        panel.add(campoRut, c);

        c.gridx = 0;
        c.gridy = 2;
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

    private void iniciarSesion() {
        String rut = campoRut.getText().trim();
        String contrasena = new String(campoContrasena.getPassword());

        if (rut.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Usuario usuario = controladorDeUsuarios.autenticarUsuario(rut, contrasena);
            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "RUT o contraseña incorrectos",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(
                    this, "Bienvenido/a, " + usuario.getNombre()
                            + "\nTipo de Usuario: " + usuario.getRol(), "Acceso correcto", JOptionPane.INFORMATION_MESSAGE);
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(usuario);
            ventanaPrincipal.setVisible(true);
            dispose();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                "Datos inválidos", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "No fue posible consultar los usuarios en la base de datos.",
                "Error de conexión", JOptionPane.ERROR_MESSAGE);
    }
    }

}
