package co.edu.uptc.gui.modelo;

import java.util.Date;

import co.edu.uptc.enums.TipoReporteEnum;
import co.edu.uptc.gui.interfaces.Reportable;

public class Reporte implements Reportable {

    private TipoReporteEnum tipoReporte;
    private Date fechaGeneracion;
    private String periodo;

    public Reporte() {
        fechaGeneracion = new Date();
    }

    public TipoReporteEnum getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(TipoReporteEnum tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @Override
    public void generarReporte() {
        System.out.println("Reporte generado");
    }
}