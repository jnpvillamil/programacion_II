package co.edu.uptc.sistienda.compras.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.sistienda.compras.modelo.Compra;
import co.edu.uptc.sistienda.compras.modelo.DetalleCompra;
import co.edu.uptc.sistienda.gui.Evento;
import co.edu.uptc.sistienda.modelo.Producto;
import co.edu.uptc.sistienda.modelo.Proveedor;

public class PanelRegistrarCompra extends JPanel {

	private JTextField campoNumeroCompra;
	private JTextField campoFechaCompra;
	private JComboBox<String> comboProveedor;
	private JComboBox<String> comboProducto;
	private JTextField campoCantidad;
	private JTextField campoPrecioCompra;
	private JTable tablaDetalleCompra;
	private DefaultTableModel modeloTablaDetalle;
	private JLabel etiquetaTotalCompra;
	private JTextField campoProveedorConsulta;
	private JTextField campoFechaInicioConsulta;
	private JTextField campoFechaFinConsulta;
	private JTable tablaComprasRegistradas;
	private DefaultTableModel modeloComprasRegistradas;
	private List<Producto> productosDisponibles = new ArrayList<>();
	private List<Proveedor> proveedoresDisponibles = new ArrayList<>();
	private List<Compra> comprasRegistradas = new ArrayList<>();
	private List<DetalleCompra> detallesCompra = new ArrayList<>();
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public PanelRegistrarCompra(Evento evento) {
		setLayout(new BorderLayout());

		JPanel encabezado = new JPanel(new BorderLayout());
		JLabel titulo = new JLabel("Compras - Registrar entrada de inventario");
		titulo.setBorder(BorderFactory.createEmptyBorder(6, 8, 4, 8));
		encabezado.add(titulo, BorderLayout.NORTH);
		encabezado.add(construirPanelDatosCompra(), BorderLayout.CENTER);
		encabezado.add(construirPanelAgregarProducto(), BorderLayout.SOUTH);

		add(encabezado, BorderLayout.NORTH);
		add(construirTablaDetalleCompra(), BorderLayout.CENTER);
		add(construirPanelAcciones(evento), BorderLayout.SOUTH);
		add(construirPanelConsultasCompras(), BorderLayout.EAST);
	}

	private JPanel construirPanelDatosCompra() {
		JPanel panel = new JPanel(new GridLayout(2, 3, 6, 4));
		panel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

		panel.add(new JLabel("N. COMPRA"));
		panel.add(new JLabel("FECHA"));
		panel.add(new JLabel("PROVEEDOR"));

		campoNumeroCompra = new JTextField();
		campoNumeroCompra.setEditable(false);

		campoFechaCompra = new JTextField();
		campoFechaCompra.setEditable(false);

		comboProveedor = new JComboBox<>();

		panel.add(campoNumeroCompra);
		panel.add(campoFechaCompra);
		panel.add(comboProveedor);

		return panel;
	}

	private JPanel construirPanelAgregarProducto() {
		JPanel panel = new JPanel(new GridLayout(2, 5, 6, 4));
		panel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

		panel.add(new JLabel("PRODUCTO"));
		panel.add(new JLabel("CANTIDAD"));
		panel.add(new JLabel("PRECIO COMPRA"));
		panel.add(new JLabel(""));
		panel.add(new JLabel(""));

		comboProducto = new JComboBox<>();
		campoCantidad = new JTextField();
		campoPrecioCompra = new JTextField();

		JButton botonAgregar = new JButton("Agregar producto");
		botonAgregar.addActionListener(e -> agregarProductoAlDetalle());

		JButton botonQuitar = new JButton("Quitar seleccionado");
		botonQuitar.addActionListener(e -> quitarProductoSeleccionado());

		panel.add(comboProducto);
		panel.add(campoCantidad);
		panel.add(campoPrecioCompra);
		panel.add(botonAgregar);
		panel.add(botonQuitar);

		return panel;
	}

	private JScrollPane construirTablaDetalleCompra() {
		modeloTablaDetalle = new DefaultTableModel() {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};

		modeloTablaDetalle.addColumn("Codigo");
		modeloTablaDetalle.addColumn("Producto");
		modeloTablaDetalle.addColumn("Cantidad");
		modeloTablaDetalle.addColumn("Precio compra");
		modeloTablaDetalle.addColumn("Subtotal");

		tablaDetalleCompra = new JTable(modeloTablaDetalle);
		tablaDetalleCompra.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaDetalleCompra.getTableHeader().setReorderingAllowed(false);
		tablaDetalleCompra.setRowHeight(24);

		JScrollPane scroll = new JScrollPane(tablaDetalleCompra);
		scroll.setPreferredSize(new Dimension(0, 180));
		return scroll;
	}

