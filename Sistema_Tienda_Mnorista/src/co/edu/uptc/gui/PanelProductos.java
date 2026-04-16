package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelProductos extends JPanel implements ActionListener {

	public JTextField textoDelCodigo;
	public JTextField textoDelNombre;
	public JTextField textoCategoria;
	public JTextField textoPrecioCompra;
	public JTextField textoPrecioVenta;
	public JTextField textoStockActual;
	public JTextField textoStockMinimo;
	public JTextField textoMostrarInfo;
	public ArrayList<Producto> listaProductos;
	public JTextArea areaListaProductos;
	public JButton botonAñadir,
	botonModificar,
	botonBorrar,
	botonBuscar;

	public PanelProductos() {
		listaProductos = new ArrayList<>();
		inicio();
		cargarDatos();
	}

	private void cargarDatos() {

		String codigo1;
		String nombre1;
		String categoria1;
		int precioCompra1;
		int precioVenta1;
		int stock1;
		codigo1 = "001";
		nombre1 = "Arroz Diana";
		categoria1 = "Granos";
		precioCompra1 = 1000;
		precioVenta1 = 2000;
		stock1 = 20;

		String codigo2;
		String nombre2;
		String categoria2;
		int precioCompra2;
		int precioVenta2;
		int stock2;
		codigo2 = "002";
		nombre2 = "Leche";
		categoria2 = "Lacteos";
		precioCompra2 = 1500;
		precioVenta2 = 2500;
		stock2 = 15;
		
		//Agregar a la lista
		Producto p1 = new Producto(codigo1, nombre1, categoria1, precioCompra1);
		p1.precioVenta = precioVenta1;
		p1.stockActual = stock1;
		p1.stockMinimo = 10;
		
		Producto p2 = new Producto(codigo2, nombre2, categoria2, precioCompra2);
		p2.precioVenta = precioVenta2;
		p2.stockActual = stock2;
		p2.stockMinimo = 8;
		
		listaProductos.add(p1);
		listaProductos.add(p2);

		areaListaProductos = new JTextArea(5, 40);
		areaListaProductos.setEditable(false);

		String texto = "";
		texto = texto + nombre1 + "   " + categoria1 + "   " + precioCompra1 + "UN" + "   " + precioVenta1 + "UN" + "   " + stock1 + "   " + "ENTRADA" + "\n";
		texto = texto + nombre2 + "   " + categoria2 + "   " + precioCompra2 + "UN" + "   " + precioVenta2 + "UN" + "   " + stock2 + "   " + "ENTRADA" + "\n";

		areaListaProductos.setText(texto);

		String infoResumen = "";
		infoResumen = infoResumen + "Productos: " + codigo1 + " " + nombre1 + " | ";
		infoResumen = infoResumen + codigo2 + " " + nombre2;
		textoMostrarInfo.setText(infoResumen);
	}

	public void inicio() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Panel superior: izquierda (campos) + derecha (botones accion)
		JPanel panelSuperior = new JPanel();
		// --- Columna izquierda: campos ---
		JPanel panelCampos = new JPanel(new GridLayout(0, 1));

		textoDelNombre = new JTextField(15);
		textoCategoria = new JTextField(15);
		textoPrecioCompra = new JTextField(15);
		textoPrecioVenta = new JTextField(15);
		textoStockActual = new JTextField(15);
		textoStockMinimo = new JTextField(15);
		textoDelCodigo = new JTextField(15);
		textoMostrarInfo = new JTextField(30);

		panelCampos.add(new JLabel("Nombre del producto"));
		panelCampos.add(textoDelNombre);
		panelCampos.add(new JLabel("Categoria"));
		panelCampos.add(textoCategoria);
		panelCampos.add(new JLabel("Precio de compra"));
		panelCampos.add(textoPrecioCompra);
		panelCampos.add(new JLabel("Precio de venta"));
		panelCampos.add(textoPrecioVenta);
		panelCampos.add(new JLabel("Stock actual"));
		panelCampos.add(textoStockActual);
		panelCampos.add(new JLabel("Stock minimo"));
		panelCampos.add(textoStockMinimo);

		JButton btnBorrar = new JButton("BORRAR");
		JButton btnRegistrar = new JButton("REGISTRAR");

		btnBorrar.setActionCommand("BorrarProducto");
		btnRegistrar.setActionCommand("RegistrarProducto");
		
		btnBorrar.addActionListener(this);
		btnRegistrar.addActionListener(this);

		JPanel panelBotonesBR = new JPanel();
		panelBotonesBR.add(btnBorrar);
		panelBotonesBR.add(btnRegistrar);
		panelCampos.add(panelBotonesBR);


		JPanel panelBotonesAccion = new JPanel(new GridLayout(0, 1));
		JButton btnActualizarProducto = new JButton("Actualizar producto");
		JButton btnActualizarPrecios = new JButton("Actualizar precios de venta y costo.");
		JButton btnControlarStock = new JButton("Controlar stock minimo y maximo.");
		
		btnActualizarProducto.setActionCommand("ActualizarProducto");
		btnActualizarPrecios.setActionCommand("ActualizarPrecios");
		btnControlarStock.setActionCommand("ControlarStock");
		
		btnActualizarProducto.addActionListener(this);
		btnActualizarPrecios.addActionListener(this);
		btnControlarStock.addActionListener(this);

		panelBotonesAccion.add(btnActualizarProducto);
		panelBotonesAccion.add(btnActualizarPrecios);
		panelBotonesAccion.add(btnControlarStock);
		panelSuperior.add(panelCampos);
		panelSuperior.add(panelBotonesAccion);

		JPanel panelCabeceraTabla = new JPanel(new GridLayout(1, 6));
		panelCabeceraTabla.add(new JLabel("Nombre del producto"));
		panelCabeceraTabla.add(new JLabel("Categoria"));
		panelCabeceraTabla.add(new JLabel("Precio de compra"));
		panelCabeceraTabla.add(new JLabel("Precio de venta"));
		panelCabeceraTabla.add(new JLabel("Stock"));
		panelCabeceraTabla.add(new JLabel("ENTRADA/SALIDA"));
		add(panelSuperior);
		add(panelCabeceraTabla);
		add(new JScrollPane(areaListaProductos = new JTextArea(4, 50)));
		add(textoMostrarInfo);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		
		if(comando.equals("RegistrarProducto")) {
			JPanel panelRegistro = new JPanel(new GridLayout(0, 2, 10, 10));
			panelRegistro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			
			JTextField txtCodigo = new JTextField(textoDelCodigo.getText(), 15);
			JTextField txtNombre = new JTextField(textoDelNombre.getText(), 15);
			JTextField txtCategoria = new JTextField(textoCategoria.getText(), 15);
			JTextField txtPCompra = new JTextField(textoPrecioCompra.getText(), 15);
			JTextField txtPVenta = new JTextField(textoPrecioVenta.getText(), 15);
			JTextField txtStock = new JTextField(textoStockActual.getText(), 15);
			JTextField txtStockMin = new JTextField(textoStockMinimo.getText(), 15);
			
			panelRegistro.add(new JLabel("Codigo:"));
			panelRegistro.add(txtCodigo);
			panelRegistro.add(new JLabel("Nombre:"));
			panelRegistro.add(txtNombre);
			panelRegistro.add(new JLabel("Categoria:"));
			panelRegistro.add(txtCategoria);
			panelRegistro.add(new JLabel("Precio Compra:"));
			panelRegistro.add(txtPCompra);
			panelRegistro.add(new JLabel("Precio Venta:"));
			panelRegistro.add(txtPVenta);
			panelRegistro.add(new JLabel("Stock Actual:"));
			panelRegistro.add(txtStock);
			panelRegistro.add(new JLabel("Stock Minimo:"));
			panelRegistro.add(txtStockMin);
			int resultado = JOptionPane.showConfirmDialog(this, panelRegistro, 
					"Registrar Nuevo Producto", JOptionPane.OK_CANCEL_OPTION);
			if(resultado == JOptionPane.OK_OPTION) {
				try {
					Producto nuevo = new Producto(
						txtCodigo.getText(),
						txtNombre.getText(),
						txtCategoria.getText(),
						Integer.parseInt(txtPCompra.getText())
					);
					nuevo.precioVenta = Integer.parseInt(txtPVenta.getText());
					nuevo.stockActual = Integer.parseInt(txtStock.getText());
					nuevo.stockMinimo = Integer.parseInt(txtStockMin.getText());
					
					listaProductos.add(nuevo);
					JOptionPane.showMessageDialog(this, "Producto registrado exitosamente!");
					actualizarVista();
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(this, "Error en los datos");
				}
			}
		}
		else if(comando.equals("BorrarProducto")) {
			String codigo = textoDelCodigo.getText();
			
			if(codigo.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Ingrese un codigo para borrar");
				return;
			}
			
			Producto aBorrar = null;
			for(Producto p : listaProductos) {
				if(p.codigo.equals(codigo)) {
					aBorrar = p;
					break;
				}
			}
			
			if(aBorrar != null) {
				JPanel panelConfirmacion = new JPanel();
				panelConfirmacion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				panelConfirmacion.add(new JLabel("¿Esta seguro de borrar el producto: " + aBorrar.nombre + "?"));
				
				int resultado = JOptionPane.showConfirmDialog(this, panelConfirmacion, 
						"Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);
				
				if(resultado == JOptionPane.YES_OPTION) {
					listaProductos.remove(aBorrar);
					JOptionPane.showMessageDialog(this, "Producto borrado correctamente");
					actualizarVista();
					limpiarCampos();
				}
			} else {
				JOptionPane.showMessageDialog(this, "Producto no encontrado");
			}
		}
		else if(comando.equals("ActualizarProducto")) {
			String codigo = textoDelCodigo.getText();
			
			if(codigo.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Ingrese un codigo para actualizar");
				return;
			}
			
			Producto aActualizar = null;
			for(Producto p : listaProductos) {
				if(p.codigo.equals(codigo)) {
					aActualizar = p;
					break;
				}
			}
			
			if(aActualizar != null) {
				JPanel panelActualizar = new JPanel(new GridLayout(0, 2, 10, 10));
				panelActualizar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
				
				JTextField txtNombre = new JTextField(aActualizar.nombre, 15);
				JTextField txtCategoria = new JTextField(aActualizar.categoria, 15);
				JTextField txtPCompra = new JTextField(String.valueOf(aActualizar.precioCompra), 15);
				JTextField txtPVenta = new JTextField(String.valueOf(aActualizar.precioVenta), 15);
				JTextField txtStock = new JTextField(String.valueOf(aActualizar.stockActual), 15);
				JTextField txtStockMin = new JTextField(String.valueOf(aActualizar.stockMinimo), 15);
				
				panelActualizar.add(new JLabel("Nombre:"));
				panelActualizar.add(txtNombre);
				panelActualizar.add(new JLabel("Categoria:"));
				panelActualizar.add(txtCategoria);
				panelActualizar.add(new JLabel("Precio Compra:"));
				panelActualizar.add(txtPCompra);
				panelActualizar.add(new JLabel("Precio Venta:"));
				panelActualizar.add(txtPVenta);
				panelActualizar.add(new JLabel("Stock Actual:"));
				panelActualizar.add(txtStock);
				panelActualizar.add(new JLabel("Stock Minimo:"));
				panelActualizar.add(txtStockMin);
				
				int resultado = JOptionPane.showConfirmDialog(this, panelActualizar, 
						"Actualizar Producto: " + aActualizar.nombre, JOptionPane.OK_CANCEL_OPTION);
				if(resultado == JOptionPane.OK_OPTION) {
					try {
						aActualizar.nombre = txtNombre.getText();
						aActualizar.categoria = txtCategoria.getText();
						aActualizar.precioCompra = Integer.parseInt(txtPCompra.getText());
						aActualizar.precioVenta = Integer.parseInt(txtPVenta.getText());
						aActualizar.stockActual = Integer.parseInt(txtStock.getText());
						aActualizar.stockMinimo = Integer.parseInt(txtStockMin.getText());
						
						JOptionPane.showMessageDialog(this, "Producto actualizado exitosamente!");
						actualizarVista();
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(this, "Error en los datos");
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Producto no encontrado");
			}
		}
		else if(comando.equals("ActualizarPrecios")) {
			JPanel panelPrecios = new JPanel(new GridLayout(0, 2, 10, 10));
			panelPrecios.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			JTextField txtPorcentaje = new JTextField("10", 10);
			JComboBox<String> comboTipo = new JComboBox<>();
			comboTipo.addItem("Incrementar");
			comboTipo.addItem("Disminuir");
			
			panelPrecios.add(new JLabel("Porcentaje (%):"));
			panelPrecios.add(txtPorcentaje);
			panelPrecios.add(new JLabel("Tipo de ajuste:"));
			panelPrecios.add(comboTipo);
			panelPrecios.add(new JLabel(""));
			panelPrecios.add(new JLabel("Aplica a TODOS los productos"));
			int resultado = JOptionPane.showConfirmDialog(this, panelPrecios, 
					"Actualizar Precios Masivamente", JOptionPane.OK_CANCEL_OPTION);
			
			if(resultado == JOptionPane.OK_OPTION) {
				try {
					double porcentaje = Double.parseDouble(txtPorcentaje.getText());
					boolean incrementar = comboTipo.getSelectedItem().equals("Incrementar");
					
					for(Producto p : listaProductos) {
						if(incrementar) {
							p.precioVenta = (int)(p.precioVenta * (1 + porcentaje/100));
						} else {
							p.precioVenta = (int)(p.precioVenta * (1 - porcentaje/100));
						}
					}
					JOptionPane.showMessageDialog(this, 
							"Precios actualizados con " + 
							(incrementar ? "incremento" : "descuento") + 
							" del " + porcentaje + "%");
					actualizarVista();
					
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(this, "Error en el porcentaje");
				}
			}
		}
		else if(comando.equals("ControlarStock")) {
			JPanel panelStock = new JPanel();
			panelStock.setLayout(new BoxLayout(panelStock, BoxLayout.Y_AXIS));
			panelStock.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			
			JTextArea areaReporte = new JTextArea(15, 40);
			areaReporte.setEditable(false);
			
			String reporte = "REPORTE DE CONTROL DE STOCK\n\n";
			int criticos = 0;
			int altos = 0;
			for(Producto p : listaProductos) {
				if(p.stockActual <= p.stockMinimo) {
					criticos++;
					reporte = reporte + "⚠ CRITICO: " + p.nombre + "\n";
					reporte = reporte + "   Stock: " + p.stockActual + " (Min: " + p.stockMinimo + ")\n";
					reporte = reporte + "   Pedir: " + (p.stockMinimo * 2 - p.stockActual) + " unidades\n\n";
				} else if(p.stockActual >= p.stockMinimo * 3) {
					altos++;
					reporte = reporte + "↑ EXCESO: " + p.nombre + "\n";
					reporte = reporte + "   Stock: " + p.stockActual + " unidades\n\n";
				} else {
					reporte = reporte + "✓ NORMAL: " + p.nombre + " (" + p.stockActual + " unidades)\n";
				}
			}
			reporte = reporte + "\n=== RESUMEN ===\n";
			reporte = reporte + "Productos criticos: " + criticos + "\n";
			reporte = reporte + "Productos con exceso: " + altos + "\n";
			reporte = reporte + "Productos normales: " + (listaProductos.size() - criticos - altos) + "\n";
			reporte = reporte + "Total productos: " + listaProductos.size();
			areaReporte.setText(reporte);
			JScrollPane scroll = new JScrollPane(areaReporte);
			panelStock.add(scroll);
			
			JOptionPane.showMessageDialog(this, panelStock, 
					"Control de Stock", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void actualizarVista() {
		String texto = "";
		for(Producto p : listaProductos) {
			texto = texto + p.nombre + "   " + p.categoria + "   " + 
					p.precioCompra + "UN   " + p.precioVenta + "UN   " + 
					p.stockActual + "   ENTRADA\n";
		}
		areaListaProductos.setText(texto);
		String resumen = "Total productos: " + listaProductos.size() + " | ";
		int totalStock = 0;
		for(Producto p : listaProductos) {
			totalStock = totalStock + p.stockActual;
		}
		resumen = resumen + "Stock total: " + totalStock + " unidades";
		textoMostrarInfo.setText(resumen);
	}
	private void limpiarCampos() {
		textoDelCodigo.setText("");
		textoDelNombre.setText("");
		textoCategoria.setText("");
		textoPrecioCompra.setText("");
		textoPrecioVenta.setText("");
		textoStockActual.setText("");
		textoStockMinimo.setText("");
	}
	class Producto {
		String codigo;
		String nombre;
		String categoria;
		int precioCompra;
		int precioVenta;
		int stockActual;
		int stockMinimo;
		
		public Producto(String codigo, String nombre, String categoria, int precioCompra) {
			this.codigo = codigo;
			this.nombre = nombre;
			this.categoria = categoria;
			this.precioCompra = precioCompra;
		}
	}
}
