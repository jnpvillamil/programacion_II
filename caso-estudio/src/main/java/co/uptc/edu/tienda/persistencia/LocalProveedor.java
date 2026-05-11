package co.uptc.edu.tienda.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;


import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.tienda.enums.EstadoEnum;
import co.uptc.edu.tienda.interfaces.IGestionProveedor;
import co.uptc.edu.tienda.modelo.Proveedor;

public class LocalProveedor implements IGestionProveedor {

	
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private final String RUTA = "proveedores.json";
	
	
	
	public LocalProveedor() {
		super();
		// TODO Auto-generated constructor stub
//		this.listaProveedores = new ArrayList<>();
	}

	@Override
	public void guardar(List<Proveedor> proveedores) {
		try(FileWriter writer = new FileWriter(RUTA)){
			gson.toJson(proveedores,writer);
			System.out.println("Proveedores");
		}catch(IOException e) {
			System.out.println("Error al guardar en "+RUTA+":"+e.getMessage());
			
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizar(Proveedor proveedor) {
		// TODO Auto-generated method stub
		List<Proveedor> lista = leerProveedores();
	    
	    // 2. Buscamos el proveedor por su ID y lo reemplazamos
	    for (int i = 0; i < lista.size(); i++) {
	        if (lista.get(i).getCodigoProveedor() == proveedor.getCodigoProveedor()) {
	            lista.set(i, proveedor); // Reemplaza el objeto viejo por el nuevo
	            break; // Ya lo encontramos, salimos del bucle
	        }
	    }
	    
	    guardar(lista);

	}

	@Override
	public void eliminar(int codigoProveedor) {
		// TODO Auto-generated method stub
		List<Proveedor> lista = leerProveedores();
	    
	    // 2. Buscamos la posición del proveedor a borrar
	    for (int i = 0; i < lista.size(); i++) {
	    	Proveedor p = lista.get(i);
	        if (p.getCodigoProveedor() == codigoProveedor) {
	            p.setEstado(EstadoEnum.INACTIVO); // Elimina el elemento en esa posición
	            break; 
	        }
	    }
	    
	    // 3. SOBRESCRIBIMOS el archivo con la lista ahora más corta
	    guardar(lista);
	}

	@Override
	public List<Proveedor> leerProveedores() {
		// TODO Auto-generated method stub
		File archivo = new File(RUTA);
	    
	    if (!archivo.exists()) {
	        return new ArrayList<Proveedor>();
	    }

	    try {
	        FileReader reader = new FileReader(archivo);
	        Type tipoLista = new TypeToken<List<Proveedor>>(){}.getType();
	        List<Proveedor> lista = gson.fromJson(reader, tipoLista);
	        reader.close(); 

	        if (lista == null) {
	            return new ArrayList<Proveedor>();
	        }
	        return lista;
	        
	    } catch (IOException e) {
	        System.out.println("Error al leer: " + e.getMessage());
	        return new ArrayList<Proveedor>();
	    }
	}
		
		
	



	@Override
    public Proveedor buscar(int codigoProveedor) {
        List<Proveedor> lista = leerProveedores();
        if (lista != null) {
            for (Proveedor p : lista) {
                if (p.getCodigoProveedor() == codigoProveedor) return p;
            }
        }
        return null;
    }

	@Override
	public void cambiarEstado(int codigoProveedor, EstadoEnum nuevoEstado) {
		// TODO Auto-generated method stub
		List<Proveedor> lista = leerProveedores();
	    for (Proveedor p : lista) {
	        if (p.getCodigoProveedor() == codigoProveedor) {
	            p.setEstado(nuevoEstado);
	            break;
	        }
	    }
	    guardar(lista);
	    
	    
		
	}


}
