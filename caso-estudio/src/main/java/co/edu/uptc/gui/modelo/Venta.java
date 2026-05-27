package co.edu.uptc.gui.modelo;

public class Venta {
    private String codigoFactura;
    private String codigoCliente;
    private String codigoProducto;
    private int cantidad;
    private double total;

    public Venta() {}

    public Venta(String codigoFactura, String codigoCliente, String codigoProducto, int cantidad, double total) {
        this.codigoFactura = codigoFactura;
        this.codigoCliente = codigoCliente;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.total = total;
    }

    public String getCodigoFactura() { return codigoFactura; }
    public void setCodigoFactura(String codigoFactura) { this.codigoFactura = codigoFactura; }

    public String getCodigoCliente() { return codigoCliente; }
    public void setCodigoCliente(String codigoCliente) { this.codigoCliente = codigoCliente; }

    public String getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(String codigoProducto) { this.codigoProducto = codigoProducto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public void registrarVenta() {
        co.edu.uptc.dao.VendedorDao dao = new co.edu.uptc.dao.VendedorDao();
        dao.registrarVentaBD(this);

        System.out.println("Venta " + codigoFactura + " registrada localmente en consola.");
    }
}