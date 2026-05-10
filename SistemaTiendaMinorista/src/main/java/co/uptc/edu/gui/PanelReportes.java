package co.uptc.edu.gui;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelReportes extends JFrame {

    public PanelReportes() {

        setTitle("Panel de Reportes");
        setSize(1200, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Ventas", crearPanelVentas());
        tabs.add("Inventario", crearPanelInventario());
        tabs.add("Contabilidad", crearPanelContable());

        add(tabs);
    }

    // ============================
    // PANEL VENTAS
    // ============================
    private JPanel crearPanelVentas() {

        JPanel panel = new JPanel(new BorderLayout(20,20));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filtros.setBorder(new TitledBorder("Filtro por Periodo"));
        filtros.add(new JLabel("Desde:"));
        filtros.add(new JTextField(8));
        filtros.add(new JLabel("Hasta:"));
        filtros.add(new JTextField(8));
        filtros.add(new JButton("Generar Reporte"));

        panel.add(filtros, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(2,2,20,20));
        cards.setBorder(new TitledBorder("Resumen de Ventas"));

        // CARD 1
        JPanel card1 = new JPanel(new BorderLayout());
        card1.setBorder(new TitledBorder("Ventas Diarias"));
        JLabel v1 = new JLabel("$0", SwingConstants.CENTER);
        v1.setFont(new Font("Arial", Font.BOLD, 24));
        v1.setForeground(Color.BLUE);
        card1.add(v1, BorderLayout.CENTER);

        // CARD 2
        JPanel card2 = new JPanel(new BorderLayout());
        card2.setBorder(new TitledBorder("Ventas Mensuales"));
        JLabel v2 = new JLabel("$0", SwingConstants.CENTER);
        v2.setFont(new Font("Arial", Font.BOLD, 24));
        v2.setForeground(Color.BLUE);
        card2.add(v2, BorderLayout.CENTER);

        // CARD 3
        JPanel card3 = new JPanel(new BorderLayout());
        card3.setBorder(new TitledBorder("Ventas Anuales"));
        JLabel v3 = new JLabel("$0", SwingConstants.CENTER);
        v3.setFont(new Font("Arial", Font.BOLD, 24));
        v3.setForeground(Color.BLUE);
        card3.add(v3, BorderLayout.CENTER);

        // CARD 4
        JPanel card4 = new JPanel(new BorderLayout());
        card4.setBorder(new TitledBorder("Utilidad Bruta"));
        JLabel v4 = new JLabel("$0", SwingConstants.CENTER);
        v4.setFont(new Font("Arial", Font.BOLD, 24));
        v4.setForeground(Color.BLUE);
        card4.add(v4, BorderLayout.CENTER);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);

        panel.add(cards, BorderLayout.CENTER);

        JPanel tablas = new JPanel(new GridLayout(1,2,20,20));

        JPanel tablaProd = new JPanel(new BorderLayout());
        tablaProd.setBorder(new TitledBorder("Productos Más Vendidos"));
        JTable t1 = new JTable(new DefaultTableModel(
                new String[]{"Código","Producto","Cantidad"},0));
        tablaProd.add(new JScrollPane(t1), BorderLayout.CENTER);

        JPanel tablaClientes = new JPanel(new BorderLayout());
        tablaClientes.setBorder(new TitledBorder("Clientes con Mayor Volumen"));
        JTable t2 = new JTable(new DefaultTableModel(
                new String[]{"Código","Cliente","Total Comprado"},0));
        tablaClientes.add(new JScrollPane(t2), BorderLayout.CENTER);

        tablas.add(tablaProd);
        tablas.add(tablaClientes);

        panel.add(tablas, BorderLayout.SOUTH);

        return panel;
    }

    // ============================
    // PANEL INVENTARIO
    // ============================
    private JPanel crearPanelInventario() {

        JPanel panel = new JPanel(new BorderLayout(20,20));

        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(new TitledBorder("Inventario Valorizado"));
        JLabel valor = new JLabel("$0", SwingConstants.CENTER);
        valor.setFont(new Font("Arial", Font.BOLD, 24));
        valor.setForeground(Color.BLUE);
        card.add(valor, BorderLayout.CENTER);

        panel.add(card, BorderLayout.NORTH);

        JPanel tablaInv = new JPanel(new BorderLayout());
        tablaInv.setBorder(new TitledBorder("Estado de Inventario"));
        JTable tabla = new JTable(new DefaultTableModel(
                new String[]{"Código","Producto","Stock","Valor Total"},0));
        tablaInv.add(new JScrollPane(tabla), BorderLayout.CENTER);

        panel.add(tablaInv, BorderLayout.CENTER);

        return panel;
    }

    // ============================
    // PANEL CONTABLE
    // ============================
    private JPanel crearPanelContable() {

        JPanel panel = new JPanel(new BorderLayout(20,20));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filtros.setBorder(new TitledBorder("Periodo Contable"));
        filtros.add(new JLabel("Desde:"));
        filtros.add(new JTextField(8));
        filtros.add(new JLabel("Hasta:"));
        filtros.add(new JTextField(8));
        filtros.add(new JButton("Generar Resumen"));

        panel.add(filtros, BorderLayout.NORTH);

        JPanel resumen = new JPanel(new GridLayout(1,3,20,20));

        JPanel ingreso = new JPanel(new BorderLayout());
        ingreso.setBorder(new TitledBorder("Ingresos"));
        ingreso.add(new JLabel("$0", SwingConstants.CENTER), BorderLayout.CENTER);

        JPanel egreso = new JPanel(new BorderLayout());
        egreso.setBorder(new TitledBorder("Egresos"));
        egreso.add(new JLabel("$0", SwingConstants.CENTER), BorderLayout.CENTER);

        JPanel utilidad = new JPanel(new BorderLayout());
        utilidad.setBorder(new TitledBorder("Utilidad"));
        utilidad.add(new JLabel("$0", SwingConstants.CENTER), BorderLayout.CENTER);

        resumen.add(ingreso);
        resumen.add(egreso);
        resumen.add(utilidad);

        panel.add(resumen, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PanelReportes().setVisible(true);
        });
    }
}