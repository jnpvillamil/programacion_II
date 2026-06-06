package co.edu.uptc.interfaces;

import co.edu.uptc.dto.ProductoResumenDTO;

import java.util.List;

public interface ManejadorEventoBodeguero {


    boolean registrarBodeguero(BodegueroDTO dto, String password);
    
    List<ProductoResumenDTO> obtenerInventarioCritico();
    
}

