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
import libreria.entidades.Editorial;
import libreria.persistencia.EditorialDAO;

/**
 *
 * @author Admin
 */
public class EditorialService {
    protected EditorialDAO DAO = new EditorialDAO();
    
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
    
    public Editorial crearEditorial() {
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        Editorial editorial = null;
        List<String> listaAux = new ArrayList();

        try {
            editorial = new Editorial();
            boolean salir = false;
            System.out.println("Ingrese el nombre de la editorial");
            String nombreEditorial = "";

            while (!salir) {
                nombreEditorial = leer.next();
                salir = atraparStringNumerico(nombreEditorial);
            }

//            listaAux.add(nombreEditorial);
//            if (listaAux.contains(nombreEditorial)) {
//                System.out.println("Esa editorial ya se encuentra registrada"
//                        + "\nEl proceso Crear Editorial volvera a ejecutarse");
//                listaAux.remove(nombreEditorial);
//            } else {
//                editorial.setNombre(nombreEditorial);
//                editorial.setAlta(true);
//                DAO.agregarEditorial(editorial);
//            }

            editorial.setNombre(nombreEditorial);
            editorial.setAlta(true);
            DAO.agregarEditorial(editorial);
            return editorial;
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
            return null;
        }
    }

    public void eliminarEditorial(){
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        Editorial editorial = null;
        String entrada = null;
        int id = -1;
        
        System.out.println("Ingrese el nombre de la editorial");
        String nombre = leer.next();
        System.out.println(DAO.buscarPorNombreLista(nombre));
        
        try {
            System.out.println("Ingrese el ID de la editorial que desee eliminar");
            entrada = leer.next();
            id = Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("Error: No ingreso un numero");
        }
        
        editorial = DAO.buscarPorID(id);
        
        if (editorial != null) {
            String respuesta;
            do {
                System.out.println("¿Confirma la eliminacion de: " + editorial.getNombre() + "? (S/N)");
                respuesta = leer.next();
                if (respuesta.equalsIgnoreCase("S")) {
                    System.out.println(editorial);
                    DAO.eliminarEditorial(editorial);
                    System.out.println("La editorial ha sido eliminada correctamente");
                } else {
                    System.out.println("La editorial no ha sido eliminada");
                }
            } while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N")); 
        }
    }
    
    public void modificarNombreEditorial(){
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        Editorial editorial = null;
        String entrada = null;
        boolean salir = false;
        int id = -1;
        
        
        List<Editorial> lista = DAO.listar();
        System.out.println("Lista de editoriales:");
        for (Editorial aux : lista) {
            System.out.println("ID: " + aux.getId() + "\tNombre completo: " + aux.getNombre() + "\tAlta: " + aux.getAlta());
        }
        
        System.out.println("Ingrese el ID");
        try {
            entrada = leer.next();
            id = Integer.parseInt(entrada);
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        
        editorial = DAO.buscarPorID(id);
        
        System.out.println("Ingrese el nuevo nombre");
        while(!salir){
            String nombreNuevo = leer.next();
            salir = atraparStringNumerico(nombreNuevo);
            editorial.setNombre(nombreNuevo);
        }
        DAO.modificarEditorial(editorial);
    }
}
