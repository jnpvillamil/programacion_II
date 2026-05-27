package co.edu.uptc.tiendaminorista.negocio;

import java.util.List;
import co.edu.uptc.tiendaminorista.interfaces.IGestionProducto;
import co.edu.uptc.tiendaminorista.modelo.Producto;
import co.edu.uptc.tiendaminorista.persistencia.LocalProducto;

// capa de negocio para productos - coordina entre la GUI y la persistencia
// principio de responsabilidad unica: solo maneja logica de negocio de productos
public class GestionProducto {

    private IGestionProducto productos;

    public GestionProducto(IGestionProducto productos) {
        this.productos = productos;
    }

    // registrar producto nuevo - valida que el codigo no este vacio
    public void registrarProducto(Producto producto) {
        if (producto.getCodigo() == null || producto.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo del producto no puede estar vacio");
        }
        productos.guardar(producto);
    }

    // actualizar producto existente
    public void actualizarProducto(Producto producto) {
        productos.actualizar(producto);
    }

    // inactivar producto - no se borra de la BD para mantener trazabilidad
    public void desactivarProducto(String codigo) {
        productos.desactivar(codigo);
    }

    // volver a activar un producto inactivado
    public void activarProducto(String codigo) {
        productos.activar(codigo);
    }

    // listar todos los productos registrados
    public List<Producto> listarProductos() {
        return productos.listar();
    }

    // registrar movimiento de inventario (entrada positiva, salida negativa)
    public void registrarMovimientoInventario(String codigo, int cantidad) {
        productos.registrarMovimientoInventario(codigo, cantidad);
    }

    // buscar producto por codigo - solo disponible si la persistencia es LocalProducto
    public Producto buscarPorCodigo(String codigo) {
        if (productos instanceof LocalProducto) {
            return ((LocalProducto) productos).buscarPorCodigo(codigo);
        }
        // busqueda manual si no es LocalProducto
        for (Producto p : productos.listar()) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    // listar productos con stock por debajo del minimo
    public List<Producto> listarProductosBajoStock() {
        if (productos instanceof LocalProducto) {
            return ((LocalProducto) productos).listarBajoStockMinimo();
        }
        // fallback manual
        List<Producto> lista = new java.util.ArrayList<>();
        for (Producto p : productos.listar()) {
            if (p.getStockActual() < p.getStockMinimo() && p.isActivo()) {
                lista.add(p);
            }
        }
        return lista;
    }

    // calcular valor total del inventario (precio venta * stock)
    public double calcularValorInventario() {
        double total = 0;
        for (Producto p : productos.listar()) {
            if (p.isActivo()) {
                total += p.getPrecioVenta() * p.getStockActual();
            }
        }
        return total;
    }
}
