package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.Disponibilita;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DisponibilitaDAO {

    List<Disponibilita> findDisponibilitaByIdProfessionista(long id) throws Exception;

    List<Disponibilita> findDisponibilitaByData(LocalDate data) throws Exception;

    List<Disponibilita> findDisponibilitaByDataOra(LocalDateTime dataora) throws Exception;

    void inserisciDisponibilita(Disponibilita disponibilita) throws Exception;
}
