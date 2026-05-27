package co.edu.uptc.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.gui.modelo.Cliente;
import co.edu.uptc.enums.TipoDocumentoEnum;
import co.edu.uptc.enums.TipoClienteEnum;
import co.edu.uptc.enums.PoseeResponsabiliadTributaria;

@SuppressWarnings("serial")
public class VentanaInsertar extends JFrame implements ActionListener {

    private JTextField campoCodigo, campoNombre, campoApellido, campoNumeroDoc, campoTelefono, campoDireccion, campoCorreo;
    private JComboBox<TipoDocumentoEnum> comboTipoDoc;
    private JComboBox<TipoClienteEnum> comboTipoCliente;
    private JComboBox<PoseeResponsabiliadTributaria> comboResponsableTributario;
    private JComboBox<ComboItem> comboPais;
    private JComboBox<ComboItem> comboCiudad;
    
    private JButton botonGuardar, botonActualizar, botonLimpiar;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;


    class ComboItem {
        private int id;
        private String value;
        public ComboItem(int id, String value) { this.id = id; this.value = value; }
        public int getId() { return id; }
        @Override public String toString() { return value; }
    }

    public VentanaInsertar() {
        setTitle("Módulo Maestro de Clientes (Con Ubicación) - UPTC");
        setSize(1050, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        iniciarComponentesFormulario();
        iniciarComponenteTabla();
        
        cargarPaises();    
        cargarDatosTabla(); 
    }

    private void iniciarComponentesFormulario() {
        JPanel panelForm = new JPanel(new GridLayout(5, 4, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));

        panelForm.add(new JLabel("Código (*clave):"));
        campoCodigo = new JTextField();
        panelForm.add(campoCodigo);

        panelForm.add(new JLabel("Nombre:"));
        campoNombre = new JTextField();
        panelForm.add(campoNombre);

        panelForm.add(new JLabel("Apellido:"));
        campoApellido = new JTextField();
        panelForm.add(campoApellido);

        panelForm.add(new JLabel("Tipo Documento:"));
        comboTipoDoc = new JComboBox<>(TipoDocumentoEnum.values());
        panelForm.add(comboTipoDoc);

        panelForm.add(new JLabel("N° Documento:"));
        campoNumeroDoc = new JTextField();
        panelForm.add(campoNumeroDoc);

        panelForm.add(new JLabel("Teléfono:"));
        campoTelefono = new JTextField();
        panelForm.add(campoTelefono);

        panelForm.add(new JLabel("Dirección:"));
        campoDireccion = new JTextField();
        panelForm.add(campoDireccion);

        // CONFIGURACIÓN COMBO PAÍS
        panelForm.add(new JLabel("País:"));
        comboPais = new JComboBox<>();
        panelForm.add(comboPais);
     
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

        panelForm.add(new JLabel("Ciudad:"));
        comboCiudad = new JComboBox<>();
        panelForm.add(comboCiudad);

        panelForm.add(new JLabel("Tipo Cliente:"));
        comboTipoCliente = new JComboBox<>(TipoClienteEnum.values());
        panelForm.add(comboTipoCliente);

        panelForm.add(new JLabel("Correo Electrónico:"));
        campoCorreo = new JTextField();
        panelForm.add(campoCorreo);

        panelForm.add(new JLabel("Resp. Tributario:"));
        comboResponsableTributario = new JComboBox<>(PoseeResponsabiliadTributaria.values());
        panelForm.add(comboResponsableTributario);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        botonGuardar = new JButton("Guardar Nuevo");
        botonActualizar = new JButton("Modificar Seleccionado");
        botonLimpiar = new JButton("Limpiar Campos");

        botonGuardar.addActionListener(this);
        botonActualizar.addActionListener(this);
        botonLimpiar.addActionListener(this);

        panelAcciones.add(botonGuardar);
        panelAcciones.add(botonActualizar);
        panelAcciones.add(botonLimpiar);

        JPanel contenedorSuperior = new JPanel(new BorderLayout(5, 5));
        contenedorSuperior.add(panelForm, BorderLayout.CENTER);
        contenedorSuperior.add(panelAcciones, BorderLayout.SOUTH);

        add(contenedorSuperior, BorderLayout.NORTH);
    }

    private void iniciarComponenteTabla() {
        String[] columnas = {"Código", "Nombre", "Apellido", "Tipo Doc", "N° Doc", "Teléfono", "Dirección", "País", "Ciudad", "Tipo Cliente", "Estado", "Correo", "Resp. Tributario"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaClientes.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    campoCodigo.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
                    campoNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
                    campoApellido.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
                    comboTipoDoc.setSelectedItem(TipoDocumentoEnum.valueOf(modeloTabla.getValueAt(filaSeleccionada, 3).toString()));
                    campoNumeroDoc.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
                    campoTelefono.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
                    campoDireccion.setText(modeloTabla.getValueAt(filaSeleccionada, 6).toString());
            
                    seleccionarItemComboPorTexto(comboPais, modeloTabla.getValueAt(filaSeleccionada, 7).toString());
                    seleccionarItemComboPorTexto(comboCiudad, modeloTabla.getValueAt(filaSeleccionada, 8).toString());

                    comboTipoCliente.setSelectedItem(TipoClienteEnum.valueOf(modeloTabla.getValueAt(filaSeleccionada, 9).toString()));
                    campoCorreo.setText(modeloTabla.getValueAt(filaSeleccionada, 11).toString());
                    comboResponsableTributario.setSelectedItem(PoseeResponsabiliadTributaria.valueOf(modeloTabla.getValueAt(filaSeleccionada, 12).toString()));
                    
                    campoCodigo.setEditable(false);
                }
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaClientes);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Registros en Base de Datos"));
        add(scrollTabla, BorderLayout.CENTER);
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

        ComboItem paisSeleccionado = (ComboItem) comboPais.getSelectedItem();
        ComboItem ciudadSeleccionada = (ComboItem) comboCiudad.getSelectedItem();
        
        int idPais = (paisSeleccionado != null) ? paisSeleccionado.getId() : 0;
        int idCiudad = (ciudadSeleccionada != null) ? ciudadSeleccionada.getId() : 0;

        Cliente cliente = new Cliente(
                campoCodigo.getText(), campoNombre.getText(), campoApellido.getText(),
                (TipoDocumentoEnum) comboTipoDoc.getSelectedItem(), campoNumeroDoc.getText(),
                campoTelefono.getText(), campoDireccion.getText(), 
                idPais, idCiudad, // 
                (TipoClienteEnum) comboTipoCliente.getSelectedItem(), true, campoCorreo.getText(),
                (PoseeResponsabiliadTributaria) comboResponsableTributario.getSelectedItem()
        );

        if (e.getSource() == botonGuardar) {
            cliente.registrar();
        } else if (e.getSource() == botonActualizar) {
            cliente.modificar();
        }
        
        limpiarCampos();
        cargarDatosTabla();
    }

    private void limpiarCampos() {
        campoCodigo.setText(""); campoNombre.setText(""); campoApellido.setText("");
        campoNumeroDoc.setText(""); campoTelefono.setText(""); campoDireccion.setText(""); campoCorreo.setText("");
        comboTipoDoc.setSelectedIndex(0); comboTipoCliente.setSelectedIndex(0); comboResponsableTributario.setSelectedIndex(0);
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