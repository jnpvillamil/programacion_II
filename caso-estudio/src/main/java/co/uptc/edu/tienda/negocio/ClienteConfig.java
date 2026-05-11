package co.uptc.edu.tienda.negocio;

import co.uptc.edu.tienda.interfaces.IGestionCliente;
import co.uptc.edu.tienda.persistencia.LocalCliente;

public class ClienteConfig {

    private GestionCliente gestCliente;

    private IGestionCliente iCliente;

    public ClienteConfig() {
        iCliente = new LocalCliente();
        gestCliente = new GestionCliente(iCliente);
    }

    public GestionCliente getGestCliente() {
        return gestCliente;
    }

    public void setGestCliente(GestionCliente gestCliente) {
        this.gestCliente = gestCliente;
    }

    public IGestionCliente getiCliente() {
        return iCliente;
    }

    public void setiCliente(IGestionCliente iCliente) {
        this.iCliente = iCliente;
    }
}