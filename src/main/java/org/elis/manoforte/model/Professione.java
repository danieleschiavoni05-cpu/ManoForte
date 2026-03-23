package org.elis.manoforte.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Professione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    @Column(unique = true)
    private String nome;

    @ManyToMany(mappedBy = "professione")
    private List<Utente> utente;

    public Professione(){}

    public Professione(long id, String nome){
        this.id = id;
        this.nome = nome;
    }

    
	public String getNome() {
        return nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj instanceof Professione prof)
            return id==prof.id;
        else return false;
    }
 public void setId(long id) {
		this.id = id;
	}
	
}
