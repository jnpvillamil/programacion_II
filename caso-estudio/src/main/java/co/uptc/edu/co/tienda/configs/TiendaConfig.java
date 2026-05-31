package co.uptc.edu.co.tienda.configs;

import co.uptc.edu.tienda.interfaces.*;
import co.uptc.edu.tienda.negocio.*;
import co.uptc.edu.tienda.persistencia.*;

public class TiendaConfig {

    // =============================================
    // CLIENTE
    // =============================================
    private IGestionCliente iCliente;
    private GestionCliente gestCliente;

    // =============================================
    // PROVEEDOR
    // =============================================
    private IGestionProveedor iProveedor;
    private GestionProveedor gestProveedor;

    // =============================================
    // PRODUCTO
    // =============================================
    private IGestionProducto iProducto;
    private GestionProducto gestProducto;

    // =============================================
    // COMPRA
    // =============================================
    private IGestionCompra iCompra;
    private GestionCompra gestCompra;

    // =============================================
    // VENTA
    // =============================================
    public static final String PREFIJO_FACTURA = "FACT-";
    private IGestionVenta iVenta;
    private GestionVenta gestVenta;

    // =============================================
    // INVENTARIO
    // =============================================
    private IGestionInventario iInventario;
    private GestionInventario gestInventario;

    // =============================================
    // CONTABLE
    // =============================================
    private IGestionContable iContable;
    private GestionContable gestContable;

    // =============================================
    // REPORTE
    // =============================================
    private IGestionReporte iReporte;
    private GestionReporte gestReporte;

    // =============================================
    // SEGURIDAD
    // =============================================
    private IGestionUsuario iUsuario;
    private GestionSeguridad gestSeguridad;

    // =============================================
    // CONSTRUCTOR ÚNICO
    // =============================================
    public TiendaConfig() {

        // Cliente
        iCliente = new LocalCliente();
        gestCliente = new GestionCliente(iCliente);

        // Proveedor
        iProveedor = new SqlProveedor();
        gestProveedor = new GestionProveedor(iProveedor);

        // Producto
        iProducto = new LocalProducto();
        gestProducto = new GestionProducto(iProducto);

        // Compra
        iCompra = new LocalCompra();
        gestCompra = new GestionCompra(iCompra);

        // Venta
        iVenta = new LocalVenta();
        gestVenta = new GestionVenta(iVenta);

        // Inventario
        iInventario = new LocalInventario();
        gestInventario = new GestionInventario(iInventario);

        // Contable
        iContable = new LocalContable();
        gestContable = new GestionContable(iContable);

        // Reporte — depende de iVenta e iCompra, va de último
        iReporte = new LocalReporte();
        gestReporte = new GestionReporte(iVenta, iCompra, iReporte);

        // Seguridad
        iUsuario = new LocalUsuario();
        gestSeguridad = new GestionSeguridad(iUsuario);
    }

    // =============================================
    // GETTERS
    // =============================================
    public GestionCliente getGestCliente()       { return gestCliente; }
    public IGestionCliente getiCliente()         { return iCliente; }

    public GestionProveedor getGestProveedor()   { return gestProveedor; }
    public IGestionProveedor getiProveedor()     { return iProveedor; }

    public GestionProducto getGestProducto()     { return gestProducto; }
    public IGestionProducto getiProducto()       { return iProducto; }

    public GestionCompra getGestCompra()         { return gestCompra; }
    public IGestionCompra getiCompra()           { return iCompra; }

    public GestionVenta getGestVenta()           { return gestVenta; }
    public IGestionVenta getiVenta()             { return iVenta; }

    public GestionInventario getGestInventario() { return gestInventario; }
    public IGestionInventario getiInventario()   { return iInventario; }

    public GestionContable getGestContable()     { return gestContable; }
    public IGestionContable getiContable()       { return iContable; }

    public GestionReporte getGestReporte()       { return gestReporte; }
    public IGestionReporte getiReporte()         { return iReporte; }

    public GestionSeguridad getGestSeguridad()   { return gestSeguridad; }
    public IGestionUsuario getiUsuario()         { return iUsuario; }
}