package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelVentas extends PanelBase {

    private JTextField txtIdentificacionCliente;
    private JTextField txtCodigoProducto;
    private JTextField txtCantidad;
    private JComboBox<String> cbFormaPago;
    
    private JButton btnBuscarCliente;
    private JButton btnAgregarProducto;
    private JButton btnFinalizarVenta;
    
    private JLabel lblNombreCliente;
    private JLabel lblTotalPagar;
    
    private JTable tablaCarrito;
    private DefaultTableModel modeloTabla;

    public PanelVentas() {
        super();
    }

    @Override
    public void initComponents() {
        this.setLayout(new BorderLayout(0, 15));

        JLabel lblTitulo = ConstructorComponentes.crearLabelTitulo("Punto de Venta (POS)");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBusqueda = new JPanel(new GridLayout(2, 1, 0, 10));
        panelBusqueda.setOpaque(false);

        JPanel panelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelCliente.setOpaque(false);
        panelCliente.add(new JLabel("ID Cliente:"));
        txtIdentificacionCliente = ConstructorComponentes.crearCampoTexto();
        txtIdentificacionCliente.setPreferredSize(new Dimension(150, 25));
        panelCliente.add(txtIdentificacionCliente);
        
        btnBuscarCliente = ConstructorComponentes.crearBotonPrimario("Buscar Cliente");
        panelCliente.add(btnBuscarCliente);
        
        lblNombreCliente = new JLabel("Cliente: NO SELECCIONADO");
        lblNombreCliente.setFont(new Font("SansSerif", Font.ITALIC, 14));
        panelCliente.add(lblNombreCliente);

        JPanel panelProducto = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelProducto.setOpaque(false);
        panelProducto.add(new JLabel("Cód. Producto:"));
        txtCodigoProducto = ConstructorComponentes.crearCampoTexto();
        txtCodigoProducto.setPreferredSize(new Dimension(120, 25));
        panelProducto.add(txtCodigoProducto);

        panelProducto.add(new JLabel("Cantidad:"));
        txtCantidad = ConstructorComponentes.crearCampoTexto();
        txtCantidad.setPreferredSize(new Dimension(60, 25));
        panelProducto.add(txtCantidad);

        btnAgregarProducto = ConstructorComponentes.crearBotonPrimario("Agregar al Carrito");
        panelProducto.add(btnAgregarProducto);

        panelBusqueda.add(panelCliente);
        panelBusqueda.add(panelProducto);

        String[] columnas = {"Código", "Descripción", "Cantidad", "Precio Unitario", "Subtotal"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaCarrito = new JTable(modeloTabla);
        tablaCarrito.getTableHeader().setBackground(ConstructorComponentes.AZUL_OSCURO);
        tablaCarrito.getTableHeader().setForeground(Color.WHITE);
        tablaCarrito.setRowHeight(25);
        JScrollPane scrollTabla = new JScrollPane(tablaCarrito);
        scrollTabla.getViewport().setBackground(Color.WHITE);

        JPanel panelCierre = new JPanel(new BorderLayout());
        panelCierre.setOpaque(false);
        panelCierre.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel panelPago = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelPago.setOpaque(false);
        panelPago.add(new JLabel("Forma de Pago:"));
        cbFormaPago = new JComboBox<>(new String[]{"EFECTIVO", "TRANSFERENCIA", "TARJETA", "CREDITO"});
        cbFormaPago.setBackground(Color.WHITE);
        panelPago.add(cbFormaPago);

        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        panelTotal.setOpaque(false);
        lblTotalPagar = new JLabel("TOTAL A PAGAR: $ 0.00");
        lblTotalPagar.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTotalPagar.setForeground(Color.decode("#C62828")); // Rojo para resaltar
        panelTotal.add(lblTotalPagar);
        
        btnFinalizarVenta = ConstructorComponentes.crearBotonPrimario("FINALIZAR VENTA");
        btnFinalizarVenta.setPreferredSize(new Dimension(200, 40));
        panelTotal.add(btnFinalizarVenta);

        panelCierre.add(panelPago, BorderLayout.WEST);
        panelCierre.add(panelTotal, BorderLayout.EAST);

        JPanel contenedorCentro = new JPanel(new BorderLayout(0, 10));
        contenedorCentro.setOpaque(false);
        contenedorCentro.add(panelBusqueda, BorderLayout.NORTH);
        contenedorCentro.add(scrollTabla, BorderLayout.CENTER);
        contenedorCentro.add(panelCierre, BorderLayout.SOUTH);

        this.add(contenedorCentro, BorderLayout.CENTER);
    }

    public JTextField getTxtIdentificacionCliente() { return txtIdentificacionCliente; }
    public JTextField getTxtCodigoProducto() { return txtCodigoProducto; }
    public JTextField getTxtCantidad() { return txtCantidad; }
    public JComboBox<String> getCbFormaPago() { return cbFormaPago; }
    public JButton getBtnBuscarCliente() { return btnBuscarCliente; }
    public JButton getBtnAgregarProducto() { return btnAgregarProducto; }
    public JButton getBtnFinalizarVenta() { return btnFinalizarVenta; }
    public JLabel getLblNombreCliente() { return lblNombreCliente; }
    public JLabel getLblTotalPagar() { return lblTotalPagar; }
    public JTable getTablaCarrito() { return tablaCarrito; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
}