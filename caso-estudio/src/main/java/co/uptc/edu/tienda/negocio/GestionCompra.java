package co.uptc.edu.tienda.negocio;

import java.util.List;

import co.uptc.edu.tienda.interfaces.IGestionCompra;
import co.uptc.edu.tienda.modelo.Compra;

public class GestionCompra {

    private IGestionCompra iCompra;

    public GestionCompra(IGestionCompra iCompra) {

        this.iCompra = iCompra;
    }

    public void guardarCompra(Compra compra) {

        List<Compra> lista =
                iCompra.cargar();

        lista.add(compra);

        iCompra.guardar(lista);
    }
}