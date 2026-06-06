package co.uptc.edu.tienda.modelo;

public class ProductoMasVendido {
    private int codigo;
    private String nombre;
    private int cantidadVendida;

    public ProductoMasVendido(int codigo, String nombre, int cantidadVendida) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidadVendida = cantidadVendida;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCantidadVendida() { return cantidadVendida; }
    public void setCantidadVendida(int cantidadVendida) { this.cantidadVendida = cantidadVendida; }
}