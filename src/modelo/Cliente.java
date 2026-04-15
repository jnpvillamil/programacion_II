package modelo;

public class Cliente {

    private String codigo;
    private String nombre;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String direccion;
    private String telefono;
    private String tipoCliente;
    private boolean activo;

    public Cliente(String codigo, String nombre, String tipoIdentificacion,
                   String numeroIdentificacion, String direccion,
                   String telefono, String tipoCliente) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.tipoCliente = tipoCliente;
        this.activo = true;
    }

   

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getTipoIdentificacion() { return tipoIdentificacion; }
    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getTipoCliente() { return tipoCliente; }
    public boolean isActivo() { return activo; }

  

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTipoIdentificacion(String tipoIdentificacion) { this.tipoIdentificacion = tipoIdentificacion; }
    public void setNumeroIdentificacion(String numeroIdentificacion) { this.numeroIdentificacion = numeroIdentificacion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setTipoCliente(String tipoCliente) { this.tipoCliente = tipoCliente; }
    public void setActivo(boolean activo) { this.activo = activo; }
}