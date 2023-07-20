/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria.servicios;

import java.util.Scanner;
import libreria.entidades.Cliente;
import libreria.persistencia.ClienteDAO;

/**
 *
 * @author Admin
 */
public class ClienteService {
    protected ClienteDAO DAO = new ClienteDAO();
    
    public Cliente crearCliente(){
        Cliente cliente = new Cliente();
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        System.out.println("Ingrese el nombre del cliente");
        String nombre = leer.next();
        cliente.setNombre(nombre);
        
        System.out.println("Ingrese el apellido del cliente");
        String apellido = leer.next();
        cliente.setApellido(apellido);
        
        System.out.println("Ingrese el documento");
        Long documento = leer.nextLong();
        cliente.setDocumento(documento);
        
        System.out.println("Ingrese el numero de telefono");
        String telefono = leer.next();
        cliente.setTelefono(telefono);
        
        DAO.agregarCliente(cliente);
        return cliente;
    }
}
