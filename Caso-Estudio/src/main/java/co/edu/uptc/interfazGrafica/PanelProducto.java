package co.edu.uptc.interfazGrafica;

import javax.swing.table.DefaultTableModel;
import co.edu.uptc.config.TiendaConfig;
import co.edu.uptc.modelo.Producto;

public class PanelProducto extends PanelCentral {
    
    public PanelProducto(Evento evento) {
        super(evento, "Gestión de Productos");
    }
    
    @Override
    protected void configurarTabla() {
        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Categoría");
        modelo.addColumn("Precio Compra");
        modelo.addColumn("Precio Venta");
        modelo.addColumn("Stock Actual");
        modelo.addColumn("Stock Mínimo");
        modelo.addColumn("Estado");
    }
    
    @Override
    public void poblarTabla() {
        limpiarTabla();
        String filtro = getTextoBuscar();
        
        for(Producto p : TiendaConfig.getInstancia().getGestionProducto().listar()) {
            
            if(filtro.isEmpty() || p.getCodigo().contains(filtro) || p.getNombre().contains(filtro)) {
                Object[] fila = {
                    p.getCodigo(),
                    p.getNombre(),
                    p.getCategoria(),
                    "$" + p.getPrecioCompra(),
                    "$" + p.getPrecioVenta(),
                    p.getStockActual(),
                    p.getStockMinimo(),
                    p.isActivo() ? "Activo" : "Inactivo"
                };
                modelo.addRow(fila);
            }
        }
        
       
        tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table, Object value, boolean isSelected, 
                boolean hasFocus, int row, int column) {
                
                java.awt.Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
                
                if(!isSelected) {
                    String codigo = table.getValueAt(row, 0).toString();
                    Producto p = TiendaConfig.getInstancia().getGestionProducto().buscar(codigo);
                    if(p != null && p.getStockActual() < p.getStockMinimo()) {
                        c.setBackground(new java.awt.Color(255, 200, 200)); // Rojo claro
                    } else {
                        c.setBackground(java.awt.Color.WHITE);
                    }
                }
                return c;
            }
        });
    }
    
    @Override
    protected String getComandoNuevo() { return Evento.NUEVO_PRODUCTO; }
    
    @Override
    protected String getComandoActualizar() { return Evento.ACTUALIZAR_PRODUCTO; }
    
    @Override
    protected String getComandoEliminar() { return Evento.ELIMINAR_PRODUCTO; }
    
    @Override
    protected String getComandoBuscar() { return Evento.BUSCAR_PRODUCTO; }
    
    @Override
    protected String getComandoLimpiar() { return Evento.LIMPIAR_PRODUCTO; }
}