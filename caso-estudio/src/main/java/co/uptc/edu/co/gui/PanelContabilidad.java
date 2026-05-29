package co.uptc.edu.co.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import co.uptc.edu.co.modelo.MovimientoContable;

public class PanelContabilidad extends PanelCentral {

    private static final String TITULO_PANEL = "Gestion Contable";
    private static final String TEXTO_TOTAL_INICIAL = "Total de movimientos: 0";
    private static final String TEXTO_TOTAL = "Total de movimientos: ";

    private static final String OPCION_TODOS = "Todos";
    private static final String OPCION_TODAS = "Todas";

    private static final String[] COLUMNAS = {
            "Codigo Transaccion",
            "Fecha",
            "Tipo Movimiento",
            "Cuenta Contable",
            "Valor",
            "Descripcion"
    };

    private static final int COLUMNA_CODIGO = 0;

    private JButton botonVerDetalle;
    private JTextField campoBuscar;

    private JComboBox<String> comboTipoMovimiento;
    private JComboBox<String> comboCuenta;
    private List<MovimientoContable> movimientosCargados;

    public PanelContabilidad() {
        super();
        movimientosCargados = new ArrayList<>();
        inicializarComponentesContabilidad();
        configurarPanelContabilidad();
        agregarComponentesContabilidad();
        inicializarFiltros();
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
        return COLUMNAS;
    }

    private void inicializarComponentesContabilidad() {
        botonVerDetalle = new JButton("Ver Detalle");

        campoBuscar = new JTextField(20);

        comboTipoMovimiento = new JComboBox<>();
        comboTipoMovimiento.addItem(OPCION_TODOS);
        comboTipoMovimiento.addItem("Ingreso");
        comboTipoMovimiento.addItem("Egreso");

        comboCuenta = new JComboBox<>();
        comboCuenta.addItem(OPCION_TODAS);
        comboCuenta.addItem("Caja");
        comboCuenta.addItem("Bancos");
        comboCuenta.addItem("Inventario");
        comboCuenta.addItem("Ingresos por Ventas");
        comboCuenta.addItem("IVA Generado");
        comboCuenta.addItem("IVA Descontable");
        comboCuenta.addItem("Proveedores");
    }

    private void configurarPanelContabilidad() {
        configurarBotonBase(botonVerDetalle);
    }

    private void agregarComponentesContabilidad() {
        panelBotones.add(botonVerDetalle);

        agregarFiltro("Buscar:", campoBuscar);
        agregarFiltro("Tipo:", comboTipoMovimiento);
        agregarFiltro("Cuenta:", comboCuenta);
    }

    private void inicializarFiltros() {
        asignarFiltroTexto(campoBuscar, this::aplicarFiltros);
        asignarFiltroCombo(comboTipoMovimiento, this::aplicarFiltros);
        asignarFiltroCombo(comboCuenta, this::aplicarFiltros);
    }

    public void inicializarEventos(Evento evento) {
        botonVerDetalle.setActionCommand(Evento.CMD_VER_DETALLE_CONTABLE);
        botonVerDetalle.addActionListener(evento);
    }

    public void cargarMovimientos(List<MovimientoContable> movimientos) {
        movimientosCargados = new ArrayList<>(movimientos);
        aplicarFiltros();
    }

    public String obtenerCodigoSeleccionado() {
        return obtenerTextoSeleccionado(COLUMNA_CODIGO);
    }

    public void actualizarTotalMovimientos(int total) {
        actualizarTextoTotal(TEXTO_TOTAL, total);
    }

    private void aplicarFiltros() {
        limpiarTabla();

        String textoBusqueda = campoBuscar.getText().trim().toLowerCase();
        String tipoSeleccionado = comboTipoMovimiento.getSelectedItem().toString();
        String cuentaSeleccionada = comboCuenta.getSelectedItem().toString();

        int totalFiltrados = 0;

        for (MovimientoContable movimiento : movimientosCargados) {
            String codigo = valorTexto(movimiento.getCodigoTransaccion());
            String fecha = movimiento.getFecha() != null ? movimiento.getFecha().toString() : "";
            String tipo = movimiento.getTipoMovimientoContable() != null
                    ? movimiento.getTipoMovimientoContable().toString()
                    : "";
            String cuenta = valorTexto(movimiento.getCuentaContable());
            String valor = movimiento.getValor() != null ? movimiento.getValor().toString() : "";
            String descripcion = valorTexto(movimiento.getDescripcion());

            boolean coincideBusqueda =
                    codigo.toLowerCase().contains(textoBusqueda) ||
                    cuenta.toLowerCase().contains(textoBusqueda) ||
                    descripcion.toLowerCase().contains(textoBusqueda);

            boolean coincideTipo =
                    tipoSeleccionado.equals(OPCION_TODOS) ||
                    tipo.equalsIgnoreCase(tipoSeleccionado);

            boolean coincideCuenta =
                    cuentaSeleccionada.equals(OPCION_TODAS) ||
                    cuenta.equalsIgnoreCase(cuentaSeleccionada);

            if (coincideBusqueda && coincideTipo && coincideCuenta) {
                Object[] fila = {
                        codigo,
                        fecha,
                        tipo,
                        cuenta,
                        valor,
                        descripcion
                };

                modeloTabla.addRow(fila);
                totalFiltrados++;
            }
        }

        actualizarTotalMovimientos(totalFiltrados);
    }

    private String valorTexto(String valor) {
        return valor != null ? valor : "";
    }
}
