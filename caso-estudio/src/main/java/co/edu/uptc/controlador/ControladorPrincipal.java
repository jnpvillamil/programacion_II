package co.edu.uptc.controlador;

import co.edu.uptc.dto.UsuarioDTO;
import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.gui.VentanaPrincipal;
import co.edu.uptc.interfaces.Autorizable;
import co.edu.uptc.negocio.GestionUsuarios;

import javax.swing.*;

public class ControladorPrincipal {

    private final VentanaPrincipal vistaPrincipal;
    private final GestionUsuarios gestionUsuarios;
    private final Autorizable autorizacion;

    public ControladorPrincipal(VentanaPrincipal vistaPrincipal,
                                GestionUsuarios gestionUsuarios,
                                Autorizable autorizacion) {
        this.vistaPrincipal = vistaPrincipal;
        this.gestionUsuarios = gestionUsuarios;
        this.autorizacion = autorizacion;
        inicializarNavegacion();
    }

    public ControladorPrincipal(VentanaPrincipal vistaPrincipal) {
        this(vistaPrincipal, new GestionUsuarios(), new co.edu.uptc.negocio.ServicioAutorizacion());
    }

    private void inicializarNavegacion() {
        vistaPrincipal.getBtnInventario().addActionListener(e -> navegar("INVENTARIO", "INVENTARIO"));
        vistaPrincipal.getBtnClientes().addActionListener(e -> navegar("CLIENTES", "CLIENTES"));
        vistaPrincipal.getBtnVentas().addActionListener(e -> navegar("VENTAS", "VENTAS"));
        vistaPrincipal.getBtnCompras().addActionListener(e -> navegar("COMPRAS", "COMPRAS"));
        vistaPrincipal.getBtnProveedores().addActionListener(e -> navegar("PROVEEDORES", "PROVEEDORES"));
        vistaPrincipal.getBtnReportes().addActionListener(e -> navegar("REPORTES", "REPORTES"));
        vistaPrincipal.getBtnConsultas().addActionListener(e -> navegar("CONSULTAS", "CONSULTAS"));
    }

    private void navegar(String modulo, String panel) {
        RolUsuario rol = obtenerRolActual();
        if (!autorizacion.verificarPermiso(rol, modulo)) {
            JOptionPane.showMessageDialog(vistaPrincipal,
                    "No tiene permisos para acceder a este módulo.",
                    "Acceso denegado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        vistaPrincipal.mostrarPanel(panel);
    }

    public void aplicarPermisosPorRol() {
        RolUsuario rol = obtenerRolActual();
        vistaPrincipal.getBtnInventario().setEnabled(autorizacion.verificarPermiso(rol, "INVENTARIO"));
        vistaPrincipal.getBtnClientes().setEnabled(autorizacion.verificarPermiso(rol, "CLIENTES"));
        vistaPrincipal.getBtnVentas().setEnabled(autorizacion.verificarPermiso(rol, "VENTAS"));
        vistaPrincipal.getBtnCompras().setEnabled(autorizacion.verificarPermiso(rol, "COMPRAS"));
        vistaPrincipal.getBtnProveedores().setEnabled(autorizacion.verificarPermiso(rol, "PROVEEDORES"));
        vistaPrincipal.getBtnReportes().setEnabled(autorizacion.verificarPermiso(rol, "REPORTES"));
        vistaPrincipal.getBtnConsultas().setEnabled(autorizacion.verificarPermiso(rol, "CONSULTAS"));
        vistaPrincipal.getBtnCerrarSesion().setEnabled(autorizacion.verificarPermiso(rol, "CERRAR_SESION"));
    }

    private RolUsuario obtenerRolActual() {
        UsuarioDTO sesion = gestionUsuarios.obtenerSesionActiva();
        if (sesion == null) {
            return RolUsuario.CAJERO;
        }
        try {
            return RolUsuario.valueOf(sesion.getRol());
        } catch (IllegalArgumentException ex) {
            return RolUsuario.ADMINISTRADOR;
        }
    }
}
