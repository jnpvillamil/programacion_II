package co.edu.uptc.sistienda.clientes.gui;

import java.util.List;

import co.edu.uptc.sistienda.comun.gui.PanelCrudAbstracto;
import co.edu.uptc.sistienda.gui.Evento;
import co.edu.uptc.sistienda.modelo.Cliente;

public class PanelClientes extends PanelCrudAbstracto {

	public PanelClientes(Evento evento) {
		super(evento);
	}

	@Override
	public String obtenerTituloPanel() {
		return "Gestión de Clientes";
	}

	@Override
	public void asignarComandosBotones() {
		botonNuevo.setActionCommand(Evento.NUEVO_CLIENTE);
		botonEditar.setActionCommand(Evento.EDITAR_CLIENTE);
		botonInactivar.setActionCommand(Evento.INACTIVAR_CLIENTE);
		botonActivar.setActionCommand(Evento.ACTIVAR_CLIENTE);
		botonBuscar.setActionCommand(Evento.BUSCAR_CLIENTE);
		botonLimpiar.setActionCommand(Evento.LIMPIAR_CLIENTE);
	}

	@Override
	public void agregarColumnasTabla() {
		modeloTabla.addColumn("Código");
		modeloTabla.addColumn("Nombre / Razón Social");
		modeloTabla.addColumn("Tipo ID");
		modeloTabla.addColumn("Número ID");
		modeloTabla.addColumn("Tipo Persona");
		modeloTabla.addColumn("Ciudad");
		modeloTabla.addColumn("Teléfono");
		modeloTabla.addColumn("Correo");
		modeloTabla.addColumn("Tipo Cliente");
		modeloTabla.addColumn("Resp. Fiscal");
		modeloTabla.addColumn("Resp. Tributaria");
		modeloTabla.addColumn("Estado");
	}

	@Override
	public void poblarTabla(List<?> listaRegistros) {
		modeloTabla.setRowCount(0);
		for (Object obj : listaRegistros) {
			Cliente cliente = (Cliente) obj;
			Object[] fila = {
				cliente.getCodigoCliente(),
				cliente.getNombreCompletoORazonSocial(),
				cliente.getTipoIdentificacion(),
				cliente.getNumeroIdentificacion(),
				cliente.getTipoPersona(),
				cliente.getCiudad(),
				cliente.getTelefono(),
				cliente.getCorreoElectronico(),
				cliente.getTipoCliente(),
				cliente.getResponsabilidadFiscal(),
				cliente.getResponsabilidadTributaria(),
				cliente.isActivo() ? "Activo" : "Inactivo"
			};
			modeloTabla.addRow(fila);
		}
	}
}