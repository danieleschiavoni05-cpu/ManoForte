package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.Utente;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UtenteDAO {

    void inserisciProfessionista(Utente professionista) throws Exception;

    void inserisciUtente(Utente utente) throws Exception;

    Utente findByEmailPassword(String email, String password) throws Exception;

    Utente findById(long id) throws Exception;

    List<Utente> findAllProfessionisti() throws Exception;

    List<Utente> findAllProfessionistiWithConditions() throws Exception;

    Utente update(Utente utente) throws Exception;

    Utente delete(Utente utente) throws Exception;

    Boolean checkEmailAvailability(String email) throws Exception;

    Boolean checkCFAvailability(String codice_fiscale) throws Exception;

    Map<Long, String> findAllUsersMap() throws SQLException;

    List<Utente> findAllProfessionistibyProfessione(String nomeProfessione) throws Exception;

    void modificaProfessionista(Utente professionista) throws Exception;

    Utente getUtentebyEmail(String emailProfessionista) throws Exception;

    long trovaIdProfessionistaPerEmail(String email) throws Exception;

    long trovaIdBasePerEmail(String email) throws Exception;

    boolean esistonoUtentiInCitta(long idCitta) throws Exception;

    boolean esistonoUtentiConProfessione(long idProfessione) throws Exception;
}
