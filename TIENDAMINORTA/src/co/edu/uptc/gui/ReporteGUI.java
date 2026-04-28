package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;

import co.edu.uptc.enums.TipoReporteEnum;
import co.edu.uptc.gui.modelo.Reporte;

@SuppressWarnings("serial")
public class ReporteGUI extends JFrame {

    private JComboBox<TipoReporteEnum> cbTipoReporte;
    private JTextField txtPeriodo;
    private JTextArea areaResultado;
    private JButton btnGenerar;

    public ReporteGUI() {
        setTitle("Reportes");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
    	
    	JPanel panelBase= new JPanel(new BorderLayout());
    	panelBase.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
        JPanel panel = new JPanel(new GridLayout(2, 2, 8, 8));
        cbTipoReporte = new JComboBox<>(TipoReporteEnum.values());
        txtPeriodo = new JTextField();

        panel.add(new JLabel("Tipo reporte:"));
        panel.add(cbTipoReporte);
        panel.add(new JLabel("Periodo:"));
        panel.add(txtPeriodo);

        btnGenerar = new JButton("Generar");
        btnGenerar.setActionCommand("GENERAR_REPORTE");

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);

        panelBase.add(panel, BorderLayout.NORTH);
        panelBase.add(btnGenerar, BorderLayout.CENTER);
        panelBase.add(new JScrollPane(areaResultado), BorderLayout.SOUTH);
        
        add(panelBase);
    }

    public Reporte obtenerReporteFormulario() {
        Reporte reporte = new Reporte();
        reporte.setTipoReporte((TipoReporteEnum) cbTipoReporte.getSelectedItem());
        reporte.setPeriodo(txtPeriodo.getText().trim());
        return reporte;
    }

    public void mostrarResultado(String texto) {
        areaResultado.setText(texto);
    }

    public JButton getBtnGenerar() {
        return btnGenerar;
    }
}