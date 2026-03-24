package org.elis.manoforte.exception;

import java.util.ArrayList;
import java.util.List;

public class DatiErratiException extends RuntimeException {
    private int count;
    private boolean errEmail;
    private boolean errPassword;
    private boolean errConfermaPassword;
    private Boolean errData;
    private boolean errMailGiaPresente;
    private boolean errCFGiaPresente;
    private boolean errProfessioni;
    private boolean errCF;
    private boolean errTariffa;
    private boolean errNome;
    private boolean errCognome;
    private boolean errCitta;
    private boolean errVeicoli;

    private List<String> messages;

    public DatiErratiException() {
        super();
        count = 0;
        errEmail = false;
        errPassword = false;
        errData = false;
        errMailGiaPresente = false;
        errCFGiaPresente = false;
        errProfessioni = false;
        errCF = false;
        errNome = false;
        errCognome = false;
        errCitta = false;
        errVeicoli = false;
    }

    public void setErrData() {
        this.errData = true;
        count++;
    }

    public void setErrEmail() {
        this.errEmail = true;
        count++;
    }

    public void setErrConfermaPassword(){
        this.errConfermaPassword = true;
        count++;
    }

    public void setErrPassword() {
        this.errPassword = true;
        count++;
    }

    public void setErrEmailGiaPresente() {
        this.errMailGiaPresente = true;
        count++;
    }

    public void setErrCFGiaPresente() {
        this.errCFGiaPresente = true;
        count++;
    }

    public void setErrProfessioni() {
        this.errProfessioni = true;
        count++;
    }

    public void setErrCF() {
        this.errCF = true;
        count++;
    }

    public void setErrCitta() {
        errCitta = true;
        count++;
    }

    public void setErrTariffa() {
        this.errTariffa= true;
        count++;
    }

    public void setErrNome(){
        this.errNome = true;
        count++;
    }

    public void setErrCognome(){
        this.errCognome= true;
        count++;
    }

    public boolean checkErrors(){
        return errEmail || errPassword || errData || errMailGiaPresente || errCFGiaPresente || errProfessioni || errCF || errTariffa;
    }

    public boolean checkEditErrors(){
        return errPassword || errData || errCFGiaPresente || errCF || errTariffa || errConfermaPassword;
    }

    public void buildErrorMessage(){
        messages = new ArrayList<>();
        if(count>=3){
            messages.add("Controllare i campi inseriti.");
            return;
        }
        if(errEmail) messages.add("La mail è in un formato invalido.");
        if(errPassword) messages.add("Le password non coincidono.");
        if(errData!=null&&errData) messages.add("La data inserita non è valida.");
        if(errMailGiaPresente) messages.add("Email già registrata.");
        if(errCFGiaPresente) messages.add("Codice fiscale già registrato.");
        if(errCF) messages.add("Codice fiscale non valido.");
        if(errTariffa) messages.add("La tariffa deve essere maggiore di 0.");
        if(errProfessioni) messages.add("Inserire almeno una professione.");
    }

    public void buildEmptyErrorMessage(){
        messages = new ArrayList<>();
        if(count>=3){
            messages.add("Compilare tutti i campi.");
            return;
        }
        if(errEmail) messages.add("Inserire una mail.");
        if(errNome) messages.add("Inserire un nome.");
        if(errCognome) messages.add("Inserire un cognome.");
        if(errConfermaPassword) messages.add("Confermare la password inserita.");
        if(errPassword) messages.add("Inserire una password.");
        if(errCitta) messages.add("Selezionare almeno una città.");
        if(errData!=null&&errData) messages.add("Inserire una data.");
        if(errCF) messages.add("Inserire un codice fiscale.");
        if(errTariffa) messages.add("La tariffa deve essere maggiore di 0.");
        if(errProfessioni) messages.add("Selezionare almeno una professione.");
        if(errCitta)  messages.add("Inserire almeno una citta.");

    }

    public void buildErrorEditMessage(){
        messages = new ArrayList<>();
        if(count>=3){
            messages.add("Controllare i campi inseriti.");
            return;
        }
        if(errConfermaPassword) messages.add("Le password non coincidono.");
        if(errPassword) messages.add("La password corrente è errata.");
        if(errData!=null&&errData) messages.add("La data inserita non è valida.");
        if(errCFGiaPresente) messages.add("Codice fiscale già registrato.");
        if(errCF) messages.add("Codice fiscale non valido.");
        if(errTariffa) messages.add("La tariffa deve essere maggiore di 0.");
    }

    public List<String> getMessages() {
        return messages;
    }
    
    public void buildErrorEditMessageBase(){
        messages = new ArrayList<>();
        if(count>=3){
            messages.add("Controllare i campi inseriti.");
            return;
        }
        if(errConfermaPassword) messages.add("Le password non coincidono.");
        if(errPassword) messages.add("La password corrente è errata.");
        if(errData!=null&&errData) messages.add("La data inserita non è valida.");
        if(errCFGiaPresente) messages.add("Codice fiscale già registrato.");
        if(errCF) messages.add("Codice fiscale non valido.");
        if(errCitta)  messages.add("Inserire almeno una citta.");
    }
}
