package co.edu.uptc.tiendaminorista.gui.administrador;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import co.edu.uptc.tiendaminorista.gui.Evento;
import co.edu.uptc.tiendaminorista.modelo.Cliente;
import co.edu.uptc.tiendaminorista.modelo.CompraPro;
import co.edu.uptc.tiendaminorista.modelo.CompasCliente;
import co.edu.uptc.tiendaminorista.modelo.MovimientoContable;
import co.edu.uptc.tiendaminorista.modelo.Producto;

public class PanelConsultas extends JPanel {

    private CardLayout cardLayout;
    private JPanel panelContenidoDinamico;

    private JTextField txtFechaVentas;
    private JTable tablaVentasFecha;
    private DefaultTableModel modeloVentasFecha;

    private JTextField txtNitProveedor;
    private JTextField txtFechaDesde;
    private JTextField txtFechaHasta;
    private JTable tablaComprasProv;
    private DefaultTableModel modeloComprasProv;

    private JTable tablaStockMinimo;
    private DefaultTableModel modeloStockMinimo;

    private JTextField txtDocumentoCliente;
    private JComboBox<String> comboClientesConsulta; 
    private List<Cliente> listaClientesConsultaAux;
    private JTable tablaHistorialCliente;
    private DefaultTableModel modeloHistorialCliente;

    private JTextField txtCuentaContable;
    private JTextField txtFechaDesdeMov;
    private JTextField txtFechaHastaMov;
    private JTable tablaMovContable;
    private DefaultTableModel modeloMovContable;

    public PanelConsultas(Evento e) {
        setLayout(new BorderLayout(10, 10));

        comboClientesConsulta = new JComboBox<>();

        JPanel panelMenuLateral = new JPanel(new GridLayout(5, 1, 5, 10));
        panelMenuLateral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
        panelMenuLateral.setPreferredSize(new Dimension(220, 0));

        JButton btnVentasFecha = new JButton("Ventas por Fecha");
        btnVentasFecha.setActionCommand(Evento.MOSTRAR_VENTAS_FECHA);
        btnVentasFecha.addActionListener(e);

        JButton btnCompraProv = new JButton("Compra por proveedor");
        btnCompraProv.setActionCommand(Evento.MOSTRAR_COMPRA_PROV);
        btnCompraProv.addActionListener(e);

        JButton btnStockMin = new JButton("Productos con bajo stock");
        btnStockMin.setActionCommand(Evento.MOSTRAR_STOCK_MIN);
        btnStockMin.addActionListener(e);

        JButton btnHistorialCli = new JButton("Historial del cliente");
        btnHistorialCli.setActionCommand(Evento.MOSTRAR_HISTORIAL_CLI_CONS);
        btnHistorialCli.addActionListener(e);

        JButton btnMovContable = new JButton("Movimiento contable");
        btnMovContable.setActionCommand(Evento.MOSTRAR_MOV_CONTABLE);
        btnMovContable.addActionListener(e);

        panelMenuLateral.add(btnVentasFecha);
        panelMenuLateral.add(btnCompraProv);
        panelMenuLateral.add(btnStockMin);
        panelMenuLateral.add(btnHistorialCli);
        panelMenuLateral.add(btnMovContable);

        cardLayout = new CardLayout();
        panelContenidoDinamico = new JPanel(cardLayout);
        panelContenidoDinamico.setBorder(BorderFactory.createEtchedBorder());

        panelContenidoDinamico.add(crearPanelPlaceholder("Espacio de Consultas: Seleccione una opcion a la izquierda."), "INICIO");
        panelContenidoDinamico.add(crearPanelVentasFecha(e), "VENTAS_FECHA");
        panelContenidoDinamico.add(crearVistaCompraProveedor(e), "COMPRA_PROVEEDOR");
        panelContenidoDinamico.add(crearPanelStockMinimo(e), "STOCK_MIN");
        panelContenidoDinamico.add(crearPanelHistorialCliente(e), "HISTORIAL_CLIENTE");
        panelContenidoDinamico.add(crearPanelMovContable(e), "MOV_CONTABLE");

        add(panelMenuLateral, BorderLayout.WEST);
        add(panelContenidoDinamico, BorderLayout.CENTER);
        cardLayout.show(panelContenidoDinamico, "INICIO");
    }

