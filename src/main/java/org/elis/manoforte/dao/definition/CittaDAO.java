package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.Citta;

import java.util.List;

public interface CittaDAO {

    List<Citta> getAllCitta() throws Exception;

    Citta getCittaById(long id) throws Exception;

    void inserisciCitta(Citta citta) throws Exception;

    void removeCitta(Long id) throws Exception;

    void modificaCitta(Long id, String trim) throws Exception;
}
