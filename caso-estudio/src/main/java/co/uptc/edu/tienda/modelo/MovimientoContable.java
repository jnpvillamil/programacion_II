package co.uptc.edu.tienda.modelo;

import co.uptc.edu.tienda.enums.TipoMovimientoContable;

public class MovimientoContable {

    private int codigoTransaccion;
    private String fecha;
    private TipoMovimientoContable tipoMovimiento; // INGRESO o EGRESO
    private String cuentaContable;                 // "Caja", "IVA generado", "Inventario"...
    private double valor;
    private String descripcion;
    private String referenciaFactura;              // factura de venta o compra que lo originó

    public MovimientoContable() {
    }

    public MovimientoContable(
            String fecha,
            TipoMovimientoContable tipoMovimiento,
            String cuentaContable,
            double valor,
            String descripcion,
            String referenciaFactura) {
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.cuentaContable = cuentaContable;
        this.valor = valor;
        this.descripcion = descripcion;
        this.referenciaFactura = referenciaFactura;
    }

    public int getCodigoTransaccion() { return codigoTransaccion; }
    public void setCodigoTransaccion(int codigoTransaccion) { this.codigoTransaccion = codigoTransaccion; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public TipoMovimientoContable getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(TipoMovimientoContable tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }

    public String getCuentaContable() { return cuentaContable; }
    public void setCuentaContable(String cuentaContable) { this.cuentaContable = cuentaContable; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getReferenciaFactura() { return referenciaFactura; }
    public void setReferenciaFactura(String referenciaFactura) { this.referenciaFactura = referenciaFactura; }
}