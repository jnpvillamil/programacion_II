package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Venta;
import co.edu.uptc.negocio.GestionVenta;

public class ControladorVenta {
    private GestionVenta gestionVenta;

    public ControladorVenta(GestionVenta gestionVenta) {
        this.gestionVenta = gestionVenta;
    }

    public boolean realizarVenta(Venta venta) {
        return this.gestionVenta.realizarVenta(venta);
    }

    public double calcularIVAVenta(double total) {
        return this.gestionVenta.calcularIVA(total);
    }
}