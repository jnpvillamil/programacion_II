package co.uptc.edu.co.tienda.configs;

import co.uptc.edu.tienda.interfaces.IGestionMascota;
import co.uptc.edu.tienda.negocio.GestionMascota;
import co.uptc.edu.tienda.persistencia.SqlMascota;

public class MascotaConfig {
	
	private IGestionMascota iMascota;
    private GestionMascota gestMascota;
    
    public MascotaConfig() {
    	iMascota = new SqlMascota();
        gestMascota = new GestionMascota(iMascota);
    }

	public IGestionMascota getiMascota() {
		return iMascota;
	}

	public void setiMascota(IGestionMascota iMascota) {
		this.iMascota = iMascota;
	}

	public GestionMascota getGestMascota() {
		return gestMascota;
	}

	public void setGestMascota(GestionMascota gestMascota) {
		this.gestMascota = gestMascota;
	}
    
    

}
