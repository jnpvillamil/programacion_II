package co.edu.uptc.dto;

import java.time.LocalDateTime;

public class MovimientoResumenDTO {
    private LocalDateTime fecha;
    private String tipoMovimiento;
    private String cuenta;
    private double valor;

    public MovimientoResumenDTO(LocalDateTime fecha, String tipoMovimiento, String cuenta, double valor) {
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.cuenta = cuenta;
        this.valor = valor;
    }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(String tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }

    public String getCuenta() { return cuenta; }
    public void setCuenta(String cuenta) { this.cuenta = cuenta; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
}