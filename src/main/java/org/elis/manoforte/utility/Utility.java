package org.elis.manoforte.utility;

import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.exception.DatiErratiException;
import org.elis.manoforte.exception.NoImageException;
import org.elis.manoforte.model.*;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class Utility {

    public static final String DEFAULT_PROPIC_PATH = "https://images.unsplash.com/photo-1772371272218-d39eca1efdeb?q=80&w=880&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
    private static final String patternEmail = "^(.+)@(.+)$";
    private static final String patternCodiceFiscale = "^[A-Z]{6}[0-9LMNPQRSTUV]{2}[A-ABCDEHLMPRST]{1}[0-9LMNPQRSTUV]{2}[A-Z]{1}[0-9LMNPQRSTUV]{3}[A-Z]{1}$";
    private static final String patternPIVA = "^[0-9]{11}$";
    private static final UtenteDAO utenteDao = DaoFactory.getInstance().getUtenteDAO();

    public static Boolean checkData(LocalDate dataNascita) {
        return !dataNascita.isAfter(LocalDate.now().minusYears(18))
                && (LocalDate.now().getYear()-dataNascita.getYear()>=18);
    }

    private static Boolean checkTariffa(BigDecimal tariffa) {
        return tariffa!=null && tariffa.compareTo(BigDecimal.ZERO)>0;
    }

    public static DatiErratiException checkInput(String email, LocalDate data_nascita, String codice_fiscale,
                                                 String password, String confermaPassword) throws Exception {

        DatiErratiException e = new DatiErratiException();

        if(!Pattern.compile(patternEmail).matcher(email).matches())
            e.setErrEmail();
        else if(!utenteDao.checkEmailAvailability(email))
            e.setErrEmailGiaPresente();

        if(!confermaPassword.equals(password))
            e.setErrPassword();
        if(!Pattern.compile(patternCodiceFiscale).matcher(codice_fiscale).matches()&&
           !Pattern.compile(patternPIVA).matcher(codice_fiscale).matches()){
            e.setErrCF();
        }else if(!utenteDao.checkCFAvailability(codice_fiscale)){
            e.setErrCFGiaPresente();
        }
        if(!Utility.checkData(data_nascita))
            e.setErrData();

         return e;
    }

    public static Utente checkInputProfessionista(String nome, String cognome, String email, LocalDate data_nascita, String codice_fiscale,
                                                  List<Professione> professioni, BigDecimal tariffa, String password, String confermaPassword) throws Exception {

        DatiErratiException e = checkInput(email, data_nascita, codice_fiscale, password, confermaPassword);

        if(professioni==null||professioni.isEmpty()) e.setErrProfessioni();
        if(!Utility.checkTariffa(tariffa)) e.setErrTariffa();

        if(e.checkErrors()) throw e;

        return new Utente(email, password, nome, cognome, data_nascita, codice_fiscale, tariffa);
    }

    public static String getUserHomePage(Utente loggedUser) {
        Ruolo userType = loggedUser.getRuolo();
        if(userType.equals(Ruolo.ADMIN)){
            return "HomeAdmin";
        }else if(userType.equals(Ruolo.UTENTE_BASE)){
            return "homeBase";
        }else return "homeprofessionista";
    }


    public static Utente createUserObj(ResultSet resultSet) throws Exception {
        if(resultSet.getInt("ruolo") == Ruolo.ADMIN.ordinal()){
            return new Utente(
                    resultSet.getString("email"),
                    resultSet.getString("password")
            );
        }else if(resultSet.getInt("ruolo") == Ruolo.UTENTE_BASE.ordinal()){
            return new Utente(
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("nome"),
                    resultSet.getString("cognome"),
                    resultSet.getDate("data_nascita").toLocalDate(),
                    resultSet.getString("codice_fiscale")
            );
        }else{
            return new Utente(
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("nome"),
                    resultSet.getString("cognome"),
                    resultSet.getDate("data_nascita").toLocalDate(),
                    resultSet.getString("codice_fiscale"),
                    resultSet.getBigDecimal("tariffa")
            );
        }
    }

    public static Utente checkInputEditProfessionista(Utente utenteLoggato, String nome, String cognome, LocalDate dataNascita,
                                                      String codiceFiscale, List<Professione> professione, BigDecimal tariffa,
                                                      String nuovaPassword, String password, String confermaPassword) throws Exception{

        DatiErratiException e;

        if(codiceFiscale.equals(utenteLoggato.getCodiceFiscale())) {
            e = checkEditInput(dataNascita, nuovaPassword, password, confermaPassword, utenteLoggato.getPassword());
        }else{
            e = checkEditInput(dataNascita, codiceFiscale, nuovaPassword, password, confermaPassword, utenteLoggato.getPassword());
        }

        if(professione==null || professione.isEmpty()) e.setErrProfessioni();
        if(!Utility.checkTariffa(tariffa)) e.setErrTariffa();

        if(e.checkErrors()) throw e;

        return new Utente(
                utenteLoggato.getEmail(),
                password,
                nome,
                cognome,
                dataNascita,
                codiceFiscale,
                tariffa);
    }

    public static DatiErratiException checkEditInput(LocalDate data_nascita, String codice_fiscale,
                                                       String nuovaPassword, String vecchiaPassword,
                                                        String confermaPassword, String savedPassword) throws Exception {

        DatiErratiException e = new DatiErratiException();

        if(nuovaPassword!=null&&!nuovaPassword.isEmpty()&&!nuovaPassword.equals(confermaPassword)){
            e.setErrConfermaPassword();
        }

        if(!vecchiaPassword.equals(savedPassword))
            e.setErrPassword();

        if(!Pattern.compile(patternCodiceFiscale).matcher(codice_fiscale).matches()&&
                !Pattern.compile(patternPIVA).matcher(codice_fiscale).matches()){
            e.setErrCF();
        }else if(!utenteDao.checkCFAvailability(codice_fiscale)){
            e.setErrCFGiaPresente();
        }

        if(!Utility.checkData(data_nascita))
            e.setErrData();

        return e;
    }

    public static DatiErratiException checkEditInput(LocalDate data_nascita, String nuovaPassword,
                                                     String vecchiaPassword, String confermaPassword,
                                                     String savedPassword) {

        DatiErratiException e = new DatiErratiException();

        if(nuovaPassword!=null&&!nuovaPassword.isEmpty()&&!nuovaPassword.equals(confermaPassword)){
            e.setErrConfermaPassword();
        }

        if(!vecchiaPassword.equals(savedPassword))
            e.setErrPassword();

        if(!Utility.checkData(data_nascita))
            e.setErrData();

        return e;
    }
    
    public static Utente checkInputUtenteBase(String email, String password, String nome, 
            String cognome, LocalDate dataNascita, String codice_fiscale, Long citta, String confermaPassword) throws Exception {

        // DEBUG: Controlla cosa arriva
        System.out.println("Validazione per: " + email + " - CF: " + codice_fiscale);

        DatiErratiException e = checkInput(email, dataNascita, codice_fiscale, password, confermaPassword);

        if(e.checkErrors()) {
            // DEBUG: Mostra quali errori sono stati trovati
            System.out.println("Errori trovati: " + e.getMessages());
            throw e;
        }

        // Assicurati che tutti i campi siano passati correttamente al costruttore
        Utente nuovo = new Utente( email,password,nome,
				  cognome,dataNascita,codice_fiscale);
        nuovo.setEmail(email);
        nuovo.setPassword(password);
        nuovo.setNome(nome);
        nuovo.setCognome(cognome);
        nuovo.setDataNascita(dataNascita);
        nuovo.setCodiceFiscale(codice_fiscale);

        
        // Evita null sul database

        return nuovo;
    }

    public static Utente checkInputEditUtenteBase(Utente utenteLoggato, String nome, String cognome, LocalDate dataNascita,
            String codiceFiscale, Citta citta,
            String nuovaPassword, String password, String confermaPassword) throws Exception {

    DatiErratiException e;
    
    // 1. Validazione (rimane uguale)
    if(codiceFiscale.equals(utenteLoggato.getCodiceFiscale()))
        e = checkEditInput(dataNascita, nuovaPassword, password, confermaPassword, utenteLoggato.getPassword());
    else 
        e = checkEditInput(dataNascita, codiceFiscale, nuovaPassword, password, confermaPassword, utenteLoggato.getPassword());

    if(e.checkErrors()) throw e;

    // 2. LOGICA CORRETTA PER LA PASSWORD
    // Se nuovaPassword non è nulla e non è vuota, usiamo quella. 
    // Altrimenti manteniamo la password attuale dell'utente loggato.
    String passwordDaSalvare = utenteLoggato.getPassword(); 
    
    if (nuovaPassword != null && !nuovaPassword.trim().isEmpty()) {
        passwordDaSalvare = nuovaPassword; // Qui dovresti eventualmente hashare se non lo fa il DAO
    }

    // 3. Ritorno dell'oggetto con i dati corretti
    return new Utente(
        utenteLoggato.getEmail(), 
        passwordDaSalvare, // <--- CAMBIATO QUI
        nome, 
        cognome, 
        dataNascita, 
        codiceFiscale
    );
}

    public static BigDecimal calcolaMedia(List<Recensione> recensioni) {
        if (recensioni == null || recensioni.isEmpty()) return BigDecimal.ZERO;
        return recensioni.stream().map(recensione ->
                        recensione.getVoto())
                .reduce(BigDecimal.ZERO, (a,b)->a.add(b))
                .divide(BigDecimal.valueOf(recensioni.size()), RoundingMode.DOWN);
    }

    public static File getFile(String path){

        if(path==null || path.isEmpty()){
            throw new NoImageException("Nessun immagine trovata.");
        }
        
        File file = new File(path);
        if(!file.exists()||!file.isFile()) {
        	throw new NoImageException("Nessun immagine trovata.");
        }

        return file;
    }
    
}
