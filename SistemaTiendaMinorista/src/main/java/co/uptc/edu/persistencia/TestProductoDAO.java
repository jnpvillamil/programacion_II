package co.uptc.edu.persistencia;

import co.uptc.edu.modelo.Producto;

public class TestProductoDAO {

    public static void main(String[] args) {

        Producto p = new Producto(
                "999",
                "Producto Prueba",
                "Viveres",
                1000,
                1500,
                10,
                2
        );

        ProductoDAO dao = new ProductoDAO();

        if(dao.guardarProducto(p)) {

            System.out.println("Producto guardado");

        } else {

            System.out.println("Error");
        }
    }
}