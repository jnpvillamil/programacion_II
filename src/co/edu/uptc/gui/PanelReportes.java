package co.edu.uptc.gui;

import co.edu.uptc.negocio.SistemaGestion;
import javax.swing.*;
import java.awt.*;

public class PanelReportes extends JPanel {
    private SistemaGestion sis;

    public PanelReportes(SistemaGestion sis) {
        this.sis = sis;
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(244, 246, 249));

        JLabel lblTitulo = new JLabel("Generador de Reportes");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelNorte.setOpaque(false);
        
        JComboBox<String> cbTipoReporte = new JComboBox<>(new String[]{
            "Ventas Totales", 
            "Inventario Valorizado", 
            "Productos Más Vendidos"
        });
        
        JButton btnGenerar = new JButton("Generar Reporte");
        btnGenerar.setBackground(new Color(52, 152, 219));
        btnGenerar.setForeground(Color.WHITE);

        panelNorte.add(new JLabel("Seleccione el reporte:"));
        panelNorte.add(cbTipoReporte);
        panelNorte.add(btnGenerar);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(lblTitulo, BorderLayout.NORTH);
        headerPanel.add(panelNorte, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        JTextArea areaReporte = new JTextArea();
        areaReporte.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaReporte.setEditable(false);
        add(new JScrollPane(areaReporte), BorderLayout.CENTER);

        btnGenerar.addActionListener(e -> {
            String seleccionado = cbTipoReporte.getSelectedItem().toString();
            areaReporte.setText("--------------------------------------------\n");
            areaReporte.append("       REPORTE: " + seleccionado.toUpperCase() + "\n");
            areaReporte.append("---------------------------------------------\n\n");
            areaReporte.append("Generando datos desde el sistema...\n");
            // falta los métodos de generación de reportes de SistemaGestion
        });
    }
}