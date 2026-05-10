package co.edu.uptc.interfazGrafica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import co.edu.uptc.config.TiendaConfig;
import co.edu.uptc.enums.CuentaContable;
import co.edu.uptc.modelo.AsientoContable;
import co.edu.uptc.modelo.MovimientoContable;

public class PanelContable extends PanelCentral {
    
    private JComboBox<String> cbTipoReporte;
    private JButton btnGenerarReporte;
    
    public PanelContable(Evento evento) {
        super(evento, "Gestión Contable");
    }
    
    @Override
    protected void configurarTabla() {
        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Fecha");
        modelo.addColumn("Descripción");
        modelo.addColumn("Referencia");
    }
    
    @Override
    public void poblarTabla() {
        limpiarTabla();
        String filtro = getTextoBuscar();
        
        for(AsientoContable a : TiendaConfig.getInstancia().getGestionContable().listarAsientos()) {
            if(filtro.isEmpty() || a.getCodigoAsiento().contains(filtro)) {
                Object[] fila = {
                    a.getCodigoAsiento(),
                    a.getFecha().toString().split("T")[0],
                    a.getDescripcion(),
                    a.getReferencia()
                };
                modelo.addRow(fila);
            }
        }
    }
    
    @Override
    protected String getComandoNuevo() { return ""; }
    @Override
    protected String getComandoActualizar() { return ""; }
    @Override
    protected String getComandoEliminar() { return ""; }
    @Override
    protected String getComandoBuscar() { return Evento.CONSULTAR_VENTA; }
    @Override
    protected String getComandoLimpiar() { return ""; }
}