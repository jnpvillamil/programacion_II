package co.edu.uptc.interfazGrafica;

import javax.swing.*;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.config.TiendaConfig;

public class DialogoProveedor extends DialogoCentral {
    
    private JTextField txtCodigo;
    private JTextField txtRazonSocial;
    private JTextField txtNit;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    
    private Proveedor proveedor;
    
    public DialogoProveedor(JFrame parent, boolean esCrear, Proveedor proveedorExistente) {
        super(parent, esCrear ? "Nuevo Proveedor" : "Modificar Proveedor", esCrear);
        
        if(!esCrear && proveedorExistente != null) {
            this.proveedor = proveedorExistente;
            cargarDatos();
        }
    }
    
    @Override
    protected void inicializarCampos() {
        txtCodigo = new JTextField();
        txtRazonSocial = new JTextField();
        txtNit = new JTextField();
        txtDireccion = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();
        
        add(new JLabel("Código:"));
        add(txtCodigo);
        
        add(new JLabel("Razón Social:"));
        add(txtRazonSocial);
        
        add(new JLabel("NIT:"));
        add(txtNit);
        
        add(new JLabel("Dirección:"));
        add(txtDireccion);
        
        add(new JLabel("Teléfono:"));
        add(txtTelefono);
        
        add(new JLabel("Correo Electrónico:"));
        add(txtCorreo);
    }
    
    private void cargarDatos() {
        txtCodigo.setText(proveedor.getCodigo());
        txtCodigo.setEnabled(false);  
        
        txtRazonSocial.setText(proveedor.getRazonSocial());
        txtNit.setText(proveedor.getNit());
        txtNit.setEnabled(false); 
        txtDireccion.setText(proveedor.getDireccion());
        txtTelefono.setText(proveedor.getTelefono());
        txtCorreo.setText(proveedor.getCorreo());
    }
    
    @Override
    protected boolean validarCampos() {
        String codigo = txtCodigo.getText().trim();
        String razonSocial = txtRazonSocial.getText().trim();
        String nit = txtNit.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        
        
        if(codigo.isEmpty() || razonSocial.isEmpty() || nit.isEmpty() || 
           direccion.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
            return false;
        }
        
        
        if(esCrear && TiendaConfig.getInstancia().getGestionProveedor().existe(codigo)) {
            JOptionPane.showMessageDialog(this, "El código de proveedor ya existe");
            return false;
        }
        
        
        if(esCrear && TiendaConfig.getInstancia().getGestionProveedor().existeNit(nit)) {
            JOptionPane.showMessageDialog(this, "El NIT ya está registrado para otro proveedor");
            return false;
        }
        
        
        if(!nit.matches("[0-9]+[-]?[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Formato de NIT inválido (ejemplo: 900123456-1)");
            return false;
        }
        
       
        if(!telefono.matches("\\d+") || telefono.length() < 7) {
            JOptionPane.showMessageDialog(this, "Teléfono inválido (solo números, mínimo 7 dígitos)");
            return false;
        }
        
        
        if(!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Correo electrónico inválido");
            return false;
        }
        
      
        proveedor = new Proveedor(
            codigo,
            razonSocial,
            nit,
            direccion,
            telefono,
            correo
        );
        
        return true;
    }
    
    public Proveedor getProveedor() {
        return proveedor;
    }
}