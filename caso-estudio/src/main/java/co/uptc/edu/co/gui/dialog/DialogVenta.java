package co.uptc.edu.co.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
import co.uptc.edu.co.modelo.Cliente;
import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.enums.EstadoVentaEnum;

public class DialogVenta extends JDialog {

	private static final double IVA = 0.19;
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

	private JTextField campoNumeroFactura;
	private JTextField campoFecha;
	private JTextField campoHora;
	private JComboBox<Cliente> comboCliente;
	private JComboBox<String> comboFormaPago;

	private JComboBox<Producto> comboProducto;
	private JTextField campoCantidad;
	private JTextField campoPrecioUnitario;

	private JTable tablaProductos;
	private DefaultTableModel modeloTabla;

	private JTextField campoSubtotal;
	private JTextField campoIva;
	private JTextField campoTotal;

	private JButton botonAgregarProducto;
	private JButton botonQuitarProducto;
	private JButton botonGuardar;
	private JButton botonCancelar;

	public DialogVenta(Frame propietario) {
		this(propietario, null);
	}

	public DialogVenta(Frame propietario, Evento evento) {
		super(propietario, "Registrar Venta", true);
		inicializarComponentes();
		configurarDialogo();
		agregarComponentes();
		inicializarEventos(evento);
	}

