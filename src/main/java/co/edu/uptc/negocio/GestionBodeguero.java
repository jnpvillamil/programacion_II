package co.edu.uptc.negocio;

import co.edu.uptc.dto.ProductoResumenDTO;
import co.edu.uptc.persistencia.PersistenciaBodeguero;

import java.util.List;

public class GestionBodeguero {

    private final PersistenciaBodeguero persistenciaBodeguero;

    public GestionBodeguero(PersistenciaBodeguero persistenciaBodeguero) {
        this.persistenciaBodeguero = persistenciaBodeguero;
    }

    public List<ProductoResumenDTO> obtenerInventarioCritico() {
        return persistenciaBodeguero.listarInventarioCritico();
    }
}