package co.edu.uptc.modelo;


import co.edu.uptc.enums.TipoMovimientoContable;  
import co.edu.uptc.enums.CuentaContable;          
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AsientoContable {
    private String codigoAsiento;
    private LocalDateTime fecha;
    private String descripcion;
    private String referencia;
    private List<MovimientoContable> movimientos;
    private String usuarioRegistro;
    
    public AsientoContable(String codigoAsiento, String descripcion, 
                           String referencia, String usuarioRegistro) {
        this.codigoAsiento = codigoAsiento;
        this.fecha = LocalDateTime.now();
        this.descripcion = descripcion;
        this.referencia = referencia;
        this.movimientos = new ArrayList<>();
        this.usuarioRegistro = usuarioRegistro;
    }
    
    public void agregarMovimiento(TipoMovimientoContable tipo, CuentaContable cuenta, 
                                   double valor, String detalle) {
        String codigoTransaccion = codigoAsiento + "-" + (movimientos.size() + 1);
        MovimientoContable movimiento = new MovimientoContable(
            codigoTransaccion, tipo, cuenta, valor, detalle, referencia, usuarioRegistro
        );
        movimientos.add(movimiento);
    }
    
   
    public boolean estaBalanceado() {
        double sumaDebitos = movimientos.stream()
            .filter(m -> m.getTipoMovimiento() == TipoMovimientoContable.INGRESO)
            .mapToDouble(MovimientoContable::getValor).sum();
        
        double sumaCreditos = movimientos.stream()
            .filter(m -> m.getTipoMovimiento() == TipoMovimientoContable.EGRESO)
            .mapToDouble(MovimientoContable::getValor).sum();
        
        return Math.abs(sumaDebitos - sumaCreditos) < 0.01;
    }
    
    
    public String getCodigoAsiento() { return codigoAsiento; }
    public LocalDateTime getFecha() { return fecha; }
    public String getDescripcion() { return descripcion; }
    public String getReferencia() { return referencia; }
    public List<MovimientoContable> getMovimientos() { return movimientos; }
    
    public String getResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("ASIENTO: ").append(codigoAsiento).append("\n");
        sb.append("Fecha: ").append(fecha).append("\n");
        sb.append("Descripción: ").append(descripcion).append("\n");
        sb.append("Referencia: ").append(referencia).append("\n");
        sb.append("Movimientos:\n");
        for(MovimientoContable m : movimientos) {
            sb.append("  - ").append(m.toString()).append("\n");
        }
        sb.append("Balanceado: ").append(estaBalanceado() ? "Sí" : "No").append("\n");
        return sb.toString();
    }
}