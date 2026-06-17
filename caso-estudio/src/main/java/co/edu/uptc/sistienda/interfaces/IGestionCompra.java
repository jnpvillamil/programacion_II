package co.edu.uptc.sistienda.interfaces;

import java.util.List;

import co.edu.uptc.sistienda.compras.modelo.Compra;

public interface IGestionCompra {

    void guardarCompra(Compra compra);

    void anularCompra(String numeroCompra);

    Compra buscarCompra(String numeroCompra);

    List<Compra> obtenerListaCompras();
}
