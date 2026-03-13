package org.elis.manoforte.utility;

import java.util.List;

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
