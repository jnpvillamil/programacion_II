package co.uptc.edu.modelo;

public class Gato {

    private int id;
    private String nombre;
    private int edad;

    public Gato() {
    }

    public Gato(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public Gato(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}