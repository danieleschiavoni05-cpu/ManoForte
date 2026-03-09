package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.Richiesta;

import java.util.List;

public interface RichiestaDAO {

    void inserisciRichiesta(Richiesta richiesta) throws Exception;

    List<Richiesta> findRecensioneByIdCliente(long id) throws Exception;

    List<Richiesta> findRecensioneByIdProfessionista(long id)throws Exception;

    List<Richiesta> updateRichiesta(long id)throws Exception;

    Richiesta getRichiestaById(long id) throws Exception;
}
