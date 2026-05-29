package co.edu.uptc.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JOptionPane;

import co.edu.uptc.gui.PanelVentas;
import co.edu.uptc.negocio.GestionCliente;
import co.edu.uptc.negocio.GestionProducto;
import co.edu.uptc.negocio.GestionVenta;
import co.edu.uptc.negocio.dto.clienteDto;
import co.edu.uptc.negocio.dto.detalleVentaDto;
import co.edu.uptc.negocio.dto.productoDto;
import co.edu.uptc.negocio.dto.ventaDto;

public class VentaControlador implements ActionListener {

	private PanelVentas vista;
	private GestionVenta negocioVenta;
	private GestionProducto negocioProducto;
	private GestionCliente negocioCliente; // ¡NUEVO! Para validar clientes

	public VentaControlador(PanelVentas vista, GestionVenta negocio) {
		this.vista = vista;
		this.negocioVenta = negocio;
		this.negocioProducto = new GestionProducto();
		this.negocioCliente = new GestionCliente(); // Lo instanciamos

		this.vista.setControlador(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		switch (comando) {
		case "BUSCAR_PRODUCTO_VENTA":
			buscarProductoParaCarrito();
			break;
		case "AGREGAR_CARRITO":
			agregarAlCarrito();
			break;
		case "QUITAR_CARRITO":
			quitarDelCarrito();
			break;
		case "REGISTRAR_VENTA":
			registrarFactura();
			break;
		case "NUEVA_VENTA":
			vista.limpiarTodo();
			break;
		case "CAMBIO_IVA":
			recalcularTotal();
			break;
		case "VOLVER":
			javax.swing.SwingUtilities.getWindowAncestor(vista).dispose();
			break;
		}
	}

	private void buscarProductoParaCarrito() {
		java.awt.Window ventanaPadre = javax.swing.SwingUtilities.getWindowAncestor(vista);
		co.edu.uptc.gui.BuscadorProductoDialog dialog = new co.edu.uptc.gui.BuscadorProductoDialog(ventanaPadre,
				negocioProducto);
		dialog.setVisible(true);

		int codigoElegido = dialog.getIdSeleccionado();
		String nombreElegido = dialog.getNombreSeleccionado();

		if (codigoElegido != -1) {
			vista.tCodigoProducto.setText(String.valueOf(codigoElegido));
			vista.tNombreProductoVisual.setText(nombreElegido);
			vista.tCantidad.requestFocus();
			vista.tCantidad.selectAll();
		}
	}

	private void agregarAlCarrito() {
		try {
			int codProducto = Integer.parseInt(vista.tCodigoProducto.getText().trim());
			int cantidad = Integer.parseInt(vista.tCantidad.getText().trim());

			if (cantidad <= 0) {
				JOptionPane.showMessageDialog(vista, "La cantidad debe ser mayor a cero.");
				return;
			}

			List<productoDto> prods = negocioProducto.buscarMulticriterio(String.valueOf(codProducto));
			if (prods.isEmpty()) {
				JOptionPane.showMessageDialog(vista, "El código de producto no existe.");
				return;
			}

			productoDto p = prods.get(0);

			if (p.getStockActual() < cantidad) {
				JOptionPane.showMessageDialog(vista,
						"¡Stock insuficiente! Solo quedan " + p.getStockActual() + " unidades.");
				return;
			}

			double subtotal = p.getPrecioVenta() * cantidad; // (Usa tu método getPrecioVenta)

			vista.modeloCarrito.addRow(
					new Object[] { p.getCodigoProducto(), p.getNombre(), cantidad, p.getPrecioVenta(), subtotal });

			recalcularTotal();
			vista.limpiarCamposProducto();

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(vista, "Por favor ingrese valores numéricos válidos.");
		}
	}

	private void quitarDelCarrito() {
		int filaSeleccionada = vista.tablaCarrito.getSelectedRow();
		if (filaSeleccionada >= 0) {
			vista.modeloCarrito.removeRow(filaSeleccionada);
			recalcularTotal();
		} else {
			JOptionPane.showMessageDialog(vista, "Seleccione un producto del carrito para quitarlo.");
		}
	}

	private void recalcularTotal() {
		double totalAcumulado = 0.0;
		for (int i = 0; i < vista.modeloCarrito.getRowCount(); i++) {
			totalAcumulado += Double.parseDouble(vista.modeloCarrito.getValueAt(i, 4).toString());
		}
		if (vista.checkIva.isSelected()) {
			totalAcumulado = totalAcumulado * 1.19;
		}
		vista.actualizarTotalPantalla(totalAcumulado);
	}

	private void registrarFactura() {
		if (vista.modeloCarrito.getRowCount() == 0) {
			JOptionPane.showMessageDialog(vista, "Agregue productos al carrito antes de registrar.");
			return;
		}

		try {
			// --- 1. VALIDACIÓN ESTRICTA DEL CLIENTE ---
			String docCliente = vista.tDocumentoCliente.getText().trim();
			int codClienteDB = 1; // Asumiremos 1 para consumidor final si no escriben nada
			String nombreClienteRecibo = "Consumidor Final";

			if (docCliente.isEmpty()) {
				int resp = JOptionPane.showConfirmDialog(vista,
						"No ingresó documento. ¿Registrar como 'Consumidor Final'?", "Validación",
						JOptionPane.YES_NO_OPTION);
				if (resp != JOptionPane.YES_OPTION)
					return; // Si dice que NO, se cancela la venta
			} else {
				// Buscar cliente en BD
				List<clienteDto> clientesEncontrados = negocioCliente.buscarMulticriterio(docCliente);
				boolean clienteExiste = false;

				for (clienteDto c : clientesEncontrados) {
					if (c.getDocumento().equals(docCliente)) {
						codClienteDB = c.getCodigoCliente();
						nombreClienteRecibo = c.getNombre();
						clienteExiste = true;
						break;
					}
				}

				if (!clienteExiste) {
					JOptionPane.showMessageDialog(vista,
							"¡El cliente con documento " + docCliente
									+ " no existe!\nPor favor regístrelo primero en el módulo de Clientes.",
							"Error de Cliente", JOptionPane.ERROR_MESSAGE);
					return; // DETIENE LA VENTA
				}
			}

			// --- 2. ARMADO DE LA VENTA ---
			ventaDto nuevaVenta = new ventaDto();
			nuevaVenta.setCodigoCliente(codClienteDB);
			nuevaVenta.setFormaPago(vista.cbFormaPago.getSelectedItem().toString());
			nuevaVenta.setAplicaIva(vista.checkIva.isSelected());

			double subtotalVenta = 0;
			for (int i = 0; i < vista.modeloCarrito.getRowCount(); i++) {
				detalleVentaDto detalle = new detalleVentaDto();
				detalle.setCodigoProducto(Integer.parseInt(vista.modeloCarrito.getValueAt(i, 0).toString()));

				// Guardamos el nombre para poder imprimirlo en el recibo
				detalle.setNombreProducto(vista.modeloCarrito.getValueAt(i, 1).toString());

				detalle.setCantidad(Integer.parseInt(vista.modeloCarrito.getValueAt(i, 2).toString()));
				detalle.setPrecioUnitario(Double.parseDouble(vista.modeloCarrito.getValueAt(i, 3).toString()));
				double subtotalLinea = Double.parseDouble(vista.modeloCarrito.getValueAt(i, 4).toString());
				detalle.setSubtotal(subtotalLinea);
				subtotalVenta += subtotalLinea;
				nuevaVenta.agregarDetalle(detalle);
			}

			nuevaVenta.setSubtotal(subtotalVenta);
			double totalFinal = vista.checkIva.isSelected() ? subtotalVenta * 1.19 : subtotalVenta;
			nuevaVenta.setTotal(totalFinal);

			// --- 3. GUARDAR EN BD Y OBTENER NUMERO DE FACTURA ---
			int numFactura = negocioVenta.registrarVentaCompleta(nuevaVenta);

			// --- 4. MOSTRAR EL TICKET FINAL ---
			mostrarTicket(nuevaVenta, numFactura, nombreClienteRecibo, docCliente.isEmpty() ? "22222222" : docCliente);

			vista.limpiarTodo();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, "Error al registrar: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// --- MÉTODO PARA DIBUJAR EL RECIBO ---
	// --- MÉTODO PARA DIBUJAR EL RECIBO (VERSIÓN MODERNA Y VISUAL) ---
	private void mostrarTicket(ventaDto venta, int numFactura, String nomCliente, String docCliente) {

		// 1. Panel principal que contendrá todo el diseño
		javax.swing.JPanel panelTicket = new javax.swing.JPanel(new java.awt.BorderLayout(10, 10));
		panelTicket.setBackground(java.awt.Color.WHITE);
		panelTicket.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));

		// 2. CABECERA (Usando HTML para darle un diseño corporativo)
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String fecha = dtf.format(LocalDateTime.now());

		String cabeceraHTML = "<html><div style='text-align: center; font-family: Arial; width: 300px;'>"
				+ "<h2 style='color: #004080; margin: 0;'>★ MiniMarket System ★</h2>"
				+ "<p style='color: gray; margin: 0; font-size: 10px;'>NIT: 890.123.456-7</p><hr>"
				+ "<table style='width: 100%; text-align: left; font-size: 11px;'>"
				+ "<tr><td><b>Factura No:</b></td><td style='color: red;'><b>" + numFactura + "</b></td></tr>"
				+ "<tr><td><b>Fecha:</b></td><td>" + fecha + "</td></tr>" + "<tr><td><b>Cliente:</b></td><td>"
				+ nomCliente + " (" + docCliente + ")</td></tr>" + "<tr><td><b>Pago:</b></td><td>"
				+ venta.getFormaPago() + "</td></tr>" + "</table><hr></div></html>";

		javax.swing.JLabel lblCabecera = new javax.swing.JLabel(cabeceraHTML);
		panelTicket.add(lblCabecera, java.awt.BorderLayout.NORTH);

		// 3. DETALLE DE PRODUCTOS (Una tabla real, más limpia y alineada)
		String[] columnas = { "Cant", "Producto", "V. Unit", "Subtotal" };
		javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(columnas, 0);

		for (detalleVentaDto d : venta.getDetalles()) {
			modelo.addRow(new Object[] { d.getCantidad(), d.getNombreProducto(),
					String.format("$ %.2f", d.getPrecioUnitario()), String.format("$ %.2f", d.getSubtotal()) });
		}

		javax.swing.JTable tablaTicket = new javax.swing.JTable(modelo);
		tablaTicket.setEnabled(false); // Para que no se pueda editar
		tablaTicket.setShowGrid(false); // Quitar líneas internas para un look más limpio
		tablaTicket.setBackground(java.awt.Color.WHITE);
		tablaTicket.getTableHeader().setBackground(new java.awt.Color(240, 240, 240));
		tablaTicket.getTableHeader().setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 11));
		tablaTicket.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 11));

		// Ajustar anchos de las columnas para que se vea perfecto
		tablaTicket.getColumnModel().getColumn(0).setPreferredWidth(40);
		tablaTicket.getColumnModel().getColumn(1).setPreferredWidth(120);
		tablaTicket.getColumnModel().getColumn(2).setPreferredWidth(70);
		tablaTicket.getColumnModel().getColumn(3).setPreferredWidth(70);

		javax.swing.JScrollPane scrollTabla = new javax.swing.JScrollPane(tablaTicket);
		scrollTabla.setPreferredSize(new java.awt.Dimension(320, 150));
		scrollTabla.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY));
		scrollTabla.getViewport().setBackground(java.awt.Color.WHITE);
		panelTicket.add(scrollTabla, java.awt.BorderLayout.CENTER);

		// 4. PIE DE PÁGINA (Totales bien alineados a la derecha)
		double valorIva = venta.isAplicaIva() ? venta.getSubtotal() * 0.19 : 0;

		String pieHTML = "<html><div style='font-family: Arial; width: 300px;'>"
				+ "<table style='width: 100%; text-align: right; font-size: 12px;'>"
				+ "<tr><td style='text-align: left;'><b>Subtotal:</b></td><td>$ "
				+ String.format("%.2f", venta.getSubtotal()) + "</td></tr>"
				+ "<tr><td style='text-align: left;'><b>IVA (19%):</b></td><td>$ " + String.format("%.2f", valorIva)
				+ "</td></tr>"
				+ "<tr><td style='text-align: left;'><h2 style='color: #004080; margin: 0;'>TOTAL:</h2></td>"
				+ "<td><h2 style='color: #004080; margin: 0;'>$ " + String.format("%.2f", venta.getTotal())
				+ "</h2></td></tr>" + "</table>"
				+ "<hr><div style='text-align: center; color: gray; font-size: 10px; margin-top: 5px;'>"
				+ "¡Gracias por su compra!<br>Software de Facturación Autorizado" + "</div></div></html>";

		javax.swing.JLabel lblPie = new javax.swing.JLabel(pieHTML);
		panelTicket.add(lblPie, java.awt.BorderLayout.SOUTH);

		// 5. Lanzar la ventana (Usamos PLAIN_MESSAGE para quitar el icono por defecto
		// de alerta)
		javax.swing.JOptionPane.showMessageDialog(vista, panelTicket, "Factura Generada",
				javax.swing.JOptionPane.PLAIN_MESSAGE);
	}
}