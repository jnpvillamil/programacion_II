package co.edu.uptc.negocio;
import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Producto;
import java.util.List;
public class GestionProducto {
   private Repositorio<Producto> persistenciaProducto;
   public GestionProducto(Repositorio<Producto> persistenciaProducto) {
       this.persistenciaProducto = persistenciaProducto;
   }
   public boolean registrarProducto(Producto producto) throws Exception {
       if (this.persistenciaProducto.buscarPorId(producto.getCodigoInterno()) != null) {
           throw new Exception("El código del producto ya existe.");
       }
       if (producto.getStockMinimo() >= producto.getStockMaximo()) {
           throw new Exception("El stock mínimo no puede ser mayor o igual al stock máximo.");
       }
       this.persistenciaProducto.guardar(producto);
       return true;
   }
   public boolean actualizarProducto(Producto productoModificado) throws Exception {
       if (productoModificado.getStockMinimo() >= productoModificado.getStockMaximo()) {
           throw new Exception("El stock mínimo no puede ser mayor o igual al stock máximo.");
       }
       Producto existente = this.persistenciaProducto.buscarPorId(productoModificado.getCodigoInterno());
       if (existente != null) {
           this.persistenciaProducto.eliminar(existente.getCodigoInterno());
           this.persistenciaProducto.guardar(productoModificado);
           return true;
       }
       return false;
   }
   public boolean inactivarProducto(String codigoInterno) {
       Producto producto = this.persistenciaProducto.buscarPorId(codigoInterno);
       if (producto != null) {
           producto.setActivo(false);
           try {
               actualizarProducto(producto);
           } catch (Exception e) {
               return false;
           }
           return true;
       }
       return false;
   }
   public Producto buscarProducto(String codigoInterno) {
       return this.persistenciaProducto.buscarPorId(codigoInterno);
   }
   public List<Producto> listarTodos() {
       return this.persistenciaProducto.listar();
   }
}
