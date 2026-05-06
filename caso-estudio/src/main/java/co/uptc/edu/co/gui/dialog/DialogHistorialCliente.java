package co.uptc.edu.co.gui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DialogHistorialCliente extends JDialog {

    private JLabel lblCodigoCliente;
    private JLabel lblNombreCliente;

    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;

    private JButton btnCerrar;

    public DialogHistorialCliente(Frame propietario) {
        super(propietario, "Historial de Compras del Cliente", true);
        setLayout(new BorderLayout());

        inicializarComponentes();
        configurarDialogo();
        agregarComponentes();
        agregarEventos();
    }

    private void inicializarComponentes() {
        lblCodigoCliente = new JLabel("Código: ");
        lblNombreCliente = new JLabel("Cliente: ");

        modeloTabla = new DefaultTableModel(
                new String[] { "Factura", "Fecha", "Forma de Pago", "Impuestos", "Total", "Estado" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaHistorial = new JTable(modeloTabla);
        btnCerrar = new JButton("Cerrar");
    }

    private void configurarDialogo() {
        setSize(750, 420);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        tablaHistorial.setRowHeight(25);
        tablaHistorial.getTableHeader().setReorderingAllowed(false);
    }

    private void agregarComponentes() {
        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearScrollTabla(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelSuperior() {
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));

        panelSuperior.add(lblCodigoCliente);
        panelSuperior.add(lblNombreCliente);

        return panelSuperior;
    }

    private JScrollPane crearScrollTabla() {
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Historial de Compras"));
        return scrollPane;
    }

    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.add(btnCerrar);
        return panelBotones;
    }

    private void agregarEventos() {
        btnCerrar.addActionListener(e -> dispose());
    }

    public void cargarCliente(String codigo, String nombre) {
        lblCodigoCliente.setText("Código: " + codigo);
        lblNombreCliente.setText("Cliente: " + nombre);
    }

    public void agregarFilaHistorial(Object[] fila) {
        modeloTabla.addRow(fila);
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    public JTable getTablaHistorial() {
        return tablaHistorial;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JButton getBtnCerrar() {
        return btnCerrar;
    }
}