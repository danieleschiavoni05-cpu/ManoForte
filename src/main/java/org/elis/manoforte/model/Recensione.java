package org.elis.manoforte.model;

import java.time.LocalDate;

public class Recensione {
    private Long id;
    private String descrizione;
    private Integer voto;
    private LocalDate data;
    private Long id_cliente;
    private Long id_professionista;

    public Recensione(Long id, String descrizione, Integer voto, LocalDate data, Long id_cliente, Long id_professionista) {
        this.id = id;
        this.descrizione = descrizione;
        this.voto = voto;
        this.data = data;
        this.id_cliente = id_cliente;
        this.id_professionista = id_professionista;
    }

    public Recensione(long id, String descrizione, int voto) {
    }

    public Long getId() {
        return id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getVoto() {
        return voto;
    }

    public LocalDate getData() {
        return data;
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public Long getId_professionista() {
        return id_professionista;
    }

}
