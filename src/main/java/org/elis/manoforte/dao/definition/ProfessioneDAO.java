package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.Professione;

import java.util.List;

public interface ProfessioneDAO {

    List<Professione> findProfessioniByIdProfessionista(Long id) throws Exception;

    Professione findProfessioneById(Long id) throws Exception;

    List<Professione> getAllProfessioni() throws Exception;

    List<Professione> getProfessioniListById(List<Long> professioni) throws Exception;

    void addProfessione(String professione) throws Exception;

    void removeProfessione(Long id) throws Exception;

    void modificaProfessione(Long id, String nome) throws Exception;
}