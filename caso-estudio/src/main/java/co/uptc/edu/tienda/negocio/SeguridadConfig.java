package co.uptc.edu.tienda.negocio; // Ajusta el paquete según corresponda

import co.uptc.edu.tienda.interfaces.IGestionUsuario;
import co.uptc.edu.tienda.persistencia.LocalUsuario;

public class SeguridadConfig {

	private GestionSeguridad gestSeguridad;
	private IGestionUsuario iUsuario;

	public SeguridadConfig() {
		super();
		// Inicializamos la persistencia y la inyectamos al negocio
		iUsuario = new LocalUsuario();
		gestSeguridad = new GestionSeguridad(iUsuario);
	}

	public GestionSeguridad getGestSeguridad() {
		return gestSeguridad;
	}

	public void setGestSeguridad(GestionSeguridad gestSeguridad) {
		this.gestSeguridad = gestSeguridad;
	}

	public IGestionUsuario getiUsuario() {
		return iUsuario;
	}

	public void setiUsuario(IGestionUsuario iUsuario) {
		this.iUsuario = iUsuario;
	}
}