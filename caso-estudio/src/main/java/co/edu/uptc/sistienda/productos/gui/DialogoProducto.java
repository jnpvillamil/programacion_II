package co.edu.uptc.sistienda.productos.gui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import co.edu.uptc.sistienda.comun.gui.DialogoCrudAbstracto;
import co.edu.uptc.sistienda.gui.Evento;
import co.edu.uptc.sistienda.modelo.Producto;
import co.edu.uptc.sistienda.modelo.enums.CategoriaProductoEnum;
import co.edu.uptc.sistienda.modelo.enums.TipoImpuestoEnum;

 //Diálogo concreto para Crear o Editar un Producto
 //Hereda el algoritmo de DialogoCrudAbstracto

public class DialogoProducto extends DialogoCrudAbstracto {

	private JTextField          campoCodigo;
	private JTextField          campoNombre;
	private JComboBox<CategoriaProductoEnum> comboCategorias;
	private JTextField          campoPrecioCompra;
	private JTextField          campoPrecioVenta;
	private JTextField          campoStockActual;
	private JTextField          campoStockMinimo;
	private JComboBox<TipoImpuestoEnum>  comboTipoImpuesto; 

	public DialogoProducto(Evento evento, boolean esCreacion) {
		super(evento, esCreacion ? "Nuevo Producto" : "Editar Producto", esCreacion);
		setSize(400, 340);
	}

	@Override
	public void construirCamposFormulario(JPanel panelCampos) {
		campoCodigo       = new JTextField();
		campoNombre       = new JTextField();
		comboCategorias   = new JComboBox<>(CategoriaProductoEnum.values());
		campoPrecioCompra = new JTextField();
		campoPrecioVenta  = new JTextField();
		campoStockActual  = new JTextField();
		campoStockMinimo  = new JTextField();
		comboTipoImpuesto = new JComboBox<>(TipoImpuestoEnum.values());

		// En modo edición, el código no se puede cambiar
		campoCodigo.setEditable(esCreacion);

		panelCampos.add(new JLabel("Código interno:"));
		panelCampos.add(campoCodigo);
		panelCampos.add(new JLabel("Nombre:"));
		panelCampos.add(campoNombre);
		panelCampos.add(new JLabel("Categoría:"));
		panelCampos.add(comboCategorias);
		panelCampos.add(new JLabel("Tipo de impuesto:"));
		panelCampos.add(comboTipoImpuesto);
		panelCampos.add(new JLabel("Precio compra:"));
		panelCampos.add(campoPrecioCompra);
		panelCampos.add(new JLabel("Precio venta:"));
		panelCampos.add(campoPrecioVenta);
		panelCampos.add(new JLabel("Stock actual:"));
		panelCampos.add(campoStockActual);
		panelCampos.add(new JLabel("Stock mínimo:"));
		panelCampos.add(campoStockMinimo);
	}

	@Override
	public void asignarComandosBotones() {
		botonGuardar.setActionCommand(esCreacion
				? Evento.GUARDAR_PRODUCTO
				: Evento.ACTUALIZAR_PRODUCTO);
		botonCancelar.setActionCommand(Evento.CANCELAR_PRODUCTO);
	}

	@Override
	public void cargarDatosEnFormulario(Object objeto) {
		Producto producto = (Producto) objeto;
		campoCodigo.setText(producto.getCodigoInterno());
		campoNombre.setText(producto.getNombreProducto());
		comboCategorias.setSelectedItem(producto.getCategoria());
		comboTipoImpuesto.setSelectedItem(producto.getTipoImpuesto() !=null ? producto.getTipoImpuesto() : TipoImpuestoEnum.IVA);
		campoPrecioCompra.setText(String.valueOf(producto.getPrecioCompra()));
		campoPrecioVenta.setText(String.valueOf(producto.getPrecioVenta()));
		campoStockActual.setText(String.valueOf(producto.getStockActual()));
		campoStockMinimo.setText(String.valueOf(producto.getStockMinimo()));
	}

	//Captura los datos del formulario y construye un objeto Producto
	public Producto capturarDatosFormulario() throws Exception {
		if (campoCodigo.getText().trim().isEmpty())
			throw new Exception("El código interno no puede estar vacío.");
		if (campoNombre.getText().trim().isEmpty())
			throw new Exception("El nombre del producto no puede estar vacío.");

		Producto producto = new Producto();
		producto.setCodigoInterno(campoCodigo.getText().trim());
		producto.setNombreProducto(campoNombre.getText().trim());
		producto.setCategoria((CategoriaProductoEnum) comboCategorias.getSelectedItem());
		producto.setTipoImpuesto((TipoImpuestoEnum)comboTipoImpuesto.getSelectedItem());
		producto.setPrecioCompra(Double.parseDouble(campoPrecioCompra.getText().trim()));
		producto.setPrecioVenta(Double.parseDouble(campoPrecioVenta.getText().trim()));
		producto.setStockActual(Integer.parseInt(campoStockActual.getText().trim()));
		producto.setStockMinimo(Integer.parseInt(campoStockMinimo.getText().trim()));
		return producto;
	}
}