package org.elis.manoforte.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Citta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    @OneToMany(mappedBy = "citta")
    private List<Utente> utente;

    public Citta(Long id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public Citta() {

    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
