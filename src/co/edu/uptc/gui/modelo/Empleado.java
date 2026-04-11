package co.edu.uptc.gui.modelo;

import co.edu.uptc.enums.EstadoEmpleadoEnum;
import co.edu.uptc.enums.TipoDocumentoEnum;
import co.edu.uptc.enums.TipoEmpleadoEnum;
import co.edu.uptc.gui.interfaces.Gestionable;

public class Empleado extends Persona implements Gestionable {

    private String codigoEmpleado;
    private double salarioBase;
    private TipoEmpleadoEnum tipoEmpleado;
    private EstadoEmpleadoEnum estadoEmpleado;

    public Empleado() {
        estadoEmpleado = EstadoEmpleadoEnum.ACTIVO;
    }

    public Empleado(String codigo, String nombre, String apellido, TipoDocumentoEnum tipoDocumento,
            String numeroDocumento, String telefono, String direccion,
            String codigoEmpleado, double salarioBase, TipoEmpleadoEnum tipoEmpleado,
            EstadoEmpleadoEnum estadoEmpleado) {
        super(codigo, nombre, apellido, tipoDocumento, numeroDocumento, telefono, direccion);
        this.codigoEmpleado = codigoEmpleado;
        this.salarioBase = salarioBase;
        this.tipoEmpleado = tipoEmpleado;
        this.estadoEmpleado = estadoEmpleado;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public TipoEmpleadoEnum getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(TipoEmpleadoEnum tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public EstadoEmpleadoEnum getEstadoEmpleado() {
        return estadoEmpleado;
    }

    public void setEstadoEmpleado(EstadoEmpleadoEnum estadoEmpleado) {
        this.estadoEmpleado = estadoEmpleado;
    }

    @Override
    public void registrar() {
        estadoEmpleado = EstadoEmpleadoEnum.ACTIVO;
    }

    @Override
    public void modificar() {
        System.out.println("Empleado modificado");
    }

    @Override
    public void inactivar() {
        estadoEmpleado = EstadoEmpleadoEnum.INACTIVO;
    }
}