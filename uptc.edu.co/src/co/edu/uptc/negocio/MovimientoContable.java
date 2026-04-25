package co.edu.uptc.negocio;

import java.util.Date;

public class MovimientoContable {
    private String tipo;
    private double monto;
    private Date fecha;
    private String cuentaAfectada;

    public MovimientoContable(String tipo, double monto) {
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = new Date();
    }
}