package co.edu.uptc.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import co.edu.uptc.negocio.dto.compraDto;
import co.edu.uptc.negocio.dto.itemCompraDto;

public class panelCompras extends JPanel {

    // ── Botones ───────────────────────────────────────────────────────────
    public JButton bRegistrar;
    public JButton bAnular;
    public JButton bBuscar;
    public JButton bAgregarItem;
    public JButton bEliminarItem;
    public JButton bLimpiar;
    public JButton bVolver;

    // ── Campos encabezado compra ──────────────────────────────────────────
    public JTextField tFecha;
    public JTextField tCodigoProveedor;
    public JTextField tRazonSocial;
    public JTextField tImpuestos;
    public JTextField tSubtotal;
    public JTextField tTotal;

    // ── Campos para agregar ítems ─────────────────────────────────────────
    public JTextField tCodProducto;
    public JTextField tNomProducto;
    public JTextField tCantidad;
    public JTextField tCostoUnit;

    // ── Tablas ────────────────────────────────────────────────────────────
    public DefaultTableModel modeloItems;
    public JTable            tablaItems;
    public DefaultTableModel modeloCompras;
    public JTable            tablaCompras;

    // ── Lista temporal de ítems en curso ─────────────────────────────────
    private List<itemCompraDto> itemsActuales = new ArrayList<>();

    private Eventos evento;

    public panelCompras(Eventos evento) {
        this.evento = evento;
        construirPanel();
    }

    public panelCompras() {
        this.evento = new Eventos();
        construirPanel();
    }

