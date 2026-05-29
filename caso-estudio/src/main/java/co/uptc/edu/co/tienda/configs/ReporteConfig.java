package co.uptc.edu.co.tienda.configs;

import co.uptc.edu.tienda.interfaces.IGestionCompra;
import co.uptc.edu.tienda.interfaces.IGestionReporte;
import co.uptc.edu.tienda.interfaces.IGestionVenta;
import co.uptc.edu.tienda.negocio.GestionReporte;
import co.uptc.edu.tienda.persistencia.LocalReporte;

public class ReporteConfig {
    private IGestionReporte iReporte;
    private GestionReporte gestReporte;

    public ReporteConfig(IGestionVenta iVenta, IGestionCompra iCompra) {
        iReporte = new LocalReporte();
        gestReporte = new GestionReporte(iVenta, iCompra, iReporte);
    }

    public GestionReporte getGestReporte() {
        return gestReporte;
    }

    public IGestionReporte getiReporte() {
        return iReporte;
    }
}