package org.elis.manoforte.model;

import jakarta.persistence.*;

@Entity
public class Immagine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String percorso;
    private Boolean isFotoProfilo;
    @ManyToOne
    private Utente utente;

    public Immagine() {}

    public Immagine(Long id, String nome, String percorso, Boolean isFotoProfilo, Utente utente) {
        this.id = id;
        this.nome = nome;
        this.percorso = percorso;
        this.isFotoProfilo = isFotoProfilo;
        this.utente = utente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPercorso() {
        return percorso;
    }

    public void setPercorso(String percorso) {
        this.percorso = percorso;
    }

    public Boolean getFotoProfilo() {
        return isFotoProfilo;
    }

    public void setFotoProfilo(Boolean fotoProfilo) {
        isFotoProfilo = fotoProfilo;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}
