package org.elis.manoforte.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Richiesta {
    private Long id;
    private LocalDate data;
    private LocalTime ora_inizio;
    private LocalTime ora_fine;
    private String indirizzo;
    private StatoRichiesta statoRichiesta;
    private Long id_cliente;
    private Long id_professionista;


    public Richiesta(Long id, LocalDate data, LocalTime ora_inizio, LocalTime ora_fine,
                     String indirizzo, StatoRichiesta statoRichiesta, Long id_cliente, Long id_professionista) {
        this.id = id;
        this.data = data;
        this.ora_inizio = ora_inizio;
        this.ora_fine = ora_fine;
        this.indirizzo = indirizzo;
        this.statoRichiesta = statoRichiesta;
        this.id_cliente = id_cliente;
        this.id_professionista = id_professionista;
    }

    public long getIdCliente() {
        return id_cliente;
    }

    public long getIdProfessionista() {
        return id_professionista;
    }

    public StatoRichiesta getStatoRichiesta() {
        return statoRichiesta;
    }

    public long getId() {
        return id;
    }

    public void nextStep(){
        if(statoRichiesta.equals(StatoRichiesta.IN_ATTESA_DI_CONFERMA)) statoRichiesta = StatoRichiesta.IN_CORSO;
        else statoRichiesta = StatoRichiesta.COMPLETA;
    }

}
