package co.uptc.edu.co.tienda.configs;

import co.uptc.edu.tienda.interfaces.IGestionCompra;
import co.uptc.edu.tienda.negocio.GestionCompra;
import co.uptc.edu.tienda.persistencia.LocalCompra;

public class CompraConfig {

    private GestionCompra gestion;

    private IGestionCompra iCompra;

    public CompraConfig() {

        iCompra = new LocalCompra();

        gestion = new GestionCompra(iCompra);
    }

    public GestionCompra getGestion() {

        return gestion;
    }

    public void setGestCompra(GestionCompra gestCompra) {

        this.gestion = gestCompra;
    }

    public IGestionCompra getiCompra() {

        return iCompra;
    }

    public void setiCompra(IGestionCompra iCompra) {

        this.iCompra = iCompra;
    }
}