package org.elis.manoforte.dao.jdbc;

import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.exception.NessunValoreTrovatoException;
import org.elis.manoforte.model.Citta;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcCittaDAO implements CittaDAO {
    private DataSource dataSource;

    public JdbcCittaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Citta> getAllCitta() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM citta");
            ResultSet resultSet = statement.executeQuery();
            List<Citta> citta = new ArrayList<>();
            while (resultSet.next()) {
                citta.add(new Citta(resultSet.getLong("id"), resultSet.getString("nome")));
            }
            if(citta.isEmpty()) throw new NessunValoreTrovatoException("Nessuna città trovata.");
            return citta;
        }catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("Errore di connessione al database.");
        }
    }

    @Override
    public Citta getCittaById(long id) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM citta WHERE id=?");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return new Citta(resultSet.getLong("id"), resultSet.getString("nome"));
            }
        }
        throw new SQLException("Città non trovata.");
    }

    @Override
    public void inserisciCitta(String nome) throws Exception {
    }
}
