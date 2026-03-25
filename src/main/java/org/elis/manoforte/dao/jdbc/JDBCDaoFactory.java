//package org.elis.manoforte.dao.jdbc;
//
//import org.elis.manoforte.dao.definition.*;
//import org.elis.manoforte.dao.jpa.*;
//
//import javax.sql.DataSource;
//
//public class JDBCDaoFactory extends DaoFactory {
//    private DataSource dataSource;
//
//    private final JdbcUtenteDAO utenteDao;
//    private final JdbcCittaDAO cittaDao;
//    //private final JdbcAdminDAO adminDao;
//    private final JdbcProfessioneDAO professioneDao;
//    private final JdbcDisponibilitaDAO disponibilitaDao;
//    private final JdbcVeicoloDAO veicoloDao;
//    private final RecensioneDAOJDBC recensioneDao;
//    private final RichiestaDAOJDBC richiestaDao;
//
//    public JDBCDaoFactory(DataSource dataSource) {
//        this.dataSource = dataSource;
//        this.utenteDao = new JdbcUtenteDAO(dataSource);
//        this.cittaDao = new JdbcCittaDAO(dataSource);
//        this.professioneDao = new JdbcProfessioneDAO(dataSource);
//        this.disponibilitaDao = new JdbcDisponibilitaDAO(dataSource);
//        this.veicoloDao = new JdbcVeicoloDAO(dataSource);
//        this.recensioneDao = new RecensioneDAOJDBC(dataSource);
//        this.richiestaDao = new RichiestaDAOJDBC(dataSource);
//    }
//
//    @Override
//    public AdminDAO getAdminDAO() {
//        return null;
//    }
//
//    @Override
//    public DisponibilitaDAO getDisponibilitaDAO() {
//        return disponibilitaDao;
//    }
//
//    @Override
//    public ImmagineDAO getImmagineDAO() {
//        return null;
//    }
//
//    @Override
//    public ProfessioneDAO getProfessioneDAO() {
//        return professioneDao;
//    }
//
//    @Override
//    public RecensioneDAO getRecensioneDAO() {
//        return recensioneDao;
//    }
//
//    @Override
//    public RichiestaDAO getRichiestaDAO() {
//        return richiestaDao;
//    }
//
//    @Override
//    public UtenteDAO getUtenteDAO() {
//        return utenteDao;
//    }
//
//    @Override
//    public VeicoloDAO getVeicoloDAO() {
//        return veicoloDao;
//    }
//
//    @Override
//    public CittaDAO getCittaDAO() {
//        return cittaDao;
//    }
//}
