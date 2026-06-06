package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.negocio.dto.compraDto;
import co.edu.uptc.negocio.dto.itemCompraDto;
import co.edu.uptc.persistencia.database.DatabaseProveedor;

public class PanelCompra extends JPanel {

	private static final long serialVersionUID = 1L;

	// ── Botones ───────────────────────────────────────────────────────────
	public JButton bRegistrar, bAnular, bBuscar, bAgregarItem, bEliminarItem, bLimpiar, bVolver;

	// ── Campos encabezado compra ──────────────────────────────────────────
	public JTextField tFecha, tCodigoProveedor, tRazonSocial, tImpuestos, tSubtotal, tTotal;
	public javax.swing.JCheckBox checkResponsableIva;

	public javax.swing.JComboBox<String> comboProveedores;

	// ── Campos para agregar ítems ─────────────────────────────────────────
	public JTextField tCodProducto, tNomProducto, tCantidad, tCostoUnit;

	// ── Tablas ────────────────────────────────────────────────────────────
	public DefaultTableModel modeloItems, modeloCompras;
	public JTable tablaItems, tablaCompras;

	// ── Lista temporal ───────────────────────────────────────────────────
	private List<itemCompraDto> itemsActuales = new ArrayList<>();

	public PanelCompra() {
		construirPanel();

		cargarProveedoresEnCombo();
		tFecha.setText(java.time.LocalDate.now().toString());
		tFecha.setEditable(false);

	}

	private void construirPanel() {
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(construirPanelEncabezado(), BorderLayout.NORTH);
		add(construirPanelCentro(), BorderLayout.CENTER);
		add(construirPanelBotones(), BorderLayout.SOUTH);
	}

