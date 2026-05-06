package co.uptc.edu.co.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
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

public class DialogVenta extends JDialog {

	  // CAMPOS DE DATOS GENERALES
    private JTextField campoNumeroFactura;
    private JTextField campoFecha;
    private JTextField campoHora;
    private JComboBox<String> comboCliente;
    private JComboBox<String> comboFormaPago;

    // CAMPOS PARA AGREGAR PRODUCTO
    private JComboBox<String> comboProducto;
    private JTextField campoCantidad;
    private JTextField campoPrecioUnitario;

    // TABLA DE DETALLE DE LA VENTA
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    
    // CAMPOS DE RESUMEN
    private JTextField campoSubtotal;
    private JTextField campoIva;
    private JTextField campoTotal;

    // BOTONES
    private JButton botonAgregarProducto;
    private JButton botonQuitarProducto;
    private JButton botonGuardar;
    private JButton botonCancelar;

    // CONSTRUCTORES
    public DialogVenta(Frame propietario) {
        this(propietario, null);
    }

    public DialogVenta(Frame propietario, Evento evento) {
        super(propietario, "Registrar Venta", true);
        inicializarComponentes();
        configurarDialogo();
        agregarComponentes();
        inicializarEventos(evento);
    }

    // INICIALIZACIÓN DE COMPONENTES
    private void inicializarComponentes() {
        campoNumeroFactura = new JTextField(18);
        campoFecha = new JTextField(18);
        campoHora = new JTextField(18);

        comboCliente = new JComboBox<>();
        comboCliente.addItem("Seleccione cliente");

        comboFormaPago = new JComboBox<>();
        comboFormaPago.addItem("Efectivo");
        comboFormaPago.addItem("Transferencia");
        comboFormaPago.addItem("Tarjeta");
        comboFormaPago.addItem("Crédito");

        comboProducto = new JComboBox<>();
        comboProducto.addItem("Seleccione producto");

        campoCantidad = new JTextField(10);
        campoPrecioUnitario = new JTextField(12);

        modeloTabla = new DefaultTableModel(
                new String[] { "Código", "Producto", "Cantidad", "Precio Unitario", "IVA", "Subtotal" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloTabla);

        campoSubtotal = new JTextField(12);
        campoIva = new JTextField(12);
        campoTotal = new JTextField(12);

        botonAgregarProducto = new JButton("Agregar Producto");
        botonQuitarProducto = new JButton("Quitar Producto");
        botonGuardar = new JButton("Guardar");
        botonCancelar = new JButton("Cancelar");
        
        botonAgregarProducto.setBackground(new Color(46, 125, 50));
        botonAgregarProducto.setForeground(Color.WHITE);

        botonGuardar.setBackground(new Color(46, 125, 50));
        botonGuardar.setForeground(Color.WHITE);

        botonQuitarProducto.setBackground(new Color(198, 40, 40));
        botonQuitarProducto.setForeground(Color.WHITE);

        campoSubtotal.setEditable(false);
        campoIva.setEditable(false);
        campoTotal.setEditable(false);
    }

    // CONFIGURACIÓN GENERAL DEL DIÁLOGO
    private void configurarDialogo() {
        setSize(950, 650);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);

        tablaProductos.setRowHeight(25);
        tablaProductos.getTableHeader().setReorderingAllowed(false);
    }

 // AGREGAR COMPONENTES AL DIÁLOGO
    private void agregarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelPrincipal.add(crearPanelDatosGenerales(), BorderLayout.NORTH);
        panelPrincipal.add(crearPanelCentro(), BorderLayout.CENTER);
        panelPrincipal.add(crearPanelInferior(), BorderLayout.SOUTH);
        
        

