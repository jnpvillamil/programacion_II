package co.uptc.edu.tienda.gui;

import java.util.List;

import co.uptc.edu.tienda.modelo.Proveedor;

public class PanelPadreProveedor extends PanelCentral {
	
	

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
		btnEliminar.setActionCommand(Evento.ELIMINAR_PR);
		btnVer.setActionCommand(Evento.VER_PR);
		btnActualizar.setActionCommand(Evento.ACTUALIZAR_PR);
		btnCrear.setActionCommand(Evento.CREAR_PR);
		btnLimpiar.setActionCommand(Evento.LIMPIAR_PR);
		btnBuscar.setActionCommand(Evento.BUSCAR_PR);
		
	}

	@Override
	public void agregarCabeceraTabla() {
		modelo.addColumn("Código");
		modelo.addColumn("Razón social");
		modelo.addColumn("NIT");
		modelo.addColumn("Dirección");
		modelo.addColumn("Teléfono");
		modelo.addColumn("Correo");
		tblProveedores.setModel(modelo);
		
	}

	@Override
	public void poblarTabla(List<?> listaProveedores) {
		// TODO Auto-generated method stub
		modelo.setRowCount(0);
		List<Proveedor> proveedores =(List<Proveedor>) listaProveedores;
		for(Proveedor p : proveedores) {
			Object[] fila = {p.getCodigoProveedor(),p.getRazonSocial(), p.getNit(), p.getDireccionP(), p.getTelefonoP(), p.getCorreoP()};
			modelo.addRow(fila);
		}
	}
	
	public int getItemSeleccionado() {
		int fila = tblProveedores.getSelectedRow();
		int var = Integer.parseInt(tblProveedores.getModel().getValueAt(fila, 0).toString());
		return var;
	}

}
