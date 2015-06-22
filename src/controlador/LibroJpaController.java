/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ejerciciolab2.Autor;
import ejerciciolab2.Libro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author adiseño.2015
 */
public class LibroJpaController implements Serializable {

    public LibroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Libro libro) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autor autor = libro.getAutor();
            if (autor != null) {
                autor = em.getReference(autor.getClass(), autor.getIdAutor());
                libro.setAutor(autor);
            }
            em.persist(libro);
            if (autor != null) {
                Libro oldLibroOfAutor = autor.getLibro();
                if (oldLibroOfAutor != null) {
                    oldLibroOfAutor.setAutor(null);
                    oldLibroOfAutor = em.merge(oldLibroOfAutor);
                }
                autor.setLibro(libro);
                autor = em.merge(autor);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLibro(libro.getIdLibro()) != null) {
                throw new PreexistingEntityException("Libro " + libro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Libro libro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Libro persistentLibro = em.find(Libro.class, libro.getIdLibro());
            Autor autorOld = persistentLibro.getAutor();
            Autor autorNew = libro.getAutor();
            if (autorNew != null) {
                autorNew = em.getReference(autorNew.getClass(), autorNew.getIdAutor());
                libro.setAutor(autorNew);
            }
            libro = em.merge(libro);
            if (autorOld != null && !autorOld.equals(autorNew)) {
                autorOld.setLibro(null);
                autorOld = em.merge(autorOld);
            }
            if (autorNew != null && !autorNew.equals(autorOld)) {
                Libro oldLibroOfAutor = autorNew.getLibro();
                if (oldLibroOfAutor != null) {
                    oldLibroOfAutor.setAutor(null);
                    oldLibroOfAutor = em.merge(oldLibroOfAutor);
                }
                autorNew.setLibro(libro);
                autorNew = em.merge(autorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = libro.getIdLibro();
                if (findLibro(id) == null) {
                    throw new NonexistentEntityException("The libro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Libro libro;
            try {
                libro = em.getReference(Libro.class, id);
                libro.getIdLibro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The libro with id " + id + " no longer exists.", enfe);
            }
            Autor autor = libro.getAutor();
            if (autor != null) {
                autor.setLibro(null);
                autor = em.merge(autor);
            }
            em.remove(libro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Libro> findLibroEntities() {
        return findLibroEntities(true, -1, -1);
    }

    public List<Libro> findLibroEntities(int maxResults, int firstResult) {
        return findLibroEntities(false, maxResults, firstResult);
    }

    private List<Libro> findLibroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Libro.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Libro findLibro(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Libro.class, id);
        } finally {
            em.close();
        }
    }

    public int getLibroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Libro> rt = cq.from(Libro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