	private JPanel construirPanelAcciones(Evento evento) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(4, 8, 8, 8));

		etiquetaTotalCompra = new JLabel("TOTAL: $0");
		panel.add(etiquetaTotalCompra, BorderLayout.WEST);

		JPanel botones = new JPanel();

		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(e -> limpiarDetalle());

		JButton botonGuardar = new JButton("Registrar compra");
		botonGuardar.setActionCommand(Evento.REGISTRAR_COMPRA);
		botonGuardar.addActionListener(evento);

		botones.add(botonCancelar);
		botones.add(botonGuardar);
		panel.add(botones, BorderLayout.EAST);

		return panel;
	}

	public void iniciarNuevaCompra(String numeroCompra, LocalDate fechaCompra, List<Proveedor> proveedores,
			List<Producto> productos) {
		iniciarNuevaCompra(numeroCompra, fechaCompra, proveedores, productos, new ArrayList<>());
	}

	public void iniciarNuevaCompra(String numeroCompra, LocalDate fechaCompra, List<Proveedor> proveedores,
			List<Producto> productos, List<Compra> compras) {
		campoNumeroCompra.setText(numeroCompra);
		campoFechaCompra.setText(fechaCompra.toString());
		proveedoresDisponibles = new ArrayList<>(proveedores);
		productosDisponibles = new ArrayList<>(productos);
		comprasRegistradas = new ArrayList<>(compras);
		llenarComboProveedores();
		llenarComboProductos();
		limpiarDetalle();
		poblarTablaCompras(comprasRegistradas);
	}

	public Compra construirCompraDesdeFormulario() {
		Proveedor proveedor = obtenerProveedorSeleccionado();
		Compra compra = new Compra(campoNumeroCompra.getText(), proveedor);
		compra.setFechaCompra(LocalDate.parse(campoFechaCompra.getText()));
		compra.setDetalles(new ArrayList<>(detallesCompra));
		compra.calcularTotalCompra();
		return compra;
	}

	private void agregarProductoAlDetalle() {
		try {
			Producto producto = obtenerProductoSeleccionado();
			if (producto == null) {
				JOptionPane.showMessageDialog(this, "Seleccione un producto.");
				return;
			}

			int cantidad = Integer.parseInt(campoCantidad.getText().trim());
			double precioCompra = Double.parseDouble(campoPrecioCompra.getText().trim());

			if (cantidad <= 0 || precioCompra <= 0) {
				JOptionPane.showMessageDialog(this, "Cantidad y precio deben ser mayores a cero.");
				return;
			}

			detallesCompra.add(new DetalleCompra(producto, cantidad, precioCompra));
			campoCantidad.setText("");
			campoPrecioCompra.setText("");
			refrescarTabla();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Digite valores numericos validos.");
		}
	}

	private void quitarProductoSeleccionado() {
		int fila = tablaDetalleCompra.getSelectedRow();
		if (fila >= 0 && fila < detallesCompra.size()) {
			detallesCompra.remove(fila);
			refrescarTabla();
		}
	}

	private void limpiarDetalle() {
		detallesCompra.clear();
		modeloTablaDetalle.setRowCount(0);
		recalcularTotal();
		campoCantidad.setText("");
		campoPrecioCompra.setText("");
	}

	private void refrescarTabla() {
		modeloTablaDetalle.setRowCount(0);
		for (DetalleCompra detalle : detallesCompra) {
			modeloTablaDetalle.addRow(
					new Object[] { detalle.getProducto().getCodigoInterno(), detalle.getProducto().getNombreProducto(),
							detalle.getCantidad(), "$" + String.format("%,.0f", detalle.getPrecioCompra()),
							"$" + String.format("%,.0f", detalle.getSubtotal()) });
		}
		recalcularTotal();
	}

	private void recalcularTotal() {
		double total = 0;
		for (DetalleCompra detalle : detallesCompra) {
			total += detalle.getSubtotal();
		}
		etiquetaTotalCompra.setText("TOTAL: $" + String.format("%,.0f", total));
	}

	private void llenarComboProveedores() {
		comboProveedor.removeAllItems();
		comboProveedor.addItem("- Seleccionar proveedor");
		for (Proveedor proveedor : proveedoresDisponibles) {
			if (proveedor.isActivo()) {
				comboProveedor.addItem(proveedor.getCodigoProveedor() + " - " + proveedor.getRazonSocial());
			}
		}
	}

	private void llenarComboProductos() {
		comboProducto.removeAllItems();
		comboProducto.addItem("- Seleccionar producto");
		for (Producto producto : productosDisponibles) {
			if (producto.isActivo()) {
				comboProducto.addItem(producto.getCodigoInterno() + " - " + producto.getNombreProducto());
			}
		}
	}

	private Proveedor obtenerProveedorSeleccionado() {
		Object seleccionado = comboProveedor.getSelectedItem();
		if (seleccionado == null || seleccionado.toString().startsWith("-")) {
			return null;
		}
		String codigo = seleccionado.toString().split(" - ")[0].trim();
		for (Proveedor proveedor : proveedoresDisponibles) {
			if (proveedor.getCodigoProveedor().equalsIgnoreCase(codigo)) {
				return proveedor;
			}
		}
		return null;
	}

	private JPanel construirPanelConsultasCompras() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(330, 0));
		panel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

		JPanel filtros = new JPanel(new GridLayout(4, 2, 4, 4));
		filtros.add(new JLabel("Proveedor"));
		campoProveedorConsulta = new JTextField();
		filtros.add(campoProveedorConsulta);
		filtros.add(new JLabel("Desde dd/mm/aaaa"));
		campoFechaInicioConsulta = new JTextField(LocalDate.now().withDayOfMonth(1).format(FORMATO_FECHA));
		filtros.add(campoFechaInicioConsulta);
		filtros.add(new JLabel("Hasta dd/mm/aaaa"));
		campoFechaFinConsulta = new JTextField(LocalDate.now().format(FORMATO_FECHA));
		filtros.add(campoFechaFinConsulta);

		JButton botonConsultar = new JButton("Consultar compras");
		botonConsultar.addActionListener(e -> consultarComprasRegistradas());
		JButton botonTodas = new JButton("Todas");
		botonTodas.addActionListener(e -> poblarTablaCompras(comprasRegistradas));
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
		botones.add(botonConsultar);
		botones.add(botonTodas);
		filtros.add(botones);

		modeloComprasRegistradas = new DefaultTableModel() {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		modeloComprasRegistradas.addColumn("Compra");
		modeloComprasRegistradas.addColumn("Fecha");
		modeloComprasRegistradas.addColumn("Proveedor");
		modeloComprasRegistradas.addColumn("Total");
		modeloComprasRegistradas.addColumn("Estado");
		tablaComprasRegistradas = new JTable(modeloComprasRegistradas);
		tablaComprasRegistradas.setRowHeight(22);

		panel.add(new JLabel("Consulta de compras"), BorderLayout.NORTH);
		panel.add(filtros, BorderLayout.CENTER);
		panel.add(new JScrollPane(tablaComprasRegistradas), BorderLayout.SOUTH);
		return panel;
	}

	private void consultarComprasRegistradas() {
		LocalDate inicio = leerFechaConsulta(campoFechaInicioConsulta.getText());
		LocalDate fin = leerFechaConsulta(campoFechaFinConsulta.getText());
		if (inicio == null || fin == null) {
			return;
		}
		String textoProveedor = campoProveedorConsulta.getText().trim().toLowerCase();
		List<Compra> filtradas = new ArrayList<>();
		for (Compra compra : comprasRegistradas) {
			String codigoProveedor = compra.getProveedor() != null ? compra.getProveedor().getCodigoProveedor() : "";
			String nombreProveedor = compra.getProveedor() != null ? compra.getProveedor().getRazonSocial() : "";
			boolean coincideProveedor = textoProveedor.isEmpty()
					|| codigoProveedor.toLowerCase().contains(textoProveedor)
					|| nombreProveedor.toLowerCase().contains(textoProveedor);
			boolean coincideFecha = compra.getFechaCompra() != null && !compra.getFechaCompra().isBefore(inicio)
					&& !compra.getFechaCompra().isAfter(fin);
			if (coincideProveedor && coincideFecha) {
				filtradas.add(compra);
			}
		}
		poblarTablaCompras(filtradas);
	}

	private LocalDate leerFechaConsulta(String texto) {
		try {
			return LocalDate.parse(texto.trim(), FORMATO_FECHA);
		} catch (DateTimeParseException ex) {
			JOptionPane.showMessageDialog(this, "Use fechas con formato dd/mm/aaaa.");
			return null;
		}
	}

	private void poblarTablaCompras(List<Compra> compras) {
		if (modeloComprasRegistradas == null) {
			return;
		}
		modeloComprasRegistradas.setRowCount(0);
		for (Compra compra : compras) {
			String proveedor = compra.getProveedor() != null ? compra.getProveedor().getRazonSocial() : "-";
			modeloComprasRegistradas.addRow(new Object[] { compra.getNumeroCompra(), compra.getFechaCompra(), proveedor,
					"$" + String.format("%,.0f", compra.getTotalCompra()), compra.isAnulada() ? "ANULADA" : "VALIDA" });
		}
	}

	private Producto obtenerProductoSeleccionado() {
		Object seleccionado = comboProducto.getSelectedItem();
		if (seleccionado == null || seleccionado.toString().startsWith("-")) {
			return null;
		}
		String codigo = seleccionado.toString().split(" - ")[0].trim();
		for (Producto producto : productosDisponibles) {
			if (producto.getCodigoInterno().equalsIgnoreCase(codigo)) {
				return producto;
			}
		}
		return null;
	}
}
