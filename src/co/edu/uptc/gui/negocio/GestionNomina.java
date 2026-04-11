package co.edu.uptc.gui.negocio;

import java.util.List;

import co.edu.uptc.gui.modelo.Empleado;
import co.edu.uptc.gui.modelo.Nomina;
import co.edu.uptc.persistencia.LocalNomina;

public class GestionNomina {

    private LocalNomina localNomina;

    public GestionNomina() {
        localNomina = new LocalNomina();
    }

    public boolean registrarNomina(Nomina nomina) {
        return localNomina.guardarNomina(nomina);
    }

    public double calcularNominaEmpleado(Empleado empleado) {
        return empleado.getSalarioBase();
    }

    public List<Nomina> listarNominas() {
        return localNomina.getNominas();
    }
}