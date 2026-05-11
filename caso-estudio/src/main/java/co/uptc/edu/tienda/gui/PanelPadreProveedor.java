package co.uptc.edu.tienda.gui;

import java.util.List;

import co.uptc.edu.tienda.modelo.Proveedor;
import co.uptc.edu.tienda.negocio.GestionProveedor;

public class PanelPadreProveedor extends PanelCentral<Proveedor> {
	

	
	public PanelPadreProveedor(Evento evento) {
		super(evento);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void agregarTituloPanel() {
		// TODO Auto-generated method stub
		tituloPanel = "Proveedores";
	}

	@Override
	public void agregarIdentificadorComandoBoton() {
		// TODO Auto-generated method stub
		btnInactivar.setActionCommand(Evento.ELIMINAR_PR);
		btnVer.setActionCommand(Evento.VER_PR);
		btnActualizar.setActionCommand(Evento.ACTUALIZAR_PR);
		btnRegistrar.setActionCommand(Evento.CREAR_PR);
		btnLimpiar.setActionCommand(Evento.LIMPIAR_PR);
		btnBuscar.setActionCommand(Evento.BUSCAR_PR);
		btnActivar.setActionCommand(Evento.ACTIVAR_PR);
		
	}

	@Override
	public void agregarCabeceraTabla() {
		modelo.addColumn("Código");
		modelo.addColumn("Razón social");
		modelo.addColumn("NIT");
		modelo.addColumn("Dirección");
		modelo.addColumn("Teléfono");
		modelo.addColumn("Correo");
		modelo.addColumn("Estado");
		tblGenerica.setModel(modelo);
		
	}

	@Override
	public void poblarTabla(List<Proveedor> listaProveedores) {
		// TODO Auto-generated method stub
		modelo.setRowCount(0);
		for (Proveedor p : listaProveedores) {
            Object[] fila = {
                p.getCodigoProveedor(),
                p.getRazonSocial(), 
                p.getNit(), 
                p.getDireccionP(), 
                p.getTelefonoP(), 
                p.getCorreoP(), 
                p.getEstado()
            };
            modelo.addRow(fila);
		}
	}
	
	public int getItemSeleccionado() {

		try {
	        int fila = tblGenerica.getSelectedRow();
	        
	        if (fila != -1) {
	            // Retornamos directamente el valor convertido
	            return Integer.parseInt(tblGenerica.getValueAt(fila, 0).toString());
	        }
	    } catch (Exception e) {
	        // Log del error opcional: e.printStackTrace();
	    }
	    
	    // Si llegó hasta aquí es porque: o no hay fila, o hubo un error.
	    return -1;	
	}
	

}
