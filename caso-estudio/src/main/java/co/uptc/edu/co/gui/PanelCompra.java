package co.uptc.edu.co.gui;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.Proveedor;
import co.uptc.edu.co.modelo.enums.FormaPago;

import java.awt.*;
import java.util.List;

public class PanelCompra extends PanelCentral {

	private static final String TITULO_PANEL = "Gestión de Compras";
	private static final String TEXTO_TOTAL_INICIAL = "Total de compras: 0";
	private static final String TEXTO_TOTAL = "Total de compras: ";

	private static final String OPCION_TODOS = "Todos";
	private static final DecimalFormat FORMATO_MONEDA = crearFormatoMoneda();
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private static final String[] COLUMNAS = { "Factura Proveedor", "Fecha", "Código Proveedor", "Proveedor",
			"Forma de Pago", "Subtotal", "Impuestos", "Total", "Estado" };

	private JButton botonNuevaCompra;
	private JButton botonAnular;
	private JButton botonDetalle;
	private JButton botonFactura;

	private JTextField campoBuscar;
	private JComboBox<String> comboProveedor;
	private JComboBox<String> comboFormaPago;
	private JComboBox<String> comboEstado;

	private java.util.List<Compra> comprasCargadas;

	public PanelCompra() {
		super();
		comprasCargadas = new java.util.ArrayList<>();
		inicializarComponentesCompra();
		configurarPanelCompra();
		agregarComponentesCompra();
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

	private void inicializarComponentesCompra() {
		botonNuevaCompra = new JButton("Nueva Compra");
		botonAnular = new JButton("Anular");
		botonDetalle = new JButton("Ver Detalle");
		botonFactura = new JButton("Generar Factura");

		campoBuscar = new JTextField(20);

		comboProveedor = new JComboBox<>();
		comboProveedor.addItem(OPCION_TODOS);

		comboFormaPago = new JComboBox<>();
		comboFormaPago.addItem(OPCION_TODOS);
		for (FormaPago formaPago : FormaPago.values()) {
			comboFormaPago.addItem(formaPago.toString());
		}

		comboEstado = new JComboBox<>();
		comboEstado.addItem(OPCION_TODOS);
		comboEstado.addItem("ACTIVA");
		comboEstado.addItem("ANULADA");

	}

	private void inicializarFiltros() {
		asignarFiltroTexto(campoBuscar, this::aplicarFiltros);
		asignarFiltroCombo(comboProveedor, this::aplicarFiltros);
		asignarFiltroCombo(comboFormaPago, this::aplicarFiltros);
		asignarFiltroCombo(comboEstado, this::aplicarFiltros);
	}

	private void configurarPanelCompra() {
		configurarBotonBase(botonNuevaCompra);
		configurarBotonBase(botonAnular);
		configurarBotonBase(botonDetalle);
		configurarBotonBase(botonFactura);

	}

	private void agregarComponentesCompra() {
		panelBotones.add(botonNuevaCompra);
		panelBotones.add(botonAnular);
		panelBotones.add(botonDetalle);
		panelBotones.add(botonFactura);

		agregarFiltro("Buscar Factura:", campoBuscar);
		agregarFiltro("Proveedor:", comboProveedor);
		agregarFiltro("Forma de pago:", comboFormaPago);
		agregarFiltro("Estado:", comboEstado);

	}

	public void inicializarEventos(Evento evento) {
		botonNuevaCompra.setActionCommand(Evento.CMD_NUEVA_COMPRA);
		botonNuevaCompra.addActionListener(evento);

		botonDetalle.setActionCommand(Evento.CMD_VER_DETALLE_COMPRA);
		botonDetalle.addActionListener(evento);

		botonAnular.setActionCommand(Evento.CMD_ANULAR_COMPRA);
		botonAnular.addActionListener(evento);

		botonFactura.setActionCommand(Evento.CMD_FACTURA_COMPRA);
		botonFactura.addActionListener(evento);

	}

	public void cargarCompras(List<Compra> compras) {
		comprasCargadas = compras != null ? new java.util.ArrayList<>(compras) : new java.util.ArrayList<>();
		aplicarFiltros();
	}

	public void cargarProveedores(List<Proveedor> proveedores) {
		comboProveedor.removeAllItems();
		comboProveedor.addItem(OPCION_TODOS);
		if (proveedores == null) {
			return;
		}
		for (Proveedor p : proveedores) {
			if (p != null && p.estaActivo()) {
				comboProveedor.addItem(p.getCodigoProveedor() + " - " + p.getRazonSocial());
			}
		}
	}

	private void aplicarFiltros() {
		limpiarTabla();

		String textoBusqueda = campoBuscar.getText().trim().toLowerCase();

		String proveedorSeleccionado = comboProveedor.getSelectedItem() != null
				? comboProveedor.getSelectedItem().toString()
				: OPCION_TODOS;

		String formaPagoSeleccionada = comboFormaPago.getSelectedItem() != null
				? comboFormaPago.getSelectedItem().toString()
				: OPCION_TODOS;

		String estadoSeleccionado = comboEstado.getSelectedItem() != null ? comboEstado.getSelectedItem().toString()
				: OPCION_TODOS;

		int totalFiltrados = 0;

		for (Compra compra : comprasCargadas) {
			String estadoCompra = compra.getEstado() != null ? compra.getEstado().name() : "";

			boolean coincideBusqueda = textoBusqueda.isEmpty() || (compra.getNumeroFacturaProveedor() != null
					&& compra.getNumeroFacturaProveedor().toLowerCase().contains(textoBusqueda));
			boolean coincideProveedor = proveedorSeleccionado.equals(OPCION_TODOS)
					|| (compra.getCodigoProveedor() != null
							&& proveedorSeleccionado.startsWith(compra.getCodigoProveedor()));

			boolean coincideFormaPago = formaPagoSeleccionada.equals(OPCION_TODOS)
			        || (compra.getFormaPago() != null
			                && compra.getFormaPago().toString().equalsIgnoreCase(formaPagoSeleccionada));
			boolean coincideEstado = estadoSeleccionado.equals(OPCION_TODOS)
					|| estadoCompra.equalsIgnoreCase(estadoSeleccionado);

			if (coincideBusqueda && coincideProveedor && coincideFormaPago && coincideEstado) {
				String factura = compra.getNumeroFacturaProveedor();
				String fecha = compra.getFecha() != null ? compra.getFecha().format(FORMATO_FECHA) : "";
				String codigoProveedor = compra.getCodigoProveedor();
				String proveedor = obtenerNombreProveedor(compra);
				String formaPago = compra.getFormaPago() != null
				        ? compra.getFormaPago().toString()
				        : "";
				String subtotal = formatearMoneda(compra.getSubtotal());
				String impuestos = formatearMoneda(compra.getImpuestos());
				String total = formatearMoneda(compra.getTotalCompra());
				String estado = estadoCompra;

				modeloTabla.addRow(
						new Object[] { factura, fecha, codigoProveedor, proveedor, formaPago, subtotal, impuestos, total,
								estado });
				totalFiltrados++;
			}
		}

		actualizarTotalCompras(totalFiltrados);
	}

	public String obtenerFacturaSeleccionada() {
		return obtenerTextoSeleccionado(0);
	}

	public String obtenerFechaSeleccionada() {
		return obtenerTextoSeleccionado(1);
	}

	public String obtenerProveedorSeleccionado() {
		return obtenerTextoSeleccionado(3);
	}

	public String obtenerFormaPagoSeleccionada() {
		return obtenerTextoSeleccionado(4);
	}

	public String obtenerSubtotalSeleccionado() {
		return obtenerTextoSeleccionado(5);
	}

	public String obtenerImpuestosSeleccionados() {
		return obtenerTextoSeleccionado(6);
	}

	public String obtenerTotalSeleccionado() {
		return obtenerTextoSeleccionado(7);
	}

	public String obtenerEstadoSeleccionadoCompra() {
		return obtenerTextoSeleccionado(8);
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

	private String obtenerNombreProveedor(Compra compra) {
		if (compra.getProveedor() != null && !compra.getProveedor().trim().isEmpty()) {
			return compra.getProveedor();
		}
		return compra.getCodigoProveedor() != null ? compra.getCodigoProveedor() : "";
	}

	public JButton getBotonFactura() {
		return botonFactura;
	}

	public void actualizarTotalCompras(int total) {
		actualizarTextoTotal(TEXTO_TOTAL, total);
	}

}
