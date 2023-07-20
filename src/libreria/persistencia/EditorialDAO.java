/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria.persistencia;

import java.util.List;
import javax.persistence.TypedQuery;
import libreria.entidades.Editorial;

/**
 *
 * @author Admin
 */
public class EditorialDAO extends DAO<Editorial>{
    
    public void agregarEditorial(Editorial editorial){
        agregar(editorial);
    }
    
    public void modificarEditorial(Editorial editorial){
        modificar(editorial);
    }
    
    public void eliminarEditorial(Editorial editorial){
        super.eliminar(editorial);
    }
    
    public List<Editorial> listar(){
        try {
            conectar();
            List<Editorial> lista = em.createQuery("SELECT e FROM Editorial e").getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
            return null;
        } finally {
            desconectar();
        }
    }
    
    public Editorial buscarPorID(int id) {
        Editorial editorial = null;
        if (id > 0) {
            try {
                conectar();
                editorial = em.find(Editorial.class, id);
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            } finally {
                desconectar();
            }
        } else {
            System.out.println("Error: El ID ingresado es invalido");
        }
        return editorial;
    }
    
    public List<Editorial> buscarPorNombreLista(String nombre) {
        List<Editorial> lista = null;
        if (!nombre.trim().isEmpty()) {
            try {
                conectar();
                lista = em.createQuery("SELECT e FROM Editorial e WHERE e.nombre LIKE :valor", Editorial.class).setParameter("valor", "%" + nombre + "%").getResultList();
//                sentencia.setParameter("valor", "%" + nombre + "%");
//                lista = sentencia.getResultList();
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            } finally {
                desconectar();
            }
        }
        return lista;
    }
}
