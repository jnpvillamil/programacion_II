package co.edu.uptc.tiendaminorista.gui;

import javax.swing.SwingUtilities;
import co.edu.uptc.tiendaminorista.persistencia.DatabaseConnection;

/**
 * Punto de entrada de la aplicación.
 * Crea la base de datos y las tablas automáticamente si no existen,
 * luego abre la ventana principal.
 *
 * Requisito: tener XAMPP abierto con MySQL iniciado.
 */
public class Main {

    public static void main(String[] args) {
        DatabaseConnection.iniciarBaseDatos();

        SwingUtilities.invokeLater(() -> {
            PanelPrincipal ventana = new PanelPrincipal();
            ventana.setVisible(true);
        });
    }
}
