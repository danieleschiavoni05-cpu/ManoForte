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
        /*try(Connection connection = dataSource.getConnection()){
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

            insertIntoUtenteProfessione(generatedKeys.getLong(1), utente.getProfessione());
            if(!utente.getVeicolo().isEmpty())
                insertIntoUtenteVeicolo(generatedKeys.getLong(1),utente.getVeicolo());
        }catch(SQLException e) {
            e.printStackTrace();
            throw new Exception("Errore di connessione con il database.");
        }*/
    }

    @Override
    public void modificaProfessionista(Utente professionista) throws Exception{
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE utente SET nome=?, cognome=?, password=?, data_nascita=?, tariffa=?, codice_fiscale=?, id_citta=? WHERE email=?");

            statement.setString(1, professionista.getNome());
            statement.setString(2, professionista.getCognome());
            statement.setString(3, professionista.getPassword());
            statement.setDate(4, Date.valueOf(professionista.getDataNascita()));
            statement.setBigDecimal(5, professionista.getTariffa());
            statement.setString(6, professionista.getCodiceFiscale());
            //statement.setLong(7, professionista.getIdCitta());
            statement.setString(8, professionista.getEmail());
            statement.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
            throw new Exception("Errore di connessione col database;");
        }
    }

    @Override
    public void inserisciUtente(Utente utente) throws Exception {
        String sql = "INSERT INTO utente (email, password, nome, cognome, data_nascita, codice_fiscale, id_citta, ruolo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, utente.getEmail());
            ps.setString(2, utente.getPassword());
            ps.setString(3, utente.getNome());
            ps.setString(4, utente.getCognome());
            ps.setDate(5, Date.valueOf(utente.getDataNascita()));
            ps.setString(6, utente.getCodiceFiscale());

            // Handling the nullable foreign key for id_citta
            /*if (utente.getIdCitta() != 0) {
                ps.setLong(7, utente.getIdCitta());
            } else {
                ps.setNull(7, java.sql.Types.BIGINT);
            }*/
            
            ps.setInt(8, utente.getRuolo().UTENTE_BASE.ordinal());

            // Execute inside the try-with-resources to ensure 'ps' is open
            ps.executeUpdate();
        } catch (SQLException e) {
            // It's usually better to log the error or wrap it in a custom exception
            throw new Exception("Error inserting user: " + e.getMessage(), e);
        }
    }

    @Override
    public Utente findByEmailPassword(String email, String password) throws Exception {
        /*try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM utente WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return Utility.createUserObj(result);
            }
        }catch(SQLException e) {
            e.printStackTrace();
            throw new Exception("Errore nella connessione col server.");
        }
        throw new UtenteNonTrovatoException("Le credenziali inserite non corrispondono a nessun account!");*/
        return null;
    }

    @Override
    public Utente findById(long id) throws Exception {

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM utente WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return null;
                //return Utility.createUserObj(resultSet);
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
                //utenti.add(Utility.createUserObj(resultSet)) ;
            }
            return utenti;
        }
    }

    @Override
    public List<Utente> findAllProfessionistiWithConditions() throws Exception {
        return List.of();
    }

    @Override
    public void update(Utente utente) throws Exception {
        // Suggerimento: usa l'ID se lo hai, è più performante e sicuro
       /* String sql = "UPDATE utente SET nome = ?, cognome = ?, data_nascita = ?, password = ?, " +
                     "tariffa = ?, codice_fiscale = ?, id_citta = ? WHERE email = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            
            // Gestione null per la data (buona pratica)
            if (utente.getDataNascita() != null) {
                ps.setDate(3, java.sql.Date.valueOf(utente.getDataNascita()));
            } else {
                ps.setNull(3, Types.DATE);
            }

            ps.setString(4, utente.getPassword());
            ps.setBigDecimal(5, utente.getTariffa());
            ps.setString(6, utente.getCodiceFiscale());
            
            // Se idCitta è Long (oggetto), controlla il null. Se è long (primitivo), ok lo 0.
            if (utente.getIdCitta() != 0 && utente.getIdCitta() > 0) {
                ps.setLong(7, utente.getIdCitta());
            } else {
                ps.setNull(7, Types.BIGINT);
            }
            
            ps.setString(8, utente.getEmail());
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new UtenteNonTrovatoException("Aggiornamento fallito: " + utente.getEmail() + " non esiste.");
            }

            return utente;

        } catch (SQLException e) {
            // Logga l'errore qui o rilancialo con un messaggio chiaro
            throw new Exception("Errore SQL durante l'update: " + e.getErrorCode(), e);
        }*/
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
	public List<Utente> findAllProfessionistibyProfessione(String nomeProfessione) throws Exception {
		List<Utente> professionisti = new ArrayList<>();
        String sql = "SELECT u.* FROM utente u " +
                     "JOIN utente_professione up ON u.id = up.id_utente " +
                     "JOIN professione p ON p.id = up.id_professione " +
                     "WHERE LOWER(p.nome) = LOWER(?) AND u.ruolo = 1 ";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nomeProfessione);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Usiamo la tua utility per mantenere coerenza nel progetto
                    //professionisti.add(Utility.createUserObj(rs));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Errore nel recupero professionisti per: " + nomeProfessione, e);
        }
        return professionisti;
    }

	@Override
	public Utente getUtentebyEmail(String emailProfessionista) throws Exception {
	    
	    // Querying the 'utente' table where the email matches
	    String sql = "SELECT * FROM utente WHERE email = ?";

	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setString(1, emailProfessionista);
	        
	        ResultSet rs=ps.executeQuery();
	            if (rs.next()) {
	                return null;
                    //return Utility.createUserObj(rs);
	            }
	            throw new Exception("Utente non trovato");
	        
	    } catch (SQLException e) {
	        throw new Exception("Errore durante la ricerca dell'utente con email: " + emailProfessionista, e);
	    }
	    
	   
	}

	@Override
	public long trovaIdProfessionistaPerEmail(String email) throws Exception {
	    String sql = "SELECT id FROM utente WHERE email = ?";
	    
	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setString(1, email);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getLong("id");
	            }
	        }
	    } catch (SQLException e) {
	        // Logga l'errore e rilancia l'eccezione come dichiarato nella firma del metodo
	        e.printStackTrace();
	        throw new Exception("Errore durante il recupero dell'ID per l'email: " + email, e);
	    }
	    
	    // Se arrivi qui, l'utente non è stato trovato. 
	    // Puoi lanciare un'eccezione specifica o restituire un valore sentinella (es. -1)
	    throw new Exception("Nessun professionista trovato con email: " + email);
	}

	@Override
	public long trovaIdBasePerEmail(String email) throws Exception {
	    String sql = "SELECT id FROM utente WHERE email = ?";
	    
	    // Inizializziamo a -1 (valore "non trovato")
	    long id = -1; 
	    
	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setString(1, email);
	       
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                id = rs.getLong("id");
	                return id; // Trovato! Esco subito
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Errore nel recupero ID per email: " + email, e);
	    }
	    
	    // Se il ResultSet era vuoto, restituirà -1
	    return id; 
	}


}
