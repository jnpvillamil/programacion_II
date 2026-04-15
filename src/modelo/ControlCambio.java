package modelo;

import java.time.LocalDateTime;

public class ControlCambio {

    private String accion;
    private String codigo;
    private LocalDateTime fecha;

    public ControlCambio(String accion, String codigo) {
        this.accion = accion;
        this.codigo = codigo;
        this.fecha = LocalDateTime.now();
    }

    public String getAccion() { return accion; }
    public String getCodigo() { return codigo; }
    public LocalDateTime getFecha() { return fecha; }
}