package co.edu.uptc.negocio;

public class Cliente extends Persona {
    private String tipoCliente;

    public Cliente(String identificacion, String nombre, String direccion, String telefono, String tipoCliente) {
        super(identificacion, nombre, direccion, telefono);
        this.tipoCliente = tipoCliente;
    }

    public String getTipoCliente() { return tipoCliente; }
}