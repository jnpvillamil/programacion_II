package co.uptc.edu.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class Evento implements ActionListener{
	
	public final static String CANCELAR = "Cancelar";
	public final static String LOGIN = "Iniciar Sesión";


	private VentanaPrincipal ventana;
	
	Evento(VentanaPrincipal v){
		ventana=v;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String evento = e.getActionCommand();
		if(evento.equals(CANCELAR)) {
			JOptionPane.showMessageDialog(null,"Hasta Luego" );
		}else if(evento.equals(LOGIN)){
			ventana.loguear();

		}
		}
}
