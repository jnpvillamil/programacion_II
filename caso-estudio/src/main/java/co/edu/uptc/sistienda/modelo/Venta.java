package co.edu.uptc.sistienda.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.sistienda.modelo.enums.FormaPagoEnum;

public class Venta {

	private String          numeroFactura;
	private LocalDateTime   fechaHora;
	private LocalDateTime   fechaVencimiento;
	private Cliente         cliente;
	private FormaPagoEnum   formaPago;
	private String          medioPago;
	private boolean         anulada;
	private List<DetalleVenta> items; 
	
	public Venta(){
		this.items              =new ArrayList<>();
		this.fechaHora          =LocalDateTime.now();
		this.fechaVencimiento   =fechaHora.plusDays(7);
		this.anulada            =false; 
		this.medioPago          ="";
	}
	
	public Venta(String numeroFactura, Cliente cliente, FormaPagoEnum formaPago){
		this();
		this.numeroFactura   =numeroFactura;
		this.cliente         =cliente;
		this.formaPago       =formaPago;
		this.medioPago       =formaPago !=null ? formaPago.getDescripcion() : "";	
	}
	
	//Gestión de Items 
	
	public void agregarItem(DetalleVenta item) {
		items.add(item);
	}
	
	public void eliminarItem(int i) {
		if(i>=0 && i < items.size()){
			items.remove(i);
		}
		
	}
	
	//Cálculos financieros no están en variables, pues estos valores van cambiando a medida que se hacen los calculos 
	
	//Suma de subtotales netos (sin impuesto) hace referencia a todos los items
	public double getSubtotal(){
		double subtotal = 0; 
		for(DetalleVenta i : items) {
			subtotal += i.getSubtotal();
		}
		return subtotal; 
	}
	
	//Suma del impuesto de cada ítem (solo los que aplican IVA)
	public double getIva() {
		double valorImpuesto =0; 
		for(DetalleVenta i : items) {
			valorImpuesto += i.getValorImpuesto();
		}
		return valorImpuesto; 
	}
	
	public double getTotal() {
		return getSubtotal() + getIva();
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public LocalDateTime getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public FormaPagoEnum getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPagoEnum formaPago) {
		this.formaPago = formaPago;
	}

	public String getMedioPago() {
		return medioPago;
	}

	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}

	public boolean isAnulada() {
		return anulada;
	}

	public void setAnulada(boolean anulada) {
		this.anulada = anulada;
	}

	public List<DetalleVenta> getItems() {
		return items;
	}

	public void setItems(List<DetalleVenta> items) {
		this.items = items;
	}

}
