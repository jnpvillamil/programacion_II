package co.uptc.edu.co.gui;

import java.text.ParseException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.text.MaskFormatter;

import co.uptc.edu.co.modelo.Cliente;
import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.MovimientoContable;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.Proveedor;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.util.LogUtil;

public class PanelConsultas extends PanelCentral {

    private static final String TITULO_PANEL = "Gestión de Consultas";
    private static final String TEXTO_TOTAL_INICIAL = "Resultados encontrados: 0";
    private static final String TEXTO_TOTAL = "Resultados encontrados: ";
    private static final DateTimeFormatter FORMATO_FECHA_ENTRADA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat FORMATO_MONEDA = crearFormatoMoneda();

    private static final String CONSULTA_VENTAS_FECHA = "Ventas por fecha";
    private static final String CONSULTA_COMPRAS_PROVEEDOR = "Compras por proveedor";
    private static final String CONSULTA_STOCK_BAJO = "Productos con stock bajo mínimo";
    private static final String CONSULTA_HISTORIAL_CLIENTE = "Historial de compras de cliente";
    private static final String CONSULTA_MOVIMIENTOS_CONTABLES = "Movimientos contables por cuenta y periodo";

    private static final String[] COLUMNAS_INICIALES = { "Resultado" };
    private static final String[] COLUMNAS_VENTAS_FECHA = {
            "Fecha", "Cliente", "Total", "Impuestos"
    };
    private static final String[] COLUMNAS_COMPRAS_PROVEEDOR = {
            "Factura Proveedor", "Fecha", "Proveedor", "Impuestos", "Total Compra"
    };
    private static final String[] COLUMNAS_STOCK_BAJO = {
            "Código", "Producto", "Categoría", "Stock Actual", "Stock Mínimo"
    };
    private static final String[] COLUMNAS_HISTORIAL_CLIENTE = {
            "Factura", "Fecha", "Cliente", "Forma de Pago", "Total"
    };
    private static final String[] COLUMNAS_MOVIMIENTOS_CONTABLES = {
            "Código Transacción", "Fecha", "Tipo Movimiento", "Cuenta", "Valor", "Descripción"
    };

    private JLabel etiquetaTipoConsulta;
    private JLabel etiquetaFecha;
    private JLabel etiquetaProveedor;
    private JLabel etiquetaCliente;
    private JLabel etiquetaCuenta;
    private JLabel etiquetaFechaInicio;
    private JLabel etiquetaFechaFin;

    private JButton botonConsultar;

    private JComboBox<String> comboTipoConsulta;
    private JComboBox<String> comboProveedor;
    private JComboBox<String> comboCliente;
    private JComboBox<String> comboCuenta;
    private JComboBox<String> comboTipoMovimiento;

    private JFormattedTextField campoFecha;
    private JFormattedTextField campoFechaInicio;
    private JFormattedTextField campoFechaFin;

    public PanelConsultas() {
        super();
        inicializarComponentesConsultas();
        configurarPanelConsultas();
        agregarComponentesConsultas();
        actualizarFiltros();
    }

    private static DecimalFormat crearFormatoMoneda() {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setGroupingSeparator('.');
        simbolos.setDecimalSeparator(',');

        DecimalFormat formato = new DecimalFormat("$ #,##0", simbolos);
        formato.setGroupingUsed(true);
        return formato;
    }

    @Override
    protected String obtenerTituloPanel() {
        return TITULO_PANEL;
    }

    @Override
    protected String obtenerTextoTotalInicial() {
        return TEXTO_TOTAL_INICIAL;
    }

    @Override
    protected Object[] obtenerColumnas() {
        return COLUMNAS_INICIALES;
    }

    private void inicializarComponentesConsultas() {
        LogUtil.info("Inicializando componentes de PanelConsultas");
        etiquetaTipoConsulta = new JLabel("Tipo de consulta:");
        etiquetaFecha = new JLabel("Fecha:");
        etiquetaProveedor = new JLabel("Proveedor:");
        etiquetaCliente = new JLabel("Cliente:");
        etiquetaCuenta = new JLabel("Cuenta:");
        etiquetaFechaInicio = new JLabel("Desde:");
        etiquetaFechaFin = new JLabel("Hasta:");

        botonConsultar = new JButton("Consultar");

        comboTipoConsulta = new JComboBox<>();
        comboTipoConsulta.addItem(CONSULTA_VENTAS_FECHA);
        comboTipoConsulta.addItem(CONSULTA_COMPRAS_PROVEEDOR);
        comboTipoConsulta.addItem(CONSULTA_STOCK_BAJO);
        comboTipoConsulta.addItem(CONSULTA_HISTORIAL_CLIENTE);
        comboTipoConsulta.addItem(CONSULTA_MOVIMIENTOS_CONTABLES);

        comboProveedor = new JComboBox<>();
        comboCliente = new JComboBox<>();
        comboCuenta = new JComboBox<>();
        comboTipoMovimiento = new JComboBox<>();
        comboTipoMovimiento.addItem("Todos");
        comboTipoMovimiento.addItem("INGRESO");
        comboTipoMovimiento.addItem("EGRESO");

        campoFecha = crearCampoFecha();
        campoFecha.setColumns(8);

        campoFechaInicio = crearCampoFecha();
        campoFechaInicio.setColumns(8);

        campoFechaFin = crearCampoFecha();
        campoFechaFin.setColumns(8);
    }

