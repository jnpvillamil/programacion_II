package co.edu.uptc.controlador;
import co.edu.uptc.dto.ProductoResumenDTO;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.negocio.GestionProducto;
import java.util.ArrayList;
import java.util.List;
public class ControladorProducto {
   private GestionProducto gestionProducto;
   public ControladorProducto(GestionProducto gestionProducto) {
       this.gestionProducto = gestionProducto;
   }
   public String registrarProducto(Producto producto) {
       try {
           gestionProducto.registrarProducto(producto);
           return "Producto registrado con éxito.";
       } catch (Exception e) {
           return "Error: " + e.getMessage();
       }
   }
   public String modificarProducto(Producto producto) {
       try {
           if (gestionProducto.actualizarProducto(producto)) {
               return "Producto actualizado correctamente.";
           }
           return "Error: Producto no encontrado.";
       } catch (Exception e) {
           return "Error: " + e.getMessage();
       }
   }
   public String inactivarProducto(String codigoInterno) {
       if (gestionProducto.inactivarProducto(codigoInterno)) {
           return "Producto inactivado correctamente.";
       }
       return "Error: Producto no encontrado.";
   }
   public Producto buscarProducto(String codigoInterno) {
       return gestionProducto.buscarProducto(codigoInterno);
   }
   public List<ProductoResumenDTO> obtenerListadoResumen() {
       List<ProductoResumenDTO> resumen = new ArrayList<>();
       for (Producto p : gestionProducto.listarTodos()) {
           if (p.isActivo()) { // Solo mostramos los activos en la tabla
               String alerta = p.getStockActual() <= p.getStockMinimo() ? "¡BAJO STOCK!" : "Normal";
               resumen.add(new ProductoResumenDTO(p.getCodigoInterno(), p.getNombreProducto(),
                           p.getCategoria().name(), p.getPrecioVenta(), p.getStockActual(), alerta));
           }
       }
       return resumen;
   }
}
