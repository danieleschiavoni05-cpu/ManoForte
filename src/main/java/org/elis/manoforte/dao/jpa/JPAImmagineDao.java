package org.elis.manoforte.dao.jpa;

import jakarta.persistence.*;
import org.elis.manoforte.dao.definition.ImmagineDAO;
import org.elis.manoforte.model.Ruolo;
import org.elis.manoforte.model.Immagine;

import java.util.List;

public class JPAImmagineDao implements ImmagineDAO {

    private EntityManagerFactory emf;
    public JPAImmagineDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Immagine getImmagineById(long id) throws Exception {
        try(EntityManager em = emf.createEntityManager()){
            return em.find(Immagine.class, id);
        }
    }

    @Override
    public List<Immagine> findAllProfessionistiProPic() throws Exception {
        try(EntityManager em = emf.createEntityManager()){
            Query query = em.createQuery("select i from Immagine i where i.utente.ruolo = Ruolo.PROFESSIONISTA");
            return query.getResultList();
        }
    }

    @Override
    public Immagine getImmagineByIdUtente(Long id){
        try(EntityManager em = emf.createEntityManager()){
            Query query = em.createQuery("select i from Immagine i where i.utente.id = :id");
            query.setParameter("id", id);
            return (Immagine) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public void inserisciImmagine(Immagine immagine) {
        try(EntityManager em = emf.createEntityManager()){
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(immagine);
            transaction.commit();
        }
    }

    @Override
    public void removeImmagineByUtenteId(Long id){
        try(EntityManager em = emf.createEntityManager()){
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Query query = em.createQuery("delete from Immagine i where i.utente.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void sostituisciImmagine(Immagine immagine) {
        try(EntityManager em = emf.createEntityManager()){
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Query query = em.createQuery("delete from Immagine i where i.utente.id = :id");
            query.setParameter("id", immagine.getUtente().getId());
            query.executeUpdate();
            em.persist(immagine);
            transaction.commit();
        }
    }
}
