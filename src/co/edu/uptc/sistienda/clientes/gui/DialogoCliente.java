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
import co.edu.uptc.sistienda.modelo.enums.TipoPersonaEnum;

public class DialogoCliente extends DialogoCrudAbstracto {

	private JTextField                       campoCodigo;
	private JTextField                       campoNombreORazonSocial;
	private JComboBox<TipoIdentificacionEnum> comboTipoIdentificacion;
	private JTextField                       campoNumeroIdentificacion;
	private JTextField                       campoDireccion;
	private JTextField                       campoCiudad; 
	private JTextField                       campoTelefono;
	private JTextField                       campoCorreo;
	private JComboBox<TipoClienteEnum>       comboTipoCliente;
	private JComboBox<TipoPersonaEnum>       comboTipoPersona;
	private JTextField                       campoResponsabilidadFiscal; 
	private JTextField                       campoResponsabilidadTributaria; 
	

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
		campoCiudad                = new JTextField();
		campoTelefono              = new JTextField();
		campoCorreo                = new JTextField();
		comboTipoCliente           = new JComboBox<>(TipoClienteEnum.values());
		comboTipoPersona           = new JComboBox<>(TipoPersonaEnum.values());
		campoResponsabilidadFiscal = new JTextField();
		campoResponsabilidadTributaria = new JTextField(); 

		campoCodigo.setEditable(esCreacion);

		panelCampos.add(new JLabel("Código:"));
		panelCampos.add(campoCodigo);
		panelCampos.add(new JLabel("Nombre / Razón social:"));
		panelCampos.add(campoNombreORazonSocial);
		panelCampos.add(new JLabel("Tipo de identificación:"));
		panelCampos.add(comboTipoIdentificacion);
		panelCampos.add(new JLabel("Número de identificación:"));
		panelCampos.add(campoNumeroIdentificacion);
		panelCampos.add(new JLabel("Tipo de persona:"));
		panelCampos.add(comboTipoPersona); 
		panelCampos.add(new JLabel("Tipo de cliente:"));
		panelCampos.add(comboTipoCliente);
		panelCampos.add(new JLabel("Dirección:"));
		panelCampos.add(campoDireccion);
		panelCampos.add(new JLabel("Ciudad:"));
		panelCampos.add(campoCiudad);
		panelCampos.add(new JLabel("Teléfono:"));
		panelCampos.add(campoTelefono);
		panelCampos.add(new JLabel("Correo electrónico:"));
		panelCampos.add(campoCorreo);
		panelCampos.add(new JLabel("Responsabilidad Fiscal"));
		panelCampos.add(campoResponsabilidadFiscal);
		panelCampos.add(new JLabel("Responsabilidad tributaria"));
		panelCampos.add(campoResponsabilidadTributaria);
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
		campoCiudad.setText(cliente.getCiudad() !=null ? cliente.getCiudad() : "");
		campoTelefono.setText(cliente.getTelefono());
		campoCorreo.setText(cliente.getCorreoElectronico() !=null ? cliente.getCorreoElectronico() : "");
		comboTipoCliente.setSelectedItem(cliente.getTipoCliente());
		comboTipoPersona.setSelectedItem(cliente.getTipoPersona() !=null ? cliente.getTipoPersona() : TipoPersonaEnum.NATURAL);
		campoResponsabilidadFiscal.setText(cliente.getResponsabilidadFiscal() != null ? cliente.getResponsabilidadFiscal() : "");
		campoResponsabilidadTributaria.setText(cliente.getResponsabilidadTributaria() != null ? cliente.getResponsabilidadTributaria() : "");
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
		cliente.setCiudad(campoCiudad.getText().trim());
		cliente.setTelefono(campoTelefono.getText().trim());
		cliente.setCorreoElectronico(campoCorreo.getText().trim());
		cliente.setTipoCliente((TipoClienteEnum) comboTipoCliente.getSelectedItem());
		cliente.setTipoPersona((TipoPersonaEnum) comboTipoPersona.getSelectedItem());
		cliente.setResponsabilidadFiscal(campoResponsabilidadFiscal.getText().trim());
		cliente.setResponsabilidadTributaria(campoResponsabilidadTributaria.getText().trim());
		return cliente;
	}
}