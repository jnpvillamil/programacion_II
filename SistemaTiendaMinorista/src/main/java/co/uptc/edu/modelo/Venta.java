package co.uptc.edu.modelo;

public class Venta {

    private String codigoProducto;
    private String nombreProducto;
    private int cantidadVendida;
    private double precio;

    public Venta(String codigoProducto,
                 String nombreProducto,
                 int cantidadVendida,
                 double precio) {

        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadVendida = cantidadVendida;
        this.precio = precio;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public double getPrecio() {
        return precio;
    }
}