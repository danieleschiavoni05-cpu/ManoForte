package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.Professione;

import java.util.List;

public interface ProfessioneDAO {

    List<Long> findProfessioniByIdProfessionista(Long id) throws Exception;

    List<Professione> findProfessioniById(Long id) throws Exception;

    void inserisciProfessione(String nome) throws Exception;

    List<Professione> getAllProfessioni() throws Exception;

}