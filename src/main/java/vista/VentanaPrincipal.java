package vista;

import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
   private Usuario usuario;
   private JLabel lblUsuario;
   private JButton btnGestionarLibros;
   private JButton btnGestionarEstudiantes;
   private JButton btnCerrarSesion;

    public VentanaPrincipal(Usuario usuario) {
        this.usuario = usuario;
        estructuraBase();
        panelVentana();
        setLocationRelativeTo(null);
    }
    private void estructuraBase() {
        setTitle("Sistema de Gestión de la Biblioteca Universal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocation(500, 300);
        getContentPane().setBackground(new Color(241, 245, 244));
    }

    private void panelVentana() {
        setLayout(new BorderLayout(0, 10));
        lblUsuario = new JLabel("Bienvenido/a, " + usuario.getNombre()
                + " | Tipo de usuario: " + usuario.getRol(), SwingConstants.CENTER);
        add(lblUsuario, BorderLayout.NORTH);
        add(panelBotones(), BorderLayout.SOUTH);
    }

    public JPanel panelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnGestionarLibros = new JButton("Gestionar Libros");
        btnGestionarEstudiantes = new JButton("Gestionar Estudiantes");
        btnCerrarSesion = new JButton("Cerrar Sesión");


        btnGestionarLibros.addActionListener(e -> abrirGestionLibros());
        btnGestionarEstudiantes.addActionListener(e -> abrirGestionEstudiantes());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        panelBotones.add(btnGestionarLibros);
        btnGestionarLibros.setVisible("bibliotecario".equals(usuario.getRol()));
        panelBotones.add(btnGestionarEstudiantes);
        btnGestionarEstudiantes.setVisible("bibliotecario".equals(usuario.getRol()));
        panelBotones.add(btnCerrarSesion);
        return panelBotones;
    }

    private void abrirGestionEstudiantes() {
        VentanaGestionEstudiantes ventanaEstudiantes = new VentanaGestionEstudiantes();
        ventanaEstudiantes.setVisible(true);
    }

    private void abrirGestionLibros() {
        VentanaGestionLibros ventanaLibros = new VentanaGestionLibros();
        ventanaLibros.setVisible(true);
    }

    private void cerrarSesion() {
        VentanaLogin ventanaLogin = new VentanaLogin();
        ventanaLogin.setVisible(true);
        dispose();
    }
}
