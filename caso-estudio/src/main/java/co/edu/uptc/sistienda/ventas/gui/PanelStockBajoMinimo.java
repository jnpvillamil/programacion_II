package co.edu.uptc.sistienda.ventas.gui;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import co.edu.uptc.sistienda.modelo.Producto;

public class PanelStockBajoMinimo extends JPanel {

	private JTable tabla;
	private DefaultTableModel modelo;

	public PanelStockBajoMinimo() {
		setLayout(new BorderLayout());

		JLabel titulo = new JLabel("Productos con stock bajo mínimo");
		titulo.setBorder(BorderFactory.createEmptyBorder(6, 8, 4, 8));
		add(titulo, BorderLayout.NORTH);

		// Tabla de solo lectura con las columnas relevantes del producto
		modelo = new DefaultTableModel(
				new String[] { "Código", "Nombre", "Categoría", "Impuesto", "Stock Actual", "Stock Mínimo" }, 0) {
			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false; // el usuario no puede editar nada directamente en la tabla
			}
		};

		tabla = new JTable(modelo);
		tabla.setRowHeight(22);
		tabla.getTableHeader().setReorderingAllowed(false); // evita que el usuario mueva las columnas
		add(new JScrollPane(tabla), BorderLayout.CENTER);
	}

	// Limpia la tabla y la vuelve a llenar con la lista de productos recibida
	public void poblarTabla(List<Producto> productos) {
		modelo.setRowCount(0); // borra las filas anteriores antes de cargar las nuevas
		for (Producto producto : productos) {
			modelo.addRow(new Object[] { producto.getCodigoInterno(), producto.getNombreProducto(),
					// Si el producto no tiene categoría o impuesto asignado, se muestra vacío
					producto.getCategoria() != null ? producto.getCategoria().getDescripcion() : "",
					producto.getTipoImpuesto() != null ? producto.getTipoImpuesto().getDescripcion() : "",
					producto.getStockActual(), producto.getStockMinimo() });
		}
	}
}