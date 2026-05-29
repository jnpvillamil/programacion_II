package co.edu.uptc.gui;

import co.edu.uptc.enums.CategoriaProducto;
import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelProducto extends PanelBase {

    private JTextField txtCodigo, txtNombre, txtPrecioCompra,
                       txtPrecioVenta, txtStockActual, txtStockMinimo, txtStockMaximo;
    private JComboBox<CategoriaProducto> cbCategoria;
    private JButton btnGuardar, btnEditar, btnBuscar, btnInactivar;
    private JButton btnRegistrarMovimiento;
    private JTextField txtCodMovimiento, txtCantMovimiento;
    private JComboBox<String> cbTipoMovimiento;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    public PanelProducto() {
        super();
    }

    @Override
    public void initComponents() {
        this.setLayout(new BorderLayout(0, 15));

        JPanel panelNorte = new JPanel(new BorderLayout(0, 10));
        panelNorte.setOpaque(false);

        
        JPanel panelForm = new JPanel(new GridLayout(4, 4, 10, 10));
        panelForm.setOpaque(false);
        
        panelForm.add(ConstructorComponentes.crearLabelFormulario("Código:")); txtCodigo = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtCodigo);
        panelForm.add(ConstructorComponentes.crearLabelFormulario("Nombre:")); txtNombre = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtNombre);
        panelForm.add(ConstructorComponentes.crearLabelFormulario("Categoría:"));
        cbCategoria = ConstructorComponentes.crearComboBoxEnum(CategoriaProducto.values());
        panelForm.add(cbCategoria);
        panelForm.add(ConstructorComponentes.crearLabelFormulario("P. Compra:")); txtPrecioCompra = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtPrecioCompra);
        panelForm.add(ConstructorComponentes.crearLabelFormulario("P. Venta:")); txtPrecioVenta = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtPrecioVenta);
        panelForm.add(ConstructorComponentes.crearLabelFormulario("Stock Act:")); txtStockActual = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtStockActual);
        panelForm.add(ConstructorComponentes.crearLabelFormulario("Stock Mín:")); txtStockMinimo = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtStockMinimo);
        panelForm.add(ConstructorComponentes.crearLabelFormulario("Stock Máx:")); txtStockMaximo = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtStockMaximo);

     
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.setOpaque(false);
        btnGuardar = ConstructorComponentes.crearBotonGuardar("GUARDAR");
        btnEditar = ConstructorComponentes.crearBotonPrimario("EDITAR");
        btnBuscar = ConstructorComponentes.crearBotonPrimario("BUSCAR");
        btnInactivar = ConstructorComponentes.crearBotonPeligro("INACTIVAR");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnInactivar);

        panelNorte.add(panelForm, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        JPanel panelMovimientos = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelMovimientos.setOpaque(false);
        panelMovimientos.setBorder(BorderFactory.createTitledBorder("Movimiento manual de inventario"));
        panelMovimientos.add(ConstructorComponentes.crearLabelFormulario("Código:"));
        txtCodMovimiento = ConstructorComponentes.crearCampoTexto();
        txtCodMovimiento.setColumns(10);
        panelMovimientos.add(txtCodMovimiento);
        panelMovimientos.add(ConstructorComponentes.crearLabelFormulario("Cantidad:"));
        txtCantMovimiento = ConstructorComponentes.crearCampoTexto();
        txtCantMovimiento.setColumns(6);
        panelMovimientos.add(txtCantMovimiento);
        panelMovimientos.add(ConstructorComponentes.crearLabelFormulario("Tipo:"));
        cbTipoMovimiento = ConstructorComponentes.crearComboBox(new String[]{"ENTRADA", "SALIDA"});
        panelMovimientos.add(cbTipoMovimiento);
        btnRegistrarMovimiento = ConstructorComponentes.crearBotonPrimario("REGISTRAR MOV.");
        panelMovimientos.add(btnRegistrarMovimiento);

        JPanel panelCentro = new JPanel(new BorderLayout(0, 10));
        panelCentro.setOpaque(false);
        panelCentro.add(panelNorte, BorderLayout.NORTH);
        panelCentro.add(panelMovimientos, BorderLayout.SOUTH);
        
        String[] columnas = {"Código", "Nombre", "Categoría", "P. Venta", "Stock"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(modeloTabla);
        ConstructorComponentes.estilizarTabla(tablaProductos);
        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.getViewport().setBackground(Color.WHITE);

        JPanel panelEncabezado = new JPanel(new BorderLayout(0, 10));
        panelEncabezado.setOpaque(false);
        panelEncabezado.add(ConstructorComponentes.crearLabelTitulo("Inventario"), BorderLayout.NORTH);
        panelEncabezado.add(panelCentro, BorderLayout.CENTER);
        this.add(panelEncabezado, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);
    }


    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JComboBox<CategoriaProducto> getCbCategoria() { return cbCategoria; }
    public JTextField getTxtPrecioCompra() { return txtPrecioCompra; }
    public JTextField getTxtPrecioVenta() { return txtPrecioVenta; }
    public JTextField getTxtStockActual() { return txtStockActual; }
    public JTextField getTxtStockMinimo() { return txtStockMinimo; }
    public JTextField getTxtStockMaximo() { return txtStockMaximo; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnEditar() { return btnEditar; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnInactivar() { return btnInactivar; }
    public JButton getBtnRegistrarMovimiento() { return btnRegistrarMovimiento; }
    public JTextField getTxtCodMovimiento() { return txtCodMovimiento; }
    public JTextField getTxtCantMovimiento() { return txtCantMovimiento; }
    public JComboBox<String> getCbTipoMovimiento() { return cbTipoMovimiento; }
    public JTable getTablaProductos() { return tablaProductos; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
}