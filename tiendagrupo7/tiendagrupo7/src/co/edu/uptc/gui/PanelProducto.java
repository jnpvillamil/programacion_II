package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import java.awt.*;

public class PanelProducto extends PanelBase {

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtCategoria;
    private JTextField txtPrecioCompra;
    private JTextField txtPrecioVenta;
    private JTextField txtStockActual;
    private JTextField txtStockMinimo;
    private JTextField txtStockMaximo;
    
    private JButton btnGuardar;

    public PanelProducto() {
        super();
    }

    @Override
    public void initComponents() {
        this.setLayout(new BorderLayout(0, 30));

        JLabel lblTitulo = ConstructorComponentes.crearLabelTitulo("Registro de Nuevos Productos");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lblTitulo, BorderLayout.NORTH);

        // Formulario simplificado (Solo para agregar) [cite: 521, 522]
        JPanel panelFormulario = new JPanel(new GridLayout(4, 4, 20, 15));
        panelFormulario.setOpaque(false);

        panelFormulario.add(new JLabel("Código:"));
        txtCodigo = ConstructorComponentes.crearCampoTexto();
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = ConstructorComponentes.crearCampoTexto();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Categoría:"));
        txtCategoria = ConstructorComponentes.crearCampoTexto();
        panelFormulario.add(txtCategoria);

        panelFormulario.add(new JLabel("Precio Compra:"));
        txtPrecioCompra = ConstructorComponentes.crearCampoTexto();
        panelFormulario.add(txtPrecioCompra);

        panelFormulario.add(new JLabel("Precio Venta:"));
        txtPrecioVenta = ConstructorComponentes.crearCampoTexto();
        panelFormulario.add(txtPrecioVenta);

        panelFormulario.add(new JLabel("Stock Inicial:"));
        txtStockActual = ConstructorComponentes.crearCampoTexto();
        panelFormulario.add(txtStockActual);

        panelFormulario.add(new JLabel("Stock Mínimo:"));
        txtStockMinimo = ConstructorComponentes.crearCampoTexto();
        panelFormulario.add(txtStockMinimo);

        panelFormulario.add(new JLabel("Stock Máximo:"));
        txtStockMaximo = ConstructorComponentes.crearCampoTexto();
        panelFormulario.add(txtStockMaximo);

        JPanel panelAccion = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelAccion.setOpaque(false);
        btnGuardar = ConstructorComponentes.crearBotonPrimario("GUARDAR EN INVENTARIO");
        btnGuardar.setPreferredSize(new Dimension(300, 45));
        panelAccion.add(btnGuardar);

        this.add(panelFormulario, BorderLayout.CENTER);
        this.add(panelAccion, BorderLayout.SOUTH);
    }

    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtCategoria() { return txtCategoria; }
    public JTextField getTxtPrecioCompra() { return txtPrecioCompra; }
    public JTextField getTxtPrecioVenta() { return txtPrecioVenta; }
    public JTextField getTxtStockActual() { return txtStockActual; }
    public JTextField getTxtStockMinimo() { return txtStockMinimo; }
    public JTextField getTxtStockMaximo() { return txtStockMaximo; }
    public JButton getBtnGuardar() { return btnGuardar; }
}