package co.uptc.edu.co.modelo;

import java.time.LocalDate;

import co.uptc.edu.co.modelo.enums.FormaPago;
import co.uptc.edu.co.modelo.enums.MedioPago;
import co.uptc.edu.co.modelo.enums.TipoFactura;
import co.uptc.edu.co.modelo.enums.TipoPersona;

public class FacturaVenta {
	private String cliente;
	private TipoFactura tipoFactura;
	private String nitOCC;
	private TipoPersona tipoPersona;
	private String direccion;
	private String ciudad;
	private String correo;
	private String telefono;
	private String responsFiscal;
	private String responsTributaria;
	private boolean facturaElectronica;
	private LocalDate fechaCreacion;
	private LocalDate fechaValidacion;
	private LocalDate fechaVencimiento;
	private FormaPago formaPago;
	private MedioPago medioPago;
	private String CUFE;
	private String detalleProductos;

	public FacturaVenta() {
	}

	FacturaVenta(String cliente, TipoFactura tipoFactura, String nitOCC, TipoPersona tipoPersona, String direccion,
			String ciudad, String correo, String telefono, String responsFiscal, String responsTributaria,
			boolean facturaElectronica, LocalDate fechaCreacion, LocalDate fechaValidacion, LocalDate fechaVencimiento,
			FormaPago formaPago, MedioPago medioPago, String cUFE, String detalleProductos) {
		this.cliente = cliente;
		this.tipoFactura = tipoFactura;
		this.nitOCC = nitOCC;
		this.tipoPersona = tipoPersona;
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.correo = correo;
		this.telefono = telefono;
		this.responsFiscal = responsFiscal;
		this.responsTributaria = responsTributaria;
		this.facturaElectronica = facturaElectronica;
		this.fechaCreacion = fechaCreacion;
		this.fechaValidacion = fechaValidacion;
		this.fechaVencimiento = fechaVencimiento;
		this.formaPago = formaPago;
		this.medioPago = medioPago;
		CUFE = cUFE;
		this.detalleProductos = detalleProductos;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public TipoFactura getTipoFactura() {
		return tipoFactura;
	}

	public void setTipoFactura(TipoFactura tipoFactura) {
		this.tipoFactura = tipoFactura;
	}

	public String getNitOCC() {
		return nitOCC;
	}

	public void setNitOCC(String nitOCC) {
		this.nitOCC = nitOCC;
	}

	public TipoPersona getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(TipoPersona tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getResponsFiscal() {
		return responsFiscal;
	}

	public void setResponsFiscal(String responsFiscal) {
		this.responsFiscal = responsFiscal;
	}

	public String getResponsTributaria() {
		return responsTributaria;
	}

	public void setResponsTributaria(String responsTributaria) {
		this.responsTributaria = responsTributaria;
	}

	public boolean isFacturaElectronica() {
		return facturaElectronica;
	}

	public void setFacturaElectronica(boolean facturaElectronica) {
		this.facturaElectronica = facturaElectronica;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDate getFechaValidacion() {
		return fechaValidacion;
	}

	public void setFechaValidacion(LocalDate fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
	}

	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public FormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

	public MedioPago getMedioPago() {
		return medioPago;
	}

	public void setMedioPago(MedioPago medioPago) {
		this.medioPago = medioPago;
	}

	public String getCUFE() {
		return CUFE;
	}

	public void setCUFE(String cUFE) {
		CUFE = cUFE;
	}

	public String getDetalleProductos() {
		return detalleProductos;
	}

	public void setDetalleProductos(String detalleProductos) {
		this.detalleProductos = detalleProductos;
	}

}
