package co.edu.uptc.sistienda.ventas.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.sistienda.gui.Evento;
import co.edu.uptc.sistienda.modelo.Cliente;
import co.edu.uptc.sistienda.modelo.DetalleVenta;
import co.edu.uptc.sistienda.modelo.enums.FormaPagoEnum;

public class PanelRegistrarVenta extends JPanel {

    private JTextField              campoNumeroFactura;
    private JTextField              campoFechaHora;
    private JComboBox<String>       comboSelectorCliente;
    private JTable                  tablaDetalleVenta;
    private DefaultTableModel       modeloTablaDetalle;
    private JLabel                  etiquetaValorSubtotal;
    private JLabel                  etiquetaValorIva;
    private JLabel                  etiquetaValorTotal;
    private JComboBox<FormaPagoEnum> comboSelectorFormaPago;
    private JButton                 botonAgregarProducto;
    private JButton                 botonQuitarProducto;
    private JButton                 botonConfirmarVenta;
    private JButton                 botonCancelarVenta;
    private JButton                 botonIrAVentasRegistradas;
    private List<DetalleVenta>      productosAgregadosALaVenta = new ArrayList<>();

    public PanelRegistrarVenta(Evento evento) {
        setLayout(new BorderLayout());

        JPanel panelEncabezado = new JPanel(new BorderLayout());
        JLabel tituloFormulario = new JLabel("Ventas - Registrar nueva venta");
        tituloFormulario.setBorder(BorderFactory.createEmptyBorder(6, 8, 4, 8));
        panelEncabezado.add(tituloFormulario, BorderLayout.NORTH);
        panelEncabezado.add(construirPanelDatosVenta(), BorderLayout.CENTER);
        panelEncabezado.add(construirBarraBotonesProducto(evento), BorderLayout.SOUTH);

        add(panelEncabezado, BorderLayout.NORTH);
        add(construirTablaDetalleVenta(), BorderLayout.CENTER);
        add(construirPanelTotalesYAcciones(evento), BorderLayout.SOUTH);
    }

    private JPanel construirPanelDatosVenta() {
        JPanel panelCamposEncabezado = new JPanel(new GridLayout(2, 4, 6, 4));
        panelCamposEncabezado.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        panelCamposEncabezado.add(new JLabel("N° FACTURA"));
        panelCamposEncabezado.add(new JLabel("FECHA"));
        panelCamposEncabezado.add(new JLabel("CLIENTE"));
        panelCamposEncabezado.add(new JLabel("FORMA DE PAGO"));

        campoNumeroFactura = new JTextField();
        campoNumeroFactura.setEditable(false);

        campoFechaHora = new JTextField();
        campoFechaHora.setEditable(false);

        comboSelectorCliente = new JComboBox<>();
        comboSelectorFormaPago = new JComboBox<>(FormaPagoEnum.values());

        panelCamposEncabezado.add(campoNumeroFactura);
        panelCamposEncabezado.add(campoFechaHora);
        panelCamposEncabezado.add(comboSelectorCliente);
        panelCamposEncabezado.add(comboSelectorFormaPago);

        return panelCamposEncabezado;
    }

    private JPanel construirBarraBotonesProducto(Evento evento) {
        JPanel barraProductos = new JPanel(new BorderLayout());
        barraProductos.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        barraProductos.add(new JLabel("PRODUCTOS VENDIDOS"), BorderLayout.WEST);

        JPanel grupoBotonesProducto = new JPanel();

        botonQuitarProducto = new JButton("Quitar seleccionado");
        botonQuitarProducto.addActionListener(e -> quitarProductoSeleccionado());

        botonAgregarProducto = new JButton("+ Agregar Producto");
        botonAgregarProducto.setActionCommand(Evento.ABRIR_SELECTOR_PRODUCTO);
        botonAgregarProducto.addActionListener(evento);

        grupoBotonesProducto.add(botonQuitarProducto);
        grupoBotonesProducto.add(botonAgregarProducto);
        barraProductos.add(grupoBotonesProducto, BorderLayout.EAST);

        return barraProductos;
    }

