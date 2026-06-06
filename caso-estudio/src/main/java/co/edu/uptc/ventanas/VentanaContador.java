package co.edu.uptc.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import co.edu.uptc.config.Config;
import co.edu.uptc.gui.modelo.Contador;

public class VentanaContador extends JFrame {
	
	private static final long serialVersionUID = 1835237524801377535L;
	
	private JTable tablaReporte;
	private DefaultTableModel modeloTablaVentas;
	private JLabel etiquetaGranTotal;
	
	private JTable tablaContadores;
	private DefaultTableModel modeloTablaContadores;
	private JTextField txtId, txtNombre, txtTarjeta, txtTelefono;
	private JButton btnGuardar, btnRefrescar;

	private Config config;

	public VentanaContador(Config config) {
		this.config = config;

		setTitle("Módulo Contable y de Gestión de Contadores - UPTC");
		setSize(1100, 520); 
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		setLayout(new BorderLayout(15, 15));
		
		iniciarComponenteTablaVentas();
		iniciarPanelBalance();
		iniciarPanelGestionContadores();
		refrescarTodoElModulo();
	}

	private void iniciarComponenteTablaVentas() {
		String[] columnas = {"N° Factura", "Código Cliente", "Código Producto", "Cantidad Vendida", "Total Facturado ($)"};
 
		modeloTablaVentas = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};

		tablaReporte = new JTable(modeloTablaVentas);
		tablaReporte.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scroll = new JScrollPane(tablaReporte);
		scroll.setBorder(BorderFactory.createTitledBorder("Registro Histórico de Facturas Emitidas)"));
		add(scroll, BorderLayout.CENTER);
	}

	private void iniciarPanelBalance() {
		JPanel panelBalance = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 12));
		panelBalance.setBorder(BorderFactory.createEtchedBorder());
  
		etiquetaGranTotal = new JLabel("TOTAL RECAUDO: $ 0.00 COP");
		etiquetaGranTotal.setFont(new Font("Arial", Font.BOLD, 16));
		etiquetaGranTotal.setForeground(new Color(0, 102, 51)); 
		
		panelBalance.add(etiquetaGranTotal);
		add(panelBalance, BorderLayout.SOUTH);
	}

	private void iniciarPanelGestionContadores() {
		JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
		panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Administración de Contadores)"));
		panelIzquierdo.setPreferredSize(new Dimension(420, 500));

		JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
		panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panelFormulario.add(new JLabel("Cédula / ID:"));
		txtId = new JTextField();
		panelFormulario.add(txtId);

		panelFormulario.add(new JLabel("Nombre Completo:"));
		txtNombre = new JTextField();
		panelFormulario.add(txtNombre);

		panelFormulario.add(new JLabel("Tarjeta Profesional:"));
		txtTarjeta = new JTextField();
		panelFormulario.add(txtTarjeta);

		panelFormulario.add(new JLabel("Teléfono:"));
		txtTelefono = new JTextField();
		panelFormulario.add(txtTelefono);

		btnGuardar = new JButton("Registrar en BD");
		btnRefrescar = new JButton("Actualizar Módulo");
		panelFormulario.add(btnGuardar);
		panelFormulario.add(btnRefrescar);

		panelIzquierdo.add(panelFormulario, BorderLayout.NORTH);

		String[] columnasContador = {"Cédula", "Nombre", "T. Profesional"};
		modeloTablaContadores = new DefaultTableModel(columnasContador, 0) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		tablaContadores = new JTable(modeloTablaContadores);
		JScrollPane scrollContadores = new JScrollPane(tablaContadores);
		scrollContadores.setBorder(BorderFactory.createTitledBorder("Contadores Registrados"));
		
		panelIzquierdo.add(scrollContadores, BorderLayout.CENTER);
		add(panelIzquierdo, BorderLayout.WEST);

		btnGuardar.addActionListener(e -> ejecutarRegistroContador());
		btnRefrescar.addActionListener(e -> refrescarTodoElModulo());
	}
	
	
	public void cargarReporteVentas() {
		modeloTablaVentas.setRowCount(0);
		co.edu.uptc.conexion.Conexion conObj = new co.edu.uptc.conexion.Conexion();
		
		
		String sqlVentas = "SELECT numero_factura, codigo_cliente, codigo_producto, cantidad, total_venta FROM factura_venta";
		String sqlSuma = "SELECT SUM(total_venta) AS gran_total FROM factura_venta";
		
		double acumulado = 0.0;

		try (Connection con = conObj.getConnection()) {
			if (con != null) {

				try (Statement st = con.createStatement(); 
					 ResultSet rs = st.executeQuery(sqlVentas)) {
					while (rs.next()) {
						Object[] fila = {
							rs.getString(1), 
							rs.getString(2), 
							rs.getString(3),
							rs.getInt(4),    
							rs.getDouble(5)  
						};
						modeloTablaVentas.addRow(fila);
					}
				}
	
				try (Statement st2 = con.createStatement(); 
					 ResultSet rs2 = st2.executeQuery(sqlSuma)) {
					if (rs2.next()) {
						acumulado = rs2.getDouble("gran_total");
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al cargar reporte de facturas: " + e.getMessage());
		} finally {
			conObj.desconectar();
		}
		
		etiquetaGranTotal.setText("TOTAL RECAUDO: $ " + acumulado + " COP");
	}

	public void cargarListaContadores() {
		modeloTablaContadores.setRowCount(0);
		
		if (this.config == null || config.getGestionContador() == null) {
			System.out.println("Configuración o Gestor no inicializados.");
			return;
		}

		try {
			List<Contador> contadores = config.getGestionContador().listarContadores();
			if (contadores != null) {
				for (Contador c : contadores) {
					Object[] fila = {
						c.getId(),
						c.getNombre(),
						c.getTarjetaProfesional()
					};
					modeloTablaContadores.addRow(fila);
				}
			}
		} catch (Exception e) {
			System.out.println("Error al pintar lista de contadores: " + e.getMessage());
		}
	}

	private void ejecutarRegistroContador() {
		try {
			String idStr = txtId.getText().trim();
			String nombre = txtNombre.getText().trim();
			String tarjeta = txtTarjeta.getText().trim();
			String telefono = txtTelefono.getText().trim();

			if (idStr.isEmpty() || nombre.isEmpty() || tarjeta.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Por favor complete los campos obligatorios (Cédula, Nombre y Tarjeta).", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
				return;
			}

			long id = Long.parseLong(idStr);

			Contador nuevoContador = new Contador(id, nombre, tarjeta, telefono);

			config.getGestionContador().registrarContador(nuevoContador);

			JOptionPane.showMessageDialog(this, "¡Contador registrado exitosamente!");

			txtId.setText("");
			txtNombre.setText("");
			txtTarjeta.setText("");
			txtTelefono.setText("");
			
			refrescarTodoElModulo();

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "La cédula / ID debe ser un dato numérico válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error en la operación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	
	public void refrescarTodoElModulo() {
		cargarReporteVentas();
		cargarListaContadores();
	}
}