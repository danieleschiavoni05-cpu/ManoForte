package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.CardRichiesta;
import org.elis.manoforte.model.Disponibilita;
import org.elis.manoforte.model.Richiesta;
import org.elis.manoforte.model.StatoRichiesta;

import java.util.List;

public interface RichiestaDAO {

    void inserisciRichiesta(Richiesta richiesta) throws Exception;

    List<Richiesta> findRichiestaByIdCliente(long id) throws Exception;

    List<Richiesta> findRichiestaByIdProfessionista(long id) throws Exception;

    void updateRichiesta(Richiesta richiesta)throws Exception;

    void updateStatoRichiesta(long id, StatoRichiesta stato) throws Exception;

    Richiesta getRichiestaById(long id) throws Exception;

    List<CardRichiesta> getRichiesteByIdProfessionistaAndStato(Long id, StatoRichiesta stato) throws Exception;

    boolean checkDisponibilitaByOra(Disponibilita disp) throws Exception;

    List<Richiesta> getRichiesteListByIdProfessionistaAndStato(Long Id, StatoRichiesta statoRichiesta) throws Exception;

    List<Richiesta> getRichiesteByIdClienteAndStato(Long id, StatoRichiesta statoRichiesta) throws Exception;
}
