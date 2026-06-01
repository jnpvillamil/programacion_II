package co.uptc.edu.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.modelo.Proveedor;

public class ProveedorDAO {

    // ================= GUARDAR =================

    public boolean guardarProveedor(Proveedor proveedor){

        String sql =
                "INSERT INTO proveedores(" +
                "codigo," +
                "razon_social," +
                "nit," +
                "direccion," +
                "telefono," +
                "correo," +
                "activo" +
                ") VALUES(?,?,?,?,?,?,?)";

        try(

            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)

        ){

            ps.setString(1, proveedor.getCodigo());
            ps.setString(2, proveedor.getRazonSocial());
            ps.setString(3, proveedor.getNit());
            ps.setString(4, proveedor.getDireccion());
            ps.setString(5, proveedor.getTelefono());
            ps.setString(6, proveedor.getCorreo());
            ps.setBoolean(7, proveedor.isActivo());

            return ps.executeUpdate() > 0;

        }catch(Exception e){

            e.printStackTrace();
            return false;
        }
    }

    // ================= LISTAR =================

    public List<Proveedor> obtenerProveedores(){

        List<Proveedor> proveedores = new ArrayList<>();

        String sql = "SELECT * FROM proveedores";

        try(

            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()

        ){

            while(rs.next()){

                Proveedor proveedor = new Proveedor(

                        rs.getString("codigo"),
                        rs.getString("razon_social"),
                        rs.getString("nit"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correo")
                );

                proveedor.setActivo(
                        rs.getBoolean("activo")
                );

                proveedores.add(proveedor);
            }

        }catch(Exception e){

            e.printStackTrace();
        }

        return proveedores;
    }

    // ================= MODIFICAR =================

    public boolean modificarProveedor(Proveedor proveedor){

        String sql =
                "UPDATE proveedores SET " +
                "razon_social=?," +
                "nit=?," +
                "direccion=?," +
                "telefono=?," +
                "correo=?," +
                "activo=? " +
                "WHERE codigo=?";

        try(

            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)

        ){

            ps.setString(1, proveedor.getRazonSocial());
            ps.setString(2, proveedor.getNit());
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getTelefono());
            ps.setString(5, proveedor.getCorreo());
            ps.setBoolean(6, proveedor.isActivo());

            ps.setString(7, proveedor.getCodigo());

            return ps.executeUpdate() > 0;

        }catch(Exception e){

            e.printStackTrace();
            return false;
        }
    }

    // ================= INACTIVAR =================

    public boolean inactivarProveedor(String codigo){

        String sql =
                "UPDATE proveedores " +
                "SET activo = false " +
                "WHERE codigo=?";

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

    // ================= ACTIVAR =================

    public boolean activarProveedor(String codigo){

        String sql =
                "UPDATE proveedores " +
                "SET activo = true " +
                "WHERE codigo=?";

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