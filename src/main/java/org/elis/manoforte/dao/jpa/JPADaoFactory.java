package org.elis.manoforte.dao.jpa;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.elis.manoforte.dao.definition.*;

import java.util.HashMap;
import java.util.Map;

public class JPADaoFactory extends DaoFactory {
    private EntityManagerFactory emf;

    private final JPAUtenteDao utenteDao;
    private final JPACittaDao cittaDao;
    //private final JPAAdminDao adminDao;
    private final JPAProfessioneDao professioneDao;
    private final JPADisponibilitaDao disponibilitaDao;
    private final JPAVeicoloDao veicoloDao;
    private final JPARecensioneDao recensioneDao;
    private final JPARichiestaDao richiestaDao;

    public JPADaoFactory() {
        Map<String,String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
        properties.put("jakarta.persistence.jdbc.url", "jdbc:mysql://localhost:3306/ManoForte_jpa");
        properties.put("jakarta.persistence.jdbc.user", "root");
        properties.put("jakarta.persistence.jdbc.password", System.getenv("db_password"));
        properties.put("jakarta.persistence.schema-generation.database.action", "update");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");

        this.emf = Persistence.createEntityManagerFactory("default", properties);
        this.utenteDao = new JPAUtenteDao(emf);
        this.cittaDao = new JPACittaDao(emf);
        this.professioneDao = new JPAProfessioneDao(emf);
        this.disponibilitaDao = new JPADisponibilitaDao(emf);
        this.veicoloDao = new JPAVeicoloDao(emf);
        this.recensioneDao = new JPARecensioneDao(emf);
        this.richiestaDao = new JPARichiestaDao(emf);
    }

//    @Override
//    public AdminDAO getAdminDAO() {
//        return null;
//    }

    @Override
    public DisponibilitaDAO getDisponibilitaDAO() {
        return disponibilitaDao;
    }

    @Override
    public ImmagineDAO getImmagineDAO() {
        return null;
    }

    @Override
    public ProfessioneDAO getProfessioneDAO() {
        return professioneDao;
    }

    @Override
    public RecensioneDAO getRecensioneDAO() {
        return recensioneDao;
    }

    @Override
    public RichiestaDAO getRichiestaDAO() {
        return richiestaDao;
    }

    @Override
    public UtenteDAO getUtenteDAO() {
        return utenteDao;
    }

    @Override
    public VeicoloDAO getVeicoloDAO() {
        return veicoloDao;
    }

    @Override
    public CittaDAO getCittaDAO() {
        return cittaDao;
    }
}
