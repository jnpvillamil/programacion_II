package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.enums.TipoDocumentoEnum;
import co.edu.uptc.gui.modelo.Proveedor;

@SuppressWarnings("serial")
public class ProveedorGUI extends JFrame {

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JComboBox<TipoDocumentoEnum> cbTipoDocumento;
    private JTextField txtNumeroDocumento;
    private JTextField txtTelefono;
    private JTextField txtDireccion;
    private JTextField txtRazonSocial;
    private JTextField txtCorreo;

    private JButton btnRegistrar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnLimpiar;

    private JTable tablaProveedores;
    private DefaultTableModel modeloTabla;

    public ProveedorGUI() {
        setTitle("Gestión de Proveedores");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
    	
    	JPanel panelBase= new JPanel(new BorderLayout());
    	panelBase.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
        JPanel panelFormulario = new JPanel(new GridLayout(9, 2, 8, 8));

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        cbTipoDocumento = new JComboBox<>(TipoDocumentoEnum.values());
        txtNumeroDocumento = new JTextField();
        txtTelefono = new JTextField();
        txtDireccion = new JTextField();
        txtRazonSocial = new JTextField();
        txtCorreo = new JTextField();

        panelFormulario.add(new JLabel("Código:"));
        panelFormulario.add(txtCodigo);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Apellido:"));
        panelFormulario.add(txtApellido);
        panelFormulario.add(new JLabel("Tipo documento:"));
        panelFormulario.add(cbTipoDocumento);
        panelFormulario.add(new JLabel("Número documento:"));
        panelFormulario.add(txtNumeroDocumento);
        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(txtTelefono);
        panelFormulario.add(new JLabel("Dirección:"));
        panelFormulario.add(txtDireccion);
        panelFormulario.add(new JLabel("Razón social:"));
        panelFormulario.add(txtRazonSocial);
        panelFormulario.add(new JLabel("Correo electrónico:"));
        panelFormulario.add(txtCorreo);

        JPanel panelBotones = new JPanel();

        btnRegistrar = new JButton("Registrar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnLimpiar = new JButton("Limpiar");

        btnRegistrar.setActionCommand("REGISTRAR_PROVEEDOR");
        btnModificar.setActionCommand("MODIFICAR_PROVEEDOR");
        btnEliminar.setActionCommand("ELIMINAR_PROVEEDOR");
        btnBuscar.setActionCommand("BUSCAR_PROVEEDOR");
        btnLimpiar.setActionCommand("LIMPIAR_PROVEEDOR");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);

        modeloTabla = new DefaultTableModel(
                new String[] { "Código", "Nombre", "Apellido", "Documento", "Teléfono", "Razón Social", "Correo" }, 0);
        tablaProveedores = new JTable(modeloTabla);

        panelBase.add(panelFormulario, BorderLayout.NORTH);
        panelBase.add(panelBotones, BorderLayout.SOUTH);
        panelBase.add(new JScrollPane(tablaProveedores), BorderLayout.CENTER);
        
        add(panelBase);
    }

    public Proveedor obtenerProveedorFormulario() {
        Proveedor proveedor = new Proveedor();
        proveedor.setCodigo(txtCodigo.getText().trim());
        proveedor.setNombre(txtNombre.getText().trim());
        proveedor.setApellido(txtApellido.getText().trim());
        proveedor.setTipoDocumento((TipoDocumentoEnum) cbTipoDocumento.getSelectedItem());
        proveedor.setNumeroDocumento(txtNumeroDocumento.getText().trim());
        proveedor.setTelefono(txtTelefono.getText().trim());
        proveedor.setDireccion(txtDireccion.getText().trim());
        proveedor.setRazonSocial(txtRazonSocial.getText().trim());
        proveedor.setCorreoElectronico(txtCorreo.getText().trim());
        proveedor.setActivo(true);
        return proveedor;
    }

    public String obtenerCodigoProveedor() {
        return txtCodigo.getText().trim();
    }

    public void cargarProveedorEnFormulario(Proveedor proveedor) {
        txtCodigo.setText(proveedor.getCodigo());
        txtNombre.setText(proveedor.getNombre());
        txtApellido.setText(proveedor.getApellido());
        cbTipoDocumento.setSelectedItem(proveedor.getTipoDocumento());
        txtNumeroDocumento.setText(proveedor.getNumeroDocumento());
        txtTelefono.setText(proveedor.getTelefono());
        txtDireccion.setText(proveedor.getDireccion());
        txtRazonSocial.setText(proveedor.getRazonSocial());
        txtCorreo.setText(proveedor.getCorreoElectronico());
    }

    public void cargarTabla(List<Proveedor> proveedores) {
        modeloTabla.setRowCount(0);
        for (Proveedor proveedor : proveedores) {
            modeloTabla.addRow(new Object[] {
                    proveedor.getCodigo(),
                    proveedor.getNombre(),
                    proveedor.getApellido(),
                    proveedor.getNumeroDocumento(),
                    proveedor.getTelefono(),
                    proveedor.getRazonSocial(),
                    proveedor.getCorreoElectronico()
            });
        }
    }

    public void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtNumeroDocumento.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtRazonSocial.setText("");
        txtCorreo.setText("");
        cbTipoDocumento.setSelectedIndex(0);
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