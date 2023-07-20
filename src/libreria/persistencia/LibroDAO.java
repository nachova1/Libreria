/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria.persistencia;

import java.util.List;
import javax.persistence.TypedQuery;
import libreria.entidades.Libro;

/**
 *
 * @author Admin
 */
public class LibroDAO extends DAO<Libro>{
    public void agregarLibro(Libro libro){
        super.agregar(libro);
    }
    
    public void modificarLibro(Libro libro){
        modificar(libro);
    }
    
    public void eliminarLibro(Libro libro){
        eliminar(libro);
    }
    
    public List<Libro> listar(){
        try {
            conectar();
            List<Libro> lista = em.createQuery("SELECT l FROM Libro l", Libro.class).getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
            return null;
        } finally {
            desconectar();
        }
    }
    
    public Libro buscarPorID(int id){
        Libro libro = null;
        if (id > 0) {
            try {
                conectar();
                libro = em.find(Libro.class, id);
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            } finally {
                desconectar();
            }
        } else {
            System.out.println("El ID es invalido");
        }
        return libro;
    }
    
    public List<Libro> buscarPorTitulo(String titulo){
        List<Libro> lista = null;
        if (!titulo.trim().isEmpty()) {
            try {
                conectar();
                TypedQuery<Libro> sentencia = em.createQuery("SELECT l FROM Libro l WHERE l.titulo LIKE :valor", Libro.class);
                sentencia.setParameter("valor", "%"+titulo+"%");
                lista = sentencia.getResultList();
            } catch (Exception e) {
                System.out.println("Error: Hay varias coincidencias");
                e.printStackTrace();
            } finally {
                desconectar();
            }   
        }
        return lista;
    }
    
    public List<Libro> buscarPorNombreAutor(String nombreAutor) {
        List<Libro> lista = null;
        if (!nombreAutor.trim().isEmpty()) {
            try {
                conectar();
                TypedQuery<Libro> sentencia = em.createQuery("SELECT l FROM Libro l INNER JOIN Autor a ON a.id = l.autor.id WHERE a.nombre LIKE :valor", Libro.class);
                sentencia.setParameter("valor", "%" + nombreAutor + "%");
                lista = sentencia.getResultList();
            } catch (Exception e) {
                System.out.println("Error: Hay varias coincidencias");
                e.printStackTrace();
            } finally {
                desconectar();
            }
        }
        return lista;
    }
    
    public List<Libro> buscarPorNombreEditorial(String nombreEditorial){
      List<Libro> lista = null;
        if (!nombreEditorial.trim().isEmpty()) {
            try {
                conectar();
                TypedQuery<Libro> sentencia = em.createQuery("SELECT l FROM Libro l INNER JOIN Editorial e ON e.id = l.editorial.id WHERE e.nombre LIKE :valor", Libro.class);
                sentencia.setParameter("valor", "%" + nombreEditorial + "%");
                lista = sentencia.getResultList();
            } catch (Exception e) {
                System.out.println("Error: Hay varias coincidencias");
                e.printStackTrace();
            } finally {
                desconectar();
            }
        }
        return lista;
    }
}
