package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class PanelVentas extends JPanel {

	private static final long serialVersionUID = 1L;

	// --- Componentes de la Cabecera (Venta) ---
	public JTextField tDocumentoCliente;
	public JComboBox<String> cbFormaPago;
	public JCheckBox checkIva;

	// --- Componentes del Ingreso de Productos ---
	public JTextField tCodigoProducto;
	public JButton bBuscarProducto; // El botón de la lupa
	public JTextField tCantidad;
	public JButton bAgregarCarrito;
	public JButton bQuitarDelCarrito;
	public JTextField tNombreProductoVisual;

	// --- Tabla del Carrito (Detalle) ---
	public DefaultTableModel modeloCarrito;
	public JTable tablaCarrito;

	// --- Componentes de Total y Cierre ---
	public JLabel lTotalVenta;
	public JButton bRegistrarVenta;
	public JButton bNuevaVenta;
	public JButton bVolver;

	public PanelVentas() {
		construirPanel();
	}

	private void construirPanel() {
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// ==========================================
		// 1. PANEL SUPERIOR: Cabecera y Buscador
		// ==========================================
		JPanel panelSuperior = new JPanel(new BorderLayout(5, 5));

		// 1.1 Datos de la Factura (Cliente y Pago)
		JPanel panelFactura = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelFactura.setBorder(BorderFactory.createTitledBorder("Datos de la Factura"));
		panelFactura.add(new JLabel("Doc. Cliente:"));
		tDocumentoCliente = new JTextField(10);
		panelFactura.add(tDocumentoCliente);

		panelFactura.add(new JLabel(" Forma de Pago:"));
		cbFormaPago = new JComboBox<>(new String[] { "Efectivo", "Transferencia", "Tarjeta", "Crédito" });
		panelFactura.add(cbFormaPago);

		checkIva = new JCheckBox("Aplicar IVA (19%)");
		panelFactura.add(checkIva);

		// 1.2 Ingreso de Productos (El Escáner)
		JPanel panelProducto = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelProducto.setBorder(BorderFactory.createTitledBorder("Agregar Producto al Carrito"));

		panelProducto.add(new JLabel("Cód:"));
		tCodigoProducto = new JTextField(5);
		panelProducto.add(tCodigoProducto);

		bBuscarProducto = new JButton("🔍");
		panelProducto.add(bBuscarProducto);

		// --- NUEVO: EL VISOR DEL NOMBRE ---
		tNombreProductoVisual = new JTextField(15);
		tNombreProductoVisual.setEditable(false); // Para que el cajero no escriba aquí
		tNombreProductoVisual.setBackground(new java.awt.Color(230, 240, 255)); // Un azul clarito de confirmación
		tNombreProductoVisual.setFont(new Font("Arial", Font.BOLD, 12));
		panelProducto.add(tNombreProductoVisual);
		// ----------------------------------

		panelProducto.add(new JLabel(" Cant:"));
		tCantidad = new JTextField(4);
		tCantidad.setText("1");
		panelProducto.add(tCantidad);

		bAgregarCarrito = new JButton("Agregar (+)");
		bQuitarDelCarrito = new JButton("Quitar (-)");
		panelProducto.add(bAgregarCarrito);
		panelProducto.add(bQuitarDelCarrito);

		// ¡ESTO ERA LO QUE FALTABA! Pegar los subpaneles al panel superior
		panelSuperior.add(panelFactura, BorderLayout.NORTH);
		panelSuperior.add(panelProducto, BorderLayout.SOUTH);

		// ==========================================
		// 2. PANEL CENTRAL: La Tabla del Carrito
		// ==========================================
		modeloCarrito = new DefaultTableModel(
				new String[] { "Cód. Producto", "Nombre", "Cantidad", "V. Unitario", "Subtotal" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Que no editen la tabla directamente
			}
		};
		tablaCarrito = new JTable(modeloCarrito);
		tablaCarrito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaCarrito.setRowHeight(25);

		JScrollPane scrollCarrito = new JScrollPane(tablaCarrito);
		scrollCarrito.setBorder(BorderFactory.createTitledBorder("Carrito de Compras (Detalle de Venta)"));

		// ==========================================
		// 3. PANEL INFERIOR: Totales y Botones Finales
		// ==========================================
		JPanel panelInferior = new JPanel(new BorderLayout());

		// Sección del Gran Total
		JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel lTextoTotal = new JLabel("TOTAL A PAGAR: $ ");
		lTextoTotal.setFont(new Font("Arial", Font.BOLD, 18));
		lTotalVenta = new JLabel("0.0");
		lTotalVenta.setFont(new Font("Arial", Font.BOLD, 22));
		lTotalVenta.setForeground(java.awt.Color.RED);
		panelTotal.add(lTextoTotal);
		panelTotal.add(lTotalVenta);

		// Sección de Botones
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		bRegistrarVenta = new JButton("REGISTRAR VENTA");
		bRegistrarVenta.setFont(new Font("Arial", Font.BOLD, 14));
		bRegistrarVenta.setBackground(new java.awt.Color(50, 205, 50));
		bRegistrarVenta.setForeground(java.awt.Color.WHITE);

		bNuevaVenta = new JButton("Limpiar / Nueva Venta");
		bVolver = new JButton("Volver al Menú");

		panelBotones.add(bVolver);
		panelBotones.add(bNuevaVenta);
		panelBotones.add(bRegistrarVenta);

		panelInferior.add(panelTotal, BorderLayout.NORTH);
		panelInferior.add(panelBotones, BorderLayout.SOUTH);

		// Agregar los 3 bloques principales al PanelVentas
		add(panelSuperior, BorderLayout.NORTH);
		add(scrollCarrito, BorderLayout.CENTER);
		add(panelInferior, BorderLayout.SOUTH);
	}

	// Método para que el controlador escuche los botones
	public void setControlador(ActionListener controlador) {
		bBuscarProducto.addActionListener(controlador);
		bBuscarProducto.setActionCommand("BUSCAR_PRODUCTO_VENTA");

		bAgregarCarrito.addActionListener(controlador);
		bAgregarCarrito.setActionCommand("AGREGAR_CARRITO");

		bQuitarDelCarrito.addActionListener(controlador);
		bQuitarDelCarrito.setActionCommand("QUITAR_CARRITO");

		bRegistrarVenta.addActionListener(controlador);
		bRegistrarVenta.setActionCommand("REGISTRAR_VENTA");

		bNuevaVenta.addActionListener(controlador);
		bNuevaVenta.setActionCommand("NUEVA_VENTA");

		bVolver.addActionListener(controlador);
		bVolver.setActionCommand("VOLVER");

		checkIva.addActionListener(controlador);
		checkIva.setActionCommand("CAMBIO_IVA");
	}

	public void limpiarCamposProducto() {
		tCodigoProducto.setText("");
		tCantidad.setText("1"); // Volver a poner 1 por defecto
		tNombreProductoVisual.setText("");
		tCodigoProducto.requestFocus();
	}

	public void limpiarTodo() {
		tDocumentoCliente.setText("");
		cbFormaPago.setSelectedIndex(0);
		checkIva.setSelected(false);
		modeloCarrito.setRowCount(0);
		actualizarTotalPantalla(0.0);
		limpiarCamposProducto();
	}

	public void actualizarTotalPantalla(double total) {
		lTotalVenta.setText(String.format("%.2f", total));
	}
}