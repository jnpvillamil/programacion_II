package co.edu.uptc.dto;
public class ProductoResumenDTO {
   private String codigo;
   private String nombre;
   private String categoria;
   private double precioVenta;
   private int stockActual;
   private String alertaMinima;
   public ProductoResumenDTO(String codigo, String nombre, String categoria, double precioVenta, int stockActual, String alertaMinima) {
       this.codigo = codigo;
       this.nombre = nombre;
       this.categoria = categoria;
       this.precioVenta = precioVenta;
       this.stockActual = stockActual;
       this.alertaMinima = alertaMinima;
   }
   public String getCodigo() { return codigo; }
   public void setCodigo(String codigo) { this.codigo = codigo; }
   public String getNombre() { return nombre; }
   public void setNombre(String nombre) { this.nombre = nombre; }
   public String getCategoria() { return categoria; }
   public void setCategoria(String categoria) { this.categoria = categoria; }
   public double getPrecioVenta() { return precioVenta; }
   public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }
   public int getStockActual() { return stockActual; }
   public void setStockActual(int stockActual) { this.stockActual = stockActual; }
   public String getAlertaMinima() { return alertaMinima; }
   public void setAlertaMinima(String alertaMinima) { this.alertaMinima = alertaMinima; }
}
