/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Admin
 */
public abstract class DAO<T> {

    protected final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("LibreriaPU");
    protected EntityManager em = EMF.createEntityManager();

    protected void conectar() {
        try {
            if (!em.isOpen()) {
                em = EMF.createEntityManager();
            }
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
            em.close();
        }
    }

    protected void desconectar() {
        if (em != null) {
            try {
                if (em.isOpen()) {
                    em.close();
                }
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            }
        }
    }

    protected void agregar(T object) {
        if (object != null) {
            try {
                conectar();
                em.getTransaction().begin();
                em.persist(object);
                em.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
                em.getTransaction().rollback();
            } finally {
                desconectar();
            }
        }
    }

    protected void modificar(T object) {
        if (object != null) {
            try {
                conectar();
                em.getTransaction().begin();
                em.merge(object);
                em.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
                em.getTransaction().rollback();
            } finally {
                desconectar();
            }
        }
    }

    protected void eliminar(T object) {
        if (object != null) {
            try {
                conectar();
                em.getTransaction().begin();
                T mergedObject = em.merge(object); //Fusionar la entidad
                em.remove(mergedObject); //Eliminar la entidad fusionada
                em.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            } finally {
                desconectar();
            }
        }
    }
}
