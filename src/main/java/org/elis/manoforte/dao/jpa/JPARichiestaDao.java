package org.elis.manoforte.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.elis.manoforte.dao.definition.RichiestaDAO;
import org.elis.manoforte.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JPARichiestaDao implements RichiestaDAO {
    private EntityManagerFactory emf;
    public JPARichiestaDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void inserisciRichiesta(Richiesta richiesta) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(richiesta);
            transaction.commit();
        }
    }

    @Override
    public List<Richiesta> findRichiestaByIdCliente(long id) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select r from Richiesta r join r.cliente c where c.id = :id");
            query.setParameter("id", id);
            return query.getResultList();
        }
    }

    @Override
    public List<Richiesta> findRichiestaByIdProfessionista(long id) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select r from Richiesta r join r.professionista p where p.id= :id");
            query.setParameter("id", id);
            return query.getResultList();
        }
    }

    @Override
    public void updateRichiesta(Richiesta richiesta) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Richiesta r = em.find(Richiesta.class, richiesta.getId());
                r.setStatoRichiesta(richiesta.getStatoRichiesta());
            transaction.commit();
        }
    }

    @Override
    public void updateStatoRichiesta(long id, StatoRichiesta stato) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Richiesta r = em.find(Richiesta.class, id);
            r.setStatoRichiesta(stato);
            transaction.commit();
        }
    }

    @Override
    public Richiesta getRichiestaById(long id) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            return em.find(Richiesta.class, id);
        }
    }

    @Override
    public List<CardRichiesta> getRichiesteByEmailProfessionistaAndStato(String email, StatoRichiesta stato) throws Exception {
        List<CardRichiesta> richieste = new ArrayList<>();
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select r from Richiesta r join " +
                    "r.professionista p where p.id = (select u.id from Utente u where u.email = :email) and r.statoRichiesta = :stato");
            query.setParameter("email", email);
            query.setParameter("stato", stato);
            if(!query.getResultList().isEmpty()) {
                for(Richiesta r: (List<Richiesta>)query.getResultList()){
                    richieste.add(new CardRichiesta(r));
                }
            }

            return query.getResultList();
        }
    }

    @Override
    public List<Richiesta> getRichiesteListByEmailProfessionistaAndStato(String email, StatoRichiesta statoRichiesta) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select r from Richiesta r join " +
                    "r.professionista p where p.id = (select u.id from Utente u where u.email = :email) and r.statoRichiesta = :stato");
            query.setParameter("email", email);
            query.setParameter("stato", statoRichiesta);
            return query.getResultList();
        }
    }

    @Override
    public boolean checkRequestByOra(Disponibilita disp) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select r from Richiesta r where r.ora_inizio<=:ora_inizio and r.ora_fine>=:ora_fine and r.data=:data");
            query.setParameter("ora_inizio", disp.getOra_inizio());
            query.setParameter("ora_fine", disp.getOra_fine());
            query.setParameter("data", disp.getData());
            return query.getResultList().isEmpty();
        }
    }
}
