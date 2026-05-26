package co.uptc.edu.tienda.negocio;

import java.time.LocalDateTime;
import java.util.List;

import co.uptc.edu.tienda.interfaces.IGestionCompra;
import co.uptc.edu.tienda.modelo.Compra;
import co.uptc.edu.tienda.modelo.DetalleCompra;

public class GestionCompra {

    private IGestionCompra iCompra;
    private List<Compra> listaCompras;
    private int consecutivo;

    public static final String PREFIJO_COMPRA = "COMP-";

    public GestionCompra(IGestionCompra iCompra) {
        this.iCompra = iCompra;
        this.listaCompras = iCompra.cargar();
        this.consecutivo = calcularSiguienteConsecutivo();
    }

    private int calcularSiguienteConsecutivo() {
        int max = 0;
        for (Compra c : listaCompras) {
            try {
                String numero = c.getNumeroFactura()
                        .replace(PREFIJO_COMPRA, "");
                int n = Integer.parseInt(numero);
                if (n > max) max = n;
            } catch (Exception e) {
                // formato inesperado, se ignora
            }
        }
        return max + 1;
    }

    public void guardarCompra(Compra compra) throws Exception {
        if (compra.getProveedor() == null) {
            throw new Exception("La compra debe tener un proveedor");
        }
        if (compra.getDetalles() == null || compra.getDetalles().isEmpty()) {
            throw new Exception("La compra debe tener al menos un producto");
        }

        // Negocio asigna número y fecha — igual que GestionVenta
        compra.setNumeroFactura(PREFIJO_COMPRA + consecutivo++);
        compra.setFechaCompra(LocalDateTime.now().toString());

        // Calcular total
        double total = 0;
        for (DetalleCompra d : compra.getDetalles()) {
            total += d.getSubtotal();
        }
        compra.setTotal(total);

        
        iCompra.guardar(compra);
        listaCompras.add(compra);
    }

    public List<Compra> listarCompras() {
        return listaCompras;
    }
}