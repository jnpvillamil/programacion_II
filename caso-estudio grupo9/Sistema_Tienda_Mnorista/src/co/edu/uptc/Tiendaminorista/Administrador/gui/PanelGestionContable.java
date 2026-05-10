package co.edu.uptc.Tiendaminorista.Administrador.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.Tiendaminorista.modelo.MovimientoContable;
import co.edu.uptc.Tiendaminorista.negocio.GestionContable;
import co.edu.uptc.Tiendaminorista.persistencia.LocalContable;

public class PanelGestionContable extends JPanel {

    private static final long serialVersionUID = 1L;

    private GestionContable gestionContable;
    private JTable tablaMovimientos;
    private DefaultTableModel modeloTabla;

    private JRadioButton rbMostrarTodo, rbCadaVenta, rbCadaCompra, rbCadaMovimiento;
    private ButtonGroup grupoMostrar;
    private JTextField txtCuentaContable, txtDesde, txtHasta;
    private JButton btnGenerar, btnFiltrar, btnBuscar;
    
    private JLabel lblTotalIngresos, lblTotalEgresos, lblSaldo;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PanelGestionContable() {
        this.gestionContable = new GestionContable(new LocalContable());
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(crearPanelFiltros(), BorderLayout.NORTH);
        add(crearPanelTabla(), BorderLayout.CENTER);
        add(crearPanelResumen(), BorderLayout.SOUTH);

        cargarTabla(gestionContable.listarMovimientos());
    }

