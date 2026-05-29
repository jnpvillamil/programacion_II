package co.edu.uptc.caso.estudio;

import javax.swing.SwingUtilities;

import co.edu.uptc.gui.VentanaPrincipal;

public class Main {
	public static void main(String[] args) {
		// Esto intentará abrir la conexión a la base de datos y luego la cerrará
		co.edu.uptc.conexion.Conexion prueba = new co.edu.uptc.conexion.Conexion();
		prueba.desconectar();
		// Ejecutamos la interfaz gráfica en el hilo de despacho de eventos de Swing
		SwingUtilities.invokeLater(() -> {
			VentanaPrincipal ventana = new VentanaPrincipal();
			ventana.setVisible(true);
		});
	}
}