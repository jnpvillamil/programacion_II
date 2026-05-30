package co.uptc.edu.co.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.enums.FormaPago;

public class PanelVenta extends PanelCentral {

	private static final String TITULO_PANEL = "Gestión de Ventas";
	private static final String TEXTO_TOTAL_INICIAL = "Total de ventas: 0";
	private static final String TEXTO_TOTAL = "Total de ventas: ";

	private static final String OPCION_TODOS = "Todos";
	private static final DecimalFormat FORMATO_MONEDA = crearFormatoMoneda();

	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("hh:mm a");

	private static final String[] COLUMNAS = { "N° Factura", "Fecha", "Hora", "Cliente", "Forma de Pago", "Impuestos",
			"Total", "Estado" };

	private static final int COLUMNA_FACTURA = 0;

	private JButton botonNuevaVenta;
	private JButton botonAnularVenta;
	private JButton botonRegistrarDevolucion;
	private JButton botonVerDetalle;
	private JButton botonGenerarFactura;

	private JTextField campoBuscarFactura;

	private JComboBox<String> comboCliente;
	private JComboBox<String> comboFormaPago;
	private JComboBox<String> comboEstado;

	private List<Venta> ventasCargadas;
	private boolean actualizandoComboClientes;

	private static DecimalFormat crearFormatoMoneda() {
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setGroupingSeparator('.');
		simbolos.setDecimalSeparator(',');
		DecimalFormat formato = new DecimalFormat("$ #,##0", simbolos);
		formato.setGroupingUsed(true);
		return formato;
	}

	public PanelVenta() {
		super();
		ventasCargadas = new ArrayList<>();
		inicializarComponentesVenta();
		configurarPanelVenta();
		agregarComponentesVenta();
		inicializarFiltros();
	}

	@Override
	protected String obtenerTituloPanel() {
		return TITULO_PANEL;
	}

	@Override
	protected String obtenerTextoTotalInicial() {
		return TEXTO_TOTAL_INICIAL;
	}

	@Override
	protected Object[] obtenerColumnas() {
		return COLUMNAS;
	}

	private void inicializarComponentesVenta() {
		botonNuevaVenta = new JButton("Nueva Venta");
		botonAnularVenta = new JButton("Anular");
		botonRegistrarDevolucion = new JButton("Registrar Devolución");
		botonVerDetalle = new JButton("Ver Detalle");
		botonGenerarFactura = new JButton("Generar Factura");

		campoBuscarFactura = new JTextField(20);

		comboCliente = new JComboBox<>();
		comboCliente.addItem(OPCION_TODOS);

		comboFormaPago = new JComboBox<>();
		comboFormaPago.addItem(OPCION_TODOS);
		for (FormaPago formaPago : FormaPago.values()) {
			comboFormaPago.addItem(formaPago.toString());
		}

		comboEstado = new JComboBox<>();
		comboEstado.addItem(OPCION_TODOS);
		comboEstado.addItem("ACTIVA");
		comboEstado.addItem("ANULADA");
		comboEstado.addItem("DEVUELTA");
	}

	private void configurarPanelVenta() {
		configurarBotonBase(botonNuevaVenta);
		configurarBotonBase(botonAnularVenta);
		configurarBotonBase(botonRegistrarDevolucion);
		configurarBotonBase(botonVerDetalle);
		configurarBotonBase(botonGenerarFactura);

	}

	private void agregarComponentesVenta() {
		panelBotones.add(botonNuevaVenta);
		panelBotones.add(botonAnularVenta);
		panelBotones.add(botonRegistrarDevolucion);
		panelBotones.add(botonVerDetalle);
		panelBotones.add(botonGenerarFactura);

		agregarFiltro("Buscar Factura:", campoBuscarFactura);
		agregarFiltro("Cliente:", comboCliente);
		agregarFiltro("Pago:", comboFormaPago);
		agregarFiltro("Estado:", comboEstado);

	}

	private void inicializarFiltros() {
		asignarFiltroTexto(campoBuscarFactura, this::aplicarFiltros);
		asignarFiltroCombo(comboCliente, this::aplicarFiltros);
		asignarFiltroCombo(comboFormaPago, this::aplicarFiltros);
		asignarFiltroCombo(comboEstado, this::aplicarFiltros);
	}

