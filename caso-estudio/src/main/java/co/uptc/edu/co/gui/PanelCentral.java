package co.uptc.edu.co.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class PanelCentral extends JPanel {

    private static final int ESPACIADO_HORIZONTAL = 10;
    private static final int ESPACIADO_VERTICAL = 10;
    private static final int ESPACIADO_SUPERIOR = 5;
    private static final int ESPACIADO_INFERIOR = 5;

    private static final int MARGEN_SUPERIOR = 10;
    private static final int MARGEN_IZQUIERDO = 10;
    private static final int MARGEN_INFERIOR = 10;
    private static final int MARGEN_DERECHO = 10;

    private static final String FUENTE_TITULO = "Arial";
    private static final int TAMANIO_TITULO = 22;
    private static final int ALTO_FILA_TABLA = 25;

    protected JLabel etiquetaTitulo;
    protected JLabel etiquetaTotal;

    protected JTable tabla;
    protected DefaultTableModel modeloTabla;
    protected JScrollPane scrollTabla;

    protected JPanel panelSuperior;
    protected JPanel panelBotones;
    protected JPanel panelFiltros;
    protected JPanel panelInferior;

    public PanelCentral() {
        inicializarBase();
        configurarBase();
        agregarBase();
    }

    private void inicializarBase() {
        etiquetaTitulo = new JLabel(obtenerTituloPanel());
        etiquetaTotal = new JLabel(obtenerTextoTotalInicial());

        modeloTabla = new DefaultTableModel(obtenerColumnas(), 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        scrollTabla = new JScrollPane(tabla);

        panelSuperior = new JPanel();
        panelBotones = new JPanel();
        panelFiltros = new JPanel();
        panelInferior = new JPanel();
    }

    private void configurarBase() {
        setLayout(new BorderLayout(ESPACIADO_HORIZONTAL, ESPACIADO_VERTICAL));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(
                MARGEN_SUPERIOR,
                MARGEN_IZQUIERDO,
                MARGEN_INFERIOR,
                MARGEN_DERECHO
        ));

        etiquetaTitulo.setFont(new Font(FUENTE_TITULO, Font.BOLD, TAMANIO_TITULO));

        panelSuperior.setLayout(new BorderLayout(ESPACIADO_SUPERIOR, ESPACIADO_INFERIOR));
        panelSuperior.setBackground(Color.WHITE);

        panelBotones.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelBotones.setBackground(Color.WHITE);

        panelFiltros.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.setBackground(Color.WHITE);

        panelInferior.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelInferior.setBackground(Color.WHITE);

        tabla.setRowHeight(ALTO_FILA_TABLA);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void agregarBase() {
        panelSuperior.add(etiquetaTitulo, BorderLayout.NORTH);
        panelSuperior.add(panelBotones, BorderLayout.CENTER);
        panelSuperior.add(panelFiltros, BorderLayout.SOUTH);

        panelInferior.add(etiquetaTotal);

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    public boolean haySeleccion() {
        return tabla.getSelectedRow() != -1;
    }

    public int obtenerFilaSeleccionada() {
        return tabla.getSelectedRow();
    }

    public Object obtenerValorSeleccionado(int columna) {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            return null;
        }
        return modeloTabla.getValueAt(filaSeleccionada, columna);
    }

    public void limpiarSeleccion() {
        tabla.clearSelection();
    }

    protected void actualizarTextoTotal(String textoBase, int total) {
        etiquetaTotal.setText(textoBase + total);
    }

    protected abstract String obtenerTituloPanel();

    protected abstract String obtenerTextoTotalInicial();

    protected abstract Object[] obtenerColumnas();
}