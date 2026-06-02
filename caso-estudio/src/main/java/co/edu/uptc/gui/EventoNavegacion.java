package co.edu.uptc.gui;

import co.edu.uptc.enums.ModuloSistema;
import co.edu.uptc.negocio.GestionUsuarios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventoNavegacion implements ActionListener {

    public static final String CMD_INVENTARIO = "CMD_INVENTARIO";
    public static final String CMD_CLIENTES = "CMD_CLIENTES";
    public static final String CMD_VENTAS = "CMD_VENTAS";
    public static final String CMD_COMPRAS = "CMD_COMPRAS";
    public static final String CMD_PROVEEDORES = "CMD_PROVEEDORES";
    public static final String CMD_REPORTES = "CMD_REPORTES";
    public static final String CMD_CONSULTAS = "CMD_CONSULTAS";
    public static final String CMD_CERRAR_SESION = "CMD_CERRAR_SESION";

    private final VentanaPrincipal ventanaPrincipal;
    private final VentanaLogin ventanaLogin;
    private final GestionUsuarios gestionUsuarios;

    public EventoNavegacion(VentanaPrincipal ventanaPrincipal,
                            VentanaLogin ventanaLogin,
                            GestionUsuarios gestionUsuarios) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.ventanaLogin = ventanaLogin;
        this.gestionUsuarios = gestionUsuarios;

        suscribir(ventanaPrincipal.getBtnInventario(), CMD_INVENTARIO);
        suscribir(ventanaPrincipal.getBtnClientes(), CMD_CLIENTES);
        suscribir(ventanaPrincipal.getBtnVentas(), CMD_VENTAS);
        suscribir(ventanaPrincipal.getBtnCompras(), CMD_COMPRAS);
        suscribir(ventanaPrincipal.getBtnProveedores(), CMD_PROVEEDORES);
        suscribir(ventanaPrincipal.getBtnReportes(), CMD_REPORTES);
        suscribir(ventanaPrincipal.getBtnConsultas(), CMD_CONSULTAS);
        suscribir(ventanaPrincipal.getBtnCerrarSesion(), CMD_CERRAR_SESION);
    }

    private void suscribir(javax.swing.JButton boton, String comando) {
        boton.setActionCommand(comando);
        boton.addActionListener(this);
    }

    private void navegar(ModuloSistema modulo) {
        ventanaPrincipal.mostrarPanel(modulo.name());
    }

    private void cerrarSesion() {
        gestionUsuarios.cerrarSesion();
        ventanaLogin.limpiarCampos();
        ventanaPrincipal.setVisible(false);
        ventanaLogin.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (CMD_INVENTARIO.equals(comando)) {
            navegar(ModuloSistema.INVENTARIO);
        } else if (CMD_CLIENTES.equals(comando)) {
            navegar(ModuloSistema.CLIENTES);
        } else if (CMD_VENTAS.equals(comando)) {
            navegar(ModuloSistema.VENTAS);
        } else if (CMD_COMPRAS.equals(comando)) {
            navegar(ModuloSistema.COMPRAS);
        } else if (CMD_PROVEEDORES.equals(comando)) {
            navegar(ModuloSistema.PROVEEDORES);
        } else if (CMD_REPORTES.equals(comando)) {
            navegar(ModuloSistema.REPORTES);
        } else if (CMD_CONSULTAS.equals(comando)) {
            navegar(ModuloSistema.CONSULTAS);
        } else if (CMD_CERRAR_SESION.equals(comando)) {
            cerrarSesion();
        }
    }
}
