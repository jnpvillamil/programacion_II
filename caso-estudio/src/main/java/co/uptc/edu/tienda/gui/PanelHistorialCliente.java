package co.uptc.edu.tienda.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.tienda.modelo.DetalleVenta;
import co.uptc.edu.tienda.modelo.Venta;

public class PanelHistorialCliente extends JPanel {

    private JTable tablaVentas;
    private JTable tablaDetalle;
    private DefaultTableModel modeloVentas;
    private DefaultTableModel modeloDetalle;
    private JTextField txtBuscar;
    private List<Venta> listaVentas;

    public PanelHistorialCliente(Evento evento) {

        this.listaVentas = new ArrayList<>();

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));

        // =====================================
        // TITULO
        // =====================================
        JLabel titulo = new JLabel("HISTORIAL DE COMPRAS POR CLIENTE", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        // =====================================
        // BUSCADOR
        // =====================================
        JPanel panelBuscar = new JPanel();
        panelBuscar.setBorder(BorderFactory.createTitledBorder("Buscar cliente"));
        panelBuscar.setBackground(new Color(240, 240, 240));

        txtBuscar = new JTextField(25);
        txtBuscar.setToolTipText("Nombre o número de documento");

        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { filtrar(); }
            public void removeUpdate(DocumentEvent e)  { filtrar(); }
            public void changedUpdate(DocumentEvent e) { filtrar(); }
        });

        panelBuscar.add(new JLabel("Nombre o documento:"));
        panelBuscar.add(txtBuscar);

        // =====================================
        // PANEL NORTE: título + buscador
        // =====================================
        JPanel norte = new JPanel(new BorderLayout());
        norte.setBackground(new Color(240, 240, 240));
        norte.add(titulo, BorderLayout.NORTH);
        norte.add(panelBuscar, BorderLayout.SOUTH);
        add(norte, BorderLayout.NORTH);

        // =====================================
        // TABLA VENTAS DEL CLIENTE
        // =====================================
        modeloVentas = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        modeloVentas.addColumn("Factura");
        modeloVentas.addColumn("Fecha");
        modeloVentas.addColumn("Cliente");
        modeloVentas.addColumn("Documento");
        modeloVentas.addColumn("Forma Pago");
        modeloVentas.addColumn("Total");
        modeloVentas.addColumn("Estado");

        tablaVentas = new JTable(modeloVentas);
        tablaVentas.setRowHeight(25);
        tablaVentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollVentas = new JScrollPane(tablaVentas);
        scrollVentas.setBorder(BorderFactory.createTitledBorder("Ventas del cliente"));

        // =====================================
        // TABLA DETALLE
        // =====================================
        modeloDetalle = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        modeloDetalle.addColumn("Producto");
        modeloDetalle.addColumn("Cantidad");
        modeloDetalle.addColumn("Precio Unitario");
        modeloDetalle.addColumn("Impuestos");
        modeloDetalle.addColumn("Subtotal");

        tablaDetalle = new JTable(modeloDetalle);
        tablaDetalle.setRowHeight(25);

        JScrollPane scrollDetalle = new JScrollPane(tablaDetalle);
        scrollDetalle.setBorder(BorderFactory.createTitledBorder("Detalle de la venta seleccionada"));
        scrollDetalle.setPreferredSize(new Dimension(0, 180));

        // =====================================
        // PANEL CENTRAL
        // =====================================
        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.setBackground(new Color(240, 240, 240));
        centro.add(scrollVentas, BorderLayout.CENTER);
        centro.add(scrollDetalle, BorderLayout.SOUTH);
        add(centro, BorderLayout.CENTER);

        // =====================================
        // EVENTO SELECCIÓN FILA
        // =====================================
        tablaVentas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDetalle();
            }
        });
    }

    // =====================================
    // FILTRAR POR NOMBRE O DOCUMENTO
    // =====================================
    private void filtrar() {
        String texto = txtBuscar.getText().trim().toLowerCase();

        modeloVentas.setRowCount(0);
        modeloDetalle.setRowCount(0);

        if (texto.isEmpty()) return; // no muestra nada hasta que el usuario escriba

        for (Venta v : listaVentas) {
            if (v.getCliente() == null) continue;

            String nombre   = v.getCliente().getNombreCompleto().toLowerCase();
            String documento = String.valueOf(v.getCliente().getNumeroDocumento());

            if (nombre.contains(texto) || documento.contains(texto)) {
                modeloVentas.addRow(new Object[]{
                    v.getNumeroFactura(),
                    v.getFechaHora(),
                    v.getCliente().getNombreCompleto(),
                    v.getCliente().getNumeroDocumento(),
                    v.getFormaPago(),
                    String.format("$%.2f", v.getTotal()),
                    v.getEstado()
                });
            }
        }
    }

    // =====================================
    // DETALLE DE VENTA SELECCIONADA
    // =====================================
    private void mostrarDetalle() {
        int fila = tablaVentas.getSelectedRow();
        if (fila == -1) return;

        modeloDetalle.setRowCount(0);

        String factura = modeloVentas.getValueAt(fila, 0).toString();
        Venta venta = null;
        for (Venta v : listaVentas) {
            if (v.getNumeroFactura().equals(factura)) {
                venta = v;
                break;
            }
        }

        if (venta == null || venta.getDetalles() == null) return;

        for (DetalleVenta d : venta.getDetalles()) {
            modeloDetalle.addRow(new Object[]{
                d.getProducto().getNombreProducto(),
                d.getCantidad(),
                String.format("$%.2f", d.getPrecioUnitario()),
                String.format("$%.2f", d.getImpuestos()),
                String.format("$%.2f", d.getSubtotal())
            });
        }
    }

    // =====================================
    // REFRESCAR DESDE VentanaPrincipal
    // =====================================
    public void refrescar(List<Venta> ventas) {
        this.listaVentas = ventas;
        txtBuscar.setText("");
        modeloVentas.setRowCount(0);
        modeloDetalle.setRowCount(0);
    }
}