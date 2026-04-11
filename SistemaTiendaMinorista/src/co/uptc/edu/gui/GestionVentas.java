package co.uptc.edu.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GestionVentas extends JFrame {

    public GestionVentas() {

        setTitle("Gestión de Ventas");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(20,20));

        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelDetalle(), BorderLayout.CENTER);
        add(crearPanelResumen(), BorderLayout.SOUTH);
    }

    // ===============================
    // PANEL SUPERIOR - DATOS VENTA
    // ===============================
    private JPanel crearPanelSuperior() {

        JPanel panel = new JPanel(new GridLayout(2,4,20,15));
        panel.setBorder(new TitledBorder("Datos de la Venta"));

        panel.add(new JLabel("Número de Factura:"));
        panel.add(new JTextField());

        panel.add(new JLabel("Fecha:"));
        panel.add(new JTextField("DD/MM/AAAA"));

        panel.add(new JLabel("Hora:"));
        panel.add(new JTextField("HH:MM"));

        panel.add(new JLabel("Cliente:"));
        panel.add(new JComboBox<>(new String[]{"Seleccione Cliente"}));

        return panel;
    }

    // ===============================
    // PANEL CENTRAL - DETALLE PRODUCTOS
    // ===============================
    private JPanel crearPanelDetalle() {

        JPanel panel = new JPanel(new BorderLayout(15,15));
        panel.setBorder(new TitledBorder("Detalle de Productos"));

        String[] columnas = {
                "Código", "Producto",
                "Cantidad", "Precio Unitario",
                "Subtotal"
        };

        JTable tabla = new JTable(new DefaultTableModel(columnas, 0));
        tabla.setRowHeight(28);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));

        botones.add(new JButton("Agregar Producto"));
        botones.add(new JButton("Eliminar Producto"));
        botones.add(new JButton("Limpiar Lista"));

        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    // ===============================
    // PANEL INFERIOR - RESUMEN Y PAGO
    // ===============================
    private JPanel crearPanelResumen() {

        JPanel panel = new JPanel(new BorderLayout(15,15));
        panel.setBorder(new TitledBorder("Resumen de la Venta"));

        // Parte izquierda (Totales)
        JPanel totales = new JPanel(new GridLayout(3,2,15,10));

        totales.add(new JLabel("IVA:"));
        totales.add(new JTextField());

        totales.add(new JLabel("Total Venta:"));
        JTextField totalField = new JTextField();
        totalField.setFont(new Font("Arial", Font.BOLD, 16));
        totales.add(totalField);

        totales.add(new JLabel("Forma de Pago:"));

        JPanel formasPago = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formasPago.add(new JCheckBox("Efectivo"));
        formasPago.add(new JCheckBox("Transferencia"));
        formasPago.add(new JCheckBox("Tarjeta"));
        formasPago.add(new JCheckBox("Crédito"));

        totales.add(formasPago);

        panel.add(totales, BorderLayout.CENTER);

        // Parte derecha (Botones)
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        botones.add(new JButton("Registrar Venta"));
        botones.add(new JButton("Anular Venta"));
        botones.add(new JButton("Devolución"));

        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GestionVentas().setVisible(true);
        });
    }
}
