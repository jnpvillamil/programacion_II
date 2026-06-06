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
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.tienda.modelo.DetalleVenta;
import co.uptc.edu.tienda.modelo.Venta;

public class PanelHistorialVentas extends JPanel {

    private JTable tablaVentas;
    private JTable tablaDetalle;
    private DefaultTableModel modeloVentas;
    private DefaultTableModel modeloDetalle;
    private JButton btnAnular;
    private List<Venta> listaVentas;
    private JTextField txtDesde;
    private JTextField txtHasta;
    private JLabel lblTotalDia;

    public PanelHistorialVentas(Evento evento) {

        this.listaVentas = new ArrayList<>();

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));

        // =====================================
        // TITULO
        // =====================================
        JLabel titulo = new JLabel("HISTORIAL DE VENTAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

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

        btnFiltrar.addActionListener(e -> filtrarPorFecha());

        btnVerTodas.addActionListener(e -> {
            txtDesde.setText("");
            txtHasta.setText("");
            poblarTabla();
        });

        filtro.add(new JLabel("Desde (yyyy-MM-dd):"));
        filtro.add(txtDesde);
        filtro.add(new JLabel("Hasta (yyyy-MM-dd):"));
        filtro.add(txtHasta);
        filtro.add(btnFiltrar);
        filtro.add(btnVerTodas);

        // =====================================
        // PANEL NORTE: título + filtro juntos
        // =====================================
        JPanel norte = new JPanel(new BorderLayout());
        norte.setBackground(new Color(240, 240, 240));
        norte.add(titulo, BorderLayout.NORTH);
        norte.add(filtro, BorderLayout.SOUTH);
        add(norte, BorderLayout.NORTH);

        // =====================================
        // TABLA VENTAS
        // =====================================
        modeloVentas = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        modeloVentas.addColumn("Factura");
        modeloVentas.addColumn("Fecha");
        modeloVentas.addColumn("Cliente");
        modeloVentas.addColumn("Forma Pago");
        modeloVentas.addColumn("Total");
        modeloVentas.addColumn("Estado");

        tablaVentas = new JTable(modeloVentas);
        tablaVentas.setRowHeight(25);
        tablaVentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollVentas = new JScrollPane(tablaVentas);
        scrollVentas.setBorder(BorderFactory.createTitledBorder("Ventas"));

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
        // PANEL INFERIOR: Ventas del día + Anular
        // =====================================
        
        lblTotalDia = new JLabel("Total del día: $0.00");
        lblTotalDia.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalDia.setForeground(new Color(39, 174, 96));
        JPanel inferior = new JPanel();
        inferior.setBackground(new Color(240, 240, 240));

        JButton btnHoy = new JButton("Ventas del día");
        btnHoy.setBackground(new Color(41, 128, 185));
        btnHoy.setForeground(Color.WHITE);
        btnHoy.setFont(new Font("Arial", Font.BOLD, 13));
        btnHoy.setFocusPainted(false);
        btnHoy.addActionListener(e -> mostrarVentasDeHoy());

        btnAnular = new JButton("Anular Venta");
        btnAnular.setBackground(new Color(192, 57, 43));
        btnAnular.setForeground(Color.WHITE);
        btnAnular.setFont(new Font("Arial", Font.BOLD, 13));
        btnAnular.setFocusPainted(false);
        btnAnular.setActionCommand(Evento.LANZAR_ANULAR_VTA);
        btnAnular.addActionListener(evento);
        
        inferior.add(lblTotalDia);
        inferior.add(btnHoy);
        inferior.add(btnAnular);
        add(inferior, BorderLayout.SOUTH);

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
    // POBLAR TABLA (todas las ventas)
    // =====================================
    private void poblarTabla() {
        modeloVentas.setRowCount(0);
        modeloDetalle.setRowCount(0);
        for (Venta v : listaVentas) {
            String nombreCliente;
            if (v.getCliente() != null) {
                nombreCliente = v.getCliente().getNombreCompleto();
            } else {
                nombreCliente = "N/A";
            }
            modeloVentas.addRow(new Object[]{
                v.getNumeroFactura(),
                v.getFechaHora(),
                nombreCliente,
                v.getFormaPago(),
                String.format("$%.2f", v.getTotal()),
                v.getEstado()
            });
        }
    }

    // =====================================
    // FILTRAR POR RANGO DE FECHAS
    // =====================================
    private void filtrarPorFecha() {
        String desde = txtDesde.getText().trim();
        String hasta  = txtHasta.getText().trim();

        modeloVentas.setRowCount(0);
        modeloDetalle.setRowCount(0);

        for (Venta v : listaVentas) {
            // Recortamos a yyyy-MM-dd para no comparar la hora
            String fechaSolo = v.getFechaHora().substring(0, 10);
            boolean cumple = true;

            if (!desde.isEmpty() && fechaSolo.compareTo(desde) < 0) cumple = false;
            if (!hasta.isEmpty()  && fechaSolo.compareTo(hasta)  > 0) cumple = false;

            if (cumple) {
                modeloVentas.addRow(new Object[]{
                    v.getNumeroFactura(),
                    v.getFechaHora(),
                    v.getCliente() != null ? v.getCliente().getNombreCompleto() : "N/A",
                    v.getFormaPago(),
                    String.format("$%.2f", v.getTotal()),
                    v.getEstado()
                });
            }
        }
        actualizarTotalVisible();
    }

    // =====================================
    // VENTAS DEL DÍA (atajo rápido)
    // =====================================
    private void mostrarVentasDeHoy() {
        String hoy = LocalDate.now().toString(); // "2026-05-24"
        txtDesde.setText(hoy);
        txtHasta.setText(hoy);
        filtrarPorFecha();
        actualizarTotalVisible();
    }

    // =====================================
    // DETALLE DE VENTA SELECCIONADA
    // =====================================
    private void mostrarDetalle() {
        int fila = tablaVentas.getSelectedRow();
        if (fila == -1) return;

        modeloDetalle.setRowCount(0);

        // Buscamos la venta por número de factura para que funcione
        // tanto con poblarTabla() como con filtrarPorFecha()
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
    
    private void actualizarTotalVisible() {
        // Obtener las facturas visibles en la tabla
        double total = 0;
        for (int i = 0; i < modeloVentas.getRowCount(); i++) {
            String factura = modeloVentas.getValueAt(i, 0).toString();
            for (Venta v : listaVentas) {
                if (v.getNumeroFactura().equals(factura)) {
                    total += v.getTotal();
                    break;
                }
            }
        }
        lblTotalDia.setText("Total vendido: $" + String.format("%.2f", total));
    }

    // =====================================
    // GETTERS PARA VentanaPrincipal
    // =====================================
    public String getFacturaSeleccionada() {
        int fila = tablaVentas.getSelectedRow();
        if (fila == -1) return null;
        return modeloVentas.getValueAt(fila, 0).toString();
    }

    public void refrescar(List<Venta> ventas) {
        this.listaVentas = ventas;
        mostrarVentasDeHoy(); // ← en lugar de poblarTabla()
        modeloDetalle.setRowCount(0);
    }
}