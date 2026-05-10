package co.edu.uptc.modelo;

import co.edu.uptc.enums.CuentaContable;
import co.edu.uptc.enums.TipoMovimientoContable;
import java.time.LocalDateTime;

public class MovimientoContable {
    private String codigoTransaccion;
    private LocalDateTime fecha;
    private TipoMovimientoContable tipoMovimiento;
    private CuentaContable cuenta;
    private double valor;
    private String descripcion;
    private String referencia;  
    private String usuarioRegistro;
    
    public MovimientoContable(String codigoTransaccion, TipoMovimientoContable tipoMovimiento,
                              CuentaContable cuenta, double valor, String descripcion, 
                              String referencia, String usuarioRegistro) {
        this.codigoTransaccion = codigoTransaccion;
        this.fecha = LocalDateTime.now();
        this.tipoMovimiento = tipoMovimiento;
        this.cuenta = cuenta;
        this.valor = valor;
        this.descripcion = descripcion;
        this.referencia = referencia;
        this.usuarioRegistro = usuarioRegistro;
    }
    
    
    public String getCodigoTransaccion() { return codigoTransaccion; }
    public LocalDateTime getFecha() { return fecha; }
    public TipoMovimientoContable getTipoMovimiento() { return tipoMovimiento; }
    public CuentaContable getCuenta() { return cuenta; }
    public double getValor() { return valor; }
    public String getDescripcion() { return descripcion; }
    public String getReferencia() { return referencia; }
    
    @Override
    public String toString() {
        return String.format("%s | %s | %s | $%,.2f | %s", 
            fecha.toLocalDate(), tipoMovimiento.getDescripcion(), 
            cuenta.getNombre(), valor, descripcion);
    }
}