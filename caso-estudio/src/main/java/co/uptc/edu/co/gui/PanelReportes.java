package co.uptc.edu.co.gui;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.dto.DetalleUtilidadBrutaDTO;
import co.uptc.edu.co.modelo.dto.ResumenClienteDTO;
import co.uptc.edu.co.modelo.dto.ResumenFormaPagoDTO;
import co.uptc.edu.co.modelo.dto.ResumenInventarioValorizadoDTO;
import co.uptc.edu.co.modelo.dto.ResumenProductoDTO;
import co.uptc.edu.co.modelo.dto.ResumenUtilidadBrutaDTO;
import co.uptc.edu.co.modelo.dto.ResumenVentasDTO;
import co.uptc.edu.co.modelo.dto.ResumenContableDTO;

public class PanelReportes extends PanelCentral {

	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DecimalFormat FORMATO_MONEDA = crearFormatoMoneda();

	private static final String TITULO_PANEL = "Gestión de Reportes";
	private static final String TEXTO_TOTAL_INICIAL = "Registros del reporte: 0";
	private static final String TEXTO_TOTAL = "Registros del reporte: ";

	private static final String TEXTO_BOTON_BUSCAR = "Buscar";
	private static final String TEXTO_BOTON_GENERAR_REPORTE = "Generar Reportes";

	
	private static final String REPORTE_VENTAS_DIARIAS = "Ventas diarias";
	private static final String REPORTE_VENTAS_MENSUALES = "Ventas mensuales";
	private static final String REPORTE_VENTAS_ANUALES = "Ventas anuales";
	private static final String REPORTE_UTILIDAD_BRUTA = "Utilidad bruta";
	private static final String REPORTE_PRODUCTOS_MAS_VENDIDOS = "Productos más vendidos";
	private static final String REPORTE_CLIENTES_MAYOR_COMPRA = "Clientes con mayor volumen de compra";
	private static final String REPORTE_VENTAS_FORMA_PAGO = "Comparación de ventas por forma de pago";
	private static final String REPORTE_INVENTARIO_VALORIZADO = "Estado de inventario valorizado";
	private static final String REPORTE_RESUMEN_CONTABLE = "Resumen contable por periodo";

	private static final String[] COLUMNAS_INICIALES = { "Resultado" };
	private static final String[] COLUMNAS_VENTAS_DIARIAS = { "Factura", "Fecha", "Cliente", "Forma Pago", "Subtotal",
			"Impuestos", "Total" };
	private static final String[] COLUMNAS_VENTAS_MENSUALES = { "Factura", "Fecha", "Cliente", "Forma Pago", "Subtotal",
			"Impuestos", "Total" };
	private static final String[] COLUMNAS_VENTAS_ANUALES = { "Factura", "Fecha", "Cliente", "Forma Pago", "Subtotal",
			"Impuestos", "Total" };
	private static final String[] COLUMNAS_UTILIDAD_BRUTA = { "Producto", "Cantidad Vendida", "Ventas",
			"Costo de Venta", "Utilidad" };
	private static final String[] COLUMNAS_PRODUCTOS_MAS_VENDIDOS = { "Código", "Producto", "Cantidad Vendida",
			"Total Vendido" };
	private static final String[] COLUMNAS_CLIENTES_MAYOR_COMPRA = { "Código Cliente", "Cliente", "Cantidad Compras",
			"Total Comprado" };
	private static final String[] COLUMNAS_VENTAS_FORMA_PAGO = { "Forma de Pago", "Cantidad Ventas", "Valor Total" };
	private static final String[] COLUMNAS_INVENTARIO_VALORIZADO = { "Código", "Producto", "Categoría", "Stock Actual",
			"Precio Compra", "Valor Inventario" };
	private static final String[] COLUMNAS_RESUMEN_CONTABLE = { "Ingresos", "Egresos", "Utilidad" };

	private JLabel etiquetaTipoReporte;
	private JLabel etiquetaFecha;
	private JLabel etiquetaMes;
	private JLabel etiquetaAnio;
	private JLabel etiquetaFechaInicio;
	private JLabel etiquetaFechaFin;

	private JButton botonBuscar;
	private JButton botonGenerarReporte;
	private JComboBox<String> comboTipoReporte;

