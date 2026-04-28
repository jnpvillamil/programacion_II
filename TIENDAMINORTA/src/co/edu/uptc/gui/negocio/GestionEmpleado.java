package co.edu.uptc.gui.negocio;

import java.util.List;

import co.edu.uptc.gui.modelo.Empleado;
import co.edu.uptc.persistencia.LocalEmpleado;

public class GestionEmpleado {

    private LocalEmpleado localEmpleado;

    public GestionEmpleado() {
        localEmpleado = new LocalEmpleado();
    }

    public boolean registrarEmpleado(Empleado empleado) {
        return localEmpleado.guardarEmpleado(empleado);
    }

    public boolean modificarEmpleado(Empleado empleado) {
        return localEmpleado.actualizarEmpleado(empleado);
    }

    public boolean eliminarEmpleado(String codigoEmpleado) {
        return localEmpleado.eliminarEmpleado(codigoEmpleado);
    }

    public Empleado buscarEmpleado(String codigoEmpleado) {
        return localEmpleado.buscarEmpleado(codigoEmpleado);
    }

    public List<Empleado> listarEmpleados() {
        return localEmpleado.getEmpleados();
    }
}