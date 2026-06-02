package co.edu.uptc.negocio;

import co.edu.uptc.dto.ProductoResumenDTO;
import co.edu.uptc.dto.ResultadoOperacion;
import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.persistencia.PersistenciaProducto;
import co.edu.uptc.utilidades.ValidadorEntradas;

import java.util.List;

public class GestionInventario {

    private Repositorio<Producto> repositorioProducto;

    public GestionInventario(Repositorio<Producto> repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }

    public GestionInventario() {
        this(new PersistenciaProducto());
    }

    public ResultadoOperacion registrarProducto(Producto producto) {
        ResultadoOperacion validacion = validarProducto(producto);
        if (!validacion.isExito()) {
            return validacion;
        }
        if (repositorioProducto.buscarPorId(producto.getCodigoProducto()) != null) {
            return ResultadoOperacion.error("Error: El código ya existe.");
        }
        if (producto.getStockMinimo() >= producto.getStockMaximo()) {
            return ResultadoOperacion.error("El stock mínimo debe ser menor que el stock máximo.");
        }
        repositorioProducto.guardar(producto);
        return ResultadoOperacion.exito("Producto guardado.");
    }

    public ResultadoOperacion actualizarProducto(Producto producto) {
        ResultadoOperacion validacion = validarProducto(producto);
        if (!validacion.isExito()) {
            return validacion;
        }
        if (repositorioProducto.buscarPorId(producto.getCodigoProducto()) == null) {
            return ResultadoOperacion.error("No se encontró el producto a actualizar.");
        }
        if (producto.getStockMinimo() >= producto.getStockMaximo()) {
            return ResultadoOperacion.error("El stock mínimo debe ser menor que el stock máximo.");
        }
        repositorioProducto.actualizar(producto);
        return ResultadoOperacion.exito("Producto actualizado.");
    }

    public ResultadoOperacion inactivarProducto(String codigo) {
        if (ValidadorEntradas.esVacio(codigo)) {
            return ResultadoOperacion.error("Ingrese el código del producto.");
        }
        Producto producto = repositorioProducto.buscarPorId(codigo.trim());
        if (producto == null) {
            return ResultadoOperacion.error("Producto no encontrado.");
        }
        producto.setActivo(false);
        repositorioProducto.actualizar(producto);
        return ResultadoOperacion.exito("Producto inactivado.");
    }

    public boolean registrarMovimientoInventario(String codigo, int cantidad, String tipo) {
        return ejecutarMovimientoInventario(codigo, cantidad, tipo);
    }

    public ResultadoOperacion registrarMovimientoInventario(String codigo, String cantidadTexto, String tipo) {
        if (ValidadorEntradas.esVacio(codigo) || ValidadorEntradas.esVacio(cantidadTexto)) {
            return ResultadoOperacion.error("Ingrese código y cantidad.");
        }
        if (!ValidadorEntradas.esNumero(cantidadTexto)) {
            return ResultadoOperacion.error("La cantidad debe ser numérica.");
        }
        int cantidad = (int) Double.parseDouble(cantidadTexto.trim());
        if (ejecutarMovimientoInventario(codigo.trim(), cantidad, tipo)) {
            return ResultadoOperacion.exito("Movimiento de inventario registrado.");
        }
        return ResultadoOperacion.error(
                "No se pudo registrar el movimiento. Verifique código, stock y cantidad.");
    }

    public boolean ejecutarMovimientoInventario(String codigo, int cantidad, String tipo) {
        if (cantidad <= 0) {
            return false;
        }

        Producto producto = repositorioProducto.buscarPorId(codigo);
        if (producto == null) {
            return false;
        }

        if ("ENTRADA".equalsIgnoreCase(tipo)) {
            producto.setStockActual(producto.getStockActual() + cantidad);
        } else if ("SALIDA".equalsIgnoreCase(tipo)) {
            if (producto.getStockActual() < cantidad) {
                return false;
            }
            producto.setStockActual(producto.getStockActual() - cantidad);
        } else {
            return false;
        }

        repositorioProducto.actualizar(producto);
        return true;
    }

    public boolean descontarStock(String codigoProducto, int cantidad) {
        Producto producto = repositorioProducto.buscarPorId(codigoProducto);
        if (producto != null && producto.isActivo()) {
            if (producto.getStockActual() >= cantidad) {
                producto.setStockActual(producto.getStockActual() - cantidad);
                repositorioProducto.actualizar(producto);
                return true;
            }
        }
        return false;
    }

    public Producto buscarProducto(String codigoProducto) {
        if (ValidadorEntradas.esVacio(codigoProducto)) {
            return null;
        }
        return repositorioProducto.buscarPorId(codigoProducto.trim());
    }

    public ResultadoOperacion buscarProductoValidado(String codigoProducto) {
        if (ValidadorEntradas.esVacio(codigoProducto)) {
            return ResultadoOperacion.error("Ingrese el código del producto.");
        }
        Producto producto = buscarProducto(codigoProducto);
        if (producto == null) {
            return ResultadoOperacion.error("No se encontró el código de producto.");
        }
        return ResultadoOperacion.exito("Producto encontrado.", producto);
    }

    public List<Producto> obtenerTodosLosProductos() {
        return repositorioProducto.listar();
    }

    public List<ProductoResumenDTO> listarResumen() {
        return repositorioProducto.listar().stream()
                .filter(Producto::isActivo)
                .map(p -> new ProductoResumenDTO(
                        p.getCodigoProducto(),
                        p.getNombreProducto(),
                        p.getCategoria() != null ? p.getCategoria().name() : "",
                        p.getPrecioVenta(),
                        p.getStockActual()))
                .toList();
    }

    private ResultadoOperacion validarProducto(Producto producto) {
        if (producto == null) {
            return ResultadoOperacion.error("Producto inválido.");
        }
        if (ValidadorEntradas.esVacio(producto.getCodigoProducto())) {
            return ResultadoOperacion.error("El código es obligatorio.");
        }
        if (ValidadorEntradas.esVacio(producto.getNombreProducto())) {
            return ResultadoOperacion.error("El nombre es obligatorio.");
        }
        if (producto.getPrecioVenta() <= 0) {
            return ResultadoOperacion.error("El precio de venta debe ser mayor a cero.");
        }
        if (producto.getPrecioCompra() < 0) {
            return ResultadoOperacion.error("El precio de compra no puede ser negativo.");
        }
        if (producto.getStockActual() < 0 || producto.getStockMinimo() < 0 || producto.getStockMaximo() < 0) {
            return ResultadoOperacion.error("Los valores de stock no pueden ser negativos.");
        }
        return ResultadoOperacion.exito("Validación correcta.");
    }
}
