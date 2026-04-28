package co.edu.uptc.gui.negocio;

import co.edu.uptc.gui.modelo.Reporte;

public class GestionReporte {

    public String generarDescripcionReporte(Reporte reporte) {
        return "Reporte de tipo " + reporte.getTipoReporte() + " generado para el periodo " + reporte.getPeriodo();
    }
}