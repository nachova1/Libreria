/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria.persistencia;

import java.util.List;
import javax.persistence.TypedQuery;
import libreria.entidades.Cliente;

/**
 *
 * @author Admin
 */
public class ClienteDAO extends DAO<Cliente>{
    public void agregarCliente(Cliente cliente){
        agregar(cliente);
    }
    
    public void modificarCliente(Cliente cliente){
        modificar(cliente);
    }
    
    public void eliminarCliente(Cliente cliente){
        eliminar(cliente);
    }
    
    public List<Cliente> buscarPorNombreLista(String nombre) {
        List<Cliente> lista = null;
        if (!nombre.trim().isEmpty()) {
            try {
                conectar();
                TypedQuery<Cliente> sentencia = em.createQuery("SELECT c FROM Cliente c WHERE c.nombre LIKE :valor", Cliente.class);
                sentencia.setParameter("valor", "%" + nombre + "%");
                lista = sentencia.getResultList();
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            } finally {
                desconectar();
            }
        }
        return lista;
    }

    public List<Cliente> listar() {
        try {
            conectar();
            List<Cliente> lista = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
            return null;
        } finally {
            desconectar();
        }
    }
       
    public Cliente buscarPorID(int id) {
        Cliente cliente = null;
        if (id > 0) {
            try {
                conectar();
                cliente = em.find(Cliente.class, id);
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            } finally {
                desconectar();
            }
        } else {
            System.out.println("El ID ingresado no es valido");
        }
        return cliente;
    }  
}
