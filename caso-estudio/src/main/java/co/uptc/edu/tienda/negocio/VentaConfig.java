package co.uptc.edu.tienda.negocio;

import co.uptc.edu.tienda.interfaces.IGestionVenta;
import co.uptc.edu.tienda.persistencia.LocalVenta;

public class VentaConfig {

    public static final double IVA = 0.19;

    public static final String PREFIJO_FACTURA = "FACT-";

    private GestionVenta gestVenta;

    private IGestionVenta iVenta;

    public VentaConfig() {

        iVenta =
                new LocalVenta();

        gestVenta =
                new GestionVenta(iVenta);
    }

    public GestionVenta getGestVenta() {

        return gestVenta;
    }

    public void setGestVenta(
            GestionVenta gestVenta) {

        this.gestVenta = gestVenta;
    }

    public IGestionVenta getiVenta() {

        return iVenta;
    }

    public void setiVenta(
            IGestionVenta iVenta) {

        this.iVenta = iVenta;
    }
}