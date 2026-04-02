package org.elis.manoforte.utility.dto;

public class DTOGenericResponse {
    private boolean successo;
    private String messaggio;

    public DTOGenericResponse(boolean successo, String messaggio) {
        this.successo = successo;
        this.messaggio = messaggio;
    }

    public boolean isSuccesso() {
        return successo;
    }
    public String getMessaggio() {
        return messaggio;
    }
}
