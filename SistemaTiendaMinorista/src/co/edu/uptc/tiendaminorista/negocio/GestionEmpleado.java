package co.edu.uptc.tiendaminorista.negocio;

import java.util.List;
import co.edu.uptc.tiendaminorista.interfaces.IGestionEmpleado;
import co.edu.uptc.tiendaminorista.modelo.Empleado;

public class GestionEmpleado {
    
    private IGestionEmpleado persistence;

    public GestionEmpleado(IGestionEmpleado persistence) {
        this.persistence = persistence;
    }

    public void guardar(Empleado empleado) {
        this.persistence.guardar(empleado);
    }
    
    public void actualizar(Empleado empleado) {
        this.persistence.actualizar(empleado);
    }
    
   
    public void eliminar(Empleado empleado) {
        this.persistence.eliminar(empleado); 
    }
    
    public List<Empleado> listarEmpleados() {
        return this.persistence.listar();
    }
}