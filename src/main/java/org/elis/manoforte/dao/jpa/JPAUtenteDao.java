package org.elis.manoforte.dao.jpa;

import jakarta.persistence.*;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.model.Ruolo;
import org.elis.manoforte.model.Utente;

import java.util.List;

public class JPAUtenteDao implements UtenteDAO {
    private EntityManagerFactory emf;
    public JPAUtenteDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void inserisciProfessionista(Utente professionista) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(professionista);
            transaction.commit();
        }
    }

    @Override
    public void inserisciUtente(Utente utente) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(utente);
            transaction.commit();
        }
    }

    @Override
    public Utente findByEmailPassword(String email, String password) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("Select u from Utente u where u.email = :email and password = :password");
            query.setParameter("email", email);
            query.setParameter("password", password);
            return (Utente) query.getSingleResult();
        }
    }

    @Override
    public Utente findById(long id) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            return em.find(Utente.class, id);
        }
    }

    @Override
    public List<Utente> findAllProfessionisti() throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("Select u from Utente u where u.ruolo = :ruolo");
            query.setParameter("ruolo", Ruolo.PROFESSIONISTA);
            return query.getResultList();
        }
    }

    @Override
    public List<Utente> findAllProfessionistiWithConditions() throws Exception {
        return List.of();
    }

    @Override
    public void update(Utente utente) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
                Utente user = em.find(Utente.class, utente.getId());
                user.setNome(utente.getNome());
                user.setPassword(utente.getPassword());
                user.setDisponibilita(utente.getDisponibilita());
                user.setCitta(utente.getCitta());
                user.setCodiceFiscale(utente.getCodice_fiscale());
                user.setDataNascita(utente.getDataNascita());
            transaction.commit();
        }
    }

    @Override
    public Utente delete(Utente utente) throws Exception {
        return null;
    }

    @Override
    public Boolean checkEmailAvailability(String email) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("Select u from Utente u where u.email = :email");
            query.setParameter("email", email);
            return query.getSingleResult() != null;
        }catch(NoResultException e){
            return true;
        }
    }

    @Override
    public Boolean checkCFAvailability(String codice_fiscale) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("Select u from Utente u where u.codice_fiscale = :codice_fiscale");
            query.setParameter("codice_fiscale", codice_fiscale);
            return query.getSingleResult() == null;
        }catch(NoResultException e){
            return true;
        }
    }


    @Override
    public List<Utente> findAllProfessionistibyProfessione(String nomeProfessione) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select u from Utente u join u.professione as p where p.nome = :nomeProfessione");
            query.setParameter("nomeProfessione", nomeProfessione);
            return query.getResultList();
        }
    }

    @Override
    public void modificaProfessionista(Utente professionista) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
                Utente user = em.find(Utente.class, professionista.getId());
                user.setNome(professionista.getNome());
                user.setPassword(professionista.getPassword());
                user.setDisponibilita(professionista.getDisponibilita());
                user.setCitta(professionista.getCitta());
                user.setCodiceFiscale(professionista.getCodice_fiscale());
                user.setDataNascita(professionista.getDataNascita());
                user.setProfessione(professionista.getProfessione());
                user.setVeicolo(professionista.getVeicolo());
            transaction.commit();
        }
    }

    @Override
    public Utente getUtentebyEmail(String email) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("Select u from Utente u where u.email = :email");
            query.setParameter("email", email);
            return (Utente) query.getSingleResult();
        }
    }

    @Override
    public Long findIdByEmail(String email) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("Select u.id from Utente u where u.email = :email");
            query.setParameter("email", email);
            return (Long) query.getSingleResult();
        }
    }

    @Override
    public Utente reinizializzaUtente(Long id){
        try(EntityManager em = emf.createEntityManager()){
            Utente u = em.find(Utente.class, id);
            return u;
        }
    }
}
