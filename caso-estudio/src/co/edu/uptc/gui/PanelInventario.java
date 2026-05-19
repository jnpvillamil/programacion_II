package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelInventario extends PanelBase {

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtCategoria;
    private JTextField txtPrecioCompra;
    private JTextField txtPrecioVenta;
    private JTextField txtStockActual;
    private JTextField txtStockMinimo;
    private JTextField txtStockMaximo;
    
    private JButton btnGuardar;
    private JButton btnEliminar;
    
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    public PanelInventario() {
        super();
    }

    @Override
    public void initComponents() {
        this.setLayout(new BorderLayout(0, 20));

      
        JLabel lblTitulo = ConstructorComponentes.crearLabelTitulo("Gestión de Productos e Inventario");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lblTitulo, BorderLayout.NORTH);

        // Panel Central (Formulario + Botones)
        JPanel panelSuperior = new JPanel(new BorderLayout(0, 10));
        panelSuperior.setOpaque(false);

        // Formulario (GridLayout)
        JPanel panelFormulario = new JPanel(new GridLayout(4, 4, 15, 10));
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

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setOpaque(false);
        
        btnGuardar = ConstructorComponentes.crearBotonPrimario("GUARDAR PRODUCTO");
        btnGuardar.setBackground(Color.decode("#2E7D32")); // Verde oscuro para guardar
        
        btnEliminar = ConstructorComponentes.crearBotonPrimario("ELIMINAR PRODUCTO");
        btnEliminar.setBackground(Color.decode("#C62828")); // Rojo para eliminar

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);

        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

      
        String[] columnas = {"Código", "Nombre", "Categoría", "P. Compra", "P. Venta", "Stock", "Mínimo", "Máximo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.getTableHeader().setBackground(ConstructorComponentes.AZUL_OSCURO);
        tablaProductos.getTableHeader().setForeground(Color.WHITE);
        tablaProductos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tablaProductos.setRowHeight(25);
        
        JScrollPane scrollTabla = new JScrollPane(tablaProductos);
        scrollTabla.getViewport().setBackground(Color.WHITE);

        JPanel contenedorCentro = new JPanel(new BorderLayout(0, 15));
        contenedorCentro.setOpaque(false);
        contenedorCentro.add(panelSuperior, BorderLayout.NORTH);
        contenedorCentro.add(scrollTabla, BorderLayout.CENTER);

        this.add(contenedorCentro, BorderLayout.CENTER);
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
    public JButton getBtnEliminar() { return btnEliminar; }
    public JTable getTablaProductos() { return tablaProductos; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
}