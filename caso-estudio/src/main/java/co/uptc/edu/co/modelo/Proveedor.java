package co.uptc.edu.co.modelo;

import co.uptc.edu.co.modelo.enums.EstadoEnum;

public class Proveedor {

    private String codigoProveedor;
    private String razonSocial;
    private String nit;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private EstadoEnum estado;

    public Proveedor() {
    }

    public Proveedor(String codigoProveedor, String razonSocial, String nit,
            String direccion, String telefono, String correoElectronico, EstadoEnum estado) {
        this.codigoProveedor = codigoProveedor;
        this.razonSocial = razonSocial;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.estado = estado;
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
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

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
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
        return codigoProveedor + " - " + razonSocial;
    }
}