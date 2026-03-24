package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.Professione;

import java.util.List;

public interface ProfessioneDAO {

    List<Professione> findProfessioniByIdProfessionista(Long id) throws Exception;

    Professione findProfessioneById(Long id) throws Exception;

    void inserisciProfessione(String nome) throws Exception;

    List<Professione> getAllProfessioni() throws Exception;

}