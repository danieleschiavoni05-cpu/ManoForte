package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.Veicolo;

import java.util.List;

public interface VeicoloDAO {

    Veicolo getVeicoloById(long id) throws Exception;

    void inserisciVeicolo(String nome) throws Exception;

    List<Veicolo> getAllVeicolo() throws Exception;

    void updateVeicoli(String email, List<Long> veicoli) throws Exception;

    List<Long> getVeicoliByEmailProfessionista(String email) throws Exception;
}
