package org.elis.manoforte.model;

import java.time.LocalDate;

public class CardRichiesta {
    private Long id;
    private Utente cliente;
    private LocalDate data;
    private StatoRichiesta statoRichiesta;

    public CardRichiesta(){}

    public void setId(Long id){
        this.id = id;
    }

    public void setCliente(Utente cliente){
        this.cliente = cliente;
    }

    public void setData(LocalDate data){
        this.data = data;
    }

    public void setStatoRichiesta(StatoRichiesta statoRichiesta){
        this.statoRichiesta = statoRichiesta;
    }

    public Long getId() {
        return id;
    }

    public Utente getCliente() {
        return cliente;
    }

    public LocalDate getData() {
        return data;
    }

    public StatoRichiesta getStatoRichiesta() {
        return statoRichiesta;
    }
}
