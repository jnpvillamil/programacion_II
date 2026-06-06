package co.edu.uptc.tiendaminorista.negocio;

import co.edu.uptc.tiendaminorista.persistencia.*;

/**
 * Punto central de configuración del sistema.
 * Instancia la capa de persistencia e inyecta las dependencias
 * en los gestores de negocio.
 */
public class TiendaConfig {
    private final GestionCliente gestionCliente;
    private final GestionProveedor gestionProveedor;
    private final GestionProducto gestionProducto;
    private final GestionContable gestionContable;
    private final GestionEmpleado gestionEmpleado; 
    private final GestionPractica gestionpractica;

    public TiendaConfig() {
        this.gestionCliente   = new GestionCliente(new LocalCliente());
        this.gestionProveedor = new GestionProveedor(new LocalProveedor());
        this.gestionProducto  = new GestionProducto(new LocalProducto());
        this.gestionContable  = new GestionContable(new LocalContable());
        this.gestionEmpleado  = new GestionEmpleado(new LocalEmpleado()); 
        this.gestionpractica  = new GestionPractica(new LocalPractica());
    }

    public GestionCliente getGestionCliente()     { return gestionCliente; }
    public GestionProveedor getGestionProveedor() { return gestionProveedor; }
    public GestionProducto getGestionProducto()   { return gestionProducto; }
    public GestionContable getGestionContable()   { return gestionContable; }
    public GestionEmpleado getGestionEmpleado()   { return gestionEmpleado; } 
    public GestionPractica getGestionPractica()    {return gestionpractica;}
}