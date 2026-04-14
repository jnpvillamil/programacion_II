package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.gui.modelo.Nomina;

public class LocalNomina {

    private List<Nomina> nominas;

    public LocalNomina() {
        nominas = new ArrayList<>();
    }

    public boolean guardarNomina(Nomina nomina) {
        if (buscarNomina(nomina.getCodigoNomina()) != null) {
            return false;
        }
        nominas.add(nomina);
        return true;
    }

    public Nomina buscarNomina(String codigoNomina) {
        for (Nomina nomina : nominas) {
            if (nomina.getCodigoNomina().equalsIgnoreCase(codigoNomina)) {
                return nomina;
            }
        }
        return null;
    }

    public List<Nomina> getNominas() {
        return nominas;
    }
}