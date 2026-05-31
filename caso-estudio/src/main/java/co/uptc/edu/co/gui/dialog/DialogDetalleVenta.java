package co.uptc.edu.co.gui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.co.modelo.DetalleVentaDevolucionDTO;
import co.uptc.edu.co.modelo.Venta;

public class DialogDetalleVenta extends JDialog {

	private static final DecimalFormat FORMATO_MONEDA = crearFormatoMoneda();
	private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("hh:mm a");
	private static final DateTimeFormatter FORMATO_FECHA_ANULACION = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private JTextField campoNumeroFactura;
	private JTextField campoFecha;
	private JTextField campoHora;
	private JTextField campoCliente;
	private JTextField campoFormaPago;
	private JTextField campoImpuestos;
	private JTextField campoTotal;
	private JTextField campoEstado;
	private JTextField campoFechaAnulacion;
	private JTextField campoMotivoAnulacion;

	private JTable tablaDetalle;
	private DefaultTableModel modeloTabla;
	private JButton botonCerrar;

	public DialogDetalleVenta(Frame propietario) {
		super(propietario, "Detalle de Venta", true);
		inicializarComponentes();
		configurarDialogo();
		agregarComponentes();
		inicializarEventos();
	}

	private static DecimalFormat crearFormatoMoneda() {
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setGroupingSeparator('.');
		simbolos.setDecimalSeparator(',');

		DecimalFormat formato = new DecimalFormat("$ #,##0", simbolos);
		formato.setGroupingUsed(true);
		return formato;
	}

