package modelo;

public class Proveedor {

    private String codigo;
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private String email;
    private boolean activo;

    public Proveedor(String codigo, String nombre, String nit,
                     String direccion, String telefono, String email) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.activo = true;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getNit() { return nit; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public boolean isActivo() { return activo; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setNit(String nit) { this.nit = nit; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }
    public void setActivo(boolean activo) { this.activo = activo; }
}