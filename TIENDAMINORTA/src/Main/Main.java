package Main;

import javax.swing.UIManager;

import co.edu.uptc.controlador.Evento;

public class Main {
    public static void main(String[] args) {
    	
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
			
		}

    	
        javax.swing.SwingUtilities.invokeLater(() -> new Evento().iniciar());
    }
}
