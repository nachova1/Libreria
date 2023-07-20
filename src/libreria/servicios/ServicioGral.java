/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria.servicios;

import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class ServicioGral {
    private final AutorService autorServ = new AutorService();
    private final EditorialService editorialServ = new EditorialService();
    private final LibroService libroServ = new LibroService();
    private final PrestamoService prestamoServ = new PrestamoService();
    
    public void menu(){
        Scanner leer = new Scanner(System.in);
        boolean salir = false;
        
        do {            
            System.out.println("1) Autor (Crear, modificar, eliminar)"
                    + "\n2) Editorial (Crear, modificar, eliminar)"
                    + "\n3) Libro (Crear, modificar, eliminar)"
                    + "\n4) Prestamos (Crear, devolver, eliminar)"
                    + "\n5) Salir"
                    + "\n Ingrese una opcion");
            int opcion = leer.nextInt();
            
            switch (opcion) {
                case 1:
                    int opcion1;
                    do {                        
                        System.out.println("1) Crear autor"
                                + "\n2) Modificar nombre"
                                + "\n3) Eliminar autor"
                                + "\n4) Volver al menu"
                                + "\nIngrese una opcion");
                        opcion1 = leer.nextInt();
                        
                        switch (opcion1) {
                            case 1:
                                autorServ.crearAutor();
                                break;
                            case 2:
                                autorServ.modificarNombreAutor();
                                break;
                            case 3:
                                autorServ.eliminarAutor();
                                break;
                            case 4:
                                break;
                        }
                    } while (opcion1 != 4);
                    break;
                    
                case 2:
                    int opcion2;
                    do {
                        System.out.println("1) Crear editorial"
                                + "\n2) Modificar nombre"
                                + "\n3) Eliminar editorial"
                                + "\n4) Volver al menu"
                                + "\nIngrese una opcion");
                        opcion2 = leer.nextInt();
                        
                        switch (opcion2) {
                            case 1:
                                editorialServ.crearEditorial();
                                break;
                            case 2:
                                editorialServ.modificarNombreEditorial();
                                break;
                            case 3:
                                editorialServ.eliminarEditorial();
                                break;
                            case 4:
                                break;
                        }
                    } while (opcion2 != 4);
                    break;
                    
                case 3:
                    int opcion3;
                    do {
                        System.out.println("1) Crear libro"
                                + "\n2) Modificar titulo"
                                + "\n3) Eliminar libro"
                                + "\n4) Volver al menu"
                                + "\nIngresa una opcion");
                        opcion3 = leer.nextInt();
                        
                        switch (opcion3) {
                            case 1:
                                libroServ.crearLibro();
                                break;
                            case 2:
                                libroServ.modificarTituloLibro();
                                break;
                            case 3:
                                libroServ.eliminarLibro();
                                break;
                            case 4:
                                break;
                        }
                    } while (opcion3 != 4);
                    break;
                    
                case 4:
                    int opcion4;
                    do {                        
                        System.out.println("1) Crear prestamo"
                                + "\n2) Devolver prestamo"
                                + "\n3) Eliminar prestamo"
                                + "\n4) Volver al menu"
                                + "\nIngresa una opcion");
                        opcion4 = leer.nextInt();
                        
                        switch (opcion4) {
                            case 1:
                                prestamoServ.crearPrestamo();
                                break;
                            case 2:
                               prestamoServ.devolverLibro();
                               break;
                            case 3:
                                prestamoServ.eliminarPrestamo();
                                break;
                            case 4:
                                break;
                        }
                    } while (opcion4 != 4);
                    break;
                    
                case 5:
                    salir = true;
            }
        } while (!salir);
    }
}
