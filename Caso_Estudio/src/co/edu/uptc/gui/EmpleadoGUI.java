package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.enums.EstadoEmpleadoEnum;
import co.edu.uptc.enums.TipoDocumentoEnum;
import co.edu.uptc.enums.TipoEmpleadoEnum;
import co.edu.uptc.gui.modelo.Empleado;

@SuppressWarnings("serial")
public class EmpleadoGUI extends JFrame {

    private JTextField txtCodigoPersona;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JComboBox<TipoDocumentoEnum> cbTipoDocumento;
    private JTextField txtNumeroDocumento;
    private JTextField txtTelefono;
    private JTextField txtDireccion;
    private JTextField txtCodigoEmpleado;
    private JTextField txtSalarioBase;
    private JComboBox<TipoEmpleadoEnum> cbTipoEmpleado;
    private JComboBox<EstadoEmpleadoEnum> cbEstadoEmpleado;

    private JButton btnRegistrar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnLimpiar;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public EmpleadoGUI() {
        setTitle("Gestión de Empleados");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
    	JPanel panelBase= new JPanel(new BorderLayout());
    	panelBase.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
        JPanel panel = new JPanel(new GridLayout(11, 2, 8, 8));

        txtCodigoPersona = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        cbTipoDocumento = new JComboBox<>(TipoDocumentoEnum.values());
        txtNumeroDocumento = new JTextField();
        txtTelefono = new JTextField();
        txtDireccion = new JTextField();
        txtCodigoEmpleado = new JTextField();
        txtSalarioBase = new JTextField();
        cbTipoEmpleado = new JComboBox<>(TipoEmpleadoEnum.values());
        cbEstadoEmpleado = new JComboBox<>(EstadoEmpleadoEnum.values());

        panel.add(new JLabel("Código persona:"));
        panel.add(txtCodigoPersona);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);
        panel.add(new JLabel("Tipo documento:"));
        panel.add(cbTipoDocumento);
        panel.add(new JLabel("Número documento:"));
        panel.add(txtNumeroDocumento);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);
        panel.add(new JLabel("Código empleado:"));
        panel.add(txtCodigoEmpleado);
        panel.add(new JLabel("Salario base:"));
        panel.add(txtSalarioBase);
        panel.add(new JLabel("Tipo empleado:"));
        panel.add(cbTipoEmpleado);
        panel.add(new JLabel("Estado empleado:"));
        panel.add(cbEstadoEmpleado);

        JPanel botones = new JPanel();
        btnRegistrar = new JButton("Registrar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnLimpiar = new JButton("Limpiar");

        btnRegistrar.setActionCommand("REGISTRAR_EMPLEADO");
        btnModificar.setActionCommand("MODIFICAR_EMPLEADO");
        btnEliminar.setActionCommand("ELIMINAR_EMPLEADO");
        btnBuscar.setActionCommand("BUSCAR_EMPLEADO");
        btnLimpiar.setActionCommand("LIMPIAR_EMPLEADO");

        botones.add(btnRegistrar);
        botones.add(btnModificar);
        botones.add(btnEliminar);
        botones.add(btnBuscar);
        botones.add(btnLimpiar);

        modeloTabla = new DefaultTableModel(
                new String[]{"Cod. Emp", "Nombre", "Apellido", "Documento", "Salario", "Tipo", "Estado"}, 0);
        tabla = new JTable(modeloTabla);

        panelBase.add(panel, BorderLayout.NORTH);
        panelBase.add(botones, BorderLayout.SOUTH);
        panelBase.add(new JScrollPane(tabla), BorderLayout.CENTER);
        
        add(panelBase);
    }

    public Empleado obtenerEmpleadoFormulario() {
        Empleado empleado = new Empleado();
        empleado.setCodigo(txtCodigoPersona.getText().trim());
        empleado.setNombre(txtNombre.getText().trim());
        empleado.setApellido(txtApellido.getText().trim());
        empleado.setTipoDocumento((TipoDocumentoEnum) cbTipoDocumento.getSelectedItem());
        empleado.setNumeroDocumento(txtNumeroDocumento.getText().trim());
        empleado.setTelefono(txtTelefono.getText().trim());
        empleado.setDireccion(txtDireccion.getText().trim());
        empleado.setCodigoEmpleado(txtCodigoEmpleado.getText().trim());
        empleado.setSalarioBase(Double.parseDouble(txtSalarioBase.getText().trim()));
        empleado.setTipoEmpleado((TipoEmpleadoEnum) cbTipoEmpleado.getSelectedItem());
        empleado.setEstadoEmpleado((EstadoEmpleadoEnum) cbEstadoEmpleado.getSelectedItem());
        return empleado;
    }

    public String obtenerCodigoEmpleado() {
        return txtCodigoEmpleado.getText().trim();
    }

    public void cargarEmpleadoEnFormulario(Empleado empleado) {
        txtCodigoPersona.setText(empleado.getCodigo());
        txtNombre.setText(empleado.getNombre());
        txtApellido.setText(empleado.getApellido());
        cbTipoDocumento.setSelectedItem(empleado.getTipoDocumento());
        txtNumeroDocumento.setText(empleado.getNumeroDocumento());
        txtTelefono.setText(empleado.getTelefono());
        txtDireccion.setText(empleado.getDireccion());
        txtCodigoEmpleado.setText(empleado.getCodigoEmpleado());
        txtSalarioBase.setText(String.valueOf(empleado.getSalarioBase()));
        cbTipoEmpleado.setSelectedItem(empleado.getTipoEmpleado());
        cbEstadoEmpleado.setSelectedItem(empleado.getEstadoEmpleado());
    }

    public void cargarTabla(List<Empleado> empleados) {
        modeloTabla.setRowCount(0);
        for (Empleado empleado : empleados) {
            modeloTabla.addRow(new Object[]{
                    empleado.getCodigoEmpleado(),
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getNumeroDocumento(),
                    empleado.getSalarioBase(),
                    empleado.getTipoEmpleado(),
                    empleado.getEstadoEmpleado()
            });
        }
    }

    public void limpiarFormulario() {
        txtCodigoPersona.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtNumeroDocumento.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtCodigoEmpleado.setText("");
        txtSalarioBase.setText("");
        cbTipoDocumento.setSelectedIndex(0);
        cbTipoEmpleado.setSelectedIndex(0);
        cbEstadoEmpleado.setSelectedIndex(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }
}