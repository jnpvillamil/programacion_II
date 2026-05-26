package co.uptc.edu.tienda.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.tienda.enums.TipoMovimiento;
import co.uptc.edu.tienda.modelo.MovimientoInventario;

public class PanelInventario extends JPanel {

    private JTable tablaMovimientos;
    private DefaultTableModel modeloMovimientos;
    private List<MovimientoInventario> listaMovimientos;
    private JTextField txtDesde;
    private JTextField txtHasta;
    private JComboBox<String> comboTipo;
    private JLabel lblTotalMovimientos, lblDetalle;

    public PanelInventario(Evento evento) {

        this.listaMovimientos = new ArrayList<>();
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));

        // =====================================
        // TITULO
        // =====================================
        JLabel titulo = new JLabel("MOVIMIENTOS DE INVENTARIO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        // =====================================
        // FILTROS
        // =====================================
        JPanel filtro = new JPanel();
        filtro.setBorder(BorderFactory.createTitledBorder("Filtrar"));
        filtro.setBackground(new Color(240, 240, 240));

        txtDesde = new JTextField(10);
        txtHasta = new JTextField(10);
        txtDesde.setToolTipText("yyyy-MM-dd");
        txtHasta.setToolTipText("yyyy-MM-dd");

        comboTipo = new JComboBox<>(new String[]{"TODOS", "ENTRADA", "SALIDA"});

        JButton btnFiltrar = new JButton("Filtrar");
        JButton btnVerTodos = new JButton("Ver todos");
        JButton btnHoy = new JButton("Movimientos de hoy");

        btnHoy.setBackground(new Color(41, 128, 185));
        btnHoy.setForeground(Color.WHITE);
        btnHoy.setFocusPainted(false);

        btnFiltrar.addActionListener(e -> filtrar());
        btnVerTodos.addActionListener(e -> {
            txtDesde.setText("");
            txtHasta.setText("");
            comboTipo.setSelectedIndex(0);
            poblarTabla();
        });
        btnHoy.addActionListener(e -> mostrarHoy());

        filtro.add(new JLabel("Desde:"));
        filtro.add(txtDesde);
        filtro.add(new JLabel("Hasta:"));
        filtro.add(txtHasta);
        filtro.add(new JLabel("Tipo:"));
        filtro.add(comboTipo);
        filtro.add(btnFiltrar);
        filtro.add(btnVerTodos);
        filtro.add(btnHoy);

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
        modeloMovimientos.addColumn("ID");
        modeloMovimientos.addColumn("Producto");
        modeloMovimientos.addColumn("Tipo");
        modeloMovimientos.addColumn("Cantidad");
        modeloMovimientos.addColumn("Fecha");
        modeloMovimientos.addColumn("Motivo");

        tablaMovimientos = new JTable(modeloMovimientos);
        tablaMovimientos.setRowHeight(25);

        // Colorear filas según tipo
        tablaMovimientos.setDefaultRenderer(Object.class,
            new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public java.awt.Component getTableCellRendererComponent(
                        JTable table, Object value, boolean isSelected,
                        boolean hasFocus, int row, int column) {
                    super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    if (!isSelected) {
                        String tipo = table.getValueAt(row, 2).toString();
                        if (tipo.equals("ENTRADA")) {
                            setBackground(new Color(212, 239, 223)); // verde claro
                        } else {
                            setBackground(new Color(250, 219, 216)); // rojo claro
                        }
                    }
                    return this;
                }
            });

        JScrollPane scroll = new JScrollPane(tablaMovimientos);
        scroll.setBorder(BorderFactory.createTitledBorder("Movimientos"));
        add(scroll, BorderLayout.CENTER);

        // =====================================
        // PANEL INFERIOR
        // =====================================
        JPanel inferior = new JPanel(new BorderLayout(10, 10));
        inferior.setBorder(BorderFactory.createTitledBorder("Detalle del movimiento seleccionado"));
        inferior.setBackground(new Color(240, 240, 240));
        inferior.setPreferredSize(new java.awt.Dimension(0, 80));

        lblTotalMovimientos = new JLabel("Movimientos visibles: 0");
        lblTotalMovimientos.setFont(new Font("Arial", Font.BOLD, 13));
        lblTotalMovimientos.setForeground(new Color(39, 174, 96));

        lblDetalle = new JLabel("Seleccione un movimiento para ver el detalle");
        lblDetalle.setFont(new Font("Arial", Font.PLAIN, 13));
        lblDetalle.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        inferior.add(lblTotalMovimientos, BorderLayout.NORTH);
        inferior.add(lblDetalle, BorderLayout.CENTER);
        add(inferior, BorderLayout.SOUTH);
        
        tablaMovimientos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDetalle();
            }
        });
    }
    
    

    // =====================================
    // POBLAR TABLA (todos los movimientos)
    // =====================================
    private void poblarTabla() {
        modeloMovimientos.setRowCount(0);
        for (MovimientoInventario m : listaMovimientos) {
            modeloMovimientos.addRow(new Object[]{
                m.getIdMovimiento(),
                m.getProducto() != null ? m.getProducto().getNombreProducto() : "N/A",
                m.getTipoMovimiento(),
                m.getCantidad(),
                m.getFechaHora(),
                m.getMotivo()
            });
        }
        actualizarContador();
    }

    // =====================================
    // FILTRAR POR FECHA Y TIPO
    // =====================================
    private void filtrar() {
        String desde = txtDesde.getText().trim();
        String hasta = txtHasta.getText().trim();
        String tipo = comboTipo.getSelectedItem().toString();

        modeloMovimientos.setRowCount(0);

        for (MovimientoInventario m : listaMovimientos) {
            String fechaSolo = m.getFechaHora().substring(0, 10);
            boolean cumple = true;

            if (!desde.isEmpty() && fechaSolo.compareTo(desde) < 0) cumple = false;
            if (!hasta.isEmpty() && fechaSolo.compareTo(hasta) > 0) cumple = false;
            if (!tipo.equals("TODOS") && !m.getTipoMovimiento().name().equals(tipo)) cumple = false;

            if (cumple) {
                modeloMovimientos.addRow(new Object[]{
                    m.getIdMovimiento(),
                    m.getProducto() != null ? m.getProducto().getNombreProducto() : "N/A",
                    m.getTipoMovimiento(),
                    m.getCantidad(),
                    m.getFechaHora(),
                    m.getMotivo()
                });
            }
        }
        actualizarContador();
    }

    // =====================================
    // MOVIMIENTOS DE HOY
    // =====================================
    private void mostrarHoy() {
        String hoy = LocalDate.now().toString();
        txtDesde.setText(hoy);
        txtHasta.setText(hoy);
        comboTipo.setSelectedIndex(0);
        filtrar();
    }

    // =====================================
    // ACTUALIZAR CONTADOR
    // =====================================
    private void actualizarContador() {
        lblTotalMovimientos.setText(
                "Movimientos visibles: " + modeloMovimientos.getRowCount());
    }

    // =====================================
    // REFRESCAR DESDE VentanaPrincipal
    // =====================================
    public void refrescar(List<MovimientoInventario> movimientos) {
        this.listaMovimientos = movimientos;
        mostrarHoy();
    }
    
    private void mostrarDetalle() {
        int fila = tablaMovimientos.getSelectedRow();
        if (fila == -1) {
            lblDetalle.setText("Seleccione un movimiento para ver el detalle");
            return;
        }

        String id = modeloMovimientos.getValueAt(fila, 0).toString();
        String producto = modeloMovimientos.getValueAt(fila, 1).toString();
        String tipo = modeloMovimientos.getValueAt(fila, 2).toString();
        String cantidad = modeloMovimientos.getValueAt(fila, 3).toString();        
        String motivo = modeloMovimientos.getValueAt(fila, 5).toString();

        lblDetalle.setText(
            "ID: " + id +
            "  |  Producto: " + producto +
            "  |  Tipo: " + tipo +
            "  |  Cantidad: " + cantidad +
            "  |  Motivo: " + motivo
        );
    }
}