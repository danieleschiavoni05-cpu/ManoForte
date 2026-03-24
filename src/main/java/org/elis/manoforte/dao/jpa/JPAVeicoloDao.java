package org.elis.manoforte.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.definition.VeicoloDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.model.Veicolo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JPAVeicoloDao implements VeicoloDAO {
    private EntityManagerFactory emf;
    public JPAVeicoloDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Veicolo getVeicoloById(long id) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            return em.find(Veicolo.class, id);
        }
    }

    @Override
    public void inserisciVeicolo(Veicolo veicolo) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(veicolo);
            transaction.commit();
        }
    }

    @Override
    public List<Veicolo> getAllVeicolo() throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select v from Veicolo v");
            List<Veicolo> veicolo = query.getResultList();
            return veicolo;
        }
    }

    @Override
    public void updateVeicoliProfessionista(String email, List<Veicolo> veicoli) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            for(Veicolo v : veicoli){
                Veicolo veicolo = em.find(Veicolo.class, v.getId());
                veicolo.setNome(v.getNome());
            }
            transaction.commit();
        }
    }

    @Override
    public List<Long> getVeicoliByEmailProfessionista(String email) throws Exception {
        try(EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("select u.veicolo from Utente u where u.email = :email");
            query.setParameter("email", email);
            return query.getResultList();
        }
    }

    @Override
    public List<Veicolo> getVeicoliByIds(List<Long> veicoli) throws Exception {
        List<Veicolo> veicolo = new ArrayList<>();
        try(EntityManager em = emf.createEntityManager()) {
            for(Long id : veicoli){
                veicolo.add(em.find(Veicolo.class, id));
            }
            return veicolo;
        }
    }
}
