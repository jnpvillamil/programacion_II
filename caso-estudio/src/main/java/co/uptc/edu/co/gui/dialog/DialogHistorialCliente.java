package co.uptc.edu.co.gui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DialogHistorialCliente extends JDialog {

    private JLabel etiquetaCodigoCliente;
    private JLabel etiquetaNombreCliente;

    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;

    public DialogHistorialCliente(Frame propietario) {
        super(propietario, "Historial de Compras del Cliente", true);
        inicializarComponentes();
        configurarDialogo();
        agregarComponentes();
    }

    private void inicializarComponentes() {
        etiquetaCodigoCliente = new JLabel("Código: ");
        etiquetaNombreCliente = new JLabel("Cliente: ");

        modeloTabla = new DefaultTableModel(
                new String[] { "Factura", "Fecha", "Forma de Pago", "Impuestos", "Total", "Estado" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaHistorial = new JTable(modeloTabla);
    }

    private void configurarDialogo() {
        setSize(700, 400);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);

        tablaHistorial.setRowHeight(25);
        tablaHistorial.getTableHeader().setReorderingAllowed(false);
    }

    private void agregarComponentes() {
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        panelSuperior.add(etiquetaCodigoCliente);
        panelSuperior.add(etiquetaNombreCliente);

        JScrollPane scroll = new JScrollPane(tablaHistorial);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    public void cargarCliente(String codigo, String nombre) {
        etiquetaCodigoCliente.setText("Código: " + codigo);
        etiquetaNombreCliente.setText("Cliente: " + nombre);
    }

    public void agregarFilaHistorial(Object[] fila) {
        modeloTabla.addRow(fila);
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }
}