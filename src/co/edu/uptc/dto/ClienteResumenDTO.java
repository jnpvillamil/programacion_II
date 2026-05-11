package co.edu.uptc.dto;

public class ClienteResumenDTO {
    private String codigo;
    private String nombreCompleto;
    private String telefono;

    public ClienteResumenDTO(String codigo, String nombreCompleto, String telefono) {
        this.codigo = codigo;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}