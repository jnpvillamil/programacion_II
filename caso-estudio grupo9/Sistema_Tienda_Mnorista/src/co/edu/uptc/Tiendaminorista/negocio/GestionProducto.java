package co.edu.uptc.Tiendaminorista.negocio;
import co.edu.uptc.Tiendaminorista.interfaces.IGestionProducto;
import co.edu.uptc.Tiendaminorista.modelo.Producto;

public class GestionProducto {
    private IGestionProducto productos;

    public GestionProducto(IGestionProducto productos) {
        this.productos = productos;
    }

    public void registrarProducto(Producto producto) {
        productos.guardar(producto);
    }

    public void actualizarProducto(Producto producto) {
        productos.actualizar(producto);
    }

    public void desactivarProducto(String codigo) {
        productos.desactivar(codigo);
    }

    public void activarProducto(String codigo) {
        productos.activar(codigo);
    }

    public java.util.List<Producto> listarProductos() {
        return productos.listar();
    }

    public void registrarMovimientoInventario(String codigo, int cantidad) {
        productos.registrarMovimientoInventario(codigo, cantidad);
    }
}