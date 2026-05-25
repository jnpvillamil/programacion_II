package co.edu.uptc.ventanas;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.*;
import co.edu.uptc.dao.ClienteDao;
import co.edu.uptc.vo.ClienteVo;

@SuppressWarnings("serial")
public class VentanaInsertarCliente extends JInternalFrame {

    private JTextField txtCodigo, txtNombre, txtApellido, txtDocumento, txtTelefono, txtDireccion, txtCorreo;
    private JComboBox<String> comboPaises, comboCiudades;
    private HashMap<String, Integer> mapaCiudades; 
    private JButton btnGuardar;
    private ClienteDao miClienteDao;

    public VentanaInsertarCliente() {
        super("Registrar Nuevo Cliente", false, true, false, true);
        setSize(500, 450); 
        setLayout(new GridLayout(10, 2, 10, 10)); 

        miClienteDao = new ClienteDao();
        mapaCiudades = new HashMap<>();

        add(new JLabel("  Código Cliente:"));
        add(txtCodigo = new JTextField());

        add(new JLabel("  Nombre:"));
        add(txtNombre = new JTextField());

        add(new JLabel("  Apellido:"));
        add(txtApellido = new JTextField());

        add(new JLabel("  Número Documento:"));
        add(txtDocumento = new JTextField());

        add(new JLabel("  Teléfono:"));
        add(txtTelefono = new JTextField());

        add(new JLabel("  Dirección:"));
        add(txtDireccion = new JTextField());

        add(new JLabel("  Correo Electrónico:"));
        add(txtCorreo = new JTextField());

        add(new JLabel("  País:"));
        comboPaises = new JComboBox<>(new String[]{"Colombia", "México", "Argentina"});
        add(comboPaises);

        add(new JLabel("  Ciudad:"));
        comboCiudades = new JComboBox<>();
        add(comboCiudades);

        add(new JLabel()); 
        add(btnGuardar = new JButton("Guardar Cliente"));

        cargarTodasLasCiudades();

        btnGuardar.addActionListener(e -> guardarCliente());
    }

    private void cargarTodasLasCiudades() {
        comboCiudades.removeAllItems();
        mapaCiudades.clear();
        
        co.edu.uptc.conexion.Conexion con = new co.edu.uptc.conexion.Conexion();
        String sql = "SELECT id_ciudad, nombre_ciudad FROM ciudades ORDER BY nombre_ciudad ASC";
        
        try (Connection c = con.getConnection(); 
             Statement st = c.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("id_ciudad");
                String nombre = rs.getString("nombre_ciudad");
                
                comboCiudades.addItem(nombre);
                mapaCiudades.put(nombre, id);
            }
        } catch (Exception ex) {
            System.out.println("Error crítico al cargar ciudades: " + ex.getMessage());
        }
    }

    private void guardarCliente() {
        if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty() || comboCiudades.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Por favor llene los campos obligatorios.");
            return;
        }

        try {
            ClienteVo c = new ClienteVo();
            c.setCodigo(txtCodigo.getText().trim());
            c.setNombre(txtNombre.getText().trim());
            c.setApellido(txtApellido.getText().trim());
            c.setNumeroDocumento(txtDocumento.getText().trim());
            c.setTelefono(txtTelefono.getText().trim());
            c.setDireccionDetallada(txtDireccion.getText().trim());
            c.setCorreo(txtCorreo.getText().trim());
    
            String ciudadSeleccionada = comboCiudades.getSelectedItem().toString();
            int idCiudadReal = mapaCiudades.get(ciudadSeleccionada);
            c.setIdCiudad(idCiudadReal);
            c.setTipoDocumento("C.C");
            c.setTipoCliente("Frecuente");
            c.setActivo(true);

            miClienteDao.registrarCliente(c);
            JOptionPane.showMessageDialog(this, "¡Cliente registrado con éxito!");
            limpiarCampos();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText(""); txtNombre.setText(""); txtApellido.setText("");
        txtDocumento.setText(""); txtTelefono.setText(""); txtDireccion.setText("");
        txtCorreo.setText("");
        if (comboPaises.getItemCount() > 0) comboPaises.setSelectedIndex(0);
        if (comboCiudades.getItemCount() > 0) comboCiudades.setSelectedIndex(0);
    }
}