        add(panelPrincipal);
    }

    // PANEL DE DATOS GENERALES
    private JPanel crearPanelDatosGenerales() {
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Datos de la venta"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelSuperior.add(new JLabel("N° Factura:"), gbc);

        gbc.gridx = 1;
        panelSuperior.add(campoNumeroFactura, gbc);

        gbc.gridx = 2;
        panelSuperior.add(new JLabel("Fecha:"), gbc);

        gbc.gridx = 3;
        panelSuperior.add(campoFecha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelSuperior.add(new JLabel("Hora:"), gbc);

        gbc.gridx = 1;
        panelSuperior.add(campoHora, gbc);

        gbc.gridx = 2;
        panelSuperior.add(new JLabel("Cliente:"), gbc);

        gbc.gridx = 3;
        panelSuperior.add(comboCliente, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelSuperior.add(new JLabel("Forma de pago:"), gbc);

        gbc.gridx = 1;
        panelSuperior.add(comboFormaPago, gbc);

        return panelSuperior;
    }

    // PANEL CENTRAL
    private JPanel crearPanelCentro() {
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.add(crearPanelAgregarProducto(), BorderLayout.NORTH);
        panelCentro.add(crearPanelTabla(), BorderLayout.CENTER);
        return panelCentro;
    }

    // PANEL PARA AGREGAR PRODUCTOS
    private JPanel crearPanelAgregarProducto() {
        JPanel panelProducto = new JPanel(new GridBagLayout());
        panelProducto.setBorder(BorderFactory.createTitledBorder("Agregar producto"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelProducto.add(new JLabel("Producto:"), gbc);

        gbc.gridx = 1;
        panelProducto.add(comboProducto, gbc);

        gbc.gridx = 2;
        panelProducto.add(new JLabel("Cantidad:"), gbc);

        gbc.gridx = 3;
        panelProducto.add(campoCantidad, gbc);

        gbc.gridx = 4;
        panelProducto.add(new JLabel("Precio Unitario:"), gbc);

        gbc.gridx = 5;
        panelProducto.add(campoPrecioUnitario, gbc);

        gbc.gridx = 6;
        panelProducto.add(botonAgregarProducto, gbc);

        return panelProducto;
    }

    // PANEL DE TABLA
    private JScrollPane crearPanelTabla() {
        JScrollPane scrollTabla = new JScrollPane(tablaProductos);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Detalle de productos vendidos"));
        return scrollTabla;
    }

    // PANEL INFERIOR
    private JPanel crearPanelInferior() {
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        panelInferior.add(crearPanelResumen(), BorderLayout.CENTER);
        panelInferior.add(crearPanelAcciones(), BorderLayout.SOUTH);
        return panelInferior;
    }

    // PANEL DE RESUMEN
    private JPanel crearPanelResumen() {
        JPanel panelResumen = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelResumen.setBorder(BorderFactory.createTitledBorder("Resumen"));

        panelResumen.add(new JLabel("Subtotal:"));
        panelResumen.add(campoSubtotal);

        panelResumen.add(new JLabel("IVA:"));
        panelResumen.add(campoIva);

        panelResumen.add(new JLabel("Total:"));
        panelResumen.add(campoTotal);

        return panelResumen;
    }

    // PANEL DE BOTONES FINALES
    private JPanel crearPanelAcciones() {
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelAcciones.add(botonQuitarProducto);
        panelAcciones.add(botonGuardar);
        panelAcciones.add(botonCancelar);
        return panelAcciones;
    }

    // EVENTOS
    private void inicializarEventos(Evento evento) {
        botonCancelar.addActionListener(e -> dispose());

        if (evento != null) {
            botonGuardar.setActionCommand(Evento.CMD_CONFIRMAR_VENTA);
            botonGuardar.addActionListener(evento);
        }
    }

    // MÉTODOS AUXILIARES DE TABLA
    public void agregarFilaProducto(Object[] fila) {
        modeloTabla.addRow(fila);
    }

    public void quitarFilaSeleccionada() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada != -1) {
            modeloTabla.removeRow(filaSeleccionada);
        }
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

   
}