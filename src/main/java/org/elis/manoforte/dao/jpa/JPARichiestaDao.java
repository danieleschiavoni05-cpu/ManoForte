package org.elis.manoforte.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.elis.manoforte.dao.definition.RichiestaDAO;
import org.elis.manoforte.model.*;

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
            Query query = em.createQuery("select r from Richiesta r join r.cliente c join r.professionista p where r.id=: id");
            query.setParameter("id", id);
            return (Richiesta) query.getSingleResult();
        }
    }

    @Override
    public List<CardRichiesta> getRichiesteByIdProfessionistaAndStato(Long id, StatoRichiesta stato) throws Exception {
        List<CardRichiesta> richieste = new ArrayList<>();
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select r from Richiesta r join " +
                    "r.professionista p where p.id = (select u.id from Utente u where u.id = :id) and r.statoRichiesta = :stato");
            query.setParameter("id", id);
            query.setParameter("stato", stato);
            if(!query.getResultList().isEmpty()) {
                for(Richiesta r: (List<Richiesta>)query.getResultList()){
                    richieste.add(new CardRichiesta(r));
                }
            }

            return richieste;
        }
    }

    @Override
    public List<Richiesta> getRichiesteListByIdProfessionistaAndStato(Long id, StatoRichiesta statoRichiesta) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select r from Richiesta r join " +
                    "r.professionista p where p.id = (select u.id from Utente u where u.id = :id) and r.statoRichiesta = :stato");
            query.setParameter("id", id);
            query.setParameter("stato", statoRichiesta);
            return query.getResultList();
        }
    }

    @Override
    public boolean checkDisponibilitaByOra(Disponibilita disp) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select r from Richiesta r " +
                    "where r.ora_inizio<=:ora_fine and r.ora_fine>=:ora_inizio " +
                    "and r.data=:data and r.professionista=:utente " +
                    "and (r.statoRichiesta=StatoRichiesta.IN_ATTESA_DI_CONFERMA or r.statoRichiesta=StatoRichiesta.IN_CORSO)");
            query.setParameter("ora_inizio", disp.getOra_inizio());
            query.setParameter("ora_fine", disp.getOra_fine());
            query.setParameter("data", disp.getData());
            query.setParameter("utente", disp.getUtente());
            var lista = query.getResultList();
            System.out.println(lista.isEmpty());
            return lista.isEmpty();
        }
    }

    @Override
    public List<Richiesta> getRichiesteByIdClienteAndStato(Long id, StatoRichiesta statoRichiesta) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select r from Richiesta r join " +
                    "r.cliente c where c.id = (select u.id from Utente u where u.id = :id) and r.statoRichiesta = :stato");
            query.setParameter("id", id);
            query.setParameter("stato", statoRichiesta);
            return query.getResultList();
        }
    }

    @Override
    public List<Richiesta> getRichiesteListByIdProfessionistaPendingRunning(Long id) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select r from Richiesta r join " +
                    "r.professionista c where c.id = :id " +
                    "and (r.statoRichiesta = StatoRichiesta.IN_CORSO or r.statoRichiesta= StatoRichiesta.IN_ATTESA_DI_CONFERMA)");
            query.setParameter("id", id);
            var list = query.getResultList();
            System.out.println(list);
            return query.getResultList();
        }
    }
}
