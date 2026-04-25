package co.edu.uptc.negocio;

public class ventaDto {

    private String numeroFactura;
    private String fechaHora;
    private String cliente;
    private String formaPago;
    private boolean aplicarIva;
    private double total;

    public String getNumeroFactura() {
    	return numeroFactura; 
    	}
    public void setNumeroFactura(String numeroFactura) {
    	this.numeroFactura = numeroFactura;
    	}

    public String getFechaHora() {
    	return fechaHora; 
    	}
    public void setFechaHora(String fechaHora) {
    	this.fechaHora = fechaHora; 
    	}

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) {
    	this.cliente = cliente; 
    	}

    public String getFormaPago() { 
    	return formaPago; 
    	}
    
    public void setFormaPago(String formaPago) {
    	this.formaPago = formaPago; 
    	}

    public boolean isAplicarIva() {
    	return aplicarIva;
    	}
    public void setAplicarIva(boolean aplicarIva) { 
    	this.aplicarIva = aplicarIva;
    	}

    public double getTotal() { 
    	return total;
    	}
    
    public void setTotal(double total) {
    	this.total = total;
    	}

    @Override
    public String toString() {
        return "ventaDto [numeroFactura=" + numeroFactura + ", fechaHora=" + fechaHora +", cliente=" + cliente + ", formaPago=" + formaPago +", aplicarIva=" + aplicarIva + ", total=" + total + "]";
    }
}