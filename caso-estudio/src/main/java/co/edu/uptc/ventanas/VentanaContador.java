package co.edu.uptc.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import co.edu.uptc.conexion.Conexion;

public class VentanaContador extends JFrame {
	
   
	private static final long serialVersionUID = 1835237524801377535L;
	private JTable tablaReporte;
    private DefaultTableModel modeloTabla;
    private JLabel etiquetaGranTotal;

    public VentanaContador() {
        setTitle("Reporte Consolidado de Ventas - Panel de Contador");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLayout(new BorderLayout(15, 15));
        
        iniciarComponenteTabla();
        iniciarPanelBalance();
        cargarReporteGeneral();
    }

    private void iniciarComponenteTabla() {
        String[] columnas = {"N° Factura", "Código Cliente", "Código Producto", "Cantidad Vendida", "Total Facturado ($)"};
 
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaReporte = new JTable(modeloTabla);
        tablaReporte.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scroll = new JScrollPane(tablaReporte);
        scroll.setBorder(BorderFactory.createTitledBorder("Registro Histórico de Facturas Emitidas en el Turno"));
        add(scroll, BorderLayout.CENTER);
    }

    private void iniciarPanelBalance() {
        JPanel panelBalance = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 12));
        panelBalance.setBorder(BorderFactory.createEtchedBorder());
  
        etiquetaGranTotal = new JLabel("TOTAL RECAUDADO EN CAJA: $ 0.00 COP");
        etiquetaGranTotal.setFont(new Font("Arial", Font.BOLD, 16));
        etiquetaGranTotal.setForeground(new Color(0, 102, 51)); 
        
        panelBalance.add(etiquetaGranTotal);
        add(panelBalance, BorderLayout.SOUTH);
    }

    public void cargarReporteGeneral() {
        modeloTabla.setRowCount(0);
        Conexion conex = new Conexion();

        String sqlVentas = "SELECT numero_factura, codigo_cliente, codigo_producto, cantidad, total_venta FROM factura_venta";
        String sqlSuma = "SELECT SUM(total_venta) AS gran_total FROM factura_venta";
        
        double acumuladoCaja = 0.0;

        try (Connection c = conex.getConnection()) {
            if (c != null) {
                try (Statement st = c.createStatement();
                     ResultSet rs = st.executeQuery(sqlVentas)) {
                    while (rs.next()) {
                        Object[] fila = {
                            rs.getString("numero_factura"),
                            rs.getString("codigo_cliente"),
                            rs.getString("codigo_producto"),
                            rs.getInt("cantidad"),
                            rs.getDouble("total_venta")
                        };
                        modeloTabla.addRow(fila);
                    }
                }

                try (Statement stSuma = c.createStatement();
                     ResultSet rsSuma = stSuma.executeQuery(sqlSuma)) {
                    if (rsSuma.next()) {
                        acumuladoCaja = rsSuma.getDouble("gran_total");
                    }
                }

                etiquetaGranTotal.setText("TOTAL RECAUDO: $ " + acumuladoCaja + " COP");
            }
        } catch (SQLException e) {
            System.out.println("Error SQL al generar el reporte contable: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error al leer base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            conex.desconectar();
        }
    }

}
