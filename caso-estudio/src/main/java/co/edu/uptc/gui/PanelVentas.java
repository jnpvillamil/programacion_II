package co.edu.uptc.gui;

import co.edu.uptc.enums.FormaPago;
import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelVentas extends PanelBase {

    private JTextField txtIdentificacionCliente;
    private JTextField txtCodigoProducto;
    private JTextField txtCantidad;
    private JComboBox<FormaPago> cbFormaPago;

    private JButton btnBuscarCliente;
    private JButton btnAgregarProducto;
    private JButton btnFinalizarVenta;

    private JLabel lblNombreCliente;
    private JLabel lblTotalPagar;

    private JTable tablaCarrito;
    private DefaultTableModel modeloTabla;

    private JTextField txtIdClienteConsulta;
    private JTextField txtFechaConsulta;
    private JTextField txtNumeroFactura;
    private JButton btnHistorialCliente;
    private JButton btnConsultarPorFecha;
    private JButton btnReimprimir;
    private JButton btnAnularVenta;
    private JTable tablaVentas;
    private DefaultTableModel modeloTablaVentas;

    public PanelVentas() {
        super();
    }

    @Override
    public void initComponents() {
        this.setLayout(new BorderLayout(0, 15));

        this.add(ConstructorComponentes.crearLabelTitulo("Ventas"), BorderLayout.NORTH);

        JTabbedPane pestanas = ConstructorComponentes.crearPestanas();
        pestanas.addTab("  Nueva Venta  ", crearPanelPos());
        pestanas.addTab("  Consultar / Anular  ", crearPanelGestion());
        this.add(pestanas, BorderLayout.CENTER);
    }

    private JPanel crearPanelPos() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelSuperior = new JPanel(new BorderLayout(0, 12));
        panelSuperior.setOpaque(false);

        JPanel panelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 8));
        panelCliente.setOpaque(false);
        panelCliente.setBorder(ConstructorComponentes.crearBordeSeccion("Cliente"));
        panelCliente.add(ConstructorComponentes.crearLabelFormulario("ID Cliente:"));
        txtIdentificacionCliente = ConstructorComponentes.crearCampoTexto();
        txtIdentificacionCliente.setPreferredSize(new Dimension(160, ConstructorComponentes.ALTURA_CAMPO));
        panelCliente.add(txtIdentificacionCliente);

        btnBuscarCliente = ConstructorComponentes.crearBotonPrimario("Buscar Cliente");
        panelCliente.add(btnBuscarCliente);

        lblNombreCliente = new JLabel("Cliente: NO SELECCIONADO");
        lblNombreCliente.setFont(new Font(ConstructorComponentes.FUENTE_UI, Font.ITALIC, ConstructorComponentes.TAMANIO_LABEL));
        panelCliente.add(lblNombreCliente);

        JPanel panelProducto = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 8));
        panelProducto.setOpaque(false);
        panelProducto.setBorder(ConstructorComponentes.crearBordeSeccion("Producto"));
        panelProducto.add(ConstructorComponentes.crearLabelFormulario("Cód. Producto:"));
        txtCodigoProducto = ConstructorComponentes.crearCampoTexto();
        txtCodigoProducto.setPreferredSize(new Dimension(140, ConstructorComponentes.ALTURA_CAMPO));
        panelProducto.add(txtCodigoProducto);

        panelProducto.add(ConstructorComponentes.crearLabelFormulario("Cantidad:"));
        txtCantidad = ConstructorComponentes.crearCampoTexto();
        txtCantidad.setPreferredSize(new Dimension(80, ConstructorComponentes.ALTURA_CAMPO));
        panelProducto.add(txtCantidad);

        btnAgregarProducto = ConstructorComponentes.crearBotonPrimario("Agregar al Carrito");
        panelProducto.add(btnAgregarProducto);

        panelSuperior.add(panelCliente, BorderLayout.NORTH);
        panelSuperior.add(panelProducto, BorderLayout.CENTER);

        String[] columnas = {"Código", "Descripción", "Cantidad", "Precio Unitario", "Subtotal"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaCarrito = new JTable(modeloTabla);
        tablaCarrito.setFont(new Font(ConstructorComponentes.FUENTE_UI, Font.PLAIN, ConstructorComponentes.TAMANIO_CAMPO));
        tablaCarrito.getTableHeader().setFont(new Font(ConstructorComponentes.FUENTE_UI, Font.BOLD, ConstructorComponentes.TAMANIO_LABEL));
        tablaCarrito.getTableHeader().setBackground(ConstructorComponentes.AZUL_OSCURO);
        tablaCarrito.getTableHeader().setForeground(Color.WHITE);
        tablaCarrito.setRowHeight(30);
        JScrollPane scrollTabla = new JScrollPane(tablaCarrito);
        scrollTabla.setBorder(ConstructorComponentes.crearBordeSeccion("Carrito de venta"));
        scrollTabla.getViewport().setBackground(Color.WHITE);

        JPanel panelCierre = new JPanel(new BorderLayout());
        panelCierre.setOpaque(false);
        panelCierre.setBorder(BorderFactory.createCompoundBorder(
                ConstructorComponentes.crearBordeSeccion("Cierre de venta"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JPanel panelPago = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 8));
        panelPago.setOpaque(false);
        panelPago.add(ConstructorComponentes.crearLabelFormulario("Forma de Pago:"));
        cbFormaPago = ConstructorComponentes.crearComboBoxEnum(FormaPago.values());
        cbFormaPago.setPreferredSize(new Dimension(180, ConstructorComponentes.ALTURA_CAMPO));
        panelPago.add(cbFormaPago);

        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 8));
        panelTotal.setOpaque(false);
        lblTotalPagar = new JLabel("TOTAL A PAGAR: $ 0.00");
        lblTotalPagar.setFont(new Font(ConstructorComponentes.FUENTE_UI, Font.BOLD, 22));
        lblTotalPagar.setForeground(Color.decode("#C62828"));
        panelTotal.add(lblTotalPagar);

        btnFinalizarVenta = ConstructorComponentes.crearBotonGuardar("FINALIZAR VENTA");
        btnFinalizarVenta.setPreferredSize(new Dimension(220, 42));
        panelTotal.add(btnFinalizarVenta);

        panelCierre.add(panelPago, BorderLayout.WEST);
        panelCierre.add(panelTotal, BorderLayout.EAST);

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(scrollTabla, BorderLayout.CENTER);
        panel.add(panelCierre, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelGestion() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelFiltros = new JPanel(new GridLayout(3, 1, 0, 12));
        panelFiltros.setOpaque(false);

        JPanel filaCliente = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 8));
        filaCliente.setOpaque(false);
        filaCliente.setBorder(ConstructorComponentes.crearBordeSeccion("Consulta por cliente"));
        filaCliente.add(ConstructorComponentes.crearLabelFormulario("ID Cliente:"));
        txtIdClienteConsulta = ConstructorComponentes.crearCampoTexto();
        txtIdClienteConsulta.setPreferredSize(new Dimension(160, ConstructorComponentes.ALTURA_CAMPO));
        filaCliente.add(txtIdClienteConsulta);
        btnHistorialCliente = ConstructorComponentes.crearBotonPrimario("Historial Cliente");
        filaCliente.add(btnHistorialCliente);

        JPanel filaFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 8));
        filaFecha.setOpaque(false);
        filaFecha.setBorder(ConstructorComponentes.crearBordeSeccion("Consulta por fecha"));
        filaFecha.add(ConstructorComponentes.crearLabelFormulario("Fecha (dd/MM/yyyy):"));
        txtFechaConsulta = ConstructorComponentes.crearCampoTexto();
        txtFechaConsulta.setPreferredSize(new Dimension(140, ConstructorComponentes.ALTURA_CAMPO));
        filaFecha.add(txtFechaConsulta);
        btnConsultarPorFecha = ConstructorComponentes.crearBotonPrimario("Consultar por Fecha");
        filaFecha.add(btnConsultarPorFecha);

        JPanel filaFactura = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 8));
        filaFactura.setOpaque(false);
        filaFactura.setBorder(ConstructorComponentes.crearBordeSeccion("Acciones sobre factura"));
        filaFactura.add(ConstructorComponentes.crearLabelFormulario("N° Factura:"));
        txtNumeroFactura = ConstructorComponentes.crearCampoTexto();
        txtNumeroFactura.setPreferredSize(new Dimension(200, ConstructorComponentes.ALTURA_CAMPO));
        filaFactura.add(txtNumeroFactura);
        btnReimprimir = ConstructorComponentes.crearBotonPrimario("Reimprimir Comprobante");
        filaFactura.add(btnReimprimir);
        btnAnularVenta = ConstructorComponentes.crearBotonPeligro("Anular Venta");
        filaFactura.add(btnAnularVenta);

        panelFiltros.add(filaCliente);
        panelFiltros.add(filaFecha);
        panelFiltros.add(filaFactura);

        String[] columnas = {"Factura", "Fecha", "Cliente", "Subtotal", "IVA", "Total", "Forma Pago"};
        modeloTablaVentas = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVentas = new JTable(modeloTablaVentas);
        tablaVentas.setFont(new Font(ConstructorComponentes.FUENTE_UI, Font.PLAIN, ConstructorComponentes.TAMANIO_CAMPO));
        tablaVentas.getTableHeader().setFont(new Font(ConstructorComponentes.FUENTE_UI, Font.BOLD, ConstructorComponentes.TAMANIO_LABEL));
        tablaVentas.getTableHeader().setBackground(ConstructorComponentes.AZUL_OSCURO);
        tablaVentas.getTableHeader().setForeground(Color.WHITE);
        tablaVentas.setRowHeight(30);
        tablaVentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollVentas = new JScrollPane(tablaVentas);
        scrollVentas.setBorder(ConstructorComponentes.crearBordeSeccion("Resultados"));
        scrollVentas.getViewport().setBackground(Color.WHITE);

        panel.add(panelFiltros, BorderLayout.NORTH);
        panel.add(scrollVentas, BorderLayout.CENTER);
        return panel;
    }

    public JTextField getTxtIdentificacionCliente() { return txtIdentificacionCliente; }
    public JTextField getTxtCodigoProducto() { return txtCodigoProducto; }
    public JTextField getTxtCantidad() { return txtCantidad; }
    public JComboBox<FormaPago> getCbFormaPago() { return cbFormaPago; }
    public JButton getBtnBuscarCliente() { return btnBuscarCliente; }
    public JButton getBtnAgregarProducto() { return btnAgregarProducto; }
    public JButton getBtnFinalizarVenta() { return btnFinalizarVenta; }
    public JLabel getLblNombreCliente() { return lblNombreCliente; }
    public JLabel getLblTotalPagar() { return lblTotalPagar; }
    public JTable getTablaCarrito() { return tablaCarrito; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTextField getTxtIdClienteConsulta() { return txtIdClienteConsulta; }
    public JTextField getTxtFechaConsulta() { return txtFechaConsulta; }
    public JTextField getTxtNumeroFactura() { return txtNumeroFactura; }
    public JButton getBtnHistorialCliente() { return btnHistorialCliente; }
    public JButton getBtnConsultarPorFecha() { return btnConsultarPorFecha; }
    public JButton getBtnReimprimir() { return btnReimprimir; }
    public JButton getBtnAnularVenta() { return btnAnularVenta; }
    public JTable getTablaVentas() { return tablaVentas; }
    public DefaultTableModel getModeloTablaVentas() { return modeloTablaVentas; }
}
