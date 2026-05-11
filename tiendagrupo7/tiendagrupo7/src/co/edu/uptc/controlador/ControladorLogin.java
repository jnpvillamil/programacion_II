package co.edu.uptc.controlador;

import co.edu.uptc.gui.VentanaLogin;
import co.edu.uptc.gui.VentanaPrincipal;
import co.edu.uptc.negocio.GestionUsuarios;
import co.edu.uptc.utilidades.ValidadorEntradas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorLogin {

    private VentanaLogin vistaLogin;
    private VentanaPrincipal vistaPrincipal;
    private GestionUsuarios gestionUsuarios;

    public ControladorLogin(VentanaLogin vistaLogin, VentanaPrincipal vistaPrincipal, GestionUsuarios gestionUsuarios) {
        this.vistaLogin = vistaLogin;
        this.vistaPrincipal = vistaPrincipal;
        this.gestionUsuarios = gestionUsuarios;
        inicializarEventos();
    }

    private void inicializarEventos() {
        vistaLogin.getBtnIngresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresar();
            }
        });
    }

    private void ingresar() {
        String usuario = vistaLogin.getUsuario();
        String clave = vistaLogin.getClave();

        if (ValidadorEntradas.esVacio(usuario) || ValidadorEntradas.esVacio(clave)) {
            JOptionPane.showMessageDialog(vistaLogin, "Por favor, ingrese usuario y contraseña.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            boolean autenticado = gestionUsuarios.autenticar(usuario, clave);
            if (autenticado) {
                vistaLogin.setVisible(false);
                vistaPrincipal.setVisible(true);
                JOptionPane.showMessageDialog(vistaPrincipal, "Bienvenido al sistema: " + gestionUsuarios.getUsuarioAutenticado().getNombre(), "Acceso Concedido", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vistaLogin, "Credenciales incorrectas. Intente nuevamente.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaLogin, "Ocurrió un error al intentar iniciar sesión: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }
}