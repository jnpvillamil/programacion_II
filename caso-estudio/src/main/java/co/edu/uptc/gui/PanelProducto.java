package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.negocio.dto.productoDto;

public class PanelProducto extends JPanel {

	private static final long serialVersionUID = 1L;

	public JButton btnRegistrar, btnModificar, btnInactivar, btnBuscar, btnVolver, btnLimpiar;
	// 1. Agregamos el JTextField para el stock máximo
	public JTextField tNombre, tCategoria, tPrecioCompra, tPrecioVenta, tStockActual, tStockMinimo, tStockMaximo;

	protected DefaultTableModel modeloTabla;
	protected JTable tablaProductos;

	private int codigoSeleccionado = -1;

	public PanelProducto() {
		construirPanel();
	}

	private void construirPanel() {
		setLayout(new BorderLayout(8, 8));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// 2. Cambiamos el GridLayout de 6 a 7 filas para que quepa el nuevo campo
		JPanel Campos = new JPanel(new GridLayout(7, 2, 10, 8));
		Campos.setBorder(BorderFactory.createTitledBorder("Datos del producto"));

		btnRegistrar = new JButton(Eventos.pREGISTRAR);
		btnModificar = new JButton(Eventos.pMODIFICAR);
		btnInactivar = new JButton(Eventos.pINACTIVAR);
		btnBuscar = new JButton(Eventos.pBUSCAR);
		btnVolver = new JButton(Eventos.VOLVER);
		btnLimpiar = new JButton(Eventos.pLIMPIAR);

		Campos.add(new JLabel("NOMBRE:"));
		Campos.add(tNombre = new JTextField(10));
		Campos.add(new JLabel("CATEGORIA:"));
		Campos.add(tCategoria = new JTextField(10));
		Campos.add(new JLabel("PRECIO COMPRA:"));
		Campos.add(tPrecioCompra = new JTextField(10));
		Campos.add(new JLabel("PRECIO VENTA:"));
		Campos.add(tPrecioVenta = new JTextField(10));
		Campos.add(new JLabel("STOCK ACTUAL:"));
		Campos.add(tStockActual = new JTextField(10));
		Campos.add(new JLabel("STOCK MINIMO:"));
		Campos.add(tStockMinimo = new JTextField(10));
		// 3. Añadimos el nuevo Label y Campo de texto a la pantalla
		Campos.add(new JLabel("STOCK MAXIMO:"));
		Campos.add(tStockMaximo = new JTextField(10));

		// 4. Añadimos "Stock Max." a los títulos de las columnas de la tabla
		modeloTabla = new DefaultTableModel(new String[] { "Codigo", "Nombre", "Categoria", "P.Compra", "P.Venta",
				"Stock", "Stock Min.", "Stock Max." }, 0) {
			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		tablaProductos = new JTable(modeloTabla);
		tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaProductos.setRowHeight(20);
		tablaProductos.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting())
				cargarSeleccionEnCampos();
		});

		JScrollPane scroll = new JScrollPane(tablaProductos);
		scroll.setBorder(BorderFactory.createTitledBorder("Lista de productos"));
		scroll.setPreferredSize(new Dimension(600, 150));

		JPanel botones = new JPanel(new GridLayout(1, 5, 8, 8));
		botones.setBorder(BorderFactory.createEmptyBorder(8, 0, 4, 0));
		botones.add(btnRegistrar);
		botones.add(btnModificar);
		botones.add(btnInactivar);
		botones.add(btnBuscar);
		botones.add(btnLimpiar);

		JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelVolver.add(btnVolver);

		JPanel sur = new JPanel(new BorderLayout());
		sur.add(botones, BorderLayout.NORTH);
		sur.add(panelVolver, BorderLayout.SOUTH);

		add(Campos, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(sur, BorderLayout.SOUTH);
	}

	public void setControlador(ActionListener controlador) {
		btnRegistrar.addActionListener(controlador);
		btnRegistrar.setActionCommand("REGISTRAR_PRODUCTO");
		btnModificar.addActionListener(controlador);
		btnModificar.setActionCommand("MODIFICAR_PRODUCTO");
		btnInactivar.addActionListener(controlador);
		btnInactivar.setActionCommand("INACTIVAR_PRODUCTO");
		btnBuscar.addActionListener(controlador);
		btnBuscar.setActionCommand("BUSCAR_PRODUCTO");
		btnLimpiar.addActionListener(controlador);
		btnLimpiar.setActionCommand("LIMPIAR_PRODUCTO");
		btnVolver.addActionListener(controlador);
		btnVolver.setActionCommand("VOLVER_PRODUCTOS");
	}

	private void cargarSeleccionEnCampos() {
		int fila = tablaProductos.getSelectedRow();
		if (fila < 0)
			return;
		codigoSeleccionado = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
		tNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
		tCategoria.setText(modeloTabla.getValueAt(fila, 2).toString());
		tPrecioCompra.setText(modeloTabla.getValueAt(fila, 3).toString());
		tPrecioVenta.setText(modeloTabla.getValueAt(fila, 4).toString());
		tStockActual.setText(modeloTabla.getValueAt(fila, 5).toString());
		tStockMinimo.setText(modeloTabla.getValueAt(fila, 6).toString());
		// 5. Leemos el stock máximo de la columna 7 y lo ponemos en el campo de texto
		tStockMaximo.setText(modeloTabla.getValueAt(fila, 7).toString());
	}

	public void poblarTabla(List<productoDto> lista) {
		modeloTabla.setRowCount(0);
		for (productoDto p : lista) {
			// 6. Añadimos el getStockMaximo al final de la fila de la tabla
			modeloTabla
					.addRow(new Object[] { p.getCodigoProducto(), p.getNombre(), p.getCategoria(), p.getPrecioCompra(),
							p.getPrecioVenta(), p.getStockActual(), p.getStockMinimo(), p.getStockMaximo() });
		}
	}

	public void limpiarCampos() {
		codigoSeleccionado = -1;
		tNombre.setText("");
		tCategoria.setText("");
		tPrecioCompra.setText("");
		tPrecioVenta.setText("");
		tStockActual.setText("");
		tStockMinimo.setText("");
		// 7. Limpiamos también el nuevo campo
		tStockMaximo.setText("");
		tablaProductos.clearSelection();
	}

	public productoDto getDatosProducto() {
		if (tNombre.getText().isBlank()) {
			JOptionPane.showMessageDialog(this, "El nombre es requerido");
			return null;
		}
		productoDto producto = new productoDto();
		producto.setNombre(tNombre.getText());
		producto.setCategoria(tCategoria.getText());
		try {
			producto.setPrecioCompra(Double.parseDouble(tPrecioCompra.getText()));
			producto.setPrecioVenta(Double.parseDouble(tPrecioVenta.getText()));
			producto.setStockActual(Integer.parseInt(tStockActual.getText()));
			producto.setStockMinimo(Integer.parseInt(tStockMinimo.getText()));
			// 8. Capturamos el texto, lo convertimos a número y lo guardamos en el DTO
			producto.setStockMaximo(Integer.parseInt(tStockMaximo.getText()));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Datos numéricos inválidos");
			return null;
		}
		return producto;
	}

	public productoDto getDatosProductoModificar() {
		if (codigoSeleccionado == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla para modificar");
			return null;
		}
		productoDto producto = getDatosProducto();
		if (producto != null)
			producto.setCodigoProducto(codigoSeleccionado);
		return producto;
	}

	public int getCodigoProducto() {
		return codigoSeleccionado;
	}

	public JTable getTabla() {
		return tablaProductos;
	}

	public int getIdSeleccionado() {
		int row = tablaProductos.getSelectedRow();
		if (row != -1) {
			return Integer.parseInt(tablaProductos.getValueAt(row, 0).toString());
		}
		return -1;
	}

}