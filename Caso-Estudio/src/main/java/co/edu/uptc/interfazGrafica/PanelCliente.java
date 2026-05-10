package co.edu.uptc.interfazGrafica;

import javax.swing.table.DefaultTableModel;
import co.edu.uptc.config.TiendaConfig;
import co.edu.uptc.modelo.Cliente;

public class PanelCliente extends PanelCentral {
    
    public PanelCliente(Evento evento) {
        super(evento, "Gestión de Clientes");
    }
    
    @Override
    protected void configurarTabla() {
        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Tipo ID");
        modelo.addColumn("Número ID");
        modelo.addColumn("Direccion");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Tipo Cliente");
    }
    
    @Override
    public void poblarTabla() {
        limpiarTabla();
        String filtro = getTextoBuscar();
        
        for(Cliente c : TiendaConfig.getInstancia().getGestionCliente().listar()) {
            if(filtro.isEmpty() || c.getCodigo().contains(filtro) || c.getNombre().contains(filtro)) {
                Object[] fila = {
                    c.getCodigo(),
                    c.getNombre(),
                    c.getTipoIdentificacion().name(),
                    c.getNumeroIdentificacion(),
                    c.getDireccion(),
                    c.getTelefono(),
                    c.getTipoCliente(),
                    c.isActivo() ? "Activo" : "Inactivo"
                };
                modelo.addRow(fila);
            }
        }
    }
    
    @Override
    protected String getComandoNuevo() { return Evento.NUEVO_CLIENTE; }
    @Override
    protected String getComandoActualizar() { return Evento.ACTUALIZAR_CLIENTE; }
    @Override
    protected String getComandoEliminar() { return Evento.ELIMINAR_CLIENTE; }
    @Override
    protected String getComandoBuscar() { return Evento.BUSCAR_CLIENTE; }
    @Override
    protected String getComandoLimpiar() { return Evento.LIMPIAR_CLIENTE; }
}