package co.edu.uptc.gui.modelo;

public class Contador {
    private long id;
    private String nombre;
    private String tarjetaProfesional;
    private String telefono;

    public Contador() {
    }

    public Contador(long id, String nombre, String tarjetaProfesional, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.tarjetaProfesional = tarjetaProfesional;
        this.telefono = telefono;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTarjetaProfesional() { return tarjetaProfesional; }
    public void setTarjetaProfesional(String tarjetaProfesional) { this.tarjetaProfesional = tarjetaProfesional; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
