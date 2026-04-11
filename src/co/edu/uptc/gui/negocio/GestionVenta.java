package co.edu.uptc.gui.negocio;

import java.util.List;

import co.edu.uptc.enums.EstadoVentaEnum;
import co.edu.uptc.gui.modelo.Venta;
import co.edu.uptc.persistencia.LocalVenta;

public class GestionVenta {

    private LocalVenta localVenta;

    public GestionVenta() {
        localVenta = new LocalVenta();
    }

    public boolean registrarVenta(Venta venta) {
        venta.calcularTotalVenta();
        return localVenta.guardarVenta(venta);
    }

    public Venta buscarVenta(String numeroFactura) {
        return localVenta.buscarVenta(numeroFactura);
    }

    public boolean anularVenta(String numeroFactura) {
        Venta venta = localVenta.buscarVenta(numeroFactura);
        if (venta != null) {
            venta.setEstado(EstadoVentaEnum.ANULADA);
            return true;
        }
        return false;
    }

    public List<Venta> listarVentas() {
        return localVenta.getVentas();
    }
}