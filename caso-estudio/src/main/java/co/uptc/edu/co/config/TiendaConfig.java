package co.uptc.edu.co.config;

import co.uptc.edu.co.negocio.GestionCliente;
import co.uptc.edu.co.negocio.GestionCompra;
import co.uptc.edu.co.negocio.GestionProducto;
import co.uptc.edu.co.negocio.GestionProveedor;
import co.uptc.edu.co.negocio.GestionVenta;


public class TiendaConfig {
	
	private GestionProducto gestionProducto;
	private GestionCliente gestionCliente;
	private GestionProveedor gestionProveedor;
	private GestionVenta gestionVenta;
	private GestionCompra gestionCompra;
	
  public TiendaConfig(){
	  
	  inicializarGestiones(); 
  }
  
  private void inicializarGestiones(){
	  
	  gestionProducto = new GestionProducto();
	  gestionCliente = new GestionCliente();
	  gestionProveedor = new GestionProveedor();
	  gestionVenta = new GestionVenta();
	  gestionCompra = new GestionCompra();
	  	  
	  
  }
  
  public GestionProducto getGestionProducto() {
      return gestionProducto;
  }

  public GestionCliente getGestionCliente() {
      return gestionCliente;
  }

  public GestionProveedor getGestionProveedor() {
      return gestionProveedor;
  }

  public GestionVenta getGestionVenta() {
      return gestionVenta;
  }

  public GestionCompra getGestionCompra() {
      return gestionCompra;
  }
  
  
}