    private void configurarPanelConsultas() {
         configurarBotonBase(botonConsultar);
         asignarFiltroCombo(comboTipoConsulta, this::actualizarFiltros);
    }

    private void agregarComponentesConsultas() {
        panelFiltros.add(etiquetaTipoConsulta);
        panelFiltros.add(comboTipoConsulta);

        panelFiltros.add(etiquetaFecha);
        panelFiltros.add(campoFecha);

        panelFiltros.add(etiquetaProveedor);
        panelFiltros.add(comboProveedor);

        panelFiltros.add(etiquetaCliente);
        panelFiltros.add(comboCliente);

        panelFiltros.add(etiquetaCuenta);
        panelFiltros.add(comboCuenta);
      
        panelFiltros.add(comboTipoMovimiento);
        panelFiltros.add(comboTipoMovimiento);

        panelFiltros.add(etiquetaFechaInicio);
        panelFiltros.add(campoFechaInicio);

        panelFiltros.add(etiquetaFechaFin);
        panelFiltros.add(campoFechaFin);

        panelFiltros.add(botonConsultar);
    }

    private void actualizarFiltros() {
        LogUtil.info("Actualizando filtros en PanelConsultas. Tipo=" + comboTipoConsulta.getSelectedItem());
        String tipoConsulta = comboTipoConsulta.getSelectedItem().toString();

        ocultarFiltros();

        if (tipoConsulta.equals(CONSULTA_VENTAS_FECHA)) {
            configurarVentasPorFecha();
        } else if (tipoConsulta.equals(CONSULTA_COMPRAS_PROVEEDOR)) {
            configurarComprasPorProveedor();
        } else if (tipoConsulta.equals(CONSULTA_STOCK_BAJO)) {
            configurarStockBajoMinimo();
        } else if (tipoConsulta.equals(CONSULTA_HISTORIAL_CLIENTE)) {
            configurarHistorialCliente();
        } else if (tipoConsulta.equals(CONSULTA_MOVIMIENTOS_CONTABLES)) {
            configurarMovimientosContables();
        }

        limpiarTabla();
        actualizarTextoTotal(TEXTO_TOTAL, 0);

        revalidate();
        repaint();
    }

    public void inicializarEventos(Evento evento) {
        LogUtil.info("Inicializando eventos en PanelConsultas");
        botonConsultar.setActionCommand(Evento.CMD_CONSULTAR_SISTEMA);
        botonConsultar.addActionListener(evento);
    }

    public boolean esConsultaVentasPorFecha() {
        return CONSULTA_VENTAS_FECHA.equals(comboTipoConsulta.getSelectedItem());
    }

    public LocalDate obtenerFechaConsulta() throws Exception {
        String textoFecha = campoFecha.getText().trim();

        if (textoFecha.contains("_")) {
            throw new Exception("Debe ingresar una fecha completa con formato dd/MM/yyyy.");
        }

        try {
            return LocalDate.parse(textoFecha, FORMATO_FECHA_ENTRADA);
        } catch (DateTimeParseException e) {
            throw new Exception("La fecha debe tener formato dd/MM/yyyy.");
        }
    }

    public void cargarVentasPorFecha(List<Venta> ventas) {
        limpiarTabla();
        actualizarColumnas(COLUMNAS_VENTAS_FECHA);

        for (Venta venta : ventas) {
            Object[] fila = {
                    venta.getFechaHora() != null ? venta.getFechaHora().toLocalDate().format(FORMATO_FECHA_ENTRADA) : "",
                    venta.getCliente(),
                    FORMATO_MONEDA.format(venta.getTotal()),
                    FORMATO_MONEDA.format(venta.getImpuestos())
            };
            modeloTabla.addRow(fila);
        }

        actualizarTextoTotal(TEXTO_TOTAL, ventas.size());
    }

