package co.uptc.edu.tienda.gui;

import javax.swing.*;
import java.awt.*;
import co.uptc.edu.tienda.modelo.Mascota;

public class DialogoMascota extends DialogoCentral {

    private JTextField txNombre, txRaza, txEdad;

    public DialogoMascota(Evento evento, String titulo, boolean isCrear) {
        super(evento, titulo, isCrear);
        setSize(400, 300);
    }

    @Override
    public void iniciarComponentes() {
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panelCentral.setLayout(new GridLayout(3, 2, 10, 15));

        txNombre = new JTextField();
        txRaza   = new JTextField();
        txEdad   = new JTextField();

        panelCentral.add(new JLabel("  Nombre:", SwingConstants.RIGHT));
        panelCentral.add(txNombre);
        panelCentral.add(new JLabel("  Raza:", SwingConstants.RIGHT));
        panelCentral.add(txRaza);
        panelCentral.add(new JLabel("  Edad:", SwingConstants.RIGHT));
        panelCentral.add(txEdad);
    }

    @Override
    public void asignarComandos() {
        btnGuardar.setActionCommand(Evento.GUARDAR_MA);
        btnCerrar.setActionCommand(Evento.CANCELAR_MA);
    }

    public Mascota capturarDatos() throws Exception {
        if (txNombre.getText().trim().isEmpty() ||
            txRaza.getText().trim().isEmpty()   ||
            txEdad.getText().trim().isEmpty()) {
            throw new Exception("Ningún campo puede estar vacío.");
        }

        int edad;
        try {
            edad = Integer.parseInt(txEdad.getText().trim());
        } catch (NumberFormatException e) {
            throw new Exception("La edad es inválida. Por favor ingresa solo números.");
        }

        Mascota nuevo = new Mascota();
        nuevo.setNombre(txNombre.getText().trim());
        nuevo.setRaza(txRaza.getText().trim());
        nuevo.setEdad(edad);
        return nuevo;
    }

    public void cargarDatos(Mascota mascota) {
        txNombre.setText(mascota.getNombre());
        txRaza.setText(mascota.getRaza());
        txEdad.setText(String.valueOf(mascota.getEdad()));
    }
}