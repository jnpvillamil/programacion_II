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
	private JTextField campoCiudad; 
	private JTextField campoTelefono;
	private JTextField campoCorreo;
	private JTextField campoResponsabilidadFiscal; 
	private JTextField campoResponsabilidadTributaria; 
	private JTextField campoActividadEconomica; 

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
		campoCiudad     = new JTextField();
		campoTelefono   = new JTextField();
		campoCorreo     = new JTextField();
		campoResponsabilidadFiscal = new JTextField();
		campoResponsabilidadTributaria = new JTextField();
		campoActividadEconomica = new JTextField();

		campoCodigo.setEditable(esCreacion);

		panelCampos.add(new JLabel("Código:"));
		panelCampos.add(campoCodigo);
		panelCampos.add(new JLabel("Razón social:"));
		panelCampos.add(campoRazonSocial);
		panelCampos.add(new JLabel("NIT:"));
		panelCampos.add(campoNit);
		panelCampos.add(new JLabel("Dirección:"));
		panelCampos.add(campoDireccion);
		panelCampos.add(new JLabel("Ciudad:"));
		panelCampos.add(campoCiudad);
		panelCampos.add(new JLabel("Teléfono:"));
		panelCampos.add(campoTelefono);
		panelCampos.add(new JLabel("Correo electrónico:"));
		panelCampos.add(campoCorreo);
		panelCampos.add(new JLabel("Responsabilidad Fiscal:"));
		panelCampos.add(campoResponsabilidadFiscal);
		panelCampos.add(new JLabel("Responsabilidad Trinutaria:"));
		panelCampos.add(campoResponsabilidadTributaria);
		panelCampos.add(new JLabel("Actividad económica (CIIU)"));
		panelCampos.add(campoActividadEconomica);
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
		campoCiudad.setText(proveedor.getCiudad() !=null ? proveedor.getCiudad() : "");
		campoTelefono.setText(proveedor.getTelefono());
		campoCorreo.setText(proveedor.getCorreoElectronico());
		campoResponsabilidadFiscal.setText(proveedor.getResponsabilidadFiscal() !=null ? proveedor.getResponsabilidadFiscal() : "");
		campoResponsabilidadTributaria.setText(proveedor.getResponsabilidadTributaria() !=null ? proveedor.getResponsabilidadTributaria() : "");
		campoActividadEconomica.setText(proveedor.getActividadEconomica() !=null ? proveedor.getActividadEconomica() : "");
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
		proveedor.setCiudad(campoCiudad.getText().trim());
		proveedor.setTelefono(campoTelefono.getText().trim());
		proveedor.setCorreoElectronico(campoCorreo.getText().trim());
		proveedor.setResponsabilidadFiscal(campoResponsabilidadFiscal.getText().trim());
		proveedor.setResponsabilidadTributaria(campoResponsabilidadTributaria.getText().trim());
		proveedor.setActividadEcocomica(campoActividadEconomica.getText().trim());
		return proveedor;
	}
}