	private JFormattedTextField campoFecha;
	private JTextField campoMes;
	private JFormattedTextField campoAnio;
	private JFormattedTextField campoFechaInicio;
	private JFormattedTextField campoFechaFin;

	private static DecimalFormat crearFormatoMoneda() {
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setGroupingSeparator('.');
		simbolos.setDecimalSeparator(',');

		DecimalFormat formato = new DecimalFormat("$ #,##0", simbolos);
		formato.setGroupingUsed(true);
		return formato;
	}

	public PanelReportes() {
		super();
		inicializarComponentesReportes();
		configurarPanelReportes();
		agregarComponentesReportes();
		actualizarFiltros();
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
		return COLUMNAS_INICIALES;
	}

	private void inicializarComponentesReportes() {
		etiquetaTipoReporte = new JLabel("Tipo de reporte:");
		etiquetaFecha = new JLabel("Fecha:");
		etiquetaMes = new JLabel("Mes:");
		etiquetaAnio = new JLabel("Año:");
		etiquetaFechaInicio = new JLabel("Desde:");
		etiquetaFechaFin = new JLabel("Hasta:");

		botonBuscar = new JButton(TEXTO_BOTON_BUSCAR);
		botonGenerarReporte = new JButton(TEXTO_BOTON_GENERAR_REPORTE);
		botonGenerarReporte.setActionCommand(Evento.CMD_GENERAR_REPORTE_JSON);
		botonGenerarReporte.setEnabled(false);
		comboTipoReporte = new JComboBox<>();
		comboTipoReporte.addItem(REPORTE_VENTAS_DIARIAS);
		comboTipoReporte.addItem(REPORTE_VENTAS_MENSUALES);
		comboTipoReporte.addItem(REPORTE_VENTAS_ANUALES);
		comboTipoReporte.addItem(REPORTE_UTILIDAD_BRUTA);
		comboTipoReporte.addItem(REPORTE_PRODUCTOS_MAS_VENDIDOS);
		comboTipoReporte.addItem(REPORTE_CLIENTES_MAYOR_COMPRA);
		comboTipoReporte.addItem(REPORTE_VENTAS_FORMA_PAGO);
		comboTipoReporte.addItem(REPORTE_INVENTARIO_VALORIZADO);
		comboTipoReporte.addItem(REPORTE_RESUMEN_CONTABLE);

		campoFecha = crearCampoFecha();
		campoFecha.setColumns(8);

		campoMes = new JTextField(6);

		campoAnio = crearCampoAnio();
		campoAnio.setColumns(6);

		campoFechaInicio = crearCampoFecha();
		campoFechaInicio.setColumns(8);

		campoFechaFin = crearCampoFecha();
		campoFechaFin.setColumns(8);
	}

	private void configurarPanelReportes() {
		configurarBotonBase(botonBuscar);
		asignarFiltroCombo(comboTipoReporte, this::actualizarFiltros);
	}

	private void agregarComponentesReportes() {
		panelFiltros.add(etiquetaTipoReporte);
		panelFiltros.add(comboTipoReporte);

		panelFiltros.add(etiquetaFecha);
		panelFiltros.add(campoFecha);

		panelFiltros.add(etiquetaMes);
		panelFiltros.add(campoMes);

		panelFiltros.add(etiquetaAnio);
		panelFiltros.add(campoAnio);

		panelFiltros.add(etiquetaFechaInicio);
		panelFiltros.add(campoFechaInicio);

		panelFiltros.add(etiquetaFechaFin);
		panelFiltros.add(campoFechaFin);

		// El botón queda junto a Buscar y solo se habilita cuando la tabla ya tiene
		// datos.
		panelFiltros.add(botonBuscar);
		panelFiltros.add(botonGenerarReporte);
	}

