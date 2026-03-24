package org.elis.manoforte.dao.definition;

import org.elis.manoforte.dao.jdbc.JDBCDaoFactory;
import org.elis.manoforte.dao.jpa.JPADaoFactory;
import org.elis.manoforte.utility.DataSourceConfig;

import javax.xml.crypto.Data;

public abstract class DaoFactory {
    protected DaoFactory() {

    }

    private static String IMPL = System.getenv("implementation");
    private static final DaoFactory FACTORY;

    static{
        FACTORY = switch(IMPL){
            case "JDBC" -> new JDBCDaoFactory(DataSourceConfig.getDataSource());
            case "JPA" -> new JPADaoFactory();
            default -> throw new IllegalStateException("Nessuna implementazione disponibile.");
        };
    }

    public static DaoFactory getInstance(){
        return FACTORY;
    }

    public abstract AdminDAO getAdminDAO();

    public abstract DisponibilitaDAO getDisponibilitaDAO();

    public abstract ImmagineDAO getImmagineDAO();

    public abstract ProfessioneDAO getProfessioneDAO();

    public abstract RecensioneDAO getRecensioneDAO();

    public abstract RichiestaDAO getRichiestaDAO();

    public abstract UtenteDAO getUtenteDAO();

    public abstract VeicoloDAO getVeicoloDAO();

    public abstract CittaDAO getCittaDAO();
}
