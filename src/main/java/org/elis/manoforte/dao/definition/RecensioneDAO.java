package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.Recensione;

import java.util.List;

public interface RecensioneDAO {
    List<Recensione> findAll();
    
    List<Recensione> findRecensioneLimit(int limit);

    void inserisciRecensione(Recensione recensione) throws Exception;

    List<Recensione> findByIdProfessionista(long id);
    
    void deleteRecensioneById(long id) throws Exception;

    List<Recensione> getRecensioneByIdProfessionista(Long id) throws Exception;

    List<Recensione> getRecensioneByIdCliente(Long id) throws Exception;

	boolean esisteRecensionePerRichiesta(long idRichiesta);

	
}
