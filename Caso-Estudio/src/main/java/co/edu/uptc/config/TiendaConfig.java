package co.edu.uptc.config;

import co.edu.uptc.interfaces.*;
import co.edu.uptc.persistencia.*;
import co.edu.uptc.negocio.*;

public class TiendaConfig {
    
    private static TiendaConfig instancia;
    private static final boolean USAR_BASE_DATOS = true;  
    
    // Persistencias
    private IGestionProducto gestionProducto;
    private IGestionCliente gestionCliente;
    private IGestionProveedor gestionProveedor;
    private IGestionVenta gestionVenta;
    private IGestionCompra gestionCompra;
    private IGestionContable gestionContable;
    
    // Negocios
    private GestionProductoNegocio negocioProducto;
    private GestionContableNegocio negocioContable;
    private GestionProveedorNegocio negocioProveedor;
    private GestionVentaNegocio negocioVenta;
    private GestionClienteNegocio negocioCliente;
    private GestionCompraNegocio negocioCompra;
    
    private TiendaConfig() {
        if (USAR_BASE_DATOS) {
            gestionProducto = new DatabaseProducto();
            gestionCliente = new DatabaseCliente();   
            gestionProveedor = new DatabaseProveedor(); 
            gestionVenta = new DatabaseVenta();         
            gestionCompra = new DatabaseCompra();       
            gestionContable = new DatabaseContable();   
        } else {
            gestionProducto = new LocalProducto();
            gestionCliente = new LocalCliente();
            gestionProveedor = new LocalProveedor();
            gestionVenta = new LocalVenta();
            gestionCompra = new LocalCompra();
            gestionContable = new LocalContable();
            System.out.println("Usando persistencia en MEMORIA");
        }
        
        // Inicializar negocios
        negocioProducto = new GestionProductoNegocio(gestionProducto);
        negocioContable = new GestionContableNegocio(gestionContable);
        negocioProveedor = new GestionProveedorNegocio(gestionProveedor);
        negocioVenta = new GestionVentaNegocio(gestionVenta);
        negocioCliente = new GestionClienteNegocio(gestionCliente);
        negocioCompra = new GestionCompraNegocio(gestionCompra);
    }
    
    public static TiendaConfig getInstancia() {
        if (instancia == null) {
            instancia = new TiendaConfig();
        }
        return instancia;
    }
    
    // Getters de persistencia
    public IGestionProducto getGestionProducto() { return gestionProducto; }
    public IGestionCliente getGestionCliente() { return gestionCliente; }
    public IGestionProveedor getGestionProveedor() { return gestionProveedor; }
    public IGestionVenta getGestionVenta() { return gestionVenta; }
    public IGestionCompra getGestionCompra() { return gestionCompra; }
    public IGestionContable getGestionContable() { return gestionContable; }
    
    // Getters de negocio
    public GestionProductoNegocio getNegocioProducto() { return negocioProducto; }
    public GestionContableNegocio getNegocioContable() { return negocioContable; }
    public GestionProveedorNegocio getNegocioProveedor() { return negocioProveedor; }
    public GestionVentaNegocio getNegocioVenta() { return negocioVenta; }
    public GestionClienteNegocio getNegocioCliente() { return negocioCliente; }
    public GestionCompraNegocio getNegocioCompra() { return negocioCompra; }
}