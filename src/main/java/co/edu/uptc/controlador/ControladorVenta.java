package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Venta;
import co.edu.uptc.negocio.GestionVenta;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;

public class ControladorVenta {

    private final GestionVenta gestionVenta;

    public ControladorVenta(GestionVenta gestionVenta) {
        this.gestionVenta = gestionVenta;
    }

    public String realizarVenta(Venta venta) {
        try {
            return gestionVenta.realizarVenta(venta);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        } catch (ExcepcionAccesoDatos e) {
            return "Error: " + ControladorCliente.mensajeParaUsuario(e);
        }
    }

    public String anularVenta(String numeroFactura) {
        try {
            return gestionVenta.anularVenta(numeroFactura);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        } catch (ExcepcionAccesoDatos e) {
            return "Error: " + ControladorCliente.mensajeParaUsuario(e);
        }
    }

    public void calcularTotales(Venta venta) {
        gestionVenta.calcularTotales(venta);
    }
}
