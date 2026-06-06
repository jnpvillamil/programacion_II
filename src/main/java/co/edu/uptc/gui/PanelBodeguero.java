package co.edu.uptc.gui;

import co.edu.uptc.interfaces.ManejadorEventoBodeguero;

import javax.swing.JPanel;

public class PanelBodeguero extends JPanel {

    private final ManejadorEventoBodeguero manejadorEvento;

    public PanelBodeguero(ManejadorEventoBodeguero manejadorEvento) {
        this.manejadorEvento = manejadorEvento;
    }
}
