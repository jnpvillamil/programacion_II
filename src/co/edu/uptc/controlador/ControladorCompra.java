package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Compra;
import co.edu.uptc.negocio.GestionCompra;

public class ControladorCompra {
    private GestionCompra gestionCompra;

    public ControladorCompra(GestionCompra gestionCompra) {
        this.gestionCompra = gestionCompra;
    }

    public void registrarCompra(Compra compra) {
        this.gestionCompra.registrarCompra(compra);
    }
}