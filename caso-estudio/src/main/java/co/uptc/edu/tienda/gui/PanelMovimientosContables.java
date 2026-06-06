package co.uptc.edu.tienda.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.tienda.modelo.MovimientoContable;

public class PanelMovimientosContables extends JPanel {

    private JTable tablaMovimientos;
    private DefaultTableModel modeloMovimientos;
    private JTextField txtCuenta;
    private JTextField txtDesde;
    private JTextField txtHasta;
    private JLabel lblTotal;
    private List<MovimientoContable> listaMovimientos;

    public PanelMovimientosContables() {

        this.listaMovimientos = new ArrayList<>();

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));

        // =====================================
        // TITULO
        // =====================================
        JLabel titulo = new JLabel("MOVIMIENTOS CONTABLES", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        // =====================================
        // FILTROS
        // =====================================
        JPanel filtro = new JPanel();
        filtro.setBorder(BorderFactory.createTitledBorder("Filtrar"));
        filtro.setBackground(new Color(240, 240, 240));

        txtCuenta = new JTextField(15);
        txtCuenta.setToolTipText("Ej: Caja, IVA generado, Inventario");
        txtDesde = new JTextField(10);
        txtHasta = new JTextField(10);
        txtDesde.setToolTipText("yyyy-MM-dd");
        txtHasta.setToolTipText("yyyy-MM-dd");

        JButton btnFiltrar = new JButton("Filtrar");
        JButton btnVerTodos = new JButton("Ver todos");

        btnFiltrar.addActionListener(e -> filtrar());
        btnVerTodos.addActionListener(e -> {
            txtCuenta.setText("");
            txtDesde.setText("");
            txtHasta.setText("");
            filtrar();
        });

        filtro.add(new JLabel("Cuenta:"));
        filtro.add(txtCuenta);
        filtro.add(new JLabel("Desde (yyyy-MM-dd):"));
        filtro.add(txtDesde);
        filtro.add(new JLabel("Hasta (yyyy-MM-dd):"));
        filtro.add(txtHasta);
        filtro.add(btnFiltrar);
        filtro.add(btnVerTodos);

        // =====================================
        // PANEL NORTE
        // =====================================
        JPanel norte = new JPanel(new BorderLayout());
        norte.setBackground(new Color(240, 240, 240));
        norte.add(titulo, BorderLayout.NORTH);
        norte.add(filtro, BorderLayout.SOUTH);
        add(norte, BorderLayout.NORTH);

        // =====================================
        // TABLA MOVIMIENTOS
        // =====================================
        modeloMovimientos = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        modeloMovimientos.addColumn("Código");
        modeloMovimientos.addColumn("Fecha");
        modeloMovimientos.addColumn("Tipo");
        modeloMovimientos.addColumn("Cuenta Contable");
        modeloMovimientos.addColumn("Valor");
        modeloMovimientos.addColumn("Descripción");
        modeloMovimientos.addColumn("Referencia Factura");

        tablaMovimientos = new JTable(modeloMovimientos);
        tablaMovimientos.setRowHeight(25);
        tablaMovimientos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollMovimientos = new JScrollPane(tablaMovimientos);
        scrollMovimientos.setBorder(BorderFactory.createTitledBorder("Movimientos"));
        add(scrollMovimientos, BorderLayout.CENTER);

        // =====================================
        // PANEL INFERIOR: total visible
        // =====================================
        JPanel inferior = new JPanel();
        inferior.setBackground(new Color(240, 240, 240));

        lblTotal = new JLabel("Total visible: $0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setForeground(new Color(52, 73, 94));

        inferior.add(lblTotal);
        add(inferior, BorderLayout.SOUTH);
    }

    // =====================================
    // FILTRAR POR CUENTA Y PERIODO
    // =====================================
    private void filtrar() {
        String cuenta = txtCuenta.getText().trim().toLowerCase();
        String desde  = txtDesde.getText().trim();
        String hasta  = txtHasta.getText().trim();

        modeloMovimientos.setRowCount(0);

        for (MovimientoContable m : listaMovimientos) {
            boolean cumpleCuenta = cuenta.isEmpty()
                    || m.getCuentaContable().toLowerCase().contains(cuenta);

            boolean cumpleFecha = true;
            if (!desde.isEmpty() && m.getFecha().compareTo(desde) < 0) cumpleFecha = false;
            if (!hasta.isEmpty() && m.getFecha().compareTo(hasta) > 0) cumpleFecha = false;

            if (cumpleCuenta && cumpleFecha) {
                modeloMovimientos.addRow(new Object[]{
                    m.getCodigoTransaccion(),
                    m.getFecha(),
                    m.getTipoMovimiento().name(),
                    m.getCuentaContable(),
                    String.format("$%.2f", m.getValor()),
                    m.getDescripcion(),
                    m.getReferenciaFactura()
                });
            }
        }
        actualizarTotal();
    }

    // =====================================
    // TOTAL VISIBLE
    // =====================================
    private void actualizarTotal() {
        double total = 0;
        for (int i = 0; i < modeloMovimientos.getRowCount(); i++) {
            String referencia = modeloMovimientos.getValueAt(i, 6).toString();
            for (MovimientoContable m : listaMovimientos) {
                if (m.getReferenciaFactura().equals(referencia)
                        && m.getCuentaContable().equals(
                                modeloMovimientos.getValueAt(i, 3).toString())) {
                    total += m.getValor();
                    break;
                }
            }
        }
        lblTotal.setText("Total visible: $" + String.format("%.2f", total));
    }

    // =====================================
    // REFRESCAR DESDE VentanaPrincipal
    // =====================================
    public void refrescar(List<MovimientoContable> movimientos) {
        this.listaMovimientos = movimientos;
        txtCuenta.setText("");
        txtDesde.setText("");
        txtHasta.setText("");
        filtrar();
    }
}