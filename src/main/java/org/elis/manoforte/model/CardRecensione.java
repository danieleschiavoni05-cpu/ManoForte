package org.elis.manoforte.model;

import java.time.LocalDate;

public class CardRecensione {
    private Long id;
    private Utente cliente;
    private LocalDate data;

    public CardRecensione(){}

    public void setId(Long id){
        this.id = id;
    }

    public void setCliente(Utente cliente){
        this.cliente = cliente;
    }

    public void setData(LocalDate data){
        this.data = data;
    }

}
