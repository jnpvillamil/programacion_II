package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public abstract class PanelBase extends JPanel {

    public PanelBase() {
        super();
        configurarPanel();
        initComponents();
    }

    private void configurarPanel() {
        this.setBackground(ConstructorComponentes.GRIS_FONDO);
        this.setBorder(new EmptyBorder(20, 20, 20, 20));
    }

    public abstract void initComponents();
}