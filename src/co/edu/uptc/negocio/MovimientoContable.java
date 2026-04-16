package co.edu.uptc.negocio;
import java.util.Date;
public class MovimientoContable {
    public String codigo, tipo, cuenta, descripcion;
    public double valor; public Date fecha;
    public MovimientoContable(String cod, String t, String cta, double val, String desc) {
        this.codigo = cod; this.tipo = t; this.cuenta = cta; this.valor = val; this.descripcion = desc; this.fecha = new Date();
    }
}