    private JPanel crearPanelVentasFecha(Evento e) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel filtro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filtro.setBorder(BorderFactory.createTitledBorder("Ventas en fecha"));
        filtro.add(new JLabel("Fecha (dd/MM/yyyy):"));
        txtFechaVentas = new JTextField(12);
        filtro.add(txtFechaVentas);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setActionCommand(Evento.EJECUTAR_CONS_VENTAS_FECHA);
        btnBuscar.addActionListener(e);
        filtro.add(btnBuscar);
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(evt -> conmutarVista("INICIO"));
        filtro.add(btnVolver);
        String[] columnas = {"Fecha", "Cliente", "Total", "Impuestos"};
        modeloVentasFecha = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaVentasFecha = new JTable(modeloVentasFecha);
        tablaVentasFecha.setRowHeight(24);
        panel.add(filtro, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaVentasFecha), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelStockMinimo(Evento e) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel norte = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        norte.setBorder(BorderFactory.createTitledBorder("Productos con Stock por Debajo del Minimo"));
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setActionCommand(Evento.EJECUTAR_CONS_STOCK_MIN);
        btnBuscar.addActionListener(e);
        norte.add(btnBuscar);
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(evt -> conmutarVista("INICIO"));
        norte.add(btnVolver);
        String[] columnas = {"Codigo", "Nombre", "Categoria", "Stock Actual", "Stock Minimo"};
        modeloStockMinimo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaStockMinimo = new JTable(modeloStockMinimo);
        tablaStockMinimo.setRowHeight(24);
        panel.add(norte, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaStockMinimo), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelHistorialCliente(Evento e) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel filtro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filtro.setBorder(BorderFactory.createTitledBorder("Historial de Compras por Cliente"));
        filtro.add(new JLabel("Documento o Nombre Cliente:"));
        txtDocumentoCliente = new JTextField(14);
        filtro.add(txtDocumentoCliente);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setActionCommand(Evento.EJECUTAR_CONS_HISTORIAL_CLI);
        btnBuscar.addActionListener(e);
        filtro.add(btnBuscar);
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(evt -> conmutarVista("INICIO"));
        filtro.add(btnVolver);
        
