package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.Immagine;

public interface ImmagineDAO {

    Immagine getImmagineById(long id) throws Exception;

    Immagine getImmagineByIdUtente(String nome) throws Exception;

    void inserisciImmagine(Immagine immagine) throws Exception;
}
