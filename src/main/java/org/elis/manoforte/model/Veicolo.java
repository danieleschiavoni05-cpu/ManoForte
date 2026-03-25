package org.elis.manoforte.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Veicolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nome;
    @ManyToMany(mappedBy = "veicolo")
    private List<Utente> utente;

    @Transient
    private Long id_utente;

    public Veicolo(){}

    public Veicolo(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Utente> getUtente() {
        return utente;
    }

    public void setUtente(List<Utente> utente) {
        this.utente = utente;
    }
}
