package co.edu.uptc.vo;

public class PersonaVo {
    private int idPersona;
    private String nombrePersona;
    private int edadPersona;
    private String profesionPersona;
    private int telefonoPersona;

    // Getters y Setters
    public int getIdPersona() { return idPersona; }
    public void setIdPersona(int idPersona) { this.idPersona = idPersona; }

    public String getNombrePersona() { return nombrePersona; }
    public void setNombrePersona(String nombrePersona) { this.nombrePersona = nombrePersona; }

    public int getEdadPersona() { return edadPersona; }
    public void setEdadPersona(int edadPersona) { this.edadPersona = edadPersona; }

    public String getProfesionPersona() { return profesionPersona; }
    public void setProfesionPersona(String profesionPersona) { this.profesionPersona = profesionPersona; }

    public int getTelefonoPersona() { return telefonoPersona; }
    public void setTelefonoPersona(int telefonoPersona) { this.telefonoPersona = telefonoPersona; }
}