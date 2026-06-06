package co.edu.uptc.interfaces;

import co.edu.uptc.dto.BodegueroDTO;

public interface ManejadorEventoBodeguero {

    boolean registrarBodeguero(BodegueroDTO dto, String password);
}
