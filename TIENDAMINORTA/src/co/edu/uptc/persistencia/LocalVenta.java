package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.gui.modelo.Venta;

public class LocalVenta {

    private List<Venta> ventas;

    public LocalVenta() {
        ventas = new ArrayList<>();
    }

    public boolean guardarVenta(Venta venta) {
        if (buscarVenta(venta.getNumeroFactura()) != null) {
            return false;
        }
        ventas.add(venta);
        return true;
    }

    public Venta buscarVenta(String numeroFactura) {
        for (Venta venta : ventas) {
            if (venta.getNumeroFactura().equalsIgnoreCase(numeroFactura)) {
                return venta;
            }
        }
        return null;
    }

    public List<Venta> getVentas() {
        return ventas;
    }
}