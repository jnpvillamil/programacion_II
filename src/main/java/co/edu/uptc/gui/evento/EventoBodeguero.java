package co.edu.uptc.gui.evento;

import co.edu.uptc.dto.ProductoResumenDTO;
import co.edu.uptc.interfaces.ManejadorEventoBodeguero;
import co.edu.uptc.negocio.AppConfig;
import co.edu.uptc.negocio.GestionBodeguero;
<<<<<<< HEAD
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;

import java.util.Collections;
import java.util.List;
=======
import co.edu.uptc.dto.BodegueroDTO;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;

>>>>>>> 7d4245951cf20ce153a582cbbc5a372a9ca9ae72

public class EventoBodeguero implements ManejadorEventoBodeguero {

    private final GestionBodeguero gestionBodeguero;
<<<<<<< HEAD

    public EventoBodeguero(AppConfig appConfig) {
        this.gestionBodeguero = appConfig.getGestionBodeguero();
=======
    
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
    			
>>>>>>> 7d4245951cf20ce153a582cbbc5a372a9ca9ae72
    }

    @Override
    public List<ProductoResumenDTO> obtenerInventarioCritico() {
        try {
            return gestionBodeguero.obtenerInventarioCritico();
        } catch (ExcepcionAccesoDatos excepcion) {
            return Collections.emptyList();
        }
    }
}