package co.edu.uptc.gui;

import co.edu.uptc.controlador.*;
import co.edu.uptc.enums.ModuloSistema;
import co.edu.uptc.interfaces.IRepositorioCompra;
import co.edu.uptc.interfaces.IRepositorioContable;
import co.edu.uptc.interfaces.IRepositorioFinanciero;
import co.edu.uptc.interfaces.IRepositorioVenta;
import co.edu.uptc.interfaces.IPersistenciaProveedor;
import co.edu.uptc.interfaces.IPersistenciaUsuario;
import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.negocio.*;
import co.edu.uptc.persistencia.*;

import javax.swing.*;

public final class AppConfig {

    private static final boolean PRODUCTOS_TXT = false;
    private static final boolean CLIENTES_TXT = false;
    private static final boolean VENTAS_TXT = false;
    private static final boolean COMPRAS_TXT = false;
    private static final boolean PROVEEDORES_TXT = false;
    private static final boolean CONTABLE_TXT = false;

    private AppConfig() {
    }

    public static void iniciarAplicacion() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Repositorio<Producto> repoProducto = PRODUCTOS_TXT
                ? new PersistenciaProductoTXT()
                : new PersistenciaProducto();
        Repositorio<Cliente> repoCliente = CLIENTES_TXT
                ? new PersistenciaClienteTXT()
                : new PersistenciaCliente();
        IRepositorioVenta repoVenta = VENTAS_TXT
                ? new PersistenciaVentasTXT()
                : new PersistenciaVentas();
        IRepositorioCompra repoCompra = COMPRAS_TXT
                ? new PersistenciaCompraTXT()
                : new PersistenciaCompra();
        IPersistenciaProveedor repoProveedor = PROVEEDORES_TXT
                ? new PersistenciaProveedorTXT()
                : new PersistenciaProveedor();
        IRepositorioContable repoContable = CONTABLE_TXT
                ? new PersistenciaContableTXT()
                : new PersistenciaContable();

        IPersistenciaUsuario repoUsuario = new PersistenciaUsuario();
        IRepositorioFinanciero repoFinanciero = new PersistenciaFinanciera();

        GestionInventario gestionInventario = new GestionInventario(repoProducto);
        GestionClientes gestionClientes = new GestionClientes(repoCliente);
        GestionContable gestionContable = new GestionContable(repoContable);
        GestionVentas gestionVentas = new GestionVentas(repoVenta, gestionInventario, gestionContable);
        GestionProveedor gestionProveedor = new GestionProveedor(repoProveedor);
        GestionCompras gestionCompra = new GestionCompras(repoCompra, gestionInventario, gestionContable);
        GestionUsuarios gestionUsuarios = new GestionUsuarios(repoUsuario);

        PersistenciaReportes persistenciaReportes = new PersistenciaReportes();
        PersistenciaConsultas persistenciaConsultas = new PersistenciaConsultas();
        GestionFinanciera gestionFinanciera = new GestionFinanciera(repoFinanciero);
        GestionReportes gestionReportes = new GestionReportes(
                persistenciaReportes, gestionFinanciera, gestionContable,
                gestionVentas, gestionCompra);
        GestionConsultas gestionConsultas = new GestionConsultas(
                persistenciaConsultas, persistenciaReportes, gestionContable, gestionFinanciera);
        ServicioAutorizacion servicioAutorizacion = new ServicioAutorizacion();

        VentanaLogin ventanaLogin = new VentanaLogin();
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();

        PanelProducto panelProducto = new PanelProducto();
        PanelClientes panelClientes = new PanelClientes();
        PanelVentas panelVentas = new PanelVentas();
        PanelCompra panelCompra = new PanelCompra();
        PanelProveedor panelProveedor = new PanelProveedor();
        PanelReportes panelReportes = new PanelReportes();
        PanelConsultas panelConsultas = new PanelConsultas();

        ventanaPrincipal.agregarPanel(panelProducto, ModuloSistema.INVENTARIO.name());
        ventanaPrincipal.agregarPanel(panelClientes, ModuloSistema.CLIENTES.name());
        ventanaPrincipal.agregarPanel(panelVentas, ModuloSistema.VENTAS.name());
        ventanaPrincipal.agregarPanel(panelCompra, ModuloSistema.COMPRAS.name());
        ventanaPrincipal.agregarPanel(panelProveedor, ModuloSistema.PROVEEDORES.name());
        ventanaPrincipal.agregarPanel(panelReportes, ModuloSistema.REPORTES.name());
        ventanaPrincipal.agregarPanel(panelConsultas, ModuloSistema.CONSULTAS.name());

        ControladorPrincipal controladorPrincipal = new ControladorPrincipal(
                ventanaPrincipal, gestionUsuarios, servicioAutorizacion);
        new ControladorLogin(ventanaLogin, ventanaPrincipal, gestionUsuarios, controladorPrincipal);
        new ControladorProducto(panelProducto, gestionInventario);
        new ControladorCliente(panelClientes, gestionClientes);
        new ControladorVentas(panelVentas, gestionVentas, gestionInventario, gestionClientes);
        new ControladorProveedor(panelProveedor, gestionProveedor);
        new ControladorCompra(panelCompra, gestionCompra, gestionProveedor, gestionInventario);
        new ControladorReportes(panelReportes, gestionReportes);
        new ControladorConsultas(panelConsultas, gestionConsultas);
        new ControladorCerrarSesion(ventanaPrincipal, ventanaLogin, gestionUsuarios);

        ventanaPrincipal.mostrarPanel(ModuloSistema.INVENTARIO.name());
        ventanaLogin.setVisible(true);
    }
}
