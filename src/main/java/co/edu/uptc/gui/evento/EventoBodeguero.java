package co.edu.uptc.gui.evento;

import co.edu.uptc.dto.ProductoResumenDTO;
import co.edu.uptc.interfaces.ManejadorEventoBodeguero;
import co.edu.uptc.negocio.AppConfig;
import co.edu.uptc.negocio.GestionBodeguero;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;

import java.util.Collections;
import java.util.List;

public class EventoBodeguero implements ManejadorEventoBodeguero {

    private final GestionBodeguero gestionBodeguero;

    public EventoBodeguero(AppConfig appConfig) {
        this.gestionBodeguero = appConfig.getGestionBodeguero();
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