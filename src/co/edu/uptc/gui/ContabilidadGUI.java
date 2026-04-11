package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.enums.TipoMovimientoEnum;
import co.edu.uptc.gui.modelo.MovimientoContable;

@SuppressWarnings("serial")
public class ContabilidadGUI extends JFrame {

    private JTextField txtCodigoTransaccion;
    private JComboBox<TipoMovimientoEnum> cbTipoMovimiento;
    private JTextField txtCuentaContable;
    private JTextField txtValor;
    private JTextField txtDescripcion;

    private JButton btnRegistrar;
    private JButton btnListar;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public ContabilidadGUI() {
        setTitle("Contabilidad");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
    	JPanel panelBase= new JPanel(new BorderLayout());
    	panelBase.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
        JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));

        txtCodigoTransaccion = new JTextField();
        cbTipoMovimiento = new JComboBox<>(TipoMovimientoEnum.values());
        txtCuentaContable = new JTextField();
        txtValor = new JTextField();
        txtDescripcion = new JTextField();

        panel.add(new JLabel("Código transacción:"));
        panel.add(txtCodigoTransaccion);
        panel.add(new JLabel("Tipo movimiento:"));
        panel.add(cbTipoMovimiento);
        panel.add(new JLabel("Cuenta contable:"));
        panel.add(txtCuentaContable);
        panel.add(new JLabel("Valor:"));
        panel.add(txtValor);
        panel.add(new JLabel("Descripción:"));
        panel.add(txtDescripcion);

        JPanel botones = new JPanel();
        btnRegistrar = new JButton("Registrar");
        btnListar = new JButton("Listar");

        btnRegistrar.setActionCommand("REGISTRAR_MOVIMIENTO");
        btnListar.setActionCommand("LISTAR_MOVIMIENTOS");

        botones.add(btnRegistrar);
        botones.add(btnListar);

        modeloTabla = new DefaultTableModel(
                new String[]{"Código", "Tipo", "Cuenta", "Valor", "Descripción"}, 0);
        tabla = new JTable(modeloTabla);

        panelBase.add(panel, BorderLayout.NORTH);
        panelBase.add(botones, BorderLayout.SOUTH);
        panelBase.add(new JScrollPane(tabla), BorderLayout.CENTER);
        
        add(panelBase);
        
        
    }

    public MovimientoContable obtenerMovimientoFormulario() {
        MovimientoContable movimiento = new MovimientoContable();
        movimiento.setCodigoTransaccion(txtCodigoTransaccion.getText().trim());
        movimiento.setTipoMovimiento((TipoMovimientoEnum) cbTipoMovimiento.getSelectedItem());
        movimiento.setCuentaContable(txtCuentaContable.getText().trim());
        movimiento.setValor(Double.parseDouble(txtValor.getText().trim()));
        movimiento.setDescripcion(txtDescripcion.getText().trim());
        return movimiento;
    }

    public void cargarTabla(List<MovimientoContable> movimientos) {
        modeloTabla.setRowCount(0);
        for (MovimientoContable movimiento : movimientos) {
            modeloTabla.addRow(new Object[]{
                    movimiento.getCodigoTransaccion(),
                    movimiento.getTipoMovimiento(),
                    movimiento.getCuentaContable(),
                    movimiento.getValor(),
                    movimiento.getDescripcion()
            });
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnListar() {
        return btnListar;
    }
}