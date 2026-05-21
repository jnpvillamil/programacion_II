package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelContabilidad extends JPanel {
    public PanelContabilidad() {
        setLayout(new BorderLayout(20, 20));
        setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = ConstructorComponentes.crearEtiquetaNegrita("HISTORIAL DE MOVIMIENTOS CONTABLES");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Tabla de Movimientos
        String[] cols = {"Fecha", "Tipo", "Cuenta Contable", "Valor", "Descripción"};
        JTable tabla = new JTable(new DefaultTableModel(cols, 0));
        ConstructorComponentes.darEstiloTabla(tabla);
        
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // Panel Inferior (Filtros o Reportes)
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        JButton btnReporte = ConstructorComponentes.crearBotonAccion("Generar Reporte", ConstructorComponentes.COLOR_MENU_OSCURO);
        panelSur.add(btnReporte);
        
        add(panelSur, BorderLayout.SOUTH);
    }
}