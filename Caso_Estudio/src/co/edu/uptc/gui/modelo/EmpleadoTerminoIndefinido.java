package co.edu.uptc.gui.modelo;

import co.edu.uptc.enums.EstadoEmpleadoEnum;
import co.edu.uptc.enums.TipoDocumentoEnum;
import co.edu.uptc.enums.TipoEmpleadoEnum;

public class EmpleadoTerminoIndefinido extends Empleado {

    private double bonificacion;

    public EmpleadoTerminoIndefinido() {
        setTipoEmpleado(TipoEmpleadoEnum.TERMINO_INDEFINIDO);
    }

    public EmpleadoTerminoIndefinido(String codigo, String nombre, String apellido, TipoDocumentoEnum tipoDocumento,
            String numeroDocumento, String telefono, String direccion,
            String codigoEmpleado, double salarioBase, EstadoEmpleadoEnum estadoEmpleado, double bonificacion) {
        super(codigo, nombre, apellido, tipoDocumento, numeroDocumento, telefono, direccion,
                codigoEmpleado, salarioBase, TipoEmpleadoEnum.TERMINO_INDEFINIDO, estadoEmpleado);
        this.bonificacion = bonificacion;
    }

    public double getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(double bonificacion) {
        this.bonificacion = bonificacion;
    }
}