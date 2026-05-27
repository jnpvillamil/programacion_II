package co.edu.uptc.sistienda.ventas.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.sistienda.modelo.DetalleDevolucion;
import co.edu.uptc.sistienda.modelo.DetalleVenta;
import co.edu.uptc.sistienda.modelo.Venta;

public class DialogoDevolucion extends JDialog {

	private Venta ventaOrigen;
	private JTable tablaItems;
	private DefaultTableModel modeloItems;
	private JTable tablaDevolucion;
	private DefaultTableModel modeloDevolucion;
	private JTextArea areaMotivo;
	private List<DetalleDevolucion> detalles = new ArrayList<>();
	private String motivoResultado;
	private boolean confirmado = false;

	public DialogoDevolucion(JFrame framePrincipal, Venta venta) {
		super(framePrincipal, "Registrar devolución – " + venta.getNumeroFactura(), true);
		this.ventaOrigen = venta;
		setSize(700, 540);
		setMinimumSize(new Dimension(620, 480));
		setLocationRelativeTo(framePrincipal);
		setLayout(new BorderLayout(6, 6));
		getRootPane().setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

		add(construirEncabezado(), BorderLayout.NORTH);
		add(construirCentral(), BorderLayout.CENTER);
		add(construirBotones(), BorderLayout.SOUTH);
		cargarItems();
	}

	private JPanel construirEncabezado() {
		JPanel panelEncabezado = new JPanel(new GridBagLayout());
		panelEncabezado.setBorder(BorderFactory.createTitledBorder("Datos de la venta original"));

		// GridBagConstraints controla dónde y cómo se ubica cada campo en la grilla
		GridBagConstraints restricciones = new GridBagConstraints();
		restricciones.insets = new Insets(3, 6, 3, 6);
		restricciones.anchor = GridBagConstraints.WEST;

		JTextField campoNumeroFactura = campo(ventaOrigen.getNumeroFactura());
		JTextField campoNombreCliente = campo(
				ventaOrigen.getCliente() != null ? ventaOrigen.getCliente().getNombreCompletoORazonSocial() : "-");
		JTextField campoTotalVenta = campo("$" + String.format("%,.0f", ventaOrigen.getTotal()));

		// Fila 1: número de factura y nombre del cliente
		restricciones.gridx = 0;
		restricciones.gridy = 0;
		panelEncabezado.add(new JLabel("Nº Factura:"), restricciones);
		restricciones.gridx = 1;
		panelEncabezado.add(campoNumeroFactura, restricciones);
		restricciones.gridx = 2;
		panelEncabezado.add(new JLabel("Cliente:"), restricciones);
		restricciones.gridx = 3;
		restricciones.fill = GridBagConstraints.HORIZONTAL; // el campo del cliente se estira para ocupar el espacio
															// restante
		restricciones.weightx = 1;
		panelEncabezado.add(campoNombreCliente, restricciones);

		// Fila 2: total de la venta
		restricciones.gridx = 0;
		restricciones.gridy = 1;
		restricciones.fill = GridBagConstraints.NONE;
		restricciones.weightx = 0;
		panelEncabezado.add(new JLabel("Total venta:"), restricciones);
		restricciones.gridx = 1;
		panelEncabezado.add(campoTotalVenta, restricciones);

		return panelEncabezado;
	}

	// Zona central dividida en dos: arriba los ítems de la venta, abajo los que se
	// van a devolver
	private JSplitPane construirCentral() {

		// Panel superior: lista de productos de la venta original
		JPanel panelItemsVenta = new JPanel(new BorderLayout(4, 4));
		panelItemsVenta.setBorder(BorderFactory.createTitledBorder("Ítems de la venta (seleccione y use ▼ Agregar)"));

		modeloItems = new DefaultTableModel(new String[] { "Producto", "Impuesto", "Precio Unit.", "Cant. vendida" },
				0) {
			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false; // la tabla es solo de lectura
			}
		};
		tablaItems = new JTable(modeloItems);
		tablaItems.setRowHeight(20);
		tablaItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panelItemsVenta.add(new JScrollPane(tablaItems), BorderLayout.CENTER);

		JButton botonAgregarItem = new JButton("Agregar ítem a devolución");
		botonAgregarItem.addActionListener(e -> agregarDetalle());
		JPanel panelBotonAgregar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelBotonAgregar.add(botonAgregarItem);
		panelItemsVenta.add(panelBotonAgregar, BorderLayout.SOUTH);

		// Panel inferior: productos que el cliente quiere devolver
		JPanel panelItemsDevolucion = new JPanel(new BorderLayout(4, 4));
		panelItemsDevolucion.setBorder(BorderFactory.createTitledBorder("Ítems a devolver"));

