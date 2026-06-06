package co.edu.uptc.modelo;

public class Gato {
	
	private String Color;
	private String Raza;
	private String Ojos;
	private boolean activo;
	
	public Gato(String Color, String Raza, String Ojos) {
		this.Color = Color;
		this.Ojos = Ojos;
		this.Raza = Raza;
		this.activo = true;
		
	}
	
	//Getters and Setters
	
	public String getOjos() { return Ojos; }         
    public void setOjos(String ojos) { this.Ojos = ojos; } 
	
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    
    @Override
    public String toString() {
        return "Gato{" +
                "color='" + Color + '\'' +
                ", raza='" + Raza + '\'' +
                ", ojos='" + Ojos + '\'' +
                '}';
    }
    
    
}




