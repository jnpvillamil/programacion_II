package co.edu.uptc.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.gui.modelo.Cliente;
import co.edu.uptc.gui.negocio.GestionCliente; 
import co.edu.uptc.dao.ClienteDao;           
import co.edu.uptc.enums.TipoDocumentoEnum;
import co.edu.uptc.enums.TipoClienteEnum;
import co.edu.uptc.enums.PoseeResponsabiliadTributaria;

@SuppressWarnings("serial")
public class VentanaCliente extends JFrame implements ActionListener {

    private JTextField campoCodigo, campoNombre, campoApellido, campoNumeroDoc, campoTelefono, campoDireccion, campoCorreo;
    private JComboBox<TipoDocumentoEnum> comboTipoDoc;
    private JComboBox<TipoClienteEnum> comboTipoCliente;
    private JComboBox<PoseeResponsabiliadTributaria> comboResponsableTributario;
    private JComboBox<ComboItem> comboPais;
    private JComboBox<ComboItem> comboCiudad;
    
    private JButton botonGuardar, botonActualizar, botonLimpiar;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private GestionCliente gestionCliente;

    class ComboItem {
        private int id;
        private String value;
        public ComboItem(int id, String value) { this.id = id; this.value = value; }
        public int getId() { return id; }
        @Override public String toString() { return value; }
    }

    public VentanaCliente() {
        setTitle("Módulo Maestro de Clientes (Con Ubicación)");
        setSize(1000, 680);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(17, 17));

        this.gestionCliente = new GestionCliente();

        iniciarComponentesFormulario();
        iniciarComponenteTabla();
        
