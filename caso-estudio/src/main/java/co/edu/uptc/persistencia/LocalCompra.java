package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.caso.estudio.dao.CompraDAOImpl;
import co.edu.uptc.interfaces.IGestionCompra;
import co.edu.uptc.negocio.dto.compraDto;

public class LocalCompra implements IGestionCompra {

    private List<compraDto> listaCompras = new ArrayList<>();
    private CompraDAOImpl   dao          = new CompraDAOImpl();
    private String          ruta         = "compras.json";

    @Override
    public void guardar(compraDto compra) {
        listaCompras.add(compra);
        dao.guardarCompras(listaCompras, ruta);
    }

    @Override
    public void anular(int numeroFactura) {
        listaCompras.removeIf(c -> c.getNumeroFacturaProveedor() == numeroFactura);
        dao.guardarCompras(listaCompras, ruta);
    }

    @Override
    public compraDto buscar(int numeroFactura) {
        for (compraDto c : listaCompras)
            if (c.getNumeroFacturaProveedor() == numeroFactura) return c;
        return null;
    }

    @Override
    public List<compraDto> listar() {
        return listaCompras;
    }
}