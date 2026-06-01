package co.edu.uptc.sistienda.ventas.gui;

import java.awt.*;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.sistienda.modelo.DetalleVenta;
import co.edu.uptc.sistienda.modelo.Venta;

public class DialogoFactura extends JDialog {

	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public DialogoFactura(JFrame ventanaPrincipal, Venta venta) {
		super(ventanaPrincipal, "Factura – " + venta.getNumeroFactura(), true);
		setSize(730, 700);
		setMinimumSize(new Dimension(660, 580));
		setLocationRelativeTo(ventanaPrincipal);
		setLayout(new BorderLayout(6, 6));
		getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

		add(construirEncabezado(venta), BorderLayout.NORTH);
		add(construirCuerpo(venta), BorderLayout.CENTER);
		add(construirPie(), BorderLayout.SOUTH);
	}

	private JPanel construirEncabezado(Venta venta) {
		JPanel panelEncabezado = new JPanel(new BorderLayout(4, 4));
		panelEncabezado.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));

		JLabel etiquetaEmpresa = new JLabel("Sistienda S.A.S", SwingConstants.CENTER);
		etiquetaEmpresa.setFont(new Font("SansSerif", Font.BOLD, 18));
		panelEncabezado.add(etiquetaEmpresa, BorderLayout.NORTH);

		JPanel panelTituloYNumero = new JPanel(new BorderLayout());
		JLabel etiquetaTitulo = new JLabel("  Factura electrónica de Venta");
		etiquetaTitulo.setFont(new Font("SansSerif", Font.PLAIN, 11));
		JLabel etiquetaNumeroFactura = new JLabel(venta.getNumeroFactura() + "  ");
		etiquetaNumeroFactura.setFont(new Font("SansSerif", Font.BOLD, 14));
		etiquetaNumeroFactura.setForeground(new Color(160, 0, 0));
		etiquetaNumeroFactura.setHorizontalAlignment(SwingConstants.RIGHT);
		panelTituloYNumero.add(etiquetaTitulo, BorderLayout.WEST);
		panelTituloYNumero.add(etiquetaNumeroFactura, BorderLayout.EAST);
		panelEncabezado.add(panelTituloYNumero, BorderLayout.CENTER);

		// Grilla con fechas, forma de pago y estado de la factura
		JPanel panelDatosFactura = new JPanel(new GridBagLayout());
		panelDatosFactura.setBorder(BorderFactory.createEmptyBorder(4, 0, 6, 0));
		GridBagConstraints restricciones = crearRestriccionesBase();

		String fechaCreacion = venta.getFechaHora() != null ? venta.getFechaHora().format(FORMATO_FECHA) : "—";
		String fechaVencimiento = venta.getFechaVencimiento() != null
				? venta.getFechaVencimiento().format(FORMATO_FECHA)
				: "—";
		String formaPago = venta.getFormaPago() != null ? venta.getFormaPago().getDescripcion() : "—";
		// Si existe un medio de pago específico se muestra; si no, se repite la forma
		// de pago
		String medioPago = venta.getMedioPago() != null && !venta.getMedioPago().isEmpty() ? venta.getMedioPago()
				: formaPago;
		String estadoFactura = venta.isAnulada() ? "ANULADA" : "VÁLIDA";

		agregarFila(panelDatosFactura, restricciones, 0, "Fecha creación:", fechaCreacion, "Fecha vencimiento:",
				fechaVencimiento);
		agregarFila(panelDatosFactura, restricciones, 1, "Forma de pago:", formaPago, "Medio de pago:", medioPago);
		agregarFila(panelDatosFactura, restricciones, 2, "Estado:", estadoFactura, "", "");
		panelEncabezado.add(panelDatosFactura, BorderLayout.SOUTH);

		return panelEncabezado;
	}

	private JPanel construirCuerpo(Venta venta) {
		JPanel panelCuerpo = new JPanel(new BorderLayout(0, 8));
		panelCuerpo.add(construirPanelCliente(venta), BorderLayout.NORTH);
		panelCuerpo.add(construirTablaItems(venta), BorderLayout.CENTER);
		panelCuerpo.add(construirTotales(venta), BorderLayout.SOUTH);
		return panelCuerpo;
	}

	// Sección con los datos personales y fiscales del cliente
	private JPanel construirPanelCliente(Venta venta) {
		JPanel panelCliente = new JPanel(new GridBagLayout());
		panelCliente.setBorder(new TitledBorder("Datos del cliente"));
		GridBagConstraints restricciones = crearRestriccionesBase();

		// Valores por defecto en caso de que la venta no tenga cliente asignado
		String nombreCliente = "—", tipoIdentificacion = "—", numeroIdentificacion = "—", tipoPersona = "—";
		String direccion = "—", ciudad = "—", telefono = "—", correo = "—";
		String responsabilidadFisc = "—", responsabilidadTrib = "—", tipoCliente = "—";

		if (venta.getCliente() != null) {
			var cliente = venta.getCliente();
			nombreCliente = textoOGuion(cliente.getNombreCompletoORazonSocial());
			tipoPersona = cliente.getTipoPersona() != null ? cliente.getTipoPersona().getDescripcion() : "—";
			tipoIdentificacion = cliente.getTipoIdentificacion() != null ? cliente.getTipoIdentificacion().name() : "—";
			numeroIdentificacion = textoOGuion(cliente.getNumeroIdentificacion());
			direccion = textoOGuion(cliente.getDireccion());
			ciudad = textoOGuion(cliente.getCiudad());
			telefono = textoOGuion(cliente.getTelefono());
			correo = textoOGuion(cliente.getCorreoElectronico());
			responsabilidadFisc = textoOGuion(cliente.getResponsabilidadFiscal());
			responsabilidadTrib = textoOGuion(cliente.getResponsabilidadTributaria());
			tipoCliente = cliente.getTipoCliente() != null ? cliente.getTipoCliente().getDescripcion() : "—";
		}

		agregarFila(panelCliente, restricciones, 0, "Cliente:", nombreCliente, "Tipo persona:", tipoPersona);
		agregarFila(panelCliente, restricciones, 1, "Identificación:", tipoIdentificacion + " " + numeroIdentificacion,
				"Tipo cliente:", tipoCliente);
		agregarFila(panelCliente, restricciones, 2, "Dirección:", direccion, "Ciudad:", ciudad);
		agregarFila(panelCliente, restricciones, 3, "Teléfono:", telefono, "Correo:", correo);
		agregarFila(panelCliente, restricciones, 4, "Resp. Fiscal:", responsabilidadFisc, "Resp. Tributaria:",
				responsabilidadTrib);

		return panelCliente;
	}

	// Tabla con cada producto, cantidad, precio, descuento e impuesto de la venta
	private JScrollPane construirTablaItems(Venta venta) {
		DefaultTableModel modeloTabla = new DefaultTableModel(new String[] { "N°", "Código", "Producto", "Impuesto",
				"Cant.", "Precio Unit.", "Dto%", "Subtotal", "Total Línea" }, 0) {
			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false; // la factura es de solo lectura
			}
		};

		int numeroLinea = 1;
		for (DetalleVenta itemVenta : venta.getItems()) {
			modeloTabla.addRow(new Object[] { numeroLinea++, itemVenta.getProducto().getCodigoInterno(),
					itemVenta.getProducto().getNombreProducto(), itemVenta.getDescripcionImpuesto(),
					itemVenta.getCantidad(), "$" + String.format("%,.0f", itemVenta.getPrecioUnitario()),
					String.format("%.1f%%", itemVenta.getDescuentoDto()),
					"$" + String.format("%,.0f", itemVenta.getSubtotal()),
					"$" + String.format("%,.0f", itemVenta.getValorTotal()) });
		}

		JTable tablaItems = new JTable(modeloTabla);
		tablaItems.setRowHeight(22);
		tablaItems.getTableHeader().setReorderingAllowed(false);

		JScrollPane scrollTabla = new JScrollPane(tablaItems);
		scrollTabla.setPreferredSize(new Dimension(0, 180));
		return scrollTabla;
	}

	// Subtotal, IVA y total a pagar; el total se muestra en negrita para destacarlo
	private JPanel construirTotales(Venta venta) {
		JPanel panelTotales = new JPanel(new GridBagLayout());
		panelTotales.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));

		GridBagConstraints restricciones = new GridBagConstraints();
		restricciones.insets = new Insets(3, 10, 3, 10);
		restricciones.anchor = GridBagConstraints.EAST;

		agregarTotal(panelTotales, restricciones, 0, "Subtotal (sin impuesto):",
				String.format("$%,.2f", venta.getSubtotal()), false);
		agregarTotal(panelTotales, restricciones, 1, "IVA:", String.format("$%,.2f", venta.getIva()), false);
		agregarTotal(panelTotales, restricciones, 2, "TOTAL A PAGAR:", String.format("$%,.2f", venta.getTotal()), true);
		agregarTotal(panelTotales, restricciones, 3, "Valor en letras:", venta.getValorEnLetras(), false);
		agregarTotal(panelTotales, restricciones, 4, "CUFE:", venta.getCufe(), false);
		return panelTotales;
	}

	private JPanel construirPie() {
		JPanel panelPie = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton botonCerrar = new JButton("Cerrar");
		botonCerrar.addActionListener(e -> dispose());
		panelPie.add(botonCerrar);
		return panelPie;
	}

	// Utilidades

	// Configuración base de posicionamiento para las grillas de la factura
	private GridBagConstraints crearRestriccionesBase() {
		GridBagConstraints restricciones = new GridBagConstraints();
		restricciones.insets = new Insets(2, 6, 2, 6);
		restricciones.anchor = GridBagConstraints.WEST;
		return restricciones;
	}

	// Agrega una fila de dos pares etiqueta–valor al panel de grilla recibido
	private void agregarFila(JPanel panel, GridBagConstraints restricciones, int numeroFila, String etiqueta1,
			String valor1, String etiqueta2, String valor2) {
		restricciones.gridx = 0;
		restricciones.gridy = numeroFila;
		restricciones.fill = GridBagConstraints.NONE;
		restricciones.weightx = 0;
		panel.add(crearEtiqueta(etiqueta1, true), restricciones);

		restricciones.gridx = 1;
		restricciones.fill = GridBagConstraints.HORIZONTAL;
		restricciones.weightx = 0.5;
		panel.add(crearEtiqueta(valor1, false), restricciones);

		if (!etiqueta2.isEmpty()) {
			restricciones.gridx = 2;
			restricciones.fill = GridBagConstraints.NONE;
			restricciones.weightx = 0;
			panel.add(crearEtiqueta(etiqueta2, true), restricciones);

			restricciones.gridx = 3;
			restricciones.fill = GridBagConstraints.HORIZONTAL;
			restricciones.weightx = 0.5;
			panel.add(crearEtiqueta(valor2, false), restricciones);
		}
	}

	private void agregarTotal(JPanel panel, GridBagConstraints restricciones, int numeroFila, String etiqueta,
			String valor, boolean negrita) {
		restricciones.gridx = 0;
		restricciones.gridy = numeroFila;
		panel.add(crearEtiqueta(etiqueta, negrita), restricciones);
		restricciones.gridx = 1;
		panel.add(crearEtiqueta(valor, negrita), restricciones);
	}

	private JLabel crearEtiqueta(String texto, boolean negrita) {
		JLabel etiqueta = new JLabel(texto);
		if (negrita)
			etiqueta.setFont(etiqueta.getFont().deriveFont(Font.BOLD));
		return etiqueta;
	}

	// Devuelve el texto tal cual si no está vacío; si está vacío o es null,
	// devuelve un guion
	private String textoOGuion(String valor) {
		return (valor != null && !valor.isEmpty()) ? valor : "—";
	}
}