	private void actualizarFiltros() {
		String tipoReporte = comboTipoReporte.getSelectedItem().toString();

		ocultarFiltros();

		if (tipoReporte.equals(REPORTE_VENTAS_DIARIAS)) {
			configurarVentasDiarias();
		} else if (tipoReporte.equals(REPORTE_VENTAS_MENSUALES)) {
			configurarVentasMensuales();
		} else if (tipoReporte.equals(REPORTE_VENTAS_ANUALES)) {
			configurarVentasAnuales();
		} else if (tipoReporte.equals(REPORTE_UTILIDAD_BRUTA)) {
			configurarUtilidadBruta();
		} else if (tipoReporte.equals(REPORTE_PRODUCTOS_MAS_VENDIDOS)) {
			configurarProductosMasVendidos();
		} else if (tipoReporte.equals(REPORTE_CLIENTES_MAYOR_COMPRA)) {
			configurarClientesMayorCompra();
		} else if (tipoReporte.equals(REPORTE_VENTAS_FORMA_PAGO)) {
			configurarVentasPorFormaPago();
		} else if (tipoReporte.equals(REPORTE_INVENTARIO_VALORIZADO)) {
			configurarInventarioValorizado();
		} else if (tipoReporte.equals(REPORTE_RESUMEN_CONTABLE)) {
			configurarResumenContable();
		}

		actualizarEstadoBotonGenerarReporte();

		limpiarTabla();
		actualizarTextoTotal(TEXTO_TOTAL, 0);

		revalidate();
		repaint();
	}

	public void inicializarEventos(Evento evento) {
		botonBuscar.setActionCommand(Evento.CMD_BUSCAR_REPORTE);
		botonBuscar.addActionListener(evento);
		botonGenerarReporte.addActionListener(evento);
	}

	private void actualizarEstadoBotonGenerarReporte() {
		boolean hayDatos = modeloTabla.getRowCount() > 0;
		botonGenerarReporte.setEnabled(hayDatos);
		if (!hayDatos) {
			botonGenerarReporte.setEnabled(false);
		}
	}

	private void configurarVentasDiarias() {
		mostrarComponentes(etiquetaFecha, campoFecha);
		actualizarColumnas(COLUMNAS_VENTAS_DIARIAS);
	}

	private void configurarVentasMensuales() {
		mostrarComponentes(etiquetaMes, campoMes, etiquetaAnio, campoAnio);
		actualizarColumnas(COLUMNAS_VENTAS_MENSUALES);
	}

	private void configurarVentasAnuales() {
		mostrarComponentes(etiquetaAnio, campoAnio);
		actualizarColumnas(COLUMNAS_VENTAS_ANUALES);
	}

	private void configurarUtilidadBruta() {
		mostrarComponentes(etiquetaFechaInicio, campoFechaInicio, etiquetaFechaFin, campoFechaFin);
		actualizarColumnas(COLUMNAS_UTILIDAD_BRUTA);
	}

	private void configurarProductosMasVendidos() {
		mostrarComponentes(etiquetaFechaInicio, campoFechaInicio, etiquetaFechaFin, campoFechaFin);
		actualizarColumnas(COLUMNAS_PRODUCTOS_MAS_VENDIDOS);
	}

	private void configurarClientesMayorCompra() {
		mostrarComponentes(etiquetaFechaInicio, campoFechaInicio, etiquetaFechaFin, campoFechaFin);
		actualizarColumnas(COLUMNAS_CLIENTES_MAYOR_COMPRA);
	}

	private void configurarVentasPorFormaPago() {
		mostrarComponentes(etiquetaFechaInicio, campoFechaInicio, etiquetaFechaFin, campoFechaFin);
		actualizarColumnas(COLUMNAS_VENTAS_FORMA_PAGO);
	}

	private void configurarInventarioValorizado() {
		actualizarColumnas(COLUMNAS_INVENTARIO_VALORIZADO);
	}

	private void configurarResumenContable() {
		mostrarComponentes(etiquetaFechaInicio, campoFechaInicio, etiquetaFechaFin, campoFechaFin);
		actualizarColumnas(COLUMNAS_RESUMEN_CONTABLE);
	}

	private void ocultarFiltros() {
		ocultarComponentes(etiquetaFecha, campoFecha, etiquetaMes, campoMes, etiquetaAnio, campoAnio,
				etiquetaFechaInicio, campoFechaInicio, etiquetaFechaFin, campoFechaFin);
	}

	private JFormattedTextField crearCampoFecha() {
		try {
			MaskFormatter mascara = new MaskFormatter("##/##/####");
			mascara.setPlaceholderCharacter('_');
			return new JFormattedTextField(mascara);
		} catch (ParseException e) {
			return new JFormattedTextField();
		}
	}

