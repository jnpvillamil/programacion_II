package co.edu.uptc.sistienda.proveedores.gui;

import java.util.List;

import co.edu.uptc.sistienda.comun.gui.PanelCrudAbstracto;
import co.edu.uptc.sistienda.gui.Evento;
import co.edu.uptc.sistienda.modelo.Proveedor;

public class PanelProveedores extends PanelCrudAbstracto {

	public PanelProveedores(Evento evento) {
		super(evento);
	}

	@Override
	public String obtenerTituloPanel() {
		return "Gestión de Proveedores";
	}

	@Override
	public void asignarComandosBotones() {
		botonNuevo.setActionCommand(Evento.NUEVO_PROVEEDOR);
		botonEditar.setActionCommand(Evento.EDITAR_PROVEEDOR);
		botonInactivar.setActionCommand(Evento.INACTIVAR_PROVEEDOR);
		botonActivar.setActionCommand(Evento.ACTIVAR_PROVEEDOR);
		botonBuscar.setActionCommand(Evento.BUSCAR_PROVEEDOR);
		botonLimpiar.setActionCommand(Evento.LIMPIAR_PROVEEDOR);
	}

	@Override
	public void agregarColumnasTabla() {
		modeloTabla.addColumn("Código");
		modeloTabla.addColumn("Razón Social");
		modeloTabla.addColumn("NIT");
		modeloTabla.addColumn("Ciudad");
		modeloTabla.addColumn("Teléfono");
		modeloTabla.addColumn("Correo");
		modeloTabla.addColumn("Resp. Fiscal");
		modeloTabla.addColumn("Resp. Tributaria");
		modeloTabla.addColumn("Act. Económica");
		modeloTabla.addColumn("Estado");
	}

	@Override
	public void poblarTabla(List<?> listaRegistros) {
		modeloTabla.setRowCount(0);
		for (Object obj : listaRegistros) {
			Proveedor proveedor = (Proveedor) obj;
			Object[] fila = {
				proveedor.getCodigoProveedor(),
				proveedor.getRazonSocial(),
				proveedor.getNit(),
				proveedor.getCiudad(),
				proveedor.getTelefono(),
				proveedor.getCorreoElectronico(),
				proveedor.getResponsabilidadFiscal(),
				proveedor.getResponsabilidadTributaria(),
				proveedor.getActividadEconomica(),
				proveedor.isActivo() ? "Activo" : "Inactivo"
			};
			modeloTabla.addRow(fila);
		}
	}
}