package co.uptc.edu.co.interfaces;

import java.util.List;

import co.uptc.edu.co.modelo.Empleado;

public interface IGestionEmpleado {
   
	void guardar(Empleado empleado) throws Exception;
	
	void actualizar(Empleado empleado) throws Exception;
	
	List<Empleado> listar() throws Exception;
}
