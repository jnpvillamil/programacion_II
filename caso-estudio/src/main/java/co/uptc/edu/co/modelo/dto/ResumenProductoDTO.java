package co.uptc.edu.co.modelo.dto;

public class ResumenProductoDTO {
    private final String codigoProducto;
    private final String nombreProducto;
    private final int cantidadVendida;
    private final double totalVendido;

    public ResumenProductoDTO(String codigoProducto, String nombreProducto, int cantidadVendida, double totalVendido) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadVendida = cantidadVendida;
        this.totalVendido = totalVendido;
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

    public double getTotalVendido() {
        return totalVendido;
    }
}
