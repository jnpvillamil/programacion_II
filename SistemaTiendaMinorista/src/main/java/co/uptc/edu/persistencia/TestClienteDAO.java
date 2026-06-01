package co.uptc.edu.persistencia;

import co.uptc.edu.modelo.Cliente;

public class TestClienteDAO {

    public static void main(String[] args) {

        Cliente cliente = new Cliente(
                "C001",
                "Sebastian",
                "CC",
                "123456",
                "Sogamoso",
                "3200000000",
                "Minorista"
        );

        ClienteDAO dao = new ClienteDAO();

        if(dao.guardarCliente(cliente)){

            System.out.println("Cliente guardado");

        }else{

            System.out.println("Error");
        }
    }
}