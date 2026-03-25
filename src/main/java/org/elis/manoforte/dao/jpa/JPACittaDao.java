package org.elis.manoforte.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.model.Citta;

import java.util.List;

public class JPACittaDao implements CittaDAO {
    private EntityManagerFactory emf;

    public JPACittaDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Citta> getAllCitta() throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select c from Citta c");
            List<Citta> citta = query.getResultList();
            return citta;
        }
    }

    @Override
    public Citta getCittaById(long id) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            return em.find(Citta.class, id);
        }
    }

    @Override
    public void inserisciCitta(Citta citta) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
                em.persist(citta);
            transaction.commit();
        }
    }

    @Override
    public void removeCitta(Long id) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
                em.remove(em.find(Citta.class, id));
            transaction.commit();
        }
    }

    @Override
    public void modificaCitta(Long id, String trim) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
                Citta citta = em.find(Citta.class, id);
                citta.setNome(trim);
                em.merge(citta);
            transaction.commit();
        }
    }

}
