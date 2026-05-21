package co.edu.uptc.modelo;

import co.edu.uptc.enums.TipoCliente;
import co.edu.uptc.enums.TipoIdentificacion;

public class Cliente extends Persona {
    private String codigoCliente;
    private TipoIdentificacion tipoIdentificacion;
    private TipoCliente tipoCliente;
    private boolean activo;

    public Cliente(String nombre, String apellido, String identificacion, String direccion, String telefono, 
                   String codigoCliente, TipoIdentificacion tipoIdentificacion, TipoCliente tipoCliente) {
        super(nombre, apellido, identificacion, direccion, telefono);
        this.codigoCliente = codigoCliente;
        this.tipoIdentificacion = tipoIdentificacion;
        this.tipoCliente = tipoCliente;
        this.activo = true; 
    }

    public String getCodigoCliente() { return codigoCliente; }
    public void setCodigoCliente(String codigoCliente) { this.codigoCliente = codigoCliente; }

    public TipoIdentificacion getTipoIdentificacion() { return tipoIdentificacion; }
    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) { this.tipoIdentificacion = tipoIdentificacion; }

    public TipoCliente getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(TipoCliente tipoCliente) { this.tipoCliente = tipoCliente; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}