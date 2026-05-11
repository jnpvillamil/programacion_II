package co.edu.uptc.dto;

public class ClienteResumenDTO {
    private String codigo;
    private String nombreCompleto;
    private String telefono;
    private String estado;

    public ClienteResumenDTO(String codigo, String nombreCompleto, String telefono, String estado) {
        this.codigo = codigo;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.estado = estado;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}