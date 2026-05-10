package co.edu.uptc.interfazGrafica;

import javax.swing.table.DefaultTableModel;
import co.edu.uptc.config.TiendaConfig;
import co.edu.uptc.modelo.Compra;

public class PanelCompra extends PanelCentral {
    
    public PanelCompra(Evento evento) {
        super(evento, "Gestión de Compras");
    }
    
    @Override
    protected void configurarTabla() {
        modelo = new DefaultTableModel();
        modelo.addColumn("Factura Proveedor");
        modelo.addColumn("Fecha");
        modelo.addColumn("Proveedor");
        modelo.addColumn("Total");
        modelo.addColumn("Estado");
    }
    
    @Override
    public void poblarTabla() {
        limpiarTabla();
        String filtro = getTextoBuscar();
        
        for(Compra c : TiendaConfig.getInstancia().getGestionCompra().listarCompras()) {
            if(filtro.isEmpty() || c.getNumeroFacturaProveedor().contains(filtro)) {
                Object[] fila = {
                    c.getNumeroFacturaProveedor(),
                    c.getFechaHora().toString().split("T")[0],
                    c.getProveedor().getRazonSocial(),
                    "$" + String.format("%,.2f", c.getTotal()),
                    c.getEstado()
                };
                modelo.addRow(fila);
            }
        }
    }
    
    @Override
    protected String getComandoNuevo() { return Evento.NUEVA_COMPRA; }
    @Override
    protected String getComandoActualizar() { return ""; } 
    @Override
    protected String getComandoEliminar() { return Evento.ANULAR_COMPRA; }
    @Override
    protected String getComandoBuscar() { return Evento.CONSULTAR_COMPRA; }
    @Override
    protected String getComandoLimpiar() { return Evento.LIMPIAR_COMPRA; }
}