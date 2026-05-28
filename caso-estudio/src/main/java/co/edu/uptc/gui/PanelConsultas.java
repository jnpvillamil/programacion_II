package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class PanelConsultas extends PanelBase {

    public static final String STOCK_BAJO = "Productos con stock bajo el mínimo";
    public static final String COMPRAS_PROVEEDOR = "Compras por proveedor";
    public static final String RESUMEN_CONTABLE = "Resumen contable por periodo";
    public static final String TOTAL_VENTAS = "Total ventas por periodo";
    public static final String UTILIDAD_FINANCIERA = "Utilidad bruta por periodo";

    private JComboBox<String> cbTipoConsulta;
    private JButton btnConsultar;
    private JTable tablaConsultas;
    private DefaultTableModel modeloTabla;

    private JLabel lblProveedor;
    private JTextField txtProveedor;
    private JLabel lblPeriodo;
    private JComboBox<String> cbPeriodo;
    private JLabel lblFechaInicio;
    private JSpinner spFechaInicio;
    private JLabel lblFechaFin;
    private JSpinner spFechaFin;
    private JPanel panelFiltros;

    public PanelConsultas() {
        super();
        initComponents();
    }

    @Override
    public void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(ConstructorComponentes.crearLabelTitulo("Consultas"), BorderLayout.NORTH);

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.setOpaque(false);

        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelControles.setOpaque(false);

        cbTipoConsulta = ConstructorComponentes.crearComboBox(new String[]{
                STOCK_BAJO,
                COMPRAS_PROVEEDOR,
                RESUMEN_CONTABLE,
                TOTAL_VENTAS,
                UTILIDAD_FINANCIERA
        });
        cbTipoConsulta.setPreferredSize(new Dimension(320, ConstructorComponentes.ALTURA_CAMPO));
        btnConsultar = ConstructorComponentes.crearBotonPrimario("Consultar");

        panelControles.add(ConstructorComponentes.crearLabelFormulario("Tipo de consulta:"));
        panelControles.add(cbTipoConsulta);
        panelControles.add(btnConsultar);

        panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelFiltros.setOpaque(false);

        lblProveedor = ConstructorComponentes.crearLabelFormulario("Código proveedor:");
        txtProveedor = ConstructorComponentes.crearCampoTexto();
        txtProveedor.setColumns(12);

        lblPeriodo = ConstructorComponentes.crearLabelFormulario("Periodo:");
        cbPeriodo = ConstructorComponentes.crearComboBox(new String[]{"DIARIO", "MENSUAL", "ANUAL"});

        lblFechaInicio = ConstructorComponentes.crearLabelFormulario("Desde:");
        spFechaInicio = crearSpinnerFecha();
        lblFechaFin = ConstructorComponentes.crearLabelFormulario("Hasta:");
        spFechaFin = crearSpinnerFecha();

        panelSuperior.add(panelControles, BorderLayout.NORTH);
        panelSuperior.add(panelFiltros, BorderLayout.CENTER);

        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaConsultas = new JTable(modeloTabla);
        tablaConsultas.setRowHeight(25);

        JPanel panelContenido = new JPanel(new BorderLayout(10, 10));
        panelContenido.setOpaque(false);
        panelContenido.add(panelSuperior, BorderLayout.NORTH);
        panelContenido.add(new JScrollPane(tablaConsultas), BorderLayout.CENTER);

        add(panelContenido, BorderLayout.CENTER);

        actualizarFiltrosVisibles();
    }

    private JSpinner crearSpinnerFecha() {
        JSpinner spinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        editor.getTextField().setFont(new Font(ConstructorComponentes.FUENTE_UI, Font.PLAIN, ConstructorComponentes.TAMANIO_CAMPO));
        spinner.setEditor(editor);
        spinner.setPreferredSize(new Dimension(130, ConstructorComponentes.ALTURA_CAMPO));
        return spinner;
    }

    public void actualizarFiltrosVisibles() {
        panelFiltros.removeAll();
        String seleccion = (String) cbTipoConsulta.getSelectedItem();

        if (COMPRAS_PROVEEDOR.equals(seleccion)) {
            panelFiltros.add(lblProveedor);
            panelFiltros.add(txtProveedor);
            panelFiltros.add(lblFechaInicio);
            panelFiltros.add(spFechaInicio);
            panelFiltros.add(lblFechaFin);
            panelFiltros.add(spFechaFin);
        } else if (RESUMEN_CONTABLE.equals(seleccion)) {
            panelFiltros.add(lblFechaInicio);
            panelFiltros.add(spFechaInicio);
            panelFiltros.add(lblFechaFin);
            panelFiltros.add(spFechaFin);
        } else if (TOTAL_VENTAS.equals(seleccion)) {
            panelFiltros.add(lblPeriodo);
            panelFiltros.add(cbPeriodo);
            panelFiltros.add(lblFechaInicio);
            panelFiltros.add(spFechaInicio);
        } else if (UTILIDAD_FINANCIERA.equals(seleccion)) {
            panelFiltros.add(lblFechaInicio);
            panelFiltros.add(spFechaInicio);
            panelFiltros.add(lblFechaFin);
            panelFiltros.add(spFechaFin);
        }

        panelFiltros.revalidate();
        panelFiltros.repaint();
    }

    public JComboBox<String> getCbTipoConsulta() {
        return cbTipoConsulta;
    }

    public JButton getBtnConsultar() {
        return btnConsultar;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JTextField getTxtProveedor() {
        return txtProveedor;
    }

    public JComboBox<String> getCbPeriodo() {
        return cbPeriodo;
    }

    public JSpinner getSpFechaInicio() {
        return spFechaInicio;
    }

    public JSpinner getSpFechaFin() {
        return spFechaFin;
    }
}
