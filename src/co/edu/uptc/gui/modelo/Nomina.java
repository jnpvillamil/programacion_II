package co.edu.uptc.gui.modelo;

import java.time.LocalDate;

import co.edu.uptc.gui.interfaces.Calculable;

public class Nomina implements Calculable {

    private String codigoNomina;
    private LocalDate fechaNomina;
    private Empleado empleado;
    private double devengado;
    private double descuentos;

    public Nomina() {
        fechaNomina = LocalDate.now();
    }

    public String getCodigoNomina() {
        return codigoNomina;
    }

    public void setCodigoNomina(String codigoNomina) {
        this.codigoNomina = codigoNomina;
    }

    public LocalDate getFechaNomina() {
        return fechaNomina;
    }

    public void setFechaNomina(LocalDate fechaNomina) {
        this.fechaNomina = fechaNomina;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public double getDevengado() {
        return devengado;
    }

    public void setDevengado(double devengado) {
        this.devengado = devengado;
    }

    public double getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(double descuentos) {
        this.descuentos = descuentos;
    }

    @Override
    public double calcularTotal() {
        return devengado - descuentos;
    }
}