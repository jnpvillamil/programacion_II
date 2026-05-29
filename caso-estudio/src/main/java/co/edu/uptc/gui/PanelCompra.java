package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelCompra extends PanelBase {

    private JTextField txtFacturaProveedor;
    private JTextField txtIdentificacionProveedor;
    private JTextField txtCodigoProducto;
    private JTextField txtCantidad;
    private JTextField txtCostoUnitario;
    
    private JButton btnBuscarProveedor;
    private JButton btnAgregarProducto;
    private JButton btnFinalizarCompra;
    
    private JLabel lblNombreProveedor;
    private JLabel lblTotalCompra;
    
    private JTable tablaCompra;
    private DefaultTableModel modeloTabla;

    public PanelCompra() {
        super();
    }

    @Override
    public void initComponents() {
        this.setLayout(new BorderLayout(0, 15));

        this.add(ConstructorComponentes.crearLabelTitulo("Compras"), BorderLayout.NORTH);

        JPanel panelBusqueda = new JPanel(new GridLayout(2, 1, 0, 10));
        panelBusqueda.setOpaque(false);

        JPanel panelProveedor = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 8));
        panelProveedor.setOpaque(false);
        panelProveedor.setBorder(ConstructorComponentes.crearBordeSeccion("Proveedor"));
        panelProveedor.add(ConstructorComponentes.crearLabelFormulario("No. Factura:"));
        txtFacturaProveedor = ConstructorComponentes.crearCampoTexto();
        txtFacturaProveedor.setPreferredSize(new Dimension(120, ConstructorComponentes.ALTURA_CAMPO));
        panelProveedor.add(txtFacturaProveedor);

        panelProveedor.add(ConstructorComponentes.crearLabelFormulario("NIT Proveedor:"));
        txtIdentificacionProveedor = ConstructorComponentes.crearCampoTexto();
        txtIdentificacionProveedor.setPreferredSize(new Dimension(140, ConstructorComponentes.ALTURA_CAMPO));
        panelProveedor.add(txtIdentificacionProveedor);
        
        btnBuscarProveedor = ConstructorComponentes.crearBotonPrimario("Buscar");
        panelProveedor.add(btnBuscarProveedor);
        
        lblNombreProveedor = new JLabel("Proveedor: NO SELECCIONADO");
        lblNombreProveedor.setFont(new Font(ConstructorComponentes.FUENTE_UI, Font.ITALIC, ConstructorComponentes.TAMANIO_LABEL));
        panelProveedor.add(lblNombreProveedor);

        JPanel panelProducto = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 8));
        panelProducto.setOpaque(false);
        panelProducto.setBorder(ConstructorComponentes.crearBordeSeccion("Producto"));
        panelProducto.add(ConstructorComponentes.crearLabelFormulario("Cód. Producto:"));
        txtCodigoProducto = ConstructorComponentes.crearCampoTexto();
        txtCodigoProducto.setPreferredSize(new Dimension(120, ConstructorComponentes.ALTURA_CAMPO));
        panelProducto.add(txtCodigoProducto);

        panelProducto.add(ConstructorComponentes.crearLabelFormulario("Costo Unitario ($):"));
        txtCostoUnitario = ConstructorComponentes.crearCampoTexto();
        txtCostoUnitario.setPreferredSize(new Dimension(120, ConstructorComponentes.ALTURA_CAMPO));
        panelProducto.add(txtCostoUnitario);

        panelProducto.add(ConstructorComponentes.crearLabelFormulario("Cantidad:"));
        txtCantidad = ConstructorComponentes.crearCampoTexto();
        txtCantidad.setPreferredSize(new Dimension(80, ConstructorComponentes.ALTURA_CAMPO));
        panelProducto.add(txtCantidad);

        btnAgregarProducto = ConstructorComponentes.crearBotonPrimario("Agregar Mercancía");
        panelProducto.add(btnAgregarProducto);

        panelBusqueda.add(panelProveedor);
        panelBusqueda.add(panelProducto);

        String[] columnas = {"Código", "Descripción", "Cantidad", "Costo Unitario", "Subtotal"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaCompra = new JTable(modeloTabla);
        ConstructorComponentes.estilizarTabla(tablaCompra);
        JScrollPane scrollTabla = new JScrollPane(tablaCompra);
        scrollTabla.getViewport().setBackground(Color.WHITE);

        JPanel panelCierre = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        panelCierre.setOpaque(false);
        panelCierre.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        lblTotalCompra = new JLabel("TOTAL COMPRA: $ 0.00");
        lblTotalCompra.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTotalCompra.setForeground(Color.decode("#2E7D32"));
        panelCierre.add(lblTotalCompra);
        
        btnFinalizarCompra = ConstructorComponentes.crearBotonGuardar("REGISTRAR COMPRA");
        btnFinalizarCompra.setPreferredSize(new Dimension(220, 40));
        panelCierre.add(btnFinalizarCompra);

        JPanel contenedorCentro = new JPanel(new BorderLayout(0, 10));
        contenedorCentro.setOpaque(false);
        contenedorCentro.add(panelBusqueda, BorderLayout.NORTH);
        contenedorCentro.add(scrollTabla, BorderLayout.CENTER);
        contenedorCentro.add(panelCierre, BorderLayout.SOUTH);

        this.add(contenedorCentro, BorderLayout.CENTER);
    }

    public JTextField getTxtFacturaProveedor() { return txtFacturaProveedor; }
    public JTextField getTxtIdentificacionProveedor() { return txtIdentificacionProveedor; }
    public JTextField getTxtCodigoProducto() { return txtCodigoProducto; }
    public JTextField getTxtCantidad() { return txtCantidad; }
    public JTextField getTxtCostoUnitario() { return txtCostoUnitario; }
    public JButton getBtnBuscarProveedor() { return btnBuscarProveedor; }
    public JButton getBtnAgregarProducto() { return btnAgregarProducto; }
    public JButton getBtnFinalizarCompra() { return btnFinalizarCompra; }
    public JLabel getLblNombreProveedor() { return lblNombreProveedor; }
    public JLabel getLblTotalCompra() { return lblTotalCompra; }
    public JTable getTablaCompra() { return tablaCompra; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
}