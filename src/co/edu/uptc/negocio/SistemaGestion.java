package co.edu.uptc.negocio;

// Importamos todas las clases de persistencia
import co.edu.uptc.persistencia.*; 

public class SistemaGestion {
    
    // 1. Capa de Persistencia (Simulación de BD)
    private PersistenciaProducto persistenciaProducto;
    private PersistenciaProveedor persistenciaProveedor;
    private PersistenciaCompra persistenciaCompra;
    
    // 2. Capa de Gestores (Lógica de Negocio)
    private GestorProducto gestorProducto;
    private GestorCompra gestorCompra;

    public SistemaGestion() {
        // Inicializamos la base de datos en memoria
        this.persistenciaProducto = new PersistenciaProducto();
        this.persistenciaProveedor = new PersistenciaProveedor();
        this.persistenciaCompra = new PersistenciaCompra();
        
        // Inicializamos los gestores conectándolos con su respectiva persistencia
        this.gestorProducto = new GestorProducto(persistenciaProducto);
        this.gestorCompra = new GestorCompra(persistenciaCompra);
        
        cargarDatosDePrueba(); // Solo para que tengas datos al abrir el sistema
    }

    private void cargarDatosDePrueba() {
        // Así no tendrás que crear todo desde cero cada vez que ejecutes para probar
        persistenciaProducto.crear(new Producto("P01", "Arroz Diana 1kg", "Abarrotes", 3000, 4000, 50, 10));
        persistenciaProducto.crear(new Producto("P02", "Leche Alquería", "Lácteos", 4000, 4500, 20, 5));
        persistenciaProveedor.crear(new Proveedor("PR01", "Distribuidora Mayorista SAS", "900.123.456-7", "ventas@mayorista.com"));
    }

    // Método para el Login
    public Persona iniciarSesion(String usuario, String contrasena) {
        // Usamos trim() para eliminar espacios en blanco accidentales al inicio o al final
        String userLimpio = usuario.trim();
        String passLimpio = contrasena.trim();

        // AQUÍ ESTÁN LAS CREDENCIALES: "admin" y "123"
        if(userLimpio.equals("admin") && passLimpio.equals("123")) {
            return new Administrador("A01", "Super Admin", userLimpio, passLimpio);
        }
        
        // Si quieres agregar un usuario para Cajero, lo harías así:
        if(userLimpio.equals("cajero") && passLimpio.equals("123")) {
            return new Cajero("C01", "Cajero 1", userLimpio, passLimpio, 1);
        }

        return null; // Retorna null si no coincide nada (Credenciales incorrectas)
    }

    // --- GETTERS PARA QUE LA GUI PUEDA ACCEDER ---
    
    public GestorProducto getGestorProducto() {
        return gestorProducto;
    }

    public GestorCompra getGestorCompra() {
        return gestorCompra;
    }

    public PersistenciaProducto getPersistenciaProducto() {
        return persistenciaProducto;
    }

    public PersistenciaProveedor getPersistenciaProveedor() {
        return persistenciaProveedor;
    }
}