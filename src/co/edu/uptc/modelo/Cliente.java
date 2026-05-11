package co.edu.uptc.modelo;

import co.edu.uptc.enums.TipoCliente;
import co.edu.uptc.enums.TipoIdentificacion;

public class Cliente extends Persona {
    private String codigoCliente; 
    private TipoIdentificacion tipoldentificacion; 
    private TipoCliente tipoCliente; 
    private boolean activo;

    public Cliente(String nombre, String apellido, String identificacion, String direccion, String telefono, String codigoCliente, TipoIdentificacion tipoldentificacion, TipoCliente tipoCliente, boolean activo) {
        super(nombre, apellido, identificacion, direccion, telefono);
        this.codigoCliente = codigoCliente;
        this.tipoldentificacion = tipoldentificacion;
        this.tipoCliente = tipoCliente;
        this.activo = activo;
    }

    public String getCodigoCliente() { return codigoCliente; }
    public void setCodigoCliente(String codigoCliente) { this.codigoCliente = codigoCliente; }

    public TipoIdentificacion getTipoldentificacion() { return tipoldentificacion; }
    public void setTipoldentificacion(TipoIdentificacion tipoldentificacion) { this.tipoldentificacion = tipoldentificacion; }

    public TipoCliente getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(TipoCliente tipoCliente) { this.tipoCliente = tipoCliente; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}