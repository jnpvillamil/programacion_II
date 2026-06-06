package co.edu.uptc.interfaces;

import java.util.List;

import co.edu.uptc.negocio.dto.empleadoDto;

public interface IGestionEmpleado {
	public void guardar(empleadoDto empleado);

	public void actualizar(empleadoDto empleado);

	public void eliminar(int codigoEmpleado);

	public empleadoDto buscar(int codigoEmpleado);

	public List<empleadoDto> listar();
}