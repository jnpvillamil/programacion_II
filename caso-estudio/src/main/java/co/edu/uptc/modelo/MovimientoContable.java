package co.edu.uptc.modelo;

import co.edu.uptc.enums.TipoMovimiento;
import java.time.LocalDateTime;

public class MovimientoContable {

    private String codigoTransaccion;
    private LocalDateTime fechaMovimiento;
    private TipoMovimiento tipoMovimiento;
    private String cuentaContable;
    private double valorMovimiento;
    private String descripcion;

    public MovimientoContable(String codigoTransaccion, LocalDateTime fechaMovimiento, TipoMovimiento tipoMovimiento, String cuentaContable, double valorMovimiento, String descripcion) {
        this.codigoTransaccion = codigoTransaccion;
        this.fechaMovimiento = fechaMovimiento;
        this.tipoMovimiento = tipoMovimiento;
        this.cuentaContable = cuentaContable;
        this.valorMovimiento = valorMovimiento;
        this.descripcion = descripcion;
    }

    public String getCodigoTransaccion() { return codigoTransaccion; }
    public void setCodigoTransaccion(String codigoTransaccion) { this.codigoTransaccion = codigoTransaccion; }
    public LocalDateTime getFechaMovimiento() { return fechaMovimiento; }
    public void setFechaMovimiento(LocalDateTime fechaMovimiento) { this.fechaMovimiento = fechaMovimiento; }
    public TipoMovimiento getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }
    public String getCuentaContable() { return cuentaContable; }
    public void setCuentaContable(String cuentaContable) { this.cuentaContable = cuentaContable; }
    public double getValorMovimiento() { return valorMovimiento; }
    public void setValorMovimiento(double valorMovimiento) { this.valorMovimiento = valorMovimiento; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}