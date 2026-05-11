package co.edu.uptc.controlador;

import java.awt.CardLayout;
import javax.swing.JPanel;

public class ControladorPrincipal {
    private JPanel panelContenedor;
    private CardLayout cardLayout;

    public ControladorPrincipal(JPanel panelContenedor, CardLayout cardLayout) {
        this.panelContenedor = panelContenedor;
        this.cardLayout = cardLayout;
    }

    public void mostrarPanel(String nombrePanel) {
        this.cardLayout.show(this.panelContenedor, nombrePanel);
    }

    public JPanel getPanelContenedor() {
        return panelContenedor;
    }

    public void setPanelContenedor(JPanel panelContenedor) {
        this.panelContenedor = panelContenedor;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }
}