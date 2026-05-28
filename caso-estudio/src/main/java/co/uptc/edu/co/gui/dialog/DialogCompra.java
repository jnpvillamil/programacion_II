package co.uptc.edu.co.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.co.gui.Evento;
import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.DetalleCompra;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.Proveedor;
import co.uptc.edu.co.modelo.enums.CategoriaProductoEnum;

public class DialogCompra extends JDialog {
	private JTextField campoNumeroFactura;
	private JTextField campoFecha;
	private JComboBox<Proveedor> comboProveedor;

	private JComboBox<Producto> comboProducto;
	private JTextField campoCantidad;
	private JTextField campoCostoUnitario;

	private JTable tablaDetalleCompra;
	private DefaultTableModel modeloTabla;

	private JTextField campoImpuestos;
	private JTextField campoTotalCompra;

	private JButton botonAgregarProducto;
	private JButton botonQuitarProducto;
	private JButton botonGuardar;
	private JButton botonCancelar;

	public DialogCompra(Frame propietario) {
		this(propietario, null);
	}

	public DialogCompra(Frame propietario, Evento evento) {
		super(propietario, "Registrar Compra", true);
		inicializarComponentes();
		configurarDialogo();
		agregarComponentes();
		inicializarEventos(evento);
	}

	private void inicializarComponentes() {
		campoNumeroFactura = new JTextField(15);
		campoFecha = new JTextField(15);
		campoFecha.setText(LocalDate.now().toString());

		comboProveedor = new JComboBox<>();
		comboProveedor.addItem(null);
		comboProducto = new JComboBox<>();
		comboProducto.addItem(null);

		campoCantidad = new JTextField(10);
		campoCostoUnitario = new JTextField(10);

		modeloTabla = new DefaultTableModel();
		modeloTabla.setColumnIdentifiers(
				new Object[] { "Código", "Producto", "Cantidad", "Costo Unitario", "IVA", "Subtotal", "Total" });

		tablaDetalleCompra = new JTable(modeloTabla);

		campoImpuestos = new JTextField(15);
		campoTotalCompra = new JTextField(15);

		botonAgregarProducto = new JButton("Agregar Producto");
		botonQuitarProducto = new JButton("Quitar Producto");
		botonGuardar = new JButton("Guardar");
		botonCancelar = new JButton("Cancelar");

		campoNumeroFactura.setEditable(false);
		campoFecha.setEditable(false);
		campoCostoUnitario.setEditable(false);
		campoImpuestos.setEditable(false);
		campoTotalCompra.setEditable(false);

		botonAgregarProducto.setBackground(new Color(46, 125, 50));
		botonAgregarProducto.setForeground(Color.WHITE);
		botonGuardar.setBackground(new Color(46, 125, 50));
		botonGuardar.setForeground(Color.WHITE);
		botonQuitarProducto.setBackground(new Color(198, 40, 40));
		botonQuitarProducto.setForeground(Color.WHITE);

		campoImpuestos.setEditable(false);
		campoTotalCompra.setEditable(false);

	}

	private void configurarDialogo() {
		setSize(950, 620);
		setLocationRelativeTo(getOwner());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		tablaDetalleCompra.setRowHeight(24);
		tablaDetalleCompra.getTableHeader().setReorderingAllowed(false);
	}

	private void agregarComponentes() {
		JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		panelPrincipal.add(crearPanelDatosCompra(), BorderLayout.NORTH);
		panelPrincipal.add(crearPanelDetalleCompra(), BorderLayout.CENTER);
		panelPrincipal.add(crearPanelInferior(), BorderLayout.SOUTH);

		add(panelPrincipal);
	}

