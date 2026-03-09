package org.elis.manoforte.model;

public class Professione {
    private final String nome;
    private long id;

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
}
