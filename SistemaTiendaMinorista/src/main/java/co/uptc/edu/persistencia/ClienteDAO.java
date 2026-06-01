package co.uptc.edu.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.modelo.Cliente;
import co.uptc.edu.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.modelo.Cliente;

public class ClienteDAO {

    // ================= GUARDAR =================

    public boolean guardarCliente(Cliente cliente){

        String sql =
                "INSERT INTO clientes(" +
                "codigo," +
                "nombre," +
                "tipo_identificacion," +
                "numero_identificacion," +
                "direccion," +
                "telefono," +
                "tipo_cliente," +
                "activo" +
                ") VALUES(?,?,?,?,?,?,?,?)";

        try(

            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)

        ){

            ps.setString(1, cliente.getCodigo());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getTipoIdentificacion());
            ps.setString(4, cliente.getNumeroIdentificacion());
            ps.setString(5, cliente.getDireccion());
            ps.setString(6, cliente.getTelefono());
            ps.setString(7, cliente.getTipoCliente());
            ps.setBoolean(8, cliente.isActivo());

            return ps.executeUpdate() > 0;

        }catch(Exception e){

            e.printStackTrace();
            return false;
        }
    }

    // ================= LISTAR =================

    public List<Cliente> obtenerClientes(){

        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT * FROM clientes";

        try(

            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()

        ){

            while(rs.next()){

                Cliente cliente = new Cliente(

                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("tipo_identificacion"),
                        rs.getString("numero_identificacion"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("tipo_cliente")
                );

                cliente.setActivo(
                        rs.getBoolean("activo")
                );

                clientes.add(cliente);
            }

        }catch(Exception e){

            e.printStackTrace();
        }

        return clientes;
    }
 
    public boolean modificarCliente(Cliente cliente){

        String sql =
                "UPDATE clientes SET " +
                "nombre=?," +
                "tipo_identificacion=?," +
                "numero_identificacion=?," +
                "direccion=?," +
                "telefono=?," +
                "tipo_cliente=?," +
                "activo=? " +
                "WHERE codigo=?";

        try(

            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)

        ){

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTipoIdentificacion());
            ps.setString(3, cliente.getNumeroIdentificacion());
            ps.setString(4, cliente.getDireccion());
            ps.setString(5, cliente.getTelefono());
            ps.setString(6, cliente.getTipoCliente());
            ps.setBoolean(7, cliente.isActivo());

            ps.setString(8, cliente.getCodigo());

            return ps.executeUpdate() > 0;

        }catch(Exception e){

            e.printStackTrace();
            return false;
        }
    }
    public boolean inactivarCliente(String codigo){

        String sql =
                "UPDATE clientes " +
                "SET activo = false " +
                "WHERE codigo = ?";

        try(

            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)

        ){

            ps.setString(1, codigo);

            return ps.executeUpdate() > 0;

        }catch(Exception e){

            e.printStackTrace();
            return false;
        }
    }
}