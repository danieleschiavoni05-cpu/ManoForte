//package org.elis.manoforte.dao.jdbc;
//
//import org.elis.manoforte.dao.definition.ProfessioneDAO;
//import org.elis.manoforte.model.Professione;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class JdbcProfessioneDAO implements ProfessioneDAO {
//
//    private final DataSource dataSource;
//
//    public JdbcProfessioneDAO(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    @Override
//    public List<Professione> findProfessioniByIdProfessionista(Long id) throws Exception {
//        return List.of();
//    }
//
//    @Override
//    public Professione findProfessioneById(Long id) throws Exception {
//        return null;
//    }
//
//    @Override
//    public void inserisciProfessione(String nome) throws Exception {
//        // da implementare se serve
//    }
//
//    @Override
//    public List<Professione> getAllProfessioni() throws SQLException {
//
//        List<Professione> professioni = new ArrayList<>();
//
//        String sql = "SELECT id, nome FROM professione ORDER BY nome";
//
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql);
//             ResultSet rs = statement.executeQuery()) {
//
//            while (rs.next()) {
//                Professione p = new Professione(
//                        rs.getLong("id"),
//                        rs.getString("nome")
//                );
//                professioni.add(p);
//            }
//        }
//
//        return professioni;
//    }
//
//    @Override
//    public List<Professione> getProfessioniListById(List<Long> professioniIds) throws Exception {
//        List<Professione> professioni = new ArrayList<>();
//
//        String sql = "SELECT id, nome FROM professione where id=?";
//
//        try(Connection connection = dataSource.getConnection()){
//            PreparedStatement statement = connection.prepareStatement(sql);
//            for(Long idProfessione: professioniIds){
//                statement.setLong(1, idProfessione);
//                statement.addBatch();
//            }
//            statement.executeBatch();
//        }
//
//        return professioni;
//    }
//}
