package co.edu.uptc.tiendaminorista.interfaces;

import java.util.List;
import co.edu.uptc.tiendaminorista.modelo.Telefono;

public interface ITelefono {

  
    void guardar(Telefono telefono);

    List<Telefono> obtenerTelefonos();
}