package org.elis.manoforte.model;

public class Immagine {
    private Long id;
    private String nome;
    private String percorso;
    private Boolean isFotoProfilo;
    private Long id_utente;

    public Immagine(Long id, String nome, String percorso, Boolean isFotoProfilo, Long id_utente) {
        this.id = id;
        this.nome = nome;
        this.percorso = percorso;
        this.isFotoProfilo = isFotoProfilo;
        this.id_utente = id_utente;
    }
}
