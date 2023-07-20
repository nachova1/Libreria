/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria.persistencia;

import java.util.List;
import javax.persistence.TypedQuery;
import libreria.entidades.Autor;

/**
 *
 * @author Admin
 */
public final class AutorDAO extends DAO<Autor> {

    public void agregarAutor(Autor autor) {
        agregar(autor);
    }

    public void modificarAutor(Autor autor) {
        modificar(autor);
    }

    public void eliminarAutor(Autor autor) {
        super.eliminar(autor);
    }

    public List<Autor> listar() {
        try {
            conectar();
            List<Autor> lista = em.createQuery("SELECT a FROM Autor a").getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
            return null;
        } finally {
            desconectar();
        }
    }

    public Autor buscarPorID(int id) {
        Autor autor = null;
        if (id > 0) {
            try {
                conectar();
                autor = em.find(Autor.class, id);
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            } finally {
                desconectar();
            }
        } else {
            System.out.println("Error: El ID ingresado es invalido");
        }
        return autor;
    }

    public Autor buscarPorNombre(String nombre) {
        Autor autor = null;
        if (!nombre.trim().isEmpty()) {
            try {
                conectar();
                TypedQuery<Autor> sentencia = em.createQuery("SELECT a FROM Autor a WHERE a.nombre LIKE :valor", Autor.class);
                sentencia.setParameter("valor", "%"+nombre+"%");
                autor = sentencia.getSingleResult();
            } catch (Exception e) {
                System.out.println("Error: Hay varias coincidencias");
                e.printStackTrace();
            } finally {
                desconectar();
            }
        }
        return autor;
    }

    public List<Autor> buscarPorNombreLista(String nombre) {
        List<Autor> lista = null;
        if (!nombre.trim().isEmpty()) {
            try {
                conectar();
                TypedQuery<Autor> sentencia = em.createQuery("SELECT a FROM Autor a WHERE a.nombre LIKE :valor", Autor.class);
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
}
