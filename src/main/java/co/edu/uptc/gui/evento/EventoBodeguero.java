package co.edu.uptc.gui.evento;

import co.edu.uptc.interfaces.ManejadorEventoBodeguero;
import co.edu.uptc.negocio.GestionBodeguero;

public class EventoBodeguero implements ManejadorEventoBodeguero {

    private final GestionBodeguero gestionBodeguero;
    

    public EventoBodeguero(AppConfig appConfig) {
    	this.gestionBodeguero = appConfig.getGestionBodeguero(); 
    }
}
