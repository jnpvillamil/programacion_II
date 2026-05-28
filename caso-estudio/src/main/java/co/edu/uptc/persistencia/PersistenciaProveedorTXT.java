package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.IPersistenciaProveedor;
import co.edu.uptc.modelo.Proveedor;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class PersistenciaProveedorTXT implements IPersistenciaProveedor {

    private static final String ARCHIVO = "proveedores.txt";
    
    @Override
    public boolean guardar(Proveedor objeto) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            String linea = objeto.getCodigoProveedor() + "|" 
                         + objeto.getNombre() + "|" 
                         + objeto.getIdentificacion() + "|" 
                         + objeto.getDireccion() + "|" 
                         + objeto.getTelefono() + "|" 
                         + objeto.getCorreoElectronico() + "|" 
                         + objeto.isActivo();
            writer.write(linea);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar proveedor en archivo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 7) {
                    Proveedor p = new Proveedor();
                    p.setCodigoProveedor(partes[0]);
                    p.setNombre(partes[1]);
                    p.setIdentificacion(partes[2]);
                    p.setDireccion(partes[3]);
                    p.setTelefono(partes[4]);
                    p.setCorreoElectronico(partes[5]);
                    p.setActivo(Boolean.parseBoolean(partes[6]));
                    
                    if (p.isActivo()) {
                        lista.add(p);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al listar proveedores desde archivo: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(Proveedor objeto) {
        List<Proveedor> todos = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 7) {
                    Proveedor p = new Proveedor();
                    p.setCodigoProveedor(partes[0]);
                    p.setNombre(partes[1]);
                    p.setIdentificacion(partes[2]);
                    p.setDireccion(partes[3]);
                    p.setTelefono(partes[4]);
                    p.setCorreoElectronico(partes[5]);
                    p.setActivo(Boolean.parseBoolean(partes[6]));
                    todos.add(p);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
            return false;
        }
        
        boolean encontrado = false;
        for (Proveedor p : todos) {
            if (p.getCodigoProveedor().equals(objeto.getCodigoProveedor())) {
                p.setNombre(objeto.getNombre());
                p.setIdentificacion(objeto.getIdentificacion());
                p.setDireccion(objeto.getDireccion());
                p.setTelefono(objeto.getTelefono());
                p.setCorreoElectronico(objeto.getCorreoElectronico());
                p.setActivo(objeto.isActivo());
                encontrado = true;
                break;
            }
        }
        
        if (!encontrado) return false;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Proveedor p : todos) {
                String linea = p.getCodigoProveedor() + "|" 
                             + p.getNombre() + "|" 
                             + p.getIdentificacion() + "|" 
                             + p.getDireccion() + "|" 
                             + p.getTelefono() + "|" 
                             + p.getCorreoElectronico() + "|" 
                             + p.isActivo();
                writer.write(linea);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error al actualizar archivo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(String id) {
        Proveedor p = buscarPorId(id);
        if (p != null) {
            p.setActivo(false);
            return actualizar(p);
        }
        return false;
    }

    @Override
    public Proveedor buscarPorId(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 7 && partes[0].equals(id)) {
                    Proveedor p = new Proveedor();
                    p.setCodigoProveedor(partes[0]);
                    p.setNombre(partes[1]);
                    p.setIdentificacion(partes[2]);
                    p.setDireccion(partes[3]);
                    p.setTelefono(partes[4]);
                    p.setCorreoElectronico(partes[5]);
                    p.setActivo(Boolean.parseBoolean(partes[6]));
                    return p;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al buscar proveedor en archivo: " + e.getMessage());
        }
        return null;
    }
}
