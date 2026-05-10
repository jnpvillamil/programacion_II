package co.edu.uptc.interfazGrafica;

import javax.swing.table.DefaultTableModel;
import co.edu.uptc.config.TiendaConfig;
import co.edu.uptc.modelo.Venta;

public class PanelVenta extends PanelCentral {
    
    public PanelVenta(Evento evento) {
        super(evento, "Gestión de Ventas");
    }
    
    @Override
    protected void configurarTabla() {
        modelo = new DefaultTableModel();
        modelo.addColumn("Factura");
        modelo.addColumn("Fecha");
        modelo.addColumn("Cliente");
        modelo.addColumn("Total");
        modelo.addColumn("Forma Pago");
        modelo.addColumn("Estado");
    }
    
    @Override
    public void poblarTabla() {
        limpiarTabla();
        String filtro = getTextoBuscar();
        
        for(Venta v : TiendaConfig.getInstancia().getGestionVenta().listarVentas()) {
            if(filtro.isEmpty() || v.getNumeroFactura().contains(filtro)) {
                Object[] fila = {
                    v.getNumeroFactura(),
                    v.getFechaHora().toString().split("T")[0],
                    v.getCliente().getNombre(),
                    "$" + v.getTotal(),
                    v.getFormaPago(),
                    v.getEstado()
                };
                modelo.addRow(fila);
            }
        }
    }
    
    @Override
    protected String getComandoNuevo() { return Evento.NUEVA_VENTA; }
    @Override
    protected String getComandoActualizar() { return ""; } // No aplica
    @Override
    protected String getComandoEliminar() { return Evento.ANULAR_VENTA; }
    @Override
    protected String getComandoBuscar() { return Evento.CONSULTAR_VENTA; }
    @Override
    protected String getComandoLimpiar() { return Evento.LIMPIAR_VENTA; }
}