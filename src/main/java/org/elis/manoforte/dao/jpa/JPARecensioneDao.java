package org.elis.manoforte.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.elis.manoforte.dao.definition.RecensioneDAO;
import org.elis.manoforte.model.Recensione;

import java.util.List;

public class JPARecensioneDao implements RecensioneDAO {
    private EntityManagerFactory emf;
    public JPARecensioneDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Recensione> findAll() {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select r from Recensione r");
            return query.getResultList();
        }
    }

    @Override
    public List<Recensione> findRecensioneLimit(int limit) {
        try(EntityManager em = emf.createEntityManager()){
            Query query = em.createQuery("select r from Recensione r join r.professionista");
            return query.setMaxResults(limit).getResultList();
        }
    }

    @Override
    public void inserisciRecensione(Recensione recensione) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(recensione);
            transaction.commit();
        }
    }

    @Override
    public List<Recensione> findByIdProfessionista(long id) {
        try(EntityManager em = emf.createEntityManager()) {
           Query query = em.createQuery("select u.recensioneRicevute from Utente u where u.id=:id");
           query.setParameter("id", id);
           return query.getResultList();
        }
    }

    @Override
    public void deleteRecensioneById(long id) {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(em.find(Recensione.class, id));
            transaction.commit();
        }
    }

    @Override
    public List<Recensione> getRecensioneByIdProfessionista(Long id) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select r from Utente u join u.recensioneRicevute r where u.id=:id");
            query.setParameter("id", id);
            return query.getResultList();
        }
    }

    @Override
    public List<Recensione> getRecensioneByIdCliente(Long id) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select r from Utente u join u.recensioneInviate r where u.id=:id");
            query.setParameter("id", id);
            return query.getResultList();
        }
    }

	@Override
	public boolean esisteRecensionePerRichiesta(long idRichiesta) {
		try (EntityManager em = emf.createEntityManager()) {
	        Long count = em.createQuery(
	            "SELECT COUNT(r) FROM Recensione r WHERE r.richiesta.id = :idRichiesta", Long.class)
	            .setParameter("idRichiesta", idRichiesta)
	            .getSingleResult();
	        return count > 0;
	    }
	}

	

}
