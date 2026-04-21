package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.Immagine;

public interface ImmagineDAO {

    Immagine getImmagineById(long id) throws Exception;

    Immagine getImmagineByIdUtente(Long id) throws Exception;

    void inserisciImmagine(Immagine immagine) throws Exception;

    void removeImmagineByUtenteId(Long id) throws Exception;
}
