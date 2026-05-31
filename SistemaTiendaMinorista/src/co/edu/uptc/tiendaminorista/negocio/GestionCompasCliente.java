package co.edu.uptc.tiendaminorista.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import co.edu.uptc.tiendaminorista.interfaces.IGestionCompraCli;
import co.edu.uptc.tiendaminorista.modelo.Cliente;
import co.edu.uptc.tiendaminorista.modelo.CompasCliente;
import co.edu.uptc.tiendaminorista.modelo.Producto;
import co.edu.uptc.tiendaminorista.persistencia.LocalCompraCliente;

public class GestionCompasCliente {

    private IGestionCompraCli persistenciaCompra;

    public GestionCompasCliente() {
        this.persistenciaCompra = new LocalCompraCliente();
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

        CompasCliente nuevaCompra = new CompasCliente(cliente, producto, cantidad);
        
        nuevaCompra.setTotalCompra(producto.getPrecioVenta() * cantidad);
        nuevaCompra.setFecha(new Date());

        persistenciaCompra.guardarCompra(nuevaCompra);
    }

    public List<CompasCliente> listarTodasLasCompras() {
        return persistenciaCompra.obtenerTodasLasCompras();
    }
     
    public List<CompasCliente> listarComprasPorCliente(String cedulaCliente) {
        List<CompasCliente> comprasCliente = new ArrayList<>();
        for (CompasCliente compra : persistenciaCompra.obtenerTodasLasCompras()) {
            if (compra.getCliente().getNumeroIdentificacion().equals(cedulaCliente)) {
                comprasCliente.add(compra);
            }
        }
        return comprasCliente;
    }
}