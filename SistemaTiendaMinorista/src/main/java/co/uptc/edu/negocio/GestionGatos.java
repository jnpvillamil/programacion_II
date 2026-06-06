package co.uptc.edu.negocio;

import java.util.List;

import co.uptc.edu.interfaces.IGatoDAO;
import co.uptc.edu.modelo.Gato;

public class GestionGatos {

    private IGatoDAO gatoDAO;

    public GestionGatos(IGatoDAO gatoDAO) {
        this.gatoDAO = gatoDAO;
    }

    public boolean registrarGato(
            String nombre,
            int edad) {

        Gato gato = new Gato(
                nombre,
                edad
        );

        return gatoDAO.guardarGato(gato);
    }

    public List<Gato> obtenerGatos() {
        return gatoDAO.obtenerGatos();
    }
}