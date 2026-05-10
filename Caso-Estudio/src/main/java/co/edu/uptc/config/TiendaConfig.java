package co.edu.uptc.config;

import co.edu.uptc.interfaces.*;
import co.edu.uptc.persistencia.*;

public class TiendaConfig {


    
    private static TiendaConfig instancia;
    
    private GestionProducto gestionProducto;
    private GestionCliente gestionCliente;
    private GestionProveedor gestionProveedor;
    private GestionVenta gestionVenta;
    private GestionCompra gestionCompra;
    private GestionContable gestionContable;  
    
    private TiendaConfig() {
        gestionProducto = new LocalProducto();
        gestionCliente = new LocalCliente();
        gestionProveedor = new LocalProveedor();
        gestionVenta = new LocalVenta();
        gestionCompra = new LocalCompra();
        gestionContable = new LocalContable();  
    }
    
    public static TiendaConfig getInstancia() {
        if(instancia == null) {
            instancia = new TiendaConfig();
        }
        return instancia;
    }
    
    public GestionProducto getGestionProducto() { return gestionProducto; }
    public GestionCliente getGestionCliente() { return gestionCliente; }
    public GestionProveedor getGestionProveedor() { return gestionProveedor; }
    public GestionVenta getGestionVenta() { return gestionVenta; }
    public GestionCompra getGestionCompra() { return gestionCompra; }
    public GestionContable getGestionContable() { return gestionContable; }  
}