package main;

import vista.VentanaLogin;

import javax.swing.*;

/**
 * Punto de inicio de la aplicación de biblioteca escolar.
 */
public class Main {

    /**
     * Inicia la interfaz gráfica en el hilo de eventos de Swing.
     *
     * @param args argumentos de ejecución; no se utilizan en esta aplicación
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            VentanaLogin ventanaLogin = new VentanaLogin();
            ventanaLogin.setVisible(true);
        });

    }
}
