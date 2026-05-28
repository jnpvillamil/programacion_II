package co.edu.uptc.controlador;

import co.edu.uptc.dto.UsuarioDTO;
import co.edu.uptc.gui.VentanaLogin;
import co.edu.uptc.gui.VentanaPrincipal;
import co.edu.uptc.negocio.GestionUsuarios;
import co.edu.uptc.utilidades.LogSistema;

import javax.swing.*;

public class ControladorCerrarSesion {

    private final VentanaPrincipal ventanaPrincipal;
    private final VentanaLogin ventanaLogin;
    private final GestionUsuarios gestionUsuarios;

    public ControladorCerrarSesion(VentanaPrincipal ventanaPrincipal,
                                   VentanaLogin ventanaLogin,
                                   GestionUsuarios gestionUsuarios) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.ventanaLogin = ventanaLogin;
        this.gestionUsuarios = gestionUsuarios;
        inicializarEventos();
    }

    private void inicializarEventos() {
        ventanaPrincipal.getBtnCerrarSesion().addActionListener(e -> confirmarCierreSesion());
    }

    private void confirmarCierreSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(
                ventanaPrincipal,
                "¿Quiere cerrar la sesión actual?",
                "Confirmar cierre de sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            UsuarioDTO sesion = gestionUsuarios.obtenerSesionActiva();
            String usuario = sesion != null ? sesion.getUsuario() : "desconocido";
            LogSistema.sesionCerrada(usuario);
            gestionUsuarios.cerrarSesion();
            ventanaPrincipal.setVisible(false);
            ventanaLogin.limpiarCampos();
            ventanaLogin.setVisible(true);
        }
    }
}