	private JFormattedTextField crearCampoAnio() {
		try {
			MaskFormatter mascara = new MaskFormatter("####");
			mascara.setPlaceholderCharacter('_');
			return new JFormattedTextField(mascara);
		} catch (ParseException e) {
			return new JFormattedTextField();
		}
	}

	public boolean esReporteProductosMasVendidos() {
		Object seleccionado = comboTipoReporte.getSelectedItem();
		return seleccionado != null && REPORTE_PRODUCTOS_MAS_VENDIDOS.equals(seleccionado.toString());
	}

	public boolean esReporteVentasDiarias() {
		Object seleccionado = comboTipoReporte.getSelectedItem();
		return seleccionado != null && REPORTE_VENTAS_DIARIAS.equals(seleccionado.toString());
	}

	public boolean esReporteVentasMensuales() {
		Object seleccionado = comboTipoReporte.getSelectedItem();
		return seleccionado != null && REPORTE_VENTAS_MENSUALES.equals(seleccionado.toString());
	}

	public boolean esReporteVentasAnuales() {
		Object seleccionado = comboTipoReporte.getSelectedItem();
		return seleccionado != null && REPORTE_VENTAS_ANUALES.equals(seleccionado.toString());
	}

	public boolean esReporteUtilidadBruta() {
		Object seleccionado = comboTipoReporte.getSelectedItem();
		return seleccionado != null && REPORTE_UTILIDAD_BRUTA.equals(seleccionado.toString());
	}

	public boolean esReporteVentasFormaPago() {
		Object seleccionado = comboTipoReporte.getSelectedItem();
		return seleccionado != null && REPORTE_VENTAS_FORMA_PAGO.equals(seleccionado.toString());
	}

	public boolean esReporteClientesMayorCompra() {
		Object seleccionado = comboTipoReporte.getSelectedItem();
		return seleccionado != null && REPORTE_CLIENTES_MAYOR_COMPRA.equals(seleccionado.toString());
	}

	public boolean esReporteInventarioValorizado() {
		Object seleccionado = comboTipoReporte.getSelectedItem();
		return seleccionado != null && REPORTE_INVENTARIO_VALORIZADO.equals(seleccionado.toString());
	}

	public boolean esReporteResumenContable() {
		Object seleccionado = comboTipoReporte.getSelectedItem();
		return seleccionado != null && REPORTE_RESUMEN_CONTABLE.equals(seleccionado.toString());
	}

	public String obtenerTipoReporteSeleccionado() {
		Object seleccionado = comboTipoReporte.getSelectedItem();
		return seleccionado != null ? seleccionado.toString() : "";
	}

	public LocalDate obtenerFechaInicioReporte() throws Exception {
		return parsearFecha(campoFechaInicio.getText().trim());
	}

	public LocalDate obtenerFechaFinReporte() throws Exception {
		return parsearFecha(campoFechaFin.getText().trim());
	}

	public LocalDate obtenerFechaReporte() throws Exception {
		LocalDate fecha = parsearFecha(campoFecha.getText().trim());
		if (fecha == null) {
			throw new Exception("Debe ingresar una fecha completa con formato dd/MM/yyyy.");
		}
		return fecha;
	}

	public int obtenerMesReporte() throws Exception {
		String textoMes = campoMes.getText().trim();
		if (textoMes.isBlank()) {
			throw new Exception("Debe ingresar el mes.");
		}

		try {
			int mes = Integer.parseInt(textoMes);
			if (mes < 1 || mes > 12) {
				throw new NumberFormatException();
			}
			return mes;
		} catch (NumberFormatException e) {
			throw new Exception("El mes debe ser un numero entre 1 y 12.");
		}
	}

	public int obtenerAnioReporte() throws Exception {
		String textoAnio = campoAnio.getText().trim();
		if (textoAnio.isBlank() || textoAnio.contains("_")) {
			throw new Exception("Debe ingresar un año completo.");
		}

		try {
			int anio = Integer.parseInt(textoAnio);
			if (anio <= 0) {
				throw new NumberFormatException();
			}
			return anio;
		} catch (NumberFormatException e) {
			throw new Exception("El año debe ser un numero valido.");
		}
	}

