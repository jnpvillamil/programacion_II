package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.GestionCliente;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.enums.TipoDocumentoEnum;
import java.util.*;

public class LocalCliente implements GestionCliente {
    
    private Map<String, Cliente> clientes;
    
    public LocalCliente() {
        clientes = new HashMap<>();
        
        
        Cliente c1 = new Cliente("C001", "Juan Perez", TipoDocumentoEnum.CC, "12345678", 
                                 "Calle 1 #2-3", "3001234567", "Minorista");
        Cliente c2 = new Cliente("C002", "Maria Gomez", TipoDocumentoEnum.CC, "87654321", 
                                 "Carrera 4 #5-6", "3007654321", "Mayorista");
        Cliente c3 = new Cliente("C003", "Tienda XYZ", TipoDocumentoEnum.NIT, "900123456", 
                                 "Avenida Siempre Viva", "6011234567", "Mayorista");
        
        clientes.put(c1.getCodigo(), c1);
        clientes.put(c2.getCodigo(), c2);
        clientes.put(c3.getCodigo(), c3);
    }
    
   
    @Override
    public void crear(Cliente cliente) {
        clientes.put(cliente.getCodigo(), cliente);
    }
    
    @Override
    public void actualizar(Cliente cliente) {
        if(clientes.containsKey(cliente.getCodigo())) {
            clientes.put(cliente.getCodigo(), cliente);
        }
    }
    
    @Override
    public void eliminar(String codigo) {
        Cliente c = clientes.get(codigo);
        if(c != null) {
            c.setActivo(false);
        }
    }
    
    @Override
    public Cliente buscar(String codigo) {
        Cliente c = clientes.get(codigo);
        
        return c;  
    }
    
    @Override
    public List<Cliente> listar() {
        return new ArrayList<>(clientes.values());
    }
    
    @Override
    public List<Cliente> listarActivos() {
        List<Cliente> activos = new ArrayList<>();
        for(Cliente c : clientes.values()) {
            if(c.isActivo()) {
                activos.add(c);
            }
        }
        return activos;
    }
    
    @Override
    public boolean existe(String codigo) {
        Cliente c = clientes.get(codigo);
        return c != null ;
    }
}