	private JPanel construirPanelEncabezado() {
		JPanel panel = new JPanel(new GridLayout(3, 4, 8, 6));
		panel.setBorder(BorderFactory.createTitledBorder("Datos de la Compra"));

		checkResponsableIva = new javax.swing.JCheckBox("Responsable IVA (19%)");
		panel.add(checkResponsableIva);
		checkResponsableIva.addActionListener(e -> recalcularTotales());

		tFecha = new JTextField(10);
		tCodigoProveedor = new JTextField(8);

		// --- LÓGICA DE AUTOCOMPLETADO PROVEEDOR ---
		tCodigoProveedor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String codTxt = tCodigoProveedor.getText().trim();
				if (!codTxt.isEmpty()) {
					try {
						int codigo = Integer.parseInt(codTxt);
						DatabaseProveedor localProv = new DatabaseProveedor();
						var proveedor = localProv.buscar(codigo); // Asegúrate que buscar devuelve un DTO con
																	// getNombre()
						if (proveedor != null) {
							tRazonSocial.setText(proveedor.getRazonSocial());
						} else {
							tRazonSocial.setText("No encontrado");
						}
					} catch (Exception ex) {
						// Error silencioso si no es numérico o falla BD
					}
				}
			}
		});

		tRazonSocial = new JTextField(15);
		tFecha = new JTextField(10);
		tCodigoProveedor = new JTextField(8);
		tRazonSocial = new JTextField(15); // <--- AQUÍ ESTÁ TU LÍNEA ACTUAL

		// Pega el bloque aquí:
		tRazonSocial.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(java.awt.event.FocusEvent e) {
				String nombre = tRazonSocial.getText().trim();
				if (!nombre.isEmpty() && tCodigoProveedor.getText().isEmpty()) {
					// Buscamos el código basado en el nombre que acaban de escribir
					int codigo = new co.edu.uptc.persistencia.database.DatabaseProveedor().buscarCodigoPorNombre(nombre);
					if (codigo != -1) {
						tCodigoProveedor.setText(String.valueOf(codigo));
					} else {
						JOptionPane.showMessageDialog(null, "Proveedor no encontrado en la base de datos");
					}
				}
			}
		});

		tImpuestos = new JTextField("0");
		tImpuestos = new JTextField("0");
		tSubtotal = new JTextField("0.00");
		tTotal = new JTextField("0.00");
		tSubtotal.setEditable(false);
		tTotal.setEditable(false);

		panel.add(new JLabel("FECHA:"));
		panel.add(tFecha);
		panel.add(new JLabel("COD. PROVEEDOR:"));
		panel.add(tCodigoProveedor);
		panel.add(new JLabel("RAZON SOCIAL:"));
		panel.add(tRazonSocial);
		panel.add(new JLabel("IMPUESTOS ($):"));
		panel.add(tImpuestos);
		panel.add(new JLabel("SUBTOTAL:"));
		panel.add(tSubtotal);
		panel.add(new JLabel("TOTAL:"));
		panel.add(tTotal);

		tImpuestos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				recalcularTotales();
			}
		});

		return panel;
	}

	private JPanel construirPanelCentro() {
		JPanel centro = new JPanel(new BorderLayout(8, 8));
		JPanel frmItem = new JPanel(new GridLayout(2, 4, 8, 6));
		frmItem.setBorder(BorderFactory.createTitledBorder("Agregar Producto a la Compra"));

		tCodProducto = new JTextField(8);
		tNomProducto = new JTextField(15);
		tCantidad = new JTextField(6);
		tCostoUnit = new JTextField(10);

		frmItem.add(new JLabel("COD. PRODUCTO:"));
		frmItem.add(tCodProducto);
		frmItem.add(new JLabel("CANTIDAD:"));
		frmItem.add(tCantidad);
		frmItem.add(new JLabel("NOMBRE:"));
		frmItem.add(tNomProducto);
		frmItem.add(new JLabel("COSTO UNIT.($):"));
		frmItem.add(tCostoUnit);

		bAgregarItem = new JButton("Agregar Producto");
		bEliminarItem = new JButton("Eliminar Producto");
		bAgregarItem.addActionListener(this::accionAgregarItem);
		bEliminarItem.addActionListener(this::accionEliminarItem);

		JPanel botonesItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
		botonesItem.add(bAgregarItem);
		botonesItem.add(bEliminarItem);

		JPanel topCentro = new JPanel(new BorderLayout());
		topCentro.add(frmItem, BorderLayout.CENTER);
		topCentro.add(botonesItem, BorderLayout.SOUTH);

		String[] colsItems = { "Cod. Producto", "Nombre", "Cantidad", "Costo Unit.", "Subtotal" };
		modeloItems = new DefaultTableModel(colsItems, 0) {
			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		tablaItems = new JTable(modeloItems);
		JScrollPane scrollItems = new JScrollPane(tablaItems);
		scrollItems.setBorder(BorderFactory.createTitledBorder("Productos en esta compra"));
		scrollItems.setPreferredSize(new Dimension(600, 120));

		String[] colsCompras = { "Factura N°", "Fecha", "Proveedor", "Subtotal", "Impuestos", "Total" };
		modeloCompras = new DefaultTableModel(colsCompras, 0) {
			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		tablaCompras = new JTable(modeloCompras);
		JScrollPane scrollCompras = new JScrollPane(tablaCompras);
		scrollCompras.setBorder(BorderFactory.createTitledBorder("Historial de Compras"));
		scrollCompras.setPreferredSize(new Dimension(600, 120));

		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollItems, scrollCompras);
		split.setDividerLocation(140);
		centro.add(topCentro, BorderLayout.NORTH);
		centro.add(split, BorderLayout.CENTER);
		return centro;
	}

	private JPanel construirPanelBotones() {
		bRegistrar = new JButton("Registrar Compra");
		bAnular = new JButton("Anular Compra");
		bBuscar = new JButton("Buscar Compra");
		bLimpiar = new JButton("Limpiar");
		bVolver = new JButton("Volver");

		JPanel botones = new JPanel(new GridLayout(1, 4, 8, 0));
		botones.add(bRegistrar);
		botones.add(bAnular);
		botones.add(bBuscar);
		botones.add(bLimpiar);
		JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelVolver.add(bVolver);
		JPanel sur = new JPanel(new BorderLayout());
		sur.add(botones, BorderLayout.NORTH);
		sur.add(panelVolver, BorderLayout.SOUTH);
		return sur;
	}

	public void setControlador(ActionListener controlador) {
		bRegistrar.addActionListener(controlador);
		bRegistrar.setActionCommand("REGISTRAR_COMPRA");
		bAnular.addActionListener(controlador);
		bAnular.setActionCommand("ANULAR_COMPRA");
		bBuscar.addActionListener(controlador);
		bBuscar.setActionCommand("BUSCAR_COMPRA");
		bLimpiar.addActionListener(controlador);
		bLimpiar.setActionCommand("LIMPIAR_COMPRA");
		bVolver.addActionListener(controlador);
		bVolver.setActionCommand("VOLVER_COMPRAS");
	}

	private void accionAgregarItem(ActionEvent e) {
		try {
			int cod = Integer.parseInt(tCodProducto.getText().trim());
			String nombre = tNomProducto.getText().trim();
			int cantidad = Integer.parseInt(tCantidad.getText().trim());
			double costo = Double.parseDouble(tCostoUnit.getText().replace(",", ".").trim());

			itemCompraDto item = new itemCompraDto(cod);
			item.setNombreProducto(nombre);
			item.setCantidad(cantidad);
			item.setCostoUnitario(costo);
			item.setSubtotal(cantidad * costo);
			itemsActuales.add(item);
			modeloItems.addRow(new Object[] { cod, nombre, cantidad, String.format("%.2f", costo),
					String.format("%.2f", item.getSubtotal()) });
			limpiarCamposItem();
			recalcularTotales();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Verifique los campos numericos del producto");
		}
	}

	private void accionEliminarItem(ActionEvent e) {
		int fila = tablaItems.getSelectedRow();
		if (fila >= 0) {
			itemsActuales.remove(fila);
			modeloItems.removeRow(fila);
			recalcularTotales();
		}
	}

	private void recalcularTotales() {
		double subtotal = itemsActuales.stream().mapToDouble(itemCompraDto::getSubtotal).sum();
		double tarifa = checkResponsableIva.isSelected() ? 0.19 : 0.0;
		double impuestos = subtotal * tarifa;
		tImpuestos.setText(String.format("%.2f", impuestos));
		tSubtotal.setText(String.format("%.2f", subtotal));
		tTotal.setText(String.format("%.2f", subtotal + impuestos));
	}

	public compraDto getDatosCompra() {
		if (tFecha.getText().trim().isEmpty() || tCodigoProveedor.getText().trim().isEmpty()
				|| itemsActuales.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Complete los datos obligatorios");
			return null;
		}
		try {
			compraDto compra = new compraDto();
			compra.setFecha(tFecha.getText().trim());
			compra.setCodigoProveedor(Integer.parseInt(tCodigoProveedor.getText().trim()));
			compra.setRazonSocialProveedor(tRazonSocial.getText().trim());
			compra.setImpuestos(Double.parseDouble(tImpuestos.getText().replace(",", ".").trim()));
			compra.setDetalles(new ArrayList<>(itemsActuales));
			return compra;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public int getNumeroFacturaSeleccionado() {
		int fila = tablaCompras.getSelectedRow();
		if (fila >= 0) {
			// Lee el valor de la columna 0 (Factura N°)
			Object valor = modeloCompras.getValueAt(fila, 0);
			return Integer.parseInt(valor.toString());
		}
		JOptionPane.showMessageDialog(this, "Seleccione una compra de la tabla");
		return -1;
	}

	public void poblarTabla(List<compraDto> compras) {
		modeloCompras.setRowCount(0);
		for (compraDto c : compras) {
			modeloCompras.addRow(new Object[] { c.getNumeroFacturaProv(), c.getFecha(), c.getRazonSocialProveedor(),
					String.format("%.2f", c.getSubtotal()), String.format("%.2f", c.getImpuestos()),
					String.format("%.2f", c.getTotalCompra()) });
		}
	}

	private void cargarProveedoresEnCombo() {
		comboProveedores = new javax.swing.JComboBox<>();
		// Aquí buscas todos los proveedores de la BD
		List<co.edu.uptc.negocio.dto.proveedorDto> lista = new co.edu.uptc.persistencia.database.DatabaseProveedor().listar();
		for (co.edu.uptc.negocio.dto.proveedorDto p : lista) {
			comboProveedores.addItem(p.getRazonSocial());
		}

		// Al seleccionar uno, que se llene el campo de código automáticamente
		comboProveedores.addActionListener(e -> {
			String nombreSeleccionado = (String) comboProveedores.getSelectedItem();
			// Aquí buscas el código basado en el nombre y lo pones en tCodigoProveedor
			int codigo = new co.edu.uptc.persistencia.database.DatabaseProveedor().buscarCodigoPorNombre(nombreSeleccionado);
			tCodigoProveedor.setText(String.valueOf(codigo));
			tRazonSocial.setText(nombreSeleccionado);
		});
	}

	// Métodos auxiliares y de limpieza mantienen igual...
	public void limpiarCampos() {
		tFecha.setText(java.time.LocalDate.now().toString());
		tCodigoProveedor.setText("");
		tRazonSocial.setText("");
		tImpuestos.setText("0");
		tSubtotal.setText("0.00");
		tTotal.setText("0.00");
		itemsActuales.clear();
		modeloItems.setRowCount(0);
	}

	private void limpiarCamposItem() {
		tCodProducto.setText("");
		tNomProducto.setText("");
		tCantidad.setText("");
		tCostoUnit.setText("");
	}
}