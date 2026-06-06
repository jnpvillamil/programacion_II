package co.edu.uptc.negocio;

import java.util.List;

import co.edu.uptc.interfaces.IGestionEmpleado;
import co.edu.uptc.negocio.dto.empleadoDto;
import co.edu.uptc.persistencia.database.DatabaseEmpleado;

public class gestionEmpleados {

	private IGestionEmpleado iEmpleado;

	public gestionEmpleados() {
		this.iEmpleado = new DatabaseEmpleado();
	}

	public void registrar(empleadoDto empleado) throws Exception {
		if (empleado == null)
			throw new Exception("No se tiene información del empleado");
		iEmpleado.guardar(empleado);
	}

	public void modificar(empleadoDto empleado) throws Exception {
		if (empleado == null)
			throw new Exception("No se tiene información del empleado");
		iEmpleado.actualizar(empleado);
	}

	public void inactivar(int codigo) throws Exception {
		iEmpleado.eliminar(codigo);
	}

	public empleadoDto buscar(int codigo) {
		return iEmpleado.buscar(codigo);
	}

	public List<empleadoDto> listar() {
		return iEmpleado.listar();
	}
}