	public void inicializarEventos(Evento evento) {
		botonNuevaVenta.setActionCommand(Evento.CMD_NUEVA_VENTA);
		botonNuevaVenta.addActionListener(evento);

		botonAnularVenta.setActionCommand(Evento.CMD_ANULAR_VENTA);
		botonAnularVenta.addActionListener(evento);

		botonRegistrarDevolucion.setActionCommand(Evento.CMD_DEVOLUCION_VENTA);
		botonRegistrarDevolucion.addActionListener(evento);

		botonVerDetalle.setActionCommand(Evento.CMD_VER_DETALLE_VENTA);
		botonVerDetalle.addActionListener(evento);

		botonGenerarFactura.setActionCommand(Evento.CMD_FACTURA_VENTA);
		botonGenerarFactura.addActionListener(evento);

	}

	public void cargarVentas(List<Venta> ventas) {
		ventasCargadas = new ArrayList<>(ventas);
		actualizarComboClientes();
		aplicarFiltros();
	}

	private void actualizarComboClientes() {
		actualizandoComboClientes = true;

		String clienteSeleccionado = comboCliente.getSelectedItem() != null ? comboCliente.getSelectedItem().toString()
				: OPCION_TODOS;

		comboCliente.removeAllItems();
		comboCliente.addItem(OPCION_TODOS);

		for (Venta venta : ventasCargadas) {
			String cliente = venta.getCliente();

			if (cliente != null && !cliente.trim().isEmpty() && !existeClienteEnCombo(cliente)) {
				comboCliente.addItem(cliente);
			}
		}

		comboCliente.setSelectedItem(clienteSeleccionado);

		if (comboCliente.getSelectedItem() == null) {
			comboCliente.setSelectedItem(OPCION_TODOS);
		}

		actualizandoComboClientes = false;
	}

	private boolean existeClienteEnCombo(String cliente) {
		for (int i = 0; i < comboCliente.getItemCount(); i++) {
			if (cliente.equalsIgnoreCase(comboCliente.getItemAt(i))) {
				return true;
			}
		}

		return false;
	}

	private void aplicarFiltros() {
		if (actualizandoComboClientes) {
			return;
		}

		limpiarTabla();

		String textoBusqueda = campoBuscarFactura.getText().trim().toLowerCase();
		String clienteSeleccionado = comboCliente.getSelectedItem().toString();
		String formaPagoSeleccionada = comboFormaPago.getSelectedItem().toString();
		String estadoSeleccionado = comboEstado.getSelectedItem().toString();

		int totalFiltradas = 0;

		for (Venta venta : ventasCargadas) {
			String estadoVenta = venta.getEstado() != null ? venta.getEstado().name() : "";

			boolean coincideBusqueda = venta.getNumeroFactura().toLowerCase().contains(textoBusqueda);

			boolean coincideCliente = clienteSeleccionado.equals(OPCION_TODOS)
					|| venta.getCliente().equalsIgnoreCase(clienteSeleccionado);

			boolean coincideFormaPago = formaPagoSeleccionada.equals(OPCION_TODOS)
					|| venta.getFormaPago().equalsIgnoreCase(formaPagoSeleccionada);

			boolean coincideEstado = estadoSeleccionado.equals(OPCION_TODOS)
					|| estadoVenta.equalsIgnoreCase(estadoSeleccionado);

			if (coincideBusqueda && coincideCliente && coincideFormaPago && coincideEstado) {
				Object[] fila = { venta.getNumeroFactura(),
						venta.getFechaHora() != null ? venta.getFechaHora().format(FORMATO_FECHA) : "",
						venta.getFechaHora() != null ? venta.getFechaHora().format(FORMATO_HORA) : "",
						venta.getCliente(), venta.getFormaPago(), formatearMoneda(venta.getImpuestos()),
						formatearMoneda(venta.getTotal()), estadoVenta };

				modeloTabla.addRow(fila);
				totalFiltradas++;
			}
		}

		actualizarTextoTotal(TEXTO_TOTAL, totalFiltradas);
	}

	public String obtenerFacturaSeleccionada() {
		return obtenerTextoSeleccionado(COLUMNA_FACTURA);
	}

	public void actualizarTotalVentas(int total) {
		actualizarTextoTotal(TEXTO_TOTAL, total);
	}

	private String formatearMoneda(double valor) {
		return FORMATO_MONEDA.format(valor);
	}

}
