package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Proveedor;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaProveedor implements Repositorio<Proveedor> {
    private final String RUTA = "proveedores.txt";
    private final String SEP = ";";

    public PersistenciaProveedor() {
        try {
            File file = new File(RUTA);
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void guardar(Proveedor objeto) {
        List<Proveedor> lista = listar();
        lista.add(objeto);
        escribir(lista);
    }

    @Override
    public void eliminar(String id) {
        List<Proveedor> lista = listar();
        lista.removeIf(p -> p.getCodigoProveedor().equals(id));
        escribir(lista);
    }

    @Override
    public Proveedor buscarPorId(String id) {
        for (Proveedor p : listar()) {
            if (p.getCodigoProveedor().equals(id) || p.getIdentificacion().equals(id)) return p;
        }
        return null;
    }

    @Override
    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(SEP);
                lista.add(new Proveedor(d[0], d[1], d[2], d[3], d[4], d[5], Boolean.parseBoolean(d[6])));
            }
        } catch (IOException e) { e.printStackTrace(); }
        return lista;
    }

    @Override
    public void actualizar(Proveedor objeto) {
        List<Proveedor> lista = listar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigoProveedor().equals(objeto.getCodigoProveedor())) {
                lista.set(i, objeto);
                break;
            }
        }
        escribir(lista);
    }

    private void escribir(List<Proveedor> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA, false))) {
            for (Proveedor p : lista) {
                pw.println(p.getNombre() + SEP + p.getIdentificacion() + SEP + p.getDireccion() + SEP + 
                           p.getTelefono() + SEP + p.getCodigoProveedor() + SEP + p.getCorreoElectronico() + SEP + p.isActivo());
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}