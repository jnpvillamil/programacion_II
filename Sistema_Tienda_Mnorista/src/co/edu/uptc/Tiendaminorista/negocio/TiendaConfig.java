package co.edu.uptc.Tiendaminorista.negocio;

import co.edu.uptc.Tiendaminorista.interfaces.*;
import co.edu.uptc.Tiendaminorista.persistencia.LocalCliente;
import co.edu.uptc.Tiendaminorista.persistencia.LocalProveedor;
import co.edu.uptc.Tiendaminorista.persistencia.LocalProducto;

public class TiendaConfig {

   
    private GestionCliente gestioncliente;
    private GestionProveedor gestionproveedor;
    private GestionProducto gestionproducto;
    

    private IGestionCliente icliente;
    private IGestionProveedor iproveedor;
    private IGestionProducto iproducto;
    
    public TiendaConfig() {
        super();
        
    
        this.icliente = new LocalCliente();
        this.iproveedor = new LocalProveedor();
        this.iproducto = new LocalProducto();   
        this.gestioncliente = new GestionCliente(icliente);
        this.gestionproveedor = new GestionProveedor(iproveedor);
        this.gestionproducto = new GestionProducto(iproducto);
    }

 
    public GestionCliente getGestioncliente() {
        return gestioncliente;
    }

    public GestionProveedor getGestionproveedor() {
        return gestionproveedor;
    }

    public GestionProducto getGestionproducto() {
        return gestionproducto;
    }


    public void setGestioncliente(GestionCliente gestioncliente) {
        this.gestioncliente = gestioncliente;
    }
}