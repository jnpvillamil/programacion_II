package co.uptc.edu.co.tienda.configs;

import co.uptc.edu.tienda.interfaces.IGestionInventario;
import co.uptc.edu.tienda.negocio.GestionInventario;
import co.uptc.edu.tienda.persistencia.LocalInventario;

public class InventarioConfig {
	
private GestionInventario gestInventario;
private IGestionInventario iInventario;

public InventarioConfig() {
	super();
	//TODO
	iInventario = new LocalInventario();
	gestInventario = new GestionInventario(iInventario);
}

public GestionInventario getGestInventario() {
	return gestInventario;
}

public void setGestInventario(GestionInventario gestInventario) {
	this.gestInventario = gestInventario;
}

public IGestionInventario getiInventario() {
	return iInventario;
}

public void setiInventario(IGestionInventario iInventario) {
	this.iInventario = iInventario;
}



}
