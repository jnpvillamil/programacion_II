package negocio;

import modelo.ControlCambio;
import java.util.ArrayList;

public class NegocioControl {

    private ArrayList<ControlCambio> lista;

    public NegocioControl() {
        lista = new ArrayList<>();
    }

    public void registrarCambio(String accion, String codigo) {
        lista.add(new ControlCambio(accion, codigo));
    }

    public ArrayList<ControlCambio> getLista() {
        return lista;
    }
}
