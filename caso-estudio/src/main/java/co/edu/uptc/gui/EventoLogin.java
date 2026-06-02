package co.edu.uptc.gui;

import co.edu.uptc.dto.LoginDTO;
import co.edu.uptc.dto.ResultadoOperacion;
import co.edu.uptc.dto.UsuarioDTO;
import co.edu.uptc.enums.ModuloSistema;
import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.negocio.GestionUsuarios;
import co.edu.uptc.negocio.ServicioAutorizacion;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventoLogin implements ActionListener {

    public static final String CMD_INGRESAR = "CMD_INGRESAR";

    private final VentanaPrincipal ventanaPrincipal;
    private final VentanaLogin ventanaLogin;
    private final GestionUsuarios gestionUsuarios;
    private final ServicioAutorizacion servicioAutorizacion;

    public EventoLogin(VentanaPrincipal ventanaPrincipal,
                       VentanaLogin ventanaLogin,
                       GestionUsuarios gestionUsuarios,
                       ServicioAutorizacion servicioAutorizacion) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.ventanaLogin = ventanaLogin;
        this.gestionUsuarios = gestionUsuarios;
        this.servicioAutorizacion = servicioAutorizacion;

        ventanaLogin.getBtnIngresar().setActionCommand(CMD_INGRESAR);
        ventanaLogin.getBtnIngresar().addActionListener(this);
    }

    private void ingresar() {
        LoginDTO credenciales = new LoginDTO(ventanaLogin.getUsuario(), ventanaLogin.getClave());
        ResultadoOperacion resultado = gestionUsuarios.autenticarConValidacion(credenciales);
        if (!resultado.isExito()) {
            JOptionPane.showMessageDialog(ventanaLogin, resultado.getMensaje(), "Acceso denegado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        UsuarioDTO sesion = resultado.getDato();
        JOptionPane.showMessageDialog(ventanaLogin, resultado.getMensaje(), "Bienvenido",
                JOptionPane.INFORMATION_MESSAGE);

        aplicarPermisos(RolUsuario.valueOf(sesion.getRol()));
        ventanaLogin.setVisible(false);
        ventanaPrincipal.setVisible(true);
        ventanaPrincipal.mostrarPanel(ModuloSistema.INVENTARIO.name());
    }

    private void aplicarPermisos(RolUsuario rol) {
        ventanaPrincipal.getBtnInventario().setEnabled(
                servicioAutorizacion.verificarPermiso(rol, ModuloSistema.INVENTARIO.name()));
        ventanaPrincipal.getBtnClientes().setEnabled(
                servicioAutorizacion.verificarPermiso(rol, ModuloSistema.CLIENTES.name()));
        ventanaPrincipal.getBtnVentas().setEnabled(
                servicioAutorizacion.verificarPermiso(rol, ModuloSistema.VENTAS.name()));
        ventanaPrincipal.getBtnCompras().setEnabled(
                servicioAutorizacion.verificarPermiso(rol, ModuloSistema.COMPRAS.name()));
        ventanaPrincipal.getBtnProveedores().setEnabled(
                servicioAutorizacion.verificarPermiso(rol, ModuloSistema.PROVEEDORES.name()));
        ventanaPrincipal.getBtnReportes().setEnabled(
                servicioAutorizacion.verificarPermiso(rol, ModuloSistema.REPORTES.name()));
        ventanaPrincipal.getBtnConsultas().setEnabled(
                servicioAutorizacion.verificarPermiso(rol, ModuloSistema.CONSULTAS.name()));
        ventanaPrincipal.getBtnCerrarSesion().setEnabled(
                servicioAutorizacion.verificarPermiso(rol, ModuloSistema.CERRAR_SESION.name()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (CMD_INGRESAR.equals(comando)) {
            ingresar();
        }
    }
}
