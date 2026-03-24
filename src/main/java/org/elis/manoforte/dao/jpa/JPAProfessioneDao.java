package org.elis.manoforte.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.elis.manoforte.dao.definition.ProfessioneDAO;
import org.elis.manoforte.model.Professione;

import java.util.ArrayList;
import java.util.List;

public class JPAProfessioneDao implements ProfessioneDAO {
    private EntityManagerFactory emf;
    public JPAProfessioneDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Professione> findProfessioniByIdProfessionista(Long id) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select u.professione from Utente u where u.id=:id");
            query.setParameter("id", id);
            return query.getResultList();
        }
    }

    @Override
    public Professione findProfessioneById(Long id) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            return em.find(Professione.class, id);
        }
    }

    @Override
    public void inserisciProfessione(String nome) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
                Professione professione = new Professione();
                professione.setNome(nome);
                em.persist(professione);
            transaction.commit();
        }
    }

    @Override
    public List<Professione> getAllProfessioni() throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select p from Professione p");
            return query.getResultList();
        }
    }
}
