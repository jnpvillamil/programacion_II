package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class PanelReportes extends PanelBase {

    public static final String REPORTE_CONSOLIDADO = "Resumen Diario Consolidado";

    private JComboBox<String> cbTipoReporte;
    private JButton btnGenerar;
    private JButton btnGenerarJSON;
    private JSpinner spFechaReporte;
    private JLabel lblFechaReporte;
    private JTable tablaReportes;
    private DefaultTableModel modeloTabla;

    public PanelReportes() {
        super(); 
        initComponents();
    }

    @Override
    public void initComponents() {
        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));

        JLabel lblSeleccion = ConstructorComponentes.crearLabelFormulario("Seleccione el Reporte:");

        String[] opcionesReporte = {
            "Mejor Cliente",
            "Producto Más Vendido",
            "Ventas por Método de Pago",
            "Estado de Inventario",
            REPORTE_CONSOLIDADO
        };
        cbTipoReporte = ConstructorComponentes.crearComboBox(opcionesReporte);
        cbTipoReporte.setPreferredSize(new Dimension(260, ConstructorComponentes.ALTURA_CAMPO));

        lblFechaReporte = ConstructorComponentes.crearLabelFormulario("Fecha:");
        lblFechaReporte.setVisible(false);

        spFechaReporte = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor editorFecha = new JSpinner.DateEditor(spFechaReporte, "dd/MM/yyyy");
        editorFecha.getTextField().setFont(new Font(ConstructorComponentes.FUENTE_UI, Font.PLAIN, ConstructorComponentes.TAMANIO_CAMPO));
        spFechaReporte.setEditor(editorFecha);
        spFechaReporte.setPreferredSize(new Dimension(130, ConstructorComponentes.ALTURA_CAMPO));
        spFechaReporte.setVisible(false);
        
        btnGenerar = ConstructorComponentes.crearBotonPrimario("Generar Reporte");

        btnGenerarJSON = ConstructorComponentes.crearBotonPrimario("Exportar a JSON");

        panelControles.add(lblSeleccion);
        panelControles.add(cbTipoReporte);
        panelControles.add(lblFechaReporte);
        panelControles.add(spFechaReporte);
        panelControles.add(btnGenerar);
        panelControles.add(btnGenerarJSON);

        JPanel panelEncabezado = new JPanel(new BorderLayout(0, 10));
        panelEncabezado.setOpaque(false);
        panelEncabezado.add(ConstructorComponentes.crearLabelTitulo("Reportes"), BorderLayout.NORTH);
        panelEncabezado.add(panelControles, BorderLayout.CENTER);
        this.add(panelEncabezado, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tablaReportes = new JTable(modeloTabla);
        tablaReportes.setFont(new Font(ConstructorComponentes.FUENTE_UI, Font.PLAIN, ConstructorComponentes.TAMANIO_CAMPO));
        tablaReportes.setRowHeight(30);
        tablaReportes.getTableHeader().setFont(new Font(ConstructorComponentes.FUENTE_UI, Font.BOLD, ConstructorComponentes.TAMANIO_LABEL));
        tablaReportes.getTableHeader().setReorderingAllowed(false); 
        
        JScrollPane scrollPane = new JScrollPane(tablaReportes);
        this.add(scrollPane, BorderLayout.CENTER);
    }


    public JComboBox<String> getCbTipoReporte() {
        return cbTipoReporte;
    }

    public JButton getBtnGenerar() {
        return btnGenerar;
    }

    public JButton getBtnGenerarJSON() {
        return btnGenerarJSON;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JSpinner getSpFechaReporte() {
        return spFechaReporte;
    }

    public JLabel getLblFechaReporte() {
        return lblFechaReporte;
    }
}