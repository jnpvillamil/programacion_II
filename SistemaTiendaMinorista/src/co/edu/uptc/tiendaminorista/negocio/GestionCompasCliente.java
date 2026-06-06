package co.edu.uptc.tiendaminorista.negocio;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import co.edu.uptc.tiendaminorista.modelo.Cliente;
import co.edu.uptc.tiendaminorista.modelo.CompasCliente;
import co.edu.uptc.tiendaminorista.modelo.Producto;
import co.edu.uptc.tiendaminorista.persistencia.LocalCompraCliente;

public class GestionCompasCliente {

    private LocalCompraCliente localCompraCliente;

    public GestionCompasCliente() {
        this.localCompraCliente = new LocalCompraCliente();
    }

    public void registrarCompra(Cliente cliente, Producto producto, int cantidad) throws Exception {
        
        if (cliente == null) {
            throw new Exception("Debe seleccionar un cliente válido.");
        }
        if (producto == null) {
            throw new Exception("Debe seleccionar un producto válido.");
        }
        if (cantidad <= 0) {
            throw new Exception("La cantidad debe ser mayor a cero.");
        }

        if (!cliente.isActivo()) {
            throw new Exception("No se puede registrar la compra porque el cliente está INACTIVO.");
        }
        if (producto.getStockActual() <= 0) {
            throw new Exception("El producto \"" + producto.getNombre() + "\" no tiene stock disponible.");
        }
        if (producto.getStockActual() < cantidad) {
            throw new Exception("Stock insuficiente para \"" + producto.getNombre() + 
                                "\". Disponible: " + producto.getStockActual() + " unidades.");
        }

        CompasCliente nuevaCompra = new CompasCliente();
        nuevaCompra.setCliente(cliente);
        nuevaCompra.setProducto(producto);
        nuevaCompra.setCantidad(cantidad);
        nuevaCompra.setTotalCompra(producto.getPrecioVenta() * cantidad);
        nuevaCompra.setFecha(new java.util.Date());

        localCompraCliente.guardarCompra(nuevaCompra);
    }

    public List<CompasCliente> listarTodasLasCompras() {
        return localCompraCliente.obtenerTodasLasCompras();
    }

    public List<CompasCliente> listarComprasPorCliente(String codigoCliente) {
        return localCompraCliente.listarComprasPorCliente(codigoCliente);
    }

    public List<CompasCliente> listarComprasPorCliente(String codigoCliente, String nombreCliente) {
        return localCompraCliente.listarComprasPorCliente(codigoCliente, nombreCliente);
    }

    public List<CompasCliente> obtenerVentasPorFecha(Date fechaBusqueda) {
        List<CompasCliente> filtradas = new ArrayList<>();
        if (fechaBusqueda == null) return filtradas;

        LocalDate fechaFiltro = fechaBusqueda.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        for (CompasCliente compra : listarTodasLasCompras()) {
            if (compra.getFecha() != null) {
                LocalDate fechaVenta = compra.getFecha().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                
                if (fechaVenta.equals(fechaFiltro)) {
                    filtradas.add(compra);
                }
            }
        }
        return filtradas;
    }

    public List<CompasCliente> obtenerHistorialCliente(String codigoCliente) {
        if (codigoCliente == null || codigoCliente.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return listarComprasPorCliente(codigoCliente);
    }
}