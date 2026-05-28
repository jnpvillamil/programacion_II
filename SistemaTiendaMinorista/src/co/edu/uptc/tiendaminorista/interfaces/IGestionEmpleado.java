package co.edu.uptc.tiendaminorista.interfaces;

import java.util.List;

import co.edu.uptc.tiendaminorista.modelo.Empleado;

public interface IGestionEmpleado {

	void guardar(Empleado empleado);
	void actualizar(Empleado empleado);
	void eliminar(Empleado empleado);
	List<Empleado>listar();
}
