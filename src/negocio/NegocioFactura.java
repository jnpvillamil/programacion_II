package negocio;

import modelo.Factura;
import java.util.ArrayList;

public class NegocioFactura {

    private ArrayList<Factura> listaFacturas;

    public NegocioFactura() {
        listaFacturas = new ArrayList<>();
    }

    public void agregarFactura(Factura f) {
        listaFacturas.add(f);
    }

    public ArrayList<Factura> getFacturas() {
        return listaFacturas;
    }
}