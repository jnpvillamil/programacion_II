package co.edu.uptc.interfazGrafica;

import javax.swing.table.DefaultTableModel;
import co.edu.uptc.config.TiendaConfig;
import co.edu.uptc.modelo.Proveedor;

public class PanelProveedor extends PanelCentral {
    
    public PanelProveedor(Evento evento) {
        super(evento, "Gestión de Proveedores");
    }
    
    @Override
    protected void configurarTabla() {
        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Razón Social");
        modelo.addColumn("NIT");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Correo");
        modelo.addColumn("Estado");
    }
    
    @Override
    public void poblarTabla() {
        limpiarTabla();
        String filtro = getTextoBuscar();
        
        for(Proveedor p : TiendaConfig.getInstancia().getGestionProveedor().listar()) {
            // Filtrar por código o razón social
            if(filtro.isEmpty() || p.getCodigo().contains(filtro) || p.getRazonSocial().contains(filtro)) {
                Object[] fila = {
                    p.getCodigo(),
                    p.getRazonSocial(),
                    p.getNit(),
                    p.getDireccion(),
                    p.getTelefono(),
                    p.getCorreo(),
                    p.isActivo() ? "Activo" : "Inactivo"
                };
                modelo.addRow(fila);
            }
        }
    }
    
    @Override
    protected String getComandoNuevo() { return Evento.NUEVO_PROVEEDOR; }
    
    @Override
    protected String getComandoActualizar() { return Evento.ACTUALIZAR_PROVEEDOR; }
    
    @Override
    protected String getComandoEliminar() { return Evento.ELIMINAR_PROVEEDOR; }
    
    @Override
    protected String getComandoBuscar() { return Evento.BUSCAR_PROVEEDOR; }
    
    @Override
    protected String getComandoLimpiar() { return Evento.LIMPIAR_PROVEEDOR; }
}