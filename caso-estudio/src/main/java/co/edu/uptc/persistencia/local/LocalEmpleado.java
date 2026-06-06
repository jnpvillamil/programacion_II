package co.edu.uptc.persistencia.local;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.interfaces.IGestionEmpleado;
import co.edu.uptc.negocio.dto.empleadoDto;

public class LocalEmpleado implements IGestionEmpleado {

	private List<empleadoDto> listaEmpleados = new ArrayList<>();

	@Override
	public void guardar(empleadoDto empleado) {
		listaEmpleados.add(empleado);
	}

	@Override
	public void actualizar(empleadoDto empleado) {
		for (int i = 0; i < listaEmpleados.size(); i++) {
			if (listaEmpleados.get(i).getCodigoEmpleado() == empleado.getCodigoEmpleado()) {
				listaEmpleados.set(i, empleado);
				return;
			}
		}
	}

	@Override
	public void eliminar(int codigoEmpleado) {
		listaEmpleados.removeIf(e -> e.getCodigoEmpleado() == codigoEmpleado);
	}

	@Override
	public empleadoDto buscar(int codigoEmpleado) {
		for (empleadoDto e : listaEmpleados)
			if (e.getCodigoEmpleado() == codigoEmpleado)
				return e;
		return null;
	}

	@Override
	public List<empleadoDto> listar() {
		return listaEmpleados;
	}
}