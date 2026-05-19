package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelProducto extends PanelBase {

    private JTextField txtCodigo, txtNombre, txtCategoria, txtPrecioCompra, 
                       txtPrecioVenta, txtStockActual, txtStockMinimo, txtStockMaximo;
    private JButton btnGuardar, btnEditar, btnBuscar, btnInactivar;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    public PanelProducto() {
        super();
    }

    @Override
    public void initComponents() {
        this.setLayout(new BorderLayout(0, 15));

       
        JLabel lblTitulo = ConstructorComponentes.crearLabelTitulo("Gestión de Inventario - CRUD Completo");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lblTitulo, BorderLayout.NORTH);

       
        JPanel panelNorte = new JPanel(new BorderLayout(0, 10));
        panelNorte.setOpaque(false);

        
        JPanel panelForm = new JPanel(new GridLayout(4, 4, 10, 10));
        panelForm.setOpaque(false);
        
        panelForm.add(new JLabel("Código:")); txtCodigo = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtCodigo);
        panelForm.add(new JLabel("Nombre:")); txtNombre = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtNombre);
        panelForm.add(new JLabel("Categoría:")); txtCategoria = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtCategoria);
        panelForm.add(new JLabel("P. Compra:")); txtPrecioCompra = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtPrecioCompra);
        panelForm.add(new JLabel("P. Venta:")); txtPrecioVenta = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtPrecioVenta);
        panelForm.add(new JLabel("Stock Act:")); txtStockActual = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtStockActual);
        panelForm.add(new JLabel("Stock Mín:")); txtStockMinimo = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtStockMinimo);
        panelForm.add(new JLabel("Stock Máx:")); txtStockMaximo = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtStockMaximo);

     
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.setOpaque(false);
        btnGuardar = ConstructorComponentes.crearBotonPrimario("GUARDAR");
        btnEditar = ConstructorComponentes.crearBotonPrimario("EDITAR");
        btnBuscar = ConstructorComponentes.crearBotonPrimario("BUSCAR");
        btnInactivar = ConstructorComponentes.crearBotonPrimario("INACTIVAR");
        btnInactivar.setBackground(new Color(198, 40, 40)); // Rojo para inactivar

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnInactivar);

        panelNorte.add(panelForm, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        
        String[] columnas = {"Código", "Nombre", "Categoría", "P. Venta", "Stock", "Mínimo"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tablaProductos);

        this.add(panelNorte, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);
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
    public JButton getBtnEditar() { return btnEditar; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnInactivar() { return btnInactivar; }
    public JTable getTablaProductos() { return tablaProductos; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
}