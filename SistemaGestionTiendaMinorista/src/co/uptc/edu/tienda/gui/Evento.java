package co.uptc.edu.tienda.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class Evento implements ActionListener{
	
	public final static String CANCELAR = "Cancelar";
	public final static String LOGIN = "Iniciar Sesión";
	public final static String VER = "Ver";
	public final static String ACTUALIZAR = "Actualizar";
	public final static String ELIMINAR = "Eliminar";
	public final static String CREAR = "Crear";
	public final static String BUSCAR = "Buscar";
	public final static String LIMPIAR = "Limpiar";
	
	public final static String ELIMINAR_PR = "Eliminar_PR";
	public final static String VER_PR = "Ver_PR";
	public final static String ACTUALIZAR_PR = "Actualizar_PR";
	public final static String CREAR_PR = "Nuevo_PR";
	public final static String BUSCAR_PR = "Buscar_PR";
	public final static String LIMPIAR_PR = "Limpiar_PR";
	public final static String CANCELAR_PR = "Cancelar_PR";
	public final static String GUARDAR_PR = "Guardar_PR";
	public final static String EDITAR_PR = "Editar_PR";



	
	public final static String GUARDAR = "Guardar";
	public final static String EDITAR = "Editar";






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
		}else if(evento.equals(CREAR_PR)) {
			ventana.lanzarDialogoProveedor();	
		}else if(evento.equals(CANCELAR_PR)) {
			ventana.cerrarDialogoProveedor();
		}else if(evento.equals(GUARDAR_PR)){
			ventana.crearProveedor();
		}else if(evento.equals(ACTUALIZAR_PR)){
		    ventana.lanzarDialogoModificarProveedor();
		}else if(evento.equals(EDITAR_PR)){
		    ventana.modificarProveedor();
		}else if(evento.equals(ELIMINAR_PR)){
		    ventana.eliminarProveedor();
		}
	}
}
