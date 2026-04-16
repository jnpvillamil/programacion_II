package co.edu.uptc.gui;

import co.edu.uptc.negocio.SistemaGestion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelContabilidad extends JPanel {
    private SistemaGestion sis;
    private DefaultTableModel modelo;

    public PanelContabilidad(SistemaGestion sis) {
        this.sis = sis;
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(244, 246, 249));

        JLabel lblTitulo = new JLabel("Libro Diario / Movimientos Contables");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        
        JButton btnConsultar = new JButton("Actualizar Registros");
        btnConsultar.setBackground(new Color(52, 152, 219));
        btnConsultar.setForeground(Color.WHITE);
        
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setOpaque(false);
        panelNorte.add(lblTitulo, BorderLayout.WEST);
        panelNorte.add(btnConsultar, BorderLayout.EAST);
        
        add(panelNorte, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"Código Trans.", "Fecha", "Tipo (Ingreso/Egreso)", "Cuenta", "Valor", "Descripción"}, 0);
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(44, 62, 80));
        tabla.getTableHeader().setForeground(Color.WHITE);
        
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnConsultar.addActionListener(e -> {
            modelo.setRowCount(0);
            // Carga desde la persistencia
            // for(MovimientoContable m : sis.getPersistenciaMovimiento().leerTodos()) { ... }
            JOptionPane.showMessageDialog(this, "Registros actualizados.");
        });
    }
}