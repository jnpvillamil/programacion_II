package co.edu.uptc.interfazGrafica;

import javax.swing.*;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.enums.TipoDocumentoEnum;
import co.edu.uptc.config.TiendaConfig;

public class DialogoCliente extends DialogoCentral {
    
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JComboBox<String> cbTipoIdentificacion;
    private JTextField txtNumeroIdentificacion;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JComboBox<String> cbTipoCliente;
    
    private Cliente cliente;
    
    public DialogoCliente(JFrame parent, boolean esCrear, Cliente clienteExistente) {
        super(parent, esCrear ? "Registrar Cliente" : "Modificar Cliente", esCrear);
        
        if(!esCrear && clienteExistente != null) {
            this.cliente = clienteExistente;
            cargarDatos();
        }
    }
    
    @Override
    protected void inicializarCampos() {
        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        
        
        cbTipoIdentificacion = new JComboBox<>();
        for(TipoDocumentoEnum tipo : TipoDocumentoEnum.values()) {
            cbTipoIdentificacion.addItem(tipo.name());
        }
        
        txtNumeroIdentificacion = new JTextField();
        txtDireccion = new JTextField();
        txtTelefono = new JTextField();
        cbTipoCliente = new JComboBox<>(new String[]{"Minorista", "Mayorista"});
        
        add(new JLabel("Código:"));
        add(txtCodigo);
        
        add(new JLabel("Nombre / Razón Social:"));
        add(txtNombre);
        
        add(new JLabel("Tipo Identificación:"));
        add(cbTipoIdentificacion);
        
        add(new JLabel("Número Identificación:"));
        add(txtNumeroIdentificacion);
        
        add(new JLabel("Dirección:"));
        add(txtDireccion);
        
        add(new JLabel("Teléfono:"));
        add(txtTelefono);
        
        add(new JLabel("Tipo Cliente:"));
        add(cbTipoCliente);
    }
    
    private void cargarDatos() {
        txtCodigo.setText(cliente.getCodigo());
        txtCodigo.setEnabled(false);
        txtNombre.setText(cliente.getNombre());
        
        
        cbTipoIdentificacion.setSelectedItem(cliente.getTipoIdentificacion().name());
        
        txtNumeroIdentificacion.setText(cliente.getNumeroIdentificacion());
        txtDireccion.setText(cliente.getDireccion());
        txtTelefono.setText(cliente.getTelefono());
        cbTipoCliente.setSelectedItem(cliente.getTipoCliente());
    }
    
    @Override
    protected boolean validarCampos() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String tipoIdStr = cbTipoIdentificacion.getSelectedItem().toString();
        String numeroId = txtNumeroIdentificacion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        
        if(codigo.isEmpty() || nombre.isEmpty() || numeroId.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
            return false;
        }
        
        if(esCrear && TiendaConfig.getInstancia().getGestionCliente().existe(codigo)) {
            JOptionPane.showMessageDialog(this, "El código de cliente ya existe");
            return false;
        }
        
        if(!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras");
            return false;
        }
        
        if(!telefono.matches("\\d+") || telefono.length() < 7) {
            JOptionPane.showMessageDialog(this, "Teléfono inválido (solo números, mínimo 7 dígitos)");
            return false;
        }
        
       
        TipoDocumentoEnum tipoIdentificacion = TipoDocumentoEnum.fromString(tipoIdStr);
        
        cliente = new Cliente(
            codigo,
            nombre,
            tipoIdentificacion,
            numeroId,
            txtDireccion.getText().trim(),
            telefono,
            cbTipoCliente.getSelectedItem().toString()
        );
        
        return true;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
}