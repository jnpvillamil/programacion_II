package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.gui.modelo.Nomina;

@SuppressWarnings("serial") //explicar
public class NominaGUI extends JFrame {

    private JTextField txtCodigoNomina;
    private JTextField txtCodigoEmpleado;
    private JTextField txtDevengado;
    private JTextField txtDescuentos;

    private JButton btnRegistrar;
    private JButton btnBuscar;
    private JButton btnLimpiar;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public NominaGUI() {
        setTitle("Gestión de Nómina");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
    	JPanel panelBase= new JPanel(new BorderLayout());
    	panelBase.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));

        txtCodigoNomina = new JTextField();
        txtCodigoEmpleado = new JTextField();
        txtDevengado = new JTextField();
        txtDescuentos = new JTextField();

        panel.add(new JLabel("Código nómina:"));
        panel.add(txtCodigoNomina);
        panel.add(new JLabel("Código empleado:"));
        panel.add(txtCodigoEmpleado);
        panel.add(new JLabel("Devengado:"));
        panel.add(txtDevengado);
        panel.add(new JLabel("Descuentos:"));
        panel.add(txtDescuentos);

        JPanel botones = new JPanel();
        btnRegistrar = new JButton("Registrar");
        btnBuscar = new JButton("Buscar");
        btnLimpiar = new JButton("Limpiar");

        btnRegistrar.setActionCommand("REGISTRAR_NOMINA");
        btnBuscar.setActionCommand("BUSCAR_NOMINA");
        btnLimpiar.setActionCommand("LIMPIAR_NOMINA");

        botones.add(btnRegistrar);
        botones.add(btnBuscar);
        botones.add(btnLimpiar);

        modeloTabla = new DefaultTableModel(
                new String[]{"Código", "Empleado", "Devengado", "Descuentos", "Total"}, 0);
        tabla = new JTable(modeloTabla);

        panelBase.add(panel, BorderLayout.NORTH);
        panelBase.add(botones, BorderLayout.SOUTH);
        panelBase.add(new JScrollPane(tabla), BorderLayout.CENTER);
        
        add(panelBase);
    }

    public String getCodigoNomina() {
        return txtCodigoNomina.getText().trim();
    }

    public String getCodigoEmpleado() {
        return txtCodigoEmpleado.getText().trim();
    }

    public double getDevengado() {
        return Double.parseDouble(txtDevengado.getText().trim());
    }

    public double getDescuentos() {
        return Double.parseDouble(txtDescuentos.getText().trim());
    }

    public void cargarTabla(List<Nomina> nominas) {
        modeloTabla.setRowCount(0);
        for (Nomina nomina : nominas) {
            modeloTabla.addRow(new Object[]{
                    nomina.getCodigoNomina(),
                    nomina.getEmpleado() != null ? nomina.getEmpleado().getCodigoEmpleado() : "",
                    nomina.getDevengado(),
                    nomina.getDescuentos(),
                    nomina.calcularTotal()
            });
        }
    }

    public void limpiarFormulario() { ///porque en la gui
        txtCodigoNomina.setText("");
        txtCodigoEmpleado.setText("");
        txtDevengado.setText("");
        txtDescuentos.setText("");
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }
}