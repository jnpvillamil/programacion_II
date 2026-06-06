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
	
	
	public String getColor(){
		return Color;
	}
	
	public void setColor(String Color) {
		this.Color = Color;
	}
	
	

	public String getRaza() {return Raza;}
	public void setRaza(String Raza) {this.Raza = Raza;}
	

	//Getters and Setters
	
	public String getOjos() { return Ojos; }         
    public void setOjos(String ojos) { this.Ojos = ojos; } 
	
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    
    
    	
}




