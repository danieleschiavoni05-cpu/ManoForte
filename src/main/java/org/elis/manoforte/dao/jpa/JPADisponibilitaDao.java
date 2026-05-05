package org.elis.manoforte.dao.jpa;

import jakarta.persistence.*;
import org.elis.manoforte.dao.definition.DisponibilitaDAO;
import org.elis.manoforte.model.Disponibilita;
import org.elis.manoforte.model.TipoDisponibilita;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class JPADisponibilitaDao implements DisponibilitaDAO {
    private EntityManagerFactory emf;
    public JPADisponibilitaDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Disponibilita> findDisponibilitaByIdProfessionista(long id) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select d from Utente u join u.disponibilita d where u.id=:id");
            query.setParameter("id", id);
            return query.getResultList();
        }
    }

    @Override
    public List<Disponibilita> findDisponibilitaByData(LocalDate data) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select d from Disponibilita d where d.data=:data");
            query.setParameter("data", data);
            return query.getResultList();
        }
    }

    @Override
    public List<Disponibilita> findDisponibilitaByDataOra(LocalDateTime dataora) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select d from Disponibilita d where d.data=:data and d.ora_inizio=:ora_inizio");
            query.setParameter("data", dataora.toLocalDate());
            query.setParameter("ora_inizio", dataora.toLocalTime());
            return query.getResultList();
        }
    }

    @Override
    public void inserisciDisponibilita(Disponibilita disponibilita) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(disponibilita);
            transaction.commit();
        }
    }

    @Override
    public List<Disponibilita> findDisponibilitaByEmailProfessionista(String email) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            //Query query = em.createQuery("select d from Utente u join u.disponibilita d where u.email=:email");
            Query query = em.createQuery("SELECT d FROM Utente u JOIN u.disponibilita d WHERE u.email = :email AND NOT EXISTS (" +
                                            "SELECT r FROM Richiesta r WHERE r.professionista = u AND r.data = d.data AND r.ora_inizio = d.ora_inizio AND r.ora_fine = d.ora_fine)");
            query.setParameter("email", email);
            return query.getResultList();
        }
    }

    @Override
    public void deleteDisponiblitaById(Long idDisponibilita) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
                em.remove(em.find(Disponibilita.class, idDisponibilita));
            transaction.commit();
        }
    }

    @Override
    public void removeDisponibilitaByDataOraEmail(LocalDate data, LocalTime ora, String email) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            Query query = em.createQuery("select d from Utente u join u.disponibilita d where u.email=:email and d.data=:data and d.ora_inizio=:ora_inizio");
            transaction.begin();
                query.setParameter("email", email);
                query.setParameter("data", data);
                query.setParameter("ora_inizio", ora);
                em.remove(query.getSingleResult());
            transaction.commit();
        }
    }

    @Override
    public Disponibilita checkRicorrenzaById(Long idDisponibilita) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select d from Disponibilita d where d.id=:id and d.tipo=:tipo");
            query.setParameter("id", idDisponibilita);
            query.setParameter("tipo", TipoDisponibilita.RICORSIVO);
            return (Disponibilita) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public List<Disponibilita> findDisponibilitaByIdProfessionistaAndTipo(Long id, TipoDisponibilita tipoDisponibilita) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select d from Utente u join u.disponibilita d where u.id=:id and d.tipo=:tipo");
            query.setParameter("id", id);
            query.setParameter("tipo", tipoDisponibilita);
            return query.getResultList();
        }
    }

    @Override
    public Disponibilita checkSovrapposizione(Disponibilita disponibilita, String email) throws Exception {
        try(EntityManager em = emf.createEntityManager()){
            Query query = em.createQuery("select d from Disponibilita d where " +
                    "d.utente.email=:email and (d.ora_inizio<=:ora_fine and d.ora_fine>=:ora_inizio) and" +
                    "(d.data=:data or d.giorno_settimana=:giorno)");
            query.setParameter("email", email);
            query.setParameter("ora_fine", disponibilita.getOra_fine());
            query.setParameter("ora_inizio", disponibilita.getOra_inizio());
            query.setParameter("data", disponibilita.getData());
            query.setParameter("giorno", disponibilita.getGiorno_settimana());
            return (Disponibilita) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public void updateDisponibilitaById(Disponibilita disponibilita) throws Exception {
        try(EntityManager em = emf.createEntityManager()){
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
                em.merge(disponibilita);
            transaction.commit();
        }
    }

    @Override
    public Disponibilita findDisponibilitaById(Long idDisponibilita) throws Exception {
        try(EntityManager em = emf.createEntityManager()){
            return em.find(Disponibilita.class, idDisponibilita);
        }
    }
}
