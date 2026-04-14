package co.edu.uptc.gui.negocio;

import java.util.List;

import co.edu.uptc.gui.modelo.Compra;
import co.edu.uptc.gui.modelo.Proveedor;
import co.edu.uptc.persistencia.LocalCompra;

public class GestionCompra {

    private LocalCompra localCompra;

    public GestionCompra() {
        localCompra = new LocalCompra();
    }

    public boolean registrarCompra(Compra compra) {
        compra.calcularTotal();
        return localCompra.guardarCompra(compra);
    }

    public Compra buscarCompra(String factura) {
        return localCompra.buscarCompra(factura);
    }

    public List<Compra> listarCompras() {
        return localCompra.getCompras();
    }

    public boolean actualizarCompra(Compra compra) {
        return localCompra.actualizarCompra(compra);
    }

    public boolean eliminarCompra(String factura) {
        return localCompra.eliminarCompra(factura);
    }
    
    }