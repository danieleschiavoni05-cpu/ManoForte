package org.elis.manoforte.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.elis.manoforte.dao.definition.RecensioneDAO;
import org.elis.manoforte.model.CardRecensione;
import org.elis.manoforte.model.Recensione;

import java.sql.SQLException;
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
    public List<Recensione> findAllwithConditions() {
        return List.of();
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
           Query query = em.createQuery("select u.recensione from Utente u where u.id=:id");
           query.setParameter("id", id);
           return query.getResultList();
        }
    }

    @Override
    public void delete(long id) {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(em.find(Recensione.class, id));
            transaction.commit();
        }
    }

    @Override
    public List<CardRecensione> getRecensioneByEmailProfessionistaLimit(String email, int i) throws SQLException {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select r from Utente u join u.recensione r where u.email=:email");
            query.setParameter("email", email);
            query.setMaxResults(i);
            return query.getResultList();
        }
    }
}
