package co.edu.uptc.gui.evento;

import co.edu.uptc.interfaces.ManejadorEventoBodeguero;
import co.edu.uptc.negocio.GestionBodeguero;
import co.edu.uptc.dto.BodegueroDTO;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;


public class EventoBodeguero implements ManejadorEventoBodeguero {

    private final GestionBodeguero gestionBodeguero;
    
    @Override
    public boolean registrarBodeguero(BodegueroDTO dto, String password) {
        try {
            gestionBodeguero.registrarBodeguero(dto, password);
            return true;
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return false;
        } catch (ExcepcionAccesoDatos excepcion) {
            return false;
        }
    }
    
    public EventoBodeguero(AppConfig appConfig) {
    	this.gestionBodeguero = appConfig.getGestionBodeguero(); 
    			
    }
}
