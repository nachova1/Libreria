/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.PersistenceException;
import libreria.entidades.Autor;
import libreria.persistencia.AutorDAO;

/**
 *
 * @author Admin
 */
public class AutorService {
    protected AutorDAO DAO = new AutorDAO();

    public static boolean atraparStringNumerico(String string) {
        try {
            // Verificar si el string contiene números
            Pattern pattern = Pattern.compile("[^a-zA-Z ]");
            Matcher matcher = pattern.matcher(string);
            if (matcher.find()) {
                throw new IllegalArgumentException("El string contiene caracteres especiales o numeros");
            }
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Ingresar un nuevo nombre");
            return false;
        }
    }
    
    public Autor crearAutor() {
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        Autor autor = null;
        List<String> listaAux = new ArrayList();

        try {
            autor = new Autor();
            boolean salir = false;
            System.out.println("Ingrese el nombre completo del autor");
            String nombreAutor = "";

            while (!salir) {
                nombreAutor = leer.next();
                salir = atraparStringNumerico(nombreAutor);
            }

//            listaAux.add(nombreAutor); //Esto lo habia hecho para que no se rompa el programa al momento de crear un autor que ya esta registrado 
//            if (listaAux.contains(nombreAutor)) {
//                System.out.println("Ese autor ya se encuentra registrado"
//                        + "\nEl proceso Crear Autor volvera a ejecutarse");
//                listaAux.remove(nombreAutor);
//            } else {
//                autor.setNombre(nombreAutor);
//                autor.setAlta(true);
//                DAO.agregarAutor(autor);
//            }
            
            autor.setNombre(nombreAutor);
            autor.setAlta(true);
            DAO.agregarAutor(autor);
            return autor;
        } catch (Exception e) {
            System.out.println("Error:");
            e.printStackTrace();
            return null;
        }
    }
    
//    public void insertarAutor(){
//        DAO.agregarAutor(crearAutor());
//    }
    
    public void modificarNombreAutor(){
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        String entrada = null;
        String nombre = null;
        Autor autor = null;
        int id = -1;
        
        List<Autor> lista = DAO.listar();
        System.out.println("Lista de Autores:");
        for (Autor aux : lista) {
            System.out.println("ID: " + aux.getId() + "\tNombre completo: " + aux.getNombre() + "\tAlta: " + aux.getAlta());
        }
        
        System.out.println("Ingrese el ID");
        try {
            entrada = leer.next();
            id = Integer.parseInt(entrada);
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace(); 
            return;
        } finally {
            if (id <= 0) {
                return;
            }
        }
        
        autor = DAO.buscarPorID(id);
        boolean salir = false;
        
        System.out.println("Ingrese el nuevo nombre");
        while(!salir){
            nombre = leer.next();
            salir = atraparStringNumerico(nombre);
            autor.setNombre(nombre);
        }            
        DAO.modificarAutor(autor);
    }
    
    public void bajaAutor(){
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        String entrada;
        int id = -1;
        try {
            System.out.println("Ingrese el ID del autor");
            entrada = leer.next();
            id = Integer.parseInt(entrada);
            Autor autor = DAO.buscarPorID(id);
            if (autor != null) {
                if (autor.getAlta()) {
                    autor.setAlta(false);
                    DAO.modificarAutor(autor);
                }
            } else {
                System.out.println("El autor ya tiene ese estado");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: No ingreso un numero");
            e.printStackTrace();
        }  
    }
    
    public void eliminarAutor(){
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        Autor autor = null;
        String entrada;
        int id = -1;
        
        System.out.println("Ingrese el nombre del autor");
        String nombre = leer.next();
        System.out.println(DAO.buscarPorNombreLista(nombre));
        
        try {
            System.out.println("Ingrese el ID del autor que desee eliminar");
            entrada = leer.next();
            id = Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("Error: No ingreso un numero");
        }
        
        autor = DAO.buscarPorID(id);
        
        if (autor != null) {
            String respuesta;
            do {
                System.out.println("¿Confrima la eliminacion de: " + autor.getNombre() + "? (S/N)");
                respuesta = leer.next();
                if (respuesta.equalsIgnoreCase("S")) {
                    System.out.println(autor + " ha sido eliminado");
                    DAO.eliminarAutor(autor);
                } else {
                    System.out.println("El autor no ha sido eliminado");
                }
            } while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));
        }
        

    }
    
    public void mostrarTabla(){
        //System.out.println("| ID\t|\tNombre\t|Activo\t|");
        System.out.println("| ID\t|\tNombre\t|\tActivo\t|");
        String activo;
        List<Autor> lista = DAO.listar();
        
        if (lista != null) {
            for (Autor aux : lista) {
                if (aux.getAlta()) {
                    activo = "Si";
                } else {
                    activo = "No";
                }
                System.out.println("| " + aux.getId() + "\t|" + aux.getNombre() + "\t|" + activo + "\t|");
            }
        }
    }
    
    public void mostrarActivos(){
        System.out.println("Autores activos");
        for (Autor aux : DAO.listar()) {
            if (aux.getAlta()) {
               System.out.println("ID: " + aux.getId() + "\tNombre: " + aux.getNombre()); 
            }
        }
    }
    
    public void mostrarPorNombre(){
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        Autor autor = null;
        System.out.println("Ingrese el nombre del autor que desee buscar");
        String nombre = leer.next();
        System.out.println(DAO.buscarPorNombre(nombre));
//        for (Autor aux : DAO.buscarPorNombreLista(nombre)) { //Si quiero que me traiga varios autores descomento esto
//            System.out.println(aux.toString()); 
//        }
    }
    
}