        // 5 Columnas declaradas
        String[] columnas = {"Fecha", "Cliente", "Producto", "Cantidad", "Total"};
        modeloHistorialCliente = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaHistorialCliente = new JTable(modeloHistorialCliente);
        tablaHistorialCliente.setRowHeight(24);
        panel.add(filtro, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaHistorialCliente), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelMovContable(Evento e) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel filtro = new JPanel(new GridLayout(2, 4, 10, 10));
        filtro.setBorder(BorderFactory.createTitledBorder("Movimientos Contables por Cuenta y Periodo"));
        filtro.add(new JLabel("Cuenta contable:"));
        txtCuentaContable = new JTextField();
        filtro.add(txtCuentaContable);
        filtro.add(new JLabel("Fecha Desde (dd/MM/yyyy):"));
        txtFechaDesdeMov = new JTextField();
        filtro.add(txtFechaDesdeMov);
        filtro.add(new JLabel("Fecha Hasta (dd/MM/yyyy):"));
        txtFechaHastaMov = new JTextField();
        filtro.add(txtFechaHastaMov);
        filtro.add(new JLabel());
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setActionCommand(Evento.EJECUTAR_CONS_MOV_CONTABLE);
        btnBuscar.addActionListener(e);
        acciones.add(btnBuscar);
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(evt -> conmutarVista("INICIO"));
        acciones.add(btnVolver);
        String[] columnas = {"Fecha", "Cuenta", "Tipo", "Debito", "Credito", "Descripcion", "Documento"};
        modeloMovContable = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaMovContable = new JTable(modeloMovContable);
        tablaMovContable.setRowHeight(24);
        panel.add(filtro, BorderLayout.NORTH);
        panel.add(acciones, BorderLayout.CENTER);
        panel.add(new JScrollPane(tablaMovContable), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelPlaceholder(String texto) {
        JPanel p = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        p.add(lbl, BorderLayout.CENTER);
        return p;
    }

    private JPanel crearVistaCompraProveedor(Evento e) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JPanel panelFiltros = new JPanel(new GridLayout(2, 3, 10, 5));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros de Busqueda"));
        txtNitProveedor = new JTextField();
        txtFechaDesde = new JTextField();
        txtFechaHasta = new JTextField();
        panelFiltros.add(new JLabel("NIT Proveedor:"));
        panelFiltros.add(new JLabel("Fecha Desde (dd/MM/yyyy):"));
        panelFiltros.add(new JLabel("Fecha Hasta (dd/MM/yyyy):"));
        panelFiltros.add(txtNitProveedor);
        panelFiltros.add(txtFechaDesde);
        panelFiltros.add(txtFechaHasta);
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setActionCommand(Evento.EJECUTAR_CONS_COMPRA_PROV);
        btnBuscar.addActionListener(e);
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(evt -> conmutarVista("INICIO"));
        panelAcciones.add(btnBuscar);
        panelAcciones.add(btnVolver);
        JPanel panelNorteEstructura = new JPanel(new BorderLayout());
        panelNorteEstructura.add(panelFiltros, BorderLayout.CENTER);
        panelNorteEstructura.add(panelAcciones, BorderLayout.SOUTH);
        String[] columnas = {"Fecha", "Proveedor", "Producto", "Total"};
        modeloComprasProv = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaComprasProv = new JTable(modeloComprasProv);
        panel.add(panelNorteEstructura, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaComprasProv), BorderLayout.CENTER);
        return panel;
    }

    public void conmutarVista(String nombreVista) {
        cardLayout.show(panelContenidoDinamico, nombreVista);
    }

    public void actualizarTablaVentasFecha(List<CompasCliente> lista) {
        modeloVentasFecha.setRowCount(0);
        if (lista != null) {
            for (CompasCliente venta : lista) {
                double impuestos = venta.getTotalCompra() * 0.19;
                modeloVentasFecha.addRow(new Object[]{
                    venta.getFechaFormateada(),
                    venta.getCliente() != null ? venta.getCliente().getNombre() : "",
                    String.format("$%,.2f", venta.getTotalCompra()),
                    String.format("$%,.2f", impuestos)
                });
            }
        }
    }

    public void actualizarTablaComprasPro(List<CompraPro> lista) {
        modeloComprasProv.setRowCount(0);
        if (lista != null) {
            for (CompraPro compra : lista) {
                modeloComprasProv.addRow(new Object[]{
                    compra.getFechaFormateada(),
                    compra.getProveedor(),
                    compra.getProducto(),
                    String.format("$%,.2f", compra.getTotal())
                });
            }
        }
    }

    public void actualizarTablaStockMinimo(List<Producto> lista) {
        modeloStockMinimo.setRowCount(0);
        if (lista != null) {
            for (Producto producto : lista) {
                modeloStockMinimo.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getCategoria(),
                    producto.getStockActual(),
                    producto.getStockMinimo()
                });
            }
        }
    }

    public void actualizarTablaHistorialCliente(List<CompasCliente> lista) {
        modeloHistorialCliente.setRowCount(0);
        if (lista != null) {
            for (CompasCliente compra : lista) {
                modeloHistorialCliente.addRow(new Object[]{
                    compra.getFechaFormateada(),                                        
                    compra.getCliente() != null ? compra.getCliente().getNombre() : "", 
                    compra.getProducto() != null ? compra.getProducto().getNombre() : "", 
                    compra.getCantidad(),                                               
                    String.format("$%,.2f", compra.getTotalCompra())                    
                });
            }
        }
    }

    public void actualizarTablaMovimientosContables(List<MovimientoContable> lista) {
        modeloMovContable.setRowCount(0);
        if (lista != null) {
            for (MovimientoContable movimiento : lista) {
                modeloMovContable.addRow(new Object[]{
                    movimiento.getFechaFormateada(),
                    movimiento.getCuentaContable(),
                    movimiento.getTipo(),
                    String.format("$%,.2f", movimiento.getDebito()),
                    String.format("$%,.2f", movimiento.getCredito()),
                    movimiento.getDescripcion(),
                    movimiento.getDocumentoRelacionado()
                });
            }
        }
    }

    public JTextField getTxtFechaVentas() { return txtFechaVentas; }
    public JTextField getTxtNitProveedor() { return txtNitProveedor; }
    public JTextField getTxtFechaDesde() { return txtFechaDesde; }
    public JTextField getTxtFechaHasta() { return txtFechaHasta; }

    public void cargarClientesEnCombo(List<Cliente> clientes) {
        this.listaClientesConsultaAux = clientes;
        comboClientesConsulta.removeAllItems();
        if (clientes != null) {
            for (Cliente c : clientes) {
                comboClientesConsulta.addItem(c.getNombre());
            }
            if (!clientes.isEmpty()) {
                comboClientesConsulta.setSelectedIndex(-1);
            }
        }
    }

    public Cliente getClienteSeleccionadoConsulta() {
        int index = comboClientesConsulta.getSelectedIndex();
        if (index >= 0 && listaClientesConsultaAux != null && index < listaClientesConsultaAux.size()) {
            return listaClientesConsultaAux.get(index);
        }
        return null;
    }

    public JTextField getTxtDocumentoCliente() { return txtDocumentoCliente; }
    public JTextField getTxtCuentaContable() { return txtCuentaContable; }
    public JTextField getTxtFechaDesdeMov() { return txtFechaDesdeMov; }
    public JTextField getTxtFechaHastaMov() { return txtFechaHastaMov; }
}