package co.edu.uptc.controlador;

import co.edu.uptc.dto.CompraDTO;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.negocio.GestionCompra;

import java.util.List;

public class ControladorCompra {
    private GestionCompra gestionCompra;

    public ControladorCompra(GestionCompra gestionCompra) {
        this.gestionCompra = gestionCompra;
    }

    public void registrarCompra(Compra compra) throws Exception {
        this.gestionCompra.registrarCompra(compra);
    }

    public Compra buscarCompra(String numeroFactura) {
        return this.gestionCompra.buscarCompra(numeroFactura);
    }

    public List<CompraDTO> obtenerListadoResumen() {
        return this.gestionCompra.obtenerListadoResumen();
    }
}