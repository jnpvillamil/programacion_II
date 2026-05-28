package co.edu.uptc.controlador;

import co.edu.uptc.dto.LoginDTO;
import co.edu.uptc.dto.UsuarioDTO;
import co.edu.uptc.gui.VentanaLogin;
import co.edu.uptc.gui.VentanaPrincipal;
import co.edu.uptc.negocio.GestionUsuarios;
import co.edu.uptc.utilidades.LogSistema;
import co.edu.uptc.utilidades.ValidadorEntradas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ControladorLogin {

    private VentanaLogin vistaLogin;
    private VentanaPrincipal vistaPrincipal;
    private GestionUsuarios gestionUsuarios;
    private ControladorPrincipal controladorPrincipal;

   
    public ControladorLogin(VentanaLogin vistaLogin, VentanaPrincipal vistaPrincipal,
                            GestionUsuarios gestionUsuarios, ControladorPrincipal controladorPrincipal) {
        this.vistaLogin = vistaLogin;
        this.vistaPrincipal = vistaPrincipal;
        this.gestionUsuarios = gestionUsuarios;
        this.controladorPrincipal = controladorPrincipal;
        inicializarEventos();
    }

    public ControladorLogin(VentanaLogin vistaLogin, VentanaPrincipal vistaPrincipal, GestionUsuarios gestionUsuarios) {
        this(vistaLogin, vistaPrincipal, gestionUsuarios, null);
    }

 
    public ControladorLogin(VentanaLogin vistaLogin, VentanaPrincipal vistaPrincipal) {
        this(vistaLogin, vistaPrincipal, new GestionUsuarios());
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
            JOptionPane.showMessageDialog(vistaLogin, "Por favor, ingrese usuario y contraseña.", "Error de Credenciales", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            LoginDTO credenciales = new LoginDTO(usuario, clave);
            boolean autenticado = gestionUsuarios.autenticar(credenciales);
            if (autenticado) {
                vistaLogin.setVisible(false);
                vistaPrincipal.setVisible(true);
                if (controladorPrincipal != null) {
                    controladorPrincipal.aplicarPermisosPorRol();
                }
                UsuarioDTO sesion = gestionUsuarios.obtenerSesionActiva();
                JOptionPane.showMessageDialog(vistaPrincipal,
                        "Bienvenido: " + sesion.getUsuario() + " (" + sesion.getRol() + ")",
                        "Acceso Concedido",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vistaLogin, "Credenciales incorrectas. Intente de nuevo.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            LogSistema.error("ControladorLogin", ex.getMessage());
            JOptionPane.showMessageDialog(vistaLogin, "Ocurrió un error al intentar iniciar sesión: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }
}