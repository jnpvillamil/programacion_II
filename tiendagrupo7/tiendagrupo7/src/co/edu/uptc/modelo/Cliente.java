package co.edu.uptc.modelo;

public class Cliente extends Persona {
    private String codigoCliente;
    private String tipoIdentificacion;
    private String tipoCliente;
    private boolean activo;

    public Cliente(String nombre, String identificacion, String direccion, String telefono, String codigoCliente, String tipoIdentificacion, String tipoCliente, boolean activo) {
        super(nombre, identificacion, direccion, telefono);
        this.codigoCliente = codigoCliente;
        this.tipoIdentificacion = tipoIdentificacion;
        this.tipoCliente = tipoCliente;
        this.activo = activo;
    }

    public Cliente() {
        super();
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}