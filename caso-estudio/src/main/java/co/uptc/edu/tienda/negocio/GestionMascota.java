package co.uptc.edu.tienda.negocio;

import co.uptc.edu.tienda.interfaces.IGestionMascota;
import co.uptc.edu.tienda.modelo.Mascota;

public class GestionMascota {

    private final IGestionMascota gestionM;

    public GestionMascota(IGestionMascota gestionM) {
        super();
        this.gestionM = gestionM;
    }

    public void agregarMascota(Mascota nuevo) throws Exception {
        if (nuevo.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre es obligatorio");
        }
        if (nuevo.getRaza().trim().isEmpty()) {
            throw new Exception("La raza es obligatoria");
        }
        if (nuevo.getEdad() <= 0) {
            throw new Exception("La edad debe ser un número positivo");
        }
        gestionM.guardar(nuevo);
    }
}