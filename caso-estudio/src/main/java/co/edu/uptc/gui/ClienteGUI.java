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

import co.edu.uptc.enums.PoseeResponsabiliadTributaria;
import co.edu.uptc.enums.TipoClienteEnum;
import co.edu.uptc.enums.TipoDocumentoEnum;
import co.edu.uptc.gui.modelo.Cliente;

public class ClienteGUI extends JFrame{
	
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JComboBox<TipoDocumentoEnum>cbTipoDocumento;
	private JTextField txtNumeroDocumento;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JComboBox<TipoClienteEnum>cbTipoCliente;
	private JTextField txtCorreoElectronico;
	private JComboBox<PoseeResponsabiliadTributaria>cbRespTributaria;
	
	
	private JButton btnRegistrar;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnBuscar;
	private JButton btnLimpiar;
	
	private JTable tablaClientes;
	private DefaultTableModel modeloTabla;
	

    public ClienteGUI() {
        setTitle("Gestión de Clientes");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iniciarComponentes();
        
        
    }

    private void iniciarComponentes() {
    	JPanel panelBase= new JPanel(new BorderLayout());
    	panelBase.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel panelFormulario = new JPanel(new GridLayout(10, 2, 4, 4));
        

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        cbTipoDocumento = new JComboBox<>(TipoDocumentoEnum.values());
        txtNumeroDocumento = new JTextField();
        txtTelefono = new JTextField();
        txtDireccion = new JTextField();
        cbTipoCliente = new JComboBox<>(TipoClienteEnum.values());
        txtCorreoElectronico= new JTextField();
        cbRespTributaria= new JComboBox<>(PoseeResponsabiliadTributaria.values());

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
        panelFormulario.add(new JLabel("Tipo cliente:"));
        panelFormulario.add(cbTipoCliente);
        panelFormulario.add(new JLabel("Correo Electronico:"));
        panelFormulario.add(txtCorreoElectronico);
        panelFormulario.add(new JLabel("Responsable Tributariamente:"));
        panelFormulario.add(cbRespTributaria);
        JPanel panelBotones = new JPanel();

        btnRegistrar = new JButton("Registrar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnLimpiar = new JButton("Limpiar");

        btnRegistrar.setActionCommand("REGISTRAR_CLIENTE");
        btnModificar.setActionCommand("MODIFICAR_CLIENTE");
        btnEliminar.setActionCommand("ELIMINAR_CLIENTE");
        btnBuscar.setActionCommand("BUSCAR_CLIENTE");
        btnLimpiar.setActionCommand("LIMPIAR_CLIENTE");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);

        modeloTabla = new DefaultTableModel(
                new String[]{"Código", "Nombre", "Apellido", "Documento", "Teléfono", "Tipo", "Correo", "Tributa"}, 0);
        tablaClientes = new JTable(modeloTabla);
           //tablaClientes. setPreferredSize(new Dimension(50,200));
        panelBase.add(panelFormulario, BorderLayout.NORTH);
        panelBase.add(panelBotones, BorderLayout.SOUTH);
        panelBase.add(new JScrollPane(tablaClientes), BorderLayout.CENTER);
        
        add(panelBase);
        
    }

    public Cliente obtenerClienteFormulario() {
        Cliente cliente = new Cliente();
        cliente.setCodigo(txtCodigo.getText().trim());
        cliente.setNombre(txtNombre.getText().trim());
        cliente.setApellido(txtApellido.getText().trim());
        cliente.setTipoDocumento((TipoDocumentoEnum) cbTipoDocumento.getSelectedItem());
        cliente.setNumeroDocumento(txtNumeroDocumento.getText().trim());
        cliente.setTelefono(txtTelefono.getText().trim());
        cliente.setDireccion(txtDireccion.getText().trim());
        cliente.setTipoCliente((TipoClienteEnum) cbTipoCliente.getSelectedItem());
        cliente.setCorreoElectronico(txtCorreoElectronico.getText().trim());
        cliente.setResponsableTributariamente((PoseeResponsabiliadTributaria)cbRespTributaria.getSelectedItem());
        cliente.setActivo(true);
        return cliente;
    }

    public String obtenerCodigoCliente() {
        return txtCodigo.getText().trim();
    }

    public void cargarClienteEnFormulario(Cliente cliente) {
        txtCodigo.setText(cliente.getCodigo());
        txtNombre.setText(cliente.getNombre());
        txtApellido.setText(cliente.getApellido());
        cbTipoDocumento.setSelectedItem(cliente.getTipoDocumento());
        txtNumeroDocumento.setText(cliente.getNumeroDocumento());
        txtTelefono.setText(cliente.getTelefono());
        txtDireccion.setText(cliente.getDireccion());
        cbTipoCliente.setSelectedItem(cliente.getTipoCliente());
        txtCorreoElectronico.setText(cliente.getCorreoElectronico());
        cbRespTributaria.setSelectedItem(cliente.getResponsableTributariamente());
    }

    public void cargarTabla(List<Cliente> clientes) {
        modeloTabla.setRowCount(0);
        for (Cliente cliente : clientes) {
            modeloTabla.addRow(new Object[]{
                cliente.getCodigo(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getNumeroDocumento(),
                cliente.getTelefono(),
                cliente.getTipoCliente(),
                cliente.getCorreoElectronico(),
                cliente.getResponsableTributariamente()
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
        txtCorreoElectronico.setText("");
        cbTipoDocumento.setSelectedIndex(0);
        cbTipoCliente.setSelectedIndex(0);
        cbRespTributaria.setSelectedItem(0);
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
