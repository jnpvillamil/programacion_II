package co.uptc.edu.tienda.gui;

import javax.swing.*;
import java.awt.*;

import co.uptc.edu.tienda.enums.TipoClienteEnum;
import co.uptc.edu.tienda.enums.TipoDocEnum;
import co.uptc.edu.tienda.modelo.Cliente;

public class DialogoCliente extends DialogoCentral {

    private int codigoActual;
    private JTextField txNombre, txDocumento, txDireccion, txTelefono, txCorreo;
    private JComboBox cbTipoCliente, cbTipoDocumento; 
    public DialogoCliente(Evento evento, String titulo, boolean isCrear) {
        super(evento, titulo, isCrear);
        setSize(400, 400);
    }

    @Override
    public void iniciarComponentes() {

        panelCentral.setBorder(
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        panelCentral.setLayout(
            new GridLayout(7, 2, 10, 15)
        );
        
        txNombre = new JTextField();
        cbTipoDocumento = new JComboBox<>(TipoDocEnum.values());
        txDocumento = new JTextField();
        txDireccion = new JTextField();
        txTelefono = new JTextField();
        txCorreo = new JTextField();
        cbTipoCliente = new JComboBox<>(TipoClienteEnum.values());

        panelCentral.add(
            new JLabel("  Nombre:", SwingConstants.RIGHT)
        );
        panelCentral.add(txNombre);
        
        panelCentral.add(
                new JLabel("  Tipo de documento:", SwingConstants.RIGHT)
            );
            panelCentral.add (cbTipoDocumento); 
        

        panelCentral.add(
            new JLabel("  Documento:", SwingConstants.RIGHT)
        );
        panelCentral.add(txDocumento);

        panelCentral.add(
            new JLabel("  Dirección:", SwingConstants.RIGHT)
        );
        panelCentral.add(txDireccion);

        panelCentral.add(
            new JLabel("  Teléfono:", SwingConstants.RIGHT)
        );
        panelCentral.add(txTelefono);
        
        panelCentral.add(
                new JLabel("  Tipo de cliente:", SwingConstants.RIGHT)
            );
            panelCentral.add (cbTipoCliente); 
        

        panelCentral.add(
            new JLabel("  Correo:", SwingConstants.RIGHT)
        );
        panelCentral.add(txCorreo);
    }

    @Override
    public void asignarComandos() {

        btnGuardar.setActionCommand(
            isCrear ? Evento.GUARDAR_CLI : Evento.EDITAR_CLI
        );

        btnCerrar.setActionCommand(
            Evento.CANCELAR_CLI
        );
    }

    public Cliente capturarDatos() throws Exception {

        if (txNombre.getText().trim().isEmpty()
        		|| cbTipoCliente.getSelectedItem().toString().trim().isEmpty()
                || txDocumento.getText().trim().isEmpty()
                || txDireccion.getText().trim().isEmpty()
                || txTelefono.getText().trim().isEmpty()
                || cbTipoDocumento.getSelectedItem().toString().trim().isEmpty()
                || txCorreo.getText().trim().isEmpty()) {

            throw new Exception(
                "Ningún campo puede estar vacío."
            );
        }

        long telefono;

        try {

            telefono = Long.parseLong(
                txTelefono.getText().trim()
            );

        } catch (NumberFormatException e) {

            throw new Exception(
                "El teléfono es inválido. Solo debe contener números."
            );
        }

        Cliente cliente;

        if (isCrear) {

            cliente = new Cliente();

        } else {

            cliente = new Cliente(codigoActual);
        }

        cliente.setNombreCompleto(
            txNombre.getText().trim()
        );
        
        cliente.setTipoCliente(
        	    TipoClienteEnum.valueOf(cbTipoCliente.getSelectedItem().toString())
        	);

        cliente.setNumeroDocumento(
            Long.parseLong(
                txDocumento.getText().trim()
            )
        );

        cliente.setDireccionC(
            txDireccion.getText().trim()
        );

        cliente.setTelefonoC(telefono);
        
        cliente.setTipoDocumento(
        	    TipoDocEnum.valueOf(cbTipoDocumento.getSelectedItem().toString())
        	);

        return cliente;
    }

    public void cargarDatos(Cliente cliente) {

        this.codigoActual = cliente.getIdCliente();

        txNombre.setText(
            cliente.getNombreCompleto()
        );

        txDocumento.setText(
            String.valueOf(
                cliente.getNumeroDocumento()
            )
        );

        txDireccion.setText(
            cliente.getDireccionC()
        );

        txTelefono.setText(
            String.valueOf(
                cliente.getTelefonoC()
            )
        );
    }
}