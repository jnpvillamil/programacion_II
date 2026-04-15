package co.edu.uptc.sistienda.app;

import javax.swing.SwingUtilities;
import co.edu.uptc.sistienda.gui.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
