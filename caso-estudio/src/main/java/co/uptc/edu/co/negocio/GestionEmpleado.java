package co.uptc.edu.co.negocio;

import co.uptc.edu.co.interfaces.IGestionEmpleado;
import co.uptc.edu.co.modelo.Empleado;


public class GestionEmpleado {
	private final IGestionEmpleado empleadoDAO;

    public GestionEmpleado(IGestionEmpleado empleadoDAO) {
        this.empleadoDAO = empleadoDAO;
    }

    public boolean registrarCargo(Empleado empleado) throws Exception {
       
        if (empleado == null || empleado.getCargonEmpleado() == null || empleado.getCargonEmpleado().trim().isEmpty()) {
            System.out.println(" Error: El cargo no es válido.");
            return false;
        }
        
       
        return empleadoDAO.guardarCargo(empleado);
    }
}
