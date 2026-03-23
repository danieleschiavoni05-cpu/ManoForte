package org.elis.manoforte.dao.definition;

import org.elis.manoforte.model.Disponibilita;
import org.elis.manoforte.model.TipoDisponibilita;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface DisponibilitaDAO {

    List<Disponibilita> findDisponibilitaByIdProfessionista(long id) throws Exception;

    List<Disponibilita> findDisponibilitaByData(LocalDate data) throws Exception;

    List<Disponibilita> findDisponibilitaByDataOra(LocalDateTime dataora) throws Exception;

    void inserisciDisponibilita(Disponibilita disponibilita) throws Exception;
    
     List<Disponibilita> findDisponibilitaByEmailProfessionista(String email) throws Exception;

    void deleteDisponiblitaById(Long idDisponibilita) throws Exception;

    void removeDisponibilitaByDataOraEmail(LocalDate data, LocalTime ora, String email) throws Exception;

    boolean checkRicorrenzaById(Long idDisponibilita) throws Exception;

    List<Disponibilita> findDisponibilitaByEmailProfessionistaAndTipo(String email, TipoDisponibilita tipoDisponibilita) throws Exception;

    Disponibilita checkSovrapposizione(Disponibilita disponibilita, String email) throws Exception;

    void updateDisponibilitaById(Disponibilita union, Long id) throws Exception;

    Disponibilita findDisponibilitaById(Long idDisponibilita) throws Exception;
}