		modeloDevolucion = new DefaultTableModel(new String[] { "Producto", "Cant. a devolver", "Subtotal dev." }, 0) {
			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false; // también solo lectura
			}
		};
		tablaDevolucion = new JTable(modeloDevolucion);
		tablaDevolucion.setRowHeight(20);
		panelItemsDevolucion.add(new JScrollPane(tablaDevolucion), BorderLayout.CENTER);

		JButton botonQuitarItem = new JButton("✕ Quitar seleccionado");
		botonQuitarItem.addActionListener(e -> quitarDetalle());
		JPanel panelBotonQuitar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelBotonQuitar.add(botonQuitarItem);

		JPanel panelMotivo = new JPanel(new BorderLayout(4, 2));
		panelMotivo.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
		panelMotivo.add(new JLabel("Motivo:"), BorderLayout.NORTH);
		areaMotivo = new JTextArea(3, 30);
		areaMotivo.setLineWrap(true);
		areaMotivo.setWrapStyleWord(true);
		panelMotivo.add(new JScrollPane(areaMotivo), BorderLayout.CENTER);

		// Agrupa la tabla de devolución, el botón quitar y el campo de motivo
		JPanel panelTablaMasBoton = new JPanel(new BorderLayout());
		panelTablaMasBoton.add(panelItemsDevolucion, BorderLayout.CENTER);
		panelTablaMasBoton.add(panelBotonQuitar, BorderLayout.SOUTH);

		JPanel panelInferiorCompleto = new JPanel(new BorderLayout());
		panelInferiorCompleto.add(panelTablaMasBoton, BorderLayout.CENTER);
		panelInferiorCompleto.add(panelMotivo, BorderLayout.SOUTH);

		// El divisor permite redimensionar las dos mitades arrastrando
		JSplitPane divisorVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelItemsVenta, panelInferiorCompleto);
		divisorVertical.setDividerLocation(180);
		divisorVertical.setResizeWeight(0.5);
		return divisorVertical;
	}

	private JPanel construirBotones() {
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
		JButton botonConfirmar = new JButton("Confirmar devolución");
		botonConfirmar.addActionListener(e -> confirmar());
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(e -> dispose());
		panelBotones.add(botonCancelar);
		panelBotones.add(botonConfirmar);
		return panelBotones;
	}

	// Rellena la tabla superior con los productos que tenía la venta original
	private void cargarItems() {
		for (DetalleVenta itemVenta : ventaOrigen.getItems()) {
			modeloItems.addRow(
					new Object[] { itemVenta.getProducto().getNombreProducto(), itemVenta.getDescripcionImpuesto(),
							"$" + String.format("%,.0f", itemVenta.getPrecioUnitario()), itemVenta.getCantidad() });
		}
	}

	// El usuario selecciona un producto de la venta y le dice cuántas unidades
	// quiere devolver
	private void agregarDetalle() {
		int filaSeleccionada = tablaItems.getSelectedRow();
		if (filaSeleccionada < 0) {
			JOptionPane.showMessageDialog(this, "Seleccione un ítem.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		DetalleVenta itemOriginal = ventaOrigen.getItems().get(filaSeleccionada);

		// Se le pregunta al usuario cuántas unidades devuelve
		String cantidadIngresada = JOptionPane.showInputDialog(this,
				"Cantidad a devolver (máx. " + itemOriginal.getCantidad() + "): ", "1");
		if (cantidadIngresada == null)
			return; // el usuario canceló el diálogo

		int cantidadADevolver;
		try {
			cantidadADevolver = Integer.parseInt(cantidadIngresada.trim());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Número inválido.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (cantidadADevolver <= 0 || cantidadADevolver > itemOriginal.getCantidad()) {
			JOptionPane.showMessageDialog(this, "La cantidad debe estar entre 1 y " + itemOriginal.getCantidad() + ".",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Crea el detalle de devolución y lo agrega a la lista y a la tabla
		DetalleDevolucion nuevoDetalle = new DetalleDevolucion(itemOriginal, cantidadADevolver);
		detalles.add(nuevoDetalle);
		modeloDevolucion.addRow(new Object[] { itemOriginal.getProducto().getNombreProducto(), cantidadADevolver,
				"$" + String.format("%,.0f", nuevoDetalle.getSubtotalReembolso()) });
	}

	private void quitarDetalle() {
		int filaSeleccionada = tablaDevolucion.getSelectedRow();
		if (filaSeleccionada < 0)
			return;
		detalles.remove(filaSeleccionada);
		modeloDevolucion.removeRow(filaSeleccionada);
	}

	private void confirmar() {
		if (detalles.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Agregue al menos un ítem.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		motivoResultado = areaMotivo.getText().trim();
		if (motivoResultado.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Ingrese el motivo.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		confirmado = true;
		dispose(); // cierra la ventana y regresa el control a quien abrió el diálogo
	}

	// Crea un campo de texto no editable, útil para mostrar datos de solo lectura
	private JTextField campo(String texto) {
		JTextField campoSoloLectura = new JTextField(texto);
		campoSoloLectura.setEditable(false);
		return campoSoloLectura;
	}

	public boolean isConfirmado() {
		return confirmado;
	}

	public List<DetalleDevolucion> getDetalles() {
		return detalles;
	}

	public String getMotivoResultado() {
		return motivoResultado;
	}
}