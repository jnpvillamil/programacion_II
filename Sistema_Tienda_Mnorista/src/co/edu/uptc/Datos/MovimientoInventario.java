package co.edu.uptc.Datos;

public class MovimientoInventario {

    private String codigoProducto;
    private String tipoMovimiento; 
    private int cantidad;
    private String fecha;
    private String descripcion;


    public MovimientoInventario() {
    this.codigoProducto = "";
    this.tipoMovimiento = "";   
    this.cantidad = 0;
    this.fecha = "";
    this.descripcion = "";
    }

    public MovimientoInventario(String codigoProducto, String tipoMovimiento,
                                int cantidad, String fecha, String descripcion) {
        this.codigoProducto = codigoProducto;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
