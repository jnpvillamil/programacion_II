package co.uptc.edu.tienda.gui;
import javax.swing.*;
import java.awt.*;

public abstract class DialogoCentral extends JDialog{

	protected boolean isCrear;
	protected String tituloDialogo;
	protected JTextField txRazonSocial,txNit,txDireccion,txTelefono,txCorreo;
	protected JButton btnGuardar;
	protected JButton btnCerrar;

	public DialogoCentral(Evento evento, String tituloDialogo, boolean isCrear) {
	    this.isCrear = isCrear;
	    setSize(250, 250);
	    setTitle(tituloDialogo);
	    setLayout(new BorderLayout());

	    txRazonSocial = new JTextField();
	    txNit = new JTextField();
	    txDireccion = new JTextField();
	    txTelefono = new JTextField();
	    txCorreo = new JTextField();

	    JPanel pProveedorPadre = new JPanel();
	    JPanel pProveedor = new JPanel();

	    pProveedor.setLayout(new GridLayout(5, 2));
	    pProveedor.add(new JLabel("Razón Social"));
	    pProveedor.add(txRazonSocial);
	    pProveedor.add(new JLabel("NIT"));
	    pProveedor.add(txNit);
	    pProveedor.add(new JLabel("Dirección"));
	    pProveedor.add(txDireccion);
	    pProveedor.add(new JLabel("Teléfono"));
	    pProveedor.add(txTelefono);
	    pProveedor.add(new JLabel("Correo"));
	    pProveedor.add(txCorreo);
	    
	    pProveedorPadre.add(pProveedor);
	    
	    if(isCrear) {
	    	btnGuardar = new JButton(Evento.GUARDAR);
	    }else {
	    	btnGuardar = new JButton(Evento.EDITAR);
	    }
	    btnCerrar = new JButton(Evento.CANCELAR);

	    btnGuardar.addActionListener(evento);
	    btnCerrar.addActionListener(evento);
	    btnCerrar.setActionCommand(Evento.CANCELAR_PR);

	    JPanel pBotones = new JPanel();
	    pBotones.add(btnCerrar);
	    pBotones.add(btnGuardar);

	    add(pProveedorPadre, BorderLayout.CENTER);
	    add(pBotones, BorderLayout.SOUTH);

	    asignarComandoBotones();
	}
	
	public abstract void asignarComandoBotones();
}
