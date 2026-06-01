package co.edu.uptc.sistienda.contabilidad.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

import co.edu.uptc.sistienda.compras.modelo.Compra;
import co.edu.uptc.sistienda.contabilidad.modelo.LineaMovimientoContable;
import co.edu.uptc.sistienda.contabilidad.modelo.MovimientoContable;
import co.edu.uptc.sistienda.modelo.Producto;
import co.edu.uptc.sistienda.modelo.Venta;
import co.edu.uptc.sistienda.negocio.GestionReportes;

public class PanelContabilidad extends JPanel {

	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private CardLayout layoutContenido;
	private JPanel panelContenido;
	private JTable tablaMovimientos;
	private DefaultTableModel modeloMovimientos;
	private JTextField campoCuenta;
	private JTextField campoFechaInicio;
	private JTextField campoFechaFin;
	private JTextArea areaReportes;
	private JComboBox<String> comboReporte;

	private List<Venta> ventas = new ArrayList<>();
	private List<Compra> compras = new ArrayList<>();
	private List<Producto> productos = new ArrayList<>();
	private List<MovimientoContable> movimientos = new ArrayList<>();
	private GestionReportes gestionReportes = new GestionReportes();

	public PanelContabilidad() {
		setLayout(new BorderLayout());

		layoutContenido = new CardLayout();
		panelContenido = new JPanel(layoutContenido);
		panelContenido.add(crearPanelMovimientos(), "MOVIMIENTOS");
		panelContenido.add(crearPanelReportes(), "REPORTES");
		panelContenido.add(crearPanelConsultas(), "CONSULTAS");

		add(crearMenuLateral(), BorderLayout.WEST);
		add(panelContenido, BorderLayout.CENTER);
	}

	private JPanel crearMenuLateral() {
		JPanel menu = new JPanel();
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		menu.setPreferredSize(new Dimension(180, 0));
		menu.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		JButton botonMovimientos = new JButton("Movimientos");
		botonMovimientos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
		botonMovimientos.addActionListener(e -> mostrarMovimientos());

		JButton botonReportes = new JButton("Reportes");
		botonReportes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
		botonReportes.addActionListener(e -> mostrarReportes());

		JButton botonConsultas = new JButton("Consultas");
		botonConsultas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
		botonConsultas.addActionListener(e -> mostrarConsultas());

		menu.add(new JLabel("CONTABILIDAD"));
		menu.add(botonMovimientos);
		menu.add(botonReportes);
		menu.add(botonConsultas);
		return menu;
	}

	private JPanel crearPanelMovimientos() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		modeloMovimientos = new DefaultTableModel() {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		modeloMovimientos.addColumn("Codigo");
		modeloMovimientos.addColumn("Fecha");
		modeloMovimientos.addColumn("Tipo");
		modeloMovimientos.addColumn("Documento");
		modeloMovimientos.addColumn("Tercero");
		modeloMovimientos.addColumn("Debitos");
		modeloMovimientos.addColumn("Creditos");
		modeloMovimientos.addColumn("Estado");

		tablaMovimientos = new JTable(modeloMovimientos);
		panel.add(new JLabel("Movimientos contables por partida doble"), BorderLayout.NORTH);
		panel.add(new JScrollPane(tablaMovimientos), BorderLayout.CENTER);
		return panel;
	}

