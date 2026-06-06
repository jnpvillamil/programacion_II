package co.uptc.edu.co.negocio;

import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.interfaces.IGestionEmpleado;
import co.uptc.edu.co.modelo.Empleado;

public class GestionEmpleado {

	private List<Empleado> empleados;
	private final IGestionEmpleado gestionEmpleado;

	public GestionEmpleado(IGestionEmpleado gestionEmpleado) {
		if (gestionEmpleado == null) {
			throw new IllegalArgumentException("La gestión de empleado no puede ser nula.");
		}

		this.gestionEmpleado = gestionEmpleado;

		try {
			empleados = gestionEmpleado.listar();
		} catch (Exception e) {
			empleados = new ArrayList<>();
			throw new IllegalStateException("Error al cargar empleados.", e);
		}
	}

	public List<Empleado> obtenerEmpleados() {
		return new ArrayList<>(empleados);
	}

	public List<Double> obtenerSalarios() {
		List<Double> salarios = new ArrayList<>();

		for (Empleado empleado : empleados) {
			salarios.add(empleado.getSalarioEmpleado());
		}

		return salarios;
	}

	public void registrarEmpleado(Empleado empleado) throws Exception {
		validarEmpleado(empleado);

		gestionEmpleado.guardar(empleado);
		empleados.add(empleado);
	}

	public void actualizarEmpleado(Empleado empleado) throws Exception {
		validarEmpleado(empleado);

		gestionEmpleado.actualizar(empleado);
		empleados = gestionEmpleado.listar();
	}

	private void validarEmpleado(Empleado empleado) throws Exception {
		if (empleado == null) {
			throw new Exception("El empleado no puede ser nulo.");
		}

		if (empleado.getSalarioEmpleado() <= 0) {
			throw new Exception("El salario del empleado debe ser mayor que 0.");
		}
	}

    public boolean registrarCargo(Empleado empleado) throws Exception {
       
        if (empleado == null || empleado.getCargoEmpleado() == null || empleado.getCargoEmpleado().trim().isEmpty()) {
            System.out.println(" Error: El cargo no es válido.");
            return false;
        }
        
       
        return gestionEmpleado.guardarCargo(empleado);
    }
}
