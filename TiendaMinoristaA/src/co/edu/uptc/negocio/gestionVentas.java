package co.edu.uptc.negocio;

import java.util.List;
import co.edu.uptc.interfaces.IGestionVenta;
import co.edu.uptc.negocio.dto.ventaDto;
import co.edu.uptc.persistencia.LocalVenta;

public class gestionVentas {

    private IGestionVenta iVenta;

    public gestionVentas() {
        this.iVenta = new LocalVenta();
    }

    public void registrar(ventaDto venta) throws Exception {
        if (venta == null) throw new Exception("No se tiene informacion de la venta");
        iVenta.guardar(venta);
    }

    public void anular(int numeroFactura) throws Exception {
        iVenta.anular(numeroFactura);
    }

    public ventaDto buscar(int numeroFactura) throws Exception {
        return iVenta.buscar(numeroFactura);
    }

    public List<ventaDto> listar() {
        return iVenta.listar();
    }
}
