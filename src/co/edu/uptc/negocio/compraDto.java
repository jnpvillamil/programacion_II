package co.edu.uptc.negocio;

public class compraDto {

    private String numeroFacturaProveedor;
    private String fechaCompra;
    private String codigoProveedor;
    private double totalCompra;

    public String getNumeroFacturaProveedor() {
        return numeroFacturaProveedor;
    }

    public void setNumeroFacturaProveedor(String numeroFacturaProveedor) {
        this.numeroFacturaProveedor = numeroFacturaProveedor;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    @Override
    public String toString() {
        return "compraDto [numeroFacturaProveedor=" + numeroFacturaProveedor
                + ", fechaCompra=" + fechaCompra
                + ", codigoProveedor=" + codigoProveedor
                + ", totalCompra=" + totalCompra + "]";
    }
}