	private void inicializarComponentes() {
		campoNumeroFactura = new JTextField(18);
		campoFecha = new JTextField(18);
		campoHora = new JTextField(18);

		comboCliente = new JComboBox<>();

		comboFormaPago = new JComboBox<>();
		comboFormaPago.addItem("Efectivo");
		comboFormaPago.addItem("Transferencia");
		comboFormaPago.addItem("Tarjeta");
		comboFormaPago.addItem("Credito");

		comboProducto = new JComboBox<>();

		campoCantidad = new JTextField(10);
		campoPrecioUnitario = new JTextField(12);

		modeloTabla = new DefaultTableModel(
				new String[] { "Codigo", "Producto", "Cantidad", "Precio Unitario", "IVA", "Subtotal" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaProductos = new JTable(modeloTabla);

		campoSubtotal = new JTextField(12);
		campoIva = new JTextField(12);
		campoTotal = new JTextField(12);

		botonAgregarProducto = new JButton("Agregar Producto");
		botonQuitarProducto = new JButton("Quitar Producto");
		botonGuardar = new JButton("Guardar");
		botonCancelar = new JButton("Cancelar");

		botonAgregarProducto.setBackground(new Color(46, 125, 50));
		botonAgregarProducto.setForeground(Color.WHITE);

		botonGuardar.setBackground(new Color(46, 125, 50));
		botonGuardar.setForeground(Color.WHITE);

		botonQuitarProducto.setBackground(new Color(198, 40, 40));
		botonQuitarProducto.setForeground(Color.WHITE);

		campoSubtotal.setEditable(false);
		campoIva.setEditable(false);
		campoTotal.setEditable(false);

		campoFecha.setText(LocalDate.now().format(FORMATO_FECHA));
		campoHora.setText(LocalTime.now().format(FORMATO_HORA));
	}

	private void configurarDialogo() {
		setSize(950, 650);
		setLocationRelativeTo(getOwner());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);

		tablaProductos.setRowHeight(25);
		tablaProductos.getTableHeader().setReorderingAllowed(false);
	}

	private void agregarComponentes() {
		JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		panelPrincipal.add(crearPanelDatosGenerales(), BorderLayout.NORTH);
		panelPrincipal.add(crearPanelCentro(), BorderLayout.CENTER);
		panelPrincipal.add(crearPanelInferior(), BorderLayout.SOUTH);

		add(panelPrincipal);
	}

	private JPanel crearPanelDatosGenerales() {
		JPanel panelSuperior = new JPanel(new GridBagLayout());
		panelSuperior.setBorder(BorderFactory.createTitledBorder("Datos de la venta"));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		panelSuperior.add(new JLabel("N. Factura:"), gbc);

		gbc.gridx = 1;
		panelSuperior.add(campoNumeroFactura, gbc);

		gbc.gridx = 2;
		panelSuperior.add(new JLabel("Fecha:"), gbc);

		gbc.gridx = 3;
		panelSuperior.add(campoFecha, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelSuperior.add(new JLabel("Hora:"), gbc);

		gbc.gridx = 1;
		panelSuperior.add(campoHora, gbc);

		gbc.gridx = 2;
		panelSuperior.add(new JLabel("Cliente:"), gbc);

		gbc.gridx = 3;
		panelSuperior.add(comboCliente, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panelSuperior.add(new JLabel("Forma de pago:"), gbc);

		gbc.gridx = 1;
		panelSuperior.add(comboFormaPago, gbc);

		return panelSuperior;
	}

	private JPanel crearPanelCentro() {
		JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
		panelCentro.add(crearPanelAgregarProducto(), BorderLayout.NORTH);
		panelCentro.add(crearPanelTabla(), BorderLayout.CENTER);
		return panelCentro;
	}

	private JPanel crearPanelAgregarProducto() {
		JPanel panelProducto = new JPanel(new GridBagLayout());
		panelProducto.setBorder(BorderFactory.createTitledBorder("Agregar producto"));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		panelProducto.add(new JLabel("Producto:"), gbc);

		gbc.gridx = 1;
		panelProducto.add(comboProducto, gbc);

		gbc.gridx = 2;
		panelProducto.add(new JLabel("Cantidad:"), gbc);

		gbc.gridx = 3;
		panelProducto.add(campoCantidad, gbc);

		gbc.gridx = 4;
		panelProducto.add(new JLabel("Precio Unitario:"), gbc);

		gbc.gridx = 5;
		panelProducto.add(campoPrecioUnitario, gbc);

		gbc.gridx = 6;
		panelProducto.add(botonAgregarProducto, gbc);

		return panelProducto;
	}

	private JScrollPane crearPanelTabla() {
		JScrollPane scrollTabla = new JScrollPane(tablaProductos);
		scrollTabla.setBorder(BorderFactory.createTitledBorder("Detalle de productos vendidos"));
		return scrollTabla;
	}

	private JPanel crearPanelInferior() {
		JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
		panelInferior.add(crearPanelResumen(), BorderLayout.CENTER);
		panelInferior.add(crearPanelAcciones(), BorderLayout.SOUTH);
		return panelInferior;
	}

	private JPanel crearPanelResumen() {
		JPanel panelResumen = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		panelResumen.setBorder(BorderFactory.createTitledBorder("Resumen"));

		panelResumen.add(new JLabel("Subtotal:"));
		panelResumen.add(campoSubtotal);

		panelResumen.add(new JLabel("IVA:"));
		panelResumen.add(campoIva);

		panelResumen.add(new JLabel("Total:"));
		panelResumen.add(campoTotal);

		return panelResumen;
	}

	private JPanel crearPanelAcciones() {
		JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		panelAcciones.add(botonQuitarProducto);
		panelAcciones.add(botonGuardar);
		panelAcciones.add(botonCancelar);
		return panelAcciones;
	}

	private void inicializarEventos(Evento evento) {
		botonCancelar.addActionListener(e -> dispose());
		botonAgregarProducto.addActionListener(e -> agregarProductoSeleccionado());
		botonQuitarProducto.addActionListener(e -> quitarProductoSeleccionado());

		if (evento != null) {
			botonGuardar.setActionCommand(Evento.CMD_CONFIRMAR_VENTA);
			botonGuardar.addActionListener(evento);
		}
	}

	public void cargarClientes(List<Cliente> clientes) {
		comboCliente.removeAllItems();

		for (Cliente cliente : clientes) {
			comboCliente.addItem(cliente);
		}
	}

	public void cargarProductos(List<Producto> productos) {
		comboProducto.removeAllItems();

		for (Producto producto : productos) {
			if (producto.estaActivo()) {
				comboProducto.addItem(producto);
			}
		}
	}

	public Venta obtenerVenta() throws Exception {
		String numeroFactura = campoNumeroFactura.getText().trim();
		Cliente cliente = (Cliente) comboCliente.getSelectedItem();
		String formaPago = comboFormaPago.getSelectedItem().toString();
		LocalDateTime fechaHora = obtenerFechaHora();
		List<DetalleVenta> detalles = obtenerDetallesVenta();

		if (numeroFactura.isEmpty()) {
			throw new Exception("El numero de factura es obligatorio.");
		}

		if (cliente == null) {
			throw new Exception("Debe seleccionar un cliente.");
		}

		if (detalles.isEmpty()) {
			throw new Exception("Debe agregar al menos un producto.");
		}

		return new Venta(numeroFactura, fechaHora, cliente.toString(), detalles, 0, formaPago, 0, 0,
				EstadoVentaEnum.ACTIVA);
	}

	public void agregarFilaProducto(Object[] fila) {
		modeloTabla.addRow(fila);
	}

	public void quitarFilaSeleccionada() {
		int filaSeleccionada = tablaProductos.getSelectedRow();
		if (filaSeleccionada != -1) {
			modeloTabla.removeRow(filaSeleccionada);
		}
	}

	public void limpiarTabla() {
		modeloTabla.setRowCount(0);
	}

	private void agregarProductoSeleccionado() {
		try {
			Producto producto = (Producto) comboProducto.getSelectedItem();

			if (producto == null) {
				throw new Exception("Debe seleccionar un producto.");
			}

			int cantidad = convertirEntero(campoCantidad.getText().trim(), "La cantidad debe ser numerica.");
			double precioUnitario = convertirDouble(
					campoPrecioUnitario.getText().trim(),
					"El precio unitario debe ser numerico."
			);

			if (cantidad <= 0) {
				throw new Exception("La cantidad debe ser mayor que cero.");
			}

			if (precioUnitario <= 0) {
				throw new Exception("El precio unitario debe ser mayor que cero.");
			}

			double subtotal = cantidad * precioUnitario;
			double iva = subtotal * IVA;

			Object[] fila = {
					producto.getCodigoProducto(),
					producto.getNombreProducto(),
					cantidad,
					precioUnitario,
					iva,
					subtotal
			};

			modeloTabla.addRow(fila);
			actualizarResumen();
			limpiarCamposProducto();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void quitarProductoSeleccionado() {
		quitarFilaSeleccionada();
		actualizarResumen();
	}

	private List<DetalleVenta> obtenerDetallesVenta() {
		List<DetalleVenta> detalles = new ArrayList<>();

		for (int fila = 0; fila < modeloTabla.getRowCount(); fila++) {
			Producto producto = new Producto();
			producto.setCodigoProducto(modeloTabla.getValueAt(fila, 0).toString());
			producto.setNombreProducto(modeloTabla.getValueAt(fila, 1).toString());

			int cantidad = Integer.parseInt(modeloTabla.getValueAt(fila, 2).toString());
			double precioUnitario = Double.parseDouble(modeloTabla.getValueAt(fila, 3).toString());
			double subtotal = Double.parseDouble(modeloTabla.getValueAt(fila, 5).toString());

			detalles.add(new DetalleVenta(producto, cantidad, precioUnitario, subtotal));
		}

		return detalles;
	}

	private LocalDateTime obtenerFechaHora() throws Exception {
		try {
			LocalDate fecha = LocalDate.parse(campoFecha.getText().trim(), FORMATO_FECHA);
			LocalTime hora = LocalTime.parse(campoHora.getText().trim(), FORMATO_HORA);
			return LocalDateTime.of(fecha, hora);
		} catch (Exception e) {
			throw new Exception("La fecha y hora deben tener formato yyyy-MM-dd y HH:mm.");
		}
	}

	private void actualizarResumen() {
		double subtotal = 0;

		for (int fila = 0; fila < modeloTabla.getRowCount(); fila++) {
			subtotal += Double.parseDouble(modeloTabla.getValueAt(fila, 5).toString());
		}

		double iva = subtotal * IVA;
		double total = subtotal + iva;

		campoSubtotal.setText(String.valueOf(subtotal));
		campoIva.setText(String.valueOf(iva));
		campoTotal.setText(String.valueOf(total));
	}

	private void limpiarCamposProducto() {
		campoCantidad.setText("");
		campoPrecioUnitario.setText("");
	}

	private int convertirEntero(String texto, String mensajeError) throws Exception {
		try {
			return Integer.parseInt(texto);
		} catch (NumberFormatException e) {
			throw new Exception(mensajeError);
		}
	}

	private double convertirDouble(String texto, String mensajeError) throws Exception {
		try {
			return Double.parseDouble(texto);
		} catch (NumberFormatException e) {
			throw new Exception(mensajeError);
		}
	}
}
