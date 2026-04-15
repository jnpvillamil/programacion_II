package co.edu.uptc.sistienda.clientes.gui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import co.edu.uptc.sistienda.comun.gui.DialogoCrudAbstracto;
import co.edu.uptc.sistienda.gui.Evento;
import co.edu.uptc.sistienda.modelo.Cliente;
import co.edu.uptc.sistienda.modelo.enums.TipoClienteEnum;
import co.edu.uptc.sistienda.modelo.enums.TipoIdentificacionEnum;

public class DialogoCliente extends DialogoCrudAbstracto {

	private JTextField                       campoCodigo;
	private JTextField                       campoNombreORazonSocial;
	private JComboBox<TipoIdentificacionEnum> comboTipoIdentificacion;
	private JTextField                       campoNumeroIdentificacion;
	private JTextField                       campoDireccion;
	private JTextField                       campoTelefono;
	private JComboBox<TipoClienteEnum>       comboTipoCliente;

	public DialogoCliente(Evento evento, boolean esCreacion) {
		super(evento, esCreacion ? "Nuevo Cliente" : "Editar Cliente", esCreacion);
		setSize(420, 380);
	}

	@Override
	public void construirCamposFormulario(JPanel panelCampos) {
		campoCodigo                = new JTextField();
		campoNombreORazonSocial    = new JTextField();
		comboTipoIdentificacion    = new JComboBox<>(TipoIdentificacionEnum.values());
		campoNumeroIdentificacion  = new JTextField();
		campoDireccion             = new JTextField();
		campoTelefono              = new JTextField();
		comboTipoCliente           = new JComboBox<>(TipoClienteEnum.values());

		campoCodigo.setEditable(esCreacion);

		panelCampos.add(new JLabel("Código:"));
		panelCampos.add(campoCodigo);
		panelCampos.add(new JLabel("Nombre / Razón social:"));
		panelCampos.add(campoNombreORazonSocial);
		panelCampos.add(new JLabel("Tipo de identificación:"));
		panelCampos.add(comboTipoIdentificacion);
		panelCampos.add(new JLabel("Número de identificación:"));
		panelCampos.add(campoNumeroIdentificacion);
		panelCampos.add(new JLabel("Dirección:"));
		panelCampos.add(campoDireccion);
		panelCampos.add(new JLabel("Teléfono:"));
		panelCampos.add(campoTelefono);
		panelCampos.add(new JLabel("Tipo de cliente:"));
		panelCampos.add(comboTipoCliente);
	}

	@Override
	public void asignarComandosBotones() {
		botonGuardar.setActionCommand(esCreacion
				? Evento.GUARDAR_CLIENTE
				: Evento.ACTUALIZAR_CLIENTE);
		botonCancelar.setActionCommand(Evento.CANCELAR_CLIENTE);
	}

	@Override
	public void cargarDatosEnFormulario(Object objeto) {
		Cliente cliente = (Cliente) objeto;
		campoCodigo.setText(cliente.getCodigoCliente());
		campoNombreORazonSocial.setText(cliente.getNombreCompletoORazonSocial());
		comboTipoIdentificacion.setSelectedItem(cliente.getTipoIdentificacion());
		campoNumeroIdentificacion.setText(cliente.getNumeroIdentificacion());
		campoDireccion.setText(cliente.getDireccion());
		campoTelefono.setText(cliente.getTelefono());
		comboTipoCliente.setSelectedItem(cliente.getTipoCliente());
	}

	public Cliente capturarDatosFormulario() throws Exception {
		if (campoCodigo.getText().trim().isEmpty())
			throw new Exception("El código del cliente no puede estar vacío.");
		if (campoNombreORazonSocial.getText().trim().isEmpty())
			throw new Exception("El nombre o razón social no puede estar vacío.");
		if (campoNumeroIdentificacion.getText().trim().isEmpty())
			throw new Exception("El número de identificación no puede estar vacío.");

		Cliente cliente = new Cliente();
		cliente.setCodigoCliente(campoCodigo.getText().trim());
		cliente.setNombreCompletoORazonSocial(campoNombreORazonSocial.getText().trim());
		cliente.setTipoIdentificacion((TipoIdentificacionEnum) comboTipoIdentificacion.getSelectedItem());
		cliente.setNumeroIdentificacion(campoNumeroIdentificacion.getText().trim());
		cliente.setDireccion(campoDireccion.getText().trim());
		cliente.setTelefono(campoTelefono.getText().trim());
		cliente.setTipoCliente((TipoClienteEnum) comboTipoCliente.getSelectedItem());
		return cliente;
	}
}
