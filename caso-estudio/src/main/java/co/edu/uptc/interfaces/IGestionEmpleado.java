package co.edu.uptc.interfaces;

import java.util.List;
import co.edu.uptc.negocio.dto.empleadoDto;

public interface IGestionEmpleado {
    public void guardar(empleadoDto empleado);
    public void actualizar(String nombreAntiguo, empleadoDto empleado);
    public void eliminar(String nombre);
    public empleadoDto buscar(String nombre);
    public List<empleadoDto> listar();
}