package co.edu.uptc.sistienda.ventas.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.sistienda.gui.Evento;
import co.edu.uptc.sistienda.modelo.Venta;

public class PanelVentasRegistradas extends JPanel {

    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter FORMATO_SOLO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private JTable            tablaListadoVentas;
    private DefaultTableModel modeloTablaVentas;
    private JTextField        campoBusquedaPorFecha;
    private JButton           botonConsultarPorFecha;
    private JButton           botonMostrarTodasLasVentas;
    private JButton           botonCrearNuevaVenta;
    private JButton           botonVerFacturaSeleccionada;
    private JButton           botonAnularVentaSeleccionada;
    private JButton           botonRegistrarDevolucion;

    public PanelVentasRegistradas(Evento evento) {
        setLayout(new BorderLayout());

        JPanel panelEncabezado = new JPanel(new BorderLayout());
        panelEncabezado.setBorder(BorderFactory.createEmptyBorder(6, 8, 4, 8));
        panelEncabezado.add(new JLabel("Ventas - Listado"), BorderLayout.NORTH);

        // Fila de filtro por fecha
        JPanel filaFiltroPorFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
        filaFiltroPorFecha.add(new JLabel("Fecha dd/mm/aaaa"));

        campoBusquedaPorFecha = new JTextField(10);
        campoBusquedaPorFecha.setText(LocalDate.now().format(FORMATO_SOLO_FECHA));
        filaFiltroPorFecha.add(campoBusquedaPorFecha);

        botonConsultarPorFecha = new JButton("Consultar");
        botonConsultarPorFecha.setActionCommand(Evento.CONSULTAR_VENTAS_POR_FECHA);
        botonConsultarPorFecha.addActionListener(evento);
        filaFiltroPorFecha.add(botonConsultarPorFecha);

        botonMostrarTodasLasVentas = new JButton("Mostrar todas");
        botonMostrarTodasLasVentas.setActionCommand(Evento.MOSTRAR_TODAS_VENTAS);
        botonMostrarTodasLasVentas.addActionListener(evento);
        filaFiltroPorFecha.add(botonMostrarTodasLasVentas);

        panelEncabezado.add(filaFiltroPorFecha, BorderLayout.CENTER);

        // Botones de acciones sobre ventas
        JPanel grupoBotonesAccion = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));

        botonCrearNuevaVenta = new JButton("+ Nueva venta");
        botonCrearNuevaVenta.setActionCommand(Evento.MENU_REGISTRAR_VENTA);
        botonCrearNuevaVenta.addActionListener(evento);
        grupoBotonesAccion.add(botonCrearNuevaVenta);

     
        botonAnularVentaSeleccionada = new JButton("Anular Venta");
        botonAnularVentaSeleccionada.setActionCommand(Evento.ANULAR_VENTA);
        botonAnularVentaSeleccionada.addActionListener(evento);
        grupoBotonesAccion.add(botonAnularVentaSeleccionada);


        panelEncabezado.add(grupoBotonesAccion, BorderLayout.SOUTH);
        add(panelEncabezado, BorderLayout.NORTH);

        // Tabla de ventas registradas
        modeloTablaVentas = new DefaultTableModel() {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        modeloTablaVentas.addColumn("N° Factura");
        modeloTablaVentas.addColumn("Fecha y Hora");
        modeloTablaVentas.addColumn("Cliente");
        modeloTablaVentas.addColumn("Subtotal");
        modeloTablaVentas.addColumn("IVA");
        modeloTablaVentas.addColumn("Total");
        modeloTablaVentas.addColumn("Forma de Pago");
        modeloTablaVentas.addColumn("Estado");

        tablaListadoVentas = new JTable(modeloTablaVentas);
        tablaListadoVentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaListadoVentas.getTableHeader().setReorderingAllowed(false);
        tablaListadoVentas.setRowHeight(22);

        add(new JScrollPane(tablaListadoVentas), BorderLayout.CENTER);
    }

    public void poblarTabla(List<Venta> ventasAMostrar) {
        modeloTablaVentas.setRowCount(0);
        for (Venta ventaRegistrada : ventasAMostrar) {
            String nombreCliente = ventaRegistrada.getCliente() != null
                    ? ventaRegistrada.getCliente().getNombreCompletoORazonSocial()
                    : "-";
            modeloTablaVentas.addRow(new Object[]{
                ventaRegistrada.getNumeroFactura(),
                ventaRegistrada.getFechaHora().format(FORMATO_FECHA_HORA),
                nombreCliente,
                "$" + String.format("%,.0f", ventaRegistrada.getSubtotal()),
                "$" + String.format("%,.0f", ventaRegistrada.getIva()),
                "$" + String.format("%,.0f", ventaRegistrada.getTotal()),
                ventaRegistrada.getFormaPago().getDescripcion(),
                ventaRegistrada.isAnulada() ? "ANULADA" : "VÁLIDA"
            });
        }
    }

    public String obtenerNumeroFacturaSeleccionada() {
        int filaSeleccionada = tablaListadoVentas.getSelectedRow();
        return filaSeleccionada < 0 ? null : (String) modeloTablaVentas.getValueAt(filaSeleccionada, 0);
    }

    public LocalDate obtenerFechaDeBusqueda() {
        try {
            return LocalDate.parse(
                campoBusquedaPorFecha.getText().trim(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );
        } catch (DateTimeParseException errorFormato) {
            JOptionPane.showMessageDialog(
                this,
                "Formato inválido. Use dd/mm/aaaa",
                "Error",
                JOptionPane.WARNING_MESSAGE
            );
        }
        return null;
    }
}
