/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria.servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import libreria.entidades.Cliente;
import libreria.entidades.Libro;
import libreria.entidades.Prestamo;
import libreria.persistencia.ClienteDAO;
import libreria.persistencia.LibroDAO;
import libreria.persistencia.PrestamoDAO;

/**
 *
 * @author Admin
 */
public class PrestamoService {
    
    protected PrestamoDAO DAO = new PrestamoDAO();
    
    public Prestamo crearPrestamo() {
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        Prestamo prestamo = new Prestamo();
        LibroDAO DAOl = new LibroDAO();
        Libro libro = new Libro();
        Cliente cliente = new Cliente();
        ClienteDAO DAOc = new ClienteDAO();
        ClienteService clienteS = new ClienteService();

        System.out.println("Ingrese el titulo del libro que desee");
        String tituloLibro = leer.next();
        System.out.println("Libros con ese titulo:");
        List<Libro> listal = DAOl.buscarPorTitulo(tituloLibro);
        for (Libro aux : listal) {
            System.out.println(aux.toString());
        }

        System.out.println("Ingrese el ID del libro que desee retirar");
        int id = leer.nextInt();
        libro = DAOl.buscarPorID(id);
        
        if (libro.prestamo()) {
            System.out.println("Hay ejemplares disponibles para continuar con el prestamo");
            prestamo.setLibro(libro);
          
            System.out.println("¿Es un nuevo cliente? S/N");
            if (leer.next().equalsIgnoreCase("N")) {
                System.out.println("Lista de clientes:");
                List<Cliente> lista = DAOc.listar();
                for (Cliente aux : lista) {
                    System.out.println(aux.toString());
                }
                System.out.println("Ingrese el ID del cliente");
                int id1 = leer.nextInt();
                cliente = DAOc.buscarPorID(id1);
            } else {
                cliente = clienteS.crearCliente();
            }
            
            prestamo.setCliente(cliente);
            System.out.println("Ahora ingresaremos la fecha de retiro del libro");
            System.out.println("Ingrese el dia");
            int dia = leer.nextInt();
            System.out.println("Ingrese el mes");
            int mes = leer.nextInt();
            System.out.println("Ingrese el año");
            int año = leer.nextInt();
            LocalDate fechaRetiro = LocalDate.of(año, mes, dia);
            prestamo.setFechaPrestamo(fechaRetiro);
            
            System.out.println("Ahora ingresaremos la fecha de devolucion del libro");
            System.out.println("Ingrese el dia");
            int diaDev = leer.nextInt();
            System.out.println("Ingrese el mes");
            int mesDev = leer.nextInt();
            System.out.println("Ingrese el año");
            int añoDev = leer.nextInt();
            LocalDate fechaDevolucion = LocalDate.of(añoDev, mesDev, diaDev);
            prestamo.setFechaDevolucion(fechaDevolucion);
           
            prestamo.setDevolucion(false);
        } else {
            System.out.println("Lo sentimos, no se encuentran ejemplares disponibles");
        }
        
        DAO.agregarPrestamo(prestamo);
        DAOl.modificarLibro(libro);
        return prestamo;
    }

    public void devolverLibro() {
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        LibroDAO DAOl = new LibroDAO();
        Libro libro = new Libro();
        Prestamo prestamo = new Prestamo();
        List<Prestamo> listaPrestamo = new ArrayList();
        
        System.out.println("Ingrese el nombre del cliente que desea devolver el libro");
        String nombre = leer.next();
        listaPrestamo = DAO.buscarPorNombreLista(nombre);
        for (Prestamo aux : listaPrestamo) {
            System.out.println(aux.toString());
        }
        
        System.out.println("Ingrese el ID del prestamo a devolver");
        int ID = leer.nextInt();
        prestamo = DAO.buscarPorID(ID);
        
        if (!prestamo.getDevolucion()) {
            System.out.println("Ingrese el ID del libro a devolver");
            int idLibro = leer.nextInt();
            libro = DAOl.buscarPorID(idLibro);
            prestamo.setLibro(libro);

            System.out.println("Ingresaremos la fecha que realiza la devolucion");
            System.out.println("Ingrese el dia que realizo la devolucion");
            int dia = leer.nextInt();
            System.out.println("Ingrese el mes que realizo la devolucion");
            int mes = leer.nextInt();
            System.out.println("Ingrese el año que realizo la devolucion");
            int año = leer.nextInt();
            LocalDate fechaAux = LocalDate.of(año, mes, dia);

            if (fechaAux.equals(prestamo.getFechaDevolucion())) {
                System.out.println("El cliente ha cumplido en tiempo y forma");
                prestamo.setDevolucion(true);
            } else {
                System.out.println("El cliente se ha retrasado en la devolucion del libro");
                prestamo.setDevolucion(true);
                prestamo.setFechaDevolucion(fechaAux);
            }

            if (libro.devolucion()) {
                System.out.println("La devolucion se ha hecho correctamente");
            }

            DAO.modificarPrestamo(prestamo);
            DAOl.modificarLibro(libro);
            
        } else {
            System.out.println("El cliente ya ha cumplido con su devolucion");
        }
    }
    
    public void eliminarPrestamo(){
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        List<Prestamo> listaPrestamo = new ArrayList();
        Prestamo prestamo = new Prestamo();
        
        System.out.println("Ingrese el nombre del cliente que desee desvincular el prestamo");
        String nombreCliente = leer.next();
        listaPrestamo = DAO.buscarPorNombreLista(nombreCliente);
        for (Prestamo aux : listaPrestamo) {
            System.out.println(aux.toString());
        }
        
        System.out.println("Ingrese el ID del prestamo a eliminar");
        int idEliminado = leer.nextInt();
        
        prestamo = DAO.buscarPorID(idEliminado);
        
        if (prestamo != null) {
            String respuesta;
            do {
                System.out.println("¿Desea eliminar el prestamo de: " + nombreCliente + "? (S/N)");
                respuesta = leer.next();
                if (respuesta.equalsIgnoreCase("S")) {
                    System.out.println(prestamo);
                    DAO.eliminarPrestamo(prestamo);
                    System.out.println("El prestamo ha sido eliminado correctamente");
                } else {
                    System.out.println("El prestamo no ha sido eliminado");
                }
            } while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));
        }
    }
    
    
}
