package co.edu.uptc.gui.negocio;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.gui.modelo.Compra;
import co.edu.uptc.gui.modelo.Proveedor;

public class GestionConsulta {

    private GestionCompra gestionCompra;

    public GestionConsulta() {
        gestionCompra = new GestionCompra();
    }

    public List<Compra> consultarComprasPorProveedor(String codigoProveedor) {
        List<Compra> resultado = new ArrayList<>();
        for (Compra compra : gestionCompra.listarCompras()) {
            Proveedor proveedor = compra.getProveedor();
            if (proveedor != null && proveedor.getCodigo().equalsIgnoreCase(codigoProveedor)) {
                resultado.add(compra);
            }
        }
        return resultado;
    }
}