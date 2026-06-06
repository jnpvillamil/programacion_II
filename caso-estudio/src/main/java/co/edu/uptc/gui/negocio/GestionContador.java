package co.edu.uptc.gui.negocio;

import java.util.List;
import co.edu.uptc.gui.interfaces.IGestionContador;
import co.edu.uptc.gui.modelo.Contador;

public class GestionContador {
    
    private IGestionContador persistentContador;

    public GestionContador(IGestionContador persistentContador) {
        this.persistentContador = persistentContador;
    }

    public void registrarContador(Contador contador) {
        if (contador != null) {
            this.persistentContador.guardar(contador);
        }
    }

    public List<Contador> listarContadores() {
        return this.persistentContador.listar();
    }
}