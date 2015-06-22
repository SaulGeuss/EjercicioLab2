/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import ejerciciolab2.Autor;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ejerciciolab2.Libro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author adiseño.2015
 */
public class AutorJpaController implements Serializable {

    public AutorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Autor autor) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Libro libro = autor.getLibro();
            if (libro != null) {
                libro = em.getReference(libro.getClass(), libro.getIdLibro());
                autor.setLibro(libro);
            }
            em.persist(autor);
            if (libro != null) {
                Autor oldAutorOfLibro = libro.getAutor();
                if (oldAutorOfLibro != null) {
                    oldAutorOfLibro.setLibro(null);
                    oldAutorOfLibro = em.merge(oldAutorOfLibro);
                }
                libro.setAutor(autor);
                libro = em.merge(libro);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAutor(autor.getIdAutor()) != null) {
                throw new PreexistingEntityException("Autor " + autor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Autor autor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autor persistentAutor = em.find(Autor.class, autor.getIdAutor());
            Libro libroOld = persistentAutor.getLibro();
            Libro libroNew = autor.getLibro();
            if (libroNew != null) {
                libroNew = em.getReference(libroNew.getClass(), libroNew.getIdLibro());
                autor.setLibro(libroNew);
            }
            autor = em.merge(autor);
            if (libroOld != null && !libroOld.equals(libroNew)) {
                libroOld.setAutor(null);
                libroOld = em.merge(libroOld);
            }
            if (libroNew != null && !libroNew.equals(libroOld)) {
                Autor oldAutorOfLibro = libroNew.getAutor();
                if (oldAutorOfLibro != null) {
                    oldAutorOfLibro.setLibro(null);
                    oldAutorOfLibro = em.merge(oldAutorOfLibro);
                }
                libroNew.setAutor(autor);
                libroNew = em.merge(libroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = autor.getIdAutor();
                if (findAutor(id) == null) {
                    throw new NonexistentEntityException("The autor with id " + id + " no longer exists.");
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
            Autor autor;
            try {
                autor = em.getReference(Autor.class, id);
                autor.getIdAutor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The autor with id " + id + " no longer exists.", enfe);
            }
            Libro libro = autor.getLibro();
            if (libro != null) {
                libro.setAutor(null);
                libro = em.merge(libro);
            }
            em.remove(autor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Autor> findAutorEntities() {
        return findAutorEntities(true, -1, -1);
    }

    public List<Autor> findAutorEntities(int maxResults, int firstResult) {
        return findAutorEntities(false, maxResults, firstResult);
    }

    private List<Autor> findAutorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Autor.class));
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

    public Autor findAutor(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Autor.class, id);
        } finally {
            em.close();
        }
    }

    public int getAutorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Autor> rt = cq.from(Autor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
