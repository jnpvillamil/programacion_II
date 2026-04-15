package co.edu.uptc.sistienda.proveedores.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import co.edu.uptc.sistienda.comun.gui.DialogoCrudAbstracto;
import co.edu.uptc.sistienda.gui.Evento;
import co.edu.uptc.sistienda.modelo.Proveedor;

public class DialogoProveedor extends DialogoCrudAbstracto {

	private JTextField campoCodigo;
	private JTextField campoRazonSocial;
	private JTextField campoNit;
	private JTextField campoDireccion;
	private JTextField campoTelefono;
	private JTextField campoCorreo;

	public DialogoProveedor(Evento evento, boolean esCreacion) {
		super(evento, esCreacion ? "Nuevo Proveedor" : "Editar Proveedor", esCreacion);
		setSize(400, 320);
	}

	@Override
	public void construirCamposFormulario(JPanel panelCampos) {
		campoCodigo     = new JTextField();
		campoRazonSocial = new JTextField();
		campoNit        = new JTextField();
		campoDireccion  = new JTextField();
		campoTelefono   = new JTextField();
		campoCorreo     = new JTextField();

		campoCodigo.setEditable(esCreacion);

		panelCampos.add(new JLabel("Código:"));
		panelCampos.add(campoCodigo);
		panelCampos.add(new JLabel("Razón social:"));
		panelCampos.add(campoRazonSocial);
		panelCampos.add(new JLabel("NIT:"));
		panelCampos.add(campoNit);
		panelCampos.add(new JLabel("Dirección:"));
		panelCampos.add(campoDireccion);
		panelCampos.add(new JLabel("Teléfono:"));
		panelCampos.add(campoTelefono);
		panelCampos.add(new JLabel("Correo electrónico:"));
		panelCampos.add(campoCorreo);
	}

	@Override
	public void asignarComandosBotones() {
		botonGuardar.setActionCommand(esCreacion
				? Evento.GUARDAR_PROVEEDOR
				: Evento.ACTUALIZAR_PROVEEDOR);
		botonCancelar.setActionCommand(Evento.CANCELAR_PROVEEDOR);
	}

	@Override
	public void cargarDatosEnFormulario(Object objeto) {
		Proveedor proveedor = (Proveedor) objeto;
		campoCodigo.setText(proveedor.getCodigoProveedor());
		campoRazonSocial.setText(proveedor.getRazonSocial());
		campoNit.setText(proveedor.getNit());
		campoDireccion.setText(proveedor.getDireccion());
		campoTelefono.setText(proveedor.getTelefono());
		campoCorreo.setText(proveedor.getCorreoElectronico());
	}

	public Proveedor capturarDatosFormulario() throws Exception {
		if (campoCodigo.getText().trim().isEmpty())
			throw new Exception("El código del proveedor no puede estar vacío.");
		if (campoRazonSocial.getText().trim().isEmpty())
			throw new Exception("La razón social no puede estar vacía.");
		if (campoNit.getText().trim().isEmpty())
			throw new Exception("El NIT no puede estar vacío.");

		Proveedor proveedor = new Proveedor();
		proveedor.setCodigoProveedor(campoCodigo.getText().trim());
		proveedor.setRazonSocial(campoRazonSocial.getText().trim());
		proveedor.setNit(campoNit.getText().trim());
		proveedor.setDireccion(campoDireccion.getText().trim());
		proveedor.setTelefono(campoTelefono.getText().trim());
		proveedor.setCorreoElectronico(campoCorreo.getText().trim());
		return proveedor;
	}
}
