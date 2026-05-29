package co.uptc.edu.co.tienda.configs;

import co.uptc.edu.tienda.interfaces.IGestionContable;
import co.uptc.edu.tienda.negocio.GestionContable;
import co.uptc.edu.tienda.persistencia.LocalContable;
import co.uptc.edu.tienda.persistencia.SqlContable;

public class ContableConfig {

    private IGestionContable iContable;
    private GestionContable gestContable;

    public ContableConfig() {
        iContable = new LocalContable();
        gestContable = new GestionContable(iContable);
    }

    public GestionContable getGestContable() {
        return gestContable;
    }

    public IGestionContable getiContable() {
        return iContable;
    }
}