package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.negocio.dto.empleadoDto;

public class panelEmpleados extends JPanel {

    public JTextField tNombre;
    public JTextField tNombreNuevo;

    public JButton bRegistrar;
    public JButton bModificar;
    public JButton bInactivar;
    public JButton bBuscar;
    public JButton bVolver;

    private DefaultTableModel modeloTabla;
    private JTable tablaEmpleados;

    public panelEmpleados() {
        construirPanel();
    }

    private void construirPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel campos = new JPanel(new GridLayout(2, 2, 8, 8));
        campos.setBorder(BorderFactory.createTitledBorder("Datos del empleado"));

        tNombre      = new JTextField();
        tNombreNuevo = new JTextField();

        campos.add(new JLabel("NOMBRE:"));
        campos.add(tNombre);
        campos.add(new JLabel("NOMBRE NUEVO (modificar):"));
        campos.add(tNombreNuevo);

        modeloTabla = new DefaultTableModel(new String[]{"Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tablaEmpleados = new JTable(modeloTabla);
        tablaEmpleados.setRowHeight(22);

        tablaEmpleados.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaEmpleados.getSelectedRow();
            if (fila >= 0) {
                tNombre.setText(modeloTabla.getValueAt(fila, 0).toString());
            }
        });

        JScrollPane scroll = new JScrollPane(tablaEmpleados);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de empleados"));
        scroll.setPreferredSize(new Dimension(400, 200));

        add(campos, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        agregarBotones();
    }

    private void agregarBotones() {
        bRegistrar = new JButton(Eventos.eREGISTRAR);
        bModificar = new JButton(Eventos.eMODIFICAR);
        bInactivar = new JButton(Eventos.eINACTIVAR);
        bBuscar    = new JButton(Eventos.eBUSCAR);
        bVolver    = new JButton(Eventos.VOLVER);

        JPanel botones = new JPanel(new GridLayout(1, 4, 10, 10));
        botones.add(bRegistrar);
        botones.add(bModificar);
        botones.add(bInactivar);
        botones.add(bBuscar);

        JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelVolver.add(bVolver);

        JPanel sur = new JPanel(new BorderLayout());
        sur.add(botones, BorderLayout.NORTH);
        sur.add(panelVolver, BorderLayout.SOUTH);

        add(sur, BorderLayout.SOUTH);
    }

    public void poblarTabla(List<empleadoDto> lista) {
        modeloTabla.setRowCount(0);
        for (empleadoDto e : lista) {
            modeloTabla.addRow(new Object[]{e.getNombre()});
        }
    }

    public void limpiarCampos() {
        tNombre.setText("");
        tNombreNuevo.setText("");
    }

    public empleadoDto getDatosEmpleado() {
        if (tNombre.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "El nombre es requerido");
            return null;
        }
        empleadoDto e = new empleadoDto();
        e.setNombre(tNombre.getText().trim());
        return e;
    }

    public String getNombreSeleccionado() {
        if (tNombre.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla");
            return null;
        }
        return tNombre.getText().trim();
    }

    public String getNombreNuevo() {
        return tNombreNuevo.getText().trim();
    }
}