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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Saul
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
        if (libro.getAutores() == null) {
            libro.setAutores(new HashSet<Autor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Autor> attachedAutores = new HashSet<Autor>();
            for (Autor autoresAutorToAttach : libro.getAutores()) {
                autoresAutorToAttach = em.getReference(autoresAutorToAttach.getClass(), autoresAutorToAttach.getIdAutor());
                attachedAutores.add(autoresAutorToAttach);
            }
            libro.setAutores(attachedAutores);
            em.persist(libro);
            for (Autor autoresAutor : libro.getAutores()) {
                autoresAutor.getLibros().add(libro);
                autoresAutor = em.merge(autoresAutor);
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
            Set<Autor> autoresOld = persistentLibro.getAutores();
            Set<Autor> autoresNew = libro.getAutores();
            Set<Autor> attachedAutoresNew = new HashSet<Autor>();
            for (Autor autoresNewAutorToAttach : autoresNew) {
                autoresNewAutorToAttach = em.getReference(autoresNewAutorToAttach.getClass(), autoresNewAutorToAttach.getIdAutor());
                attachedAutoresNew.add(autoresNewAutorToAttach);
            }
            autoresNew = attachedAutoresNew;
            libro.setAutores(autoresNew);
            libro = em.merge(libro);
            for (Autor autoresOldAutor : autoresOld) {
                if (!autoresNew.contains(autoresOldAutor)) {
                    autoresOldAutor.getLibros().remove(libro);
                    autoresOldAutor = em.merge(autoresOldAutor);
                }
            }
            for (Autor autoresNewAutor : autoresNew) {
                if (!autoresOld.contains(autoresNewAutor)) {
                    autoresNewAutor.getLibros().add(libro);
                    autoresNewAutor = em.merge(autoresNewAutor);
                }
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
            Set<Autor> autores = libro.getAutores();
            for (Autor autoresAutor : autores) {
                autoresAutor.getLibros().remove(libro);
                autoresAutor = em.merge(autoresAutor);
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
