package co.uptc.edu.co.tienda.configs;

import co.uptc.edu.tienda.interfaces.IGestionCompra;
import co.uptc.edu.tienda.negocio.GestionCompra;
import co.uptc.edu.tienda.persistencia.LocalCompra;

public class CompraConfig {

    private GestionCompra gestCompra;

    private IGestionCompra iCompra;

    public CompraConfig() {

        iCompra = new LocalCompra();

        gestCompra = new GestionCompra(iCompra);
    }

    public GestionCompra getGestion() {

        return gestCompra;
    }

    public void setGestCompra(GestionCompra gestCompra) {

        this.gestCompra = gestCompra;
    }

    public IGestionCompra getiCompra() {

        return iCompra;
    }

    public void setiCompra(IGestionCompra iCompra) {

        this.iCompra = iCompra;
    }
}