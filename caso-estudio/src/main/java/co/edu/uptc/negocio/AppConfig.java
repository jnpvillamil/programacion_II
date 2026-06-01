package co.edu.uptc.negocio;

import co.edu.uptc.enums.ModuloSistema;
import co.edu.uptc.gui.*;
import co.edu.uptc.interfaces.*;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.persistencia.*;
import javax.swing.UIManager;

public final class AppConfig {

    private AppConfig() {
    }

    public static void iniciarAplicacion() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Repositorio<Producto> repoProducto = new PersistenciaProducto();
        Repositorio<Cliente> repoCliente = new PersistenciaCliente();
        IRepositorioVenta repoVenta = new PersistenciaVentas();
        IRepositorioCompra repoCompra = new PersistenciaCompra();
        IPersistenciaProveedor repoProveedor = new PersistenciaProveedor();
        IRepositorioContable repoContable = new PersistenciaContable();
        IPersistenciaUsuario repoUsuario = new PersistenciaUsuario();
        IRepositorioFinanciero repoFinanciero = new PersistenciaFinanciera();

        PersistenciaReportes persistenciaReportes = new PersistenciaReportes();
        PersistenciaConsultas persistenciaConsultas = new PersistenciaConsultas();


        GestionInventario gestionInventario = new GestionInventario(repoProducto);
        GestionClientes gestionClientes = new GestionClientes(repoCliente);
        GestionContable gestionContable = new GestionContable(repoContable);
        GestionVentas gestionVentas = new GestionVentas(repoVenta, gestionInventario, gestionContable);
        GestionProveedor gestionProveedor = new GestionProveedor(repoProveedor);
        GestionCompras gestionCompras = new GestionCompras(repoCompra, gestionInventario, gestionContable);
        GestionUsuarios gestionUsuarios = new GestionUsuarios(repoUsuario);
        GestionFinanciera gestionFinanciera = new GestionFinanciera(repoFinanciero);
        
        GestionReportes gestionReportes = new GestionReportes(
                persistenciaReportes, gestionFinanciera, gestionContable,
                gestionVentas, gestionCompras);
        GestionConsultas gestionConsultas = new GestionConsultas(
                persistenciaConsultas, persistenciaReportes, gestionContable, gestionFinanciera,
                gestionProveedor);
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

        new EventoLogin(ventanaPrincipal, ventanaLogin, gestionUsuarios, servicioAutorizacion);
        new EventoNavegacion(ventanaPrincipal, ventanaLogin, gestionUsuarios);

        EventoProducto eventoProducto = new EventoProducto(ventanaPrincipal, panelProducto, gestionInventario);
        EventoClientes eventoClientes = new EventoClientes(ventanaPrincipal, panelClientes, gestionClientes);
        new EventoVentas(ventanaPrincipal, panelVentas, gestionVentas, gestionClientes);
        new EventoCompra(ventanaPrincipal, panelCompra, gestionCompras, gestionProveedor);
        EventoProveedor eventoProveedor = new EventoProveedor(ventanaPrincipal, panelProveedor, gestionProveedor);
        new EventoReportes(ventanaPrincipal, panelReportes, gestionReportes);
        new EventoConsultas(ventanaPrincipal, panelConsultas, gestionConsultas);

        eventoProducto.refrescarVista();
        eventoClientes.refrescarVista();
        eventoProveedor.refrescarVista();

        ventanaPrincipal.setVisible(false);
        ventanaPrincipal.mostrarPanel(ModuloSistema.INVENTARIO.name());
        ventanaLogin.setVisible(true);
    }
}