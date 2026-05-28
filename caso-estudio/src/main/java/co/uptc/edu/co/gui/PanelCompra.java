package co.uptc.edu.co.gui;

import javax.swing.*;

import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.Proveedor;

import java.awt.*;
import java.util.List;

public class PanelCompra extends PanelCentral {

	private static final String TITULO_PANEL = "Gestión de Compras";
	private static final String TEXTO_TOTAL_INICIAL = "Total de compras: 0";
	private static final String TEXTO_TOTAL = "Total de compras: ";

	private static final String OPCION_TODOS = "Todos";

	private static final String[] COLUMNAS = { "Factura Proveedor", "Fecha", "Proveedor", "Subtotal", "Impuestos",
			"Total", "Estado" };

	private JButton botonNuevaCompra;
	private JButton botonAnular;
	private JButton botonDetalle;

	private JTextField campoBuscar;
	private JComboBox<String> comboProveedor;

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

		campoBuscar = new JTextField(20);

		comboProveedor = new JComboBox<>();
		comboProveedor.addItem(OPCION_TODOS);

	}

	private void inicializarFiltros() {
		asignarFiltroTexto(campoBuscar, this::aplicarFiltros);
		asignarFiltroCombo(comboProveedor, this::aplicarFiltros);
	}

	private void configurarPanelCompra() {
		configurarBotonBase(botonNuevaCompra);
		configurarBotonBase(botonAnular);
		configurarBotonBase(botonDetalle);

	}

	private void agregarComponentesCompra() {
		panelBotones.add(botonNuevaCompra);
		panelBotones.add(botonAnular);
		panelBotones.add(botonDetalle);

		agregarFiltro("Buscar Factura:", campoBuscar);
		agregarFiltro("Proveedor:", comboProveedor);

	}

	public void inicializarEventos(Evento evento) {
		botonNuevaCompra.setActionCommand(Evento.CMD_NUEVA_COMPRA);
		botonNuevaCompra.addActionListener(evento);

		botonDetalle.setActionCommand(Evento.CMD_VER_DETALLE_COMPRA);
		botonDetalle.addActionListener(evento);

		botonAnular.setActionCommand(Evento.CMD_ANULAR_COMPRA);
		botonAnular.addActionListener(evento);

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
		String proveedorSeleccionado = comboProveedor.getSelectedItem() != null ? comboProveedor.getSelectedItem().toString() : OPCION_TODOS;

		int totalFiltrados = 0;

		for (Compra compra : comprasCargadas) {
			boolean coincideBusqueda = textoBusqueda.isEmpty() ||
					(compra.getNumeroFacturaProveedor() != null && compra.getNumeroFacturaProveedor().toLowerCase().contains(textoBusqueda));

			boolean coincideProveedor = proveedorSeleccionado.equals(OPCION_TODOS) ||
					(compra.getCodigoProveedor() != null && proveedorSeleccionado.startsWith(compra.getCodigoProveedor()));

			if (coincideBusqueda && coincideProveedor) {
				String factura = compra.getNumeroFacturaProveedor();
				String fecha = compra.getFecha() != null ? compra.getFecha().toString() : "";
				String proveedor = compra.getCodigoProveedor();
				double subtotal = compra.getSubtotal();
				double impuestos = compra.getImpuestos();
				double total = compra.getTotalCompra();
				String estado = compra.getEstado() != null ? compra.getEstado().name() : "";

				modeloTabla.addRow(new Object[] { factura, fecha, proveedor, subtotal, impuestos, total, estado });
				totalFiltrados++;
			}
		}

		actualizarTotalCompras(totalFiltrados);
	}

	public String obtenerFacturaSeleccionada() {
		// TODO Auto-generated method stub
		return obtenerTextoSeleccionado(0);
	}

	public String obtenerFechaSeleccionada() {
		return obtenerTextoSeleccionado(1);
	}

	public String obtenerProveedorSeleccionado() {
		return obtenerTextoSeleccionado(2);
	}

	public String obtenerSubtotalSeleccionado() {
		return obtenerTextoSeleccionado(3);
	}

	public String obtenerImpuestosSeleccionados() {
		return obtenerTextoSeleccionado(4);
	}

	public String obtenerTotalSeleccionado() {
		return obtenerTextoSeleccionado(5);
	}

	public String obtenerEstadoSeleccionadoCompra() {
		return obtenerTextoSeleccionado(6);
	}

	public void actualizarTotalCompras(int total) {
		actualizarTextoTotal(TEXTO_TOTAL, total);
	}

}
