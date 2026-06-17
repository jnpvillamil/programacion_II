package co.edu.uptc.sistienda.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import co.edu.uptc.sistienda.modelo.Producto;

public class PanelDashboard extends JPanel {

	private JLabel etiquetaVentasDia;
	private JLabel etiquetaComprasDia;
	private JLabel etiquetaStockBajo;
	private JTextArea areaProductosBajoStock;

	public PanelDashboard() {
		setLayout(new BorderLayout());

		// Título
		JLabel etiquetaTitulo = new JLabel("Dashboard - Resumen del día");
		etiquetaTitulo.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
		add(etiquetaTitulo, BorderLayout.NORTH);

		// Panel de tarjetas de resumen
		JPanel panelTarjetas = new JPanel(new GridLayout(1, 3, 8, 8));
		panelTarjetas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		etiquetaVentasDia  = new JLabel("Ventas del día: $0", JLabel.CENTER);
		etiquetaComprasDia = new JLabel("Compras del día: $0", JLabel.CENTER);
		etiquetaStockBajo  = new JLabel("Stock bajo mínimo: 0", JLabel.CENTER);

		JPanel tarjetaVentas  = crearTarjeta(etiquetaVentasDia,  "VENTAS DEL DÍA");
		JPanel tarjetaCompras = crearTarjeta(etiquetaComprasDia, "COMPRAS DEL DÍA");
		JPanel tarjetaStock   = crearTarjeta(etiquetaStockBajo,  "STOCK BAJO MÍNIMO");

		panelTarjetas.add(tarjetaVentas);
		panelTarjetas.add(tarjetaCompras);
		panelTarjetas.add(tarjetaStock);
		add(panelTarjetas, BorderLayout.NORTH);

		// Área de productos con stock bajo mínimo
		JPanel panelBajoStock = new JPanel(new BorderLayout());
		panelBajoStock.setBorder(BorderFactory.createTitledBorder("Productos con Stock bajo el Mínimo"));
		areaProductosBajoStock = new JTextArea();
		areaProductosBajoStock.setEditable(false);
		areaProductosBajoStock.setText("  (sin alertas)");
		panelBajoStock.add(new JScrollPane(areaProductosBajoStock), BorderLayout.CENTER);
		add(panelBajoStock, BorderLayout.CENTER);
	}

	private JPanel crearTarjeta(JLabel etiqueta, String titulo) {
		JPanel tarjeta = new JPanel(new BorderLayout());
		tarjeta.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(java.awt.Color.GRAY),
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		JLabel etiquetaTitulo = new JLabel(titulo, JLabel.CENTER);
		tarjeta.add(etiquetaTitulo, BorderLayout.NORTH);
		tarjeta.add(etiqueta, BorderLayout.CENTER);
		return tarjeta;
	}

	//Actualiza los datos del dashboard con información real
	public void actualizarResumen(List<Producto> productosConStockBajo) {
		int cantidadBajo = productosConStockBajo.size();
		etiquetaStockBajo.setText("Stock bajo mínimo: " + cantidadBajo);

		if (cantidadBajo == 0) {
			areaProductosBajoStock.setText("  (sin alertas de stock)");
		} else {
			StringBuilder sb = new StringBuilder();
			for (Producto producto : productosConStockBajo) {
				sb.append("  ")
				  .append(producto.getNombreProducto())
				  .append("  —  Stock actual: ").append(producto.getStockActual())
				  .append("  /  Mínimo: ").append(producto.getStockMinimo())
				  .append("\n");
			}
			areaProductosBajoStock.setText(sb.toString());
		}
	}
}
