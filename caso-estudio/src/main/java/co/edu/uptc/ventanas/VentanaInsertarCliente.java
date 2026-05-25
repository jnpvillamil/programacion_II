package co.edu.uptc.ventanas;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import co.edu.uptc.dao.ClienteDao;
import co.edu.uptc.vo.ClienteVo;

@SuppressWarnings("serial")
public class VentanaInsertarCliente extends JInternalFrame { 

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDocumento;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JComboBox<String> comboPais;
    private JComboBox<String> comboCiudad;
    private JTextField txtDireccionDetallada;
    
    private JButton btnGuardar;

    public VentanaInsertarCliente() {
        setTitle("Registrar Nuevo Cliente (Escalable)");
        setSize(450, 450);
        setClosable(true);
        setIconifiable(true);
        
       
        getContentPane().setLayout(new GridLayout(10, 2, 10, 10));

        getContentPane().add(new JLabel("  Código Cliente:"));
        txtCodigo = new JTextField();
        getContentPane().add(txtCodigo);

        getContentPane().add(new JLabel("  Nombre:"));
        txtNombre = new JTextField();
        getContentPane().add(txtNombre);

        getContentPane().add(new JLabel("  Apellido:"));
        txtApellido = new JTextField();
        getContentPane().add(txtApellido);

        getContentPane().add(new JLabel("  Número Documento:"));
        txtDocumento = new JTextField();
        getContentPane().add(txtDocumento);

        getContentPane().add(new JLabel("  Teléfono:"));
        txtTelefono = new JTextField();
        getContentPane().add(txtTelefono);

        getContentPane().add(new JLabel("  Correo Electrónico:"));
        txtCorreo = new JTextField();
        getContentPane().add(txtCorreo);

        getContentPane().add(new JLabel("  País:"));
        comboPais = new JComboBox<>(new String[] { "Colombia", "México", "Argentina" });
        getContentPane().add(comboPais);

        getContentPane().add(new JLabel("  Ciudad (ID - Nombre):"));
        comboCiudad = new JComboBox<>(new String[] { "1 - Bogotá", "2 - Medellín", "3 - Tunja" });
        getContentPane().add(comboCiudad);

        getContentPane().add(new JLabel("  Dirección Detallada:"));
        txtDireccionDetallada = new JTextField();
        getContentPane().add(txtDireccionDetallada);

        getContentPane().add(new JLabel("")); 
        btnGuardar = new JButton("Guardar Cliente");
        getContentPane().add(btnGuardar);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarRegistroDeCliente();
            }
        });
    }

    private void ejecutarRegistroDeCliente() {
        if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor llene los campos obligatorios (Código, Nombre, Apellido).", 
                    "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ClienteVo miCliente = new ClienteVo();

            miCliente.setCodigo(txtCodigo.getText());
            miCliente.setNombre(txtNombre.getText());
            miCliente.setApellido(txtApellido.getText());
            miCliente.setNumeroDocumento(txtDocumento.getText());
            miCliente.setTelefono(txtTelefono.getText());
            miCliente.setCorreo(txtCorreo.getText());
            miCliente.setTipoDocumento("C.C");
            miCliente.setTipoCliente("Frecuente");
            miCliente.setActivo(true);
            
       
            String ciudadSeleccionada = (String) comboCiudad.getSelectedItem();
            String[] partes = ciudadSeleccionada.split(" - ");
            int idCiudad = Integer.parseInt(partes[0]); 
            
            miCliente.setIdCiudad(idCiudad);
            miCliente.setDireccionDetallada(txtDireccionDetallada.getText());
            
       
            ClienteDao miClienteDao = new ClienteDao();
            miClienteDao.registrarCliente(miCliente);
 
            limpiarFormulario();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error de formato en los datos: " + ex.getMessage(), 
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtDocumento.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccionDetallada.setText("");
        comboPais.setSelectedIndex(0);
        comboCiudad.setSelectedIndex(0);
    }
}