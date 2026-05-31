package co.uptc.edu.co.modelo;

public class ResumenProductoDTO {
    private final String codigoProducto;
    private final String nombreProducto;
    private final int cantidadVendida;

    public ResumenProductoDTO(String codigoProducto, String nombreProducto, int cantidadVendida) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadVendida = cantidadVendida;
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
}
