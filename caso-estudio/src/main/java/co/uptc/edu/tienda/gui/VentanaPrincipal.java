package co.uptc.edu.tienda.gui;

import javax.swing.*;

import co.uptc.edu.tienda.negocio.GestionSeguridad;
import co.uptc.edu.tienda.negocio.Proveedor;
import co.uptc.edu.tienda.negocio.ProveedorConfig;
import co.uptc.edu.tienda.negocio.dto.CredencialDto;

import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {
	private PanelLogin pLogin;
	private PanelPadreProveedor pCentral;
	private GestionSeguridad seguridad;
	private DialogoProveedor nuevoProveedor;
	private Evento evento;
	private ProveedorConfig proveedorConfig;

	public VentanaPrincipal() {
		setSize(400,300);
		setTitle("Tienda Minorista");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		//Inicializar relaciones o asociaciones
		
		evento = new Evento(this);
		pLogin = new PanelLogin(evento);
		pCentral = new PanelPadreProveedor(evento);
		seguridad = new GestionSeguridad();
		proveedorConfig = new ProveedorConfig();
		add(pLogin,BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		VentanaPrincipal login = new VentanaPrincipal();
		login.setVisible(true);
		//MODELO SOLID
	}
	
	public void loguear() {
		try {
			CredencialDto validar = pLogin.getCredencialesUsuario();
			if(validar!=null && seguridad.validarLogueo(validar)) {
				pLogin.setVisible(false);
				add(pCentral, BorderLayout.CENTER);
				pCentral.poblarTabla(proveedorConfig.getGestProveedor().leerProveedores());
				pCentral.setVisible(true);
				this.setSize(800,500);
			}else {
				JOptionPane.showMessageDialog(this, "Usuario o contraseña no válida");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

	}
	
	public void lanzarDialogoProveedor() {
	    nuevoProveedor = new DialogoProveedor(evento, "Crear Proveedor", true);
	    nuevoProveedor.setVisible(Boolean.TRUE);
	    nuevoProveedor.setSize(400,400);
	    nuevoProveedor.setLocationRelativeTo(null);
	}

	public void cerrarDialogoProveedor() {
	    nuevoProveedor.setVisible(Boolean.FALSE);
	    nuevoProveedor = null;
	}

	public void crearProveedor() {	
		proveedorConfig.getGestProveedor().agregarProveedor(nuevoProveedor.capturarDatos()); 
		cerrarDialogoProveedor();
		pCentral.poblarTabla(proveedorConfig.getGestProveedor().leerProveedores());
	}
	
	public void lanzarDialogoModificarProveedor() {
	    int codigo = pCentral.getItemSeleccionado(); // obtiene el código de la fila
	    Proveedor p = proveedorConfig.getGestProveedor().buscarProveedorPorCodigo(codigo); // busca el proveedor
	    nuevoProveedor = new DialogoProveedor(evento, "Modificar Proveedor", false);
	    nuevoProveedor.cargarDatos(p); // precarga los datos en el diálogo
	    nuevoProveedor.setVisible(true);
	    nuevoProveedor.setSize(400, 400);
	    nuevoProveedor.setLocationRelativeTo(null);
	}

	public void modificarProveedor() {
	    int codigo = pCentral.getItemSeleccionado();
	    Proveedor p = nuevoProveedor.capturarDatos();
	   
	    proveedorConfig.getGestProveedor().modificarProveedor(p);
	    cerrarDialogoProveedor();
	    pCentral.poblarTabla(proveedorConfig.getGestProveedor().leerProveedores());
	}
	

	
	
	public void eliminarProveedor() {
		int codigo = pCentral.getItemSeleccionado();

	    // 2. Mostrar el diálogo de confirmación
	    int respuesta = JOptionPane.showConfirmDialog(
	        this, 
	        "¿Está seguro de que desea eliminar al proveedor con código " + codigo + "?", 
	        "Confirmar Eliminación", 
	        JOptionPane.YES_NO_OPTION, 
	        JOptionPane.WARNING_MESSAGE
	    );

	    // 3. Solo si el usuario presiona "SÍ", procedemos a borrar
	    if (respuesta == JOptionPane.YES_OPTION) {
	        proveedorConfig.getGestProveedor().eliminarProveedor(codigo);
	        
	        // Refrescar la tabla para mostrar los cambios
	        pCentral.poblarTabla(proveedorConfig.getGestProveedor().leerProveedores());
	        
	        JOptionPane.showMessageDialog(this, "Proveedor eliminado exitosamente.");
	    }
		
	}



}
