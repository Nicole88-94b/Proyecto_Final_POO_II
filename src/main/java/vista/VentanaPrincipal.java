package vista;

import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Menú principal del sistema. Presenta las funciones disponibles según el rol
 * del usuario autenticado y permite cerrar la sesión actual.
 */
public class VentanaPrincipal extends JFrame {
   private Usuario usuario;
   private JLabel lblUsuario;
   private JButton btnGestionarLibros;
   private JButton btnGestionarEstudiantes;
   private JButton btnGestionarPrestamos;
   private JButton btnCerrarSesion;

    /**
     * Construye el menú principal para el usuario que inició sesión.
     *
     * @param usuario usuario autenticado que determina las opciones visibles
     */
    public VentanaPrincipal(Usuario usuario) {
        this.usuario = usuario;
        estructuraBase();
        panelVentana();
        setLocationRelativeTo(null);
    }
    private void estructuraBase() {
        setTitle("Sistema de Gestión de la Biblioteca Escolar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 520);
        setLocation(500, 300);
        getContentPane().setBackground(new Color(241, 245, 244));
    }

    private void panelVentana() {
        setLayout(new BorderLayout(0, 10));
        lblUsuario = new JLabel("Bienvenido/a, " + usuario.getNombre()
                + " | Tipo de usuario: " + usuario.getRol(), SwingConstants.CENTER);
        add(lblUsuario, BorderLayout.NORTH);
        add(crearImagenPrincipal(), BorderLayout.CENTER);
        add(panelBotones(), BorderLayout.SOUTH);
    }

    private JLabel crearImagenPrincipal() {
        URL recursoImagen = getClass().getResource("/images/biblioteca_principal.png");
        if (recursoImagen == null) {
            return new JLabel();
        }

        ImageIcon iconoOriginal = new ImageIcon(recursoImagen);
        Image imagenEscalada = iconoOriginal.getImage()
                .getScaledInstance(360, 360, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(imagenEscalada), SwingConstants.CENTER);
    }

    public JPanel panelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnGestionarLibros = new JButton("Gestionar Libros");

        if (!"bibliotecario".equals(usuario.getRol())) {
            btnGestionarLibros.setText("Consultar Libros");
        }
        btnGestionarEstudiantes = new JButton("Gestionar Estudiantes");
        btnGestionarPrestamos = new JButton("Gestionar Prestamos");
        btnCerrarSesion = new JButton("Cerrar Sesión");


        btnGestionarLibros.addActionListener(e -> abrirGestionLibros());
        btnGestionarEstudiantes.addActionListener(e -> abrirGestionEstudiantes());
        btnGestionarPrestamos.addActionListener(e -> abrirGestionPrestamos());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        panelBotones.add(btnGestionarLibros);
        panelBotones.add(btnGestionarEstudiantes);
        btnGestionarEstudiantes.setVisible("bibliotecario".equals(usuario.getRol()));
        panelBotones.add(btnGestionarPrestamos);
        panelBotones.add(btnCerrarSesion);
        return panelBotones;
    }

    private void abrirGestionPrestamos() {
        VentanaGestionDePrestamos ventanaPrestamos = new VentanaGestionDePrestamos(usuario);
        ventanaPrestamos.setVisible(true);
    }

    private void abrirGestionEstudiantes() {
        VentanaGestionEstudiantes ventanaEstudiantes = new VentanaGestionEstudiantes();
        ventanaEstudiantes.setVisible(true);
    }

    private void abrirGestionLibros() {
        boolean soloLectura = !"bibliotecario".equals(usuario.getRol());
        VentanaGestionLibros ventanaLibros = new VentanaGestionLibros(soloLectura);
        ventanaLibros.setVisible(true);
    }

    private void cerrarSesion() {
        VentanaLogin ventanaLogin = new VentanaLogin();
        ventanaLogin.setVisible(true);
        dispose();
    }
}
