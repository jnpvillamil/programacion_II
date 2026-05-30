package co.edu.uptc.gui.interfaces;
import co.edu.uptc.gui.modelo.Producto;
import java.util.List;

public interface IProductoBase {
    public void ejecutarOperacionProducto(Producto producto);
    public List<Producto> listarProductos();
}