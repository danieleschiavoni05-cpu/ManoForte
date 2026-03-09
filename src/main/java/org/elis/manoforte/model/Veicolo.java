package org.elis.manoforte.model;

public class Veicolo {
    private Long id;
    private String nome;

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
}
