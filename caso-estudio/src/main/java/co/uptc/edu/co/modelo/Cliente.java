package co.uptc.edu.co.modelo;

import co.uptc.edu.co.modelo.enums.EstadoEnum;
import co.uptc.edu.co.modelo.enums.TipoClienteEnum;
import co.uptc.edu.co.modelo.enums.TipoDocEnum;

public class Cliente {

    private String codigo;
    private String nombre;
    private TipoDocEnum tipoIdentificacion;
    private String numeroIdentificacion;
    private String direccion;
    private String telefono;
    private TipoClienteEnum tipoCliente;
    private EstadoEnum estado;

    public Cliente() {
    }

    public Cliente(String codigo, String nombre, TipoDocEnum tipoIdentificacion,
                   String numeroIdentificacion, String direccion, String telefono,
                   TipoClienteEnum tipoCliente, EstadoEnum estado) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.tipoCliente = tipoCliente;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoDocEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoDocEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TipoClienteEnum getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoClienteEnum tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public EstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    public boolean estaActivo() {
        return estado == EstadoEnum.ACTIVO;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}