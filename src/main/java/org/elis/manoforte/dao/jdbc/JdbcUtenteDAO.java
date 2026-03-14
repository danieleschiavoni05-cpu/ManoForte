package org.elis.manoforte.dao.jdbc;

import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.exception.UtenteNonTrovatoException;
import org.elis.manoforte.model.Ruolo;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.utility.Utility;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtenteDAO implements UtenteDAO {
    private DataSource dataSource;

    public JdbcUtenteDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void inserisciProfessionista(Utente utente) throws Exception {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO utente(email, nome, cognome, password, data_nascita, codice_fiscale, ruolo, id_citta) VALUES(?,?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            System.out.println(": hello");

            statement.setString(1, utente.getEmail());
            statement.setString(2, utente.getNome());
            statement.setString(3, utente.getCognome());
            statement.setString(4, utente.getPassword());
            statement.setDate(5, Date.valueOf(utente.getDataNascita()));
            statement.setString(6, utente.getCodiceFiscale());
            statement.setInt(7, utente.getRuolo().ordinal());
            statement.setLong(8, utente.getIdCitta());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();

            insertIntoUtenteProfessione(generatedKeys.getLong(1), utente.getProfessioni());
            if(!utente.getVeicoli().isEmpty())
                insertIntoUtenteVeicolo(generatedKeys.getLong(1),utente.getVeicoli());
        }
    }

    @Override
    public void modificaProfessionista(Utente professionista) throws Exception{
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE utente SET nome=?, cognome=?, password=?, data_nascita=?, codice_fiscale=?, id_citta=? WHERE email=?");

            statement.setString(1, professionista.getNome());
            statement.setString(2, professionista.getCognome());
            statement.setString(3, professionista.getPassword());
            statement.setDate(4, Date.valueOf(professionista.getDataNascita()));
            statement.setString(5, professionista.getCodiceFiscale());
            statement.setLong(6, professionista.getIdCitta());
            statement.setString(7, professionista.getEmail());
            statement.executeUpdate();
        }
    }

    @Override
    public void inserisciUtente(Utente utente) throws Exception {

    }

    @Override
    public Utente findByEmailPassword(String email, String password) throws Exception {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM utente WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return Utility.createUserObj(result);
            }
        }
        throw new UtenteNonTrovatoException("Le credenziali inserite non corrispondono a nessun account!");
    }

    @Override
    public Utente findById(long id) throws Exception {

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM utente WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return Utility.createUserObj(resultSet);
            }
        }
        throw new UtenteNonTrovatoException("Utente non trovato!");
    }

    @Override
    public List<Utente> findAllProfessionisti() throws SQLException, Exception {
        List<Utente> utenti = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM utente WHERE Ruolo = ?");
            preparedStatement.setInt(1, Ruolo.PROFESSIONISTA.ordinal());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                utenti.add(Utility.createUserObj(resultSet)) ;
            }
            return utenti;
        }
    }

    @Override
    public List<Utente> findAllProfessionistiWithConditions() throws Exception {
        return List.of();
    }

    @Override
    public Utente update(Utente utente) throws Exception {
        return null;
    }

    @Override
    public Utente delete(Utente utente) throws Exception {
        return null;
    }

    private void insertIntoUtenteProfessione(long id_utente, List<Long> professioni) throws SQLException{
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO utente_professione(id_utente, id_professione) VALUES (?,?)");
            statement.setLong(1, id_utente);
            for(Long idProfessione: professioni){
                statement.setLong(2, idProfessione);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void insertIntoUtenteVeicolo(long id_utente, List<Long> veicoli) throws SQLException{
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO utente_veicolo(id_utente, id_veicolo) VALUES (?,?)");
            statement.setLong(1, id_utente);
            for(Long id_veicolo: veicoli){
                statement.setLong(2, id_veicolo);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    @Override
    public Boolean checkEmailAvailability(String email) throws SQLException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM utente WHERE email = ?");
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                System.out.println(email+"--> "+result.getString("email"));
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean checkCFAvailability(String codice_fiscale) throws SQLException{
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM utente WHERE codice_fiscale = ?");
            statement.setString(1, codice_fiscale);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return false;
            }
        }
        return true;
    }

    @Override
    public Map<Long, String> findAllUsersMap() throws SQLException {
        Map<Long, String>  listaUtenti = new HashMap<Long, String>();
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM utente WHERE Ruolo = ? AND RUOLO = ?");
            statement.setInt(1, Ruolo.PROFESSIONISTA.ordinal());
            statement.setInt(2, Ruolo.UTENTE_BASE.ordinal());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                listaUtenti.put(resultSet.getLong(1), resultSet.getString(2));
            }
        }
        return listaUtenti;
    }

	@Override
	public List<Utente> findAllProfessionistibyProfessione(String nomeProfessione) throws Exception {
		List<Utente> professionisti = new ArrayList<>();
        String sql = "SELECT u.* FROM utente u " +
                     "JOIN utente_professione up ON u.id = up.id_utente " +
                     "JOIN professione p ON p.id = up.id_professione " +
                     "WHERE p.nome = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nomeProfessione);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Usiamo la tua utility per mantenere coerenza nel progetto
                    professionisti.add(Utility.createUserObj(rs));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Errore nel recupero professionisti per: " + nomeProfessione, e);
        }
        return professionisti;
    }


}


