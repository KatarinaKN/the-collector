package app.daos;

import app.entities.Find;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class FindDAO implements IDAO<Find, Long> {

    private EntityManagerFactory emf;

    public FindDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    //Alle mine fund (vist i en liste).
    public List<Find> getAllFindsByUserId(Long userId) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Find> query = em.createQuery("SELECT f FROM Find f WHERE f.user.id = :userId", Find.class);
        query.setParameter("userId", userId);
        List<Find> userFinds = query.getResultList();
        em.close();

        return userFinds;
    }

    //Fund sorteret efter dato (stigende/faldende).
    public List<Find> getUserFindsSortedByDate(Long userId, boolean ascending) {
        EntityManager em = emf.createEntityManager();

        //Hvis metoden kaldes med "true", sættes variablen til ASC, hvis den kaldes med "false", sættes den til DESC.
        String direction = ascending ? "ASC" : "DESC";
        String jpql = "SELECT f FROM Find f WHERE f.user.id = :userId ORDER BY f.date " + direction;
        TypedQuery<Find> query = em.createQuery(jpql, Find.class);
        query.setParameter("userId", userId);
        List<Find> userFindsSortedByDate = query.getResultList();
        em.close();

        return userFindsSortedByDate;
    }

    //Alle sten i en kategori (søg på kategori).
    public List<Find> getUserFindsByCategory(Long userId, Long categoryId) {
        EntityManager em = emf.createEntityManager();

        String jpql = "SELECT f FROM Find f JOIN f.categories c WHERE f.user.id = :userId AND c.id = :categoryId";
        TypedQuery<Find> query = em.createQuery(jpql, Find.class);
        query.setParameter("userId", userId);
        query.setParameter("categoryId", categoryId);
        List<Find> userFindsByCategory = query.getResultList();
        em.close();

        return userFindsByCategory;
    }

    //Alle fund fra et fundsted (søg på fundsted).
    public List<Find> getUserFindsByFindSpot(Long userId, Long findSpotId) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Find> query = em.createQuery("SELECT f FROM Find f WHERE f.user.id = :userId AND f.findSpot.id = :findSpotId", Find.class);
        query.setParameter("userId", userId);
        query.setParameter("findSpotId", findSpotId);
        List<Find> userFindsByFindSpot = query.getResultList();
        em.close();

        return userFindsByFindSpot;
    }

    //Alle fund fra en bestemt dato (søg på dato).
    public List<Find> getUserFindsByDate(Long userId, LocalDate date) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Find> query = em.createQuery("SELECT f FROM Find f WHERE f.user.id = :userId AND f.date = :date", Find.class);
        query.setParameter("userId", userId);
        query.setParameter("date", date);
        List<Find> userFindsByDate = query.getResultList();
        em.close();

        return userFindsByDate;
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

        //TODO skal nok ikke begynde transaktion.
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