	private JPanel crearPanelDatosCompra() {
		JPanel panelDatos = new JPanel(new GridBagLayout());
		panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de la compra"));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		panelDatos.add(new JLabel("N° Factura Proveedor:"), gbc);

		gbc.gridx = 1;
		panelDatos.add(campoNumeroFactura, gbc);

		gbc.gridx = 2;
		panelDatos.add(new JLabel("Fecha:"), gbc);

		gbc.gridx = 3;
		panelDatos.add(campoFecha, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelDatos.add(new JLabel("Proveedor:"), gbc);

		gbc.gridx = 1;
		gbc.gridwidth = 3;
		panelDatos.add(comboProveedor, gbc);

		return panelDatos;
	}

	private JPanel crearPanelDetalleCompra() {
		JPanel panelDetalle = new JPanel(new BorderLayout(10, 10));
		panelDetalle.setBorder(BorderFactory.createTitledBorder("Detalle de productos"));

		JPanel panelFormularioDetalle = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		panelFormularioDetalle.add(new JLabel("Producto:"), gbc);

		gbc.gridx = 1;
		panelFormularioDetalle.add(comboProducto, gbc);

		gbc.gridx = 2;
		panelFormularioDetalle.add(new JLabel("Cantidad:"), gbc);

		gbc.gridx = 3;
		panelFormularioDetalle.add(campoCantidad, gbc);

		gbc.gridx = 4;
		panelFormularioDetalle.add(new JLabel("Costo Unitario:"), gbc);

		gbc.gridx = 5;
		panelFormularioDetalle.add(campoCostoUnitario, gbc);

		JPanel panelBotonesTabla = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		panelBotonesTabla.add(botonAgregarProducto);
		panelBotonesTabla.add(botonQuitarProducto);

		JScrollPane scrollTabla = new JScrollPane(tablaDetalleCompra);

		JPanel panelSuperior = new JPanel(new BorderLayout());
		panelSuperior.add(panelFormularioDetalle, BorderLayout.NORTH);
		panelSuperior.add(panelBotonesTabla, BorderLayout.SOUTH);

		panelDetalle.add(panelSuperior, BorderLayout.NORTH);
		panelDetalle.add(scrollTabla, BorderLayout.CENTER);

		return panelDetalle;
	}

	private JPanel crearPanelInferior() {
		JPanel panelInferior = new JPanel(new BorderLayout());

		JPanel panelResumen = new JPanel(new GridBagLayout());
		panelResumen.setBorder(BorderFactory.createTitledBorder("Resumen de compra"));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		panelResumen.add(new JLabel("Impuestos:"), gbc);

		gbc.gridx = 1;
		panelResumen.add(campoImpuestos, gbc);

		gbc.gridx = 2;
		panelResumen.add(new JLabel("Total Compra:"), gbc);

		gbc.gridx = 3;
		panelResumen.add(campoTotalCompra, gbc);

		JPanel panelBotonesFinales = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		panelBotonesFinales.add(botonGuardar);
		panelBotonesFinales.add(botonCancelar);

		panelInferior.add(panelResumen, BorderLayout.NORTH);
		panelInferior.add(panelBotonesFinales, BorderLayout.SOUTH);

		return panelInferior;
	}

	private void inicializarEventos(java.awt.event.ActionListener evento) {
		botonCancelar.addActionListener(e -> dispose());
		comboProducto.addActionListener(e -> actualizarCostoUnitarioDesdeProducto());
		botonAgregarProducto.addActionListener(e -> {
			try {
				agregarProductoSeleccionado();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		botonQuitarProducto.addActionListener(e -> quitarFilaSeleccionadaInterna());

		if (evento != null) {
			botonGuardar.setActionCommand(Evento.CMD_CONFIRMAR_COMPRA);
			botonGuardar.addActionListener(evento);
		}
	}

	public void cargarProductos(List<Producto> productos) {
		comboProducto.removeAllItems();

		if (productos == null) {
			return;
		}

		for (Producto producto : productos) {
			if (producto != null && producto.estaActivo()) {
				comboProducto.addItem(producto);
			}
		}
	}

	public void cargarProveedores(List<Proveedor> proveedores) {
		comboProveedor.removeAllItems();
		comboProveedor.addItem(null);

		if (proveedores == null) {
			return;
		}

		for (Proveedor proveedor : proveedores) {
			if (proveedor != null && proveedor.estaActivo()) {
				comboProveedor.addItem(proveedor);
			}
		}
	}

	private void agregarProductoSeleccionado() throws Exception {
		Producto producto = (Producto) comboProducto.getSelectedItem();

		if (producto == null) {
			throw new Exception("Debe seleccionar un producto.");
		}
		int cantidad;
		try {
			cantidad = Integer.parseInt(campoCantidad.getText().trim());
		} catch (NumberFormatException e) {
			throw new Exception("La cantidad debe ser un número entero válido.");
		}

		if (cantidad <= 0) {
			throw new Exception("La cantidad debe ser mayor que cero.");
		}

		double costoUnitario = obtenerCostoUnitarioProducto();
		double subtotal = costoUnitario * cantidad;
		double impuesto = subtotal * obtenerTasaIva(producto.getCategoria());
		double total = subtotal + impuesto;

		modeloTabla.addRow(new Object[] { producto.getCodigoProducto(), producto.getNombreProducto(), cantidad,
				costoUnitario, impuesto, subtotal, total });
		campoCostoUnitario.setText(String.valueOf(costoUnitario));
		actualizarResumenCompra();
		campoCantidad.setText("");
	}

	private void quitarFilaSeleccionadaInterna() {
		int filaSeleccionada = tablaDetalleCompra.getSelectedRow();
		if (filaSeleccionada != -1) {
			modeloTabla.removeRow(filaSeleccionada);
			actualizarResumenCompra();
		}
	}

	private void actualizarResumenCompra() {
		// TODO Auto-generated method stub
		double totalImpuestos = 0.0;
		double totalCompra = 0.0;

		for (int i = 0; i < modeloTabla.getRowCount(); i++) {
			totalImpuestos += Double.parseDouble(modeloTabla.getValueAt(i, 4).toString());
			totalCompra += Double.parseDouble(modeloTabla.getValueAt(i, 6).toString());
		}

		campoImpuestos.setText(String.valueOf(totalImpuestos));
		campoTotalCompra.setText(String.valueOf(totalCompra));
	}

	private void actualizarCostoUnitarioDesdeProducto() {
		double costoUnitario = obtenerCostoUnitarioProducto();
		if (costoUnitario > 0) {
			campoCostoUnitario.setText(String.valueOf(costoUnitario));
		}
	}

	private double obtenerCostoUnitarioProducto() {
		// TODO Auto-generated method stub
		Producto producto = (Producto) comboProducto.getSelectedItem();
		return producto != null ? producto.getPrecioCompra() : 0.0;
	}

	private double obtenerTasaIva(CategoriaProductoEnum categoria) {
		if (categoria == null) {
			return 0.19;
		}

		switch (categoria) {
		case ALIMENTOS:
			return 0.05;
		case ASEO:
		case PAPELERIA:
		default:
			return 0.19;
		}
	}

	public JTable getTablaDetalleCompra() {
		return tablaDetalleCompra;
	}

	public DefaultTableModel getModeloTabla() {
		return modeloTabla;
	}

	public JButton getBotonAgregarProducto() {
		return botonAgregarProducto;
	}

	public JButton getBotonQuitarProducto() {
		return botonQuitarProducto;
	}

	public JButton getBotonGuardar() {
		return botonGuardar;
	}

	public JButton getBotonCancelar() {
		return botonCancelar;
	}

	public JComboBox<Proveedor> getComboProveedor() {
		return comboProveedor;
	}

	public JComboBox<Producto> getComboProducto() {
		return comboProducto;
	}

	public JTextField getCampoNumeroFactura() {
		return campoNumeroFactura;
	}

	public JTextField getCampoFecha() {
		return campoFecha;
	}

	public JTextField getCampoCantidad() {
		return campoCantidad;
	}

	public JTextField getCampoCostoUnitario() {
		return campoCostoUnitario;
	}

	public JTextField getCampoImpuestos() {
		return campoImpuestos;
	}

	public JTextField getCampoTotalCompra() {
		return campoTotalCompra;
	}

	public void limpiarTabla() {
		modeloTabla.setRowCount(0);
		actualizarResumenCompra();
	}

	public void agregarFilaProducto(Object[] fila) {
		modeloTabla.addRow(fila);
		actualizarResumenCompra();
	}

	public void quitarFilaSeleccionada() {
		quitarFilaSeleccionadaInterna();
	}

	public Compra obtenerCompra() throws Exception {
		String numeroFactura = campoNumeroFactura.getText().trim();
		Proveedor proveedor = (Proveedor) comboProveedor.getSelectedItem();

		if (numeroFactura.isEmpty()) {
			throw new Exception("El número de factura es obligatorio.");
		}

		if (proveedor == null) {
			throw new Exception("Debe seleccionar un proveedor.");
		}

		if (modeloTabla.getRowCount() == 0) {
			throw new Exception("Debe agregar al menos un producto a la compra.");
		}

		List<DetalleCompra> detalles = new ArrayList<>();
		double subtotalCompra = 0.0;
		double impuestosCompra = 0.0;
		double totalCompra = 0.0;

		for (int fila = 0; fila < modeloTabla.getRowCount(); fila++) {
			Producto producto = obtenerProductoDesdeFila(fila);
			int cantidad = Integer.parseInt(modeloTabla.getValueAt(fila, 2).toString());
			double costoUnitario = Double.parseDouble(modeloTabla.getValueAt(fila, 3).toString());
			double impuestos = Double.parseDouble(modeloTabla.getValueAt(fila, 4).toString());
			double subtotal = Double.parseDouble(modeloTabla.getValueAt(fila, 5).toString());
			double total = Double.parseDouble(modeloTabla.getValueAt(fila, 6).toString());

			detalles.add(new DetalleCompra(producto, cantidad, costoUnitario, subtotal, impuestos, total));
			subtotalCompra += subtotal;
			impuestosCompra += impuestos;
			totalCompra += total;
		}

		Compra compra = new Compra();
		compra.setNumeroFacturaProveedor(numeroFactura);
		compra.setFecha(LocalDate.now());
		compra.setCodigoProveedor(proveedor.getCodigoProveedor());
		compra.setDetalles(detalles);
		compra.setSubtotal(subtotalCompra);
		compra.setImpuestos(impuestosCompra);
		compra.setTotalCompra(totalCompra);
		return compra;
	}

	private Producto obtenerProductoDesdeFila(int fila) {
		String codigoProducto = modeloTabla.getValueAt(fila, 0).toString();
		Producto producto = (Producto) comboProducto.getSelectedItem();
		if (producto != null && codigoProducto.equalsIgnoreCase(producto.getCodigoProducto())) {
			return producto;
		}
		Producto productoFila = new Producto();
		productoFila.setCodigoProducto(codigoProducto);
		productoFila.setNombreProducto(modeloTabla.getValueAt(fila, 1).toString());
		return productoFila;
	}

	public String obtenerNumeroFactura() {
		return campoNumeroFactura.getText().trim();
	}

	public String obtenerFecha() {
		return campoFecha.getText().trim();
	}

	public String obtenerProveedor() {
		Object proveedorSeleccionado = comboProveedor.getSelectedItem();
		return proveedorSeleccionado != null ? ((Proveedor) proveedorSeleccionado).getCodigoProveedor() : "";
	}

	public Proveedor obtenerProveedorObjeto() {
		return (Proveedor) comboProveedor.getSelectedItem();
	}

	public Producto obtenerProductoObjeto() {
		return (Producto) comboProducto.getSelectedItem();
	}

	public String obtenerImpuestos() {
		return campoImpuestos.getText().trim();
	}

	public String obtenerTotalCompra() {
		return campoTotalCompra.getText().trim();
	}

}
