package co.edu.uptc.negocio;

import co.edu.uptc.dto.CompraDTO;
import co.edu.uptc.enums.TipoMovimiento;
import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleCompra;
import co.edu.uptc.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

public class GestionCompra {
    private static final double TASA_IVA = 0.19;

    private Repositorio<Compra> persistenciaCompra;
    private GestionProducto gestionProducto;
    private GestionContable gestionContable;

    public GestionCompra(Repositorio<Compra> persistenciaCompra, GestionProducto gestionProducto,
                         GestionContable gestionContable) {
        this.persistenciaCompra = persistenciaCompra;
        this.gestionProducto = gestionProducto;
        this.gestionContable = gestionContable;
    }

    public void registrarCompra(Compra compra) throws Exception {
        if (compra.getNumeroFacturaProveedor() == null || compra.getNumeroFacturaProveedor().trim().isEmpty()) {
            throw new Exception("El número de factura del proveedor es obligatorio.");
        }
        if (compra.getProveedor() == null) {
            throw new Exception("Debe seleccionar un proveedor.");
        }
        if (compra.getListaDetalles() == null || compra.getListaDetalles().isEmpty()) {
            throw new Exception("Debe agregar al menos un producto a la compra.");
        }
        if (this.persistenciaCompra.buscarPorId(compra.getNumeroFacturaProveedor()) != null) {
            throw new Exception("Ya existe una compra registrada con ese número de factura.");
        }

        // Calcular subtotal, IVA y total, y almacenarlos en el objeto
        double subtotal = calcularSubtotal(compra);
        double iva = subtotal * TASA_IVA;
        double total = subtotal + iva;

        compra.setSubtotal(subtotal);
        compra.setIva(iva);
        compra.setTotal(total);

        // Incrementar stock de cada producto
        for (DetalleCompra detalle : compra.getListaDetalles()) {
            incrementarStock(detalle.getProducto(), detalle.getCantidad());
        }

        this.persistenciaCompra.guardar(compra);

        // Registrar movimientos contables (partida doble)
        this.gestionContable.registrarPartidaDoble(subtotal, TipoMovimiento.EGRESO,
                "Inventario", "Compra Proveedor Fac: " + compra.getNumeroFacturaProveedor());
        this.gestionContable.registrarPartidaDoble(iva, TipoMovimiento.EGRESO,
                "IVA Descontable", "IVA Compra Fac: " + compra.getNumeroFacturaProveedor());
        this.gestionContable.registrarPartidaDoble(total, TipoMovimiento.EGRESO,
                "Caja/Bancos", "Pago Proveedor Fac: " + compra.getNumeroFacturaProveedor());
    }

    public void incrementarStock(Producto producto, int cantidadComprada) {
        int nuevoStock = producto.getStockActual() + cantidadComprada;
        producto.setStockActual(nuevoStock);
        try {
            this.gestionProducto.actualizarProducto(producto);
        } catch (Exception e) {
            System.err.println("Error al actualizar el stock del producto tras la compra: " + e.getMessage());
        }
    }

    public Compra buscarCompra(String numeroFactura) {
        return this.persistenciaCompra.buscarPorId(numeroFactura);
    }

    public List<CompraDTO> obtenerListadoResumen() {
        List<Compra> compras = this.persistenciaCompra.listar();
        List<CompraDTO> resumen = new ArrayList<>();
        for (Compra c : compras) {
        	String nombreProveedor = c.getProveedor() != null ? c.getProveedor().getRazonSocial() : "N/A";
            resumen.add(new CompraDTO(
                    c.getNumeroFacturaProveedor(),
                    c.getFecha(),
                    nombreProveedor,
                    c.getSubtotal(),
                    c.getIva(),
                    c.getTotal()
            ));
        }
        return resumen;
    }

    private double calcularSubtotal(Compra compra) {
        double subtotal = 0;
        for (DetalleCompra detalle : compra.getListaDetalles()) {
            subtotal += detalle.getSubtotal();
        }
        return subtotal;
    }
}