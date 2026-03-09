package org.elis.manoforte.utility;

import org.elis.manoforte.dao.definition.ProfessioneDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcProfessioneDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.exception.DatiErratiException;
import org.elis.manoforte.model.Ruolo;
import org.elis.manoforte.model.Utente;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class Utility {

    private static final String patternEmail = "^(.+)@(.+)$";
    private static final String patternCodiceFiscale = "^[A-Z]{6}[0-9LMNPQRSTUV]{2}[A-ABCDEHLMPRST]{1}[0-9LMNPQRSTUV]{2}[A-Z]{1}[0-9LMNPQRSTUV]{3}[A-Z]{1}$";
    private static final String patternPIVA = "^[0-9]{11}$";

    public static Boolean checkData(LocalDate dataNascita) {
        return !dataNascita.isAfter(LocalDate.now().minusYears(18))
                && (LocalDate.now().getYear()-dataNascita.getYear()>=18);
    }

    private static Boolean checkTariffa(BigDecimal tariffa) {
        return tariffa!=null && tariffa.compareTo(BigDecimal.ZERO)>0;
    }

    public static DatiErratiException checkInput(String email, LocalDate data_nascita, String codice_fiscale,
                                                 String password, String confermaPassword) throws SQLException {

        DatiErratiException e = new DatiErratiException();
        UtenteDAO utenteDAO = new JdbcUtenteDAO(DataSourceConfig.getDataSource());

        if(!Pattern.compile(patternEmail).matcher(email).matches())
            e.setErrEmail();
        else if(!utenteDAO.checkEmailAvailability(email))
            e.setErrEmailGiaPresente();

        if(!confermaPassword.equals(password))
            e.setErrPassword();
        if(!Pattern.compile(patternCodiceFiscale).matcher(codice_fiscale).matches()&&
           !Pattern.compile(patternPIVA).matcher(codice_fiscale).matches()){
            e.setErrCF();
        }else if(!utenteDAO.checkCFAvailability(codice_fiscale)){
            e.setErrCFGiaPresente();
        }
        if(!Utility.checkData(data_nascita))
            e.setErrData();

         return e;
    }

    public static Utente checkInputProfessionista(String nome, String cognome, String email, LocalDate data_nascita, String codice_fiscale,
                                                  Long citta, List<Long> professioni, List<Long> veicoli,
                                                  BigDecimal tariffa, String password, String confermaPassword) throws SQLException, DatiErratiException {

        DatiErratiException e = checkInput(email, data_nascita, codice_fiscale, password, confermaPassword);

        if(professioni==null||professioni.isEmpty()) e.setErrProfessioni();
        if(!Utility.checkTariffa(tariffa)) e.setErrTariffa();

        if(e.checkErrors()) throw e;

        return new Utente(email, password, nome, cognome, data_nascita, codice_fiscale, citta, professioni, tariffa, veicoli);
    }

    public static String getUserHomePage(Utente loggedUser) {
        Ruolo userType = loggedUser.getRuolo();
        if(userType.equals(Ruolo.ADMIN)){
            return "homeadmin";
        }else if(userType.equals(Ruolo.UTENTE_BASE)){
            return "homecliente";
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
                    resultSet.getString("nome"),
                    resultSet.getString("cognome"),
                    resultSet.getString("password"),
                    resultSet.getDate("data_nascita").toLocalDate(),
                    resultSet.getString("codice_fiscale"),
                    resultSet.getLong("id_citta")
            );
        }else{
            ProfessioneDAO professioneDAO = new JdbcProfessioneDAO(DataSourceConfig.getDataSource());
            return new Utente(
                    resultSet.getString("email"),
                    resultSet.getString("nome"),
                    resultSet.getString("cognome"),
                    resultSet.getString("password"),
                    resultSet.getDate("data_nascita").toLocalDate(),
                    resultSet.getString("codice_fiscale"),
                    resultSet.getLong("id_citta"),
                    professioneDAO.findProfessioniByIdProfessionista(resultSet.getLong("id")),
                    null,
                    null
            );
        }
    }
}
