package org.elis.manoforte.dao.jdbc;

import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.definition.VeicoloDAO;
import org.elis.manoforte.exception.NessunValoreTrovatoException;
import org.elis.manoforte.model.Veicolo;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void inserisciVeicolo(Veicolo veicolo) throws Exception {

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

    @Override
    public void updateVeicoliProfessionista(String email, List<Long> veicoli) throws Exception {
        Connection connection = null;

        try{
            connection = dataSource.getConnection();
            //Inizio transazione
            connection.setAutoCommit(false);
                UtenteDAO utenteDAO = new JdbcUtenteDAO(dataSource);
                Long id_utente = 10L; //sostituire con funzione utenteDAO.findIdByEmail(email);

                String arrayVeicoli = veicoli.stream().map(veicolo -> "?").collect(Collectors.joining(","));


                String query = "DELETE FROM utente_veicolo WHERE id_utente = ? AND id_veicolo NOT IN ("+arrayVeicoli+")";
                try(PreparedStatement statement = connection.prepareStatement(query)){
                    statement.setLong(1, id_utente);
                    int index =2;
                    for(Long veicolo:veicoli)
                        statement.setLong(index++, veicolo);
                    statement.executeUpdate();
                }

                query = "INSERT IGNORE INTO utente_veicolo (id_utente, id_veicolo) VALUES (?,?)";

                try(PreparedStatement statement = connection.prepareStatement(query)){
                    statement.setLong(1, id_utente);
                    for(Long veicolo:veicoli){
                        statement.setLong(2,  veicolo);
                        statement.addBatch();
                    }
                    statement.executeBatch();
                }
            connection.commit();
            // Fine transazione

        }catch(SQLException e){
            if(connection!=null)
                connection.rollback();
            e.printStackTrace();
            throw new SQLException("Errore di connessione al database.");
        }finally {
            if(connection!=null){
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    @Override
    public List<Long> getVeicoliByEmailProfessionista(String email) throws Exception {
        List<Long> veicoli = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT id_veicolo as id FROM utente_veicolo WHERE " +
                    "id_utente = (SELECT id FROM utente WHERE email = ?)");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                veicoli.add(resultSet.getLong("id"));
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("Errore di connessione al database.");
        }

        return veicoli;
    }

}
