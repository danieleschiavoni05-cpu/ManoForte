package org.elis.manoforte.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Entity
@Check(constraints = "cliente!=professionista")
public class Recensione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descrizione;
    @Column(columnDefinition = "byte unsigned check(voto>0 AND voto<6")
    private Integer voto;
    @Column(nullable = false)
    private LocalDate data;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente cliente;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente professionista;

    public Recensione(Long id, String descrizione, Integer voto, LocalDate data, Utente cliente, Utente professionista) {
        this.id = id;
        this.descrizione = descrizione;
        this.voto = voto;
        this.data = data;
        this.cliente = cliente;
        this.professionista = professionista;
    }

    public Recensione() {

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

    public Utente getCliente() {
        return cliente;
    }

    public Utente getProfessionista() {
        return professionista;
    }

}
