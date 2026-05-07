package co.uptc.edu.tienda.gui;

import co.uptc.edu.tienda.modelo.Proveedor;

public class DialogoProveedor extends DialogoCentral {
	private int codigoActual;
	
	public DialogoProveedor(Evento evento, String tituloDialo, boolean isCrear) {
	    super(evento, tituloDialo, isCrear);
	    // TODO Auto-generated constructor stub
	}

	public Proveedor capturarDatos() {
		  Proveedor nuevo;
		    if (isCrear) {
		        nuevo = new Proveedor(); // genera código nuevo con el contador
		    } else {
		        nuevo = new Proveedor(codigoActual); // usa el código original, no incrementa contador
		    }
	    nuevo.setRazonSocial(txRazonSocial.getText());
	    nuevo.setNit(txNit.getText());
	    nuevo.setDireccionP(txDireccion.getText());
	    nuevo.setTelefonoP(Long.parseLong(txTelefono.getText()));
	    nuevo.setCorreoP(txCorreo.getText());
	    return nuevo;
	    
	}

	@Override
	public void asignarComandoBotones() {
	    if (isCrear) {
	        btnGuardar.setActionCommand(Evento.GUARDAR_PR);
	    } else {
	        btnGuardar.setActionCommand(Evento.EDITAR_PR);
	    }

	}
	
	public void cargarDatos(Proveedor proveedor) {
		codigoActual = proveedor.getCodigoProveedor();
	    txRazonSocial.setText(proveedor.getRazonSocial());
	    txNit.setText(proveedor.getNit());
	    txDireccion.setText(proveedor.getDireccionP());
	    txTelefono.setText(String.valueOf(proveedor.getTelefonoP()));
	    txCorreo.setText(proveedor.getCorreoP());
	}

}
