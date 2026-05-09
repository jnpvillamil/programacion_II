package co.uptc.edu.tienda.gui;

import javax.swing.*;
import java.awt.*;

import co.uptc.edu.tienda.modelo.Proveedor;

public class DialogoProveedor extends DialogoCentral {
    private int codigoActual;
    private JTextField txRazonSocial, txNit, txDireccion, txTelefono, txCorreo;

    public DialogoProveedor(Evento evento, String titulo, boolean isCrear) {
        super(evento, titulo, isCrear);
        setSize(400, 400); // Tamaño ajustado para Proveedor
    }

    @Override
    public void iniciarComponentes() {
    	panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panelCentral.setLayout(new GridLayout(5, 2, 10, 15));
        
        txRazonSocial = new JTextField();
        txNit = new JTextField();
        txDireccion = new JTextField();
        txTelefono = new JTextField();
        txCorreo = new JTextField();

        panelCentral.add(new JLabel("  Razón Social:", SwingConstants.RIGHT));
        panelCentral.add(txRazonSocial);
        panelCentral.add(new JLabel("  NIT:", SwingConstants.RIGHT));
        panelCentral.add(txNit);
        panelCentral.add(new JLabel("  Dirección:", SwingConstants.RIGHT));
        panelCentral.add(txDireccion);
        panelCentral.add(new JLabel("  Teléfono:", SwingConstants.RIGHT));
        panelCentral.add(txTelefono);
        panelCentral.add(new JLabel("  Correo:", SwingConstants.RIGHT));
        panelCentral.add(txCorreo);
    }

    @Override
    public void asignarComandos() {
        btnGuardar.setActionCommand(isCrear ? Evento.GUARDAR_PR : Evento.EDITAR_PR);
        btnCerrar.setActionCommand(Evento.CANCELAR_PR);
    }


    public Proveedor capturarDatos() throws Exception {
        if (txRazonSocial.getText().trim().isEmpty() || txNit.getText().trim().isEmpty()
                || txDireccion.getText().trim().isEmpty() || txTelefono.getText().trim().isEmpty()
                || txCorreo.getText().trim().isEmpty()) {
            throw new Exception("Ningún campo puede estar vacío.");
        }
        
        long telefono;
        try {
            telefono = Long.parseLong(txTelefono.getText().trim());
        } catch(NumberFormatException e) {
            throw new Exception("El teléfono es inválido. Por favor ingresa solo números");
        }

        Proveedor nuevo;
        if (isCrear) {
            nuevo = new Proveedor(); 
        } else {
            nuevo = new Proveedor(codigoActual); 
        }

        nuevo.setRazonSocial(txRazonSocial.getText().trim());
        nuevo.setNit(txNit.getText().trim());
        nuevo.setDireccionP(txDireccion.getText().trim());
        nuevo.setTelefonoP(telefono);
        nuevo.setCorreoP(txCorreo.getText().trim());
        
        return nuevo;
    }


    public void cargarDatos(Proveedor proveedor) {
        this.codigoActual = proveedor.getCodigoProveedor();
        txRazonSocial.setText(proveedor.getRazonSocial());
        txNit.setText(proveedor.getNit());
        txDireccion.setText(proveedor.getDireccionP());
        txTelefono.setText(String.valueOf(proveedor.getTelefonoP()));
        txCorreo.setText(proveedor.getCorreoP());
    }
}
