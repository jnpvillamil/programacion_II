package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.gui.modelo.Empleado;

public class LocalEmpleado {

    private List<Empleado> empleados;

    public LocalEmpleado() {
        empleados = new ArrayList<>();
    }

    public boolean guardarEmpleado(Empleado empleado) {
        if (buscarEmpleado(empleado.getCodigoEmpleado()) != null) {
            return false;
        }
        empleados.add(empleado);
        return true;
    }

    public Empleado buscarEmpleado(String codigoEmpleado) {
        for (Empleado empleado : empleados) {
            if (empleado.getCodigoEmpleado().equalsIgnoreCase(codigoEmpleado)) {
                return empleado;
            }
        }
        return null;
    }

    public boolean actualizarEmpleado(Empleado empleadoActualizado) {
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getCodigoEmpleado().equalsIgnoreCase(empleadoActualizado.getCodigoEmpleado())) {
                empleados.set(i, empleadoActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarEmpleado(String codigoEmpleado) {
        Empleado empleado = buscarEmpleado(codigoEmpleado);
        if (empleado != null) {
            empleados.remove(empleado);
            return true;
        }
        return false;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }
}