    private JScrollPane construirTablaDetalleVenta() {
        modeloTablaDetalle = new DefaultTableModel() {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        modeloTablaDetalle.addColumn("CÓDIGO");
        modeloTablaDetalle.addColumn("PRODUCTO");
        modeloTablaDetalle.addColumn("IMPUESTO");
        modeloTablaDetalle.addColumn("CANTIDAD");
        modeloTablaDetalle.addColumn("PRECIO UNIT.");
        modeloTablaDetalle.addColumn("DTO%");
        modeloTablaDetalle.addColumn("SUBTOTAL");

        tablaDetalleVenta = new JTable(modeloTablaDetalle);
        tablaDetalleVenta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaDetalleVenta.getTableHeader().setReorderingAllowed(false);
        tablaDetalleVenta.setRowHeight(24);

        JScrollPane scrollTabla = new JScrollPane(tablaDetalleVenta);
        scrollTabla.setPreferredSize(new Dimension(0, 160));
        return scrollTabla;
    }

    private JPanel construirPanelTotalesYAcciones(Evento evento) {
        JPanel panelInferior = new JPanel(new BorderLayout());

        JPanel panelResumenTotales = new JPanel(new GridLayout(3, 2, 4, 2));
        panelResumenTotales.setBorder(BorderFactory.createEmptyBorder(4, 250, 4, 8));

        panelResumenTotales.add(new JLabel("Subtotal"));
        etiquetaValorSubtotal = new JLabel("$0");
        panelResumenTotales.add(etiquetaValorSubtotal);

        panelResumenTotales.add(new JLabel("IVA"));
        etiquetaValorIva = new JLabel("$0");
        panelResumenTotales.add(etiquetaValorIva);

        panelResumenTotales.add(new JLabel("TOTAL"));
        etiquetaValorTotal = new JLabel("$0");
        panelResumenTotales.add(etiquetaValorTotal);

        panelInferior.add(panelResumenTotales, BorderLayout.CENTER);

        JPanel grupoBotonesAccion = new JPanel();
        grupoBotonesAccion.setBorder(BorderFactory.createEmptyBorder(4, 8, 8, 8));

        botonIrAVentasRegistradas = new JButton("Ventas registradas");
        botonIrAVentasRegistradas.setActionCommand(Evento.MENU_VENTAS_REGISTRADAS);
        botonIrAVentasRegistradas.addActionListener(evento);

        botonCancelarVenta = new JButton("Cancelar");
        botonCancelarVenta.setActionCommand(Evento.CANCELAR_VENTA);
        botonCancelarVenta.addActionListener(evento);

        botonConfirmarVenta = new JButton("Registrar venta");
        botonConfirmarVenta.setActionCommand(Evento.REGISTRAR_VENTA);
        botonConfirmarVenta.addActionListener(evento);

        grupoBotonesAccion.add(botonIrAVentasRegistradas);
        grupoBotonesAccion.add(botonCancelarVenta);
        grupoBotonesAccion.add(botonConfirmarVenta);

        panelInferior.add(grupoBotonesAccion, BorderLayout.SOUTH);
        return panelInferior;
    }

    private void quitarProductoSeleccionado() {
        int filaSeleccionada = tablaDetalleVenta.getSelectedRow();
        if (filaSeleccionada >= 0 && filaSeleccionada < productosAgregadosALaVenta.size()) {
            productosAgregadosALaVenta.remove(filaSeleccionada);
            refrescarFilasTabla();
            recalcularTotales();
        }
    }

    private void refrescarFilasTabla() {
        modeloTablaDetalle.setRowCount(0);
        for (DetalleVenta detalleProducto : productosAgregadosALaVenta) {
            modeloTablaDetalle.addRow(new Object[]{
                detalleProducto.getProducto().getCodigoInterno(),
                detalleProducto.getProducto().getNombreProducto(),
                detalleProducto.getDescripcionImpuesto(),
                detalleProducto.getCantidad(),
                "$" + String.format("%,.0f", detalleProducto.getPrecioUnitario()),
                String.format("%.1f%%", detalleProducto.getDescuentoDto()),
                "$" + String.format("%,.0f", detalleProducto.getSubtotal())
            });
        }
    }

    public void recalcularTotales() {
        double acumuladoSubtotal = 0;
        double acumuladoIva = 0;

        for (DetalleVenta detalleProducto : productosAgregadosALaVenta) {
            acumuladoSubtotal += detalleProducto.getSubtotal();
            acumuladoIva     += detalleProducto.getValorImpuesto();
        }

        etiquetaValorSubtotal.setText("$" + String.format("%,.0f", acumuladoSubtotal));
        etiquetaValorIva.setText("$"      + String.format("%,.0f", acumuladoIva));
        etiquetaValorTotal.setText("$"    + String.format("%,.0f", acumuladoSubtotal + acumuladoIva));
    }

    // Métodos públicos
    public void iniciarNuevaVenta(String numeroFactura, String fechaHoraVenta) {
        campoNumeroFactura.setText(numeroFactura);
        campoFechaHora.setText(fechaHoraVenta);
        productosAgregadosALaVenta.clear();
        modeloTablaDetalle.setRowCount(0);
        recalcularTotales();
    }

    public void poblarComboCliente(List<Cliente> clientesDisponibles) {
        comboSelectorCliente.removeAllItems();
        comboSelectorCliente.addItem("- Seleccionar cliente");
        for (Cliente clienteDisponible : clientesDisponibles) {
            comboSelectorCliente.addItem(
                clienteDisponible.getCodigoCliente() + " - " +
                clienteDisponible.getNombreCompletoORazonSocial()
            );
        }
    }

    public void agregarProductoALaVenta(DetalleVenta detalleNuevoProducto) {
        productosAgregadosALaVenta.add(detalleNuevoProducto);
        refrescarFilasTabla();
        recalcularTotales();
    }

    public String getCodigoClienteSeleccionado() {
        Object opcionSeleccionada = comboSelectorCliente.getSelectedItem();
        if (opcionSeleccionada == null || opcionSeleccionada.toString().startsWith("-")) {
            return null;
        }
        return opcionSeleccionada.toString().split(" - ")[0].trim();
    }

    public FormaPagoEnum getFormaPagoSeleccionada() {
        return (FormaPagoEnum) comboSelectorFormaPago.getSelectedItem();
    }

    public String getNumeroFactura() {
        return campoNumeroFactura.getText();
    }

    public List<DetalleVenta> getProductosAgregados() {
        return new ArrayList<>(productosAgregadosALaVenta);
    }
}