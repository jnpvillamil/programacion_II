package co.uptc.edu.tienda.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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

import co.uptc.edu.tienda.modelo.Compra;
import co.uptc.edu.tienda.modelo.DetalleCompra;

public class PanelHistorialCompra extends JPanel {

    private JTable tablaCompras;
    private JTable tablaDetalle;
    private DefaultTableModel modeloCompras;
    private DefaultTableModel modeloDetalle;
    private JTextField txtBuscar;
    private JTextField txtDesde;
    private JTextField txtHasta;
    private JLabel lblTotalCompras;
    private List<Compra> listaCompras;

    public PanelHistorialCompra(Evento evento) {

        this.listaCompras = new ArrayList<>();

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));

        // =====================================
        // TITULO
        // =====================================
        JLabel titulo = new JLabel("HISTORIAL DE COMPRAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        // =====================================
        // BUSCADOR POR PROVEEDOR
        // =====================================
        JPanel panelBuscar = new JPanel();
        panelBuscar.setBorder(BorderFactory.createTitledBorder("Buscar proveedor"));
        panelBuscar.setBackground(new Color(240, 240, 240));

        txtBuscar = new JTextField(20);
        txtBuscar.setToolTipText("Razón social o NIT del proveedor");

        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { filtrar(); }
            public void removeUpdate(DocumentEvent e)  { filtrar(); }
            public void changedUpdate(DocumentEvent e) { filtrar(); }
        });

        panelBuscar.add(new JLabel("Razón social o NIT:"));
        panelBuscar.add(txtBuscar);

        // =====================================
        // FILTRO DE FECHAS
        // =====================================
        JPanel filtro = new JPanel();
        filtro.setBorder(BorderFactory.createTitledBorder("Filtrar por fecha"));
        filtro.setBackground(new Color(240, 240, 240));

        txtDesde = new JTextField(10);
        txtHasta = new JTextField(10);
        txtDesde.setToolTipText("yyyy-MM-dd");
        txtHasta.setToolTipText("yyyy-MM-dd");

        JButton btnFiltrar = new JButton("Filtrar");
        JButton btnVerTodas = new JButton("Ver todas");

        btnFiltrar.addActionListener(e -> filtrar());

        btnVerTodas.addActionListener(e -> {
            txtDesde.setText("");
            txtHasta.setText("");
            txtBuscar.setText("");
            filtrar();
        });

        filtro.add(new JLabel("Desde (yyyy-MM-dd):"));
        filtro.add(txtDesde);
        filtro.add(new JLabel("Hasta (yyyy-MM-dd):"));
        filtro.add(txtHasta);
        filtro.add(btnFiltrar);
        filtro.add(btnVerTodas);

        // =====================================
        // PANEL NORTE: título + buscador + fechas
        // =====================================
        JPanel norte = new JPanel(new BorderLayout());
        norte.setBackground(new Color(240, 240, 240));
        norte.add(titulo, BorderLayout.NORTH);
        norte.add(panelBuscar, BorderLayout.CENTER);
        norte.add(filtro, BorderLayout.SOUTH);
        add(norte, BorderLayout.NORTH);

        // =====================================
        // TABLA COMPRAS
        // =====================================
        modeloCompras = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        modeloCompras.addColumn("Factura");
        modeloCompras.addColumn("Fecha");
        modeloCompras.addColumn("Proveedor");
        modeloCompras.addColumn("NIT");
        modeloCompras.addColumn("Total");

        tablaCompras = new JTable(modeloCompras);
        tablaCompras.setRowHeight(25);
        tablaCompras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollCompras = new JScrollPane(tablaCompras);
        scrollCompras.setBorder(BorderFactory.createTitledBorder("Compras al proveedor"));

        // =====================================
        // TABLA DETALLE
        // =====================================
        modeloDetalle = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        modeloDetalle.addColumn("Producto");
        modeloDetalle.addColumn("Cantidad");
        modeloDetalle.addColumn("Precio Compra");
        modeloDetalle.addColumn("Subtotal");

        tablaDetalle = new JTable(modeloDetalle);
        tablaDetalle.setRowHeight(25);

        JScrollPane scrollDetalle = new JScrollPane(tablaDetalle);
        scrollDetalle.setBorder(BorderFactory.createTitledBorder("Detalle de la compra seleccionada"));
        scrollDetalle.setPreferredSize(new Dimension(0, 180));

        // =====================================
        // PANEL CENTRAL
        // =====================================
        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.setBackground(new Color(240, 240, 240));
        centro.add(scrollCompras, BorderLayout.CENTER);
        centro.add(scrollDetalle, BorderLayout.SOUTH);
        add(centro, BorderLayout.CENTER);

        // =====================================
        // PANEL INFERIOR: total + compras del día
        // =====================================
        JPanel inferior = new JPanel();
        inferior.setBackground(new Color(240, 240, 240));

        lblTotalCompras = new JLabel("Total compras: $0.00");
        lblTotalCompras.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalCompras.setForeground(new Color(192, 57, 43));

        JButton btnHoy = new JButton("Compras del día");
        btnHoy.setBackground(new Color(41, 128, 185));
        btnHoy.setForeground(Color.WHITE);
        btnHoy.setFont(new Font("Arial", Font.BOLD, 13));
        btnHoy.setFocusPainted(false);
        btnHoy.addActionListener(e -> mostrarComprasDeHoy());

        inferior.add(lblTotalCompras);
        inferior.add(btnHoy);
        add(inferior, BorderLayout.SOUTH);

        // =====================================
        // EVENTO SELECCIÓN FILA
        // =====================================
        tablaCompras.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDetalle();
            }
        });
    }

    // =====================================
    // FILTRAR POR PROVEEDOR Y/O FECHA
    // =====================================
    private void filtrar() {
        String texto = txtBuscar.getText().trim().toLowerCase();
        String desde = txtDesde.getText().trim();
        String hasta = txtHasta.getText().trim();

        modeloCompras.setRowCount(0);
        modeloDetalle.setRowCount(0);

        for (Compra c : listaCompras) {
            if (c.getProveedor() == null) continue;

            // Filtro por proveedor
            String razonSocial = c.getProveedor().getRazonSocial().toLowerCase();
            String nit         = c.getProveedor().getNit().toLowerCase();
            boolean cumpleProveedor = texto.isEmpty()
                    || razonSocial.contains(texto)
                    || nit.contains(texto);

            // Filtro por fecha
            String fechaSolo = c.getFechaCompra().substring(0, 10);
            boolean cumpleFecha = true;
            if (!desde.isEmpty() && fechaSolo.compareTo(desde) < 0) cumpleFecha = false;
            if (!hasta.isEmpty() && fechaSolo.compareTo(hasta)  > 0) cumpleFecha = false;

            if (cumpleProveedor && cumpleFecha) {
                modeloCompras.addRow(new Object[]{
                    c.getNumeroFactura(),
                    c.getFechaCompra(),
                    c.getProveedor().getRazonSocial(),
                    c.getProveedor().getNit(),
                    String.format("$%.2f", c.getTotal())
                });
            }
        }
        actualizarTotalVisible();
    }

    // =====================================
    // COMPRAS DEL DÍA (atajo rápido)
    // =====================================
    private void mostrarComprasDeHoy() {
        String hoy = LocalDate.now().toString();
        txtDesde.setText(hoy);
        txtHasta.setText(hoy);
        txtBuscar.setText("");
        filtrar();
    }

    // =====================================
    // TOTAL VISIBLE
    // =====================================
    private void actualizarTotalVisible() {
        double total = 0;
        for (int i = 0; i < modeloCompras.getRowCount(); i++) {
            String factura = modeloCompras.getValueAt(i, 0).toString();
            for (Compra c : listaCompras) {
                if (c.getNumeroFactura().equals(factura)) {
                    total += c.getTotal();
                    break;
                }
            }
        }
        lblTotalCompras.setText("Total compras: $" + String.format("%.2f", total));
    }

    // =====================================
    // DETALLE DE COMPRA SELECCIONADA
    // =====================================
    private void mostrarDetalle() {
        int fila = tablaCompras.getSelectedRow();
        if (fila == -1) return;

        modeloDetalle.setRowCount(0);

        String factura = modeloCompras.getValueAt(fila, 0).toString();
        Compra compra = null;
        for (Compra c : listaCompras) {
            if (c.getNumeroFactura().equals(factura)) {
                compra = c;
                break;
            }
        }

        if (compra == null || compra.getDetalles() == null) return;

        for (DetalleCompra d : compra.getDetalles()) {
            modeloDetalle.addRow(new Object[]{
                d.getProducto().getNombreProducto(),
                d.getCantidad(),
                String.format("$%.2f", d.getPrecioCompra()),
                String.format("$%.2f", d.getSubtotal())
            });
        }
    }

    // =====================================
    // REFRESCAR DESDE VentanaPrincipal
    // =====================================
    public void refrescar(List<Compra> compras) {
        this.listaCompras = compras;
        txtBuscar.setText("");
        txtDesde.setText("");
        txtHasta.setText("");
        modeloDetalle.setRowCount(0);
        filtrar();
    }
}