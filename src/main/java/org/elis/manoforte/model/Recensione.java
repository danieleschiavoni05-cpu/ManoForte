package org.elis.manoforte.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
import java.util.List;

@Entity
@Check(constraints = "cliente_id!=professionista_id")
public class Recensione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descrizione;
    @Column(columnDefinition = "tinyint unsigned check(voto>0 AND voto<6)")
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

    public Recensione(Long id, String descrizione, Integer voto, LocalDate data) {
        this.id = id;
        this.descrizione = descrizione;
        this.voto = voto;
        this.data = data;
        this.cliente = null;
        this.professionista = null;
    }

    public Recensione() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto){
        this.voto = voto;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Utente getCliente() {
        return cliente;
    }

    public void setCliente(Utente cliente){
        this.cliente = cliente;
    }

    public Utente getProfessionista() {
        return professionista;
    }

    public void setProfessionista(Utente professionista){
        this.professionista = professionista;
    }
}
