package co.edu.uptc.interfazGrafica;

import javax.swing.*;
import java.awt.*;

public abstract class DialogoCentral extends JDialog {
    
    protected boolean ok = false;
    protected boolean esCrear; 
    
    public DialogoCentral(JFrame parent, String titulo, boolean esCrear) {
        super(parent, titulo, true);
        this.esCrear = esCrear;
        setLayout(new GridLayout(0, 2, 10, 10));
        setSize(450, 400);
        setLocationRelativeTo(parent);
        
       
        inicializarCampos();
        
        JButton btnAceptar = new JButton(esCrear ? "Guardar" : "Actualizar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnAceptar.addActionListener(e -> {
            if(validarCampos()) {
                ok = true;
                setVisible(false);
            }
        });
        
        btnCancelar.addActionListener(e -> {
            ok = false;
            setVisible(false);
        });
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        
        add(panelBotones);
    }
    
    protected abstract void inicializarCampos();
    protected abstract boolean validarCampos();
    
    public boolean isOk() { return ok; }
}