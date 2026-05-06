package co.uptc.edu.co.gui.dialog;
import javax.swing.JDialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class DialogDetalleVenta extends JDialog{
	
	 private JTextField campoNumeroFactura;
	 private JTextField campoFecha;
	 private JTextField campoHora;
	 private JTextField campoCliente;
	 private JTextField campoFormaPago;
	 private JTextField campoImpuestos;
	 private JTextField campoTotal;
	 private JTextField campoEstado;

	 private JTable tablaDetalle;
	 private DefaultTableModel modeloTabla;
	 private JButton botonCerrar;
	 

	 public DialogDetalleVenta(Frame propietario) {
	        super(propietario, "Detalle de Venta", true);
	        inicializarComponentes();
	        configurarDialogo();
	        agregarComponentes();
	        inicializarEventos();
	 }
	 

	 private void inicializarComponentes() {
		 
	     campoNumeroFactura = new JTextField(15);
	     campoFecha = new JTextField(15);
	     campoHora = new JTextField(15);
	     campoCliente = new JTextField(15);
	     campoFormaPago = new JTextField(15);
	     campoImpuestos = new JTextField(15);
	     campoTotal = new JTextField(15);
	     campoEstado = new JTextField(15);

	     campoNumeroFactura.setEditable(false);
	     campoFecha.setEditable(false);
	     campoHora.setEditable(false);
	     campoCliente.setEditable(false);
	     campoFormaPago.setEditable(false);
	     campoImpuestos.setEditable(false);
	     campoTotal.setEditable(false);
	     campoEstado.setEditable(false);

	     modeloTabla = new DefaultTableModel(
	    		 
	            new Object[] { "Producto", "Cantidad", "Precio Unitario", "Impuestos", "Subtotal" }, 0) {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };

	        tablaDetalle = new JTable(modeloTabla);

	        botonCerrar = new JButton("Cerrar");
	    }

	    private void configurarDialogo() {
	        setSize(700, 480);
	        setLocationRelativeTo(getOwner());
	        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	        setResizable(false);
	    }

	    private void agregarComponentes() {
	        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
	        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

	        JPanel panelDatos = new JPanel(new GridBagLayout());
	        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de la Venta"));

	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(6, 6, 6, 6);
	        gbc.anchor = GridBagConstraints.WEST;
	        gbc.fill = GridBagConstraints.HORIZONTAL;

	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        panelDatos.add(new JLabel("N° Factura:"), gbc);
	        gbc.gridx = 1;
	        panelDatos.add(campoNumeroFactura, gbc);

	        gbc.gridx = 2;
	        panelDatos.add(new JLabel("Fecha:"), gbc);
	        gbc.gridx = 3;
	        panelDatos.add(campoFecha, gbc);

	        gbc.gridx = 0;
	        gbc.gridy = 1;
	        panelDatos.add(new JLabel("Hora:"), gbc);
	        gbc.gridx = 1;
	        panelDatos.add(campoHora, gbc);

	        gbc.gridx = 2;
	        panelDatos.add(new JLabel("Cliente:"), gbc);
	        gbc.gridx = 3;
	        panelDatos.add(campoCliente, gbc);

	        gbc.gridx = 0;
	        gbc.gridy = 2;
	        panelDatos.add(new JLabel("Forma de Pago:"), gbc);
	        gbc.gridx = 1;
	        panelDatos.add(campoFormaPago, gbc);

	        gbc.gridx = 2;
	        panelDatos.add(new JLabel("Impuestos:"), gbc);
	        gbc.gridx = 3;
	        panelDatos.add(campoImpuestos, gbc);

	        gbc.gridx = 0;
	        gbc.gridy = 3;
	        panelDatos.add(new JLabel("Total:"), gbc);
	        gbc.gridx = 1;
	        panelDatos.add(campoTotal, gbc);

	        gbc.gridx = 2;
	        panelDatos.add(new JLabel("Estado:"), gbc);
	        gbc.gridx = 3;
	        panelDatos.add(campoEstado, gbc);

	        JScrollPane scrollTabla = new JScrollPane(tablaDetalle);
	        scrollTabla.setBorder(BorderFactory.createTitledBorder("Productos Vendidos"));

	        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
	        panelBotones.add(botonCerrar);

	        panelPrincipal.add(panelDatos, BorderLayout.NORTH);
	        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
	        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

	        add(panelPrincipal);
	    }

	    private void inicializarEventos() {
	        botonCerrar.addActionListener(e -> dispose());
	    }

	    public void cargarVenta(String numeroFactura, String fecha, String hora, String cliente,
	            String formaPago, String impuestos, String total, String estado) {

	        campoNumeroFactura.setText(numeroFactura);
	        campoFecha.setText(fecha);
	        campoHora.setText(hora);
	        campoCliente.setText(cliente);
	        campoFormaPago.setText(formaPago);
	        campoImpuestos.setText(impuestos);
	        campoTotal.setText(total);
	        campoEstado.setText(estado);
	    }

	    public void agregarDetalle(String producto, String cantidad, String precioUnitario,
	            String impuestos, String subtotal) {
	        modeloTabla.addRow(new Object[] { producto, cantidad, precioUnitario, impuestos, subtotal });
	    }

	    public void limpiarTabla() {
	        modeloTabla.setRowCount(0);
	    }

	    public void limpiarCampos() {
	        campoNumeroFactura.setText("");
	        campoFecha.setText("");
	        campoHora.setText("");
	        campoCliente.setText("");
	        campoFormaPago.setText("");
	        campoImpuestos.setText("");
	        campoTotal.setText("");
	        campoEstado.setText("");
	    }
}
