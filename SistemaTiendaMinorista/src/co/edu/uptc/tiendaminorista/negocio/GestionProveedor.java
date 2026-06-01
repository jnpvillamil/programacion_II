package co.edu.uptc.tiendaminorista.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.tiendaminorista.modelo.CompraPro;
import co.edu.uptc.tiendaminorista.modelo.Proveedor;
import co.edu.uptc.tiendaminorista.persistencia.LocalProveedor;
import co.edu.uptc.tiendaminorista.persistencia.LocalCompraPro; 

public class GestionProveedor {

    private final LocalProveedor localProveedor;
    private final LocalCompraPro localCompraPro; 

    // Constructor que no daña tu TiendaConfig
    public GestionProveedor(LocalProveedor localProveedor) {
        this.localProveedor = localProveedor;
        this.localCompraPro = new LocalCompraPro(); 
    }

    public void agregarProveedor(Proveedor proveedor) {
        localProveedor.guardar(proveedor);
    }

    public List<Proveedor> listarProveedores() {
        return localProveedor.listar();
    }

    public void actualizarProveedor(Proveedor proveedor) {
        localProveedor.actualizar(proveedor);
    }

    public void desactivarProveedor(String codigo) {
        localProveedor.desactivar(codigo);
    }

    public void activarProveedor(String codigo) {
        localProveedor.activar(codigo);
    }

   
    public List<CompraPro> listarComprasProveedorPorFecha(String nitProveedor, LocalDate desde, LocalDate hasta) {
        List<CompraPro> resultado = new ArrayList<>();
        if (nitProveedor == null || nitProveedor.trim().isEmpty() || desde == null || hasta == null) {
            return resultado;
        }

        List<CompraPro> todasLasCompras = localCompraPro.leer();
        if (todasLasCompras == null) return resultado;

        for (CompraPro compra : todasLasCompras) {
            if (compra == null || compra.getFecha() == null) continue;

            String nitCompra = compra.getProveedor();
            String proveedorOrigen = compra.getProveedor();
            boolean coincideNit = nitCompra != null && nitCompra.equalsIgnoreCase(nitProveedor.trim());
            boolean coincideNombre = proveedorOrigen != null && proveedorOrigen.toLowerCase().contains(nitProveedor.trim().toLowerCase());

            if (coincideNit || coincideNombre) {
                LocalDate fechaCompra = compra.getFecha();
                if (!fechaCompra.isBefore(desde) && !fechaCompra.isAfter(hasta)) {
                    resultado.add(compra);
                }
            }
        }
        return resultado;
    }
}