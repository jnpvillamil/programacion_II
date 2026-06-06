package co.edu.uptc.negocio;

import java.util.List;
import co.edu.uptc.interfaces.IGestionEmpleado;
import co.edu.uptc.negocio.dto.empleadoDto;
import co.edu.uptc.persistencia.local.LocalEmpleado;

public class gestionEmpleados {

    private IGestionEmpleado iEmpleado;

    public gestionEmpleados() {
        this.iEmpleado = new LocalEmpleado();
    }

    public void registrar(empleadoDto empleado) throws Exception {
        if (empleado == null || empleado.getNombre().isBlank())
            throw new Exception("El nombre del empleado es requerido");
        iEmpleado.guardar(empleado);
    }

    public void modificar(String nombreAntiguo, empleadoDto empleado) throws Exception {
        if (empleado == null)
            throw new Exception("No se tiene información del empleado");
        iEmpleado.actualizar(nombreAntiguo, empleado);
    }

    public void inactivar(String nombre) throws Exception {
        iEmpleado.eliminar(nombre);
    }

    public empleadoDto buscar(String nombre) {
        return iEmpleado.buscar(nombre);
    }

    public List<empleadoDto> listar() {
        return iEmpleado.listar();
    }
}