package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.caso.estudio.dao.VentaDAOImpl;
import co.edu.uptc.interfaces.IGestionVenta;
import co.edu.uptc.negocio.dto.ventaDto;

public class LocalVenta implements IGestionVenta {

    private List<ventaDto> listaVentas = new ArrayList<>();
    private VentaDAOImpl dao = new VentaDAOImpl();
    private String ruta = "ventas.json";

    @Override
    public void guardar(ventaDto venta) {
        listaVentas.add(venta);
        dao.guardarVentas(listaVentas, ruta);
    }

    @Override
    public void anular(int numeroFactura) {
        listaVentas.removeIf(v -> v.getNumeroFactura() == numeroFactura);
        dao.guardarVentas(listaVentas, ruta);
    }

    @Override
    public ventaDto buscar(int numeroFactura) {
        for (ventaDto v : listaVentas)
            if (v.getNumeroFactura() == numeroFactura) return v;
        return null;
    }

    @Override
    public List<ventaDto> listar() {
        return listaVentas;
    }
}