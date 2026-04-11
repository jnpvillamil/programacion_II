package co.edu.uptc.gui.modelo;

import co.edu.uptc.enums.EstadoEmpleadoEnum;
import co.edu.uptc.enums.TipoDocumentoEnum;
import co.edu.uptc.enums.TipoEmpleadoEnum;

public class EmpleadoTerminoFijo extends Empleado {

    private int mesesContrato;

    public EmpleadoTerminoFijo() {
        setTipoEmpleado(TipoEmpleadoEnum.TERMINO_FIJO);
    }

    public EmpleadoTerminoFijo(String codigo, String nombre, String apellido, TipoDocumentoEnum tipoDocumento,
            String numeroDocumento, String telefono, String direccion,
            String codigoEmpleado, double salarioBase, EstadoEmpleadoEnum estadoEmpleado, int mesesContrato) {
        super(codigo, nombre, apellido, tipoDocumento, numeroDocumento, telefono, direccion,
                codigoEmpleado, salarioBase, TipoEmpleadoEnum.TERMINO_FIJO, estadoEmpleado);
        this.mesesContrato = mesesContrato;
    }

    public int getMesesContrato() {
        return mesesContrato;
    }

    public void setMesesContrato(int mesesContrato) {
        this.mesesContrato = mesesContrato;
    }
}