	public void mostrarResumenProductos(List<ResumenProductoDTO> resumenes) {
		limpiarTabla();
		if (resumenes == null) {
			actualizarTextoTotal(TEXTO_TOTAL, 0);
			actualizarEstadoBotonGenerarReporte();
			return;
		}

		for (ResumenProductoDTO r : resumenes) {
			modeloTabla.addRow(new Object[] { r.getCodigoProducto(), r.getNombreProducto(), r.getCantidadVendida(),
					FORMATO_MONEDA.format(r.getTotalVendido()) });
		}
		actualizarTextoTotal(TEXTO_TOTAL, resumenes.size());
		actualizarEstadoBotonGenerarReporte();
	}

	public void mostrarVentasPorFormaPago(List<ResumenFormaPagoDTO> resumen) {
		limpiarTabla();
		if (resumen == null) {
			actualizarTextoTotal(TEXTO_TOTAL, 0);
			actualizarEstadoBotonGenerarReporte();
			return;
		}

		for (ResumenFormaPagoDTO item : resumen) {
			modeloTabla.addRow(new Object[] { item.getFormaPago(), item.getCantidadVentas(),
					FORMATO_MONEDA.format(item.getValorTotal()) });
		}
		actualizarTextoTotal(TEXTO_TOTAL, resumen.size());
		actualizarEstadoBotonGenerarReporte();
	}

	public void mostrarClientesMayorCompra(List<ResumenClienteDTO> resumen) {
		limpiarTabla();
		if (resumen == null) {
			actualizarTextoTotal(TEXTO_TOTAL, 0);
			actualizarEstadoBotonGenerarReporte();
			return;
		}

		for (ResumenClienteDTO item : resumen) {
			modeloTabla.addRow(new Object[] { item.getCodigoCliente(), item.getNombreCliente(),
					item.getCantidadCompras(), FORMATO_MONEDA.format(item.getTotalComprado()) });
		}
		actualizarTextoTotal(TEXTO_TOTAL, resumen.size());
		actualizarEstadoBotonGenerarReporte();
	}

	public void mostrarInventarioValorizado(List<ResumenInventarioValorizadoDTO> resumen) {
		limpiarTabla();
		if (resumen == null) {
			actualizarTextoTotal(TEXTO_TOTAL, 0);
			actualizarEstadoBotonGenerarReporte();
			return;
		}

		double valorTotalInventario = 0;
		for (ResumenInventarioValorizadoDTO item : resumen) {
			valorTotalInventario += item.getValorInventario();
			modeloTabla.addRow(new Object[] { item.getCodigoProducto(), item.getNombreProducto(), item.getCategoria(),
					item.getStockActual(), FORMATO_MONEDA.format(item.getPrecioCompra()),
					FORMATO_MONEDA.format(item.getValorInventario()) });
		}

		modeloTabla.addRow(new Object[] { "TOTAL", "", "", "", "", FORMATO_MONEDA.format(valorTotalInventario) });
		actualizarTextoTotal(TEXTO_TOTAL, resumen.size());
		actualizarEstadoBotonGenerarReporte();
	}

	public void mostrarResumenContable(ResumenContableDTO resumen) {
		limpiarTabla();
		if (resumen == null) {
			actualizarTextoTotal(TEXTO_TOTAL, 0);
			actualizarEstadoBotonGenerarReporte();
			return;
		}

		modeloTabla.addRow(new Object[] { FORMATO_MONEDA.format(resumen.getIngresos()),
				FORMATO_MONEDA.format(resumen.getEgresos()), FORMATO_MONEDA.format(resumen.getUtilidad()) });
		actualizarTextoTotal(TEXTO_TOTAL, 1);
		actualizarEstadoBotonGenerarReporte();
	}

