package co.uptc.edu.co.gui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.co.gui.Evento;


import javax.swing.JDialog;

public class DialogCompra extends JDialog {
	private JTextField campoNumeroFactura;
    private JTextField campoFecha;
    private JComboBox<String> comboProveedor;

    private JTable tablaDetalleCompra;
    private DefaultTableModel modeloTabla;

    private JTextField campoImpuestos;
    private JTextField campoTotalCompra;

    private JButton botonAgregarProducto;
    private JButton botonQuitarProducto;
    private JButton botonGuardar;
    private JButton botonCancelar;

    public DialogCompra(Frame propietario) {
        this(propietario, null);
    }

    public DialogCompra(Frame propietario, Evento evento) {
        super(propietario, "Registrar Compra", true);
        inicializarComponentes();
        configurarDialogo();
        agregarComponentes();
    }

    private void inicializarComponentes() {
        campoNumeroFactura = new JTextField(15);
        campoFecha = new JTextField(15);

        comboProveedor = new JComboBox<>();
        comboProveedor.addItem("Seleccione proveedor");

        modeloTabla = new DefaultTableModel();
        modeloTabla.setColumnIdentifiers(new Object[] {
                "Código", "Producto", "Cantidad", "Costo Unitario", "Impuesto", "Subtotal"
        });

        tablaDetalleCompra = new JTable(modeloTabla);

        campoImpuestos = new JTextField(15);
        campoTotalCompra = new JTextField(15);

        botonAgregarProducto = new JButton("Agregar Producto");
        botonQuitarProducto = new JButton("Quitar Producto");
        botonGuardar = new JButton("Guardar");
        botonCancelar = new JButton("Cancelar");
    }

    private void configurarDialogo() {
        setSize(900, 550);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void agregarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelPrincipal.add(crearPanelDatosCompra(), BorderLayout.NORTH);
        panelPrincipal.add(crearPanelDetalleCompra(), BorderLayout.CENTER);
        panelPrincipal.add(crearPanelInferior(), BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JPanel crearPanelDatosCompra() {
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de la compra"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatos.add(new JLabel("N° Factura Proveedor:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(campoNumeroFactura, gbc);

        gbc.gridx = 2;
        panelDatos.add(new JLabel("Fecha:"), gbc);

        gbc.gridx = 3;
        panelDatos.add(campoFecha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelDatos.add(new JLabel("Proveedor:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        panelDatos.add(comboProveedor, gbc);

        return panelDatos;
    }

    private JPanel crearPanelDetalleCompra() {
        JPanel panelDetalle = new JPanel(new BorderLayout(10, 10));
        panelDetalle.setBorder(BorderFactory.createTitledBorder("Detalle de productos"));

        JPanel panelBotonesTabla = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelBotonesTabla.add(botonAgregarProducto);
        panelBotonesTabla.add(botonQuitarProducto);

        JScrollPane scrollTabla = new JScrollPane(tablaDetalleCompra);

        panelDetalle.add(panelBotonesTabla, BorderLayout.NORTH);
        panelDetalle.add(scrollTabla, BorderLayout.CENTER);

        return panelDetalle;
    }

    private JPanel crearPanelInferior() {
        JPanel panelInferior = new JPanel(new BorderLayout());

        JPanel panelResumen = new JPanel(new GridBagLayout());
        panelResumen.setBorder(BorderFactory.createTitledBorder("Resumen de compra"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelResumen.add(new JLabel("Impuestos:"), gbc);

        gbc.gridx = 1;
        panelResumen.add(campoImpuestos, gbc);

        gbc.gridx = 2;
        panelResumen.add(new JLabel("Total Compra:"), gbc);

        gbc.gridx = 3;
        panelResumen.add(campoTotalCompra, gbc);

        JPanel panelBotonesFinales = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotonesFinales.add(botonGuardar);
        panelBotonesFinales.add(botonCancelar);

        panelInferior.add(panelResumen, BorderLayout.NORTH);
        panelInferior.add(panelBotonesFinales, BorderLayout.SOUTH);

        return panelInferior;
    }

    public JTable getTablaDetalleCompra() {
        return tablaDetalleCompra;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JButton getBotonAgregarProducto() {
        return botonAgregarProducto;
    }

    public JButton getBotonQuitarProducto() {
        return botonQuitarProducto;
    }

    public JButton getBotonGuardar() {
        return botonGuardar;
    }

    public JButton getBotonCancelar() {
        return botonCancelar;
    }

    public JComboBox<String> getComboProveedor() {
        return comboProveedor;
    }

    public JTextField getCampoNumeroFactura() {
        return campoNumeroFactura;
    }

    public JTextField getCampoFecha() {
        return campoFecha;
    }

    public JTextField getCampoImpuestos() {
        return campoImpuestos;
    }

    public JTextField getCampoTotalCompra() {
        return campoTotalCompra;
    }
    // PENDIENTE:
    // Implementar captura de datos y conexión con lógica de compras.
	public String obtenerNumeroFactura() {
		// TODO Auto-generated method stub
		return null;
	}

	public String obtenerFecha() {
		// TODO Auto-generated method stub
		return null;
	}

	public String obtenerProveedor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String obtenerImpuestos() {
		// TODO Auto-generated method stub
		return null;
	}

	public String obtenerTotalCompra() {
		// TODO Auto-generated method stub
		return null;
	}


}
