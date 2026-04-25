package co.uptc.edu.co.gui.dialog;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import co.uptc.edu.co.gui.Evento;
import co.uptc.edu.co.modelo.Cliente;
import co.uptc.edu.co.modelo.enums.EstadoEnum;
import co.uptc.edu.co.modelo.enums.TipoClienteEnum;
import co.uptc.edu.co.modelo.enums.TipoDocEnum;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class DialogCliente extends JDialog {

    private JTextField campoCodigo;
    private JTextField campoNombre;
    private JComboBox<TipoDocEnum> comboTipoIdentificacion;
    private JTextField campoNumeroIdentificacion;
    private JTextField campoDireccion;
    private JTextField campoTelefono;
    private JComboBox<TipoClienteEnum> comboTipoCliente;

    private JButton botonGuardar;
    private JButton botonCancelar;

    private boolean modoEdicion;

    public DialogCliente(Frame propietario) {
        this(propietario, null);
    }

    public DialogCliente(Frame propietario, Evento evento) {
        super(propietario, "Registrar Cliente", true);
        modoEdicion = false;
        inicializarComponentes();
        configurarDialogo();
        agregarComponentes();
        inicializarEventos(evento);
    }

    private void inicializarComponentes() {
        campoCodigo = new JTextField(25);
        campoNombre = new JTextField(25);

        comboTipoIdentificacion = new JComboBox<>(TipoDocEnum.values());

        campoNumeroIdentificacion = new JTextField(25);
        campoDireccion = new JTextField(25);
        campoTelefono = new JTextField(25);

        comboTipoCliente = new JComboBox<>(TipoClienteEnum.values());

        botonGuardar = new JButton("Guardar");
        botonCancelar = new JButton("Cancelar");

        botonGuardar.setBackground(new Color(46, 125, 50));
        botonGuardar.setForeground(Color.WHITE);

        botonCancelar.setBackground(new Color(220, 220, 220));
    }

    private void configurarDialogo() {
        setSize(420, 520);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void agregarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 4, 0);

        panelPrincipal.add(new JLabel("Código:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoCodigo, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Nombre:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoNombre, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Tipo de identificación:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(comboTipoIdentificacion, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Número de identificación:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoNumeroIdentificacion, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Dirección:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoDireccion, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Teléfono:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoTelefono, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Tipo de cliente:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(comboTipoCliente, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0);
        panelPrincipal.add(panelBotones, gbc);

        add(panelPrincipal);
    }

    private void inicializarEventos(Evento evento) {
        botonCancelar.addActionListener(e -> dispose());

        if (evento != null) {
            botonGuardar.setActionCommand(Evento.CMD_CONFIRMAR_CLIENTE);
            botonGuardar.addActionListener(evento);
        } else {
            botonGuardar.addActionListener(e -> {
                try {
                    Cliente cliente = obtenerCliente();
                    JOptionPane.showMessageDialog(
                            this,
                            "Cliente válido: " + cliente.getNombre(),
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            this,
                            ex.getMessage(),
                            "Error de validación",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            });
        }
    }

    public void configurarModoEdicion() {
        modoEdicion = true;
        setTitle("Editar Cliente");
        botonGuardar.setText("Actualizar");
        botonGuardar.setActionCommand(Evento.CMD_CONFIRMAR_EDICION_CLIENTE);
        campoCodigo.setEditable(false);
    }

    public Cliente obtenerCliente() throws Exception {
        String codigo = campoCodigo.getText().trim();
        String nombre = campoNombre.getText().trim();
        TipoDocEnum tipoIdentificacion = (TipoDocEnum) comboTipoIdentificacion.getSelectedItem();
        String numeroIdentificacion = campoNumeroIdentificacion.getText().trim();
        String direccion = campoDireccion.getText().trim();
        String telefono = campoTelefono.getText().trim();
        TipoClienteEnum tipoCliente = (TipoClienteEnum) comboTipoCliente.getSelectedItem();

        validarCamposObligatorios(
                codigo,
                nombre,
                numeroIdentificacion,
                direccion,
                telefono
        );

        EstadoEnum estado = EstadoEnum.ACTIVO;

        return new Cliente(
                codigo,
                nombre,
                tipoIdentificacion,
                numeroIdentificacion,
                direccion,
                telefono,
                tipoCliente,
                estado
        );
    }

    private void validarCamposObligatorios(
            String codigo,
            String nombre,
            String numeroIdentificacion,
            String direccion,
            String telefono
    ) throws Exception {

        if (codigo.isEmpty()) {
            throw new Exception("El código es obligatorio.");
        }

        if (nombre.isEmpty()) {
            throw new Exception("El nombre es obligatorio.");
        }

        if (numeroIdentificacion.isEmpty()) {
            throw new Exception("El número de identificación es obligatorio.");
        }

        if (direccion.isEmpty()) {
            throw new Exception("La dirección es obligatoria.");
        }

        if (telefono.isEmpty()) {
            throw new Exception("El teléfono es obligatorio.");
        }
    }

    public void cargarCliente(Cliente cliente) {
        campoCodigo.setText(cliente.getCodigo());
        campoNombre.setText(cliente.getNombre());
        comboTipoIdentificacion.setSelectedItem(cliente.getTipoIdentificacion());
        campoNumeroIdentificacion.setText(cliente.getNumeroIdentificacion());
        campoDireccion.setText(cliente.getDireccion());
        campoTelefono.setText(cliente.getTelefono());
        comboTipoCliente.setSelectedItem(cliente.getTipoCliente());
    }
}