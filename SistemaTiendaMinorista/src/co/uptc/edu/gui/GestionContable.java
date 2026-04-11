package co.uptc.edu.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GestionContable extends JFrame {

    public GestionContable() {

        setTitle("Gestión Contable");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(25,25));

        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearTablaMovimientos(), BorderLayout.CENTER);
        add(crearPanelConsultas(), BorderLayout.SOUTH);
    }

    // =========================================================
    // PANEL SUPERIOR – REGISTRO CONTABLE
    // =========================================================
    private JPanel crearPanelSuperior() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Registro de Movimiento Contable"));

        Insets espacio = new Insets(10,15,10,15);

        // Código Transacción
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.insets = espacio;
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Código Transacción:"), gbc1);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = espacio;
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JTextField(15), gbc2);

        // Fecha
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.insets = espacio;
        gbc3.gridx = 2;
        gbc3.gridy = 0;
        gbc3.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Fecha:"), gbc3);

        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.insets = espacio;
        gbc4.gridx = 3;
        gbc4.gridy = 0;
        gbc4.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JTextField("DD/MM/AAAA"), gbc4);

        // Tipo Movimiento
        GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.insets = espacio;
        gbc5.gridx = 0;
        gbc5.gridy = 1;
        gbc5.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Tipo Movimiento:"), gbc5);

        GridBagConstraints gbc6 = new GridBagConstraints();
        gbc6.insets = espacio;
        gbc6.gridx = 1;
        gbc6.gridy = 1;
        gbc6.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JComboBox<>(new String[]{"Ingreso", "Egreso"}), gbc6);

        // Cuenta Contable
        GridBagConstraints gbc7 = new GridBagConstraints();
        gbc7.insets = espacio;
        gbc7.gridx = 2;
        gbc7.gridy = 1;
        gbc7.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Cuenta Contable:"), gbc7);

        GridBagConstraints gbc8 = new GridBagConstraints();
        gbc8.insets = espacio;
        gbc8.gridx = 3;
        gbc8.gridy = 1;
        gbc8.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JComboBox<>(new String[]{
                "Caja",
                "Bancos",
                "Ingresos por Ventas",
                "IVA Generado",
                "Inventario",
                "IVA Descontable",
                "Proveedores"
        }), gbc8);

        // Valor
        GridBagConstraints gbc9 = new GridBagConstraints();
        gbc9.insets = espacio;
        gbc9.gridx = 0;
        gbc9.gridy = 2;
        gbc9.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Valor:"), gbc9);

        GridBagConstraints gbc10 = new GridBagConstraints();
        gbc10.insets = espacio;
        gbc10.gridx = 1;
        gbc10.gridy = 2;
        gbc10.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JTextField(), gbc10);

        // Descripción
        GridBagConstraints gbc11 = new GridBagConstraints();
        gbc11.insets = espacio;
        gbc11.gridx = 2;
        gbc11.gridy = 2;
        gbc11.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Descripción:"), gbc11);

        GridBagConstraints gbc12 = new GridBagConstraints();
        gbc12.insets = espacio;
        gbc12.gridx = 3;
        gbc12.gridy = 2;
        gbc12.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JTextField(), gbc12);

        return panel;
    }

    // =========================================================
    // TABLA DE MOVIMIENTOS CONTABLES
    // =========================================================
    private JScrollPane crearTablaMovimientos() {

        String[] columnas = {
                "Código",
                "Fecha",
                "Tipo",
                "Cuenta",
                "Débito",
                "Crédito",
                "Descripción"
        };

        JTable tabla = new JTable(new DefaultTableModel(columnas, 0));
        tabla.setRowHeight(30);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        return new JScrollPane(tabla);
    }

    // =========================================================
    // PANEL INFERIOR – CONSULTAS
    // =========================================================
    private JPanel crearPanelConsultas() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        panel.setBorder(new TitledBorder("Consultas y Reportes"));

        panel.add(new JLabel("Buscar por Fecha:"));
        panel.add(new JTextField(10));

        panel.add(new JLabel("Tipo:"));
        panel.add(new JComboBox<>(new String[]{"Todos", "Ingreso", "Egreso"}));

        panel.add(new JButton("Filtrar"));
        panel.add(new JButton("Generar Reporte"));

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GestionContable().setVisible(true);
        });
    }
}