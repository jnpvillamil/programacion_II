package co.edu.uptc.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.negocio.dto.ventaDto;

public class panelVentas extends JPanel {

    public JButton bRegistrar;
    public JButton bAnular;
    public JButton bBuscar;
    public JButton bVolver;
    public JButton bAgregarProducto;
    public JButton bQuitarProducto;

    private JLabel lFechaHora;
    private JLabel lCliente;
    private JLabel lFormaPago;
    private JLabel lIva;
    private JLabel lTotal;
    private JLabel lRetencion;
    private JLabel lTotalFinal;

    public JTextField tFechaHora;
    public JTextField tCliente;
    public JTextField tTotal;
    public JTextField tRetencion;
    public JTextField tTotalFinal;

    public JComboBox<String> cbFormaPago;
    public JCheckBox         chkIva;

    public DefaultTableModel modeloProductos;
    public JTable             tablaProductos;

    private Eventos evento;

    public panelVentas(Eventos evento) {
        this.evento = evento;
        construirPanel();
    }

    public panelVentas() {
        this.evento = new Eventos();
        construirPanel();
    }

    private void construirPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel Campos = new JPanel(new GridLayout(7, 2, 8, 8));
        Campos.setBorder(BorderFactory.createTitledBorder("Informacion de factura"));

        lFechaHora  = new JLabel("FECHA Y HORA:");
        lCliente    = new JLabel("CLIENTE:");
        lFormaPago  = new JLabel("FORMA DE PAGO:");
        lIva        = new JLabel("IVA:");
        lTotal      = new JLabel("SUBTOTAL:");
        lRetencion  = new JLabel("RETENCION (%):");
        lTotalFinal = new JLabel("TOTAL A PAGAR:");

        tFechaHora  = new JTextField(10);
        tCliente    = new JTextField(10);
        tTotal      = new JTextField(10);
        tRetencion  = new JTextField("0");
        tTotalFinal = new JTextField(10);
        tTotalFinal.setEditable(false);

        String[] formasPago = {"Efectivo", "Transferencia", "Tarjeta", "Credito"};
        cbFormaPago = new JComboBox<>(formasPago);
        chkIva      = new JCheckBox("Aplicar IVA");

        Campos.add(lFechaHora);  Campos.add(tFechaHora);
        Campos.add(lCliente);    Campos.add(tCliente);
        Campos.add(lFormaPago);  Campos.add(cbFormaPago);
        Campos.add(lIva);        Campos.add(chkIva);
        Campos.add(lTotal);      Campos.add(tTotal);
        Campos.add(lRetencion);  Campos.add(tRetencion);
        Campos.add(lTotalFinal); Campos.add(tTotalFinal);

        String[] columnas = {"Codigo", "Descripcion", "Cant", "Precio Unit.", "Subtotal"};
        modeloProductos = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return col != 4; }
        };
        tablaProductos = new JTable(modeloProductos);
        tablaProductos.setRowHeight(22);

        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBorder(BorderFactory.createTitledBorder("Productos de la venta"));
        scroll.setPreferredSize(new Dimension(480, 150));

        bAgregarProducto = new JButton("Agregar fila");
        bQuitarProducto  = new JButton("Quitar fila");

        JPanel filaBtnsTabla = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        filaBtnsTabla.add(bAgregarProducto);
        filaBtnsTabla.add(bQuitarProducto);

        JPanel panelTablaBtn = new JPanel(new BorderLayout(5, 5));
        panelTablaBtn.add(scroll,        BorderLayout.CENTER);
        panelTablaBtn.add(filaBtnsTabla, BorderLayout.SOUTH);

        bRegistrar = new JButton(Eventos.vREGISTRAR);
        bAnular    = new JButton(Eventos.vANULAR);
        bBuscar    = new JButton(Eventos.vBUSCAR);
        bVolver    = new JButton(Eventos.VOLVER);

        JPanel botones = new JPanel(new GridLayout(1, 3, 10, 10));
        botones.setBorder(BorderFactory.createEmptyBorder(8, 0, 4, 0));
        botones.add(bRegistrar);
        botones.add(bAnular);
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
        bAnular.addActionListener(evento);
        bBuscar.addActionListener(evento);
        bVolver.addActionListener(evento);

        bAgregarProducto.addActionListener(e ->
            modeloProductos.addRow(new Object[]{"", "", "1", "0.0", "0.0"})
        );
        bQuitarProducto.addActionListener(e -> {
            int fila = tablaProductos.getSelectedRow();
            if (fila >= 0) modeloProductos.removeRow(fila);
            recalcularTotales();
        });

        modeloProductos.addTableModelListener(e -> recalcularTotales());
        chkIva.addActionListener(e -> recalcularTotales());
        tRetencion.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) { recalcularTotales(); }
        });
    }

    private void recalcularTotales() {
        double subtotal = 0;
        for (int i = 0; i < modeloProductos.getRowCount(); i++) {
            try {
                double cant   = Double.parseDouble(modeloProductos.getValueAt(i, 2).toString());
                double precio = Double.parseDouble(modeloProductos.getValueAt(i, 3).toString());
                double sub    = cant * precio;
                modeloProductos.setValueAt(String.format("%.2f", sub), i, 4);
                subtotal += sub;
            } catch (NumberFormatException ex) {
                modeloProductos.setValueAt("0.0", i, 4);
            }
        }
        double iva = chkIva.isSelected() ? subtotal * 0.19 : 0;
        double total = subtotal + iva;
        double pctRet = 0;
        try { pctRet = Double.parseDouble(tRetencion.getText()); } catch (NumberFormatException ex) {}
        double retencion  = total * (pctRet / 100.0);
        double totalFinal = total - retencion;
        tTotal.setText(String.format("%.2f", total));
        tTotalFinal.setText(String.format("%.2f", totalFinal));
    }

    public ventaDto getDatosVenta() {
        String cliente = tCliente.getText();
        if (cliente == null || cliente.isBlank()) {
            JOptionPane.showMessageDialog(this, "El cliente es requerido");
            return null;
        }
        ventaDto venta = new ventaDto();
        venta.setFechaHora(tFechaHora.getText());
        venta.setCliente(cliente);
        venta.setFormaPago((String) cbFormaPago.getSelectedItem());
        venta.setAplicarIva(chkIva.isSelected());
        try {
            venta.setTotal(Double.parseDouble(tTotalFinal.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El total debe ser numerico");
            return null;
        }
        return venta;
    }

    public int getNumeroFactura() {
        // Retorna el numero de la fila seleccionada o -1
        // Para anular/buscar se maneja desde Eventos con un campo de busqueda simple
        JOptionPane.showMessageDialog(this, "Seleccione la venta desde la tabla o use Buscar");
        return -1;
    }
}
