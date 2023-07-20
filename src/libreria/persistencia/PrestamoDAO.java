/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria.persistencia;

import java.util.List;
import javax.persistence.TypedQuery;
import libreria.entidades.Prestamo;

/**
 *
 * @author Admin
 */
public class PrestamoDAO extends DAO<Prestamo>{
    
    public void agregarPrestamo(Prestamo prestamo){
        super.agregar(prestamo);
    }
    
    public void modificarPrestamo(Prestamo prestamo){
        modificar(prestamo);
    }
    
    public void eliminarPrestamo(Prestamo prestamo){
        eliminar(prestamo);
    }
    
    public Prestamo buscarPorID(int id) {
        Prestamo prestamo = null;
        if (id > 0) {
            try {
                conectar();
                prestamo = em.find(Prestamo.class, id);
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            } finally {
                desconectar();
            }
        } else {
            System.out.println("El ID ingresado no es valido");
        }
        return prestamo;
    }
    
    public List<Prestamo> buscarPorNombreLista(String nombre) {
        List<Prestamo> lista = null;
        if (!nombre.trim().isEmpty()) {
            try {
                conectar();
                TypedQuery<Prestamo> sentencia = em.createQuery("SELECT p FROM Prestamo p INNER JOIN Cliente c ON p.cliente.id = c.id WHERE c.nombre LIKE :valor", Prestamo.class);
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
    
    public List<Prestamo> listar(){
        try {
            conectar();
            List<Prestamo> lista = em.createQuery("SELECT p FROM Prestamo p", Prestamo.class).getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
            return null;
        } finally {
            desconectar();
        }
    }
     
}
