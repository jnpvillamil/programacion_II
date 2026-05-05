package co.edu.uptc.negocio;

public class Proveedor extends Persona {
    private String email;

    public Proveedor(String identificacion, String nombre, String direccion, String telefono, String email) {
        super(identificacion, nombre, direccion, telefono);
        this.email = email;
    }

    public String getEmail() { return email; }
}