package co.edu.uptc.gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import co.edu.uptc.negocio.dto.empleadoDto;

public class panelEmpleados extends JPanel {


    public JTextField tCodigo;
    public JTextField tNombre;

    // Botones — LOS DECLARA TU COMPAÑERO
    public JButton bRegistrar;
    public JButton bModificar;
    public JButton bInactivar;
    public JButton bBuscar;
    public JButton bVolver;


    private DefaultTableModel modeloTabla;
    private JTable            tablaEmpleados;

    public panelEmpleados() {
        construirPanel();
    }

    private void construirPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JPanel campos = new JPanel(new GridLayout(2, 2, 8, 8));
        campos.setBorder(BorderFactory.createTitledBorder("Datos del empleado"));

        tCodigo = new JTextField(); 
        tCodigo.setEditable(false);
        tNombre = new JTextField();

        campos.add(new JLabel("CODIGO:")); campos.add(tCodigo);
        campos.add(new JLabel("NOMBRE:")); campos.add(tNombre);


        modeloTabla = new DefaultTableModel(
            new String[]{"Codigo", "Nombre"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaEmpleados = new JTable(modeloTabla);
        tablaEmpleados.setRowHeight(22);

        tablaEmpleados.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaEmpleados.getSelectedRow();
            if (fila >= 0) {
                tCodigo.setText(modeloTabla.getValueAt(fila, 0).toString());
                tNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            }
        });

        JScrollPane scroll = new JScrollPane(tablaEmpleados);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de empleados"));
        scroll.setPreferredSize(new Dimension(400, 200));

        add(campos, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        // ── El SUR lo agrega tu compañero ──────────
    }

    public void poblarTabla(List<empleadoDto> lista) {
        modeloTabla.setRowCount(0);
        for (empleadoDto e : lista) {
            modeloTabla.addRow(new Object[]{
                e.getCodigoEmpleado(),
                e.getNombre()
            });
        }
    }

    public void limpiarCampos() {
        tCodigo.setText("");
        tNombre.setText("");
    }
}