    // ── Construcción del panel ────────────────────────────────────────────
    private void construirPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(construirPanelEncabezado(), BorderLayout.NORTH);
        add(construirPanelCentro(),    BorderLayout.CENTER);
        add(construirPanelBotones(),   BorderLayout.SOUTH);
    }

    private JPanel construirPanelEncabezado() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 8, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Datos de la Compra"));

        tFecha           = new JTextField(10);
        tCodigoProveedor = new JTextField(8);
        tRazonSocial     = new JTextField(15);
        tImpuestos       = new JTextField("0");
        tSubtotal        = new JTextField("0.00");
        tTotal           = new JTextField("0.00");

        tSubtotal.setEditable(false);
        tTotal.setEditable(false);

        panel.add(new JLabel("FECHA:"));            panel.add(tFecha);
        panel.add(new JLabel("COD. PROVEEDOR:"));   panel.add(tCodigoProveedor);
        panel.add(new JLabel("RAZON SOCIAL:"));     panel.add(tRazonSocial);
        panel.add(new JLabel("IMPUESTOS ($):"));    panel.add(tImpuestos);
        panel.add(new JLabel("SUBTOTAL:"));         panel.add(tSubtotal);
        panel.add(new JLabel("TOTAL:"));            panel.add(tTotal);

        tImpuestos.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) { recalcularTotales(); }
        });

        return panel;
    }

    private JPanel construirPanelCentro() {
        JPanel centro = new JPanel(new BorderLayout(8, 8));

        // Formulario ítem
        JPanel frmItem = new JPanel(new GridLayout(2, 4, 8, 6));
        frmItem.setBorder(BorderFactory.createTitledBorder("Agregar Producto a la Compra"));

        tCodProducto = new JTextField(8);
        tNomProducto = new JTextField(15);
        tCantidad    = new JTextField(6);
        tCostoUnit   = new JTextField(10);

        frmItem.add(new JLabel("COD. PRODUCTO:")); frmItem.add(tCodProducto);
        frmItem.add(new JLabel("CANTIDAD:"));      frmItem.add(tCantidad);
        frmItem.add(new JLabel("NOMBRE:"));        frmItem.add(tNomProducto);
        frmItem.add(new JLabel("COSTO UNIT.($):")); frmItem.add(tCostoUnit);

        // Botones ítem
        bAgregarItem  = new JButton("Agregar Producto");
        bEliminarItem = new JButton("Eliminar Producto");
        bAgregarItem.addActionListener(this::accionAgregarItem);
        bEliminarItem.addActionListener(this::accionEliminarItem);

        JPanel botonesItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
        botonesItem.add(bAgregarItem);
        botonesItem.add(bEliminarItem);

        JPanel topCentro = new JPanel(new BorderLayout());
        topCentro.add(frmItem,     BorderLayout.CENTER);
        topCentro.add(botonesItem, BorderLayout.SOUTH);

        // Tabla ítems actuales
        String[] colsItems = {"Cod. Producto", "Nombre", "Cantidad", "Costo Unit.", "Subtotal"};
        modeloItems = new DefaultTableModel(colsItems, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaItems = new JTable(modeloItems);
        tablaItems.setRowHeight(22);
        JScrollPane scrollItems = new JScrollPane(tablaItems);
        scrollItems.setBorder(BorderFactory.createTitledBorder("Productos en esta compra"));
        scrollItems.setPreferredSize(new Dimension(600, 120));

        // Tabla historial de compras
        String[] colsCompras = {"Factura N°", "Fecha", "Proveedor", "Subtotal", "Impuestos", "Total"};
        modeloCompras = new DefaultTableModel(colsCompras, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaCompras = new JTable(modeloCompras);
        tablaCompras.setRowHeight(22);
        JScrollPane scrollCompras = new JScrollPane(tablaCompras);
        scrollCompras.setBorder(BorderFactory.createTitledBorder("Historial de Compras"));
        scrollCompras.setPreferredSize(new Dimension(600, 120));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollItems, scrollCompras);
        split.setDividerLocation(140);

        centro.add(topCentro, BorderLayout.NORTH);
        centro.add(split,     BorderLayout.CENTER);
        return centro;
    }

    private JPanel construirPanelBotones() {
        bRegistrar = new JButton("Registrar Compra");
        bAnular    = new JButton("Anular Compra");
        bBuscar    = new JButton("Buscar Compra");
        bLimpiar   = new JButton("Limpiar");
        bVolver    = new JButton(Eventos.VOLVER);

        bRegistrar.setActionCommand(Eventos.cmREGISTRAR);
        bAnular.setActionCommand(Eventos.cmANULAR);
        bBuscar.setActionCommand(Eventos.cmBUSCAR);
        bLimpiar.setActionCommand(Eventos.cmLIMPIAR);

        bRegistrar.addActionListener(evento);
        bAnular.addActionListener(evento);
        bBuscar.addActionListener(evento);
        bLimpiar.addActionListener(evento);
        bVolver.addActionListener(evento);

        JPanel botones = new JPanel(new GridLayout(1, 4, 8, 0));
        botones.setBorder(BorderFactory.createEmptyBorder(6, 0, 2, 0));
        botones.add(bRegistrar);
        botones.add(bAnular);
        botones.add(bBuscar);
        botones.add(bLimpiar);

        JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelVolver.add(bVolver);

        JPanel sur = new JPanel(new BorderLayout());
        sur.add(botones,     BorderLayout.NORTH);
        sur.add(panelVolver, BorderLayout.SOUTH);
        return sur;
    }

    // ── Acciones internas de ítems ────────────────────────────────────────
    private void accionAgregarItem(ActionEvent e) {
        try {
            int    cod      = Integer.parseInt(tCodProducto.getText().trim());
            String nombre   = tNomProducto.getText().trim();
            int    cantidad = Integer.parseInt(tCantidad.getText().trim());
            double costo    = Double.parseDouble(tCostoUnit.getText().replace(",", ".").trim());

            if (nombre.isEmpty()) { JOptionPane.showMessageDialog(this, "Ingrese el nombre del producto"); return; }
            if (cantidad <= 0)    { JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0");  return; }
            if (costo <= 0)       { JOptionPane.showMessageDialog(this, "El costo unitario debe ser mayor a 0"); return; }

            itemCompraDto item = new itemCompraDto(cod);
            item.setNombreProducto(nombre);
            item.setCantidad(cantidad);
            item.setCostoUnitario(costo);
            item.setSubtotal(cantidad * costo);
            itemsActuales.add(item);

            modeloItems.addRow(new Object[]{
                cod, nombre, cantidad,
                String.format("%.2f", costo),
                String.format("%.2f", item.getSubtotal())
            });

            limpiarCamposItem();
            recalcularTotales();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique los campos numericos del producto");
        }
    }

    private void accionEliminarItem(ActionEvent e) {
        int fila = tablaItems.getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(this, "Seleccione un producto de la lista"); return; }
        itemsActuales.remove(fila);
        modeloItems.removeRow(fila);
        recalcularTotales();
    }

    // ── Cálculo de totales ────────────────────────────────────────────────
    private void recalcularTotales() {
        double subtotal = itemsActuales.stream()
                .mapToDouble(itemCompraDto::getSubtotal).sum();
        double impuestos = 0;
        try { impuestos = Double.parseDouble(tImpuestos.getText().replace(",", ".")); }
        catch (NumberFormatException ignored) {}
        tSubtotal.setText(String.format("%.2f", subtotal));
        tTotal.setText(String.format("%.2f", subtotal + impuestos));
    }

    // ── Métodos públicos ──────────────────────────────────────────────────
    public void poblarTabla(List<compraDto> compras) {
        modeloCompras.setRowCount(0);
        for (compraDto c : compras) {
            modeloCompras.addRow(new Object[]{
                c.getNumeroFacturaProveedor(),
                c.getFecha(),
                c.getRazonSocialProveedor(),
                String.format("%.2f", c.getSubtotal()),
                String.format("%.2f", c.getImpuestos()),
                String.format("%.2f", c.getTotal())
            });
        }
    }

    public void limpiarCampos() {
        tFecha.setText("");
        tCodigoProveedor.setText("");
        tRazonSocial.setText("");
        tImpuestos.setText("0");
        tSubtotal.setText("0.00");
        tTotal.setText("0.00");
        limpiarCamposItem();
        itemsActuales.clear();
        modeloItems.setRowCount(0);
    }

    private void limpiarCamposItem() {
        tCodProducto.setText("");
        tNomProducto.setText("");
        tCantidad.setText("");
        tCostoUnit.setText("");
    }

    public compraDto getDatosCompra() {
        String fecha = tFecha.getText().trim();
        if (fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La fecha es requerida");
            return null;
        }
        String codProvTxt = tCodigoProveedor.getText().trim();
        if (codProvTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El codigo del proveedor es requerido");
            return null;
        }
        if (itemsActuales.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe agregar al menos un producto a la compra");
            return null;
        }
        try {
            int    codProv   = Integer.parseInt(codProvTxt);
            double impuestos = Double.parseDouble(tImpuestos.getText().replace(",", ".").trim());

            compraDto compra = new compraDto();
            compra.setFecha(fecha);
            compra.setCodigoProveedor(codProv);
            compra.setRazonSocialProveedor(tRazonSocial.getText().trim());
            compra.setImpuestos(impuestos);
            compra.setProductos(new ArrayList<>(itemsActuales));
            return compra;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verifique los campos numericos del encabezado");
            return null;
        }
    }

    public int getNumeroFacturaSeleccionado() {
        int fila = tablaCompras.getSelectedRow();
        if (fila >= 0) return (int) modeloCompras.getValueAt(fila, 0);
        JOptionPane.showMessageDialog(this, "Seleccione una compra de la tabla");
        return -1;
    }
}