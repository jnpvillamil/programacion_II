package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.GestionProveedor;
import co.edu.uptc.modelo.Proveedor;
import java.util.*;

public class LocalProveedor implements GestionProveedor {
    
    private Map<String, Proveedor> proveedores;
    
    public LocalProveedor() {
        proveedores = new HashMap<>();
        
      
        Proveedor prov1 = new Proveedor("PROV001", "Distribuidora La Campiña", "900123456-1",
                                        "Calle 10 #20-30", "3101234567", "ventas@lacampina.com");
        Proveedor prov2 = new Proveedor("PROV002", "Alimentos SAS", "800987654-2",
                                        "Carrera 15 #45-60", "3209876543", "pedidos@alimentossas.com");
        Proveedor prov3 = new Proveedor("PROV003", "Papelería Central", "700456789-3",
                                        "Avenida 5 #12-08", "6012345678", "compras@papeleriacentral.com");
        
        proveedores.put(prov1.getCodigo(), prov1);
        proveedores.put(prov2.getCodigo(), prov2);
        proveedores.put(prov3.getCodigo(), prov3);
    }
    
    @Override
    public void crear(Proveedor proveedor) {
        proveedores.put(proveedor.getCodigo(), proveedor);
    }
    
    @Override
    public void actualizar(Proveedor proveedor) {
        if(proveedores.containsKey(proveedor.getCodigo())) {
            proveedores.put(proveedor.getCodigo(), proveedor);
        }
    }
    
    @Override
    public void eliminar(String codigo) {
        Proveedor p = proveedores.get(codigo);
        if(p != null) {
            p.setActivo(false);  
        }
    }
    
    @Override
    public Proveedor buscar(String codigo) {
        Proveedor p = proveedores.get(codigo);
        return (p != null && p.isActivo()) ? p : null;
    }
    
    @Override
    public Proveedor buscarPorNit(String nit) {
        for(Proveedor p : proveedores.values()) {
            if(p.isActivo() && p.getNit().equals(nit)) {
                return p;
            }
        }
        return null;
    }
    
    @Override
    public List<Proveedor> listar() {
        return new ArrayList<>(proveedores.values());
    }
    
    @Override
    public List<Proveedor> listarActivos() {
        List<Proveedor> activos = new ArrayList<>();
        for(Proveedor p : proveedores.values()) {
            if(p.isActivo()) {
                activos.add(p);
            }
        }
        return activos;
    }
    
    @Override
    public boolean existe(String codigo) {
        Proveedor p = proveedores.get(codigo);
        return p != null && p.isActivo();
    }
    
    @Override
    public boolean existeNit(String nit) {
        for(Proveedor p : proveedores.values()) {
            if(p.isActivo() && p.getNit().equals(nit)) {
                return true;
            }
        }
        return false;
    }
}