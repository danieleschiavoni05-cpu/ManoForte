package org.elis.manoforte.dao.jdbc;

import org.elis.manoforte.dao.definition.VeicoloDAO;
import org.elis.manoforte.exception.NessunValoreTrovatoException;
import org.elis.manoforte.model.Veicolo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcVeicoloDAO implements VeicoloDAO {
    DataSource dataSource;

    public JdbcVeicoloDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Veicolo getVeicoloById(long id) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM veicolo WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return new Veicolo(resultSet.getLong("id"), resultSet.getString("nome"));
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("Errore di connessione al database.");
        }

        throw new NessunValoreTrovatoException("Veicolo non trovato");
    }

    @Override
    public void inserisciVeicolo(String nome) throws Exception {

    }

    @Override
    public List<Veicolo> getAllVeicolo() throws Exception {
        List<Veicolo> veicoli = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM veicolo");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                veicoli.add(new Veicolo(resultSet.getLong("id"), resultSet.getString("nome")));
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("Errore di connessione al database.");
        }
        if(veicoli.isEmpty()) throw new NessunValoreTrovatoException("Nessun veicolo trovato.");
        return veicoli;
    }
}
