package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.Recensione;

import java.util.List;

public interface RecensioneDAO {
    List<Recensione> findAll();

    void inserisciRecensione(Recensione recensione) throws Exception;

    List<Recensione> findByIdProfessionista(long id);
    
    void delete(long id);
}
