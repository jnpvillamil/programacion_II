package co.edu.uptc.negocio;
import co.edu.uptc.enums.TipoMovimiento;
import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Venta;
public class GestionVenta {
   private Repositorio<Venta> persistenciaVenta;
   private GestionProducto gestionProducto;
   private GestionContable gestionContable;
   public GestionVenta(Repositorio<Venta> persistenciaVenta, GestionProducto gestionProducto, GestionContable gestionContable) {
       this.persistenciaVenta = persistenciaVenta;
       this.setGestionProducto(gestionProducto);
       this.gestionContable = gestionContable;
   }
  
	public GestionProducto getGestionProducto() {
		return gestionProducto;
	}
	public void setGestionProducto(GestionProducto gestionProducto) {
		this.gestionProducto = gestionProducto;
	}
   public boolean realizarVenta(Venta venta) {
       for (DetalleVenta detalle : venta.getListaDetalles()) {
           if (detalle.getProducto().getStockActual() < detalle.getCantidad()) {
               return false;
           }
       }
       for (DetalleVenta detalle : venta.getListaDetalles()) {
           actualizarStock(detalle.getProducto(), detalle.getCantidad());
       }
       this.persistenciaVenta.guardar(venta);
      
       double iva = calcularIVA(venta.getTotal());
       double subtotal = venta.getTotal() - iva;
       this.gestionContable.registrarPartidaDoble(venta.getTotal(), TipoMovimiento.INGRESO, "Caja/Bancos", "Venta Fac: " + venta.getNumeroFactura());
       this.gestionContable.registrarPartidaDoble(subtotal, TipoMovimiento.INGRESO, "Ingresos por ventas", "Ingreso Fac: " + venta.getNumeroFactura());
       this.gestionContable.registrarPartidaDoble(iva, TipoMovimiento.INGRESO, "IVA Generado", "IVA Fac: " + venta.getNumeroFactura());
       return true;
   }
   public double calcularIVA(double total) {
       return total * 0.19;
   }
   public void actualizarStock(Producto producto, int cantidadVendida) {
       int nuevoStock = producto.getStockActual() - cantidadVendida;
       producto.setStockActual(nuevoStock);
       // Envolvemos en try-catch igual que en las compras
       try {
           this.gestionProducto.actualizarProducto(producto);
       } catch (Exception e) {
           // TODO: Manejar el envío de este mensaje a la GUI.
           System.err.println("LOG: Error al descontar el stock en la venta: " + e.getMessage());
       }
   }
}