        cargarPaises();    
        cargarDatosTabla(); 
    }

    private void iniciarComponentesFormulario() {
 
        JPanel contenedorFormulario = new JPanel();
        contenedorFormulario.setLayout(new BoxLayout(contenedorFormulario, BoxLayout.Y_AXIS));
        contenedorFormulario.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        javax.swing.border.Border bordeLineaGrueso = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);

        // BLOQUE 1: IDENTIFICACIÓN Y DATOS PERSONALES
        JPanel panelIdentificacion = new JPanel(new GridLayout(2, 6, 10, 10));
        panelIdentificacion.setBorder(BorderFactory.createTitledBorder(bordeLineaGrueso, " 1. Identificación y Datos Personales "));

        panelIdentificacion.add(new JLabel("Código (*Clave):", SwingConstants.RIGHT));
        campoCodigo = new JTextField();
        panelIdentificacion.add(campoCodigo);

        panelIdentificacion.add(new JLabel("Nombre(s):", SwingConstants.RIGHT));
        campoNombre = new JTextField();
        panelIdentificacion.add(campoNombre);

        panelIdentificacion.add(new JLabel("Apellido(s):", SwingConstants.RIGHT));
        campoApellido = new JTextField();
        panelIdentificacion.add(campoApellido);

        panelIdentificacion.add(new JLabel("Tipo Doc:", SwingConstants.RIGHT));
        comboTipoDoc = new JComboBox<>(TipoDocumentoEnum.values());
        panelIdentificacion.add(comboTipoDoc);

        panelIdentificacion.add(new JLabel("N° Documento:", SwingConstants.RIGHT));
        campoNumeroDoc = new JTextField();
        panelIdentificacion.add(campoNumeroDoc);

        panelIdentificacion.add(new JLabel("Teléfono:", SwingConstants.RIGHT));
        campoTelefono = new JTextField();
        panelIdentificacion.add(campoTelefono);

        // BLOQUE 2: UBICACIÓN Y DATOS DE CONTACTO
        JPanel panelUbicacion = new JPanel(new GridLayout(2, 4, 10, 10));
        panelUbicacion.setBorder(BorderFactory.createTitledBorder(bordeLineaGrueso, " 2. Ubicación y Datos de Contacto "));

        panelUbicacion.add(new JLabel("Dirección Residencial:", SwingConstants.RIGHT));
        campoDireccion = new JTextField();
        panelUbicacion.add(campoDireccion);

        panelUbicacion.add(new JLabel("País Región:", SwingConstants.RIGHT));
        comboPais = new JComboBox<>();
        panelUbicacion.add(comboPais);
     
        comboPais.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    ComboItem itemSeleccionado = (ComboItem) comboPais.getSelectedItem();
                    if (itemSeleccionado != null) {
                        cargarCiudades(itemSeleccionado.getId());
                    }
                }
            }
        });

        panelUbicacion.add(new JLabel("Ciudad / Municipio:", SwingConstants.RIGHT));
        comboCiudad = new JComboBox<>();
        panelUbicacion.add(comboCiudad);

        panelUbicacion.add(new JLabel("Correo Electrónico:", SwingConstants.RIGHT));
        campoCorreo = new JTextField();
        panelUbicacion.add(campoCorreo);

        JPanel panelComercial = new JPanel(new GridLayout(1, 4, 10, 10));
        panelComercial.setBorder(BorderFactory.createTitledBorder(bordeLineaGrueso, " 3. Clasificación Comercial y Tributaria "));

        panelComercial.add(new JLabel("Tipo de Cliente:", SwingConstants.RIGHT));
        comboTipoCliente = new JComboBox<>(TipoClienteEnum.values());
        panelComercial.add(comboTipoCliente);

        panelComercial.add(new JLabel("Responsabilidad Tributaria:", SwingConstants.RIGHT));
        comboResponsableTributario = new JComboBox<>(PoseeResponsabiliadTributaria.values());
        panelComercial.add(comboResponsableTributario);

        contenedorFormulario.add(panelIdentificacion);
        contenedorFormulario.add(Box.createVerticalStrut(8));
        contenedorFormulario.add(panelUbicacion);
        contenedorFormulario.add(Box.createVerticalStrut(8));
        contenedorFormulario.add(panelComercial);

        // Panel de acciones
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        botonGuardar = new JButton("Guardar Nuevo");
        botonActualizar = new JButton("Guardar Modificación"); 
        botonLimpiar = new JButton("Limpiar Campos");

        botonGuardar.setBackground(new Color(225, 245, 225));
        botonActualizar.setBackground(new Color(225, 235, 245));

        botonGuardar.addActionListener(this);
        botonActualizar.addActionListener(this);
        botonLimpiar.addActionListener(this);

        panelAcciones.add(botonGuardar);
        panelAcciones.add(botonActualizar);
        panelAcciones.add(botonLimpiar);

        JPanel contenedorSuperior = new JPanel(new BorderLayout(5, 5));
        contenedorSuperior.add(contenedorFormulario, BorderLayout.CENTER);
        contenedorSuperior.add(panelAcciones, BorderLayout.SOUTH);

        contenedorSuperior.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        add(contenedorSuperior, BorderLayout.NORTH);
    }

    private void iniciarComponenteTabla() {
        String[] columnas = {"Código", "Nombre", "Apellido", "Tipo Doc", "N° Doc", "Teléfono", "Dirección", "País", "Ciudad", "Tipo Cliente", "Estado", "Correo", "Resp. Tributario"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.setRowHeight(22);
        
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaClientes.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    try {
                        campoCodigo.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
                        campoNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
                        campoApellido.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());

                        String tDoc = modeloTabla.getValueAt(filaSeleccionada, 3).toString().trim().toUpperCase();
                        comboTipoDoc.setSelectedItem(TipoDocumentoEnum.valueOf(tDoc));
                        
                        campoNumeroDoc.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
                        campoTelefono.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
                        campoDireccion.setText(modeloTabla.getValueAt(filaSeleccionada, 6).toString());
                
                        seleccionarItemComboPorTexto(comboPais, modeloTabla.getValueAt(filaSeleccionada, 7).toString());
                        seleccionarItemComboPorTexto(comboCiudad, modeloTabla.getValueAt(filaSeleccionada, 8).toString());

                        String tCli = modeloTabla.getValueAt(filaSeleccionada, 9).toString().trim().toUpperCase();
                        comboTipoCliente.setSelectedItem(TipoClienteEnum.valueOf(tCli));
                        
                        campoCorreo.setText(modeloTabla.getValueAt(filaSeleccionada, 11).toString());

                        String respTrib = modeloTabla.getValueAt(filaSeleccionada, 12).toString().trim().toUpperCase().replace(" ", "_");
                        comboResponsableTributario.setSelectedItem(PoseeResponsabiliadTributaria.valueOf(respTrib));
                        
                        campoCodigo.setEditable(false);
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Aviso de consistencia en Enums: " + ex.getMessage());
                    }
                }
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaClientes);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Registros en Base de Datos"));

        JPanel contenedorTabla = new JPanel(new BorderLayout());
        contenedorTabla.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
        contenedorTabla.add(scrollTabla, BorderLayout.CENTER);

        add(contenedorTabla, BorderLayout.CENTER);
    }

    private void cargarPaises() {
        comboPais.removeAllItems();
        Conexion conex = new Conexion();
        try (Connection c = conex.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM pais ORDER BY nombre")) {
            while (rs.next()) {
                comboPais.addItem(new ComboItem(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar países: " + e.getMessage());
        }
    }

    private void cargarCiudades(int paisId) {
        comboCiudad.removeAllItems();
        Conexion conex = new Conexion();
        try (Connection c = conex.getConnection();
             PreparedStatement pst = c.prepareStatement("SELECT * FROM ciudad WHERE pais_id = ? ORDER BY nombre")) {
            pst.setInt(1, paisId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    comboCiudad.addItem(new ComboItem(rs.getInt("id"), rs.getString("nombre")));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar ciudades: " + e.getMessage());
        }
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        Conexion conex = new Conexion();
        String sql = "SELECT c.*, p.nombre AS nombre_pais, ci.nombre AS nombre_ciudad FROM cliente c "
                   + "LEFT JOIN pais p ON c.pais_id = p.id "
                   + "LEFT JOIN ciudad ci ON c.ciudad_id = ci.id";
        try (Connection c = conex.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("codigo"), rs.getString("nombre"), rs.getString("apellido"),
                    rs.getString("tipo_documento"), rs.getString("numero_documento"),
                    rs.getString("telefono"), rs.getString("direccion"),
                    rs.getString("nombre_pais") != null ? rs.getString("nombre_pais") : "",
                    rs.getString("nombre_ciudad") != null ? rs.getString("nombre_ciudad") : "",
                    rs.getString("tipo_cliente"), rs.getInt("activo") == 1 ? "Activo" : "Inactivo",
                    rs.getString("correo_electronico"), rs.getString("responsable_tributario")
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            System.out.println("Error al poblar tabla clientes: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonLimpiar) {
            limpiarCampos();
            return;
        }

        String codigo = campoCodigo.getText().trim();
        if (codigo.isEmpty() || campoNombre.getText().trim().isEmpty() || campoNumeroDoc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Código, Nombre y N° Documento son campos requeridos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ComboItem paisSeleccionado = (ComboItem) comboPais.getSelectedItem();
        ComboItem ciudadSeleccionada = (ComboItem) comboCiudad.getSelectedItem();
        
        int idPais = (paisSeleccionado != null) ? paisSeleccionado.getId() : 0;
        int idCiudad = (ciudadSeleccionada != null) ? ciudadSeleccionada.getId() : 0;

        Cliente cliente = new Cliente(
                codigo, 
                campoNombre.getText().trim(), 
                campoApellido.getText().trim(),
                (TipoDocumentoEnum) comboTipoDoc.getSelectedItem(), 
                campoNumeroDoc.getText().trim(),
                campoTelefono.getText().trim(), 
                campoDireccion.getText().trim(), 
                idPais, 
                idCiudad, 
                (TipoClienteEnum) comboTipoCliente.getSelectedItem(), 
                true, // Activo = true
                campoCorreo.getText().trim(),
                (PoseeResponsabiliadTributaria) comboResponsableTributario.getSelectedItem()
        );

        try {
            ClienteDao dao = new ClienteDao(); 

            if (e.getSource() == botonGuardar) {
                System.out.println("Intentando guardar cliente con código: " + codigo);
               
                dao.registrarCliente(cliente); 
                
                JOptionPane.showMessageDialog(this, "¡Cliente NUEVO guardado con éxito en la Base de Datos!");
                
            } else if (e.getSource() == botonActualizar) {
                System.out.println("Intentando modificar cliente con código: " + codigo);
                
                dao.actualizarCliente(cliente);
                JOptionPane.showMessageDialog(this, "¡Modificación del cliente guardada con éxito!");
            }
        
            limpiarCampos();
            cargarDatosTabla(); 
            
        } catch (Exception ex) {
     
            JOptionPane.showMessageDialog(this, "Error crítico en la operación: " + ex.getMessage(), "Detalle del Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void limpiarCampos() {
        campoCodigo.setText(""); campoNombre.setText(""); campoApellido.setText("");
        campoNumeroDoc.setText(""); campoTelefono.setText(""); campoDireccion.setText(""); campoCorreo.setText("");
        if (comboTipoDoc.getItemCount() > 0) comboTipoDoc.setSelectedIndex(0); 
        if (comboTipoCliente.getItemCount() > 0) comboTipoCliente.setSelectedIndex(0); 
        if (comboResponsableTributario.getItemCount() > 0) comboResponsableTributario.setSelectedIndex(0);
        if (comboPais.getItemCount() > 0) comboPais.setSelectedIndex(0);
        campoCodigo.setEditable(true);
        tablaClientes.clearSelection();
    }

    private void seleccionarItemComboPorTexto(JComboBox<ComboItem> combo, String texto) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).toString().equalsIgnoreCase(texto)) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }
}