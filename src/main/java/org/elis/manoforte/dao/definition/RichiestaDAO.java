package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.CardRichiesta;
import org.elis.manoforte.model.Richiesta;
import org.elis.manoforte.model.StatoRichiesta;

import java.util.List;

public interface RichiestaDAO {

    void inserisciRichiesta(Richiesta richiesta) throws Exception;

    List<Richiesta> findRichiestaByIdCliente(long id) throws Exception;

    List<Richiesta> findRichiestaByIdProfessionista(long id)throws Exception;

    void updateRichiesta(Richiesta richiesta)throws Exception;

    void updateStatoRichiesta(long id, StatoRichiesta stato) throws Exception;

    Richiesta getRichiestaById(long id) throws Exception;

    List<CardRichiesta> getRichiesteByEmailProfessionistaAndStato(String email, StatoRichiesta stato) throws Exception;
}
