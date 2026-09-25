package app.daos;

import app.entities.Find;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class FindDAO implements IDAO<Find, Long> {

    private EntityManagerFactory emf;

    public FindDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void create(Find find) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(find);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Find> getAll() {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Find> query = em.createQuery("SELECT f FROM Find f", Find.class);
        List<Find> allFinds = query.getResultList();
        em.close();

        return allFinds;
    }

    @Override
    public Find getById(Long id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Find find = em.find(Find.class, id);
        em.close();

        return find;
    }

    @Override
    public void update(Find find) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Find existingFind = em.find(Find.class, find.getId());
        if (existingFind == null) {
            em.getTransaction().rollback();
            em.close();
            throw new ApiException(404, "Find not found.");
        }

        if (find.getDate() != null) {existingFind.setDate(find.getDate()); }
        if (find.getPhotoURL() != null) {existingFind.setPhotoURL(find.getPhotoURL()); }
        if (find.getNote() != null) {existingFind.setNote(find.getNote()); }

        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();

        Find existingFind = em.find(Find.class, id);
        if (existingFind == null) {
            em.getTransaction().rollback();
            em.close();
            throw new ApiException(404, "Find not found");
        }

        em.remove(existingFind);
        em.getTransaction().commit();
        em.close();
    }
}
