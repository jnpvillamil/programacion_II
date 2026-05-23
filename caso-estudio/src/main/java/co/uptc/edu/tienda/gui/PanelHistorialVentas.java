package co.uptc.edu.tienda.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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

    public PanelHistorialVentas(Evento evento) {

        // Lista vacía hasta que VentanaPrincipal llame refrescar()
        this.listaVentas = new ArrayList<>();

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));

        // =====================================
        // TITULO
        // =====================================
        JLabel titulo = new JLabel("HISTORIAL DE VENTAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

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
        modeloDetalle.addColumn("Subtotal");

        tablaDetalle = new JTable(modeloDetalle);
        tablaDetalle.setRowHeight(25);

        JScrollPane scrollDetalle = new JScrollPane(tablaDetalle);
        scrollDetalle.setBorder(BorderFactory.createTitledBorder("Detalle de la venta seleccionada"));

        // =====================================
        // PANEL CENTRAL
        // =====================================
        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.setBackground(new Color(240, 240, 240));
        centro.add(scrollVentas, BorderLayout.CENTER);
        centro.add(scrollDetalle, BorderLayout.SOUTH);
        scrollDetalle.setPreferredSize(new java.awt.Dimension(0, 180));

        add(centro, BorderLayout.CENTER);

        // =====================================
        // BOTON ANULAR
        // =====================================
        JPanel inferior = new JPanel();
        inferior.setBackground(new Color(240, 240, 240));

        btnAnular = new JButton("Anular Venta");
        btnAnular.setBackground(new Color(192, 57, 43));
        btnAnular.setForeground(Color.WHITE);
        btnAnular.setFont(new Font("Arial", Font.BOLD, 13));
        btnAnular.setActionCommand(Evento.LANZAR_ANULAR_VTA);
        btnAnular.addActionListener(evento);

        inferior.add(btnAnular);
        add(inferior, BorderLayout.SOUTH);

        // =====================================
        // EVENTO SELECCION FILA
        // =====================================
        tablaVentas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDetalle();
            }
        });
    }

    // =====================================
    // POBLAR TABLA VENTAS
    // =====================================
    private void poblarTabla() {
        modeloVentas.setRowCount(0);
        for (Venta v : listaVentas) {
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

    // =====================================
    // MOSTRAR DETALLE DE VENTA SELECCIONADA
    // =====================================
    private void mostrarDetalle() {
        int fila = tablaVentas.getSelectedRow();
        if (fila == -1) return;

        modeloDetalle.setRowCount(0);
        Venta venta = listaVentas.get(fila);

        if (venta.getDetalles() != null) {
            for (DetalleVenta d : venta.getDetalles()) {
                modeloDetalle.addRow(new Object[]{
                    d.getProducto().getNombreProducto(),
                    d.getCantidad(),
                    String.format("$%.2f", d.getProducto().getPrecioVenta()),
                    String.format("$%.2f", d.getSubtotal())
                });
            }
        }
    }

    // =====================================
    // GETTERS PARA VentanaPrincipal
    // =====================================
    public String getFacturaSeleccionada() {
        int fila = tablaVentas.getSelectedRow();
        if (fila == -1) return null;
        return modeloVentas.getValueAt(fila, 0).toString();
    }

    // VentanaPrincipal llama esto cada vez que se muestra el panel
    public void refrescar(List<Venta> ventas) {
        this.listaVentas = ventas;
        poblarTabla();
        modeloDetalle.setRowCount(0);
    }
}