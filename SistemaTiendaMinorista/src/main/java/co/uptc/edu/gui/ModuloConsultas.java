package co.uptc.edu.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ModuloConsultas extends JFrame {

    public ModuloConsultas() {

        setTitle("Consultas del Sistema");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Ventas por Fecha", crearConsultaVentas());
        tabs.add("Compras por Proveedor", crearConsultaCompras());
        tabs.add("Stock Bajo Mínimo", crearConsultaStock());
        tabs.add("Historial Cliente", crearConsultaHistorial());
        tabs.add("Movimientos Contables", crearConsultaContable());

        add(tabs);
    }

    // =====================================================
    // 1️⃣ VENTAS POR FECHA
    // =====================================================
    private JPanel crearConsultaVentas() {

        JPanel panel = new JPanel(new BorderLayout(15,15));
        panel.setBorder(new TitledBorder("Ventas por Fecha"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filtros.add(new JLabel("Fecha:"));
        filtros.add(new JTextField(10));
        filtros.add(new JButton("Buscar"));

        panel.add(filtros, BorderLayout.NORTH);

        String[] columnas = {"Factura", "Cliente", "Total", "IVA"};
        JTable tabla = new JTable(new DefaultTableModel(columnas, 0));
        tabla.setRowHeight(28);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        return panel;
    }

    // =====================================================
    // 2️⃣ COMPRAS POR PROVEEDOR
    // =====================================================
    private JPanel crearConsultaCompras() {

        JPanel panel = new JPanel(new BorderLayout(15,15));
        panel.setBorder(new TitledBorder("Compras por Proveedor"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filtros.add(new JLabel("Proveedor:"));
        filtros.add(new JComboBox<>(new String[]{"Seleccione Proveedor"}));
        filtros.add(new JLabel("Desde:"));
        filtros.add(new JTextField(8));
        filtros.add(new JLabel("Hasta:"));
        filtros.add(new JTextField(8));
        filtros.add(new JButton("Buscar"));

        panel.add(filtros, BorderLayout.NORTH);

        String[] columnas = {"Factura", "Fecha", "Total", "IVA"};
        JTable tabla = new JTable(new DefaultTableModel(columnas, 0));
        tabla.setRowHeight(28);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        return panel;
    }

    // =====================================================
    // 3️⃣ PRODUCTOS BAJO STOCK
    // =====================================================
    private JPanel crearConsultaStock() {

        JPanel panel = new JPanel(new BorderLayout(15,15));
        panel.setBorder(new TitledBorder("Productos con Stock Bajo Mínimo"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtros.add(new JButton("Actualizar Lista"));

        panel.add(filtros, BorderLayout.NORTH);

        String[] columnas = {"Código", "Producto", "Stock Actual", "Stock Mínimo"};
        JTable tabla = new JTable(new DefaultTableModel(columnas, 0));
        tabla.setRowHeight(28);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        return panel;
    }

    // =====================================================
    // 4️⃣ HISTORIAL CLIENTE
    // =====================================================
    private JPanel crearConsultaHistorial() {

        JPanel panel = new JPanel(new BorderLayout(15,15));
        panel.setBorder(new TitledBorder("Historial de Compras del Cliente"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtros.add(new JLabel("Cliente:"));
        filtros.add(new JComboBox<>(new String[]{"Seleccione Cliente"}));
        filtros.add(new JButton("Buscar"));

        panel.add(filtros, BorderLayout.NORTH);

        String[] columnas = {"Factura", "Fecha", "Total", "IVA"};
        JTable tabla = new JTable(new DefaultTableModel(columnas, 0));
        tabla.setRowHeight(28);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        return panel;
    }

    // =====================================================
    // 5️⃣ MOVIMIENTOS CONTABLES
    // =====================================================
    private JPanel crearConsultaContable() {

        JPanel panel = new JPanel(new BorderLayout(15,15));
        panel.setBorder(new TitledBorder("Movimientos Contables"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filtros.add(new JLabel("Cuenta:"));
        filtros.add(new JComboBox<>(new String[]{
                "Caja",
                "Bancos",
                "Ingresos por Ventas",
                "Inventario",
                "IVA Generado",
                "IVA Descontable",
                "Proveedores"
        }));
        filtros.add(new JLabel("Desde:"));
        filtros.add(new JTextField(8));
        filtros.add(new JLabel("Hasta:"));
        filtros.add(new JTextField(8));
        filtros.add(new JButton("Buscar"));

        panel.add(filtros, BorderLayout.NORTH);

        String[] columnas = {"Código", "Fecha", "Tipo", "Débito", "Crédito", "Descripción"};
        JTable tabla = new JTable(new DefaultTableModel(columnas, 0));
        tabla.setRowHeight(28);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ModuloConsultas().setVisible(true);
        });
    }
}