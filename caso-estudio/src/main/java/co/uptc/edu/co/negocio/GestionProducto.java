package co.uptc.edu.co.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.interfaces.IGestionProducto;
import co.uptc.edu.co.modelo.MovimientoInventario;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.enums.EstadoEnum;
import co.uptc.edu.co.modelo.enums.TipoMovimientoInventarioEnum;

public class GestionProducto implements IGestionProducto {

    private List<Producto> productos;
    private List<MovimientoInventario> movimientos;

    public GestionProducto() {
        productos = new ArrayList<>();
        movimientos = new ArrayList<>();
    }

    @Override
    public Producto buscarProductoPorCodigo(String codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigoProducto().equalsIgnoreCase(codigo)) {
                return producto;
            }
        }
        return null;
    }

    @Override
    public List<Producto> obtenerProductos() {
        return new ArrayList<>(productos);
    }

    @Override
    public List<MovimientoInventario> obtenerMovimientos() {
        return new ArrayList<>(movimientos);
    }

    @Override
    public void registrarProducto(Producto producto) throws Exception {
        validarProducto(producto);

        if (buscarProductoPorCodigo(producto.getCodigoProducto()) != null) {
            throw new Exception("Ya existe un producto con ese código.");
        }

        producto.setEstado(EstadoEnum.ACTIVO);
        productos.add(producto);
    }

    @Override
    public void actualizarProducto(Producto productoActualizado) throws Exception {
        validarProducto(productoActualizado);

        Producto productoExistente = buscarProductoPorCodigo(productoActualizado.getCodigoProducto());

        if (productoExistente == null) {
            throw new Exception("No se encontró el producto a actualizar.");
        }

        productoExistente.setNombreProducto(productoActualizado.getNombreProducto());
        productoExistente.setCategoria(productoActualizado.getCategoria());
        productoExistente.setPrecioCompra(productoActualizado.getPrecioCompra());
        productoExistente.setPrecioVenta(productoActualizado.getPrecioVenta());
        productoExistente.setStockActual(productoActualizado.getStockActual());
        productoExistente.setStockMinimo(productoActualizado.getStockMinimo());
        productoExistente.setStockMaximo(productoActualizado.getStockMaximo());
    }

    @Override
    public void cambiarEstadoProducto(String codigo) throws Exception {
        Producto producto = buscarProductoPorCodigo(codigo);

        if (producto == null) {
            throw new Exception("No se encontró el producto.");
        }

        if (producto.estaActivo()) {
            producto.setEstado(EstadoEnum.INACTIVO);
        } else {
            producto.setEstado(EstadoEnum.ACTIVO);
        }
    }

    private void validarProducto(Producto producto) throws Exception {
        if (producto == null) {
            throw new Exception("El producto no puede ser nulo.");
        }

        if (producto.getCodigoProducto() == null || producto.getCodigoProducto().trim().isEmpty()) {
            throw new Exception("El código del producto es obligatorio.");
        }

        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
            throw new Exception("El nombre del producto es obligatorio.");
        }

        if (producto.getCategoria() == null) {
            throw new Exception("La categoría es obligatoria.");
        }

        if (!producto.getCodigoProducto().matches("[A-Z0-9]{3,10}")) {
            throw new Exception("El código debe ser alfanumérico, en mayúsculas, y tener entre 3 y 10 caracteres.");
        }

        if (!producto.getNombreProducto().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            throw new Exception("El nombre solo debe contener letras y espacios.");
        }

        if (producto.getPrecioCompra() < 0) {
            throw new Exception("El precio de compra no puede ser negativo.");
        }

        if (producto.getPrecioVenta() < 0) {
            throw new Exception("El precio de venta no puede ser negativo.");
        }

        if (producto.getPrecioVenta() < producto.getPrecioCompra()) {
            throw new Exception("El precio de venta no puede ser menor al precio de compra.");
        }

        if (producto.getStockActual() < 0) {
            throw new Exception("El stock actual no puede ser negativo.");
        }

        if (producto.getStockMinimo() < 0) {
            throw new Exception("El stock mínimo no puede ser negativo.");
        }

        if (producto.getStockMaximo() < 0) {
            throw new Exception("El stock máximo no puede ser negativo.");
        }

        if (producto.getStockMinimo() > producto.getStockActual()) {
            throw new Exception("El stock mínimo no puede ser mayor que el stock actual.");
        }

        if (producto.getStockActual() > producto.getStockMaximo()) {
            throw new Exception("El stock actual no puede ser mayor que el stock máximo.");
        }

        if (producto.getStockMinimo() > producto.getStockMaximo()) {
            throw new Exception("El stock mínimo no puede ser mayor que el stock máximo.");
        }
    }

    @Override
    public void actualizarPrecioProducto(String codigo, Double nuevoPrecioCompra, Double nuevoPrecioVenta) throws Exception {
        Producto producto = buscarProductoPorCodigo(codigo);

        if (producto == null) {
            throw new Exception("No se encontró el producto.");
        }

        if (nuevoPrecioCompra == null && nuevoPrecioVenta == null) {
            throw new Exception("Debe ingresar al menos un precio para actualizar.");
        }

        if (nuevoPrecioCompra != null) {
            if (nuevoPrecioCompra < 0) {
                throw new Exception("El precio de compra no puede ser negativo.");
            }
            producto.setPrecioCompra(nuevoPrecioCompra);
        }

        if (nuevoPrecioVenta != null) {
            if (nuevoPrecioVenta < 0) {
                throw new Exception("El precio de venta no puede ser negativo.");
            }
            producto.setPrecioVenta(nuevoPrecioVenta);
        }

        if (producto.getPrecioVenta() < producto.getPrecioCompra()) {
            throw new Exception("El precio de venta no puede ser menor al precio de compra.");
        }
    }

    @Override
    public void registrarMovimientoInventario(String codigo, String tipoMovimiento, int cantidad) throws Exception {
        Producto producto = buscarProductoPorCodigo(codigo);

        if (producto == null) {
            throw new Exception("No se encontró el producto.");
        }

        if (cantidad <= 0) {
            throw new Exception("La cantidad debe ser mayor que cero.");
        }

        TipoMovimientoInventarioEnum movimientoEnum;
        try {
            movimientoEnum = TipoMovimientoInventarioEnum.valueOf(tipoMovimiento.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new Exception("Tipo de movimiento no válido.");
        }

        if (movimientoEnum == TipoMovimientoInventarioEnum.ENTRADA) {
            int nuevoStock = producto.getStockActual() + cantidad;

            if (nuevoStock > producto.getStockMaximo()) {
                throw new Exception("La entrada supera el stock máximo permitido.");
            }

            producto.setStockActual(nuevoStock);

        } else if (movimientoEnum == TipoMovimientoInventarioEnum.SALIDA) {
            if (cantidad > producto.getStockActual()) {
                throw new Exception("No hay stock suficiente para realizar la salida.");
            }

            producto.setStockActual(producto.getStockActual() - cantidad);
        }

      /*  String codigoMovimiento = "MOV" + (movimientos.size() + 1);

        MovimientoInventario movimiento = new MovimientoInventario(
                codigoMovimiento,
                codigo,
                movimientoEnum.name(),
                cantidad,
                LocalDate.now(),
                "Movimiento registrado"
        );

        movimientos.add(movimiento);*/
        MovimientoInventario movimiento = new MovimientoInventario(
                codigo,
                movimientoEnum,
                cantidad,
                LocalDate.now(),
                "Movimiento registrado"
            );

            movimientos.add(movimiento);
    }
}