    private JPanel crearPanelFiltros() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new TitledBorder("Gestión contable"));

        JPanel panelFiltros = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Mostrar
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelFiltros.add(new JLabel("Mostrar:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        JPanel panelRadios = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));

        rbMostrarTodo = new JRadioButton("Mostrar todo", true);
        rbCadaVenta = new JRadioButton("cada venta");
        rbCadaCompra = new JRadioButton("cada compra");
        rbCadaMovimiento = new JRadioButton("Cada movimiento contable");

        grupoMostrar = new ButtonGroup();
        grupoMostrar.add(rbMostrarTodo);
        grupoMostrar.add(rbCadaVenta);
        grupoMostrar.add(rbCadaCompra);
        grupoMostrar.add(rbCadaMovimiento);

        panelRadios.add(rbMostrarTodo);
        panelRadios.add(rbCadaVenta);
        panelRadios.add(rbCadaCompra);
        panelRadios.add(rbCadaMovimiento);

        panelFiltros.add(panelRadios, gbc);

        // Cuenta contable
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelFiltros.add(new JLabel("Cuenta contable:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        txtCuentaContable = new JTextField(25);
        panelFiltros.add(txtCuentaContable, gbc);

        // Fechas
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panelFiltros.add(new JLabel("desde:"), gbc);

        gbc.gridx = 1;
        txtDesde = new JTextField(10);
        txtDesde.setText("08/03/2004");
        panelFiltros.add(txtDesde, gbc);

        gbc.gridx = 2;
        panelFiltros.add(new JLabel("hasta:"), gbc);

        gbc.gridx = 3;
        txtHasta = new JTextField(10);
        txtHasta.setText(LocalDate.now().format(formatter));
        panelFiltros.add(txtHasta, gbc);

        // Botones
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnGenerar = new JButton("Generar");
        btnFiltrar = new JButton("Filtrar");
        btnBuscar = new JButton("Buscar");

        panelBotones.add(btnGenerar);
        panelBotones.add(btnFiltrar);
        panelBotones.add(btnBuscar);

        panelFiltros.add(panelBotones, gbc);

        panelPrincipal.add(panelFiltros, BorderLayout.CENTER);

        // Acciones
        btnGenerar.addActionListener(e -> generarReporte());
        btnFiltrar.addActionListener(e -> aplicarFiltros());
        btnBuscar.addActionListener(e -> buscarPorCuenta());

        return panelPrincipal;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnas = { "Código", "Fecha", "Tipo", "Cuenta contable", "Débito", "Crédito", "Descripción" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaMovimientos = new JTable(modeloTabla);
        tablaMovimientos.getTableHeader().setReorderingAllowed(false);
        tablaMovimientos.setRowHeight(25);

        // Centrar contenido
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columnas.length; i++) {
            tablaMovimientos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tablaMovimientos);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelResumen() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 20, 10));
        panel.setBorder(new TitledBorder("Resumen Financiero"));

        lblTotalIngresos = new JLabel("Total Ingresos: $0", SwingConstants.CENTER);
        lblTotalEgresos = new JLabel("Total Egresos: $0", SwingConstants.CENTER);
        lblSaldo = new JLabel("Saldo: $0", SwingConstants.CENTER);

        lblTotalIngresos.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalIngresos.setForeground(new Color(46, 125, 50));
        lblTotalEgresos.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalEgresos.setForeground(new Color(198, 40, 40));
        lblSaldo.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(lblTotalIngresos);
        panel.add(lblTotalEgresos);
        panel.add(lblSaldo);

        actualizarResumen();

        return panel;
    }

    private void actualizarResumen() {
        lblTotalIngresos.setText(String.format("Total Ingresos: $%,.0f", gestionContable.getTotalIngresos()));
        lblTotalEgresos.setText(String.format("Total Egresos: $%,.0f", gestionContable.getTotalEgresos()));
        lblSaldo.setText(String.format("Saldo: $%,.0f", gestionContable.getSaldoActual()));
        
        if (gestionContable.getSaldoActual() >= 0) {
            lblSaldo.setForeground(new Color(46, 125, 50));
        } else {
            lblSaldo.setForeground(new Color(198, 40, 40));
        }
    }

    private void aplicarFiltros() {
        List<MovimientoContable> resultado = gestionContable.listarMovimientos();

        if (rbCadaVenta.isSelected()) {
            resultado = gestionContable.filtrarPorTipo("Ingreso");
        } else if (rbCadaCompra.isSelected()) {
            resultado = gestionContable.filtrarPorTipo("Egreso");
        }

        try {
            String desdeStr = txtDesde.getText().trim();
            String hastaStr = txtHasta.getText().trim();

            if (!desdeStr.isEmpty() && !hastaStr.isEmpty()) {
                LocalDate desde = LocalDate.parse(desdeStr, formatter);
                LocalDate hasta = LocalDate.parse(hastaStr, formatter);
                resultado = gestionContable.filtrarPorRangoFechas(desde, hasta);
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Error en formato de fecha. Use dd/mm/aaaa");
        }

        cargarTabla(resultado);
    }

    private void buscarPorCuenta() {
        String cuenta = txtCuentaContable.getText().trim();
        if (!cuenta.isEmpty()) {
            List<MovimientoContable> resultado = gestionContable.buscarPorCuenta(cuenta);
            if (resultado.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron movimientos para: " + cuenta);
            }
            cargarTabla(resultado);
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese una cuenta contable para buscar");
        }
    }

    private void generarReporte() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE GESTIÓN CONTABLE ===\n");
        reporte.append("===================================\n\n");
        reporte.append(String.format("Total Ingresos: $%,.0f\n", gestionContable.getTotalIngresos()));
        reporte.append(String.format("Total Egresos: $%,.0f\n", gestionContable.getTotalEgresos()));
        reporte.append(String.format("Saldo Actual: $%,.0f\n\n", gestionContable.getSaldoActual()));
        reporte.append("=== ÚLTIMOS MOVIMIENTOS ===\n");
        
        List<MovimientoContable> movs = gestionContable.listarMovimientos();
        int inicio = Math.max(0, movs.size() - 10);
        for (int i = movs.size() - 1; i >= inicio; i--) {
            MovimientoContable m = movs.get(i);
            reporte.append(String.format("%s | %s | %s | $%,.0f | %s\n", 
                m.getCodigo(), 
                m.getFechaFormateada(), 
                m.getTipo(),
                m.getCredito() > 0 ? m.getCredito() : m.getDebito(),
                m.getDescripcion()));
        }

        JOptionPane.showMessageDialog(this, reporte.toString(), "Reporte Contable", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cargarTabla(List<MovimientoContable> lista) {
        modeloTabla.setRowCount(0);
        for (MovimientoContable m : lista) {
            modeloTabla.addRow(new Object[] {
                    m.getCodigo(),
                    m.getFechaFormateada(),
                    m.getTipo(),
                    m.getCuentaContable(),
                    m.getDebito() > 0 ? String.format("%,.0f", m.getDebito()) : "---",
                    m.getCredito() > 0 ? String.format("%,.0f", m.getCredito()) : "---",
                    m.getDescripcion()
            });
        }
        
        actualizarResumen();
    }

    public GestionContable getGestionContable() {
        return gestionContable;
    }

    public void actualizarDatos() {
        cargarTabla(gestionContable.listarMovimientos());
    }
}