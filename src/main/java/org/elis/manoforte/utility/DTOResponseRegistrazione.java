package org.elis.manoforte.utility;

import java.util.List;

public class DTOResponseRegistrazione {
    private boolean successo;
    private String messaggio;
    private List<String> listaErrori;

    public DTOResponseRegistrazione(boolean successo, String messaggio, List<String> listaErrori) {
        this.successo = successo;
        this.messaggio = messaggio;
        this.listaErrori = listaErrori;
    }

    public boolean isSuccesso() {
        return successo;
    }
    public String getMessaggio() {
        return messaggio;
    }
    public List<String> getListaErrori() {
        return listaErrori;
    }
}
