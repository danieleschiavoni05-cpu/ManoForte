package org.elis.manoforte.utility.dto;

import org.elis.manoforte.model.Richiesta;

public class DTOResponseDettagliRichiesta {
    private Richiesta richiesta;
    private String committente;
    private String citta;

    public DTOResponseDettagliRichiesta(Richiesta richiesta, String committente, String citta) {
        this.richiesta = richiesta;
        this.committente = committente;
        this.citta = citta;
    }

    public Richiesta getRichiesta() {return richiesta;}

    public String getCommittente() {return committente;}

    public String getCitta() {
        return citta;
    }
}
