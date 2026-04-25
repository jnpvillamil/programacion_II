package co.uptc.edu.co.modelo;

import java.time.LocalDate;

import co.uptc.edu.co.modelo.enums.TipoMovimientoInventarioEnum;

public class MovimientoInventario {

   
    private String codigoProducto;
    private TipoMovimientoInventarioEnum tipoMovimientoInventarioEnum;
    private int cantidad;
    private LocalDate fechaMovimiento;
    private String descripcion;

    public MovimientoInventario() {
    }

    public MovimientoInventario( String codigoProducto,
            TipoMovimientoInventarioEnum tipoMovimiento, int cantidad,
            LocalDate fechaMovimiento, String descripcion) {
       
        this.codigoProducto = codigoProducto;
        this.tipoMovimientoInventarioEnum = tipoMovimiento;
        this.cantidad = cantidad;
        this.fechaMovimiento = fechaMovimiento;
        this.descripcion = descripcion;
    }

   

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public TipoMovimientoInventarioEnum getTipoMovimiento() {
        return tipoMovimientoInventarioEnum;
    }

    public void setTipoMovimiento(TipoMovimientoInventarioEnum tipoMovimiento) {
        this.tipoMovimientoInventarioEnum = tipoMovimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(LocalDate fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}