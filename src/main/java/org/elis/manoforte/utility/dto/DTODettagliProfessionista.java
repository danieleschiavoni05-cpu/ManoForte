package org.elis.manoforte.utility.dto;

import org.elis.manoforte.model.Citta;
import org.elis.manoforte.model.Immagine;
import org.elis.manoforte.model.Professione;
import org.elis.manoforte.model.Veicolo;

import java.math.BigDecimal;
import java.util.List;

public class DTODettagliProfessionista {
    private String nomeCompleto;
    private BigDecimal mediaVoto;
    private BigDecimal tariffa;
    private String immagine;
    private List<Professione> professioni;
    private List<Veicolo> veicoli;
    private Citta citta;

    public DTODettagliProfessionista(){}

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public BigDecimal getMediaVoto() {
        return mediaVoto;
    }

    public void setMediaVoto(BigDecimal mediaVoto) {
        this.mediaVoto = mediaVoto;
    }

    public BigDecimal getTariffa(){return tariffa;}

    public void setTariffa(BigDecimal tariffa){this.tariffa = tariffa;}

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public List<Professione> getProfessioni() {
        return professioni;
    }

    public void setProfessioni(List<Professione> professioni) {
        this.professioni = professioni;
    }

    public List<Veicolo> getVeicoli() {
        return veicoli;
    }

    public void setVeicoli(List<Veicolo> veicoli) {
        this.veicoli = veicoli;
    }

    public Citta getCitta() {
        return citta;
    }

    public void setCitta(Citta citta) {
        this.citta = citta;
    }
}
