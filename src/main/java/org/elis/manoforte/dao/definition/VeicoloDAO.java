package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.Veicolo;

import java.util.List;

public interface VeicoloDAO {

    Veicolo getVeicoloById(long id) throws Exception;

    void inserisciVeicolo(Veicolo veicolo) throws Exception;

    List<Veicolo> getAllVeicolo() throws Exception;

    void updateVeicoliProfessionista(String email, List<Veicolo> veicoli) throws Exception;

    List<Veicolo> getVeicoliByEmailProfessionista(String email) throws Exception;

    List<Veicolo> getVeicoliByIds(List<Long> veicoli) throws Exception;

    void removeVeicolo(Long id) throws Exception;

    void modificaVeicoloById(Long id, String trim) throws Exception;
}
