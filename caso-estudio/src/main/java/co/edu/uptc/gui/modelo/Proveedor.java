package co.edu.uptc.gui.modelo;

public class Proveedor {
    private String nit;
    private String razonSocial;
    private String telefono;
    private String direccion;
    private String productoSuministrado;

    public Proveedor() {}

    public Proveedor(String nit, String razonSocial, String telefono, String direccion, String productoSuministrado) {
        this.nit = nit;
        this.razonSocial = razonSocial;
        this.telefono = telefono;
        this.direccion = direccion;
        this.productoSuministrado = productoSuministrado;
    }

    // Getters y Setters
    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getProductoSuministrado() { return productoSuministrado; }
    public void setProductoSuministrado(String productoSuministrado) { this.productoSuministrado = productoSuministrado; }

	public void registrar() {
		// TODO Auto-generated method stub
		
	}

	public void modificar() {
		// TODO Auto-generated method stub
		
	}
}