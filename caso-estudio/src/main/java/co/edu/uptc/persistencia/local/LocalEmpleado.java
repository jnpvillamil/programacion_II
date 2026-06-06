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
    public void actualizar(String nombreAntiguo, empleadoDto empleado) {
        for (int i = 0; i < listaEmpleados.size(); i++) {
            if (listaEmpleados.get(i).getNombre().equals(nombreAntiguo)) {
                listaEmpleados.set(i, empleado);
                return;
            }
        }
    }

    @Override
    public void eliminar(String nombre) {
        listaEmpleados.removeIf(e -> e.getNombre().equals(nombre));
    }

    @Override
    public empleadoDto buscar(String nombre) {
        for (empleadoDto e : listaEmpleados)
            if (e.getNombre().equals(nombre)) return e;
        return null;
    }

    @Override
    public List<empleadoDto> listar() {
        return listaEmpleados;
    }
}