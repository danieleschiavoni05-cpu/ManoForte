package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.Utente;

import java.util.List;

public interface UtenteDAO {

    void inserisciProfessionista(Utente professionista) throws Exception;

    void inserisciUtente(Utente utente) throws Exception;

    Utente findByEmailPassword(String email, String password) throws Exception;

    Utente findById(long id) throws Exception;

    List<Utente> findAllProfessionisti() throws Exception;

    List<Utente> findAllProfessionistiWithConditions() throws Exception;

    void update(Utente utente) throws Exception;

    Utente delete(Utente utente) throws Exception;

    Boolean checkEmailAvailability(String email) throws Exception;

    Boolean checkCFAvailability(String codice_fiscale) throws Exception;

    List<Utente> findAllProfessionistibyProfessione(String nomeProfessione) throws Exception;

    void modificaProfessionista(Utente professionista) throws Exception;

	Utente getUtentebyEmail(String emailProfessionista) throws Exception;

	Long findIdByEmail(String email) throws Exception;

}
