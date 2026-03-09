package org.elis.manoforte.dao.jdbc;

import org.elis.manoforte.dao.definition.ProfessioneDAO;
import org.elis.manoforte.model.Professione;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcProfessioneDAO implements ProfessioneDAO {
    private DataSource dataSource;

    public JdbcProfessioneDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Long> findProfessioniByIdProfessionista(Long id) throws Exception {
        return List.of();
    }

    @Override
    public List<Professione> findProfessioniById(Long id) throws Exception {
        return List.of();
    }

    @Override
    public void inserisciProfessione(String nome) throws Exception {

    }

    @Override
    public List<Professione> getAllProfessioni() throws SQLException {
        List<Professione> professioni = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM professione");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Professione p = new Professione(resultSet.getLong("id"), resultSet.getString("nome"));
                professioni.add(p);
            }
        }
        return professioni;
    }

}
