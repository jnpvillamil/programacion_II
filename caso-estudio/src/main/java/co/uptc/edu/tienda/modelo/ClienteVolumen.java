package co.uptc.edu.tienda.modelo;

public class ClienteVolumen {
    private int codigo;
    private String nombre;
    private double totalComprado;

    public ClienteVolumen(int codigo, String nombre, double totalComprado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.totalComprado = totalComprado;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getTotalComprado() { return totalComprado; }
    public void setTotalComprado(double totalComprado) { this.totalComprado = totalComprado; }
}