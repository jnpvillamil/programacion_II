package co.edu.uptc.config;

import co.edu.uptc.interfaces.*;
import co.edu.uptc.persistencia.*;

public class TiendaConfig {
    
    private static TiendaConfig instancia;
    
    // Cambiar a true para base de datos
    private static final boolean USAR_BASE_DATOS = true;  
    
    private GestionProducto gestionProducto;
    private GestionCliente gestionCliente;
    private GestionProveedor gestionProveedor;
    private GestionVenta gestionVenta;
    private GestionCompra gestionCompra;
    private GestionContable gestionContable;
    
    
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