    private void configurarVentasPorFecha() {
          mostrarComponentes(etiquetaFecha, campoFecha);
          actualizarColumnas(COLUMNAS_VENTAS_FECHA);
    }

    private void configurarComprasPorProveedor() {
        mostrarComponentes(
                etiquetaProveedor,
                comboProveedor,
                etiquetaFechaInicio,
                campoFechaInicio,
                etiquetaFechaFin,
                campoFechaFin
        );
          actualizarColumnas(COLUMNAS_COMPRAS_PROVEEDOR);
    }

    private void configurarStockBajoMinimo() {
         actualizarColumnas(COLUMNAS_STOCK_BAJO);
    }

    private void configurarHistorialCliente() {
         mostrarComponentes(etiquetaCliente, comboCliente);
         actualizarColumnas(COLUMNAS_HISTORIAL_CLIENTE);
    }

    private void configurarMovimientosContables() {
         mostrarComponentes(
             etiquetaCuenta,
             comboCuenta,
             comboTipoMovimiento,
             comboTipoMovimiento,
             etiquetaFechaInicio,
             campoFechaInicio,
             etiquetaFechaFin,
             campoFechaFin
         );
          actualizarColumnas(COLUMNAS_MOVIMIENTOS_CONTABLES);
    }

    private void ocultarFiltros() {
         ocultarComponentes(
             etiquetaFecha, campoFecha,
             etiquetaProveedor, comboProveedor,
             etiquetaCliente, comboCliente,
             etiquetaCuenta, comboCuenta,
             comboTipoMovimiento, comboTipoMovimiento,
             etiquetaFechaInicio, campoFechaInicio,
             etiquetaFechaFin, campoFechaFin
         );
     }

