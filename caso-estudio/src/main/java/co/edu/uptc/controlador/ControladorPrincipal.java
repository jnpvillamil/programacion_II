package co.edu.uptc.controlador;

import co.edu.uptc.dto.UsuarioDTO;
import co.edu.uptc.enums.ModuloSistema;
import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.gui.VentanaPrincipal;
import co.edu.uptc.interfaces.Autorizable;
import co.edu.uptc.negocio.GestionUsuarios;
import co.edu.uptc.negocio.ServicioAutorizacion;

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
        this(vistaPrincipal, new GestionUsuarios(), new ServicioAutorizacion());
    }

    private void inicializarNavegacion() {
        vistaPrincipal.getBtnInventario().addActionListener(e -> navegar(ModuloSistema.INVENTARIO));
        vistaPrincipal.getBtnClientes().addActionListener(e -> navegar(ModuloSistema.CLIENTES));
        vistaPrincipal.getBtnVentas().addActionListener(e -> navegar(ModuloSistema.VENTAS));
        vistaPrincipal.getBtnCompras().addActionListener(e -> navegar(ModuloSistema.COMPRAS));
        vistaPrincipal.getBtnProveedores().addActionListener(e -> navegar(ModuloSistema.PROVEEDORES));
        vistaPrincipal.getBtnReportes().addActionListener(e -> navegar(ModuloSistema.REPORTES));
        vistaPrincipal.getBtnConsultas().addActionListener(e -> navegar(ModuloSistema.CONSULTAS));
    }

    private void navegar(ModuloSistema modulo) {
        RolUsuario rol = obtenerRolActual();
        if (!autorizacion.verificarPermiso(rol, modulo.name())) {
            JOptionPane.showMessageDialog(vistaPrincipal,
                    "No tiene permisos para acceder a este módulo.",
                    "Acceso denegado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        vistaPrincipal.mostrarPanel(modulo.name());
    }

    public void aplicarPermisosPorRol() {
        RolUsuario rol = obtenerRolActual();
        vistaPrincipal.getBtnInventario().setEnabled(autorizacion.verificarPermiso(rol, ModuloSistema.INVENTARIO.name()));
        vistaPrincipal.getBtnClientes().setEnabled(autorizacion.verificarPermiso(rol, ModuloSistema.CLIENTES.name()));
        vistaPrincipal.getBtnVentas().setEnabled(autorizacion.verificarPermiso(rol, ModuloSistema.VENTAS.name()));
        vistaPrincipal.getBtnCompras().setEnabled(autorizacion.verificarPermiso(rol, ModuloSistema.COMPRAS.name()));
        vistaPrincipal.getBtnProveedores().setEnabled(autorizacion.verificarPermiso(rol, ModuloSistema.PROVEEDORES.name()));
        vistaPrincipal.getBtnReportes().setEnabled(autorizacion.verificarPermiso(rol, ModuloSistema.REPORTES.name()));
        vistaPrincipal.getBtnConsultas().setEnabled(autorizacion.verificarPermiso(rol, ModuloSistema.CONSULTAS.name()));
        vistaPrincipal.getBtnCerrarSesion().setEnabled(autorizacion.verificarPermiso(rol, ModuloSistema.CERRAR_SESION.name()));
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
