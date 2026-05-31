package co.uptc.edu.co.gui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
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
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.co.gui.Evento;
import co.uptc.edu.co.modelo.Venta;

public class DialogHistorialCliente extends JDialog {

	private static final DecimalFormat FORMATO_MONEDA = crearFormatoMoneda();

	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("hh:mm a");

	private JLabel lblCodigoCliente;
	private JLabel lblNombreCliente;

	private JTable tablaHistorial;
	private DefaultTableModel modeloTabla;

	private JButton btnVerDetalle;
	private JButton btnCerrar;

	public DialogHistorialCliente(Frame propietario) {
		this(propietario, null);
	}

	public DialogHistorialCliente(Frame propietario, Evento evento) {
		super(propietario, "Historial de Ventas del Cliente", true);
		setLayout(new BorderLayout());

		inicializarComponentes();
		configurarDialogo();
		agregarComponentes();
		agregarEventos(evento);
	}

	private void inicializarComponentes() {
		lblCodigoCliente = new JLabel("Código: ");
		lblNombreCliente = new JLabel("Cliente: ");

		modeloTabla = new DefaultTableModel(
				new String[] { "Factura", "Fecha", "Hora", "Forma de Pago", "Impuestos", "Total", "Estado" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaHistorial = new JTable(modeloTabla);
		btnVerDetalle = new JButton("Ver Detalle");
		btnCerrar = new JButton("Cerrar");
	}

	private void configurarDialogo() {
		setSize(820, 460);
		setLocationRelativeTo(getOwner());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);

		tablaHistorial.setRowHeight(25);
		tablaHistorial.getTableHeader().setReorderingAllowed(false);
	}

	private void agregarComponentes() {
		add(crearPanelSuperior(), BorderLayout.NORTH);
		add(crearScrollTabla(), BorderLayout.CENTER);
		add(crearPanelBotones(), BorderLayout.SOUTH);
	}

	private JPanel crearPanelSuperior() {
		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		panelSuperior.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));

		panelSuperior.add(lblCodigoCliente);
		panelSuperior.add(lblNombreCliente);

		return panelSuperior;
	}

	private JScrollPane crearScrollTabla() {
		JScrollPane scrollPane = new JScrollPane(tablaHistorial);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Historial de Ventas"));
		return scrollPane;
	}

	private JPanel crearPanelBotones() {
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		panelBotones.add(btnVerDetalle);
		panelBotones.add(btnCerrar);
		return panelBotones;
	}

	private void agregarEventos(Evento evento) {
		if (evento != null) {
			btnVerDetalle.setActionCommand(Evento.CMD_VER_DETALLE_CLIENTE);
			btnVerDetalle.addActionListener(evento);
		}

		btnCerrar.addActionListener(e -> dispose());
	}

	public void cargarCliente(String codigo, String nombre) {
		lblCodigoCliente.setText("Código: " + codigo);
		lblNombreCliente.setText("Cliente: " + nombre);
	}

	public void agregarFilaHistorial(Object[] fila) {
		modeloTabla.addRow(fila);
	}

	public void limpiarTabla() {
		modeloTabla.setRowCount(0);
	}

	public void cargarHistorial(List<Venta> ventas) {
		limpiarTabla();

		for (Venta venta : ventas) {
			String fecha = venta.getFechaHora() != null ? venta.getFechaHora().toLocalDate().format(FORMATO_FECHA)
					: "";
			String hora = venta.getFechaHora() != null ? venta.getFechaHora().format(FORMATO_HORA) : "";
			String formaPago = venta.getFormaPago() != null
			        ? venta.getFormaPago().toString()
			        : "";
			String impuestos = formatearMoneda(venta.getImpuestos());
			String total = formatearMoneda(venta.getTotal());
			String estado = venta.getEstado() != null ? venta.getEstado().name() : "";

			modeloTabla.addRow(new Object[] { venta.getNumeroFactura(), fecha, hora, formaPago, impuestos, total,
					estado });
		}
	}

	public boolean haySeleccion() {
		return tablaHistorial.getSelectedRow() != -1;
	}

	public String obtenerFacturaSeleccionada() {
		int filaSeleccionada = tablaHistorial.getSelectedRow();

		if (filaSeleccionada == -1) {
			return null;
		}

		Object valor = modeloTabla.getValueAt(filaSeleccionada, 0);
		return valor == null ? null : valor.toString();
	}

	public JTable getTablaHistorial() {
		return tablaHistorial;
	}

	public DefaultTableModel getModeloTabla() {
		return modeloTabla;
	}

	public JButton getBtnCerrar() {
		return btnCerrar;
	}

	public JButton getBtnVerDetalle() {
		return btnVerDetalle;
	}

	private static DecimalFormat crearFormatoMoneda() {
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setGroupingSeparator('.');
		simbolos.setDecimalSeparator(',');

		DecimalFormat formato = new DecimalFormat("$ #,##0", simbolos);
		formato.setGroupingUsed(true);
		return formato;
	}

	private String formatearMoneda(double valor) {
		return FORMATO_MONEDA.format(valor);
	}
}