    private JFormattedTextField crearCampoFecha() {
        try {
            MaskFormatter mascara = new MaskFormatter("##/##/####");
            mascara.setPlaceholderCharacter('_');
            return new JFormattedTextField(mascara);
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }

    public void cargarProveedores(List<Proveedor> proveedores) {
        LogUtil.info("Cargando proveedores en PanelConsultas. count=" + (proveedores != null ? proveedores.size() : 0));
        comboProveedor.removeAllItems();
        if (proveedores == null) {
            return;
        }
        for (Proveedor p : proveedores) {
            if (p != null && p.estaActivo()) {
                comboProveedor.addItem(p.getCodigoProveedor() + " - " + p.getRazonSocial());
            }
        }
    }

    public void cargarClientes(List<Cliente> clientes) {
        LogUtil.info("Cargando clientes en PanelConsultas. count=" + (clientes != null ? clientes.size() : 0));
        comboCliente.removeAllItems();
        if (clientes == null) {
            return;
        }
        for (Cliente c : clientes) {
            if (c != null && c.estaActivo()) {
                comboCliente.addItem(c.getCodigo() + " - " + c.getNombre());
            }
        }
    }

    public void cargarCuentas() {
        LogUtil.info("Cargando cuentas contables en PanelConsultas");
        comboCuenta.removeAllItems();
        comboCuenta.addItem("Caja");
        comboCuenta.addItem("Bancos");
        comboCuenta.addItem("Cuentas por Cobrar");
        comboCuenta.addItem("Inventario");
        comboCuenta.addItem("Ingresos por Ventas");
        comboCuenta.addItem("Compras");
        comboCuenta.addItem("IVA Generado");
        comboCuenta.addItem("IVA Descontable");
    }

    public void cargarProductosStockBajo(List<Producto> productos) {
        LogUtil.info("Cargando productos con stock bajo en PanelConsultas. count=" + (productos != null ? productos.size() : 0));
        limpiarTabla();
        if (productos == null) {
            return;
        }
        for (Producto p : productos) {
            if (p != null && p.estaActivo() && p.stockBajoMinimo()) {
                Object[] fila = {
                    p.getCodigoProducto(),
                    p.getNombreProducto(),
                    p.getCategoria().toString(),
                    p.getStockActual(),
                    p.getStockMinimo()
                };
                modeloTabla.addRow(fila);
            }
        }
        actualizarTextoTotal(TEXTO_TOTAL, modeloTabla.getRowCount());
    }

    public void cargarComprasPorProveedor(List<Compra> compras) {
        LogUtil.info("Cargando compras por proveedor en PanelConsultas. count=" + (compras != null ? compras.size() : 0));
        limpiarTabla();
        if (compras == null) {
            return;
        }
        for (Compra c : compras) {
            if (c != null) {
                Object[] fila = {
                    c.getNumeroFacturaProveedor(),
                    c.getFecha() != null ? c.getFecha().toString() : "",
                    c.getProveedor(),
                    FORMATO_MONEDA.format(c.getImpuestos()),
                    FORMATO_MONEDA.format(c.getTotalCompra())
                };
                modeloTabla.addRow(fila);
            }
        }
        actualizarTextoTotal(TEXTO_TOTAL, modeloTabla.getRowCount());
    }

    public void cargarHistorialCliente(List<Venta> ventas) {
        LogUtil.info("Cargando historial cliente en PanelConsultas. count=" + (ventas != null ? ventas.size() : 0));
        limpiarTabla();
        if (ventas == null) {
            return;
        }
        for (Venta v : ventas) {
            if (v != null) {
                Object[] fila = {
                    v.getNumeroFactura(),
                    v.getFechaHora() != null ? v.getFechaHora().toLocalDate().toString() : "",
                    v.getCliente(),
                    v.getFormaPago() != null ? v.getFormaPago().toString() : "",
                    FORMATO_MONEDA.format(v.getTotal())
                };
                modeloTabla.addRow(fila);
            }
        }
        actualizarTextoTotal(TEXTO_TOTAL, modeloTabla.getRowCount());
    }

    public void cargarMovimientosContables(List<MovimientoContable> movimientos) {
        LogUtil.info("Cargando movimientos contables en PanelConsultas. count=" + (movimientos != null ? movimientos.size() : 0));
        limpiarTabla();
        if (movimientos == null) {
            return;
        }
        for (MovimientoContable m : movimientos) {
            if (m != null) {
                Object[] fila = {
                    m.getCodigoTransaccion(),
                    m.getFecha() != null ? m.getFecha().toString() : "",
                    m.getTipoMovimientoContable() != null ? m.getTipoMovimientoContable().toString() : "",
                    m.getCuentaContable(),
                    FORMATO_MONEDA.format(m.getValor()),
                    m.getDescripcion()
                };
                modeloTabla.addRow(fila);
            }
        }
        actualizarTextoTotal(TEXTO_TOTAL, modeloTabla.getRowCount());
    }

    public boolean esConsultaComprasPorProveedor() {
        return CONSULTA_COMPRAS_PROVEEDOR.equals(comboTipoConsulta.getSelectedItem());
    }

    public boolean esConsultaStockBajo() {
        return CONSULTA_STOCK_BAJO.equals(comboTipoConsulta.getSelectedItem());
    }

    public boolean esConsultaHistorialCliente() {
        return CONSULTA_HISTORIAL_CLIENTE.equals(comboTipoConsulta.getSelectedItem());
    }

    public boolean esConsultaMovimientosContables() {
        return CONSULTA_MOVIMIENTOS_CONTABLES.equals(comboTipoConsulta.getSelectedItem());
    }

    public String obtenerProveedorSeleccionado() {
        Object item = comboProveedor.getSelectedItem();
        if (item != null) {
            String texto = item.toString();
            if (texto.contains(" - ")) {
                return texto.split(" - ")[0].trim();
            }
            return texto;
        }
        return null;
    }

    public String obtenerClienteSeleccionado() {
        Object item = comboCliente.getSelectedItem();
        if (item != null) {
            String texto = item.toString();
            if (texto.contains(" - ")) {
                return texto.split(" - ")[0].trim();
            }
            return texto;
        }
        return null;
    }

    public String obtenerCuentaSeleccionada() {
        Object item = comboCuenta.getSelectedItem();
        return item != null ? item.toString() : null;
    }

    public String obtenerTipoMovimientoSeleccionado() {
        Object item = comboTipoMovimiento.getSelectedItem();
        return item != null ? item.toString() : null;
    }

    public LocalDate obtenerFechaInicio() throws Exception {
        String textoFecha = campoFechaInicio.getText().trim();
        if (textoFecha.isEmpty() || textoFecha.contains("_")) {
            throw new Exception("Debe ingresar una fecha de inicio completa con formato dd/MM/yyyy.");
        }
        try {
            return LocalDate.parse(textoFecha, FORMATO_FECHA_ENTRADA);
        } catch (DateTimeParseException e) {
            throw new Exception("La fecha de inicio debe tener formato dd/MM/yyyy.");
        }
    }

    public LocalDate obtenerFechaFin() throws Exception {
        String textoFecha = campoFechaFin.getText().trim();
        if (textoFecha.isEmpty() || textoFecha.contains("_")) {
            throw new Exception("Debe ingresar una fecha de fin completa con formato dd/MM/yyyy.");
        }
        try {
            return LocalDate.parse(textoFecha, FORMATO_FECHA_ENTRADA);
        } catch (DateTimeParseException e) {
            throw new Exception("La fecha de fin debe tener formato dd/MM/yyyy.");
        }
    }
}
