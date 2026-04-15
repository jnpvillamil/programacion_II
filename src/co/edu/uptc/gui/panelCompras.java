package co.edu.uptc.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import co.edu.uptc.negocio.compraDto;

public class panelCompras extends JPanel {

    public JButton bRegistrar;
    public JButton bBuscar;
    public JButton bVolver;
    public JButton bAgregarProducto;
    public JButton bQuitarProducto;

    private JLabel lNumeroFacturaProveedor;
    private JLabel lFechaCompra;
    private JLabel lCodigoProveedor;
    private JLabel lTotalCompra;

    public JTextField tNumeroFacturaProveedor;
    public JTextField tFechaCompra;
    public JTextField tCodigoProveedor;
    public JTextField tTotalCompra;


    public DefaultTableModel modeloProductos;
    public JTable             tablaProductos;

    private Eventos evento;

    public panelCompras(Eventos evento) {
        this.evento = evento;
        construirPanel();
    }

    public panelCompras() {
        this.evento = new Eventos();
        construirPanel();
    }

    private void construirPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JPanel Campos = new JPanel(new GridLayout(4, 2, 8, 8));
        Campos.setBorder(BorderFactory.createTitledBorder("Encabezado de orden de compra"));

        lNumeroFacturaProveedor = new JLabel("FACTURA PROVEEDOR:");
        lFechaCompra            = new JLabel("FECHA COMPRA:");
        lCodigoProveedor        = new JLabel("CODIGO PROVEEDOR:");
        lTotalCompra            = new JLabel("TOTAL COMPRA:");

        tNumeroFacturaProveedor = new JTextField(10);
        tFechaCompra            = new JTextField(10);
        tCodigoProveedor        = new JTextField(10);
        tTotalCompra            = new JTextField(10);
        tTotalCompra.setEditable(false);

        Campos.add(lNumeroFacturaProveedor); Campos.add(tNumeroFacturaProveedor);
        Campos.add(lFechaCompra);            Campos.add(tFechaCompra);
        Campos.add(lCodigoProveedor);        Campos.add(tCodigoProveedor);
        Campos.add(lTotalCompra);            Campos.add(tTotalCompra);


        String[] columnas = {"Codigo", "Descripcion", "Cantidad", "Costo Unit.", "Subtotal"};
        modeloProductos = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col != 4;
            }
        };
        tablaProductos = new JTable(modeloProductos);
        tablaProductos.setRowHeight(22);

        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBorder(BorderFactory.createTitledBorder("Productos comprados"));
        scroll.setPreferredSize(new Dimension(480, 150));

        bAgregarProducto = new JButton("+ Agregar fila");
        bQuitarProducto  = new JButton("- Quitar fila");

        JPanel filaBtnsTabla = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        filaBtnsTabla.add(bAgregarProducto);
        filaBtnsTabla.add(bQuitarProducto);

        JPanel panelTablaBtn = new JPanel(new BorderLayout(5, 5));
        panelTablaBtn.add(scroll,        BorderLayout.CENTER);
        panelTablaBtn.add(filaBtnsTabla, BorderLayout.SOUTH);


        bRegistrar = new JButton(Eventos.cREGISTRAR_COMPRA);
        bBuscar    = new JButton(Eventos.cBUSCAR_COMPRA);
        bVolver    = new JButton(Eventos.VOLVER);

        JPanel botones = new JPanel(new GridLayout(1, 2, 10, 10));
        botones.setBorder(BorderFactory.createEmptyBorder(8, 0, 4, 0));
        botones.add(bRegistrar);
        botones.add(bBuscar);

        JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelVolver.add(bVolver);

        JPanel sur = new JPanel(new BorderLayout());
        sur.add(botones,     BorderLayout.NORTH);
        sur.add(panelVolver, BorderLayout.SOUTH);


        add(Campos,        BorderLayout.NORTH);
        add(panelTablaBtn, BorderLayout.CENTER);
        add(sur,           BorderLayout.SOUTH);


        bRegistrar.addActionListener(evento);
        bBuscar.addActionListener(evento);
        bVolver.addActionListener(evento);

        bAgregarProducto.addActionListener(e ->
            modeloProductos.addRow(new Object[]{"", "", "1", "0.0", "0.0"})
        );

        bQuitarProducto.addActionListener(e -> {
            int fila = tablaProductos.getSelectedRow();
            if (fila >= 0) modeloProductos.removeRow(fila);
            recalcularTotal();
        });

        modeloProductos.addTableModelListener(e -> recalcularTotal());
    }

    private void recalcularTotal() {
        double total = 0;
        for (int i = 0; i < modeloProductos.getRowCount(); i++) {
            try {
                double cant  = Double.parseDouble(modeloProductos.getValueAt(i, 2).toString());
                double costo = Double.parseDouble(modeloProductos.getValueAt(i, 3).toString());
                double sub   = cant * costo;
                modeloProductos.setValueAt(String.format("%.2f", sub), i, 4);
                total += sub;
            } catch (NumberFormatException ex) {
                modeloProductos.setValueAt("0.0", i, 4);
            }
        }
        tTotalCompra.setText(String.format("%.2f", total));
    }

    public compraDto getDatosCompra() {
        String factura   = tNumeroFacturaProveedor.getText();
        String proveedor = tCodigoProveedor.getText();
        if (factura == null || factura.isBlank()) {
            JOptionPane.showMessageDialog(this, "El numero de factura del proveedor es requerido");
            return null;
        }
        if (proveedor == null || proveedor.isBlank()) {
            JOptionPane.showMessageDialog(this, "El codigo del proveedor es requerido");
            return null;
        }
        compraDto compra = new compraDto();
        compra.setNumeroFacturaProveedor(factura);
        compra.setFechaCompra(tFechaCompra.getText());
        compra.setCodigoProveedor(proveedor);
        try {
            compra.setTotalCompra(Double.parseDouble(tTotalCompra.getText()));
        } catch (NumberFormatException e) {
            compra.setTotalCompra(0);
        }
        return compra;
    }

    public String getNumeroFacturaProveedor() {
        String numero = tNumeroFacturaProveedor.getText();
        if (numero == null || numero.isBlank()) {
            JOptionPane.showMessageDialog(this, "El numero de factura del proveedor es requerido");
            return null;
        }
        return numero;
    }
}