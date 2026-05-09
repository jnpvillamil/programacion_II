package co.uptc.edu.tienda.gui;

import javax.swing.*;
import java.awt.*;

public abstract class DialogoCentral extends JDialog {
    protected boolean isCrear;
    protected JButton btnGuardar, btnCerrar;
    protected JPanel panelCentral;

    public DialogoCentral(Evento evento, String titulo, boolean isCrear) {
        this.isCrear = isCrear;
        setTitle(titulo);
        setModal(true);
        setLayout(new BorderLayout());
        
        
        
        panelCentral = new JPanel();
        // Los hijos llenarán este panel en iniciarComponentes()
        
        btnGuardar = new JButton(isCrear ? "Guardar" : "Modificar");
        btnCerrar = new JButton("Cancelar");

        btnGuardar.addActionListener(evento);
        btnCerrar.addActionListener(evento);

        JPanel pBotones = new JPanel();
        pBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        pBotones.add(btnCerrar);
        pBotones.add(btnGuardar);	

        add(panelCentral, BorderLayout.CENTER);
        add(pBotones, BorderLayout.SOUTH);

        iniciarComponentes();
        asignarComandos();
    }

    public abstract void iniciarComponentes();
    public abstract void asignarComandos();
}
