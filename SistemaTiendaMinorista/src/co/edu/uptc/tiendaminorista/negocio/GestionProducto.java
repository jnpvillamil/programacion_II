package co.edu.uptc.tiendaminorista.negocio;

import java.util.List;
import co.edu.uptc.tiendaminorista.interfaces.IGestionProducto;
import co.edu.uptc.tiendaminorista.modelo.Producto;

public class GestionProducto {
    private IGestionProducto productos;

    public GestionProducto(IGestionProducto productos) {
        this.productos = productos;
    }

    public void registrarProducto(Producto producto) { productos.guardar(producto); }
    public void actualizarProducto(Producto producto) { productos.actualizar(producto); }
    public void desactivarProducto(String codigo) { productos.desactivar(codigo); }
    public void activarProducto(String codigo) { productos.activar(codigo); }
    public List<Producto> listarProductos() { return productos.listar(); }
    public void registrarMovimientoInventario(String codigo, int cantidad) {
        productos.registrarMovimientoInventario(codigo, cantidad);
    }
}
