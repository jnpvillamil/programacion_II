package co.edu.uptc.gui;

import java.awt.BorderLayout;
import javax.swing.*;

@SuppressWarnings("serial")
public class ConsultaGUI extends JFrame {

    private JTextField txtCodigoProveedor;
    private JTextArea areaResultados;
    private JButton btnConsultar;
    private JButton btnLimpiar;

    public ConsultaGUI() {
        setTitle("Consultas");
        setSize(550, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
    	
    	JPanel panelBase= new JPanel(new BorderLayout());
    	panelBase.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
        JPanel superior = new JPanel();
        superior.add(new JLabel("Código proveedor:"));
        txtCodigoProveedor = new JTextField(15);
        superior.add(txtCodigoProveedor);

        btnConsultar = new JButton("Consultar");
        btnLimpiar = new JButton("Limpiar");

        btnConsultar.setActionCommand("CONSULTAR_COMPRAS_PROVEEDOR");
        btnLimpiar.setActionCommand("LIMPIAR_CONSULTA");

        superior.add(btnConsultar);
        superior.add(btnLimpiar);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);

        panelBase.add(superior, BorderLayout.NORTH);
        panelBase.add(new JScrollPane(areaResultados), BorderLayout.CENTER);
        add(panelBase);
    }

    public String getCodigoProveedor() {
        return txtCodigoProveedor.getText().trim();
    }

    public void setResultados(String texto) {
        areaResultados.setText(texto);
    }

    public void limpiar() {
        txtCodigoProveedor.setText("");
        areaResultados.setText("");
    }

    public JButton getBtnConsultar() {
        return btnConsultar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }
}