	private JPanel crearPanelReportes() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT));
		comboReporte = new JComboBox<>(new String[] { "Ventas del periodo", "Utilidad bruta", "Productos mas vendidos",
				"Clientes con mayor compra", "Ventas por forma de pago", "Inventario valorizado", "Resumen contable" });
		JButton botonGenerar = new JButton("Generar");
		botonGenerar.addActionListener(e -> generarReporteSeleccionado());

		barra.add(new JLabel("Reporte:"));
		barra.add(comboReporte);
		barra.add(new JLabel("Desde:"));
		barra.add(campoFechaInicio = new JTextField(LocalDate.now().withDayOfMonth(1).format(FORMATO_FECHA), 8));
		barra.add(new JLabel("Hasta:"));
		barra.add(campoFechaFin = new JTextField(LocalDate.now().format(FORMATO_FECHA), 8));
		barra.add(botonGenerar);

		areaReportes = new JTextArea();
		areaReportes.setEditable(false);

		panel.add(barra, BorderLayout.NORTH);
		panel.add(new JScrollPane(areaReportes), BorderLayout.CENTER);
		return panel;
	}

	private JPanel crearPanelConsultas() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		JPanel filtros = new JPanel(new GridLayout(2, 4, 6, 4));
		filtros.add(new JLabel("Cuenta"));
		filtros.add(new JLabel("Fecha inicio"));
		filtros.add(new JLabel("Fecha fin"));
		filtros.add(new JLabel(""));

		campoCuenta = new JTextField("Todas las cuentas");
		JTextField campoInicioConsulta = new JTextField(LocalDate.now().withDayOfMonth(1).format(FORMATO_FECHA));
		JTextField campoFinConsulta = new JTextField(LocalDate.now().format(FORMATO_FECHA));
		JButton botonConsultar = new JButton("Consultar");
		botonConsultar.addActionListener(e -> consultarMovimientos(campoInicioConsulta, campoFinConsulta));

		filtros.add(campoCuenta);
		filtros.add(campoInicioConsulta);
		filtros.add(campoFinConsulta);
		filtros.add(botonConsultar);

		panel.add(filtros, BorderLayout.NORTH);
		panel.add(new JLabel("La consulta se muestra en la tabla de Movimientos."), BorderLayout.CENTER);
		return panel;
	}

	public void cargarVentas(List<Venta> ventas) {
		this.ventas = ventas != null ? ventas : new ArrayList<>();
		refrescarTablaMovimientos(this.movimientos);
	}

	public void cargarDatos(List<Venta> ventas, List<Compra> compras, List<Producto> productos,
			List<MovimientoContable> movimientos) {
		this.ventas = ventas != null ? ventas : new ArrayList<>();
		this.compras = compras != null ? compras : new ArrayList<>();
		this.productos = productos != null ? productos : new ArrayList<>();
		this.movimientos = movimientos != null ? movimientos : new ArrayList<>();
		refrescarTablaMovimientos(this.movimientos);
	}

	private void refrescarTablaMovimientos(List<MovimientoContable> movimientosAMostrar) {
		if (modeloMovimientos == null) {
			return;
		}
		modeloMovimientos.setRowCount(0);
		for (MovimientoContable movimiento : movimientosAMostrar) {
			modeloMovimientos.addRow(new Object[] { movimiento.getCodigoMovimiento(), movimiento.getFechaMovimiento(),
					movimiento.getTipoOperacion(), movimiento.getDocumentoOrigen(), movimiento.getTercero(),
					"$" + String.format("%,.0f", movimiento.getTotalDebito()),
					"$" + String.format("%,.0f", movimiento.getTotalCredito()),
					movimiento.isAnulado() ? "ANULADO" : (movimiento.estaCuadrado() ? "CUADRADO" : "DESCUADRADO") });
		}
	}

	private void generarReporteSeleccionado() {
		LocalDate inicio = leerFecha(campoFechaInicio.getText());
		LocalDate fin = leerFecha(campoFechaFin.getText());
		if (inicio == null || fin == null) {
			return;
		}

		String opcion = (String) comboReporte.getSelectedItem();
		String texto;
		if ("Ventas del periodo".equals(opcion)) {
			texto = "Ventas del periodo: $"
					+ String.format("%,.0f", gestionReportes.calcularTotalVentas(ventas, inicio, fin));
		} else if ("Utilidad bruta".equals(opcion)) {
			texto = "Utilidad bruta: $"
					+ String.format("%,.0f", gestionReportes.calcularUtilidadBruta(ventas, inicio, fin));
		} else if ("Productos mas vendidos".equals(opcion)) {
			texto = gestionReportes.generarReporteProductosMasVendidos(ventas);
		} else if ("Clientes con mayor compra".equals(opcion)) {
			texto = gestionReportes.generarReporteClientesConMayorCompra(ventas);
		} else if ("Ventas por forma de pago".equals(opcion)) {
			texto = gestionReportes.generarReporteVentasPorFormaPago(ventas);
		} else if ("Inventario valorizado".equals(opcion)) {
			texto = "Inventario valorizado: $"
					+ String.format("%,.0f", gestionReportes.calcularInventarioValorizado(productos));
		} else {
			texto = gestionReportes.generarResumenContable(movimientos, inicio, fin);
		}
		areaReportes.setText(texto);
	}

	private void consultarMovimientos(JTextField campoInicioConsulta, JTextField campoFinConsulta) {
		LocalDate inicio = leerFecha(campoInicioConsulta.getText());
		LocalDate fin = leerFecha(campoFinConsulta.getText());
		if (inicio == null || fin == null) {
			return;
		}
		String cuenta = campoCuenta.getText().trim();
		List<MovimientoContable> filtrados = new ArrayList<>();
		for (MovimientoContable movimiento : movimientos) {
			boolean fechaOk = !movimiento.getFechaMovimiento().isBefore(inicio)
					&& !movimiento.getFechaMovimiento().isAfter(fin);
			boolean cuentaOk = cuenta.isEmpty() || "Todas las cuentas".equalsIgnoreCase(cuenta)
					|| movimientoContieneCuenta(movimiento, cuenta);
			if (fechaOk && cuentaOk) {
				filtrados.add(movimiento);
			}
		}
		refrescarTablaMovimientos(filtrados);
		mostrarMovimientos();
	}

	private boolean movimientoContieneCuenta(MovimientoContable movimiento, String cuenta) {
		for (LineaMovimientoContable linea : movimiento.getLineas()) {
			if (linea.getCuentaContable() != null
					&& linea.getCuentaContable().toLowerCase().contains(cuenta.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	private LocalDate leerFecha(String texto) {
		try {
			return LocalDate.parse(texto.trim(), FORMATO_FECHA);
		} catch (DateTimeParseException ex) {
			JOptionPane.showMessageDialog(this, "Use fechas con formato dd/mm/aaaa.");
			return null;
		}
	}

	public void mostrarMovimientos() {
		layoutContenido.show(panelContenido, "MOVIMIENTOS");
	}

	public void mostrarReportes() {
		layoutContenido.show(panelContenido, "REPORTES");
	}

	public void mostrarConsultas() {
		layoutContenido.show(panelContenido, "CONSULTAS");
	}
}