	public void mostrarVentasDiarias(ResumenVentasDTO resumen) {
		limpiarTabla();
		if (resumen == null) {
			actualizarTextoTotal(TEXTO_TOTAL, 0);
			actualizarEstadoBotonGenerarReporte();
			return;
		}

		for (Venta venta : resumen.getVentas()) {
			modeloTabla
					.addRow(new Object[] { venta.getNumeroFactura(),
							venta.getFechaHora() != null ? venta.getFechaHora().toLocalDate().format(FORMATO_FECHA) : "",
							venta.getCliente() != null && !venta.getCliente().isBlank() ? venta.getCliente()
									: "ANÓNIMO",
							venta.getFormaPago(), FORMATO_MONEDA.format(venta.getSubTotal()),
							FORMATO_MONEDA.format(venta.getImpuestos()), FORMATO_MONEDA.format(venta.getTotal()) });
		}

		modeloTabla.addRow(new Object[] { "TOTAL", formatearPeriodo(resumen.getPeriodo()), resumen.getCantidadVentas() + " ventas", "",
				FORMATO_MONEDA.format(resumen.getSubtotalVentas()), FORMATO_MONEDA.format(resumen.getImpuestos()),
				FORMATO_MONEDA.format(resumen.getTotalVentas()) });

		actualizarTextoTotal(TEXTO_TOTAL, resumen.getCantidadVentas());
		actualizarEstadoBotonGenerarReporte();
	}

	public void mostrarResumenVentas(ResumenVentasDTO resumen) {
		limpiarTabla();
		if (resumen == null) {
			actualizarTextoTotal(TEXTO_TOTAL, 0);
			actualizarEstadoBotonGenerarReporte();
			return;
		}

		for (Venta venta : resumen.getVentas()) {
			modeloTabla
					.addRow(new Object[] { venta.getNumeroFactura(),
							venta.getFechaHora() != null ? venta.getFechaHora().toLocalDate().format(FORMATO_FECHA) : "",
							venta.getCliente() != null && !venta.getCliente().isBlank() ? venta.getCliente()
									: "ANÓNIMO",
							venta.getFormaPago(), FORMATO_MONEDA.format(venta.getSubTotal()),
							FORMATO_MONEDA.format(venta.getImpuestos()), FORMATO_MONEDA.format(venta.getTotal()) });
		}

		modeloTabla.addRow(new Object[] { "TOTAL", formatearPeriodo(resumen.getPeriodo()), resumen.getCantidadVentas() + " ventas", "",
				FORMATO_MONEDA.format(resumen.getSubtotalVentas()), FORMATO_MONEDA.format(resumen.getImpuestos()),
				FORMATO_MONEDA.format(resumen.getTotalVentas()) });
		actualizarTextoTotal(TEXTO_TOTAL, resumen.getCantidadVentas());
		actualizarEstadoBotonGenerarReporte();
	}

	public void mostrarUtilidadBruta(ResumenUtilidadBrutaDTO resumen) {
		limpiarTabla();
		if (resumen == null) {
			actualizarTextoTotal(TEXTO_TOTAL, 0);
			actualizarEstadoBotonGenerarReporte();
			return;
		}

		for (DetalleUtilidadBrutaDTO detalle : resumen.getDetalles()) {
			modeloTabla.addRow(new Object[] { detalle.getNombreProducto(), detalle.getCantidadVendida(),
					FORMATO_MONEDA.format(detalle.getVentas()), FORMATO_MONEDA.format(detalle.getCostoVenta()),
					FORMATO_MONEDA.format(detalle.getUtilidad()) });
		}

		modeloTabla.addRow(new Object[] { "TOTAL", resumen.getCantidadVendida(),
				FORMATO_MONEDA.format(resumen.getTotalVentas()), FORMATO_MONEDA.format(resumen.getCostoVentas()),
				FORMATO_MONEDA.format(resumen.getUtilidadBruta()) });
		actualizarTextoTotal(TEXTO_TOTAL, resumen.getDetalles().size());
		actualizarEstadoBotonGenerarReporte();
	}

	private LocalDate parsearFecha(String texto) throws Exception {
		if (texto == null || texto.isBlank() || texto.contains("_")) {
			return null;
		}

		try {
			return LocalDate.parse(texto, FORMATO_FECHA);
		} catch (Exception e) {
			throw new Exception("La fecha debe tener formato dd/MM/yyyy.");
		}
	}

	private String formatearPeriodo(String periodo) {
		if (periodo == null || periodo.isBlank()) {
			return "";
		}

		try {
			return LocalDate.parse(periodo).format(FORMATO_FECHA);
		} catch (Exception e) {
			return periodo;
		}
	}

}
