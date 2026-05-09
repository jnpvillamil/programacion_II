package co.edu.uptc.sistienda.contabilidad.gui;

import java.awt.BorderLayout;
import java.awt.Label;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import co.edu.uptc.sistienda.modelo.Venta;

public class PanelContabilidad extends JPanel{
	
	private JLabel lblTotalVentas;
	
	public PanelContabilidad() {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Módulo de Contabilidad", JLabel.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(18f));

        add(titulo, BorderLayout.NORTH);
        
     // Label total ventas
        lblTotalVentas = new JLabel("Total ventas: $0", JLabel.CENTER);
        

     // Área de reportes
        JTextArea area = new JTextArea();
        area.setText("Aquí verás reportes financieros...");
        
        add(lblTotalVentas, BorderLayout.SOUTH);
        add(new JScrollPane(area), BorderLayout.CENTER);
    }
	
	 public void cargarTotalVentas(List<Venta> ventas) {

	        double totalVentas = 0;

	        for (Venta venta : ventas) {
	            totalVentas += venta.getTotal();
	        }

	        lblTotalVentas.setText("Total ventas: $" + totalVentas);
	    }

}
