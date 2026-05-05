package co.edu.uptc.negocio;

public class Administrador {
    private String user;
    private String pass;

    public Administrador(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public boolean login(String u, String p) { return user.equals(u) && pass.equals(p); }
}