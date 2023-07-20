/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria.servicios;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;
import libreria.entidades.Libro;
import libreria.persistencia.AutorDAO;
import libreria.persistencia.EditorialDAO;
import libreria.persistencia.LibroDAO;

/**
 *
 * @author Admin
 */
public class LibroService {
    protected LibroDAO DAO = new LibroDAO();
    
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
            System.out.println("Ingresar un nuevo titulo");
            return false;
        }

    }
    
    public void crearLibro() {
        Autor autor = null;
        AutorDAO autorDAO = new AutorDAO();
        AutorService autorS = new AutorService();
        EditorialService editorialS = new EditorialService();
        EditorialDAO editorialDAO = new EditorialDAO();
        Editorial editorial = null;

        try {
            Libro libro = new Libro();
            boolean salir = false;
            Scanner leer = new Scanner(System.in).useDelimiter("\n");

            System.out.println("Ingrese el titulo del libro");
            while (!salir) {
                String titulo = leer.next();
                salir = atraparStringNumerico(titulo);
                libro.setTitulo(titulo);
            }
            System.out.println("Ingrese el año de publicacion");
            Integer anio = leer.nextInt();
            libro.setAnio(anio);
            
            System.out.println("Ingrese la cantidad de ejemplares");
            Integer ejemplares = leer.nextInt();
            libro.setEjemplares(ejemplares);
            
            System.out.println("¿Ha prestado ejemplares? S/N");
            if (leer.next().equalsIgnoreCase("S")) {
                System.out.println("Ingrese la cantidad de ejemplares prestados");
                Integer ejemplaresPrestados = leer.nextInt();
                libro.setEjemplaresPrestados(ejemplaresPrestados);
            } else {
                libro.setEjemplaresPrestados(0);
            }
            
            System.out.println("¿Todavia quedan ejemplares a prestar? S/N");
            if (leer.next().equalsIgnoreCase("S")) {
                System.out.println("Ingrese la cantidad de ejemplares restantes");
                Integer ejemplaresRestantes = leer.nextInt();
                libro.setEjemplaresRestantes(ejemplaresRestantes);
            } else {
                libro.setEjemplaresRestantes(0);
            }
            libro.setAlta(true);

            System.out.println("¿Desea crear un nuevo autor? S/N");
            if (leer.next().equalsIgnoreCase("N")) {
                List<Autor> listaA = autorDAO.listar();
                System.out.println("Lista de autores:");
                for (Autor aux : listaA) {
                    System.out.println(aux.toString());
                }

                System.out.println("Ingrese el ID del autor que desee agregar");
                try {
                    //entrada = leer.next();
                    int id = leer.nextInt();
                    autor = autorDAO.buscarPorID(id);
                    libro.setAutor(autor);
                    System.out.println("Se ha cargado correctamente el autor");
                } catch (NumberFormatException e) {
                    System.out.println("Error: ID mal ingresado");
                }
            } else {
                autor = autorS.crearAutor();
                libro.setAutor(autor);
            }
            
            System.out.println("¿Desea crear una nueva editorial? S/N");
            if (leer.next().equalsIgnoreCase("N")) {
                List<Editorial> listaE = editorialDAO.listar();
                System.out.println("Lista de editoriales:");
                for (Editorial aux : listaE) {
                    System.out.println(aux.toString());
                }

                System.out.println("Ingrese el ID de la editorial que desee agregar");
                try {
                    int id1 = leer.nextInt();
                    editorial = editorialDAO.buscarPorID(id1);
                    libro.setEditorial(editorial);
                    System.out.println("Se ha cargado correctamente la editorial");
                } catch (NumberFormatException e) {
                    System.out.println("Error: ID mal ingresado");
                }
            } else {
                editorial = editorialS.crearEditorial();
                libro.setEditorial(editorial);
            }
            
            DAO.agregarLibro(libro);
            
        } catch (NumberFormatException e) {
            System.out.println("Error: No ha ingresado un numero");
        }
    }
   
    public void modificarTituloLibro(){
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        Libro libro = null;
        String entrada = null;
        int id = -1;
        boolean salir = false;
        
        List<Libro> lista = DAO.listar();
        System.out.println("Lista de Libros:");
        for (Libro aux : lista) {
            System.out.println(aux.toString());
        }
        
        System.out.println("Ingrese el ID");
        try {
            entrada = leer.next();
            id = Integer.parseInt(entrada);
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        } 
        
        libro = DAO.buscarPorID(id);
        
        System.out.println("Ingrese el nuevo titulo");
        while(!salir){
            String tituloNuevo = leer.next();
            salir = atraparStringNumerico(tituloNuevo);
            libro.setTitulo(tituloNuevo);
        }
        DAO.modificarLibro(libro);
    }
    
    public void eliminarLibro(){
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        Libro libro = null;
        String nombre = null;
        String entrada = null;
        int id = -1;
        
        System.out.println("Ingrese el titulo del libro");
        nombre = leer.next();
        System.out.println(DAO.buscarPorTitulo(nombre));
        
        try {
            System.out.println("Ingrese el ID del libro a eliminar");
            entrada = leer.next();
            id = Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("Error: No ingreso un numero");
        }
        
        DAO.buscarPorID(id);
        
        if (libro != null) {
            System.out.println("¿Confrima la eliminacion? S/N");
            if (leer.next().equalsIgnoreCase("S")) {
                System.out.println(libro);
                DAO.eliminarLibro(libro);
            }
        }
    }
    
    public void mostrarLista(){
        for (Libro aux : DAO.listar()) {
            System.out.println(aux.toString());
        }
    }
    
    public void mostrarPorISBN(){
        Libro libro = null;
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el ID del libro a buscar");
        int id = leer.nextInt();
        System.out.println(DAO.buscarPorID(id));    
    }
    
    public void mostrarPorTitulo(){
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el titulo del libro a buscar");
        String titulo = leer.next();
        
        for (Libro aux : DAO.buscarPorTitulo(titulo)) {
            System.out.println(aux.toString());
        }
    }
    
    public void mostrarPorNombreAutor(){
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el nombre del autor del libro a buscar");
        String nombreAutor = leer.next();
        System.out.println(DAO.buscarPorNombreAutor(nombreAutor));
    }
    
    public void mostrarPorNombreEditorial(){
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el nombre de la editorial del libro a buscar");
        String nombreEditorial = leer.next();
        for (Libro aux : DAO.buscarPorNombreEditorial(nombreEditorial)) {
            System.out.println(aux.toString());
        }
    }
}
