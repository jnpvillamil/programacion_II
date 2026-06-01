package co.edu.uptc.config;

import co.edu.uptc.interfaces.*;
import co.edu.uptc.persistencia.*;
import co.edu.uptc.negocio.*;

public class TiendaConfig {
    
    private static TiendaConfig instancia;
    
    // Cambiar a true para base de datos
    private static final boolean USAR_BASE_DATOS = true;  
    
    private IGestionProducto gestionProducto;
    private IGestionCliente gestionCliente;
    private IGestionProveedor gestionProveedor;
    private IGestionVenta gestionVenta;
    private IGestionCompra gestionCompra;
    private IGestionContable gestionContable;
    
    
    private GestionProductoNegocio negocioProducto;
    private GestionContableNegocio negocioContable;
    
    private GestionProveedorNegocio negocioProveedor;
    private GestionVentaNegocio negocioVenta;
    
    
    // TODO Actualizar el sistema de base de datos de acuerdo a las nuevas clases
    
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
            System.out.println(" Usando persistencia en MEMORIA");
        }
        
        negocioProducto = new GestionProductoNegocio(gestionProducto);
        negocioContable = new GestionContableNegocio(gestionContable);
        
        negocioProveedor = new GestionProveedorNegocio(gestionProveedor);
        negocioVenta = new GestionVentaNegocio(gestionVenta);

        
    }
    
    public static TiendaConfig getInstancia() {
        if(instancia == null) {
            instancia = new TiendaConfig();
        }
        return instancia;
    }
    
    
    
    
    //-----------------Metodos Nuevos (Capa de negocio)--------

    public GestionProductoNegocio getNegocioProducto() { return negocioProducto; }
    public GestionContableNegocio getNegocioContable() { return negocioContable; }
    
    public GestionProveedorNegocio getNegocioProveedor() { return negocioProveedor; }
    public GestionVentaNegocio getNegocioVenta() { return negocioVenta; }
    
    //------------------Metodos Iguales---------------------
    public IGestionProducto getGestionProducto() { return gestionProducto; }
    public IGestionCliente getGestionCliente() { return gestionCliente; }
    public IGestionProveedor getGestionProveedor() { return gestionProveedor; }
    public IGestionVenta getGestionVenta() { return gestionVenta; }
    public IGestionCompra getGestionCompra() { return gestionCompra; }
    public IGestionContable getGestionContable() { return gestionContable; }
}