package co.edu.uptc.negocio.dto;

public class clienteDto {

    public static int contadorCliente = 200;

    private int codigoCliente;
    private String nombre;
    private long telefono;
    private String direccion;

    public clienteDto() {
        this.codigoCliente = contadorCliente++;
    }

    public clienteDto(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getTelefono() {
        return telefono;
    }
    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "clienteDto [codigoCliente=" + codigoCliente + ", nombre=" + nombre
                + ", telefono=" + telefono + ", direccion=" + direccion + "]";
    }
}
