package gui;

public class SistemaApp {

    public static void main(String[] args) {

       
        System.out.println("=========================");
        System.out.println("Credenciales de acceso:");
        System.out.println("Usuario: admin");
        System.out.println("Contraseña: 1234");
        System.out.println("==========================");

        VentanaPrincipal vp = new VentanaPrincipal();
        vp.setVisible(true);
    }
}