	private void inicializarComponentes() {

		campoNumeroFactura = new JTextField(15);
		campoFecha = new JTextField(15);
		campoHora = new JTextField(15);
		campoCliente = new JTextField(15);
		campoFormaPago = new JTextField(15);
		campoImpuestos = new JTextField(15);
		campoTotal = new JTextField(15);
		campoEstado = new JTextField(15);
		campoFechaAnulacion = new JTextField(15);
		campoMotivoAnulacion = new JTextField(15);

		campoNumeroFactura.setEditable(false);
		campoFecha.setEditable(false);
		campoHora.setEditable(false);
		campoCliente.setEditable(false);
		campoFormaPago.setEditable(false);
		campoImpuestos.setEditable(false);
		campoTotal.setEditable(false);
		campoEstado.setEditable(false);
		campoFechaAnulacion.setEditable(false);
		campoMotivoAnulacion.setEditable(false);

		modeloTabla = new DefaultTableModel(

				new Object[] { "Producto", "Vendido", "Devuelto", "Pendiente", "Precio Unitario",
						"Subtotal Original", "Valor Devuelto", "Total Actual" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaDetalle = new JTable(modeloTabla);

		botonCerrar = new JButton("Cerrar");
	}

	private void configurarDialogo() {
		setSize(1050, 600);
		setLocationRelativeTo(getOwner());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		tablaDetalle.setRowHeight(25);
		tablaDetalle.getTableHeader().setReorderingAllowed(false);
		tablaDetalle.getColumnModel().getColumn(0).setPreferredWidth(170);
		tablaDetalle.getColumnModel().getColumn(1).setPreferredWidth(70);
		tablaDetalle.getColumnModel().getColumn(2).setPreferredWidth(75);
		tablaDetalle.getColumnModel().getColumn(3).setPreferredWidth(80);
		tablaDetalle.getColumnModel().getColumn(4).setPreferredWidth(115);
		tablaDetalle.getColumnModel().getColumn(5).setPreferredWidth(135);
		tablaDetalle.getColumnModel().getColumn(6).setPreferredWidth(125);
		tablaDetalle.getColumnModel().getColumn(7).setPreferredWidth(115);
	}

	private void agregarComponentes() {
		JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		JPanel panelDatos = new JPanel(new GridBagLayout());
		panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de la Venta"));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		panelDatos.add(new JLabel("N° Factura:"), gbc);
		gbc.gridx = 1;
		panelDatos.add(campoNumeroFactura, gbc);

		gbc.gridx = 2;
		panelDatos.add(new JLabel("Fecha:"), gbc);
		gbc.gridx = 3;
		panelDatos.add(campoFecha, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelDatos.add(new JLabel("Hora:"), gbc);
		gbc.gridx = 1;
		panelDatos.add(campoHora, gbc);

		gbc.gridx = 2;
		panelDatos.add(new JLabel("Cliente:"), gbc);
		gbc.gridx = 3;
		panelDatos.add(campoCliente, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panelDatos.add(new JLabel("Forma de Pago:"), gbc);
		gbc.gridx = 1;
		panelDatos.add(campoFormaPago, gbc);

		gbc.gridx = 2;
		panelDatos.add(new JLabel("Impuestos Originales:"), gbc);
		gbc.gridx = 3;
		panelDatos.add(campoImpuestos, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panelDatos.add(new JLabel("Total Original:"), gbc);
		gbc.gridx = 1;
		panelDatos.add(campoTotal, gbc);

		gbc.gridx = 2;
		panelDatos.add(new JLabel("Estado:"), gbc);
		gbc.gridx = 3;
		panelDatos.add(campoEstado, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		panelDatos.add(new JLabel("Fecha Anulación:"), gbc);
		gbc.gridx = 1;
		panelDatos.add(campoFechaAnulacion, gbc);

		gbc.gridx = 2;
		panelDatos.add(new JLabel("Motivo Anulación:"), gbc);
		gbc.gridx = 3;
		panelDatos.add(campoMotivoAnulacion, gbc);

		JScrollPane scrollTabla = new JScrollPane(tablaDetalle);
		scrollTabla.setBorder(BorderFactory.createTitledBorder("Productos Vendidos"));

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		panelBotones.add(botonCerrar);

		panelPrincipal.add(panelDatos, BorderLayout.NORTH);
		panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
		panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

		add(panelPrincipal);
	}

	private void inicializarEventos() {
		botonCerrar.addActionListener(e -> dispose());
	}

	public void cargarVenta(String numeroFactura, String fecha, String hora, String cliente, String formaPago,
			String impuestos, String total, String estado) {

		campoNumeroFactura.setText(numeroFactura);
		campoFecha.setText(fecha);
		campoHora.setText(hora);
		campoCliente.setText(cliente);
		campoFormaPago.setText(formaPago);
		campoImpuestos.setText(impuestos);
		campoTotal.setText(total);
		campoEstado.setText(estado);
	}

	public void cargarVenta(Venta venta) {
		cargarVenta(venta, List.of());
	}

	public void cargarVenta(Venta venta, List<DetalleVentaDevolucionDTO> detallesResumen) {
		cargarVenta(venta.getNumeroFactura(),
				venta.getFechaHora() != null ? venta.getFechaHora().toLocalDate().toString() : "",
				venta.getFechaHora() != null ? venta.getFechaHora().format(FORMATO_HORA) : "",
				venta.getCliente(),
				venta.getFormaPago() != null ? venta.getFormaPago().toString() : "",
				formatearMoneda(venta.getImpuestos()),
				formatearMoneda(venta.getTotal()),
				venta.getEstado() != null ? venta.getEstado().name() : "");
		campoFechaAnulacion.setText(
				venta.getFechaAnulacion() != null ? venta.getFechaAnulacion().format(FORMATO_FECHA_ANULACION) : "");
		campoMotivoAnulacion.setText(venta.getMotivoAnulacion() != null ? venta.getMotivoAnulacion() : "");

		limpiarTabla();

		if (detallesResumen != null) {
			for (DetalleVentaDevolucionDTO detalle : detallesResumen) {
				agregarDetalle(detalle);
			}
		}
	}

	private void agregarDetalle(DetalleVentaDevolucionDTO detalle) {
		agregarDetalle(detalle.getNombreProducto(), String.valueOf(detalle.getCantidadVendida()),
				String.valueOf(detalle.getCantidadDevuelta()), String.valueOf(detalle.getCantidadPendiente()),
				formatearMoneda(detalle.getPrecioUnitario()), formatearMoneda(detalle.getSubtotalOriginal()),
				formatearMoneda(detalle.getTotalDevuelto()), formatearMoneda(detalle.getTotalPendiente()));
	}

	public void agregarDetalle(String producto, String cantidadVendida, String cantidadDevuelta,
			String cantidadPendiente, String precioUnitario, String subtotalOriginal, String valorDevuelto,
			String totalActual) {
		modeloTabla.addRow(
				new Object[] { producto, cantidadVendida, cantidadDevuelta, cantidadPendiente, precioUnitario,
						subtotalOriginal, valorDevuelto, totalActual });
	}

	public void limpiarTabla() {
		modeloTabla.setRowCount(0);
	}

	public void limpiarCampos() {
		campoNumeroFactura.setText("");
		campoFecha.setText("");
		campoHora.setText("");
		campoCliente.setText("");
		campoFormaPago.setText("");
		campoImpuestos.setText("");
		campoTotal.setText("");
		campoEstado.setText("");
		campoFechaAnulacion.setText("");
		campoMotivoAnulacion.setText("");
	}

	private String formatearMoneda(double valor) {
		return FORMATO_MONEDA.format(valor);
	}
}
