package co.uptc.edu.co.modelo;

import co.uptc.edu.co.modelo.enums.EstadoEnum;
import co.uptc.edu.co.modelo.enums.TipoClienteEnum;
import co.uptc.edu.co.modelo.enums.TipoDocEnum;

public class Cliente extends Persona {

	private String codigo;
	private String nombre;
	private TipoDocEnum tipoIdentificacion;
	private String numeroIdentificacion;
	private TipoClienteEnum tipoCliente;

	public Cliente() {
		super();
	}

	public Cliente(String codigo, String nombre, TipoDocEnum tipoIdentificacion,
			String numeroIdentificacion,String direccion, String telefono,
			TipoClienteEnum tipoCliente, EstadoEnum estado)
			 {
        super(direccion,telefono,estado);
		this.codigo = codigo;
		this.nombre = nombre;
		this.tipoIdentificacion = tipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion;
		this.tipoCliente = tipoCliente;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoDocEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(TipoDocEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}


	public TipoClienteEnum getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoClienteEnum tipoCliente) {
		this.tipoCliente = tipoCliente;
	}


	@Override
	public String toString() {
		return codigo + " - " + nombre;
	}
}